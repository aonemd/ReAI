package util;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Invoker {
    public static <T> T invoke(Object obj, String methodName) {
        Method method;
        T methodReturnObj = null;

        try {
            method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);

            methodReturnObj = (T) method.invoke(obj);
        } catch (SecurityException
                | NoSuchMethodException
                | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            System.out.println("Error:" + e);
        }

        return methodReturnObj;
    }
}
