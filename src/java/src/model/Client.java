package model;

// класс клиента
public class Client extends Person {
    private final String clientId;

    public Client(String clientId, String lastName, String firstName, String middleName, String phone, String email) {
        super(clientId, lastName, firstName, middleName, phone, email);
        this.clientId = clientId;
    }

    public String getClientId() { return clientId; }

    @Override
    public String getDisplayInfo() {
        return "Клиент: " + getLastName() + " " + getFirstName() + " (тел: " + getPhone() + ")";
    }
}