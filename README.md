# fchallenge

Shiny and breathtaking search engine wrapper

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

```bash
lein ring server
```

Or build a fat jar and run it as a regular Java app:
```bash
lein ring uberjar && java -jar target/fchallenge-0.1.0-SNAPSHOT-standalone.jar
```
After that, you can type the following in your favorite browser:
```
http://localhost:3000/search?query=scala
```
Multiple "query" params are supported, too:
```
http://localhost:3000/search?query=scala&query=java&query=clojure
```
