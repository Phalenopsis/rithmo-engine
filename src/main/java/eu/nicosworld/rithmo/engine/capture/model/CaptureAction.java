package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.capture.CaptureType;
import eu.nicosworld.rithmo.engine.capture.justification.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import java.util.List;

public record CaptureAction(
    InvolvedPiece actor,
    InvolvedPiece target,
    List<InvolvedPiece> supporters,
    CaptureType type,
    CaptureJustification justification) {
  public CaptureAction {
    supporters = List.copyOf(supporters);
  }

  public static CaptureAction assault(
      InvolvedPiece actor, InvolvedPiece target, AssaultJustification justification) {
    return new CaptureAction(actor, target, List.of(), CaptureType.ASSAULT, justification);
  }

  public static CaptureAction ambush(
      InvolvedPiece actor,
      InvolvedPiece target,
      InvolvedPiece supporter,
      AmbushJustification justification) {
    return new CaptureAction(actor, target, List.of(supporter), CaptureType.AMBUSH, justification);
  }

  public static CaptureAction encounter(
      InvolvedPiece actor, InvolvedPiece target, EncounterJustification justification) {
    return new CaptureAction(actor, target, List.of(), CaptureType.ENCOUNTER, justification);
  }

  public static CaptureAction progression(
      InvolvedPiece actor,
      InvolvedPiece target,
      InvolvedPiece supporter,
      ProgressionCaptureJustification justification) {
    return new CaptureAction(
        actor, target, List.of(supporter), CaptureType.PROGRESSION, justification);
  }

  public static CaptureAction power(
      InvolvedPiece actor, InvolvedPiece target, PowerJustification justification) {
    return new CaptureAction(actor, target, List.of(), CaptureType.POWER, justification);
  }

  public static CaptureAction imprisonment(
      InvolvedPiece actor,
      InvolvedPiece target,
      List<InvolvedPiece> blockers,
      List<Position> blockedAt,
      List<Position> regularMovesTo) {
    return new CaptureAction(
        actor,
        target,
        blockers,
        CaptureType.IMPRISONMENT,
        new ImprisonmentJustification(regularMovesTo, blockedAt));
  }

  public static CaptureAction imprisonment(
      InvolvedPiece actor,
      InvolvedPiece target,
      List<InvolvedPiece> blockers,
      ImprisonmentJustification justification) {
    return new CaptureAction(actor, target, blockers, CaptureType.IMPRISONMENT, justification);
  }

  public boolean isWholeCapture() {
    return target.parentPiece().equals(target.specificComponent());
  }

  public boolean isReversible() {
    return !(target.parentPiece() instanceof Pyramid);
  }

  public Piece targetPiece() {
    return target.parentPiece();
  }

  public Position targetPosition() {
    return target.position();
  }

  public Piece capturedPiece() {
    return target.specificComponent();
  }
}
