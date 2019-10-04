package aam.dex.web

import aam.dex.web.RpcCall
import aam.dex.web.RpcResponse

public class RpcCallback(
    val onResponse: (rpcCall: RpcCall, rpcResponse: RpcResponse<*>) -> Unit,
    val onFailure: (rpcCall: RpcCall, exception: Throwable) -> Unit
)