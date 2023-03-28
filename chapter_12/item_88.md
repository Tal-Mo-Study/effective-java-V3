# Item88. readObject 메서드는 방어적으로 작성하라

[메인페이지 이동](../README.md)

[가변인 Date 필드를 이용해 만든 불변 날짜 범위 클래스](./java/Period.java)

직렬화 해보자 

Period 객체의 물리적 표현이 논리적 표현과 부합하므로 기본 직렬화 형태를 가용해도 나쁘지 않다.
=> implements Serializable 추가

하지만 불변식을 보장하지 못하게 된다.

> readObject 메서드가 실질적으로는 또 다른 public 생성자이기 때문이다.

다른 생성자와 똑같은 수준으로 주의를 기울여야한다.

보통의 생성자처럼 readObject메서드에서도 인수가 유효한지 검사해야하고 필요하다면 매개변수를 방어적으로 복사해야한다.

readObject가 이 작업을 제대로 수행하지 못하면 공격자는 아주 손쉽게 해당 클래스의 불변식을 깨뜨릴 수 있다.

readObject는 매개변수로 바이트 스트림을 받는 생성자라 할 수 있다.
보통은 정상적으로 생성된 인스턴스를 직렬화해 만들어진다.
하지만 불변식을 깨뜨릴 의도로 임의 생성한 바이트 스트림을 건네면 문제가 생긴다. 정상적인 생성자로는 만들어낼 수 없는 객체를 생성해낼 수 있기 때문

직렬화된 바이트스트림 수정으로 



> 가변 공격

개변 객체의 참조를 훔쳐 바꿈

> 객체를 역직렬화할 떄는 클라이언트가 소유해서는 안 되는 객체 참조를 갖는 필드를 모두 반드시 방어적으로 복사해야 한다.

readObject에서는 불변 클래스 안의 모든 private가변 요소를 방어적으로 복사해야한다.
```
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException{
    s.defaultReadObject();

    // 가변 요소들을 방어적으로 복사한다.
    start = new Date(start.getTime());
    end = new Date(end.getTime());

    // 불변식을 만족하는지 검사한다.
    if (start.compareTo(end)>0)
        throw new InvalidObjectException(start + "가 "+ end + "보다 늦다.");

}

```

방어적 복사를 유효성 검사보다 앞서 수행하며, Date의 clone 메서드는 사용하지 않았다.

아이템 50

final 필드는 방어적 복사가 불가능하다.

final 한정자를 제거하고 방어적 복사를 수행

final이 아닌 직렬화 가능 클래스라면 readObject와 생성자의 공톰점이 하나 더 있다.
생성자처럼 readObject 메서드도 재정의 가능 메서드를 호출해서는 안 된다.

이 규칙을 어긴 메서드가 재정의 되면, 하위 클래스의 상태가 완전히 역직렬화 되기 전에 하위 클래스에서 재정의된 메서드가 실행된다.


</br>

### 정리

> readObject 메서드를 작성할 때는 언제나 public 생성자를 작성하는 자세로 임해야 한다. readObject는 어떤 바이트 스트림이 넘어오더라도 유효한 인스턴스를 만들어 내야한다. 바이트 스트림이 진짜 직렬화된 인스턴스라고 가정해서는 안 된다. 이번 아이템에서는 기본 직렬화 형태를 사용한 클래스를 예로 들었지만 커스텀 직렬화를 사용하더라도 모든 문제가 그대로 발생할 수 있다.
```
안전한 readObject 메서드를 작성하는 지침 요약

- private 이어야 하는 객체 참조 필드는 각 필드가 가리키는 객체를 방어적으로 복사하라. 불변 클래스 내의 가변 요소가 여기 속한다.
- 모든 불변식을 검사하여 어긋나는 게 발견되면 InvalidObjectException을 던진다. 방어적 복사 다음에는 반드시 불변식 검사가 뒤따라야 한다.
- 역직렬화 후 객체 그래프 전체의 유효성을 검사해야한다면 ObjectInputValidation 인터페이스를 사용하라.
- 직접적이든 간접적이든, 재정의할 수 있는 메서드는 호출하지 말자
```