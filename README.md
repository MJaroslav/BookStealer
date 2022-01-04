# BookStealer

Minecraft client mod for book content saving. For saving the book, open it and click save button.

## Configuration

- `rude_text` — use rude text for save book button. Why? Just for lulz. `false` as default.
- `save_dir` — book saving directory, use `%MINECRAFT%` for game instance directory (it's `--gameDir` launch param
  value). `%MINECRAFT%/books` as default.
- `book_file_format` — book file name pattern, you can use %TITLE%, %AUTHOR% and %DATE% for
  autoreplacing. `%TITLE%_%AUTHOR%_%DATE%.txt` as default.
- `date_format` — date format for %DATE% in book file name. Uses formatting from SimpleDateFormat. `yyyy-MM-dd_hh-mm` as
  default.

## Porting on other versions and other questions

See LICENSE

## Credits
Anonymous request from [2ch.hk/mc/](https://2ch.hk/mc/)
