# simple-graph2svg

A single-purpose command-line tool that transforms XML with sealed graph into static SVG for viewing.

Command line call Format : `java -jar simple-graph2svg.jar <{input file name} >{output file name}`

## Non-goals

This tool deliberately does **not**:

- support non-sealed graphs
- validate input content beyond basic structural sanity checks
- perform automatic layout or routing
- provide a graphical user interface
- 
### Input file sample

```xml
<?xml version="1.0" encoding="UTF-8" ?>

<graph>
    <node id="r" color="red" />
    <node id="g" color="green" />
    <node id="b" color="blue" />
    <edge id="r-g" color="yellow" sourceRef="r" targetRef="g" />
    <edge id="g-b" color="cyan" sourceRef="g" targetRef="b" />
    <edge id="b-g" color="magenta" sourceRef="b" targetRef="r" />
</graph>
```
## Design philosophy

This project follows the Unix philosophy:

- do one thing
- do it well
- read from stdin, write to stdout
- compose with other tools

## Assumptions

- Input XML is syntactically and semantically valid
- Graph does not contain isolated parts

## Typical usage

```bash

java -jar simple-graph2svg.jar < graphph.xml > graph.svg \
  && xdg-open graph.svg
```

## When NOT to use this tool
- 
- Input XML is syntactically and semantically valid
- There are not any isolated subgraphs and self-cycling

## Feature requests

Feature requests are welcome **only if** they:

- do not introduce additional operating modes
- do not require interactive state or UI
- keep the tool usable in Unix pipelines

Requests outside this scope will be closed as out-of-scope.

## Stability

The command-line interface and SVG output format are intended to be stable.
Breaking changes will be avoided unless strictly necessary.

This project intentionally prefers composability over feature completeness.

# Web client
Web client is located in directory **/web-ui**. It starts the simple web server on port 8080 (based on Netty).

* Server start: `java -jar simple-graph2svg0web-ui-0.0.1jar`.
* URL of page: `http://localhost:8080/`.

Page contains textarea for source xml, button for submit and image for SVG.

