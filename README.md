# inGen

InGen is a Pandoc based invoice generator.

There is currently limited functionality.

# Dependencies

- `java17+`
- `pandoc`
- `texlive-latex-extra`

# Compiling

Run `./compile.sh`

# Usage

Help is provided, `./ingen --help`.

Program offers two commands `./ingen template` and `./ingen compile`.

Additional help can be provided such as `./ingen compile --help`.

### ingen template

```
Usage: ingen [options] [command] [command options]
  Options:
    -h, --help

  Commands:
    template      Generate template in working directory
      Usage: template

    compile      Compile an invoice with a template
      Usage: compile [options] Input file(s)
        Options:
          -o, --out
            Output folder
            Default: .
          -t, --template
            Template (pandoc style)
            Default: default.invoice
```