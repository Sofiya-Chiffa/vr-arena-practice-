package model;

public class Session {
    private final String sessionId;
    private final String bookingId;
    private final String instructorId;
    private final String actualStart;
    private String actualEnd;
    private String status;

    public Session(String sessionId, String bookingId, String instructorId,
                   String actualStart, String actualEnd, String status) {
        this.sessionId = sessionId;
        this.bookingId = bookingId;
        this.instructorId = instructorId;
        this.actualStart = actualStart;
        this.actualEnd = actualEnd;
        this.status = status;
    }

    public Session(String sessionId, String bookingId, String instructorId,
                   String actualStart) {
        this.sessionId = sessionId;
        this.bookingId = bookingId;
        this.instructorId = instructorId;
        this.actualStart = actualStart;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getActualStart() {
        return actualStart;
    }

    public String getActualEnd() {
        return actualEnd;
    }

    public void setActualEnd(String actualEnd) {
        this.actualEnd = actualEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}