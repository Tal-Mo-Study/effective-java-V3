package chapter_06.java.item34;

public enum BadEnum {
    PLUS,
    MINUS,
    TIMES,
    DIVIDE;

    /**
     * 새로운 상수를 추가 할 때 apply 재정의 해야 한다는 사실을 깜빡하기 쉬움
     */
    public double apply(double x, double y) {
        switch (this) {
            case PLUS: return x + y;
            case MINUS: return x - y;
            case TIMES: return x * y;
            case DIVIDE: return x / y;
        }
        throw new AssertionError("알 수 없는 연산: " + this);
    }
}
