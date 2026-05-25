package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.justification.*;
import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.PlayerColor;
import eu.nicosworld.rithmo.engine.model.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;

public class CaptureTestCase {

    private final PieceType attackerType;
    private final int attackerValue;
    private final Position attackerPos;
    private final List<ComponentData> attackerComponents = new ArrayList<>();

    private final List<ExtraPiece> otherPieces = new ArrayList<>();
    private int expectedCaptureCount = 0;

    private final List<ExpectedCapture> expectedCaptures = new ArrayList<>();

    private CaptureTestCase(PieceType type, int value, int x, int y) {
        this.attackerType = type;
        this.attackerValue = value;
        this.attackerPos = new Position(x, y);
    }

    public record ComponentData(PieceType type, int value) {}
    public record ExtraPiece(PieceType type, int value, PlayerColor color, Position pos, List<ComponentData> components) {}

    // --- Entry Points ---
    public static CaptureTestCase blackCircleAt(int val, int x, int y) { return new CaptureTestCase(PieceType.CIRCLE, val, x, y); }
    public static CaptureTestCase blackTriangleAt(int val, int x, int y) { return new CaptureTestCase(PieceType.TRIANGLE, val, x, y); }
    public static CaptureTestCase blackSquareAt(int val, int x, int y) { return new CaptureTestCase(PieceType.SQUARE, val, x, y); }
    public static CaptureTestCase blackPyramidAt(int x, int y) { return new CaptureTestCase(PieceType.PYRAMID, 0, x, y); }
    public static CaptureTestCase whitePyramidAt(int x, int y) { return new CaptureTestCase(PieceType.PYRAMID, 0, x, y); }

    public CaptureTestCase withComponent(PieceType type, int value) {
        this.attackerComponents.add(new ComponentData(type, value));
        return this;
    }

    public CaptureTestCase againstWhite(PieceType type, int value, int x, int y) {
        this.otherPieces.add(new ExtraPiece(type, value, PlayerColor.WHITE, new Position(x, y), List.of()));
        return this;
    }

    public CaptureTestCase againstWhitePyramid(int x, int y, ComponentData... comps) {
        this.otherPieces.add(new ExtraPiece(PieceType.PYRAMID, 0, PlayerColor.WHITE, new Position(x, y), List.of(comps)));
        return this;
    }

    public CaptureTestCase withBlackAlly(PieceType type, int value, int x, int y) {
        this.otherPieces.add(new ExtraPiece(type, value, PlayerColor.BLACK, new Position(x, y), List.of()));
        return this;
    }

    public CaptureTestCase withBlackPyramidAlly(int x, int y, ComponentData ...components ) {
        this.otherPieces.add(new ExtraPiece(PieceType.PYRAMID, 0, PlayerColor.BLACK, new Position(x, y), List.of(components)));
        return this;
    }

    public CaptureTestCase withObstacleAt(int x, int y) {
        return withBlackAlly(PieceType.CIRCLE, 999, x, y);
    }

    // --- EXPECTATIONS (Typed) ---

    // 1. ENCOUNTER
    public CaptureTestCase expectEncounter(PieceType type, int value, EncounterJustification justification) {
        return addExpected(type, value, true, CaptureType.ENCOUNTER, justification);
    }

    public CaptureTestCase expectPartialEncounter(PieceType type, int value, EncounterJustification justification) {
        return addExpected(type, value, false, CaptureType.ENCOUNTER, justification);
    }

    // 2. AMBUSH
    public CaptureTestCase expectAmbush(PieceType type, int value, AmbushJustification justification) {
        return addExpected(type, value, true, CaptureType.AMBUSH, justification);
    }

    public CaptureTestCase expectPartialAmbush(PieceType type, int value, AmbushJustification justification) {
        return addExpected(type, value, false, CaptureType.AMBUSH, justification);
    }

    // 3. ASSAULT
    public CaptureTestCase expectAssault(PieceType type, int value, AssaultJustification justification) {
        return addExpected(type, value, true, CaptureType.ASSAULT, justification);
    }

    public CaptureTestCase expectPartialAssault(PieceType type, int value, AssaultJustification justification) {
        return addExpected(type, value, false, CaptureType.ASSAULT, justification);
    }

    // 4. POWER
    public CaptureTestCase expectPower(PieceType type, int value, PowerJustification justification) {
        return addExpected(type, value, true, CaptureType.POWER, justification);
    }

    public CaptureTestCase expectPartialPower(PieceType type, int value, PowerJustification justification) {
        return addExpected(type, value, false, CaptureType.POWER, justification);
    }

    public CaptureTestCase expectNoCapture() {
        this.expectedCaptureCount = 0;
        this.expectedCaptures.clear();
        return this;
    }

    private CaptureTestCase addExpected(
            PieceType type,
            int value,
            boolean isWhole,
            CaptureType captureType,
            CaptureJustification justification
    ) {
        expectedCaptures.add(
                new ExpectedCapture(
                        type,
                        value,
                        isWhole,
                        captureType,
                        justification
                )
        );
        expectedCaptureCount++;
        return this;
    }

    public Arguments build() { return Arguments.of(this); }

    // --- Getters ---
    public PieceType getAttackerType() { return attackerType; }
    public int getAttackerValue() { return attackerValue; }
    public Position getAttackerPos() { return attackerPos; }
    public List<ComponentData> getAttackerComponents() { return attackerComponents; }
    public List<ExtraPiece> getOtherPieces() { return otherPieces; }
    public int getExpectedCaptureCount() { return expectedCaptureCount; }
    public List<ExpectedCapture> getExpectedCaptures() { return expectedCaptures; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Attacker: ").append(attackerType).append("(").append(attackerValue).append(") at ").append(attackerPos);
        if (!attackerComponents.isEmpty()) sb.append(" comps=").append(attackerComponents);
        sb.append(" | Targets: ");
        for (ExtraPiece p : otherPieces) {
            sb.append(p.type()).append("(").append(p.value()).append(") at ").append(p.pos()).append("; ");
        }
        sb.append(" => Expected: ").append(expectedCaptures);
        return sb.toString();
    }
}