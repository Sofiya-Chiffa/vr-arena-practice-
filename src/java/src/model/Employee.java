package model;

// класс работника
public class Employee extends Person {
    public enum Role { ADMIN, INSTRUCTOR, TECHNICIAN }

    private final Role position;
    private final float salary;

    public Employee(String employeeId, String lastName, String firstName, String middleName,
                    String phone, String email, Role position, float salary) {
        super(employeeId, lastName, firstName, middleName, phone, email);
        this.position = position;
        this.salary = salary;
    }

    public Role getRole() { return position; }
    public float getSalary() { return salary; }

    @Override
    public String getDisplayInfo() {
        return "Сотрудник: " + getLastName() + " " + getFirstName() + ", должность: " + position;
    }
}