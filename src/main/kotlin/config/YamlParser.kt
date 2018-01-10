package config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Files
import java.nio.file.Paths

class YamlParser {
    fun parse() : Configuration {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())

        return Files.newBufferedReader(Paths.get(Thread.currentThread().contextClassLoader.getResource("khttpserver.yaml").path))
                .use { mapper.readValue(it, Configuration::class.java) }
    }
}