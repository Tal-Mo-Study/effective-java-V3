package chapter_06.java.item39;

public class Imple {

    public static void run () {
        Class clazz = Annotation.class;
        for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Apple.class)) {
                System.out.println("Apple");
            }
            if (method.isAnnotationPresent(Orange.class)) {
                System.out.println("Orange");
            }
        }
    }
}
