# 비트 필드 대신 EnumSet을 사용하라

열거한 값들이 주로(단독이 아닌) 집합으로 사용될 경우,예전에는 각 상수에 서로 다른 2의 거듭 제곱값을 할당한 정수 열거 패턴(아이템 34)을 사용해왔다.

```java
// 비트필드 열거 상수 - 구닥다리 기법!
public class Text {
    public static final int STYLE BOLD = 1 << 0; //1 
    public static final int STYLE_ITALIC = 1 <<1 //2 
    public static final int STYLE_UNDERLINE = 1 << 2; // 4 
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8
    // 매개변수 styles는 0개 이상의 STYLE_ 상수를 비트별 0R한 값이다. 
    public void applyStyles (int styles) { ... }
}
```

다음과 같은 식으로 비트별 OR를 사용해 여러 상수를 하나의 집합으로 모을 수 있으며, 이렇게 만들어진 집합을 비트필드 (bit field)라 한다.
```text.applyStyles(STYLE_BOLD|STYLE_ITALIC);```

장점 : 비트 필드를 사용하면 비트별 연산을 사용해 합집합과 교집합 같은 집합 연산을 효율적으로 수행할 수 있다. <br>
단점 : 정수 열거 상수 단점 + 출력되면 단순한 정수 열거 상수를 출력할때 보다 해석하기 훨씬 어렵다. 비트 필드 하나에 녹아있는 모든 원소를 순회하기도 까다롭다. 마지막으로, 최대 몇 비트가 필요한지를 API 작성시 미리 예측하여 적절한 타입(보통은 int나 long)을 선택해야 한다. <br>
대안 : java.util 패키지의 EnumSet 클래스는 열거타입 상수의 값 으로 구성된 집합을 효과적으로 표현해준다. Set 인터페이스를 완벽히 구현하며, 타입안전하고, 다른 어떤 Set구현체와도 함께 사용할 수 있다.

```java
// EnumSet- 비트필드를 대체하는 현대적 기법
public class Text {
    public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
    public void applyStyles (Set<Style> styles) { ... }
}
```

```java
// applystyles 메서드에Enumset 인스턴스를건네는클라이 언트코드
text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
```
