# Adaptive AUTOSAR Application Yocto Build
This project describes how to build an Adaptive Application using Yocto and include the ETAS ISOLAR-VRTE SDK and build artifacts into a Linux image to generate the final image.
- Target Board : S32G RDB2
- Adaptive AUTOSAR Platform : ETAS ISOLAR-VRTE/RTA-VRTE


## 01. Recipe Structure
| Recipe File                        | Purpose                                                            |
| ---------------------------------- | ------------------------------------------------------------------ |
| `sdk.bb`                           | Install packages required for CMake in the sysroot                 |                                                                 |
| `ad-build.bb`                      | Build the Adaptive Application in Yocto                                                                                           |
| `vrte.bb`                          | Include the ISOLAR-VRTE SDK and build artifacts in the final image |

### Required Files
- **sdk_linux_aarch64.zip** : Configuration files required to build ETAS Adaptive Applications using CMake. Used in the sdk.bb recipe.  
- **AraCM_IPC.zip** : Example project for IPC communication from the ETAS StarterKit. Used in the ad-build.bb recipe.

  
  
## 02. Usage
It is possible to compile images tailored to specific purposes using the BSP provided by NXP. On this page, the build was performed based on fsl-image-auto.

#### (1) Set up the Yocto environment and create a build directory
```
> source nxp-setup-alb.sh -m <machine> -b <build_directory>
> bitbake fsl-image-auto
```

#### (2) Create Layer and Update Metadata
- Create a new meta-layer
```
> bitbake-layers create-layer <path-to-new-layer>
```

- Modify bblayers.conf  
Since a new layer has been created, you need to add its path to the existing bblayers.conf so that BitBake can recognize it.
Add the path of the newly created layer to the BBLAYERS variable


#### (3) Bitbake
All previous build artifacts are cleaned, and the final image was generated based on fsl-image-auto.
```
> bitbake -c cleanall fsl-image-auto
> bitbake fsl-image-auto
```
