# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---
## [0.5.0-SNAPSHOT] - 2026-05-03
### Added
- **BoardBuilder**: Added `fullWhitePyramidAt(int, int)` and `fullBlackPyramidAt(int, int)` to quickly setup historical Rithmomachia configurations.
- **Tests**: Created `MultiRulesTest` suite to validate complex capture scenarios involving multiple simultaneous rules (Encounter, Power, Ambush) and path blocking (Obstacles).

### Changed
- **Capture Engine**: Refactored the `capture` package by moving Data Transfer Objects and Records into a dedicated `model` sub-package for better separation of concerns.
- **Capture Model**: Enhanced `CaptureAction` by migrating it to a Record and introducing `InvolvedPiece`. This allows for precise tracking of which specific component of a Pyramid is involved in a capture, while maintaining a reference to the parent piece.
- **Rules**: Clarified capture rules regarding Pyramids, specifically their non-reversibility (cannot be re-entered after capture).
- **Player Assets**: Enforced business rule where Pyramids cannot be added to the reserve. Attempting to add a Pyramid to the reserve now triggers an exception to ensure consistency with historical Rithmomachia rules.

### Fixed
- **Capture Engine**: Resolved a bug where a Pyramid with a single component (or multiple components of the same value) would generate duplicate `CaptureAction` objects for the same mathematical match by implementing value-based de-duplication in `AbstractCaptureRule`.
- **Capture Logic**: Fixed a regression in multi-capture scenarios where only the first identified rule was being returned; the engine now correctly returns all valid capture opportunities for a given move.

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