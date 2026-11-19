package model;

// класс брони
public class Booking {
    public enum Status {ACTIVE, CLOSED, CANCELLED}

    private final String bookingId;
    private final String clientId;
    private final String slotId;
    private final String scenarioId;
    private final int participantsCount;
    private Status status;

    public Booking(String bookingId, String clientId, String slotId,
                   String scenarioId, int participantsCount) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.slotId = slotId;
        this.scenarioId = scenarioId;
        this.participantsCount = participantsCount;
        this.status = Status.ACTIVE;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    // Операция: закрыть бронь
    public void close() {
        if (!isActive()) throw new IllegalStateException("Бронь уже закрыта или отменена");
        this.status = Status.CLOSED;
    }

    // Операция: отменить бронь
    public void cancel() {
        if (!isActive()) throw new IllegalStateException("Бронь уже закрыта или отменена");
        this.status = Status.CANCELLED;
    }

    public String describe() {
        return "Бронь " + bookingId + " (клиент " + clientId + ", слот " + slotId
                + ", статус " + status + ")";
    }
}