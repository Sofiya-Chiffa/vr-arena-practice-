import model.*;
import util.*;
import util.InMemoryStorage;

import java.time.LocalDateTime;
import java.util.List;

// Консольный интерфейс
public class ConsoleApp {

    private final InMemoryStorage storage;
    private final SlotService slotService;
    private final BookingService bookingService;
    private final SessionService sessionService;

    public ConsoleApp() {
        this.storage = new InMemoryStorage();
        this.slotService = new SlotService();
        this.bookingService = new BookingService(storage);
        this.sessionService = new SessionService(storage);
        seedDemoData();
    }

    // Демо-данные
    private void seedDemoData() {
        storage.getScenarios().add(new Scenario("SC01", "Pirates of the Caribbean", 45, 6));
        storage.getSlots().add(new Slot("SL0003", "2026-12-10", "12:00", Slot.Status.FREE));
        storage.getSlots().add(new Slot("SL0004", "2026-12-10", "13:30", Slot.Status.FREE));
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: java ConsoleApp {slots|check|book|close} ...");
            return;
        }
        new ConsoleApp().run(args);
    }

    private void run(String[] args) {
        String cmd = args[0];
        switch (cmd) {
            case "slots" -> cmdSlots(args.length > 1 ? args[1] : LocalDateTime.now().toLocalDate().toString());
            case "check" -> cmdCheck();
            case "book"  -> cmdBook();
            case "close" -> cmdClose(
                    args.length > 1 ? args[1] : "B0001",
                    args.length > 2 ? args[2] : "E0001");
            default -> System.out.println("Неизвестная команда: " + cmd);
        }
    }

    // свободные слоты
    private void cmdSlots(String targetDate) {
        List<Slot> free = slotService.findFreeSlots(storage.getSlots(), targetDate, LocalDateTime.now());
        if (free.isEmpty()) {
            System.out.println("На " + targetDate + " свободных слотов нет.");
            return;
        }
        System.out.println("Свободные слоты на " + targetDate + ":");
        for (Slot s : free) {
            System.out.println("  " + s.getSlotId() + "  " + s.getTimeStart());
        }
    }

    // валидация заявки
    private void cmdCheck() {
        String msg = Validator.validateRequest(
                "SC01", "SL0003", 4, "+7 999 123-45-67",
                storage.getScenarios(), storage.getSlots(), storage.getBookings());
        System.out.println(msg);
    }

    // бронирование
    private void cmdBook() {
        try {
            Booking booking = bookingService.bookSlot("C0001", "SL0003", "SC01", 4);
            System.out.println("Бронь создана");
            System.out.println(" " + booking.describe());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // закрытие брони
    private void cmdClose(String bookingId, String instructorId) {
        try {
            Session session = sessionService.closeBooking(
                    bookingId, instructorId, LocalDateTime.now(), "COMPLETED");
            System.out.println("Сеанс проведён, услуга оказана");
            System.out.println(" Сеанс " + session.getSessionId()
                    + " (" + session.getActualStart() + " — " + session.getActualEnd() + ")");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}