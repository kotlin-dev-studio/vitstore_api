package com.devh.vitstore.service.product

import com.devh.vitstore.common.model.ResultListRes
import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.product.ProductDao
import com.devh.vitstore.model.product.ProductDto
import com.devh.vitstore.repository.ProductRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class ProductService(
    private var productRepository: ProductRepository
) {
    fun listAllProductByPage(pageNo: Int, pageSize: Int): ResultListRes<ProductDao?> {
        val paginate: Pageable = PageRequest.of(pageNo, pageSize)
        val pageProducts: Page<ProductDao?> = productRepository.findAll(paginate)
        // TODO: tupm Refactor only arg `ResultListRes(paginate, pageProducts)`
        val result = pageProducts.content
        val totalItem = pageProducts.totalElements
        val currentPage = pageProducts.number
        val totalPages = pageProducts.totalPages
        val hasNext = pageProducts.hasNext()
        return ResultListRes(result, totalItem, currentPage, totalPages, hasNext)
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
