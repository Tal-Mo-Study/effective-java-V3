package chapter_06.java.item39;

import java.lang.annotation.*;

// 반복 가능한 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface RepeatableExceptionTest {
    Class<? extends Throwable> value();
}
