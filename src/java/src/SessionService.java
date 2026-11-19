import model.Booking;
import model.Session;
import util.InMemoryStorage;
import util.DateTimeUtil;

import java.time.LocalDateTime;

public class SessionService {
    private final InMemoryStorage storage;

    public SessionService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Session closeBooking(String bookingId, String instructorId, LocalDateTime now, String status) {
        Booking booking = storage.bookings.stream()
                .filter(b -> b.getBookingId().equals(bookingId))
                .findFirst().orElse(null);
        if (booking == null) throw new IllegalArgumentException("Бронь не найдена");
        if (booking.getStatus() != Booking.Status.ACTIVE)
            throw new IllegalStateException("Бронь уже закрыта или отменена");

        booking.setStatus(Booking.Status.CLOSED);
        String sessionId = String.format("S%04d", storage.sessions.size() + 1);
        Session session = new Session(
                sessionId,
                bookingId,
                instructorId,
                DateTimeUtil.format(now),
                DateTimeUtil.format(now.plusMinutes(60)),
                status
        );
        storage.sessions.add(session);
        return session;
    }
}