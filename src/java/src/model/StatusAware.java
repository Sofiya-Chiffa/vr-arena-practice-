package model;

public interface StatusAware {
    // Проверка, находится ли объект в активном/рабочем состоянии
    boolean isAvailable();

    // Получение текстового описания статуса
    String getStatusDescription();
}