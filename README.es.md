# Trianafy: artists, songs and playlists

[English](README.md) · [GitHub](https://github.com/navalcode)

## Resumen

API REST formativa revisada con **16 pruebas locales**: el contexto original y 15 recorridos HTTP/JPA. Artistas, canciones y listas conservan sus rutas y formatos de éxito originales.

Artistas, canciones, listas y pertenencias ausentes devuelven 404. Añadir dos veces una canción deja una única pertenencia; retirarla de una lista conserva la canción. Eliminar un artista desvincula sus canciones, incluso si otras ya no tienen artista. Eliminar una canción retira sus enlaces de todas las listas. Las operaciones comparten transacción; las pruebas recargan los repositorios después de las peticiones sin envolver el test en una transacción.

Crear/editar una canción rechaza artistas desconocidos antes de cambiar datos. POST de artista/lista con ID devuelve 400 y evita sobrescribir registros. Las colecciones vacías mantienen el 404 original.

| Acción | Resultado |
|---|---|
| POST `/artist/` y después POST `/song/` con su `artistId` | 201 e IDs generados |
| POST `/lists` y POST `/lists/{listId}/songs/{songId}` | Lista 201, pertenencia 200; repetir conserva una canción |
| GET o DELETE `/list/{listId}/songs/{songId}` | 200/204 si pertenece, 404 si falta |
| DELETE `/artist/{id}` o `/song/{id}` | 204 con asociaciones retiradas; 404 si falta |

Empieza por [los recorridos HTTP](trianafy/src/test/java/com/salesianostriana/dam/trianafy/MusicHttpJourneyTest.java) y después los tres controladores. Las notas y créditos compartidos originales siguen en el README inglés. Es una API pequeña de aprendizaje sin autenticación: recorre colecciones, no incluye paginación, streaming ni un servicio musical de producción. Conserva sus versiones históricas de Maven/Spring; no se ha añadido CI alojada.

## Ejecutar

Un arranque independiente del JAR en loopback supera 11 peticiones HTTP reales con su configuración en memoria por defecto y se cierra correctamente.

Requiere JDK 17. Desde la raíz del repositorio:

```sh
cd trianafy
sh ./mvnw verify
java -jar target/trianafy-0.0.1-SNAPSHOT.jar --server.port=0
```

Los valores por defecto usan H2 en memoria, consola H2 desactivada y dirección loopback. La base desaparece al terminar; no se abre el fichero DB original. Tomcat imprime el puerto asignado por el sistema.

Resultados locales con JDK 17/macOS del 29 de octubre de 2026 en [verification.json](docs/verification.json). No equivalen a un servicio de producción. [Decisiones de diseño y contrato](DESIGN.md) · [Componentes y atribución](THIRD_PARTY.md).
