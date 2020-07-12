package com.cubetiqs.wsclient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@SpringBootApplication
class WsClientApplication

fun main(args: Array<String>) {
    runApplication<WsClientApplication>(*args)
}

@RestController
class RestController @Autowired constructor(
        private val stockClient: StockClient
) {
    @GetMapping("/{symbol}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStockPrices(@PathVariable symbol: String): Flux<StockPrice> {
        return stockClient.priceFor(symbol)
    }
}