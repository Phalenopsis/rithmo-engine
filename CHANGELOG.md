# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---
## [0.4.5-SNAPSHOT] - 2026-
### Added
- **BoardBuilder**: Added `fullWhitePyramidAt(int, int)` and `fullBlackPyramidAt(int, int)` to quickly setup historical Rithmomachia configurations.

## [0.4.4] - 2026-04-29

### Added
- **Board**: Added `getWidth()` and `getHeight()` methods to facilitate board traversal and boundary checks.
- **BoardBuilder**: Added `at(Position)` method for consistency with the domain model.
- **BoardBuilder**: Added `fullWhitePyramidAt(Position)` and `fullBlackPyramidAt(Position)` to quickly setup historical Rithmomachia configurations.
- **Documentation**: Added comprehensive Javadoc to `BoardBuilder`, specifically detailing the assembly of composite pieces (Pyramids).

### Changed
- **Board**: Improved spatial validation by centralizing boundary logic.

### Fixed
- **Pyramid Immutability**: The `BoardBuilder` now creates a defensive copy of the component list when finalizing a `Pyramid`. This ensures that modifying the builder's internal state after placement does not affect existing pieces on the board.

### Test
- **Board**: Added unit tests for `getWidth()`, `getHeight()`, and `isInside(Position)`.
- **BoardBuilder**: Implemented a complete test suite (`BoardBuilderTest`) covering simple piece placement, custom pyramid assembly, and historical pyramid presets.
- **Pyramid**: Added regression tests to verify component immutability after placement via the builder.

## [0.4.3] - 2026-04-25
### Added
- **Fluent Assertions**: add a isEmpty(Position) method in GameStateAssertion


## [0.4.2] - 2026-04-25
### Fixed
- Made `RithmoDebug` methods static for easier access without instantiation.


## [0.4.1] - 2026-04-25
### Added
- **Test Artifact**: Generation of rithmo-engine-tests.jar now included in the deploy process.
- **Fluent Assertions**: Added GameStateAssertion for readable, domain-specific test verification.
- **Test Utilities**: Package eu.nicosworld.rithmo.engine.testutils to share helpers across repositories.
### Changed

### Test



## [0.4.0] - 2026-04-23
### Added
- Pyramid : add a removeComponent method with Piece as parameter because CaptureAction contains a Piece as capturedPiece, even if Pyramid component is a SimplePiece
- add toString in CaptureAction
- debug : add a prettyPrint to Board
- GameState now have player assets (capturedPieces and reserve)
- doc add javadoc for Player and Gamestate
- feat : rework Victory Engine and implement GoodsVictoryRule and BodyVictoryRule
### Changed
- MovementEngine : change getAllMoves signature, with only a GameState as parameter. Gamestate already posses active player. No needed as 2nd parameter
- refactor : Player have now only 2 instances
### Test
- refactor: improve tests with GameState and Player changes


## [0.3.0] - 2026-04-19

### Added
- Introduce VictoryEngine with body victory condition
- Add Board.replacePiece() for immutable pyramid updates
- Add method to retrieve all regular moves for a piece in MovementEngine
- Introduce PyramidBuilder and use it in tests
- Add method to remove a component from a pyramid

### Changed
- Improve PyramidTestCaseBuilder
- Move PyramidTestCaseBuilder to testbuilder package to decouple it from movement tests

### Tests
- Add pyramid-specific tests

---

## [0.2.0] - 2026-04-18
### Added
- Initial movement engine
- Capture system