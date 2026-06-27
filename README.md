# Rithmomachie — Engine

Rithmomachie is a historical mathematical strategy board game combining tactical movement with numerical reasoning. Unlike most abstract strategy games, victory can be achieved not only by capturing pieces but also by constructing mathematical relationships directly on the board.

This repository contains **rithmo-engine**, a standalone Java library implementing the game's rule engine.

---

# ⚙️ What is rithmo-engine?

The engine is a **pure, deterministic rule evaluator**.

Given a game state, it computes:

* legal movements
* capture opportunities
* victory conditions
* mathematical relationships between pieces

The engine intentionally contains **no game flow management**.

It does **not**:

* manage turns
* apply moves
* mutate game sessions
* decide which move is played

Those responsibilities belong to the external **rithmo-core** project.

---

# ✨ Features

Current implementation includes:

* ✅ Regular and irregular movement generation
* ✅ Complete capture engine

  * Ambush
  * Assault
  * Encounter
  * Power
  * Progression
  * Imprisonment (*Obsidio*)
* ✅ Mathematical progression detection

  * Arithmetic
  * Geometric
  * Harmonic
* ✅ Geometric pattern detection

  * Lines
  * Squares
* ✅ Victory engine

  * Body Victory
  * Goods Victory
  * Lawsuit Victory
  * Proper Victory
* ✅ Extensive testing utilities

---

# 🧠 Design Philosophy

The project follows a strict separation of concerns.

| Project           | Responsibility                                    |
| ----------------- | ------------------------------------------------- |
| **rithmo-engine** | Computes what is possible                         |
| **rithmo-core**   | Applies rules over time and orchestrates the game |

The engine is designed to remain:

* Stateless
* Deterministic
* Modular
* Easily testable
* Reusable by any frontend or orchestration layer

---

# 📚 Documentation

Game documentation:

* [Rules](doc/rules.fr.md)
* [Board](doc/board.fr.md)
* [Mathematics](doc/math.fr.md)
* [Movement](doc/move.fr.md)
* [Captures](doc/capture.fr.md)
* [Victory Conditions](doc/victory.fr.md)

Development documentation:

* [Contributing Guide](doc/contributing.md)
* [Release Process](doc/release.md)

---

# 🔗 Related Project

**rithmo-core**

The game orchestration layer built on top of this engine.

---

# 🚀 Building

```bash
mvn verify
```

---

# 📜 Changelog

See [CHANGELOG.md](CHANGELOG.md).

---

# 📄 License

(To be added.)
