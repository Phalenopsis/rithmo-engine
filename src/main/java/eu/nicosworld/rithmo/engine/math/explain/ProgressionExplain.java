package eu.nicosworld.rithmo.engine.math.explain;

import eu.nicosworld.rithmo.engine.math.ProgressionTriplet;

/**
 * Utility class that converts a {@link ProgressionTriplet} into a human-readable explanation.
 *
 * <p>Default language is French. English can be requested explicitly.
 *
 * <p>This class is designed to be easily extensible for future languages.
 */
public final class ProgressionExplain {

  private ProgressionExplain() {
    // utility class
  }

  /**
   * Explain a triplet in French by default.
   *
   * @param t progression triplet
   * @return human-readable explanation in French
   */
  public static String explain(ProgressionTriplet t) {
    return explain(t, ProgressionLanguage.FR);
  }

  /**
   * Explain a triplet in a given language.
   *
   * @param t progression triplet
   * @param lang target language
   * @return human-readable explanation
   */
  public static String explain(ProgressionTriplet t, ProgressionLanguage lang) {

    String base = formatTriplet(t, lang);

    StringBuilder sb = new StringBuilder(base);

    if (t.isArithmetic()) {
      sb.append(" → ").append(arithmetic(t, lang));
    }

    if (t.isGeometric()) {
      sb.append(" → ").append(geometric(t, lang));
    }

    if (t.isHarmonic()) {
      sb.append(" → ").append(harmonic(t, lang));
    }

    return sb.toString();
  }

  private static String formatTriplet(ProgressionTriplet t, ProgressionLanguage lang) {
    return t.min() + ", " + t.mid() + ", " + t.max();
  }

  private static String arithmetic(ProgressionTriplet t, ProgressionLanguage lang) {
    int min = t.min();
    int mid = t.mid();
    int diff = mid - min;

    return switch (lang) {
      case FR -> "progression arithmétique (raison " + diff + ")";
      case EN -> "arithmetic progression (difference " + diff + ")";
    };
  }

  private static String geometric(ProgressionTriplet t, ProgressionLanguage lang) {
    int min = t.min();
    int mid = t.mid();

    int ratio = mid / min; // safe assumption in game context

    return switch (lang) {
      case FR -> "progression géométrique (raison " + ratio + ")";
      case EN -> "geometric progression (ratio " + ratio + ")";
    };
  }

  private static String harmonic(ProgressionTriplet t, ProgressionLanguage lang) {
    return switch (lang) {
      case FR -> "progression harmonique";
      case EN -> "harmonic progression";
    };
  }
}
