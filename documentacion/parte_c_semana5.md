# Parte C - Semana 5

## ¿Por qué el ViewModel depende de la interfaz ReporteRepository?

El ViewModel depende de la interfaz `ReporteRepository` y no directamente de una implementación concreta porque esto permite separar la lógica de negocio de la forma en que se almacenan los datos.

Gracias a esta separación, el ViewModel puede trabajar con diferentes implementaciones del repositorio sin necesidad de modificar su código. Por ejemplo, actualmente se utiliza `MemoriaReporteRepository`, pero en el futuro podría reemplazarse por una base de datos, una API u otra fuente de datos.

Además, esta estructura facilita las pruebas, ya que se puede utilizar una implementación de prueba del repositorio para verificar el comportamiento del ViewModel de manera independiente.