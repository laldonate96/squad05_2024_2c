Feature: Generacion de reporte

    Scenario: Se genera un reporte anual exitosamente
        Given un proyecto con id "f635b4ca-c091-472c-8b5a-cb3086d1973" con 100 horas cargadas en el año 2024
        When se intenta generar un reporte para el año 2024
        Then el reporte debería contener 100 horas cargadas