package eu.nicosworld.rithmoEngine.math.explain;

import eu.nicosworld.rithmoEngine.math.ProgressionMask;
import eu.nicosworld.rithmoEngine.math.ProgressionTriplet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgressionExplainTest {

    @Test
    void should_explain_arithmetic_progression_in_french_by_default() {
        ProgressionTriplet t = new ProgressionTriplet(
                1, 4, 7,
                ProgressionMask.ARITHMETIC
        );

        String result = ProgressionExplain.explain(t);

        assertTrue(result.contains("progression arithmétique"));
        assertTrue(result.contains("raison 3"));
    }

    @Test
    void should_explain_geometric_progression_in_french() {
        ProgressionTriplet t = new ProgressionTriplet(
                2, 6, 18,
                ProgressionMask.GEOMETRIC
        );

        String result = ProgressionExplain.explain(t);

        assertTrue(result.contains("progression géométrique"));
        assertTrue(result.contains("raison 3"));
    }

    @Test
    void should_explain_harmonic_progression_in_french() {
        ProgressionTriplet t = new ProgressionTriplet(
                2, 3, 6,
                ProgressionMask.HARMONIC
        );

        String result = ProgressionExplain.explain(t);

        assertTrue(result.contains("progression harmonique"));
    }

    @Test
    void should_explain_arithmetic_progression_in_english() {
        ProgressionTriplet t = new ProgressionTriplet(
                1, 4, 7,
                ProgressionMask.ARITHMETIC
        );

        String result = ProgressionExplain.explain(t, ProgressionLanguage.EN);

        assertTrue(result.contains("arithmetic progression"));
        assertTrue(result.contains("difference 3"));
    }

    @Test
    void should_explain_geometric_progression_in_english() {
        ProgressionTriplet t = new ProgressionTriplet(
                2, 6, 18,
                ProgressionMask.GEOMETRIC
        );

        String result = ProgressionExplain.explain(t, ProgressionLanguage.EN);

        assertTrue(result.contains("geometric progression"));
        assertTrue(result.contains("ratio 3"));
    }

    @Test
    void should_combine_multiple_progressions() {
        ProgressionTriplet t = new ProgressionTriplet(
                1, 4, 7,
                ProgressionMask.ARITHMETIC | ProgressionMask.HARMONIC
        );

        String result = ProgressionExplain.explain(t);

        System.out.println(result);

        assertTrue(result.contains("progression arithmétique"));
        assertTrue(result.contains("progression harmonique"));
    }

    @Test
    void should_return_base_triplet_values_in_output() {
        ProgressionTriplet t = new ProgressionTriplet(
                3, 6, 12,
                ProgressionMask.GEOMETRIC
        );

        String result = ProgressionExplain.explain(t);

        assertTrue(result.startsWith("3, 6, 12"));
    }
}