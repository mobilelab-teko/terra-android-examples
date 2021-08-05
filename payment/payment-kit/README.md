# Payment Kit demo

Demonstration for using [PaymentKit](https://terra.dev.teko.vn/developer/docs/payment/v3/paymentKit/overview) in a Mini App and setting up necessary component in the Host app.

## Project setup

### Adding credential for Teko's repository

In `local.properties` file, add lines as belows:

```
TekoGoogleRegistry.password=<teko-google-registry-token>
```

Notes: Please contact Terra team to get `password` to able to sync the project.

## Project structure

### app

The simple Host application for running Mini applications.
Integrated modules:

- Terra
- Auth
- Payment
- Apollo
- Iris

### Native mini apps

This project contains 2 native mini apps:

- `demoMiniApp`: the mini app with self payment flow
- `demoMiniApp-02`: the mini app with host payment flow

A mini app includes 2 part:

1. `Application`

The simple Mini application for running features supported by Terra.
Integrated feature:

- **Payment**: request payment from Mini app to Super app
  The Super app will start the Mini app through an entry point. It is the `MiniAppActivity`.

2. `Connector`

The **Connector** is responsible for initializing data and starting the Mini app. To achieve this, the **Connector** must have a public class that inherited `AndroidNativeAppLauncher` interface with one method:

- `create`: initialize intent for starting Mini app activity

## Payment Kit

### Host app payment flow

Please refer to [Host app payment flow](https://terra.dev.teko.vn/developer/docs/miniAppIntegration/v0/paymentFlow/hostAppFlow) before.

This section focuses on PaymentKit in `demoMiniApp-02/demoMiniNativeApp` module. Please check out [`demoMiniApp-02`](./demoMiniApp) for more details of the connector.

#### Dependencies

```groovy
dependencies {

    implementation "vn.teko.android.payment:payment-kit:1.6.0"

}
```

#### Usage

Calling `PaymentKit.payForOrder` method

```kotlin
class MiniAppActivity : AppCompatActivity() {

        private fun requestPayment() {
        val paymentRequestBuilder =
            PaymentRequestBuilder(
                PaymentV2Request.Order(
                    orderCode = "abc-xyz-6789",
                    orderAmount = 12345,
                    displayOrderCode = "terra-6789"
                )
            )
                .setMainMethod(
                    PaymentV2Request.Payment.MainMethod(
                        PaymentV2Request.Payment.Method.All,
                        12345
                    )
                )

        PaymentKit.payForOrder(
            null,
            paymentRequest = paymentRequestBuilder.build(),
            callback = paymentCallbackV2
        )
    }

}
```

Please checkout [MiniAppActivity](./demoMiniApp-02/demoMiniNativeApp/src/main/java/vn/teko/hestia/android/demomininativeapp02/MiniAppActivity.kt) for more details.

### Payment directly (self payment flow)

Please refer to [Payment direct flow](https://terra.dev.teko.vn/developer/docs/miniAppIntegration/v0/paymentFlow/directFlow) before.

This section focuses on PaymentKit in `demoMiniApp/demoMiniNativeApp` module. Please check out [`demoMiniApp`](./demoMiniApp) for more details of the connector.

For the Payment directly flow, we have two options:

- Using default config on Terra: we don't need to config a TerraApp on the mini-app and call the PaymentKit as same as the Host Payment Flow.
- Using initialized Terra on the mini-app itself: in some cases, the mini-apps has initialized a TerraApp itself for some purposes and want to use for Payment too.

#### Dependencies

```groovy
dependencies {

    implementation "vn.teko.android.payment:payment-kit:1.6.0"

    // for apps that initialize Terra itself
    implementation "vn.teko.terra:terra-core-android:0.8.1"
}
```

#### Usage

**_1. Using default config on Terra_**

```kotlin
class MiniAppActivity : AppCompatActivity() {

    private fun requestPaymentWithDefaultTerra() {
        PaymentKit.payForOrder(
            null,
            paymentRequest = getPaymentRequest(),
            callback = paymentCallbackV2
        )
    }

}
```

**_1. Using default config on Terra_**

```kotlin
class MiniAppActivity : AppCompatActivity() {

    private fun requestPaymentWithSelfInitializedTerra() {
        PaymentKit.payForOrder(
            "<INITIALIZED-TERRA-APP-NAME>",
            paymentRequest = getPaymentRequest(),
            callback = paymentCallbackV2
        )
    }

}
```

Please checkout [MiniAppActivity](./demoMiniApp/demoMiniNativeApp/src/main/java/vn/teko/hestia/android/demomininativeapp/MiniAppActivity.kt) for more details.
