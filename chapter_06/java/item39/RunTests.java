package chapter_06.java.item39;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

    public static void main(String[] args) {
        int tests = 0;
        int passed = 0;
        Class testClass = Sample.class;

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++; //4
                try {
                    m.invoke(null);
                    passed++; // 1
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " 실패: " + exc); // 2
                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @Test: " + m); // 1

                }
            }
        }
        System.out.printf("성공: %d, 실패: %d%n", passed, tests - passed);
    }
}
