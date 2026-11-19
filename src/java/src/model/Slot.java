package model;

import util.DateTimeUtil;

import java.time.LocalDateTime;

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

    public String describe() {
        return "Слот " + slotId + " (" + date + " " + timeStart + ", " + status + ")";
    }
}