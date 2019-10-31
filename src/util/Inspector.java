package util;

import java.lang.reflect.Field;

public class Inspector {
    public static void inspect(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                String name = field.getName();
                Object value = field.get(obj);

                System.out.printf("%s: %s%n", name, value);
            } catch (IllegalAccessException e) {
                System.out.println("Inspection Error: " + e);
            }
        }
    }
}
