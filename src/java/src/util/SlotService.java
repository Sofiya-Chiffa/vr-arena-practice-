package util;

import model.Slot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// поиск свободных слотов на дату
public class SlotService {

    public List<Slot> findFreeSlots(List<Slot> slots, String targetDate, LocalDateTime now) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots) {
            if (!slot.getDate().equals(targetDate)) continue;
            if (!slot.isAvailable()) continue;
            if (!slot.startDateTime().isAfter(now)) continue;
            result.add(slot);
        }
        result.sort(Comparator.comparing(Slot::getTimeStart));
        return result;
    }
}