package local_server

interface JvmAuthServer {
    fun startServerAndWaitForCode(
        onDataReceived: (url: String) -> Unit,
    )
}