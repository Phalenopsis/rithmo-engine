package eu.nicosworld.rithmo.engine.setup;

import eu.nicosworld.rithmo.engine.model.Board;

public class InitialSetup {
  static Board createEscaleAJeu() {
    // BLACK
    // colonne 1
    // spotless:off
    BoardBuilder builder =
        new BoardBuilder(16, 8)
            .blackSquare(289).at(0, 0)
            .blackSquare(169).at(0, 1)
            .blackSquare(81).at(0, 6)
            .blackSquare(25).at(0, 7);
    // colonne 2
    builder
        .blackSquare(153).at(1, 0)
        .fullBlackPyramidAt(1, 1)
        .blackTriangle(49).at(1, 2)
        .blackTriangle(42).at(1, 3)
        .blackTriangle(20).at(1, 4)
        .blackTriangle(25).at(1, 5)
        .blackSquare(45).at(1, 6)
        .blackSquare(15).at(1, 7);
    // colonne 3
    builder
        .blackTriangle(81).at(2, 0)
        .blackTriangle(72).at(2, 1)
        .blackCircle(64).at(2, 2)
        .blackCircle(36).at(2, 3)
        .blackCircle(16).at(2, 4)
        .blackCircle(4).at(2, 5)
        .blackTriangle(6).at(2, 6)
        .blackTriangle(9).at(2, 7);
    // colonne 4
    builder
        .blackCircle(8).at(3, 2)
        .blackCircle(6).at(3, 3)
        .blackCircle(4).at(3, 4)
        .blackCircle(2).at(3, 5);
    // colonne 13
    builder
        .whiteCircle(3).at(12, 2)
        .whiteCircle(5).at(12, 3)
        .whiteCircle(7).at(12, 4)
        .whiteCircle(9).at(12, 5);
    // colonne 14
    builder
        .whiteTriangle(16).at(13, 0)
        .whiteTriangle(12).at(13, 1)
        .whiteCircle(9).at(13, 2)
        .whiteCircle(25).at(13, 3)
        .whiteCircle(49).at(13, 4)
        .whiteCircle(81).at(13, 5)
        .whiteTriangle(90).at(13, 6)
        .whiteTriangle(100).at(13, 7);
    // colonne 15
    builder
        .whiteSquare(28).at(14, 0)
        .whiteSquare(66).at(14, 1)
        .whiteTriangle(36).at(14, 2)
        .whiteTriangle(30).at(14, 3)
        .whiteTriangle(56).at(14, 4)
        .whiteTriangle(64).at(14, 5)
        .whiteSquare(120).at(14, 6)
        .fullWhitePyramidAt(14, 7);
    // colonne 16
    builder
        .whiteSquare(49).at(15, 0)
        .whiteSquare(121).at(15, 1)
        .whiteSquare(225).at(15, 6)
        .whiteSquare(361).at(15, 7);
    // spotless:on
    return builder.build();
  }

  public static Board createBoissiere() {
    BoardBuilder builder = new BoardBuilder(16, 8);
    // spotless:off
    builder
        .blackSquare(361).at(0, 0)
        .blackSquare(225).at(0, 1)
        .blackSquare(121).at(0, 6)
        .blackSquare(49).at(0, 7);

    builder
        .fullBoissiereBlackPyramidAt(1, 0)
        .blackSquare(120).at(1, 1)
        .blackTriangle(64).at(1, 2)
        .blackTriangle(56).at(1, 3)
        .blackTriangle(30).at(1, 4)
        .blackTriangle(36).at(1, 5)
        .blackSquare(66).at(1, 6)
        .blackSquare(28).at(1, 7);

    builder
        .blackTriangle(100).at(2, 0)
        .blackTriangle(90).at(2, 1)
        .blackCircle(81).at(2, 2)
        .blackCircle(49).at(2, 3)
        .blackCircle(25).at(2, 4)
        .blackCircle(9).at(2, 5)
        .blackTriangle(12).at(2, 6)
        .blackTriangle(16).at(2, 7);

    builder
        .blackCircle(9).at(3, 2)
        .blackCircle(7).at(3, 3)
        .blackCircle(5).at(3, 4)
        .blackCircle(3).at(3, 5);

    builder
        .whiteCircle(2).at(12, 2)
        .whiteCircle(4).at(12, 3)
        .whiteCircle(6).at(12, 4)
        .whiteCircle(8).at(12, 5);

    builder
        .whiteTriangle(9).at(13, 0)
        .whiteTriangle(6).at(13, 1)
        .whiteCircle(4).at(13, 2)
        .whiteCircle(16).at(13, 3)
        .whiteCircle(36).at(13, 4)
        .whiteCircle(64).at(13, 5)
        .whiteTriangle(72).at(13, 6)
        .whiteTriangle(81).at(13, 7);

    builder
        .whiteSquare(15).at(14, 0)
        .whiteSquare(45).at(14, 1)
        .whiteTriangle(25).at(14, 2)
        .whiteTriangle(20).at(14, 3)
        .whiteTriangle(42).at(14, 4)
        .whiteTriangle(49).at(14, 5)
        .fullBoissiereWhitePyramidAt(14, 6)
        .whiteSquare(153).at(14, 7);

    builder
        .whiteSquare(25).at(15, 0)
        .whiteSquare(81).at(15, 1)
        .whiteSquare(169).at(15, 6)
        .whiteSquare(289).at(15, 7);
    // spotless:on
    return builder.build();
  }
}
