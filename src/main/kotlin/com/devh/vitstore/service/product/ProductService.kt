package com.devh.vitstore.service.product

import com.devh.vitstore.model.product.ProductDao
import com.devh.vitstore.model.product.ProductDto
import com.devh.vitstore.repository.ProductRepository
import javassist.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(
    private var productRepository: ProductRepository
) {
    fun listAllProduct(): List<ProductDao?> {
        return productRepository.findAll()
    }

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
}
