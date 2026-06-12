package eu.nicosworld.rithmo.engine.threat;

import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import eu.nicosworld.rithmo.engine.threat.model.AssistedThreat;
import eu.nicosworld.rithmo.engine.threat.model.SoloThreat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AttackSupport {
  /**
   * Extracts all possible declarations from a piece. For a simple piece, returns the piece itself.
   * For a pyramid, returns the pyramid as a whole and its components (without duplicates).
   * Duplicates are resolved by prioritizing the whole pyramid over its components.
   */
  public static List<InvolvedPiece> getPieceVariants(PieceAtPosition target) {
    Map<Integer, InvolvedPiece> uniqueTargets = new LinkedHashMap<>();

    uniqueTargets.put(target.piece().getValue(), InvolvedPiece.whole(target));

    if (target.piece() instanceof Pyramid pyramid) {
      for (Piece component : pyramid.getComponents()) {
        uniqueTargets.putIfAbsent(
            component.getValue(), InvolvedPiece.component(pyramid, component, target.position()));
      }
    }

    return new ArrayList<>(uniqueTargets.values());
  }

  public static List<SoloThreat> buildSoloThreats(
      List<InvolvedPiece> attackers, List<InvolvedPiece> targets) {
    List<SoloThreat> soloThreats = new ArrayList<>();
    for (InvolvedPiece attacker : attackers) {
      for (InvolvedPiece target : targets) {
        soloThreats.add(new SoloThreat(attacker, target));
      }
    }
    return soloThreats;
  }

  public static boolean areEnemies(Piece first, Piece second) {
    return first.getPlayer() != second.getPlayer();
  }

  public static boolean canAttack(PieceAtPosition attacker, Piece targetPiece) {
    return targetPiece != null && areEnemies(attacker.piece(), targetPiece);
  }

  public static Predicate<Piece> isAllyOf(Piece piece) {
    return p -> p.getPlayer() == piece.getPlayer() && !p.equals(piece);
  }

  public static List<AssistedThreat> buildAssistedThreats(
      List<InvolvedPiece> attackers, List<InvolvedPiece> targets, List<InvolvedPiece> allies) {
    List<AssistedThreat> assistedThreats = new ArrayList<>();
    for (InvolvedPiece attacker : attackers) {
      for (InvolvedPiece target : targets) {
        for (InvolvedPiece ally : allies) {
          assistedThreats.add(new AssistedThreat(attacker, ally, target));
        }
      }
    }
    return assistedThreats;
  }
}
