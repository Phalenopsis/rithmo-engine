package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.model.Board;

public class RithmoDebug {
    public void printBoardAfterArrange(Board board) {
        System.out.println("*** PRE ARRANGE BOARD ***");
        System.out.println(board.prettyPrint());
    }

    public void printBoardAfterAct(Board board) {
        System.out.println("*** AFTER ACT BOARD ***");
        System.out.println(board.prettyPrint());
    }
}
