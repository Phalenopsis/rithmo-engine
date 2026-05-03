package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.model.PlayerColor;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCaptureTest {
    protected final RegularMoveGenerator regularGenerator = new RegularMoveGenerator();
    protected final FreePathMovementValidator pathValidator = new FreePathMovementValidator();

    public CaptureEngine engine;

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

        CaptureContext ctx = new CaptureContextBuilder()
                .from(bb)
                .at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY())
                .build();

        List<CaptureAction> captures = engine.findCaptures(ctx);

        // 1. Verify the number of captures found
        assertEquals(
                testCase.getExpectedCaptureCount(),
                captures.size(),
                "Incorrect number of captures for test case: " + testCase
        );

        // 2. Verify each expected capture detail (including the TYPE)
        for (ExpectedCapture expected : testCase.getExpectedCaptures()) {
            boolean found = captures.stream().anyMatch(c ->
                    c.capturedPiece().getType() == expected.type()
                            && c.capturedPiece().getValue() == expected.value()
                            && c.isWholeCapture() == expected.isWhole()
                            && c.type() == expected.captureType() // Now validating the rule type!
            );

            assertTrue(found,
                    String.format("Missing expected capture: %s (%s) in test case: %s",
                            expected.type(), expected.captureType(), testCase));
        }
    }

}
