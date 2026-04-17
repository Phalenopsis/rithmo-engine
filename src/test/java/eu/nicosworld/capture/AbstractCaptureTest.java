package eu.nicosworld.capture;

import eu.nicosworld.capture.capturerule.AmbushRule;
import eu.nicosworld.model.PlayerColor;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.RegularMoveGenerator;
import eu.nicosworld.setup.BoardBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCaptureTest {
    protected final RegularMoveGenerator regularGenerator = new RegularMoveGenerator();
    protected final FreePathMovementValidator pathValidator = new FreePathMovementValidator();

    public CaptureEngine engine;

    protected void launchTestCase(CaptureTestCase testCase) {

        BoardBuilder bb = new BoardBuilder();

        // 1. Attacker
        bb.piece(testCase.getAttackerType(), testCase.getAttackerValue(), PlayerColor.BLACK);
        for (CaptureTestCase.ComponentData comp : testCase.getAttackerComponents()) {
            bb.withComponent(comp.type(), comp.value());
        }
        bb.at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY());

        // 2. Others
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

        // 1. count
        assertEquals(
                testCase.getExpectedCaptureCount(),
                captures.size(),
                "Failure on test case: " + testCase
        );

        // 2. verify captured pieces exist
        for (ExpectedCapture expected : testCase.getExpectedCaptures()) {

            boolean found = captures.stream().anyMatch(c ->
                    c.capturedPiece().getType() == expected.type()
                            && c.capturedPiece().getValue() == expected.value()
                            && c.isWholeCapture() == expected.isWhole()
            );

            assertTrue(found,
                    "Missing expected ambush capture: " + expected + " in " + testCase);
        }
    }


}
