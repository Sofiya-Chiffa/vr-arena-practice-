import model.*;
import util.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        InMemoryStorage storage = new InMemoryStorage();

        storage.getScenarios().add(new Scenario("SC01", "Pirates of the Caribbean", 45, 6));
        storage.getSlots().add(new Slot("SL0001", "2026-11-10", "12:00", Slot.Status.BOOKED));
        storage.getSlots().add(new Slot("SL0002", "2026-12-10", "11:00", Slot.Status.FREE));
        storage.getSlots().add(new Slot("SL0003", "2026-12-10", "12:00", Slot.Status.FREE));
        storage.getSlots().add(new Slot("SL0004", "2026-12-10", "13:00", Slot.Status.FREE));
        storage.getSlots().add(new Slot("SL0005", "2026-12-11", "14:00", Slot.Status.FREE));
        storage.getBookings().add(new Booking("B0001", "C0001", "SL0001", "SC01", 3));
        storage.addClient(new Client("C0001", "Иванов", "Иван", "Иванович", "+7 999 123-45-67", "ivan@mail.ru"));
        storage.addEmployee(new Employee("E0001", "Петров", "Петр", "Петрович", "+7 999 765-43-21", "petr@mail.ru", Employee.Role.INSTRUCTOR, 50000));

        SlotService slotService = new SlotService();
        BookingService bookingService = new BookingService(storage);
        SessionService sessionService = new SessionService(storage);

        // нахождение свободных слотов на дату
        System.out.println("Свободные слоты на 2026-12-10:");
        slotService.findFreeSlots(storage.getSlots(), "2026-12-10", LocalDateTime.now())
                .forEach(s -> System.out.println("  " + s.describe()));

        // валидация заявки
        String validation = Validator.validateRequest(
                "SC01", "SL0003", 4, "+7 999 123-45-67",
                storage.getScenarios(), storage.getSlots(), storage.getBookings());
        System.out.println(validation);

        // бронирование слота
        Booking booking = bookingService.bookSlot("C0001", "SL0003", "SC01", 4);
        System.out.println(booking.describe());

        // Закрытие брони
        Session session = sessionService.closeBooking(
                booking.getBookingId(), "E0001", LocalDateTime.now(), "COMPLETED");
        System.out.println("Сеанс " + session.getSessionId() + " проведён: "
                + session.getActualStart() + " — " + session.getActualEnd());
    }
}