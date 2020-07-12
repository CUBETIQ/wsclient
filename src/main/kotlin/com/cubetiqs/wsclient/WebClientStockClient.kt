package com.cubetiqs.wsclient

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

class WebClientStockClient(private val webClient: WebClient) : StockClient {
    override fun priceFor(symbol: String): Flux<StockPrice> {
        return webClient.get()
                .uri("http://localhost:8080/stocks/{symbol}", symbol)
                .retrieve()
                .bodyToFlux(StockPrice::class.java)
                .doOnError {
                    println(it.message)
                }
    }
}