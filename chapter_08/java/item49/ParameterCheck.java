package chapter_08.java.item49;

public class ParameterCheck {

    /**
     * 입력된 문자열의 길이를 출력한다
     *
     * @param str 문자열
     *
     * @throws NullPointerException 입력된 문자열이 null인 경우
     */
    public void execute(String str) {
        if (str == null) {
            throw new NullPointerException("str is null");
        }

        System.out.println("length: " + str.length());
    }

    public static void main(String[] args) {
        ParameterCheck pc = new ParameterCheck();
        pc.execute("Hello");

        pc.execute(null);
    }
}
