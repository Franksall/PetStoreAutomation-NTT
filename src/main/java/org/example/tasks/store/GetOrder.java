
package org.example.tasks.store;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetOrder implements Task {

    private final Long orderId;

    public GetOrder(Long orderId) {
        this.orderId = orderId;
    }

    public static Performable withId(Long orderId) {
        return instrumented(GetOrder.class, orderId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/store/order/{orderId}")
                        .with(request -> request
                                .pathParam("orderId", orderId)
                                .accept(ContentType.JSON)
                        )
        );
    }
}
