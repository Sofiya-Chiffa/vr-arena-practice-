import model.*;
import util.DateTimeUtil;

import java.util.List;

public final class Validator {
    private Validator() {
    }

    public static String validateRequest(String scenarioId, String slotId,
                                         int participants, String phone,
                                         List<Scenario> scenarios,
                                         List<Slot> slots,
                                         List<Booking> bookings) {
        if (scenarioId == null || scenarioId.isBlank()) return "Ошибка: пустое поле 'scenario_id'";
        if (slotId == null || slotId.isBlank()) return "Ошибка: пустое поле 'slot_id'";
        if (phone == null || phone.isBlank()) return "Ошибка: пустое поле 'phone'";

        Scenario scenario = scenarios.stream()
                .filter(s -> s.getScenarioId().equals(scenarioId))
                .findFirst().orElse(null);
        if (scenario == null) return "Ошибка: сценарий " + scenarioId + " не найден";

        Slot target = slots.stream()
                .filter(s -> s.getSlotId().equals(slotId))
                .findFirst().orElse(null);
        if (target == null) return "Ошибка: слот " + slotId + " не найден";
        if (target.getStatus() != Slot.Status.FREE) return "Ошибка: слот " + slotId + " уже занят";

        var targetStart = target.startDateTime();
        var targetEnd = targetStart.plusMinutes(scenario.getDurationMin() + DateTimeUtil.PREP_MINUTES);

        for (Booking b : bookings) {
            if (b.getStatus() != Booking.Status.ACTIVE) continue;
            Slot bookedSlot = slots.stream()
                    .filter(s -> s.getSlotId().equals(b.getSlotId()))
                    .findFirst().orElse(null);
            if (bookedSlot == null || !bookedSlot.getDate().equals(target.getDate())) continue;

            Scenario bookedScenario = scenarios.stream()
                    .filter(s -> s.getScenarioId().equals(b.getScenarioId()))
                    .findFirst().orElse(null);
            if (bookedScenario == null) continue;

            var bookedStart = bookedSlot.startDateTime();
            var bookedEnd = bookedStart.plusMinutes(
                    bookedScenario.getDurationMin() + DateTimeUtil.PREP_MINUTES);

            boolean overlap = !(targetEnd.isBefore(bookedStart) || bookedEnd.isBefore(targetStart)
                    || targetEnd.equals(bookedStart) || bookedEnd.equals(targetStart));
            if (!(targetEnd.isBefore(bookedStart) || bookedEnd.isBefore(targetStart))) {
                if (targetEnd.isAfter(bookedStart) && bookedEnd.isAfter(targetStart)) {
                    return "Ошибка: желаемое время пересекается с бронью " + b.getBookingId()
                            + " (слот " + bookedSlot.getSlotId() + ")";
                }
            }
        }

        if (participants > scenario.getMaxParticipants()) {
            return "Ошибка: количество участников " + participants
                    + " превышает допустимый максимум " + scenario.getMaxParticipants();
        }

        String digits = phone.replaceAll("\\D", "");
        if (digits.length() != 11) return "Ошибка: некорректный формат номера телефона";

        return "Заявка корректна";
    }
}