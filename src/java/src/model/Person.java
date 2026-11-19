package model;

// базовый класс персоны
public abstract class Person {
    private final String id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String phone;
    private final String email;

    public Person(String id, String lastName, String firstName, String middleName, String phone, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
    }

    public String getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    // Разное поведение для наследников
    public abstract String getDisplayInfo();
}