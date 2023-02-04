package chapter_05.java.Item_35;

/*
 *  Code 35-1. ordinal을 잘못 사용한 예 - 따라 하지 말 것!
 */
// public enum Ensemble {
//     SOLO, DUET, TRIO, QUARTET, QUINTET,
//     SEXTET, SEPTET, OCTET, NONET, DECTET;   // 1 ~ 10, 13 추가시

//     public int numberOfMusicians() {
//         return ordinal() + 1;   // ordinal()을 사용하지 말자
//     }
// }

public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
    NONET(9), DECTET(10), TRIPLE_QUARTET(12);

    private final int numberOfMusicians;    // 인스턴스 필드
    Ensemble(int size) {
        this.numberOfMusicians = size;
    }
    public int numberOfMusicians() {
        return numberOfMusicians;
    }
}