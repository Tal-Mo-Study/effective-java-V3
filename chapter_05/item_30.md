# Item30. 이왕이면 제네릭 메서드로 만들라

[메인페이지 이동](../README.md)

```
제네릭 타입과 마찬가지로, 클라이언트에서 입력 매개변수와 반환값을 명시적으로 형변환해야 하는 메서드보다 제네릭 메서드가 더 안전하며 사용하기도 쉽다.
타입과 마찬가지로, 메서드도 형변환 없이 사용할 수 있는 편이 좋으며, 많은 경우 그렇게 하려면 제네릭 메서드가 되어야 한다.
역시 타입과 마찬가지로, 형변환을 해줘야 하는 기존 메서드는 제네릭하게 만들자.
기존 클라이언트는 그대로 둔 채 새로운 사용자의 삶을 훨씬 편하게 만들어줄 것이다.
```

<br>

## 제네릭 메서드?

---

>매개변수화 타입을 받는 정적 유틸리티 메서드는 보통 제네릭임

```
1. Collections의 '알고리즘' 메서드
 ex) binarySearch, sort, ...
```

<br>

## 제네릭 메서드 작성법

---

>제네릭 타입 작성법과 비슷

```
1) 메서드 선언시 파라미터를 타입 매개변수로, 메서드 안에서도 해당 타입 매개변수만 사용하도록 작성
2) 타입 매개변수들을 선언하는 타입 매개변수 목록은 메서드의 제한자와 반환 타입 사이에 온다.

 * 책에 소개된 Code 30-2 메서드는 한정적 와일드카드 타입을 사용해서 더 유연하게 개선 가능(Item 31)
```

>불변 객체를 여러 타입으로 활용할 수 있게 만들어야 하는 경우

```
제네릭은 런타임에 타입 정보가 소거되므로 하나의 객체를 어떤 타입으로든 매개변수화 할 수 있다. 다만, 이렇게 하려면 요청한 타입 매개변수에 맞게 매번 그 객체의 타입을 바꿔주는 정적 팩터리를 만들어야 한다.
 → 제네릭 싱글턴 팩터리
  ex) Collections.reverseOrder, Collections.emptySet
```

[Collections Source Code](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/Collections.java)


>항등 함수(identity function)를 담은 클래스를 만들어 본다면

```
Function.identity를 사용하면 됨.
항등함수 객체는 상태가 없으니 요청시마다 새로 생성하는 것은 낭비
 → 소거 방식을 사용하여 제네릭 싱글턴 하나면 충분히 커버 가능
```

>재귀적 타입 한정(recursive type bound)

```
자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정
주로 타입의 자연적 순서를 정하는 Comparable 인터페이스와 함께 쓰임

ex) 
public interface Comparable<T> {
    int compareTo(T o);
}

타입 매개변수 T는 Comparable<T>를 구현한 타입이 비교할 수 있는 원소의 타입을 정의
컬렉션에 담긴 모든 원소가 상호 비교될 수 있어야 함

use) Code 30-6
public static <E extends Comparable<E>> E max(Collection<E> c);
```


<br><br>

>Code

[Java Code Item_30](./java/Item_30)