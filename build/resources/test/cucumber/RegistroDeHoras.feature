Feature: Registro de horas a tareas

  Scenario: Exitosamente se registran horas en una tarea
  Given una tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973"
  When se intentan cargar 5 horas en la tarea
  Then las horas totales de la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" son 5
  And las horas cargadas son 5

  Scenario: Exitosamente se modifican las horas de una tarea
  Given una tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" y 5 horas registradas en el dia "2024-11-16"
  When intento modificar a 7 las horas registradas de la tarea
  Then las horas totales de la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" son 7
  And las horas cargadas son 7

  Scenario: No se puede cargar horas negativas en una tarea
  Given una tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973"
  When se intentan cargar -5 horas en la tarea
  Then la acción debería fallar debido a que "Las horas a cargar no pueden ser negativas"