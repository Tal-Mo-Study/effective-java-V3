# Item04. 인스턴스화를 막으려거든 private 생성자를 사용하라

[메인페이지 이동](../README.md)

```
정적 메서드와 정적 필드만을 담은 클래스를 만들고 싶을때가 있을것이다.
객체 지향적으로 사고하지 않는 이들이 종종 남용하는 방식이지만 나름대로의 쓰임새가 있다.
java.lang.Math나 java.util.Arrays 처럼 기본 타입 값이나 배열 관련 메서드 들을 모아둔 유틸리티 클래스
```

## 그렇다면 이러한 클래스의 인스턴스화를 막는 방법은 무엇인가??

---

> private 생성자를 사용하면 객체의 인스턴스화를 막을 수 있다!!

```
public class Test {
    public static void main(String[] args) {
        //System.out.println(new Utility()); //생성자가 private 이기 때문에 인스턴스화가 불가능하다
        System.out.println(Utility.bark(););
    }
}

public class Utility{
    public static void bark(){
        System.out.println("멍멍");
    }
    private Utility(){};
}
```
