# PSA (Praxis Systems Argentina)

<span style="color:green;">
    <h2>Introducción</h2>
</span>

Praxis Systems Argentina (PSA) es una compañía de desarrollo y venta de software empresarial con base en Buenos Aires y clientes en Sudamérica de habla hispana. Con más de quince años en el mercado, una facturación anual cercana a los u$s 20 M, 450 empleados y oficinas en Buenos Aires, Santiago de Chile y Lima, PSA se ha consolidado como un proveedor competitivo en el segmento de productos ERP, CRM y BI para empresas medianas y medianas/grandes.

Debido a la pérdida de algunos clientes, nuevas oportunidades de negocio, la situación financiera mundial, acciones agresivas de la competencia y la amenaza de nuevos jugadores, PSA se ha embarcado en un plan para mejorar su oferta de productos, reducir costos operativos y obtener nuevos clientes. Hace 12 meses, el management decidió migrar su producto más importante, PSA Spring ERP, a una nueva plataforma en la nube, dejando atrás una arquitectura complicada basada en HTML, Visual C++ y Visual Basic. Este proyecto sufrió múltiples demoras debido a la necesidad de atender otros proyectos relacionados con el ERP y mantener los otros dos productos de la compañía: PSA Spring CRM y PSA Business Analytics.

La compañía está dirigida por <strong>Juan Zeo</strong>, socio fundador y accionista mayoritario, y se organiza en cuatro gerencias: Operaciones, Marketing, Ventas y Administración y Finanzas.

<span style="color:white">
    <h3 style="text-decoration:underline;">Gerencia de Operaciones</h3>
</span>


A cargo de <strong>Fernando Soluzzia</strong>, está organizada en cuatro grupos: desarrollo de productos, implementaciones, soporte a clientes e infraestructura tecnológica.

- **Desarrollo de productos**: Responsable de la evolución de los tres productos de la compañía y del desarrollo de la nueva generación del ERP, PSA Cloud Spring ERP. Produce tres releases al año para cada uno de los tres productos originales.
- **Implementaciones**: Encargada de instalar, parametrizar y customizar el producto. Supervisada por un líder y un equipo de consultores y desarrolladores.
- **Soporte a clientes**: Responsable de atender consultas de los usuarios y supervisar la resolución de incidentes.
- **Infraestructura tecnológica**: Administra la infraestructura de hardware y software de la empresa y participa en proyectos de implementación evaluando la infraestructura existente o dimensionando la nueva.

<span style="color:white">
    <h3 style="text-decoration:underline;">Gerencia de Marketing</h3>
</span>

A cargo de <strong>José Mercado</strong>, esta área es responsable de analizar las tendencias del mercado y de proveer una visión de muy alto nivel de la
funcionalidad a incluir en cada release. También tiene a
su cargo la publicidad en medios especializados, la
relación con clientes y proveedores estratégicos y la
participación de la empresa en ferias y congresos.

<span style="color:white">
    <h3 style="text-decoration:underline;">Gerencia de Ventas</h3>
</span>

A cargo de <strong>Juan Anvizzio</strong>, esta área tiene a cargo todas
las actividades relacionadas con la comercialización de
los productos de la compañía. Además de su gerente, el
área cuenta con ocho vendedores senior.

<span style="color:white">
    <h3 style="text-decoration:underline;">Gerencia de Administración y Finanzas</h3>
</span>

Supervisada por <strong>Roberto Ratio</strong>, esta área es
responsable de las actividades de facturación, compras,
impuestos, tesorería, sueldos y planeamiento
financiero. Además de Roberto, integran el sector tres
contador y varios administrativos.


<span style="color:green">
    <h2>Gestión y organización de proyecto</h2>
</span>

En PSA, la Gerencia de Operaciones gestiona las carteras de proyectos y recursos. Los equipos se organizan en Tribus y Squads. Un squad tiene entre 4 y 7 personas, y una tribu incluye de 2 a 4 squads. Las tribus se asignan a proyectos, y los gestores deciden la cantidad y tamaño de los squads según las necesidades. Los proyectos de desarrollo también tienen un Program Manager (PM) y un Product Owner (PO). El PM gestiona el ciclo de vida del proyecto, mientras que el PO orienta sobre el alcance funcional.

<span style="color:green">
    <h2>Gestión de las comunicaciones en los proyectos</h2>
</span>

El principal medio de comunicación interna utilizado en PSA es Slack. Allí se utilizan canales de comunicación tanto públicos como privados según las necesidades. Generalmente, para las comunicaciones de los proyectos existen canales privados a nivel tribus y squads. Los anuncios por parte del management de PSA se realizan en canales públicos y por lo general se utilizan como canales de una sola vía de comunicación, o sea, solo el management realiza publicaciones en estos canales.

La herramienta de gestión del alcance de los proyectos de desarrollo es Jira. Allí se vuelca la información asociada a la especificación de requisitos, el alcance de las iteraciones, las estimaciones y esfuerzo real y toda información asociada al proyecto en lo que respecta su alcance. Los squads utilizan Github Projects, Trello o Jira como herramienta principal para autoorganización de sus tareas dentro de las iteraciones de los proyectos de desarrollo. Se utiliza el concepto de tablero Kanban para distinguir entre tareas pendientes, en desarrollo y finalizadas por cada iteración.

<span style="color:green">
    <h2>Información técnica de los proyectos</h2>
</span>

Desde la gerencia de operaciones, en acuerdo con el management de PSA, estableció las preferencias respecto a las tecnologías, arquitectura y herramientas de los nuevos proyectos.

<span style="color:green">
    <h2>Arquitectura</h2>
</span>

Desde hace varios años, el estándar del mercado se inclinó por productos y servicios basados en la nube. Por ello, la arquitectura web será la base en los nuevos proyectos de PSA.

La estructura del contenido y las funcionalidades de los productos de PSA está regida por módulos temáticos asociados a las necesidades cubiertas por el producto. Por ejemplo, el PSA Spring CRM contiene los módulos de Clientes, Cuentas, Contactos, Oportunidades, Ventas y Marketing.

Desde el punto de vista técnico, se prefiere la arquitectura basada en microservicios. Se propone que dicha arquitectura implemente su interfaz de comunicación bajo el protocolo REST (Transferencia de Estado Representacional).

Gracias a esta arquitectura orientada a servicios, todos los productos de PSA pueden intercambiar información entre sí mediante una API (Interfaz de programación de aplicaciones).

Los lenguajes de programación pueden variar según necesidades, pero se prefiere Java y Python. Las tecnologías y librerías utilizadas son las de mayor respaldo y aplicación en la industria, como React.js, SpringMVC, Springboot, Swagger, Django, y Flask. Siempre existe la posibilidad de incorporar nuevas tecnologías y herramientas según cada proyecto.