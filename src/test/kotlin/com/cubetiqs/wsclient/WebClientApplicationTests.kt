package com.cubetiqs.wsclient

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@SpringBootTest
class WebClientApplicationTests @Autowired constructor(
        private val stockClient: WebClientStockClient
) {
    @Test
    fun shouldRetrieveStockPricesFromTheService() {
        val prices: Flux<StockPrice> = stockClient.priceFor("USD")

        Assertions.assertNotNull(prices)
        val fivePrices = prices.take(5)
        Assertions.assertEquals(5, fivePrices.count().block())
        Assertions.assertEquals("USD", fivePrices.blockFirst()?.symbol)

        StepVerifier.create(prices.take(2))
                .expectNextMatches { it.symbol == "USD" }
                .expectNextMatches { it.symbol == "USD" }
                .verifyComplete()
    }
}
