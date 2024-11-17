Feature: Consultas de horas cargadas a tareas

  Scenario: Exitosamente se consultan las tareas trabajadas en una semana
    Given 5 horas cargadas en la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" en la fecha "2024-11-11" y con 4 horas cargadas en la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" en la fecha "2024-11-13"
    When se intenta consultar las tareas asignadas en la semana de la fecha "2024-11-11" del empleado con id "ff14a491-e26d-4092-86ea-d76f20c165d1"
    Then la cantidad de horas cargadas a la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" es 9
    And la cantidad total trabajada por el empleado "ff14a491-e26d-4092-86ea-d76f20c165d1" en la semana del "2024-11-11" es 9

  Scenario: No se puede consultar tareas de un empleado inexistente
    Given 5 horas cargadas en la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" en la fecha "2024-11-11" y con 4 horas cargadas en la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" en la fecha "2024-11-13"
    When se intenta consultar las tareas asignadas en la semana de la fecha "2024-11-11" del empleado con id "id-invalido"
    Then la busqueda debería fallar debido a que "No se encontró el recurso con ID: id-invalido"
