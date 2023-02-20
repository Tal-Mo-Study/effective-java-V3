package chapter_06.java.item34;

/**
 * apply 메서드가 상수 선언 바로 옆에 있어 새로운 상수를 추가 할 때 apply 재정의 해야 한다는 사실을 깜빡하기 어려움
 */
public enum GoodEnum {
    PLUS {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS {
        public double apply(double x, double y) { return x - y; }
    },
    TIMES {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE {
        public double apply(double x, double y) { return x / y; }
    };
    
    public abstract double apply(double x, double y);
}
