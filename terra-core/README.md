# TerraCore demo

Demonstration for using [TerraCore](https://terra.dev.teko.vn/developer/docs/terraCore/v0/overview).

## Project setup

### Adding credential for Teko's repository

In `local.properties` file, add lines as belows:

```
TekoGoogleRegistry.password=<teko-google-registry-token>
```

Notes: Please contact Terra team to get `password` to able to sync the project.

## Demo initializing `TerraCore`

`TerraApp` is initialized in `MyApplication.onCreate`

Change `MyApplication.initMode` to try different ways to initialize `TerraApp`

### Default

`TerraApp` reads config from `assets/TerraConfig-[DEFAULT].json`

To access `TerraApp` instance later, use `TerraApp.getInstance()` (see `MainActivity.onCreate`)

### Without config file
`TerraApp` reads config from `terraConfig` parameter

To access `TerraApp` instance later, use `TerraApp.getInstance()` (see `MainActivity.onCreate`)

### With `appName` mode

`TerraApp` reads config from `assets/TerraConfig-<appName>.json`

To access `TerraApp` instance later, use  (see `MainActivity.onCreate`)

### Check if `TerraApp` initialized successfully

- Use `terraApp.isInitialized()` to check if `TerraApp` is initialized successfully
- If `TerraApp` initialized failed, check detail error by `(terraApp.getState() as? TerraState.InitializeFailed)?.error`   

## Further usage of `TerraApp`

Please check code in `MainActivity.onCreate`

### Get instance

- If initialized without using an `appName`, get `TerraApp` instance by `TerraApp.getInstance()`
- Else use `TerraApp.getInstance(appName = "<appName>")`

### Get App Config

- App can get its remote config in [Terra Dashboard](https://terra.teko.vn) by using `terraApp.getAppConfig()`


For further concern, please contact Terra team.

