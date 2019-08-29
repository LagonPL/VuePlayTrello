# VuePlayTrello

## [Wymagania](https://trello.com/b/IhaGTlIQ/wymagania-paw-2018)

## Technologie

* **Front - [Vue.js](https://vuejs.org/)**
* **Back - [Play Framework Java](https://www.playframework.com/)**

## Autorzy

* **[Dawid Sowała](https://github.com/LagonPL)**
* **[Kamil Błażejczuk](https://github.com/habywyzygyzy)**

Projekt był robiony poprzez [LiveShare](https://visualstudio.microsoft.com/pl/services/live-share/), przez co jest tylko jeden autor commit'ów.

## Instalacja
Do działania wymagany jest [sbt](https://www.scala-sbt.org/)


W lini komend w głównym folderze wpisz:
```
npm install
```
W folderze front wpisz:
```
npm install
```
Umieść w folderze głównym pliki sbt i odpal komendą **run**.

Aplikacja powinna uruchomić się domyślnie na porcie **9000**.

<details><summary>Body Request'ów</summary>
<p>
	
```	
{"CardViewModel":
  	{
  	"parentList":1,
	"name": "Card1",
	"row": 1,
	"description":"This is a description"
	}
}
```
```
{"ListViewModel":
  	{
  	"parentBoard":1,
	"name": "List1",
	"column": 1
	}	
}
```
```
{"Board":
  	{
	"Name": "Board1",
	"User": null,
	"listts": null,
	"isPrivate": true
	}
}
```
</p>
</details>