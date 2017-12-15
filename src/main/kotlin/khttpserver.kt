import com.sun.net.httpserver.HttpServer
import handlers.RequestHandler
import java.net.InetSocketAddress

fun main(args : Array<String>) {
    val server = HttpServer.create(InetSocketAddress(8080), 0)
    server.createContext("/", RequestHandler())
    server.start()
}