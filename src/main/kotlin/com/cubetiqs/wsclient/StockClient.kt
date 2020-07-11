package com.cubetiqs.wsclient

import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
interface StockClient {
    fun priceFor(symbol: String): Flux<StockPrice>
}

@Service
class RSocketStockClient (
        private val requesterBuilder: RSocketRequester.Builder
) : StockClient {
    private fun createRSocketRequester(): RSocketRequester {
        return requesterBuilder.connectTcp("localhost", 7000).block() ?: throw Exception("requester not loaded")
    }

    override fun priceFor(symbol: String): Flux<StockPrice> {
        return createRSocketRequester().route("stockPrices")
                .data(symbol)
                .retrieveFlux(StockPrice::class.java)
    }
}