import model.Slot;
import util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SlotService {
    public List<Slot> findFreeSlots(List<Slot> slots, String targetDate, LocalDateTime now) {
        return slots.stream()
                .filter(s -> s.getDate().equals(targetDate))
                .filter(s -> s.getStatus() == Slot.Status.FREE)
                .filter(s -> !s.startDateTime().isBefore(now) && !s.startDateTime().isEqual(now))
                .sorted(Comparator.comparing(Slot::getTimeStart))
                .collect(Collectors.toList());
    }
}