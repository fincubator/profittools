package aam.dex.web

import com.squareup.moshi.*
import java.io.Serializable


class RpcCall(
    private var apiId: Int = 0,
    private val apiMethod: String,
    private val params: List<Serializable?> = emptyList()
) {

    var tryCount = 0
    var sequenceId: Int = 0

    public class Adapter : JsonAdapter<RpcCall>() {

        companion object {
            private const val KEY_ID = "id"
            private const val KEY_METHOD = "method"
            private const val KEY_PARAMS = "params"
            private const val KEY_JSONRPC = "jsonrpc"

            private const val VALUE_DEFAULT_RPC_METHOD = "call"
            private const val VALUE_RPC_VERSION = "2.0"
        }

        override fun fromJson(reader: JsonReader): RpcCall? {
            return null
        }

        override fun toJson(writer: JsonWriter, value: RpcCall?) {
            //{"id":8,"method":"call","params":[2,"list_assets",["FINTEH",1]]}
            if (value != null) {
                writer
                    .beginObject()
                    .name("id")
                    .value(value.sequenceId)
                    .name(KEY_METHOD)
                    .value(VALUE_DEFAULT_RPC_METHOD)
                    .name(KEY_PARAMS)
                    .beginArray()
                    .value(value.apiId)
                    .value(value.apiMethod)
                    .beginArray()
                    .value("FINTEH")
                    .value(1)
                    .endArray()
                    .endArray()
                    .endObject()
            }
        }


    }
}