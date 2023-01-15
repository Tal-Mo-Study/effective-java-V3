# Item20. 추상 클래스보다는 인터페이스를 우선하라

[메인페이지 이동](../README.md)

```
일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합하다.
복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 꼭 고려해보자.
골격 구현은 '가능한 한' 인터페이스의 디폴트 메서드로 제공하여 그 인터페이스를 구현한 모든 곳에서 활용하도록 하는 것이 좋다.
'가능한 한'이라고 한 이유는, 인터페이스에 걸려 있는 구현상의 제약 때문에 골격 구현을 추상 클래스로 제공하는 경우가 더 흔하기 때문이다.
```

<br>

## 인터페이스 VS 추상 클래스

---

>자바가 제공하는 다중 구현 메커니즘은 인터페이스와 추상 클래스 두 가지

```
둘의 가장 큰 차이는 추상 클래스가 정의한 타입을 구현하는 클래스는 반드시 추상 클래스의 하위 클래스가 되어야 한다는 것
 → 새로운 타입 정의시 큰 제약이 생김
 → 반면 인터페이스는 기존 클래스에도 손쉽게 구현해 넣을 수 있음
```

<br>

## 추상 클래스 대비 인터페이스의 장점

---

>기존 클래스에도 손쉽게 새로운 인터페이스를 구현해넣을 수 있다.

```
1) 인터페이스가 요구하는 메서드를 아직 없다면 추가한다.
2) 클래스 선언에 implements 구문을 추가한다.
```

>인터페이스는 믹스인(mixin) 정의에 안성맞춤이다.

```
믹스인 : 클래스가 구현할 수 있는 타입
 → 믹스인을 구현한 클래스에 원래의 '주된 타입' 외에도 특정 선택적 행위를 제공한다고 선언하는 효과를 준다.

ex) Comparable : 구현한 클래스의 인스턴스들끼리는 순서를 정할 수 있다고 선언
```

>인터페이스로는 계층구조가 없는 타입 프레임워크를 만들 수 있다.

```
현실에서는 계층을 엄격히 구분하기 어려운 개념도 있음

조합 폭발 현상 : 같은 구조를 클래스로 만들려면 가능한 조합 전부를 각각의 클래스로 정의한 큼직한 계층구조가 만들어지는 현상
 → 속성이 n개면 2^n 개의 조합을 지원해야 함
```

>래퍼 클래스 관용구와 사용시 인터페이스는 기능을 향상시키는 안전하고 강력한 수단이 된다.

```
현실에서는 계층을 엄격히 구분하기 어려운 개념도 있음

조합 폭발 현상 : 같은 구조를 클래스로 만들려면 가능한 조합 전부를 각각의 클래스로 정의한 큼직한 계층구조가 만들어지는 현상
 → 속성이 n개면 2^n 개의 조합을 지원해야 함
```

<br>

## 디폴트 메서드 제약사항

---

```
equals와 hashCode같은 Object의 메서드를 디폴트 메서드로 제공해서는 안 된다.
인터페이스는 인스턴스 필드를 가질 수 없고 public이 아닌 정적 멤버도 가질 수 없다.
우리가 만들지 않은 인터페이스에는 디폴트 메서드를 추가할 수 없다.
```

<br>

## 인터페이스와 추상 클래스의 장점을 모두 취하는 방법

---

>추상 골격 구현 클래스 제공

```
인터페이스 -> 타입 정의 및 디폴트 메서드 제공
골격 구현 클래스 -> 나머지 메서드들까지 구현
 → 템플릿 메서드 패턴
 → 구현을 도와주는 동시에 추상 클래스와 달리 타입 정의시의 제약이 없다.
```

>골격 구현 클래스 네이밍 규칙 및 구현 사례

```
1. 관례상 인터페이스 이름이 Interface면 골격 구현 클래스 이름은 AbstractInterface
 ex) AbstractCollection, AbstractSet, AbstractList, AbstractMap, ...

2. List 구현체를 반환하는 정적 팩터리 메서드 예제
static List<Integer> intArrayAsList(int[] a){
    Objects.requireNonNull(a);

    // <> : Since Java 9
    return new AbstractList<> {
        @Override public Integer get(int i) {
            return a[i];    // auto boxing
        }

        @Override public Integer set(int i, Integer val) {
            int oldVal = a[i];
            a[i] = val;     // auto unboxing
            return oldVal;
        }

        @Override public int size() {
            return a.length;
        }
    }
}
```

>시뮬레이트한 다중 상속

```
골격 구현 클래스를 우회적으로 이용.
인터페이스를 구현한 클래스에서 해당 골격 구현을 확장한 private 내부 클래스를 정의하고,
각 메서드 호출을 내부 클래스의 인스턴스에 전달
 → 다중 상속의 장점 제공과 동시에 단점은 피할 수 있게 해줌
   ? 어떤 장점과 어떤 단점? 보통 단점만 많지않나
```

>골격 구현 작성

```
1) 인터페이스에서 다른 메서드들의 구현에 사용되는 기반 메서드들 선정
 -> 골격 구현에서는 추상 메서드가 됨
 -> Object의 메서드는 디폴트 메서드로 제공하면 안 됨!!

2) 기반 메서드나 디폴트 메서드로 만들지 못한 메서드가 남아있다면, 나머지를 작성

3) 상속해서 사용하는 것을 가정하므로 반드시 @implSpec JavaDoc Tag로 문서화

 * AbstractMapEntry 로 골격 구현 클래스 살펴보기
```
[AbstractMapEntry Source Code](https://github.com/google/guava/blob/master/guava/src/com/google/common/collect/AbstractMapEntry.java)

<br>

>단순 구현

```
골격 구현의 작은 변종
 ex) AbstractMap.SimpleEntry
 → 상속을 위해 인터페이스를 구현한 것이지만, 추상 클래스가 아님
 → 동작하는 가장 단순한 구현
   -> 그대로 쓰거나 필요에 맞게 확장해서 사용
```

[AbstractMap.SimpleEntry Source Code](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/AbstractMap.java)