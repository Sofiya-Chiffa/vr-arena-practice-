package model;

public class Employee {
    public enum Role {ADMIN, INSTRUCTOR, TECHNICIAN}

    private final String employeeId;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String phone;
    private final String email;
    private final Role position;
    private final float salary;

    public Employee(String employeeId, String lastName, String firstName, String middleName, String phone, String email, Role position, float salary) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
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

    public float getSalary() {
        return salary;
    }

    public Role getRole() {
        return position;
    }
}