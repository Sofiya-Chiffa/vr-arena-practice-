package util;

import model.*;

import java.util.ArrayList;
import java.util.List;

// класс хранения коллекций сущностей
public class InMemoryStorage {
    public final List<Scenario> scenarios = new ArrayList<>();
    public final List<Slot> slots = new ArrayList<>();
    public final List<Booking> bookings = new ArrayList<>();
    public final List<Session> sessions = new ArrayList<>();
}