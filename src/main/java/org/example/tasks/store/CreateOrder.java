
package org.example.tasks.store;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateOrder implements Task {

    public static Performable newOrder() {
        return instrumented(CreateOrder.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        long orderId = ThreadLocalRandom.current()
                .nextLong(1, 11);

        Map<String, Object> order = Map.of(
                "id", orderId,
                "petId", 1,
                "quantity", 1,
                "shipDate", Instant.now().toString(),
                "status", "placed",
                "complete", false
        );

        actor.attemptsTo(
                Post.to("/store/order")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(order)
                        )
        );

        actor.remember("orderId", orderId);
    }
}
