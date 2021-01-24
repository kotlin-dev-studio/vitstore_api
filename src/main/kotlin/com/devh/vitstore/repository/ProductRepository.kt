package com.devh.vitstore.repository

import com.devh.vitstore.model.dao.ProductDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductDao?, Long>
