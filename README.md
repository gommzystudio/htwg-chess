# HTWG Chess - A Chess Game in Scala 3

![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/gommzystudio/htwg-chess/test)
[![Coverage Status](https://coveralls.io/repos/github/gommzystudio/htwg-chess/badge.svg?branch=master)](https://coveralls.io/github/gommzystudio/htwg-chess?branch=master)

## Overview

This project is a chess game built in Scala 3 with sbt for Software Engineering at HTWG Konstanz.

## Features

- [x] TUI
- [ ] GUI
- [ ] Move Validation
  - [] Pawn
    - [x] Single Move
    - [x] Double Move
    - [x] Capture
    - [ ] Promotion
    - [ ] En Passant
  - [ ] Rook
    - [ ] Normal Move
    - [ ] Castling
  - [ ] Knight
    - [ ] Normal Move
  - [ ] Bishop
    - [ ] Normal Move
  - [ ] Queen
    - [ ] Normal Move
  - [ ] King
    - [ ] Normal Move
    - [ ] Castling
- [ ] Game State
  - [ ] Check
  - [ ] Checkmate
  - [ ] Draw by Repetition
  - [ ] Draw by 50 Moves
  - [ ] Draw by Insufficient Material
  - [ ] Draw by Stalemate

## Execution

This is a standard sbt project. You can compile the code with `sbt compile` and run it with `sbt run`. `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the [scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
