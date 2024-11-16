Feature: Registro de horas a tareas

  Scenario: Exitosamente se registran horas en una tarea
  Given una tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973"
  When se intentan cargar 5 horas en la tarea
  Then las horas totales de la tarea con id "f635b4ca-c091-472c-8b5a-cb3086d1973" son 5
  And las horas del dia actual son 5