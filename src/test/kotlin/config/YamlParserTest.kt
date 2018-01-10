package config

import com.natpryce.hamkrest.anything
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

class YamlParserTest : Spek({
    describe("a yaml parser") {
        val sut = YamlParser()

        given("The khttpserver yaml file exist") {
            it("should return a Configuration object if the khttpserver yaml file exist") {
                val configuration = sut.parse()
                assertThat(configuration, anything)
            }

            it("should return the root value assuming the khttpserver yaml file exist") {
                val configuration = sut.parse()
                assertThat(configuration.root, equalTo("/root"))
            }
        }
    }
})