package eu.nicosworld.rithmo.engine.capture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.nicosworld.rithmo.engine.capture.justification.CaptureJustification;
import eu.nicosworld.rithmo.engine.capture.justification.ImprisonmentJustification;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PlayerColor;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import eu.nicosworld.rithmo.engine.threat.AttackSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractCaptureTest {
  protected final RegularMoveGenerator regularGenerator = new RegularMoveGenerator();
  protected final FreePathMovementValidator pathValidator = new FreePathMovementValidator();

  public CaptureEngine engine;
  private CaptureContext ctx;

  protected void launchTestCase(CaptureTestCase testCase) {
    BoardBuilder bb = new BoardBuilder();

    // 1. Attacker Setup
    bb.piece(testCase.getAttackerType(), testCase.getAttackerValue(), PlayerColor.BLACK);
    for (CaptureTestCase.ComponentData comp : testCase.getAttackerComponents()) {
      bb.withComponent(comp.type(), comp.value());
    }
    bb.at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY());

    // 2. Other pieces Setup
    for (CaptureTestCase.ExtraPiece p : testCase.getOtherPieces()) {
      bb.piece(p.type(), p.value(), p.color());
      for (CaptureTestCase.ComponentData comp : p.components()) {
        bb.withComponent(comp.type(), comp.value());
      }
      bb.at(p.pos().getX(), p.pos().getY());
    }

    ctx =
        new CaptureContextBuilder()
            .from(bb)
            .at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY())
            .build();

    List<CaptureAction> captures = engine.findActiveCaptures(ctx);
    captures.addAll(engine.findGlobalCaptures(ctx));

    // 1. Verify the number of captures found
    assertEquals(
        testCase.getExpectedCaptureCount(),
        captures.size(),
        "Incorrect number of captures for test case: " + testCase);

    // 2. Verify each expected capture detail (including the TYPE)
    for (ExpectedCapture expected : testCase.getExpectedCaptures()) {
      boolean found = captures.stream().anyMatch(c -> matches(c, expected));

      assertTrue(
          found,
          String.format("Missing expected capture: %s in test case: %s", expected, testCase));
    }
  }

  private boolean matches(CaptureAction actual, ExpectedCapture expected) {
    return actual.capturedPiece().getType() == expected.capturedPieceType()
        && actual.capturedPiece().getValue() == expected.capturedValue()
        && actual.isWholeCapture() == expected.isWhole()
        && actual.type() == expected.captureType()
        && compareJustifications(actual.justification(), expected.justification())
        && lookAtBlockers(actual, expected);
  }

  private boolean compareJustifications(
      CaptureJustification actualJustification, CaptureJustification expectedJustification) {
    if (actualJustification instanceof ImprisonmentJustification actual
        && expectedJustification instanceof ImprisonmentJustification expected) {
      boolean areDestinationsEquals =
          Set.copyOf(actual.regularMovesTo()).equals(Set.copyOf(expected.regularMovesTo()));
      boolean areBlockedAtEquals =
          Set.copyOf(actual.blockedAt()).equals(Set.copyOf(expected.blockedAt()));
      return areBlockedAtEquals && areDestinationsEquals;
    }
    return Objects.equals(actualJustification, expectedJustification);
  }

  private boolean lookAtBlockers(CaptureAction actual, ExpectedCapture expected) {
    if (!expected.captureType().equals(CaptureType.IMPRISONMENT)) return true;
    Piece target = ctx.board().getPieceAt(expected.targetPosition());

    List<InvolvedPiece> blockers = new ArrayList<>();
    for (Position position : expected.blockersPosition()) {
      if (position.equals(expected.attackerPos())) continue;
      Piece piece = ctx.board().getPieceAt(position);
      if (AttackSupport.areEnemies(piece, target)) {
        InvolvedPiece involvedPiece = new InvolvedPiece(piece, position, piece);
        blockers.add(involvedPiece);
      }
    }

    return Set.copyOf(actual.supporters()).equals(Set.copyOf(blockers));
  }
}
