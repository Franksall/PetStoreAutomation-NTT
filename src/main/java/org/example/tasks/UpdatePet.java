
package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdatePet implements Task {

    private final String newName;
    private final String newStatus;

    public UpdatePet(String newName, String newStatus) {
        this.newName = newName;
        this.newStatus = newStatus;
    }

    public static Performable withData(String newName, String newStatus) {
        return instrumented(UpdatePet.class, newName, newStatus);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        Long petId = actor.recall("petId");

        Map<String, Object> pet = Map.of(
                "id", petId,
                "name", newName,
                "photoUrls", List.of("https://example.com/pet.jpg"),
                "status", newStatus
        );

        actor.attemptsTo(
                Put.to("/pet")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(pet)
                        )
        );
    }
}
