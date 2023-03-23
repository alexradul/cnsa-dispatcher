package com.polarbookshop.dispatcher;

import com.polarbookshop.dispatcher.events.OrderAcceptedMessage;
import com.polarbookshop.dispatcher.events.OrderDispatchedMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;


@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTests {
    public static final String COMPOSITE_FUNCTION_NAME = "pack|label";
    @Autowired
    private FunctionCatalog catalog;

    @Test
    @Disabled
    void packAndLabelOrder() {
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel =
                catalog
                        .lookup(Function.class, COMPOSITE_FUNCTION_NAME);
        long orderId = 121;

        StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
                .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();
    }
}