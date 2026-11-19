package util;

import model.Booking;
import model.Session;

import java.time.LocalDateTime;

// операция закрытия брони
public class SessionService {
    private final InMemoryStorage storage;

    public SessionService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Session closeBooking(String bookingId, String instructorId, LocalDateTime now, String status) {
        Booking booking = storage.getBookings().stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst().orElse(null);
        if (booking == null) throw new IllegalArgumentException("Бронь не найдена");

        booking.close();

        String sessionId = String.format("S%04d", storage.getSessions().size() + 1);
        Session session = new Session(
                sessionId, bookingId, instructorId,
                DateTimeUtil.format(now),
                DateTimeUtil.format(now.plusMinutes(60)),
                status
        );
        storage.getSessions().add(session);
        return session;
    }
}