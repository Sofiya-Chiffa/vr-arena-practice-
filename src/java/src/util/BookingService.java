package util;

import model.*;

// операция бронирования:
public class BookingService {
    private final InMemoryStorage storage;

    public BookingService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public Booking bookSlot(String clientId, String slotId, String scenarioId, int participants) {
        Client client = storage.getClient(clientId);
        if (client == null) throw new IllegalArgumentException("Клиент не найден");
        Slot slot = storage.getSlots().stream()
                .filter(s -> s.getSlotId().equals(slotId))
                .findFirst().orElse(null);
        if (slot == null) throw new IllegalArgumentException("Слот не найден");
        slot.book();
        String bookingId = String.format("B%04d", storage.getBookings().size() + 1);
        Booking booking = new Booking(bookingId, clientId, slotId, scenarioId, participants);
        storage.getBookings().add(booking);
        return booking;
    }
}