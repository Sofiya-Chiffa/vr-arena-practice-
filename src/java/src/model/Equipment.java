package model;

// класс оборудования
public class Equipment {
    public enum State {WORKING, MAINTENANCE, BROKEN}

    private final String equipmentId;
    private final String serialNumber;
    private State state;

    public Equipment(String equipmentId, String serialNumber, State state) {
        this.equipmentId = equipmentId;
        this.serialNumber = serialNumber;
        this.state = state;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isAvailable() {
        return state == State.WORKING;
    }

}