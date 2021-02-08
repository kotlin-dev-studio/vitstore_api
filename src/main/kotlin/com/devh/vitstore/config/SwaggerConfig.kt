package com.devh.vitstore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.HttpAuthenticationScheme
import springfox.documentation.service.SecurityScheme
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableOpenApi
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.OAS_30)
        .produces(HashSet(listOf(MediaType.APPLICATION_JSON_VALUE)))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .securitySchemes(listOf(apiKey()) as List<SecurityScheme>)
        .tags(Tag("ID API", ""))

    private fun apiKey(): HttpAuthenticationScheme {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER
            .name("Bearer")
            .description("Enter JWT Bearer token **_only_**")
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("API Documentation")
            .description("API Description")
            .version("1.0")
            .build()
    }
}
