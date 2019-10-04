package aam.dex.web

import okhttp3.*
import okio.ByteString
import java.io.IOException

class BitsharesService(
    private val okHttpClient: OkHttpClient,
    private val serverProvider: ServerProvider
) {

    private val ID_DIVIDER = 100000

    val callCounter = CyclicCounter(0..ID_DIVIDER)
    val subscriptionCounter = CyclicCounter(ID_DIVIDER..ID_DIVIDER+1000)

    val calls = mutableListOf<Pair<RpcCall, RpcCallback>>()

    private var websocket: WebSocket

    init {
       websocket = createWebsocket()
    }

    fun login() {
        send(
            RpcCall(0, "login", listOf("", "")), RpcCallback(
            { rpcCall: RpcCall, rpcResponse: RpcResponse<*> ->  },
            { rpcCall: RpcCall, exception: Throwable ->  }
        ))
    }

    private fun createWebsocket(): WebSocket {
        val request = Request.Builder()
            .url(serverProvider.provideServer())
            .build()

        return okHttpClient.newWebSocket(request, SocketListener())
    }

    public inline fun <T> Iterable<T>.find(predicate: (T) -> Boolean): T? {
        for (element in this) if (predicate(element)) return element
        return null
    }

    public fun send(rpcCall: RpcCall, rpcCallback: RpcCallback) {
        calls.add(Pair(rpcCall, rpcCallback))
        if(rpcCall.tryCount++ < 5 && !websocket.send(rpcCall.toString())) {
            websocket = createWebsocket()
            send(rpcCall, rpcCallback)
        } else {
            rpcCallback.onFailure(rpcCall, IOException("Can not send data after 5 retries!"))
        }
    }

    private inner class SocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            login()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            calls.forEach {
                it.second.onFailure(it.first, t)
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val rpcResponse = RpcResponse(1, "", "") // from text
            if(rpcResponse.id <= ID_DIVIDER) { // call response
                calls.find { it.first.sequenceId == 1 }?.let {
                    it.second.onResponse(it.first, rpcResponse)
                    calls.remove(it) // optimize
                }
            } else { // subscription response

            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }
    }

}