package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.imprisonment.Imprisonment;
import eu.nicosworld.rithmo.engine.capture.justification.ImprisonmentJustification;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.threat.AttackSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ImprisonmentRule implements GlobalCaptureRule {
  private enum ImprisonmentMode {
    PRE_MOVE,
    POST_MOVE,
    END_TURN
  }

  private final FreePathMovementValidator validator;
  private final RegularMoveGenerator moveGenerator;

  public ImprisonmentRule(FreePathMovementValidator validator, RegularMoveGenerator moveGenerator) {
    this.validator = validator;
    this.moveGenerator = moveGenerator;
  }

  @Override
  public List<CaptureAction> findPreMoveCaptures(CaptureContext context) {
    return findImprisonmentActions(context, ImprisonmentMode.PRE_MOVE);
  }

  public List<CaptureAction> findPostMoveCaptures(CaptureContext context) {
    return findImprisonmentActions(context, ImprisonmentMode.POST_MOVE);
  }

  public List<CaptureAction> findEndTurnCaptures(CaptureContext context) {
    return findImprisonmentActions(context, ImprisonmentMode.END_TURN);
  }

  private List<CaptureAction> findImprisonmentActions(
      CaptureContext context, ImprisonmentMode phase) {
    List<CaptureAction> actions = new ArrayList<>();
    List<Imprisonment> imprisonments = findImprisonment(context);
    for (Imprisonment imprisonment : imprisonments) {
      actions.addAll(buildCaptureActions(context.actor(), imprisonment, phase));
    }
    return actions;
  }

  private List<Imprisonment> findImprisonment(CaptureContext context) {
    List<Imprisonment> imprisonments = new ArrayList<>();

    for (PieceAtPosition target :
        context.board().getPiecesForPlayer(context.state().currentPlayer().opponent())) {

      analyzeImprisonment(context, target).ifPresent(imprisonments::add);
    }

    return imprisonments;
  }

  private List<CaptureAction> buildCaptureActions(
      PieceAtPosition actor, Imprisonment imprisonment, ImprisonmentMode phase) {
    ImprisonmentJustification justification = imprisonment.toJustification();

    return switch (phase) {
      case PRE_MOVE -> buildDetailedCaptureAction(imprisonment, justification);
      case POST_MOVE -> buildCaptureActionForActor(actor, imprisonment, justification);
      case END_TURN -> buildGlobalCaptureActions(imprisonment, justification);
    };
  }

  private List<CaptureAction> buildGlobalCaptureActions(
      Imprisonment imprisonment, ImprisonmentJustification justification) {
    CaptureAction captureAction =
        CaptureAction.imprisonment(
            null,
            InvolvedPiece.whole(imprisonment.target()),
            imprisonment.enemyBlockers().stream().map(InvolvedPiece::whole).toList(),
            justification);

    return List.of(captureAction);
  }

  private List<CaptureAction> buildCaptureActionForActor(
      PieceAtPosition attacker,
      Imprisonment imprisonment,
      ImprisonmentJustification justification) {
    if (!imprisonment.enemyBlockers().contains(attacker)) {
      return List.of();
    }

    return List.of(createCaptureActionWithAllies(attacker, imprisonment, justification));
  }

  List<CaptureAction> buildDetailedCaptureAction(
      Imprisonment imprisonment, ImprisonmentJustification justification) {
    List<CaptureAction> captureActions = new ArrayList<>();
    for (PieceAtPosition actor : imprisonment.enemyBlockers()) {
      captureActions.add(createCaptureActionWithAllies(actor, imprisonment, justification));
    }
    return captureActions;
  }

  private CaptureAction createCaptureActionWithAllies(
      PieceAtPosition actor, Imprisonment imprisonment, ImprisonmentJustification justification) {
    List<PieceAtPosition> allies =
        imprisonment.enemyBlockers().stream().filter(e -> !e.equals(actor)).toList();
    return CaptureAction.imprisonment(
        InvolvedPiece.whole(actor),
        InvolvedPiece.whole(imprisonment.target()),
        allies.stream().map(InvolvedPiece::whole).toList(),
        justification);
  }

  private Optional<Imprisonment> analyzeImprisonment(
      CaptureContext context, PieceAtPosition target) {

    List<PieceAtPosition> enemyBlockers = new ArrayList<>();
    List<PieceAtPosition> allyBlockers = new ArrayList<>();
    List<Move> regularMoves = moveGenerator.generate(context.state(), target);

    for (Move move : regularMoves) {
      Optional<Position> blockerPos =
          validator.isBlockedAt(context.state(), move.from(), move.to(), true);

      if (blockerPos.isEmpty()) {
        return Optional.empty();
      }
      PieceAtPosition blocker = context.board().getPieceAtPosition(blockerPos.get());
      if (AttackSupport.areEnemies(blocker, target)) {
        enemyBlockers.add(blocker);
      } else {
        allyBlockers.add(blocker);
      }
    }

    if (enemyBlockers.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        new Imprisonment(
            target, enemyBlockers, allyBlockers, regularMoves.stream().map(Move::to).toList()));
  }
}
