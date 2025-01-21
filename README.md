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

| Flag | Long Name | Description      |
|------|-----------|------------------|
| -h   | --help    |                  |
| -g   | --gui     | Launches the GUI |


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


- I want to get to GUI as soon as possible.  CLI is not appropriate for creating
  extracts, nothing beyond maybe opening the text extract in nano/vi/vim at most
  if at all.

## Architecture

How do does a Record _map_ to a database row?

- All values?

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

Phases and their objectives

### Incremental Reading I

A full workflow for adding links and text extracts to the db.  Creating extracts
from a web link.

- No view of text items

#### Adding

CLI Only

Invoke an add mode with `java -jar ./ir.java add`

- ask user for kind of material
- enter kind
- enter the content of the kind
- create a Record based on the _kind_ of material
- invoke the `add` method on the Record

#### Reading

CLI Only

Web links

Open link in browser, options to
1. next link
2. add text extract

### Incremental Reading II

First GUI Elements

- View text extract
- Edit text extract

### Incremental Reading III

### Incremental Reading IV

### Incremental Reading Long Term

- Open PDF instance with dialogue to add text extracts
- Extract text from PDF if possible
- Highlight text in PDF if possible

PDF + Transformations to not overwrite PDF.

Image extraction from PDF pages

### Interaction with Anki I


## Kinds

### Introduction

A kind is a type of material.

List of kinds and their status

| Kind      | Supported | Not Supported | Planned Support |
|-----------|-----------|---------------|-----------------|
| Text      |           |               | Yes             |
| Web Links |           |               | Yes             |
| PDF       |           |               | Yes             |
| Images    |           |               | Yes             |
| YouTube   |           |               | Undecided       |
| MP4       |           |               | Undecided       |
| MP3       |           |               | Undecided       |

### Text


### Image

An image element in GUI is an image with the ability to write text underneath.

## License

Copyright © 2025 Adham

<!-- Local Variables: -->
<!-- jinx-local-words: "Anki" -->
<!-- End: -->
