# Trianafy: artists, songs and playlists

[Español](README.es.md) · [GitHub](https://github.com/navalcode)

## Overview

A coursework REST API reviewed with **16 local tests**: the original application context and 15 HTTP/JPA journeys. Artists, songs and playlists keep their original routes and successful response shapes.

Missing artists, songs, playlists and memberships return 404. Adding a song twice keeps one membership; removing a membership preserves the song. Deleting an artist detaches its songs, including when other songs have no artist. Deleting a song removes its links from every playlist before deleting it. These operations share a transaction, and tests reload the repositories after requests without wrapping the test in a transaction.

Song creation/editing rejects an unknown artist before changing data. POST artist/playlist requests with an ID return 400 rather than merging over an existing record. Empty collection reads retain the original 404 behavior.

| Action | Result |
|---|---|
| POST `/artist/`, then POST `/song/` with its `artistId` | 201 and generated IDs |
| POST `/lists`, then POST `/lists/{listId}/songs/{songId}` | 201 playlist, 200 membership; repeating keeps one song |
| GET or DELETE `/list/{listId}/songs/{songId}` | 200/204 for a member, 404 when absent |
| DELETE `/artist/{id}` or `/song/{id}` | 204 with associations detached; 404 when missing |

Read [the HTTP journeys](trianafy/src/test/java/com/salesianostriana/dam/trianafy/MusicHttpJourneyTest.java) before the three controllers. Original documentation and shared credits remain below. This remains a small, unauthenticated learning API: collection scans, no pagination, streaming or production music service. Its historical Maven/Spring pins remain; no hosted CI was added.

## Run

A separate loopback run of the packaged JAR passed 11 actual HTTP requests with its default memory configuration, then shut down cleanly.

Requires JDK 17. From the repository root:

```sh
cd trianafy
sh ./mvnw verify
java -jar target/trianafy-0.0.1-SNAPSHOT.jar --server.port=0
```

Defaults use memory-only H2, a disabled H2 console and loopback binding. Data disappears on shutdown; the original file DB is not opened. Tomcat prints the OS-assigned port.

Local JDK 17/macOS results from 29 October 2026 are recorded in [verification.json](docs/verification.json). They do not establish production readiness. [Design and contract decisions](DESIGN.md) · [Components and attribution](THIRD_PARTY.md).

## Resumen

[La versión española completa](README.es.md) cubre los mismos contratos, comandos y límites.

## Original notes

The historical notes below are retained verbatim; the reviewed commands and current behavior above take precedence.

<details>
<summary>Original documentation and credits · Documentación y créditos originales</summary>

# Trianafy

_Trianafy propone una API REST que permitirá generar artistas, canciones y playlist por medio de peticiones http del tipo GET, POST, PUT y DELETE. 
Pudiendo salvar los cambios en una base de datos._

_La aplicación corre bajo el framework Spring empaquetado con maven._

## Entidades

###### Artist
_Esta entidad está compuesta por su id y el nombre del artista_

###### Song
_Esta entidad está compuesta por su id, el titulo de la canción, el nombre del album, año y un Artist, teniendo está última una relación OneToMany con Artist a través de su id._

###### Playlist
_Esta entidad está compuesta por su id, su nombre, una descripción y una lista de canciones, teniendo está última una relación ManyToMany con Song a través de su id_

## Controladores
_Cadda entidad está relacionada con su controlador y su repositorio de tal modo que los endpoints en cada controlador respete la estructura REST_

## Sugerencia de pruebas 📋

_Las pruebas se realizarán por defecto en el puerto designado (localhost:8080/)_

###### Colecciones json

_Para probar todos los endpoints del proyecto sugerimos importar el archivo.json que hay en la raiz del proyecto con postman_

_Por defecto se generan algunos datos para realizar pruebas por medio de un InitData, puede comenzar con las peticiones GET para listar todos los artistas, canciones y playlist.
Esta primera busqueda le servirá para conocer los id de las entidades, de este modo podrá usar las peticiones POST, PUT y DELETE correspondientes a cada entidad._

###### Sugerimos fervientemente prestar atención a los id de las entidades con los métodos GET para poder realizar las peticiones POST,PUT y DELETE correctamente, tal como describimos en el parrafo previo.

## Documentación con swagger
_Para acceder a la documentación ejecute el proyecto maven con el plugin boot-boot:run y en su navegador acceda a esta ruta:_


* **Documentación** - [Documentación](http://localhost:8080/swagger-ui.html)

## Autores ✒️

* **Manuel Naval** - *Proyecto Trianafy* - [navalcode](https://github.com/navalcode)

* **Fran Gallego** - *Proyecto Trianafy* - [Gallegogofra21](https://github.com/Gallegogofra21)

* **Alejandro Ramírez** - *Proyecto Trianafy* - [arramos27](https://github.com/arramos270)



</details>
