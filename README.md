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

`whichbeatle --wrote something`

`whichbeatle -ws mean mr mustard`

## This project is still under development and is not functional yet

**TODO:** 
- [X] Interpret command-line arguments (flags and search query)
- [ ] Filter invalid input
- [ ] Perform queries on the SQLite database
- [ ] Return results
- [ ] Package for Mac/Windows/Linux
