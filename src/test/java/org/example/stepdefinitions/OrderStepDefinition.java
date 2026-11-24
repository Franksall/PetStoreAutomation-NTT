
package org.example.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;

import org.example.tasks.store.CreateOrder;
import org.example.tasks.store.GetOrder;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.junit.Assert.assertEquals;

public class OrderStepDefinition {

    @When("el {actor} registra un pedido de mascota")
    public void registrarPedido(Actor actor) {
        actor.attemptsTo(
                CreateOrder.newOrder()
        );
    }

    @Then("el pedido registrado debe tener estado {string}")
    public void validarPedido(String estadoEsperado) {

        String estadoObtenido = SerenityRest.lastResponse()
                .jsonPath().getString("status");

        Long idObtenido = SerenityRest.lastResponse()
                .jsonPath().getLong("id");

        Long idEsperado = theActorInTheSpotlight()
                .recall("orderId");

        assertEquals("El ID del pedido no coincide",
                idEsperado, idObtenido);

        assertEquals("El estado del pedido no coincide",
                estadoEsperado, estadoObtenido);
    }

    @When("el {actor} registra y consulta un pedido")
    public void registrarYConsultarPedido(Actor actor) {

        actor.attemptsTo(
                CreateOrder.newOrder()
        );

        assertEquals(200, SerenityRest.lastResponse().statusCode());

        Long orderId = actor.recall("orderId");

        actor.attemptsTo(
                GetOrder.withId(orderId)
        );
    }

    @Then("el pedido consultado debe tener estado {string}")
    public void validarPedidoConsultado(String estadoEsperado) {

        String estadoObtenido = SerenityRest.lastResponse()
                .jsonPath().getString("status");

        Long idObtenido = SerenityRest.lastResponse()
                .jsonPath().getLong("id");

        Long idEsperado = theActorInTheSpotlight().recall("orderId");

        assertEquals("El ID del pedido no coincide",
                idEsperado, idObtenido);

        assertEquals("El estado del pedido no coincide",
                estadoEsperado, estadoObtenido);
    }
    @When("el {actor} consulta un pedido inexistente con id {long}")
    public void consultarPedidoInexistente(Actor actor, Long orderId) {

        actor.attemptsTo(
                GetOrder.withId(orderId)
        );
    }


}
