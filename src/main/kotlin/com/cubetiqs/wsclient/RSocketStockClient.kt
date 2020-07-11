package com.cubetiqs.wsclient

import org.springframework.messaging.rsocket.RSocketRequester
import reactor.core.publisher.Flux

class RSocketStockClient(
        private val requester: RSocketRequester
) : StockClient {
    override fun priceFor(symbol: String): Flux<StockPrice> {
        return requester.route("stockPrices")
                .data(symbol)
                .retrieveFlux(StockPrice::class.java)
    }
}