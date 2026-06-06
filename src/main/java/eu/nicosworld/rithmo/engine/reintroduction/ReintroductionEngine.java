package eu.nicosworld.rithmo.engine.reintroduction;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.Position;
import java.util.ArrayList;
import java.util.List;

public class ReintroductionEngine {

  public List<Reintroduction> generateReintroduction(GameState state) {
    List<Reintroduction> possiblesPlacement = new ArrayList<>();
    List<Position> freeSpaces = getFreeSpacesFor(state, state.currentPlayer());
    for (Piece piece : state.assetsOfCurrentPlayer().reserve()) {
      for (Position position : freeSpaces) {
        possiblesPlacement.add(new Reintroduction(piece, position));
      }
    }
    return possiblesPlacement;
  }

  List<Position> getFreeSpacesFor(GameState state, Player player) {
    if (player.equals(Player.BLACK)) {
      return getFreeSpaceForColumn(state, 0);
    }
    return getFreeSpaceForColumn(state, state.board().getWidth() - 1);
  }

  List<Position> getFreeSpaceForColumn(GameState state, int columnIndex) {
    List<Position> freeSpaces = new ArrayList<>();
    for (int y = 0; y < state.board().getHeight(); y++) {
      Position position = new Position(columnIndex, y);
      if (state.board().isEmpty(position)) {
        freeSpaces.add(position);
      }
    }
    return freeSpaces;
  }
}
