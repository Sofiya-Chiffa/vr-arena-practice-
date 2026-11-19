package model;

// класс использования оборудования в сеансах
public class EquipmentUsage {
    private final String equipmentId;
    private final String sessionId;

    public EquipmentUsage(String equipmentId, String sessionId) {
        this.equipmentId = equipmentId;
        this.sessionId = sessionId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getSessionId() {
        return sessionId;
    }
}