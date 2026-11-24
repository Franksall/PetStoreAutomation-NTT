@PetStore
Feature: Gestion de mascotas en Swagger PetStore

  Como usuario de la API PetStore
  Quiero consultar informacion de las mascotas
  Para verificar que el servicio responde correctamente

  @CP01_ConsultaMascotaInexistente @Negativo
  Scenario: Consultar una mascota que no existe
    Given el actor establece el endpoint de PetStore
    When el actor consulta una mascota con id 999999999
    Then el codigo de respuesta debe ser 404



  @CP02_CrearMascota @Positivo
  Scenario Outline: Registrar mascotas exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor registra una mascota llamada "<nombre>" con estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And el nombre de la mascota creada debe ser "<nombre>"

    Examples:
      | nombre | estado    |
      | Max    | available |
      | Luna   | pending   |
      | Rocky  | sold      |


  @CP03_ConsultarMascota @Positivo
  Scenario: Consultar una mascota registrada exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor registra y consulta una mascota llamada "Luna" con estado "available"
    Then el codigo de respuesta debe ser 200
    And los datos de la mascota consultada deben ser "Luna" y "available"


  @CP04_ActualizarMascota @Positivo
  Scenario Outline: Actualizar los datos de una mascota exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor crea una mascota llamada "<nombreOriginal>" y la actualiza a "<nombreNuevo>" con estado "<estadoNuevo>"
    Then el codigo de respuesta debe ser 200
    And los datos de la mascota consultada deben ser "<nombreNuevo>" y "<estadoNuevo>"

    Examples:
      | nombreOriginal | nombreNuevo      | estadoNuevo |
      | Rocky          | RockyActualizado | sold        |
      | Max            | MaxActualizado   | pending     |


  @CP05_CrearPedido @Positivo
  Scenario: Registrar un pedido de mascota exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor registra un pedido de mascota
    Then el codigo de respuesta debe ser 200
    And el pedido registrado debe tener estado "placed"

  @CP06_ConsultarPedido @Positivo
  Scenario: Consultar un pedido registrado exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor registra y consulta un pedido
    Then el codigo de respuesta debe ser 200
    And el pedido consultado debe tener estado "placed"

  @CP07_PedidoInexistente @Negativo
  Scenario: Consultar un pedido que no existe
    Given el actor establece el endpoint de PetStore
    When el actor consulta un pedido inexistente con id 1
    Then el codigo de respuesta debe ser 404
