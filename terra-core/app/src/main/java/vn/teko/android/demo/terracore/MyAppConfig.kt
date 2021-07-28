package vn.teko.android.demo.terracore

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MyAppConfig(
    val demoString: String,
    val demoJson: DemoJson
) {
    companion object {
        fun fromMap(source: Map<String, String>): MyAppConfig {
            return MyAppConfig(
                demoString = source["demoString"]!!,
                demoJson = DemoJson.fromString(source["demoJson"]!!)
            )
        }
    }
}

data class DemoJson(

    @SerializedName("demoNumber")
    val demoNumber: Int,

    @SerializedName("demoBoolean")
    val demoBoolean: Boolean
) {
    companion object {
        fun fromString(source: String): DemoJson {
            return Gson().fromJson(source, DemoJson::class.java)
        }
    }
}