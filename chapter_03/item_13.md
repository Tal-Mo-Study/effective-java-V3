# Item13. clone 재정의는 주의해서 진행하라

[메인페이지 이동](../README.md)

</br>

## Cloneable
복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스([item20](../chapter_04/item_20.md))</br>

문제점 clone 메서드 선언된곳이 Cloneable이 아닌 Object에 구현이고, protected이다.
 -> Cloneable 구현만으로는 외부 객체에서  clone메서드를 호출할 수 없다.

</br>

### Cloneable의 역할
Object의 protected 메서드인 clone의 동작 방식을 결정한다.
구현한 클래스의 인스턴스에서 clone을 호출하면 그 객체의 필드들을 하나하나 복사한 객체를 반환하며, 그렇지 않은 클래스의 인스턴스에서 clone을 호출하면
CloneNotSupportedException을 던진다.</br>

</br>

> clone 메서드의 일반 규약 - 허술하다
```
일반적인 복사의 의도 (일반적으로 참이지만 필수는 아님)

x.clone() != x

x.clone().getClass() == x.getClass()

x.clone().equals(x)

x.clone().getClass() == x.getClass()

```


</br>

>clone() 메서드를 통해 생성된 객체의 멤버변수를 수정했을 때, 원본 객체에 아무런 영향을 주지 않는 동시에 복제된 객체의 불변식을 보장해야한다.


```
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
    
       @Override public Stack clone() {
        try {
            return (Stack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    } 
    
    public static void main(String[] args) {
        Stack stack = new Stack();
        for (String arg : args)
            stack.push(arg);

        while (true)
            System.err.println(stack.pop());
    }
}
```

</br>

> 단순 super.clone으로 반환한다면?

size 필드는 올바른 값을 갖지만, elements 필드는 원본과 같은 배열을 참조한다. 

생성자 호출시에는 이러한 상황 안일어난다.
clone() 메서드는 사실상 생성자와 같은 효과를 내야한다.

```
    @Override public Stack clone() {
        try {
            Stack result = (Stack) super.clone();
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
```

만약 elements 필드가 final 이라면 clone이 동작하지 않는다.

근본적인 문제로 Cloneable 아키텍처는 '가변 객체를 참조하는 필드는 final로 선언하라'는 일반 용법과 충돌한다.
일부 필드에서 한정적으로 final  한정자를 제거해야할 수도 있다.


clone() 메서드를 제대로 사용하고 있는 유일한 예

</br>

> 해쉬테이블용 clone 메서드

재귀 호출을 통해 각 배열 버킷 내부에 있는 연결리스트도 새롭게 copy (deep copy) 해줘야, 복제 객체를 수정시에 원치 않은 결과 도출을 막을 수 있다.

연결리스트가 너무  길경우는 stackOverFlow 발생할 수 있다. for문을 사용하자


</br>

> 상속용 클래스는 Cloneable을 구현해서는 안된다.


Cloneable을 구현한 스레드 안전 클래스를 작성할 때는 clone 메서드 역시 적절히 동기화 해줘야한다.

</br>

> 복사 생성자와 복사 팩터리라는 더 나은 객체 복사 방식을 제공할 수 있다.

복사 생성자란? </br>
자신과 같은 클래스의 인스턴스를 변수로 받는 생성자
```
public Yum(Yum yum){
    // 복사 처리 후 복사한 객체 봔한
}
```

복사 팩터리?</br>
복사 생성자를 모방한 정정팩터리([item1](../chapter_01/item_01.md))
```
public static Yum newInstance(Yum yum){...}
```

위험한 객체 생성 매커니즘(생성자를 쓰지 않는 방식) 엉성하게 문서환된 규약에 기대지 않고, 정상적인 final 필드 용법과도 충돌하지 않으며, 불필요한 검사 예외를 던지지 않고, 형변환도 필요치 않다.

</br>

> 관례상 모든 범용 컬렉션 구현체는 Collection이나 Map 타입을 받는 생성자를 제공한다.

HashSet 객체 s를 TreeSet 타입으로 복제가능
new TreeSet<>(s);



## 정리

Cloneable이 몰고 온 문제를 되짚어봤을 때, </br>
새로운 인터페이스를 만들 때는 절대 사용하면 안되며, 새로운 클래스도 사용하면 안된다. </br>
final 클래스일 경우는 위험도가 적지만, 성능 최적화 관점에서 검토한 후 드물게 사용 해야 한다.</br> 
기본원칙은 '복제기능은 생성자와 팩터리를 이용하는게 최고'라는 것이다. </br>
단, 배열만은 clone 메서드 방식이 가장 깔끔하다.
