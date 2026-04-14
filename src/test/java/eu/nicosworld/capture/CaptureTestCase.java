package eu.nicosworld.capture;

import eu.nicosworld.model.PieceType;
import eu.nicosworld.model.PlayerColor;
import eu.nicosworld.model.Position;
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
    private CaptureType expectedType;

    private final List<ExpectedCapture> expectedCaptures = new ArrayList<>();

    private CaptureTestCase(PieceType type, int value, int x, int y) {
        this.attackerType = type;
        this.attackerValue = value;
        this.attackerPos = new Position(x, y);
    }

    public record ComponentData(PieceType type, int value) {}
    public record ExtraPiece(PieceType type, int value, PlayerColor color, Position pos, List<ComponentData> components) {}

    // Entry Points
    public static CaptureTestCase blackCircleAt(int val, int x, int y) { return new CaptureTestCase(PieceType.CIRCLE, val, x, y); }
    public static CaptureTestCase blackTriangleAt(int val, int x, int y) { return new CaptureTestCase(PieceType.TRIANGLE, val, x, y); }
    public static CaptureTestCase blackSquareAt(int val, int x, int y) { return new CaptureTestCase(PieceType.SQUARE, val, x, y); }
    public static CaptureTestCase blackPyramidAt(int x, int y) { return new CaptureTestCase(PieceType.PYRAMID, 0, x, y); }
    public static CaptureTestCase whitePyramidAt(int x, int y) { return new CaptureTestCase(PieceType.PYRAMID, 0, x, y); } // For symetry in tests

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

    public CaptureTestCase withObstacleAt(int x, int y) { return withBlackAlly(PieceType.CIRCLE, 999, x, y); }

    public CaptureTestCase expectEncounter() { return expectEncounterCount(1); }
    public CaptureTestCase expectEncounterCount(int count) {
        this.expectedCaptureCount = count;
        this.expectedType = CaptureType.ENCOUNTER;
        return this;
    }
    public CaptureTestCase expectNoCapture() { this.expectedCaptureCount = 0; return this; }

    public CaptureTestCase expectCapture(PieceType type, int value) {
        this.expectedCaptures.add(new ExpectedCapture(type, value, true));
        this.expectedCaptureCount++;
        this.expectedType = CaptureType.ENCOUNTER;
        return this;
    }

    public CaptureTestCase expectPartialCapture(PieceType type, int value) {
        this.expectedCaptures.add(new ExpectedCapture(type, value, false));
        this.expectedCaptureCount++;
        this.expectedType = CaptureType.ENCOUNTER;
        return this;
    }

    public Arguments build() { return Arguments.of(this); }

    // Getters
    public PieceType getAttackerType() { return attackerType; }
    public int getAttackerValue() { return attackerValue; }
    public Position getAttackerPos() { return attackerPos; }
    public List<ComponentData> getAttackerComponents() { return attackerComponents; }
    public List<ExtraPiece> getOtherPieces() { return otherPieces; }
    public int getExpectedCaptureCount() { return expectedCaptureCount; }
    public CaptureType getExpectedType() { return expectedType; }

    public List<ExpectedCapture> getExpectedCaptures() {
        return expectedCaptures;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Attacker: ")
                .append(attackerType)
                .append("(").append(attackerValue).append(")")
                .append(" at ").append(attackerPos);

        if (!attackerComponents.isEmpty()) {
            sb.append(" components=").append(attackerComponents);
        }

        sb.append(" | Targets: ");

        for (ExtraPiece p : otherPieces) {
            sb.append(p.type())
                    .append("(").append(p.value()).append(")")
                    .append(" ").append(p.color())
                    .append(" at ").append(p.pos());

            if (!p.components().isEmpty()) {
                sb.append(" comps=").append(p.components());
            }

            sb.append(" ; ");
        }

        sb.append(" => expected=")
                .append(expectedCaptureCount)
                .append(" ").append(expectedType);

        if (!expectedCaptures.isEmpty()) {
            sb.append(" details=").append(expectedCaptures);
        }

        return sb.toString();
    }
}