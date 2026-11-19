package util;

import model.Booking;
import model.Slot;

// операция бронирования:
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
        slot.book();
        String bookingId = String.format("B%04d", storage.bookings.size() + 1);
        Booking booking = new Booking(bookingId, clientId, slotId, scenarioId, participants);
        storage.bookings.add(booking);
        return booking;
    }
}