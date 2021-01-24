package com.devh.vitstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VitstoreApplication

fun main(args: Array<String>) {
    runApplication<VitstoreApplication>(*args)
}
// https://springframework.guru/configuring-spring-boot-for-mariadb/
