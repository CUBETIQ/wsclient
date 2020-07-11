package com.cubetiqs.wsclient

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfig {
    @Bean
    @ConditionalOnMissingBean
    fun webClient(): WebClient {
        return WebClient.builder().build()
    }

    @Bean
    fun webClientStockClient(webClient: WebClient): WebClientStockClient {
        return WebClientStockClient(webClient)
    }

    @Bean
    fun stockClient(requester: RSocketRequester): RSocketStockClient {
        return RSocketStockClient(requester)
    }

    @Bean
    fun createRequester(builder: RSocketRequester.Builder): RSocketRequester {
        return builder.connectTcp("localhost", 7000).block() ?: throw Exception("rsocket requester not loaded")
    }
}