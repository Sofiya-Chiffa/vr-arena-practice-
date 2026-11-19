package model;

import util.DateTimeUtil;

import java.time.LocalDateTime;

// класс слота
public class Slot {
    public enum Status {FREE, BOOKED, MAINTENANCE, BLOCKED}

    private final String slotId;
    private final String date;
    private final String timeStart;
    private Status status;

    public Slot(String slotId, String date, String timeStart, Status status) {
        this.slotId = slotId;
        this.date = date;
        this.timeStart = timeStart;
        this.status = status;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getDate() {
        return date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime startDateTime() {
        return DateTimeUtil.parse(date, timeStart);
    }

    public boolean isFree() {
        return status == Status.FREE;
    }

    // Операция: зафиксировать слот за клиентом
    public void book() {
        if (!isFree()) throw new IllegalStateException("Слот " + slotId + " уже занят");
        this.status = Status.BOOKED;
    }

    // Операция: освободить слот
    public void release() {
        this.status = Status.FREE;
    }

    // Алгоритм: пересечение с другим слотом с учётом длительностей сценариев и подготовки
    public boolean overlapsWith(Slot other, Scenario thisScenario, Scenario otherScenario) {
        if (!date.equals(other.getDate())) return false;
        LocalDateTime thisStart = startDateTime();
        LocalDateTime thisEnd = thisStart.plusMinutes(thisScenario.totalDurationWithPrep());
        LocalDateTime otherStart = other.startDateTime();
        LocalDateTime otherEnd = otherStart.plusMinutes(otherScenario.totalDurationWithPrep());
        return thisStart.isBefore(otherEnd) && otherStart.isBefore(thisEnd);
    }

    public String describe() {
        return "Слот " + slotId + " (" + date + " " + timeStart + ", " + status + ")";
    }
}