# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---
## [0.5.2-SNAPSHOT] - In progress
### Fixed
- **Goods Victory**: Fixed victory evaluation to use the total value of captured pieces rather than the captured piece count, restoring correct "Victory of Goods" behavior.

### Added
- **GameState**: Added `GameState.initial(Player)` factory method to create a fully initialized game state with an empty board and empty assets for all players.
- **PlayerAssets**: Added `capturedDigitCount()` to compute the total number of digits represented by captured piece values, supporting lawsuit victory evaluation.
- **Victory Model**: Added `BodyVictory`, `GoodsVictory`, and `LawsuitVictory` result objects exposing winner information and rule-specific victory metrics.
- **Victory Rules**: Added lawsuit victory evaluation based on the total number of digits represented by captured piece values.
- **Victory Engine**: Added validation preventing registration of multiple rules of the same victory type.

### Changed
- **Victory Engine**: Victory evaluation now returns typed `Victory` results carrying rule-specific details instead of simple boolean outcomes.
- **Victory Rules**: Introduced a common threshold-based implementation for body, goods and lawsuit victory conditions.

### Test
- **Victory Assertions**: Introduced dedicated assertion helpers for `BodyVictory`, `GoodsVictory`, and `LawsuitVictory`, improving readability of victory-rule test suites.
- **Victory Rules**: Added coverage for lawsuit victories and enriched body/goods victory validation scenarios.
- **Victory Engine**: Added integration tests covering multiple simultaneous victories, active-player evaluation, and duplicate-rule validation.


## [0.5.1] - 2026-05-25
### Documentation
- Fixed wording in assault capture rule description
- Clarified that assault capture does not require the target to be larger

### Added
- **Capture Justification System**: Added explicit mathematical explanations for generated capture actions.
- **Rule Semantics**: Introduced structured justification models for:
  - Encounter
  - Ambush
  - Assault
  - Power
- **Debugging Support**: Capture results can now expose the exact mathematical reasoning used to validate a capture.

### Test
- **GameStateAssertion**: Replaced `hasInReserve(...)` with `hasCapturedEquivalentInReserve(...)` to support reserve-piece ownership normalization introduced by the Engine.
- **GameStateAssertion**: Added `doesNotHaveCapturedEquivalentInReserve(...)` helper for validating absence of converted reserve pieces.
- **Reserve Assertions**: Kept `hasNotInReserve(...)` to explicitly verify that reserve pieces are newly instantiated objects rather than preserved captured instances.
- **Pyramid**: Added immutability regression tests to verify defensive copying and protection of internal component collections.
- **Capture Utilities**: Added shared `CaptureJustifications` helpers to reusable test utilities.
- **Capture Engine Tests**: Extended internal capture test infrastructure to support rule-specific justification assertions.
- **Capture Rules**: Added exhaustive justification-based validation for Encounter, Ambush, Assault and Power capture rules.
- **MultiRules**: Added integration coverage for simultaneous multi-rule captures and justification aggregation.
- **Cleaning**: Removed temporary debug output from capture test suites.

### Refactor
- **Pyramid**:
  - secured internal component collection using immutable list copies
  - prevented external mutation of pyramid components through constructor arguments or getters
  - aligned pyramid behavior with the engine's immutable piece model
  - reinforced safety of move/action context propagation and state replay consistency

- **Capture Model**:
  - introduced explicit capture justification records for rule traceability
  - added operator/relation enums for Ambush, Assault and Power semantic clarity
  - enriched capture actions with mathematical justification metadata


## [0.5.0] - 2026-05-15
### Added
- **BoardBuilder**: Added `fullWhitePyramidAt(int, int)` and `fullBlackPyramidAt(int, int)` to quickly setup historical Rithmomachia configurations.
- **Tests**: Created `MultiRulesTest` suite to validate complex capture scenarios involving multiple simultaneous rules (Encounter, Power, Ambush) and path blocking (Obstacles).
- **Piece**: Added `isPyramid()` method to the `Piece` abstract class to allow programmatic distinction between standard pieces and pyramids.
- **InvolvedPiece**: Added static factory methods `whole()` and `component()` to clarify whether a piece is targeted as a single entity or as part of a pyramid.
- **InvolvedPiece**: Added `isWhole()` helper method for easier piece-type checks within the engine and appliers. 
- **Test Setup**: Added helper utilities to generate predefined test cases for complex rule validation scenarios.

### Changed
- **Capture Engine**: Refactored the `capture` package by moving Data Transfer Objects and Records into a dedicated `model` sub-package for better separation of concerns.
- **Capture Model**: Enhanced `CaptureAction` by migrating it to a Record and introducing `InvolvedPiece`. This allows for precise tracking of which specific component of a Pyramid is involved in a capture, while maintaining a reference to the parent piece.
- **Rules**: Clarified capture rules regarding Pyramids, specifically their non-reversibility (cannot be re-entered after capture).
- **Player Assets**: Enforced business rule where Pyramids cannot be added to the reserve. Attempting to add a Pyramid to the reserve now triggers an exception to ensure consistency with historical Rithmomachia rules.
- **Player Assets**: Introduced a dual-representation system for captured pieces:
    - `captured` now preserves original pieces with their initial identity and color for historical tracking and victory condition evaluation.
    - `reserve` contains newly instantiated pieces derived from captured ones, with their color normalized to the owning player and a new identity assigned.
      This separation ensures that game-state integrity, scoring logic, and reintroduction mechanics remain decoupled.
### Fixed
- **Capture Engine**: Resolved a bug where a Pyramid with a single component (or multiple components of the same value) would generate duplicate `CaptureAction` objects for the same mathematical match by implementing value-based de-duplication in `AbstractCaptureRule`.
- **Capture Logic**: Fixed a regression in multi-capture scenarios where only the first identified rule was being returned; the engine now correctly returns all valid capture opportunities for a given move.

### Features
- **Reintroduction System**: Introduced computation of valid reintroduction options, allowing players to reintroduce captured pieces back onto the board under defined rule constraints (board availability, ownership, and rule validation).

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