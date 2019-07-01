# whichbeatle
Java command-line application that finds the writer/singer/album of any Beatles song using an embedded SQLite database

## Usage
`whichbeatle [options] [song name]`

Options:

  -h, --help: display usage instructions
  
  -w, --wrote: display who wrote the song

  -s, --sang: display who sang the song

  -a, --album: display the albums on which the song appeared

Examples:

`whichbeatle --wrote something` will display who wrote "Something"

`whichbeatle -ws mean mr mustard` will display who wrote and who sang "Mean Mr. Mustard"

## How it works

The program consists of one main Java class and a SQLite database file that contains a table with the names, composers, singers, and album appearances of every song in the Beatles' discography. The program reads in user input, parsing any command-line flags, then executes a query on the embedded database.

## Running whichbeatle

The easiest way to run this program is by downloading whichbeatle.jar and then running it with `java -jar whichbeatle.jar`, followed by any command line arguments.

## Screenshot

<img src="https://user-images.githubusercontent.com/37086292/60407599-6511c900-9b89-11e9-98b8-fc908b38b0df.png" alt="screenshot" height="250px">

## What's next?

I want to get this packaged better for all platforms. For macOS/Linux, this just means using a standalone executable. For Windows, this means making an exe and *maybe* impementing windows-style command flags (e.g. /wrote, /sang).
