package chapter_06.java.item39;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests3 {

    public static void main(String[] args) {
        int tests = 0;
        int passed = 0;
        Class testClass = Sample2.class;

        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(RepeatableExceptionTest.class) || m.isAnnotationPresent(ExceptionTestContainer.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    RepeatableExceptionTest[] excTests = m.getAnnotationsByType(RepeatableExceptionTest.class);
                    int oldPassed = passed;
                    for (RepeatableExceptionTest excTest : excTests) {
                        if (excTest.value().isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s%n", m, excTests, exc);
                    }
                } catch (Exception exc) {
                    System.out.println("잘못 사용한 @ExceptionTest: " + m);
                }
            }
        }
    }
}
