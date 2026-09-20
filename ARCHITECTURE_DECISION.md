# Architectural Decision Record (ADR)

## ADR-001: Adopción de Spring MVC y Concurrencia Asíncrona (Java / Spring Boot)

### 📅 Fecha

2026-09-18

### 📌 Estado

**Aceptado**

---

### 📖 Contexto

La prueba técnica requiere exponer un endpoint REST (`GET /product/{productId}/similar`) en el puerto 5000 que orqueste la consulta de productos similares y el detalle de cada uno consumiendo APIs mock existentes. Los criterios de evaluación principales son:

- Claridad y mantenibilidad del código
- Rendimiento (Performance)
- Resiliencia (Tolerancia a fallos)

Se evaluaron dos enfoques arquitectónicos principales:

1. **Spring WebFlux (Programación Reactiva con Project Reactor: `Mono` / `Flux`)**
2. **Spring MVC + Ejecución Concurrente Asíncrona / Virtual Threads (Java 21 Ready)**

---

### 🎯 Decisión

Se decide adoptar **Spring MVC** con una **Arquitectura Hexagonal (Puertos y Adaptadores)** y concurrencia paralela (`CompletableFuture` / `ExecutorService` optimizado, compatible y listo para **Virtual Threads** de Java 21 con `spring.threads.virtual.enabled=true`).

---

### 💡 Justificación y Razón de la Decisión

1. **Naturaleza del Caso de Uso (Peticiones HTTP Request/Response tradicionales)**:
   - La aplicación actúa como un servicio agregador HTTP REST estándar.
   - No existe necesidad de _Streaming_ continuo en tiempo real, _Server-Sent Events (SSE)_, ni conexiones bidireccionales persistentes por _WebSockets_.
   - No se requiere una gestión compleja de contrapresión (_Backpressure_) entre capas.

2. **Claridad, Mantenibilidad y Trazas de Error (Stack Traces)**:
   - En **Spring WebFlux**, los flujos asíncronos y operadores reactivos (`Mono`, `Flux`, `flatMap`, `zip`) generan trazas de error (_stack traces_) fragmentadas y difíciles de seguir en los logs de producción.
   - Con **Spring MVC**, el código sigue un flujo imperativo natural, lineal y legible, permitiendo depuración directa con _breakpoints_ y trazas de error limpias e inequívocas línea a línea.

3. **Rendimiento y Escalabilidad sin Complejidad Reactiva**:
   - La concurrencia entre las llamadas a los productos similares se gestiona en paralelo de forma asíncrona preservando el orden de similitud.
   - Al ejecutarse con pools dedicados y compatibilidad con Virtual Threads (Project Loom en Java 21), se obtiene la misma capacidad de respuesta y bajo consumo de recursos que el paradigma reactivo, sin introducir su curva de aprendizaje.

4. **Arquitectura Hexagonal (Ports & Adapters)**:
   - El dominio (`ProductDetail`) y los casos de uso (`GetSimilarProductsUseCase`) se mantienen 100% aislados de detalles de infraestructura y frameworks, permitiendo cambiar el cliente HTTP o el controlador en el futuro sin alterar la lógica de negocio.

---

### ⚖️ Consecuencias

#### Positivas:

- Código altamente legible, mantenible y testeable sin dependencia de librerías reactivas.
- Stack traces y logs limpios en caso de excepciones o timeouts.
- Consultas a productos similares ejecutadas en paralelo con tolerancia a fallos individuales.
- Transición transparente a Virtual Threads de Java 21 activando `spring.threads.virtual.enabled=true`.

#### Negativas / Mitigaciones:

- Para casos futuros de WebSockets bidireccionales, se requeriría evaluar WebFlux o WebSockets dedicados sobre Spring MVC.
