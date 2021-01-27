package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.common.annotation.BearerHeaderToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable
import java.time.LocalDateTime

// TODO: huorlk-0450 This controller will remove later

@RestController
@RequestMapping("/api/v1/sample")
class SampleController {
    @GetMapping("/health")
    @BearerHeaderToken
    fun health(): ResponseEntity<Health> = ResponseEntity.ok(Health("Alive at ${LocalDateTime.now()}"))

    class Health(
        var status: String
    ) : Serializable
}
