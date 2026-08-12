package com.comprago.payments.repository

import com.comprago.payments.model.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository : JpaRepository<Subscription, Long> {
    fun findBySellerId(sellerId: Long): Subscription?
}
