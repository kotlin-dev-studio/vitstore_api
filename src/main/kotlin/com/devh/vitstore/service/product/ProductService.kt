package com.devh.vitstore.service.product

import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.product.ProductDao
import com.devh.vitstore.model.product.ProductDto
import com.devh.vitstore.repository.ProductRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(
    private var productRepository: ProductRepository
) {
    fun findOneById(id: Long): ProductDao {
        return productRepository.findByIdOrNull(id) ?: throw NotFoundException("Product not found with id: $id")
    }

    fun create(product: ProductDto): ProductDao {
        val newProduct = ProductDao().apply {
            productName = product.productName
            price = product.price
            description = product.description
            imageUrl = product.imageUrl
        }
        return productRepository.save(newProduct)
    }

    fun update(id: Long, product: ProductDto): ProductDao {
        val updateProduct: ProductDao = productRepository.findByIdOrNull(id)
            ?: throw NotFoundException("Product not found with id: $id")
        updateProduct.apply {
            productName = product.productName
            price = product.price
            description = product.description
            imageUrl = product.imageUrl
            updatedAt = LocalDateTime.now()
        }
        return productRepository.save(updateProduct)
    }

    fun delete(id: Long): Any {
        val product: ProductDao = productRepository.findByIdOrNull(id)
            ?: throw NotFoundException("Product not found with id: $id")
        // Throws: IllegalArgumentException – in case the given entity is null.
        productRepository.delete(product)
        return ResultRes.success("Deleted product with id: $id")
    }
}
