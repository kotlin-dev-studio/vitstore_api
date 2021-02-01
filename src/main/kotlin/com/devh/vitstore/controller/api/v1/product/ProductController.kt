package com.devh.vitstore.controller.api.v1.product

import com.devh.vitstore.common.annotation.BearerHeaderToken
import com.devh.vitstore.common.model.ResultListRes
import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.product.ProductDao
import com.devh.vitstore.model.product.ProductDto
import com.devh.vitstore.service.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/products")
@BearerHeaderToken
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun listProducts(
        @RequestParam(defaultValue = "0") pageNo: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<ResultListRes<ProductDao?>> {
        val response = productService.listAllProductByPage(pageNo, pageSize)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/testPro")
    fun testPro(): ResponseEntity<ResultRes> = ResponseEntity.ok(ResultRes.success("OK"))

    @GetMapping("/{id}")
    fun findOneById(@PathVariable("id") id: Long): ResponseEntity<ProductDao> {
        return ResponseEntity.ok(productService.findOneById(id))
    }

    @PostMapping
    fun createProduct(@Valid @RequestBody product: ProductDto): ResponseEntity<Any> {
        return ResponseEntity.ok(productService.create(product))
    }

    @PostMapping("/{id}")
    fun updateProduct(
        @PathVariable("id") id: Long,
        @Valid @RequestBody product: ProductDto
    ): ResponseEntity<ProductDao> {
        return ResponseEntity.ok(productService.update(id, product))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(productService.delete(id))
    }
}
