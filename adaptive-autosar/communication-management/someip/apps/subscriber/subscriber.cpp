#include "common.h"
#include "pst/com/vehiclecontrolservice_proxy.h"
#include <ara/log/logger.h>
#include <ara/core/initialization.h>
#include <thread>
#include <atomic>
#include <csignal>
#include "pipc/utils/Semaphore.hpp"

std::atomic<bool> g_rx_killswitch{false};

static void sigHandler(int f_sig)
{
    std::ignore = f_sig;
    g_rx_killswitch = true;
}

int main()
{

    struct sigaction act;
    sigemptyset(&act.sa_mask);
    act.sa_handler = sigHandler;
    act.sa_flags = 0;
    sigaction(SIGINT, &act, NULL);
    sigaction(SIGTERM, &act, NULL);

    auto ret = ara::core::Initialize();
    if(!ret.HasValue())
    {
        std::cerr << "ARA Core Initialize failed\n";
        return -1;
    }

    ara::log::Logger& log = ara::log::CreateLogger("SUB","Subscriber");
    using ProxyType = pst::com::proxy::VehicleControlServiceProxy;
    std::shared_ptr<ProxyType> l_proxy;
    pipc::utils::Semaphore sem{0,false};

    const ara::core::String SERVICE_INSTANCE_SPECIFIER = "Subscriber/Subscriber_RootSwComponent/RPort";
    ara::core::InstanceSpecifier instanceSpec(SERVICE_INSTANCE_SPECIFIER);

    bool is_service_available = false;
    
    auto l_handler = [&](ara::com::ServiceHandleContainer<ProxyType::HandleType> f_container,
                        ara::com::FindServiceHandle f_handle) {

        std::ignore = f_handle;

        if (f_container.size() == 0)
        {
            is_service_available = false;
            if ( std::atomic_load( &l_proxy ) != nullptr )
            {
                log.LogInfo() << "[Subscriber] Publisher Disconnected ";
            }
        }

        for (auto& handle : f_container)
        {
            is_service_available = true;
            log.LogInfo() << "[Subscriber] Found Instance for InstanceSpecifier:" << SERVICE_INSTANCE_SPECIFIER;
            std::shared_ptr<ProxyType> proxy = std::make_shared<ProxyType>(
            handle);
            if ( std::atomic_load( &l_proxy ) == nullptr )
            {
                log.LogInfo() << "[Subscriber] Set PROXY for Instance corresponding to InstanceSpecifier:"
                                        << SERVICE_INSTANCE_SPECIFIER;
                std::atomic_store( &l_proxy, proxy );
                sem.post();
            }
            else
            {
                log.LogInfo() << "[Subscriber] Ignore setting proxy for InstanceSpecifier: "
                                    << SERVICE_INSTANCE_SPECIFIER << "it was already set";
            }
            break;
        }
    };

    ProxyType::StartFindService(l_handler, instanceSpec);
    log.LogInfo() << "Waiting for publisher service...";
    sem.wait();
    log.LogInfo() << "Publisher service found. Registering handlers...";

    l_proxy->VehicleSpeed.SetReceiveHandler([&](){
        l_proxy->VehicleSpeed.GetNewSamples([&](const ara::com::SamplePtr<const VehicleSpd> s){
            if(s) log.LogInfo() << "[Event] VehicleSpeed:" << static_cast<int>(*s);
        });
    });

    l_proxy->ParkingBrakeStatus.SetReceiveHandler([&](){
        l_proxy->ParkingBrakeStatus.GetNewSamples([&](const ara::com::SamplePtr<const PrkBrkSta> s){
            if(s) log.LogInfo() << "[Field - Notify] ParkingBrakeStatus:" << static_cast<int>(*s);
        });
    });

    l_proxy->VehicleSpeed.Subscribe(10);
    l_proxy->ParkingBrakeStatus.Subscribe(10);

    // Method
    EvDrvRdySta_Req input{1};
    auto future = l_proxy->getEvDriveReadyStatus(input);
    log.LogInfo() << "[Method] Request Drive Ready Status (input value : 1)";
    if(future.wait_for(std::chrono::milliseconds(1000)) == ara::core::future_status::ready)
    {
        auto result = future.GetResult();
        if(result.HasValue()){
            if (result.Value().m_ev_drv_rdy_sta_resp == 0) log.LogInfo() << "[Method] getEvDriveReadyStatus:" << result.Value().m_ev_drv_rdy_sta_resp << "(None)";
            else if(result.Value().m_ev_drv_rdy_sta_resp == 1) log.LogInfo() << "[Method] getEvDriveReadyStatus:" << result.Value().m_ev_drv_rdy_sta_resp << "(Drivable)";
            else if(result.Value().m_ev_drv_rdy_sta_resp == 2) log.LogInfo() << "[Method] getEvDriveReadyStatus:" << result.Value().m_ev_drv_rdy_sta_resp << "(N/A)";
            else if(result.Value().m_ev_drv_rdy_sta_resp == 3) log.LogInfo() << "[Method] getEvDriveReadyStatus:" << result.Value().m_ev_drv_rdy_sta_resp << "(Error)";
        }   
        else
            log.LogError() << "[Method] Error: " << result.Error().Message();
    }
    else
    {
        log.LogWarn() << "[Method] No response within 1 second";
    }

    // Field
    uint8_t l_field_value = 0;

    // Field - Getter
    auto l_getter_future = l_proxy->ParkingBrakeStatus.Get();
    if(l_getter_future.wait_for(std::chrono::milliseconds(1000u)) == ara::core::future_status::ready)
    {
        try {
            if(is_service_available)
            {
                PrkBrkSta restult = l_getter_future.get();
                if (restult == 0) log.LogInfo() << "[Field - Get] ParkingBrakeStatus: " << restult << "(OFF)";
                if (restult == 1) log.LogInfo() << "[Field - Get] ParkingBrakeStatus: " << restult << "(On)";
                if (restult == 2) log.LogInfo() << "[Field - Get] ParkingBrakeStatus: " << restult << "(Error)";
            }
        } catch(const std::exception& e) {
            log.LogInfo() << "[Field - GET] Failed to get ParkingBrakeStatus : " << e.what();
        }
    }
    std::this_thread::sleep_for(std::chrono::seconds(5));

    // Field Setter : 0, 1, 2
    while(!g_rx_killswitch)
    {       
        log.LogInfo() << "[Field - SET (Req)] ParkingBrakeStatus : " << l_field_value;
        auto l_setter_future = l_proxy->ParkingBrakeStatus.Set(PrkBrkSta{l_field_value});
        if(l_setter_future.wait_for(std::chrono::milliseconds(1000u)) == ara::core::future_status::ready)
        {
            try {
                if(is_service_available)
                {
                    PrkBrkSta result = l_setter_future.get();
                    log.LogInfo() << "[Field - SET (Res)] ParkingBrakeStauts : " << result;
                }
            } catch(const std::exception& e) {
                log.LogError() << "[Field - SET] Failed to set ParkingBrakeStatus " << e.what();
            }
        }
        l_field_value = (l_field_value + 1) % 3;
        std::this_thread::sleep_for(std::chrono::seconds(5));
            
    }

    if(l_proxy)
    {
        l_proxy->VehicleSpeed.Unsubscribe();
        l_proxy->ParkingBrakeStatus.Unsubscribe();
        l_proxy.reset();
    }

    ara::core::Deinitialize();
    return 0;
}
