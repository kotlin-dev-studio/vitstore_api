package com.devh.vitstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class VitstoreApplication {
    @PostConstruct
    fun postConstruct() {
        TimeZone.setDefault(TimeZone.getTimeZone("JST"))
    }
}

fun main(args: Array<String>) {
    runApplication<VitstoreApplication>(*args)
}
// https://springframework.guru/configuring-spring-boot-for-mariadb/
