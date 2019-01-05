# whichbeatle
Java command-line application that finds the writer/singer/album of any Beatles song using a SQLite database

## Usage
`whichbeatle [options] [song name]`

Options:

  -h, --help: display usage instructions
  
  -w, --wrote: display who wrote the song

  -s, --sang: display who sang the song

  -a, --album: display the album on which the song first appeared

Example:

`whichbeatle --wrote something` will display who wrote "Something"

`whichbeatle -ws mean mr mustard` will display who wrote and who sang "Mean Mr. Mustard"

## This project is still under development and is not functional yet

**TODO:** 
- [X] Interpret command-line arguments (flags and search query)
- [X] Filter invalid input *- all valid flags are detected and the rest is added to the query*
- [X] Perform queries on the SQLite database
- [X] Return results *- a little rough around the edges but currently working*
- [ ] Package for Mac/Windows/Linux
