package eu.nicosworld.rithmo.engine.threat;

import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;
import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.threat.model.AssistedThreat;
import eu.nicosworld.rithmo.engine.threat.model.RegularThreats;
import eu.nicosworld.rithmo.engine.threat.model.SoloThreat;
import java.util.*;

public class RegularThreatEngine {
  private final RegularMoveGenerator regularMoveGenerator;
  private final FreePathMovementValidator pathValidator;

  public RegularThreatEngine(
      RegularMoveGenerator regularMoveGenerator, FreePathMovementValidator pathValidator) {
    this.regularMoveGenerator = regularMoveGenerator;
    this.pathValidator = pathValidator;
  }

  public RegularThreats findRegularThreats(GameState state, PieceAtPosition attacker) {
    List<InvolvedPiece> attackerDeclarations = AttackSupport.getPieceVariants(attacker);
    List<Move> potentialMoves = regularMoveGenerator.generate(state, attacker);

    List<AssistedThreat> assistedThreats = new ArrayList<>();
    List<SoloThreat> soloThreats = new ArrayList<>();

    for (Move move : potentialMoves) {
      Position targetPosition = move.to();
      PieceAtPosition target = state.board().getPieceAtPosition(targetPosition);

      if (!isValidRegularTarget(state, attacker, target)) continue;

      List<InvolvedPiece> targetDeclarations = AttackSupport.getPieceVariants(target);

      soloThreats.addAll(AttackSupport.buildSoloThreats(attackerDeclarations, targetDeclarations));
      assistedThreats.addAll(
          findAssistedThreats(state, attacker, target, attackerDeclarations, targetDeclarations));
    }
    return new RegularThreats(assistedThreats, soloThreats);
  }

  private List<AssistedThreat> findAssistedThreats(
      GameState state,
      PieceAtPosition attacker,
      PieceAtPosition target,
      List<InvolvedPiece> attackerDeclarations,
      List<InvolvedPiece> targetDeclarations) {
    List<AssistedThreat> assistedThreats = new ArrayList<>();
    Map<Position, Piece> alliesAroundTarget =
        regularMoveGenerator.getAllPieceAround(
            state, target.position(), AttackSupport.isAllyOf(attacker.piece()));

    for (Map.Entry<Position, Piece> entry : alliesAroundTarget.entrySet()) {
      Position allyPosition = entry.getKey();
      Piece allyPiece = entry.getValue();

      if (pathValidator.isBlocked(state, allyPosition, target.position())) {
        continue;
      }

      List<InvolvedPiece> allyDeclarations =
          AttackSupport.getPieceVariants(new PieceAtPosition(allyPiece, allyPosition));
      assistedThreats.addAll(
          AttackSupport.buildAssistedThreats(
              attackerDeclarations, targetDeclarations, allyDeclarations));
    }
    return assistedThreats;
  }

  private boolean isValidRegularTarget(
      GameState state, PieceAtPosition attacker, PieceAtPosition target) {
    return AttackSupport.canAttack(attacker, target.piece())
        && isRegularFreePath(state, attacker, target);
  }

  private boolean isRegularFreePath(
      GameState state, PieceAtPosition attacker, PieceAtPosition target) {
    return pathValidator.isFreePath(state, attacker.position(), target.position());
  }
}
