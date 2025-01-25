# adham-omran/ir

FIXME: my new application.

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

These are for the CLI mode

| Command | Description                          |
|---------|--------------------------------------|
| add     | Begins adding mode to insert into db |
| read    | Begins reading mode                  |

## Ideas

A protocol defines an Element protocol with two methods
- `add`: given kind-dependent input (e.g. link for a web element), add the
  element to the db
- `read`: Construct a record based on the kind of the element and the read
  method _reads_ the element in an appropriate way (e.g. open a web browser with
  a link, open a text box for a text kind).  This will typically occur after
  querying the db for the next element to _read_,


- I want to get to GUI as soon as possible.  CLI is not appropriate for creating
  extracts, nothing beyond maybe opening the text extract in nano/vi/vim at most
  if at all.

## Architecture

How do does a Record _map_ to a database row?

- All values?

### Representation of Information

The domain information is that of Elements that are incrementally read.

The information is stored in a SQLite database.  When pulled to be used
in-memory they are represented as Records, the Record is based on the _kind_ of
the element, these records extend the Element protocol to implement `add` and
`read`.

### Shared

Shared code between CLI and GUI this includes constructing a library that provides

1. DB functionality
   1. Add element to a db
   2. Query for latest element
2. Algorithm calculations
   1. Calculate the next interval

### CLI

### GUI

The GUI is complex and contains many features, the development of the features
is over phases over an undefined amount of time.

#### Display Per Kind

Each kind is viewed and interacted with in a different manner than other kinds,
that's the differentiating factor between kinds.

##### Text

Deceptively simple.  What appears to be just plain text is in fact rich text.

The reason it needs to be rich text is the requirements for:
1. Highlighting the text upon subsequent extractions.
2. Highlighting the text upon cloze deletion.

Features I'll need to implement

1. Obtain selected text
2. Highlight selected text

Candidates for a solution
- [JavaFX/HTMLEditor](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/web/HTMLEditor.html)

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

The idea is that cloze deletion would instantly create and send a card to Anki.

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
<!-- jinx-local-words: "Anki cloze" -->
<!-- End: -->
