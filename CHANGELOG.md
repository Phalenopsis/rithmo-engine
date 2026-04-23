# Changelog

All notable changes to this project will be documented in this file.

The format is based on Keep a Changelog
and this project adheres to Semantic Versioning.

---
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