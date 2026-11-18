public class Client {
    private final String clientId;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String phone;
    private final String email;

    public Client(String clientId, String lastName, String firstName, String middleName, String phone, String email) {
        this.clientId = clientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
    }

    public String getClientId() {
        return clientId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}