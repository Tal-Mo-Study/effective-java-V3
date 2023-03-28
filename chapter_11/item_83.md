# Item83. 지연 초기화는 신중히 사용하라

[메인페이지 이동](../README.md)

> 지연 초기화

필드의 초기화 시점을 그 값이 처음 필요할 때까지 늦추는 기법

값이 전혀 쓰이지 않으면 초기화도 일어나지 않는다.
정적 필드와 인스턴스 필드 모두에 사용할 수 있다.

주로 최적화 용도로 쓰이지만, 클래스와 인스턴스 초기화 때 발생하는 위험한 순환 문제를 해결하는 효과도 있다.

> 필요한 경우

해당 클래스의 인스턴스 중 그필드를 사용하는 인스턴스의 비율이 낮은 반면, 그 필드를 초기화하는 비용이 큰 경우 => 적용 전후 성능 측정 필요

</br>

## 대부분의 상황에서 일반적인 초기화가 지연 초기화보다 낫다

</br>


> 일반적인 인스턴스 필드를 초기화하는 방법
```
    // 코드 83-1 인스턴스 필드를 초기화하는 일반적인 방법 
    private final FieldType field1 = computeFieldValue();
```
final 한정자 사용

</br>

> 지연 초기화가 초기화 순환성을 깨뜨릴 것 같으면 synchronized를 단 접근자를 사용

[ initialization circularity ]

Class A in its constructor creates instance of class B, class B creates instance of class C, and class C creates instance of class A.

```
    // 코드 83-2 인스턴스 필드의 지연 초기화 - synchronized 접근자 방식 
    private FieldType field2;
    private synchronized FieldType getField2() {
        if (field2 == null)
            field2 = computeFieldValue();
        return field2;
    }
```

</br>

> 성능 때문에 정적 필드를 지연 초기화해야 한다면 지연 초기화 홀더 클래스 관용구를 사용하자

<b>클래스는 클래스가 처음 쓰일 때 비로소 초기화된다는 특성</b>을 이용한 관용구

```
    // 코드 83-3 정적 필드용 지연 초기화 홀더 클래스 관용구
    private static class FieldHolder {
        static final FieldType field = computeFieldValue();
    }

    private static FieldType getField() { return FieldHolder.field; }
```
getField가 처음 호출되는 순간 FieldHolder.field가 처음 읽히면서, FieldHolder 클래스 초기화를 촉발한다.

getField 메서드가 필드에 접근하면서 동기화를 전혀하지 않으니 성능이 느려질 거리가 전혀 없다.

일반적인 VM은 오직 클래스를 초기화할 때만 필드 접근을 동기화한다.
클래스 초기화가 끝난 후에는 VM이 동기화 코드를 제거하여, 그 다음부터는 아무런 검사나 동기화 없이 필드에 접근하게 된다.

</br>


> 성능 때문에 인스턴스 필드를 지연 초기화해야 한다면 이중검사 관용구를 사용하자

초기화된 필드에 접근할 때의 동기화 비용을 없애준다. 필드가 초기화된 후로는 동기화하지 않으므로 해당 필드는 volatile로 선언해야한다.

```
    // 코드 83-4 인스턴스 필드 지연 초기화용 이중검사 관용구
    private volatile FieldType field4;

    private FieldType getField4() {
        FieldType result = field4;
        if (result != null)    // 첫 번째 검사 (락 사용 안 함)
            return result;

        synchronized(this) {
            if (field4 == null) // 두 번째 검사 (락 사용)
                field4 = computeFieldValue();
            return field4;
        }
    }
```

result란 지역변수가 필요한 이유는?

이 변수는 필드가 이미 초기화된 상황에서는 그 필드를 딱 한번만 읽도록 보장하는 역할을 한다.
반드시 필요하지는 않지만 성능을 높여주고, 저수준 동시성 프로그래밍에 표준적으로 적용되는 더 우아한 방법. 
지역변수를 사용하지 않을 때보다 1.4배 빠르다.

이중검사를 정적 필드에도 적용할 수 있지만 굳이 필요없다. 지연 초기화 홀더 클래스 방식이 더 낫다.



</br>

> 반복해서 초기화해도 상관없는 인스턴스 필드를 지연초기화 한다면, 이중검사에서 두 번째 검사를 생략할 수 있다.
```
    // 코드 83-5 단일검사 관용구 - 초기화가 중복해서 일어날 수 있다!
    private volatile FieldType field5;

    private FieldType getField5() {
        FieldType result = field5;
        if (result == null)
            field5 = result = computeFieldValue();
        return result;
    }

```

모든 초기화 기법은 기본 타입 필드와 객체 참조 필드 모두에 적용할 수 있다.

이중, 단일 검사 관용구를 수치 기본 타입 필드에 적용한다면 null 대신 0과 비교

</br>

> 짜릿한 단일검사 관용구

모든 스레드가 필드의 값을 다시 계산해도 상관없고 필드의 타입이 long과 double을 제외한 다른 기본 타입이라면, 단일검사의 필드 선언에서 volatile 한정자를 없애도 된다.

어떤 환경에서는 필드 접근 속도를 높여주지만, 초기화가 스레드당 최대 한 번 더 이뤄질 수 있다. 이례적인 기법으로 보통은 쓰지 않는다.




</br>

### 정리

> 대부분의 필드는 지연시키지 말고 곧바로 초기화해야 한다. 성능 때문에 혹은 위험한 초기화 순환을 막기 위해 꼭 지연 초기화를 써야한다면 올바른 지연 초기화 기법을 사용하자. 인스턴스 필드에는 이중검사 관용구를, 정적필드에는 지연 초기화 홀더 클래스 관용구를 사용하자. 반복해 초기화해도 괜찮은 인스턴스 필드에는 단일검사 관용구도 고려 대상이다.</br> 