package com.devh.vitstore.repository

import com.devh.vitstore.model.dao.TokenDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<TokenDao?, Long>
