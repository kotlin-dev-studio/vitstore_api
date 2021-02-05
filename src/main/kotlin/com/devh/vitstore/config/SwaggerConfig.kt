package com.devh.vitstore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*
import springfox.documentation.service.ApiInfo.DEFAULT_CONTACT

@EnableOpenApi
@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .produces(hashSetOf("application/json"))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
        .paths(PathSelectors.any())
        .build()
        .securitySchemes(listOf(apiKey()) as List<SecurityScheme>)

    private fun apiKey(): HttpAuthenticationScheme {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
            .name("Bearer Token")
            .description("Enter JWT Bearer token **_only_**")
            .build()
//        return ApiKey("Bearer", "Authorization", "header")
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("API Documentation")
            .description("API Description")
            .version("1.0")
            .build();
    }
}
