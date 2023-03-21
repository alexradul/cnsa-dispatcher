package com.polarbookshop.dispatcher;

import com.polarbookshop.dispatcher.events.OrderAcceptedMessage;
import com.polarbookshop.dispatcher.events.OrderDispatchedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@Configuration
public class DispatchingFunctions {

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return orderAcceptedMessage -> {
            log.info("The order {} is packed", orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(orderId -> {
            log.info("The order {} is labeled", orderId);
            return new OrderDispatchedMessage(orderId);
        });
    }
}
