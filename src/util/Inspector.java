package util;

import java.lang.reflect.Field;
import java.lang.StringBuilder;

public class Inspector {
    public static void inspect(Object obj) {
        System.out.printf("%s%n", obj);

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                String name = field.getName();
                Object value = field.get(obj);

                System.out.printf("%s: %s%n", name, value.toString());
            } catch (IllegalAccessException e) {
                System.out.println("Inspection Error: " + e);
            }
        }
    }

    public static String inspectToString(Object obj) {
        StringBuilder inspectedObjectStringBuilder = new StringBuilder();
        inspectedObjectStringBuilder
            .append("---- ")
            .append(obj)
            .append(" ----")
            .append("\n");

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                String name = field.getName();
                Object value = field.get(obj);

                inspectedObjectStringBuilder
                    .append(name)
                    .append(": ")
                    .append(value.toString())
                    .append("\n");
            } catch (IllegalAccessException e) {
                System.out.println("Inspection Error: " + e);
            }
        }

        return inspectedObjectStringBuilder.toString();
    }
}
