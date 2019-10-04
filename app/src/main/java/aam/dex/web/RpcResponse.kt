package aam.dex.web

class RpcResponse<T>(
    val id: Int,
    val jsonrpc: String,
    val result: T
)