package util;
import model.*;

import java.util.List;

// проверка корректности заявки
public final class Validator {
    private Validator() {}

    public static String validateRequest(String scenarioId, String slotId,
                                         int participants, String phone,
                                         List<Scenario> scenarios,
                                         List<Slot> slots,
                                         List<Booking> bookings) {
        if (scenarioId == null || scenarioId.isBlank())
            return "Ошибка: пустое поле 'scenario_id'";
        if (slotId == null || slotId.isBlank())
            return "Ошибка: пустое поле 'slot_id'";
        if (phone == null || phone.isBlank())
            return "Ошибка: пустое поле 'phone'";

        Scenario scenario = scenarios.stream()
                .filter(s -> s.getScenarioId().equals(scenarioId))
                .findFirst().orElse(null);
        if (scenario == null) return "Ошибка: сценарий " + scenarioId + " не найден";

        Slot targetSlot = slots.stream()
                .filter(s -> s.getSlotId().equals(slotId))
                .findFirst().orElse(null);
        if (targetSlot == null) return "Ошибка: слот " + slotId + " не найден";
        if (!targetSlot.isAvailable()) return "Ошибка: слот " + slotId + " уже занят";

        for (Slot slot : slots) {
            if (!slot.getDate().equals(targetSlot.getDate())) continue;
            if (slot.isAvailable()) continue;
            for (Booking booking : bookings) {
                if (!slot.getSlotId().equals(booking.getSlotId())) continue;
                if (!booking.isActive()) continue;
                Scenario bookingScenario = scenarios.stream()
                        .filter(s -> s.getScenarioId().equals(booking.getScenarioId()))
                        .findFirst().orElse(null);
                if (bookingScenario == null) continue;
                if (targetSlot.overlapsWith(slot, scenario, bookingScenario)) {
                    return "Ошибка: желаемое время пересекается с бронью "
                            + booking.getBookingId() + " (слот " + slot.getSlotId() + ")";
                }
            }
        }

        if (!scenario.canHost(participants)) {
            return "Ошибка: количество участников " + participants
                    + " должно быть от 1 до " + scenario.getMaxParticipants();
        }

        String digits = phone.replaceAll("\\D", "");
        if (digits.length() != 11) return "Ошибка: некорректный формат номера телефона";
        return "Заявка корректна";
    }
}