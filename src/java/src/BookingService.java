import model.Booking;
import model.Slot;
import util.InMemoryStorage;

import java.util.List;

public class BookingService {
    private final InMemoryStorage storage;

    public BookingService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Booking bookSlot(String clientId, String slotId, String scenarioId, int participants) {
        Slot slot = storage.slots.stream()
                .filter(s -> s.getSlotId().equals(slotId))
                .findFirst().orElse(null);
        if (slot == null) throw new IllegalArgumentException("Слот не найден");
        if (slot.getStatus() != Slot.Status.FREE) throw new IllegalStateException("Слот уже занят");

        String bookingId = String.format("B%04d", storage.bookings.size() + 1);
        Booking booking = new Booking(bookingId, clientId, slotId, scenarioId, participants);
        storage.bookings.add(booking);
        slot.setStatus(Slot.Status.BOOKED);
        return booking;
    }
}