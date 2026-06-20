package eu.nicosworld.rithmo.engine.math;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

class ProgressionEngineTechnicalTest {

  @Test
  void should_cover_progression_mask_private_constructor() throws Exception {
    // Force la couverture du constructeur privé de ProgressionMask
    Constructor<ProgressionMask> constructor = ProgressionMask.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException exception =
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    assertInstanceOf(AssertionError.class, exception.getCause());
    assertEquals("No instances", exception.getCause().getMessage());
    fail();
  }

  @Test
  void should_cover_progression_type_enum() {
    // Force la couverture des méthodes implicites de l'enum ProgressionType
    assertNotNull(ProgressionType.values());
    assertEquals(ProgressionType.ARITHMETIC, ProgressionType.valueOf("ARITHMETIC"));
  }

  @Test
  void should_cover_engine_builder_and_mask_filtering() {
    // 1. On part d'un moteur vide (si tu as un constructeur de base ou une factory à 0)
    // Note : Adapte l'initialisation selon tes constructeurs disponibles.
    // Ici on va tester l'enchaînement des méthodes rouges :
    ProgressionEngine engine = ProgressionEngine.fast();

    // 2. On crée des moteurs partiels pour forcer les "if" à être FALSE dans detect()
    ProgressionEngine arithmeticOnly = ProgressionEngine.empty().allowArithmetic();
    ProgressionEngine geometricOnly = ProgressionEngine.empty().allowGeometric();
    ProgressionEngine harmonicOnly = ProgressionEngine.empty().allowHarmonic();

    // Un triplet purement arithmétique : 2, 4, 6
    int[] values = {2, 4, 6};

    // L'arithmétique doit le trouver
    assertTrue(arithmeticOnly.detect(values).isAny());

    // Le géométrique seul doit ignorer le bloc arithmétique (le if passe à false -> JAUNE PASSE AU
    // VERT)
    assertFalse(geometricOnly.detect(values).isAny());

    // L'harmonique seul doit aussi l'ignorer
    assertFalse(harmonicOnly.detect(values).isAny());
  }
}
