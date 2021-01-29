package com.devh.vitstore.model.dao

import com.devh.vitstore.common.dao.BaseEntity
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
    var updatedAt: LocalDateTime? = null
) : BaseEntity<Long>()
