
## Document
<insert_document_link_here>

## How to build project
Install Google Cloud SDK (https://cloud.google.com/sdk/docs/install) then follow instruction below

   ```java
   allprojects {
       repositories {
           google()
           mavenCentral()
           googleArtifactsRegistryTeko(project = this@allprojects)
       }
   }
   ```

   To download any sdk, add these lines to module/build.gradle.kts

    ```java
    dependencies {
            implementation("vn.teko.loyalty:terra-loyalty-consumer:0.1.0-alpha19") // replace by your desired sdk
       }
    ```

   We will provide a secret key that allows to retrieved mobile sdks by add these lines to root build.gradle.kts. Please contact us (hong.vx@teko.vn) to get secret key.
   
   *** Teko Member *** 
   Secret Key is located here (https://confluence.teko.vn/display/TERRA/Terra+Doc+links) 

   Add a text file with name `google_registry.properties` with value

   ```java
   artifactRegistryMavenSecret={secretKey}
   ```

The project contain `ReactNative` part, so make sure to install `npm`/`yarn` then run this command to setup:

```
./install-rn-packages.sh
```

## How to publish artifacts
To publish modules, create tag with format "releases/<module_path>/<version>". This will trigger CI build & publish according module.
For example:
releases/hestia/hestia-core/v0.0.3
releases/hestia/hestia-android/v0.0.3
releases/hestia/hestia-android-native/v0.0.3
releases/hestia/hestia-android-react-native/v0.0.3
releases/hestia/hestia-android-react-native-ui-activity/v0.0.3
releases/hestia/hestia-android-react-native-ui-fragment/v0.0.3
releases/hestia/hestia-android-webapp/v0.0.3
releases/hestia/hestia-tracking-bridge/v0.0.3