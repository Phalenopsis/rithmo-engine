package eu.nicosworld.rithmo.engine.capture.capturerule;

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

public final class ImprisonmentRule implements CaptureRule {
  // TODO : add dependency injection
  FreePathMovementValidator validator = new FreePathMovementValidator();
  RegularMoveGenerator moveGenerator = new RegularMoveGenerator();

  @Override
  public List<CaptureAction> findCaptures(CaptureContext context) {
    return findImprisonmentActions(context);
  }

  private List<CaptureAction> findImprisonmentActions(CaptureContext context) {
    List<CaptureAction> captureActions = new ArrayList<>();

    for (PieceAtPosition target :
        context.board().getPiecesForPlayer(context.state().currentPlayer().opponent())) {
      List<PieceAtPosition> blockers = new ArrayList<>();
      List<Move> possiblePositions = moveGenerator.generate(context.state(), target);
      for (Move move : possiblePositions) {
        Optional<Position> blockerPosition =
            validator.isBlockedAt(context.state(), move.from(), move.to(), true);
        if (blockerPosition.isEmpty()) {
          blockers.clear();
          break; // il y a au moins un regularMove possible, l'emprisonnement n'est pas possible
        }
        PieceAtPosition pieceAtPosition = context.board().getPieceAtPosition(blockerPosition.get());
        blockers.add(pieceAtPosition);
      }
      if (!blockers.isEmpty()) {
        if (blockers.stream()
            .anyMatch(p -> p.piece().getPlayer().equals(target.piece().getPlayer().opponent()))) {
          // dans ce cas il y a au moins un ennemi qui bloque la cible, donc l'emprisonnement est
          // valide
          captureActions.addAll(
              buildCaptureActions(
                  target, blockers, possiblePositions.stream().map(Move::to).toList()));
        }
      }
    }
    return captureActions;
  }

  private List<CaptureAction> buildCaptureActions(
      PieceAtPosition target, List<PieceAtPosition> blockers, List<Position> possibleRegularMoves) {
    List<CaptureAction> captureActions = new ArrayList<>();
    ImprisonmentJustification justification =
        new ImprisonmentJustification(
            possibleRegularMoves, blockers.stream().map(PieceAtPosition::position).toList());

    List<PieceAtPosition> enemiesBlockers =
        blockers.stream().filter(p -> AttackSupport.areEnemies(p, target)).toList();
    for (PieceAtPosition actor : enemiesBlockers) {
      List<PieceAtPosition> allies =
          enemiesBlockers.stream().filter(e -> !e.equals(actor)).toList();
      CaptureAction captureAction =
          CaptureAction.imprisonment(
              InvolvedPiece.whole(actor),
              InvolvedPiece.whole(target),
              allies.stream().map(InvolvedPiece::whole).toList(),
              justification);
      captureActions.add(captureAction);
    }
    return captureActions;
  }
}
