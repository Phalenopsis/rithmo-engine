package eu.nicosworld.rithmo.engine.threat.model;

import java.util.List;

public record RegularThreats(List<AssistedThreat> assistedThreats, List<SoloThreat> soloThreats) {}
