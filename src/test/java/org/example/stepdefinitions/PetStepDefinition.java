
package org.example.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import org.example.tasks.CreatePet;
import org.example.tasks.GetPet;
import org.example.questions.ResponseCode;
import org.example.tasks.UpdatePet;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class PetStepDefinition {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de PetStore")
    public void establecerEndpoint(Actor actor) {
        actor.whoCan(
                CallAnApi.at("https://petstore.swagger.io/v2")
        );
    }

    @When("el {actor} consulta una mascota con id {long}")
    public void consultarMascota(Actor actor, Long petId) {
        actor.attemptsTo(
                GetPet.withId(petId)
        );
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void validarCodigoRespuesta(int codigoEsperado) {
        theActorInTheSpotlight().should(
                seeThat(
                        "El codigo HTTP de respuesta",
                        ResponseCode.getStatus(),
                        equalTo(codigoEsperado)
                )
        );
    }

    @When("el {actor} registra una mascota llamada {string} con estado {string}")
    public void registrarMascota(Actor actor, String nombre, String estado) {
        actor.attemptsTo(
                CreatePet.withData(nombre, estado)
        );
    }

    @Then("el nombre de la mascota creada debe ser {string}")
    public void validarNombreMascota(String nombreEsperado) {
        String nombreObtenido =
                SerenityRest
                        .lastResponse()
                        .jsonPath()
                        .getString("name");

        assertEquals(
                "El nombre de la mascota no coincide",
                nombreEsperado,
                nombreObtenido
        );
    }

    @When("el {actor} registra y consulta una mascota llamada {string} con estado {string}")
    public void registrarYConsultarMascota(
            Actor actor, String nombre, String estado) {

        actor.attemptsTo(
                CreatePet.withData(nombre, estado)
        );
        Long petId = actor.recall("petId");
        actor.should(
                seeThat(
                        "La mascota fue creada correctamente",
                        ResponseCode.getStatus(),
                        equalTo(200)
                )
        );
        actor.attemptsTo(
                GetPet.withId(petId)
        );
    }

    @Then("los datos de la mascota consultada deben ser {string} y {string}")
    public void validarDatosMascota(
            String nombreEsperado, String estadoEsperado) {

        String nombreObtenido = SerenityRest.lastResponse().jsonPath().getString("name");

        String estadoObtenido = SerenityRest.lastResponse().jsonPath().getString("status");

        Long idObtenido = SerenityRest.lastResponse().jsonPath().getLong("id");

        Long idEsperado = theActorInTheSpotlight().recall("petId");
        assertEquals(
                "El ID de la mascota no coincide",
                idEsperado, idObtenido
        );
        assertEquals(
                "El nombre de la mascota no coincide",
                nombreEsperado, nombreObtenido
        );
        assertEquals(
                "El estado de la mascota no coincide",
                estadoEsperado, estadoObtenido
        );
    }

    @When("el {actor} crea una mascota llamada {string} y la actualiza a {string} con estado {string}")
    public void crearYActualizarMascota(
            Actor actor,
            String nombreOriginal,
            String nombreNuevo,
            String estadoNuevo) {

        actor.attemptsTo(
                CreatePet.withData(nombreOriginal, "available")
        );

        actor.should(
                seeThat(
                        "La mascota fue creada",
                        ResponseCode.getStatus(),
                        equalTo(200)
                )
        );

        actor.attemptsTo(
                UpdatePet.withData(nombreNuevo, estadoNuevo)
        );
        actor.should(
                seeThat(
                        "La mascota fue actualizada",
                        ResponseCode.getStatus(),
                        equalTo(200)
                )
        );
        Long petId = actor.recall("petId");
        actor.attemptsTo(
                GetPet.withId(petId)
        );
    }


}
