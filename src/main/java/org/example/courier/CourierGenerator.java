package org.example.courier;

import java.time.LocalDateTime;

public class CourierGenerator {
    public static Courier generic() {
        return new Courier("raikov", "1234", "saske");
    }

    public static Courier random() {
        return new Courier("raikov" + LocalDateTime.now(), "1234", "saske");
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
