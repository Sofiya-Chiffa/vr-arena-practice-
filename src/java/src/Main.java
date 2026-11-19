import model.*;
import util.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InMemoryStorage storage = new InMemoryStorage();

        storage.scenarios.add(new Scenario("SC01", "Pirates of the Caribbean", 45, 6));
        storage.slots.add(new Slot("SL0003", "2026-11-10", "12:00", Slot.Status.FREE));
        storage.slots.add(new Slot("SL0004", "2026-11-10", "13:30", Slot.Status.FREE));

        SlotService slotService = new SlotService();
        BookingService bookingService = new BookingService(storage);
        SessionService sessionService = new SessionService(storage);

        // 1. Свободные слоты
        System.out.println("Свободные слоты на 2026-11-10:");
        slotService.findFreeSlots(storage.slots, "2026-11-10", LocalDateTime.now())
                .forEach(s -> System.out.println("  " + s.describe()));

        // 2. Проверка заявки
        String validation = Validator.validateRequest(
                "SC01", "SL0003", 4, "+7 999 123-45-67",
                storage.scenarios, storage.slots, storage.bookings);
        System.out.println(validation);

        // 3. Бронирование
        Booking booking = bookingService.bookSlot("C0001", "SL0003", "SC01", 4);
        System.out.println(booking.describe());

        // 4. Закрытие брони
        Session session = sessionService.closeBooking(booking.getBookingId(), "E0001", LocalDateTime.now(), "COMPLETED");
        System.out.println("Сеанс " + session.getSessionId() + " проведён: "
                + session.getActualStart() + " — " + session.getActualEnd());

    }
}