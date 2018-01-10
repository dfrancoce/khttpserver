package handlers

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.sun.net.httpserver.HttpExchange
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import java.net.URI

class RequestHandlerTest : Spek({
    describe("a request handler") {
        val sut = RequestHandler()

        given("a http request") {
            val httpExchange: HttpExchange = mock()

            afterEachTest {
                reset(httpExchange)
            }

            it("should serve the html file requested") {
                whenever(httpExchange.requestMethod).thenReturn("GET")
                whenever(httpExchange.requestURI).thenReturn(URI("/index.html"))

                sut.handle(httpExchange)
            }

            it("should serve the index.html file in case no file is requested and index.html exists in the path") {
                whenever(httpExchange.requestMethod).thenReturn("GET")
                whenever(httpExchange.requestURI).thenReturn(URI("/"))

                sut.handle(httpExchange)
            }
        }
    }
})