package com.devh.vitstore.controller.api.v1.product

import com.devh.vitstore.common.dto.ResultDataRes
import com.devh.vitstore.model.dao.ProductDao
import com.devh.vitstore.model.dto.ProductDto
import com.devh.vitstore.service.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("/{id}")
    fun findOneById(@PathVariable("id") id: Long): ResponseEntity<ResultDataRes<ProductDao>> {
        val product = productService.findOneById(id)
        return ResponseEntity.ok(ResultDataRes.Success(product))
    }

    @PostMapping
    fun createProduct(@Valid @RequestBody product: ProductDto): ResponseEntity<ResultDataRes<Any>> {
        return ResponseEntity.ok(ResultDataRes.Success(productService.create(product)))
    }

    @PostMapping("/{id}")
    fun updateProduct(
        @PathVariable("id") id: Long,
        @Valid @RequestBody product: ProductDto
    ): ResponseEntity<ResultDataRes<ProductDao>> {
        return ResponseEntity.ok(ResultDataRes.Success(productService.update(id, product)))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") id: Long): ResponseEntity<ResultDataRes<Any>> {
        return ResponseEntity.ok(ResultDataRes.Success(productService.delete(id)))
    }
}
