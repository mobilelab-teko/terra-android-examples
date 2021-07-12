# Payment Kit demo

Demonstration for using [PaymentKit](https://terra.dev.teko.vn/developer/docs/payment/v3/paymentKit/overview) in a Mini App and setting up necessary component in the Host app.

## Project setup

### Adding credential for Teko's repository

In `local.properties` file, add lines as belows:

```
TekoPackage.username=<teko-package-username>
TekoPackage.password=<teko-package-token>
```

Notes: Please contact Terra team to get `userName` and `password` to able to sync the project.

## Project structure

### app

The simple Host application for running Mini applications.
Intergrated modules:

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
Intergrated feature:

- **Payment**: request payment from Mini app to Super app
  The Super app will start the Mini app through an entry point. It is the `MiniAppActivity`.

2. `Connector`

The **Connector** is responsible for initializing data and starting the Mini app. To achieve this, the **Connector** must have a public class that inherited `AndroidNativeAppLauncher` interface with one method:

- `create`: initialize intent for starting Mini app activity

## Payment Kit

### Payment directly (self payment flow)

Please refer to [Payment direct flow](https://terra.dev.teko.vn/developer/docs/miniAppIntegration/v0/paymentFlow/directFlow) before.

This section focuses on PaymentKit in `demoMiniApp/demoMiniNativeApp` module. Please check out [`demoMiniApp`](./demoMiniApp) for more details of the connector.

#### Dependencies

```groovy
dependencies {

    // Apollo is required for payment
    implementation "vn.teko.apollo:terra-apollo:1.7.0"
    implementation "vn.teko.terra:terra-core-android:0.7.3"

    implementation "vn.teko.android.payment:payment-kit:1.5.0-alpha2"

}
```

#### Usage

1. Initlizing TerraApp and Apollo in main activity of the mini app:

- Config TerraApp for the mini app on Terra console.
- Download config file.
- Initlize TerraApp in the code.

```kotlin
class MiniAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // init TerraApp for mini app
        val terraApp = TerraApp.initializeApp(application, MINI_APP_APP_NAME)
        TerraApollo.getInstance(terraApp)

        super.onCreate(savedInstanceState)

        //...
    }

    companion object {
        private const val MINI_APP_APP_NAME = "MINI-APP-DEMO"
    }

}
```

2. Calling `PaymentKit.payForOrder` method

```kotlin
class MiniAppActivity : AppCompatActivity() {

    private fun requestPayment() {
        val paymentRequestBuilder =
            PaymentRequestBuilder(
                PaymentV2Request.Order(
                    orderCode = "abc-xyz-6789",
                    orderAmount = 11111,
                    displayOrderCode = "terra-6789"
                )
            )
                .setMainMethod(
                    PaymentV2Request.Payment.MainMethod(
                        PaymentV2Request.Payment.Method.All,
                        11111
                    )
                )

        PaymentKit.payForOrder(
            MINI_APP_APP_NAME,
            paymentRequest = paymentRequestBuilder.build(),
            callback = paymentCallbackV2
        )
    }

}
```

Please checkout [MiniAppActivity](./demoMiniApp/demoMiniNativeApp/src/main/java/vn/teko/hestia/android/demomininativeapp/MiniAppActivity.kt) for more details.

### Host app payment flow

Please refer to [Host app payment flow](https://terra.dev.teko.vn/developer/docs/miniAppIntegration/v0/paymentFlow/hostAppFlow) before.

This section focuses on PaymentKit in `demoMiniApp-02/demoMiniNativeApp` module. Please check out [`demoMiniApp-02`](./demoMiniApp) for more details of the connector.

#### Dependencies

```groovy
dependencies {

    implementation "vn.teko.android.payment:payment-kit:1.5.0-alpha2"

}
```

#### Usage

In the host app payment flow, we don't need to create a TerraApp for the mini app

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
