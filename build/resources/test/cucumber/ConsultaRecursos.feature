Feature: Ver recursos en proyecto especifico

    Scenario: Exitosamente se obtienen los recursos de un proyecto
        Given un proyecto con id "f635b4ca-c091-472c-8b5a-cb3086d1973"
        When se intentan solicitar los recursos del proyecto
        Then se obtienen los recursos del proyecto

    Scenario: No se obtienen recursos de un proyecto inexistente
        Given un proyecto con id "f635b4ca-c091-472c-8b5a-cb3086d1973"
        When se intentan solicitar los recursos del proyecto
        Then la acción debería fallar debido a que el proyecto con id "f635b4ca-c091-472c-8b5a-cb3086d1974" no existe