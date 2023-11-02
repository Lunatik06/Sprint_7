package org.example.courier;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;

public class CourierGenerator {

    static Faker faker = new Faker();

    public static Courier generic() {
        return new Courier("raikov", "1234", "saske");
    }

    // UPD: добавил пользователю с рандомными данными получение данных из java-faker
    public static Courier random() {
        return new Courier(faker.name().username(), faker.number().digits(6), faker.name().firstName());
    }

    public static Courier withoutLogin() {
        return new Courier(null, "1234", "saske");
    }

    public static Courier withoutPassword() {
        return new Courier("raikov" + LocalDateTime.now(), null, "saske");
    }

    public static Courier withoutFirstname() {
        return new Courier("raikov" + LocalDateTime.now(), "1234", null);
    }

    public static Courier withWrongLogin() {
        return new Courier("WrongLogin", "1234", null);
    }

    public static Courier withWrongPassword() {
        return new Courier("raikov", "WrongPassword", null);
    }
}
