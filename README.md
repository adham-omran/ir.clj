# adham-omran/ir

FIXME: my new application.

## Installation

Download from https://github.com/adham-omran/ir

## Usage

Build

    $ clojure -T:build ci

Run that uberjar:

    $ java -jar target/ir-0.1.0-SNAPSHOT.jar

## Options

| Flag | Long Name | Description                   |
|------|-----------|-------------------------------|
| -h   | --help    |                               |
| -g   | --gui     | Launches the experimental GUI |


## Commands

| Command | Description                          |
|---------|--------------------------------------|
| add     | Begins adding mode to insert into db |
| read    | Begins reading mode                  |



## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## Ideas

A protocol defines an Element protocol with two methods
- add, given kind-dependent input (e.g. link for a web element), add the element
  to the db
- read, after querying the db for the next element to _read_, construct a record
  based on the kind of the element and the read method _reads_ the element in an
  appropriate way (e.g. open a web browser with a link, open a text box for a
  text kind)

## Architecture

### Shared

Shared code between CLI and GUI this includes constructing a library that provides

1. DB functionality
   1. Add element to a db
   2. Query for latest element
2. Algorithm calculations
   1. Calculate the next interval

### CLI

### GUI

## Development Phases

### Basic Incremental Reading I

CLI Only

Web links

Open link, options to
1. next link
2. add text extract

### CLI: Interaction with Anki

###

## License

Copyright © 2025 Adham

<!-- Local Variables: -->
<!-- jinx-local-words: "Anki" -->
<!-- End: -->
