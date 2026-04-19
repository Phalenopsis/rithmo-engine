# Rithmomachie

Rithmomachie is a historical mathematical strategy board game that combines numerical reasoning with tactical movement and capture mechanics. The objective is to achieve specific victory conditions, either by capturing pieces or by forming numerical progressions on the board.

This repository serves both as:
- 📘 a **structured documentation** of the game rules
- ⚙️ an **implementation of a modular game engine**

---

## 📦 Modules

### ⚙️ rithmo-engine

A standalone engine responsible for:

- Piece movement generation (regular & irregular)
- Capture detection (multiple capture rules)
- Victory evaluation (initial implementation)

The engine is:
- stateless
- rule-driven
- designed to be reused by higher-level orchestration layers

**Current version:** `0.3.0`

---

## 📚 Documentation

The rules have been carefully analyzed and structured before implementation, in order to handle the many historical variants of the game.

- [Game Rules](doc/rules.fr.md) – Overview and references
- [Board](doc/board.fr.md) – Board layout and setup
- [Mathematical Concepts](doc/math.fr.md) – Arithmetic, geometric, harmonic progressions
- [Piece Movement](doc/move.fr.md) – Movement rules
- [Captures](doc/capture.fr.md) – Capture mechanics
- [Victory Conditions](doc/victory.fr.md) – Victory rules

> All documents are in French (`.fr.md`) and include diagrams and examples.

---

## 🧠 Design Philosophy

This project follows a strict separation of concerns:

- **Engine** → computes what is possible (moves, captures, victory)
- **Core (future)** → will orchestrate turns, player decisions, and game flow

The engine does **not**:
- manage turns
- apply moves
- mutate game sessions

---

## 🚧 Project Status

The engine currently supports:

- ✅ Movement system (regular + irregular)
- ✅ Capture system (multiple rules)
- ✅ Pyramid mechanics (including partial capture)
- ✅ Victory engine (body condition – initial implementation)

Planned next steps:

- Turn orchestration (core module)
- Move & capture application (state transitions)
- Additional victory conditions (progressions, etc.)

---

## 📜 Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history and details.

---

## 🎯 Purpose

This project is intended for:

- studying Rithmomachie in a structured way
- experimenting with rule formalization
- building a reusable and testable game engine

---

## ⚠️ Notes

- The game has many historical variations; this project implements a **consistent, chosen ruleset**
- Documentation precedes implementation by design