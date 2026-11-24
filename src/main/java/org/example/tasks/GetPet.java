
package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetPet implements Task {

    private final Long petId;

    public GetPet(Long petId) {
        this.petId = petId;
    }

    public static Performable withId(Long petId) {
        return instrumented(GetPet.class, petId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Get.resource("/pet/{petId}")
                        .with(request -> request
                                .pathParam("petId", petId)
                                .accept(ContentType.JSON)
                                .log().all()
                        )
        );

        /*
        System.out.println("===== RESPUESTA DE PETSTORE =====");

        System.out.println("ID CONSULTADO: " + petId);

        System.out.println("STATUS: " +
                SerenityRest.lastResponse().statusCode());


        SerenityRest.lastResponse().body().prettyPrint();*/
    }
}
