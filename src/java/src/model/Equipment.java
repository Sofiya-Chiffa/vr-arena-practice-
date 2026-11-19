package model;

// класс оборудования
public class Equipment implements StatusAware {
    public enum State { WORKING, MAINTENANCE, BROKEN }

    private final String equipmentId;
    private final String serialNumber;
    private State state;

    public Equipment(String equipmentId, String serialNumber, State state) {
        this.equipmentId = equipmentId;
        this.serialNumber = serialNumber;
        this.state = state;
    }

    public String getEquipmentId() { return equipmentId; }
    public String getSerialNumber() { return serialNumber; }
    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    @Override
    public boolean isAvailable() {
        return state == State.WORKING; // Оборудование доступно только если WORKING
    }

    @Override
    public String getStatusDescription() {
        return "Оборудование " + equipmentId + " находится в состоянии: " + state;
    }
}