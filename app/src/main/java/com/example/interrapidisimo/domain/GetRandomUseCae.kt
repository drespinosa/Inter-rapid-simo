package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.data.model.Provider
import javax.inject.Inject

class GetRandomUseCae @Inject constructor() {

    operator fun invoke(): Model? {
        val quotes = Provider.quotes2
        if (!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}