# Rithmomachie — Engine

Rithmomachie is a historical mathematical strategy board game that combines numerical reasoning with tactical movement and capture mechanics. The objective is to achieve specific victory conditions, either by capturing pieces or by forming numerical progressions on the board.

This repository contains the **rithmo-engine**, a standalone module responsible for implementing the core rules of the game.

---

## ⚙️ rithmo-engine

The engine is a **pure, rule-based system** that evaluates game situations without managing game flow.

It is responsible for:

* Generating piece movements (regular and irregular)
* Detecting capture opportunities (multiple capture rules)
* Evaluating victory conditions (initial implementation)

### Key characteristics

* **Stateless**: no internal game progression
* **Deterministic**: same input → same output
* **Modular**: each rule system (movement, capture, victory) is independent
* **Reusable**: designed to be used by external orchestrators

**Current version:** `0.3.0`

---

## 🧠 Design Philosophy

This project follows a strict separation of concerns:

* **Engine** → computes what is possible
* **Core (external)** → decides what is played and applies game rules over time

The engine deliberately does **not**:

* manage turns
* apply moves
* mutate game sessions
* enforce game flow

---

## 🔗 Related Project

* **rithmo-core** (separate repository)
  Handles game orchestration:

    * turn processing
    * move application
    * capture resolution
    * game session management

The core uses this engine as a dependency.

---

## 📚 Game Documentation

Before implementing the engine, the rules of the game were carefully analyzed and structured to handle the many historical variations of Rithmomachie.

* [Game Rules](doc/rules.fr.md) – Overview and references
* [Board](doc/board.fr.md) – Board layout and setup
* [Mathematical Concepts](doc/math.fr.md) – Arithmetic, geometric, harmonic progressions
* [Piece Movement](doc/move.fr.md) – Movement rules
* [Captures](doc/capture.fr.md) – Capture mechanics
* [Victory Conditions](doc/victory.fr.md) – Victory rules

> All documents are written in French (`.fr.md`) and include diagrams and examples.

---

## 🚧 Project Status

The engine currently supports:

* ✅ Movement system (regular + irregular)
* ✅ Capture system (multiple rules)
* ✅ Pyramid mechanics (including partial capture)
* ✅ Victory engine (body condition — initial implementation)

Planned improvements:

* Additional victory conditions (progressions, combinations)
* Extended rule coverage
* API stabilization
---

# 🛠️ Development Environment

## Git Hooks

This project uses a local `pre-commit` hook to enforce code hygiene before every commit.

The hook automatically:

* Runs the Spotless formatter
* Rejects commits containing accidental `TestDebugger` usage
* Ensures formatting consistency across the codebase

Enable hooks after cloning:

```bash
git config core.hooksPath .githooks
```

---

## Code Formatting

This project uses Spotless with Google Java Format.

Formatting is enforced automatically through the pre-commit hook.

### Manual formatting

Format the whole project:

```bash
mvn spotless:apply
```

Validate formatting without modifying files:

```bash
mvn spotless:check
```

---

### Formatter Rules

The formatter automatically handles:

* Import ordering
* Removal of unused imports
* Indentation normalization
* Empty-line cleanup
* Standardized Java formatting

---

### Preserving intentional formatting

Some test assertions use visual indentation to express decision trees.

For these rare cases, formatting can be locally disabled:

```java
// spotless:off
StatusDTOAssertion.from(nextStatus)
        .status()
            .isInPostCapturePhase()
        .decisions()
            .canCaptureInOneDecision("WT12(2,1)")
            .hasCaptureCiblesFor("BC4(1,2)", "WT12(2,1)")
            .cannotCaptureWith("BC8(1,0)", "WT12(2,1)");
// spotless:on
```

---

## 📜 Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history and details.

---

## 🎯 Purpose

This project aims to:

* Provide a **clear and structured model** of Rithmomachie rules
* Offer a **testable and reusable game engine**
* Serve as a foundation for building higher-level game systems

---

## ⚠️ Notes

* Rithmomachie has many historical variants; this project implements a **consistent and explicit ruleset**
* The implementation is driven by prior rule formalization, not the other way around
