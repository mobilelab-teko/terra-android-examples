# Payment CORE demo

Demonstration for using [PaymentCore](https://terra.dev.teko.vn/developer/docs/payment/v3/paymentCore/overview) and setting up necessary component.

## Project setup

### Adding credential for Teko's repository

1. In `local.properties` file, add lines as belows:

```
TekoPackage.username=<teko-package-username>
TekoPackage.password=<teko-package-token>
TekoGoogleRegistry.password=<teko-google-registry-token>
```

Notes: Please contact Terra team to get `userName` and `password` to able to sync the project.

2. Update `TerraService-[DEFAULT].json` file based on `Terra Dashboard`

## Project structure

### app

The simple application.
Integrated modules:

- Payment

## Payment Core

### Sample for Payment Core

#### Dependencies

```groovy
dependencies {

    implementation("vn.teko.android.payment:payment-core-v2:$payment_version")
    implementation("vn.teko.android.payment:payment-manager-v2:$payment_version")

}
```

#### Usage

Calling `TerraApp.initializeApp(this)` method to init config default from `TerraService-[DEFAULT].json`

Please checkout [Terra Core](https://terra.dev.teko.vn/developer/docs/terraCore/v0/quickstart) for more details.


Please checkout [Basic Flow Payment](./app/src/main/java/vn/teko/terra/demo/paymentcore/home/HomeFragment.kt) and [Other APIs](./app/src/main/java/vn/teko/terra/demo/paymentcore/other/OtherAPIsFragment.kt) for more details.