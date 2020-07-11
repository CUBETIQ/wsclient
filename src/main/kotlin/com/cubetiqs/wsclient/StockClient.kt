package com.cubetiqs.wsclient

import reactor.core.publisher.Flux

interface StockClient {
    fun priceFor(symbol: String): Flux<StockPrice>
}