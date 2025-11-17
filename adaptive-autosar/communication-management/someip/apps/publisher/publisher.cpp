#include "common.h"
#include <ara/core/pmr/safe_memory_resource.h>
#include "pst/com/vehiclecontrolservice_skeleton.h"

ara::log::Logger& PublisherLog = ara::log::CreateLogger("PUB", "Publisher Log");
const ara::core::String SERVICE_INSTANCE_SPECIFIER = "Publisher/Publisher_RootSwComponent/PPort";
std::atomic<bool> g_tx_killswitch{false};
std::mutex skeMutex;

// Signal Handler
static void sigHandler(int f_sig) { g_tx_killswitch = true; }

class SampleSkeleton : public pst::com::skeleton::VehicleControlServiceSkeleton
{
public:
    SampleSkeleton(const ara::core::InstanceSpecifier& spec, ara::log::Logger& logger)
        : pst::com::skeleton::VehicleControlServiceSkeleton(spec, ara::com::MethodCallProcessingMode::kEvent)
        , l_logger(logger)
    {}
    //
    ara::core::Future<pst::com::OutputgetEvDriveReadyStatus> getEvDriveReadyStatus(const EvDrvRdySta_Req input) override
    {
        using namespace pst::com;
        if(input == 1){
            OutputgetEvDriveReadyStatus output {};
            output.m_ev_drv_rdy_sta_resp = 1; //test value
            PublisherLog.LogInfo() << "[Method] EvDrvRdyStatus: " << output.m_ev_drv_rdy_sta_resp;
            ara::core::Promise<OutputgetEvDriveReadyStatus> promise;
            promise.set_value(output);
            return promise.get_future();
        }
    }

private:
    ara::log::Logger& l_logger;
};

PrkBrkSta field_data{1};
ara::core::InstanceSpecifier instanceSpec(SERVICE_INSTANCE_SPECIFIER);
auto skeleton = std::make_unique<SampleSkeleton>(instanceSpec, PublisherLog);

ara::core::Future<PrkBrkSta> getterSample()
{
    std::lock_guard<std::mutex> lock(skeMutex);
    ara::core::Promise<PrkBrkSta> promise;
    promise.set_value(field_data);
    PublisherLog.LogInfo() << "[Field - Get] ParkingBrakeStatus: " << field_data;
    return promise.get_future();
}

ara::core::Future<PrkBrkSta> setterSample(const PrkBrkSta& data)
{
    std::lock_guard<std::mutex> lock(skeMutex);
    field_data = data;
    ara::core::Promise<PrkBrkSta> promise;
    promise.set_value(field_data);
    PublisherLog.LogInfo() << "[Field - Set] ParkingBrakeStatus: " << field_data;
    return promise.get_future();
}

// Event
int check_event = 0; // for test changed event value
void SendVehicleSpeed(std::unique_ptr<SampleSkeleton>& l_skeleton)
{
    VehicleSpd speed_data;
    check_event++;
    if(check_event < 20) speed_data = 50;
    else speed_data = 100;
    auto allocate_result = l_skeleton->VehicleSpeed.Allocate();
    auto sample = std::move(allocate_result).Value();
    *sample = speed_data;
    l_skeleton->VehicleSpeed.Send(std::move(sample));
    PublisherLog.LogInfo() << "[Event] VehicleSpeed: " << speed_data;
}


void TransmitFunc()
{
    PublisherLog.LogInfo() << "[Publisher] Skeleton created for InstanceSpecifier: " 
                       << SERVICE_INSTANCE_SPECIFIER;
                       
    // Field Getter/Setter
    skeleton->ParkingBrakeStatus.RegisterGetHandler(getterSample);
    skeleton->ParkingBrakeStatus.RegisterSetHandler(setterSample);
    
    skeleton->ParkingBrakeStatus.Update(field_data); // Notify
    PublisherLog.LogInfo() << "[Publisher] Initial Field Value : " << field_data;
    
    // Service Offer
    skeleton->OfferService();
    PublisherLog.LogInfo() << "[Publisher] Service offered.";
    ara::exec::ExecutionClient().ReportExecutionState(ara::exec::ExecutionState::kRunning);

    while(!g_tx_killswitch)
    {
        SendVehicleSpeed(skeleton);
        std::this_thread::sleep_for(std::chrono::milliseconds(1000u));
    }

    skeleton->StopOfferService();
}

int main()
{
    struct sigaction act{};
    sigemptyset(&act.sa_mask);
    act.sa_handler = sigHandler;
    sigaction(SIGINT, &act, nullptr);
    sigaction(SIGTERM, &act, nullptr);

    if(ara::core::Initialize().HasValue())
    {
        TransmitFunc();
    }
    ara::core::Deinitialize();
    return 0;
}
