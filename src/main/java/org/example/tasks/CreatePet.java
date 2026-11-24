
package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreatePet implements Task {

    private final String name;
    private final String status;

    public CreatePet(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public static Performable withData(String name, String status) {
        return instrumented(CreatePet.class, name, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        long petId = ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 9_000_000_000L);

        Map<String, Object> pet = Map.of(
                "id", petId,
                "name", name,
                "photoUrls", List.of("https://example.com/pet.jpg"),
                "status", status
        );

        actor.attemptsTo(
                Post.to("/pet")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(pet)
                                .log().all()
                        )
        );

        actor.remember("petId", petId);
    }
}
