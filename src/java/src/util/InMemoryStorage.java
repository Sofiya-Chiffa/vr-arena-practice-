package util;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage {
    private final List<Scenario> scenarios = new ArrayList<>();
    private final List<Slot> slots = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Session> sessions = new ArrayList<>();
    private final List<Client> clients = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void addScenario(Scenario s) {
        scenarios.add(s);
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void addSlot(Slot s) {
        slots.add(s);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking b) {
        bookings.add(b);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session s) {
        sessions.add(s);
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client c) {
        clients.add(c);
    }

    public Client getClient(String clientId) {
        for (Client c : clients) {
            if (c.getClientId().equals(clientId)) return c;
        }
        return null;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }
}