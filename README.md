# Trianafy

[English](#overview) · [Español](#resumen)

## Overview

Java REST learning project for artists, songs and playlists.

An earlier learning exercise. The source is preserved with its original toolchain; compatibility with current versions has not been revalidated.

## Resumen

Proyecto Java de aprendizaje REST para artistas, canciones y listas.

Ejercicio de una etapa anterior de aprendizaje. Se conserva con sus herramientas originales; no se ha vuelto a validar con versiones actuales.

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

[More of my work · Más proyectos](https://github.com/navalcode)
