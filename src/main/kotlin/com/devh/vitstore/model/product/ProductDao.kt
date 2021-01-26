package com.devh.vitstore.model.product

import com.devh.vitstore.common.model.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "products")
data class ProductDao(
    @NotBlank
    @Column
    var productName: String? = "",

    @NotNull
    @Column
    var price: Long? = 0L,

    @Column
    var description: String? = "",

    @Column
    var imageUrl: String? = "",

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity<Long>()
