package handlers

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import mu.KLogging
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class RequestHandler : HttpHandler {
    companion object: KLogging()

    override fun handle(httpExchange: HttpExchange?) {
        when (httpExchange?.requestMethod) {
            "GET" -> get(httpExchange)
        }
    }

    private fun get(httpExchange: HttpExchange?) {
        serve(httpExchange)
    }

    private fun serve(httpExchange: HttpExchange?) {
        try {
            val fileContent = handleFile(httpExchange)
            writeResponse(httpExchange, fileContent, 200)
        } catch (ex: IOException) {
            logger.error(ex) { "An error occurred trying to get the file: $ex" }
            handleError(httpExchange)
        }
    }

    @Throws(IOException::class)
    private fun handleFile(httpExchange: HttpExchange?) : ByteArray {
        val path = httpExchange?.requestURI?.path
        val fullPath = Paths.get(Paths.get(".").toAbsolutePath().normalize().toString() + path)
        return Files.readAllBytes(fullPath)
    }

    @Throws(IOException::class)
    private fun writeResponse(httpExchange: HttpExchange?, fileContent: ByteArray, responseCode: Int) {
        val headers = httpExchange?.responseHeaders
        headers?.add("Content-Type", "text/html")
        headers?.add("Content-Length", fileContent.size.toString())
        httpExchange?.sendResponseHeaders(responseCode, fileContent.size.toLong())

        val response = httpExchange?.responseBody
        response?.write(fileContent)
        response?.close()
    }

    private fun handleError(httpExchange: HttpExchange?) {
        val errorPage =   Thread.currentThread().contextClassLoader.getResourceAsStream("error404.html")
                .bufferedReader()
                .use { it.readText() }
        writeResponse(httpExchange, errorPage.toByteArray(Charset.defaultCharset()), 404)
    }
}