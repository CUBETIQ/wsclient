package com.cubetiqs.wsclient

import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.kotlin.extra.retry.retryExponentialBackoff
import java.time.Duration

class WebClientStockClient(val webClient: WebClient) : StockClient {
    override fun priceFor(symbol: String): Flux<StockPrice> {
        return webClient.get()
                .uri("http://localhost:8080/stocks/{symbol}", symbol)
                .retrieve()
                .bodyToFlux(StockPrice::class.java)
                .retryExponentialBackoff(5, Duration.ofSeconds(10), Duration.ofSeconds(20))
                .doOnError {
                    println(it.message)
                }
    }
}