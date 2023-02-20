package chapter_08.java.item49;

import java.util.Objects;

public class ParameterCheck2 {

    /**
     * 입력된 문자열의 길이를 출력한다
     *
     * @param str 문자열
     *
     * @throws NullPointerException 입력된 문자열이 null인 경우
     */
    public void execute(String str) {
        // null 검사를 수동으로 할 필요가 없음 (JDK 7 이상)
        Objects.requireNonNull(str, "str is null");

        subExecute("ABC");
    }

    /**
     * 공개되지 않은 메서드라면 패키지 제작자가 유효한 값만이 메서드에 넘겨지리라는 것을 보증할 수 있으므로
     * assert(단언문)을 사용해 매개변수 유효성을 검증할 수 있음
     *
     * @param str
     */
    private void subExecute(String str) {
        assert str != null;

        System.out.println("length: " + str.length());
    }

    public static void main(String[] args) {
        ParameterCheck2 pc = new ParameterCheck2();
        pc.execute("Hello");

        pc.execute(null);
    }
}
