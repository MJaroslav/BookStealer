![](assets/logo.png)

[![Curseforge](https://cf.way2muchnoise.eu/bookstealer.svg)](https://www.curseforge.com/minecraft/mc-mods/bookstealer/ "Curseforge")
[![Curseforge versions](https://cf.way2muchnoise.eu/versions/bookstealer.svg)](https://www.curseforge.com/minecraft/mc-mods/bookstealer/files/all "Curseforge all files")
[![GitHub issues](https://img.shields.io/github/issues/mjaroslav/bookstealer)](https://github.com/mjaroslav/bookstealer/issues "GitHub issues")
[![GitHub forks](https://img.shields.io/github/forks/mjaroslav/bookstealer)](https://github.com/mjaroslav/bookstealer/network "GitHub forks")
[![GitHub stars](https://img.shields.io/github/stars/mjaroslav/bookstealer)](https://github.com/mjaroslav/bookstealer/stargazers "GitHub stars")
[![GitHub license](https://img.shields.io/github/license/mjaroslav/bookstealer)](LICENSE "Open license")
[![JitPack](https://jitpack.io/v/MJaroslav/bookstealer.svg)](https://jitpack.io/#MJaroslav/bookstealer "JitPack")

# BookStealer

Minecraft client mod for book content saving.

## Configuration

- `rude_text` — use rude text for save book button. Why? Just for lulz. `false` as default.
- `save_dir` — book saving directory, use `%MINECRAFT%` for game instance directory (it's `--gameDir` launch param
  value). `%MINECRAFT%/books` as default.
- `book_file_format` — book file name pattern, you can use `%TITLE%`, `%AUTHOR%` and `%DATE%` for
  autoreplacing. `%TITLE%_%AUTHOR%_%DATE%.txt` as default.
- `date_format` — date format for `%DATE%` in book file name. Uses formatting from SimpleDateFormat. `yyyy-MM-dd_hh-mm` as
  default.

## Porting on other versions and other questions

See [LICENSE](LICENSE)

## Credits
Anonymous request from [2ch.hk/mc/](https://2ch.hk/mc/)
