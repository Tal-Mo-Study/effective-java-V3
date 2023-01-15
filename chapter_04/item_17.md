# Item17. 변경 가능성을 최소화하라

[메인페이지 이동](../README.md)


```
불변클래스란? 
불변 클래스란 그 인스턴스의 내부 값을 수정할 수 없는 클래스다. 
불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다. 
자바 플랫폼 라이브러리에도 다양한 불변 클래스가 있다
String, 기본타입의 박싱된 클래스들, BigInteger, BigDecimal이 있다.

불변 클래스는 가변 클래스보다 설계하고 구현하고 사용하기 쉬우며, 오류가 생길 여지도 적고 훨씬 안전하다.
```

</br></br>

## 클래스를 불변으로 만들기 위한 5가지 규칙

---

> 객체의 상태를 변경하는 메서드를 제공하지 않는다. </br>


>  클래스를 확장할 수 없도록 한다. </br>

하위 클래스에서 부주의 하게 혹은 나쁜 의도로 객체의 상태를 변하게 만드는 사태를 막아준다.
상속을 막는 대표적인 방법은 클래스를 final로 선언하는 것이지만 다른 방법도있다.

>  모든 필드를 final로 선언한다. </br>

- 시스템이 강제하는 수단을 이용해 설계자의 의도를 드러내는 방법</br>
- 새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없이 동작하게끔 보장하는 데도 필요하다. ([코드](./java/item_17.java))</br>
(자바언어 명세의 메모리 모델 부분)

>  모든 필드를 private으로 선언한다.  </br>

필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 수정하는 일을 막아준다. </br>
불변 객체를 참조하는 필드를 public final로만 선언해도 불변 객체가 되지만, 
다음 릴리스에서 내부 표현을 바꾸지 못하므로 좋은 방법은 아니다. ([item15](./item_15.md), [item16](./item_16.md))</br>

>  자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록한다. </br>
- 클래스에 가변 객체를 참조하는 필드가 하나라도 있다면 클라이언트에서 그 객체의 참조를 얻을 수 없도록 해야한다.</br>
이런 필드는 절대 클라이언트가 제공한 객체 참조를 가리키게 해서는 안되며, </br> 
접근자 메서드가 그 필드를 그대로 반환해서도 안 된다.
- 생성자, 접근자, readObject 메서드 모두에서 방어적 복사를 수행할 것.

</br>

## 불변 클래스의 복잡한 예시
```
public final class Complex {
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex times(Complex c) {
        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }
    
    public Complex divideBy(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re - im * c.im) / tmp,
                (re * c.im + im * c.re) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complex)) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.re, re) == 0 &&
                Double.compare(complex.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}   
```
이 클래스는 복소수(실수부와 허수부로 구성된 수)를 표현한다. </br>
실수부와 허수부 값을 반환하는 접근자 메서드(realPart와 imaginaryPart)와 </br>
사칙연산 메서드(plus, minus, times, divideBy)를 정의했다.</br>

사칙연산 메서드들이 인스턴스 자신은 수정하지 않고 새로운 Complex 인스턴스를 만드렁 반환하는 모습에 주목할 것. 이처럼 피연산자에 함수를 적용해 그 결과를 반환하지만, 피연산자 자체는 그대로인 프로그래밍 패턴을 함수형 프로그래밍이라 한다.

이와 달리, 절차적 혹은 명령형 프로그래밍에서는 메서드에서 피연산자인 자신을 수정해 자신의 상태가 변하게 된다. 또한 메서드 이름으로 (add 같은) 동사 대신 (plus 같은) 전치사를 사용한 점에도 주목해야한다.

이는 해당 메서드가 객체의 값을 변경하지 않는다는 사실을 강조하려는 의도다. 이 명명 규칙을 따르지 않은 BigInteger와 BigDecimal클래스를 사람들이 잘못 사용해 오류가 발생하는 일이 자주 있다.

</br>

## 불변객체 (함수형 프로그래밍) 장점
> 불변 객체는 단순하다. 코드의 불변이 되는 영역 비율이 높아진다.

불변 객체는 생성된 시점의 상태를 파괴될 때까지 그대로 간직한다.</br>
모든 생성자가 클래스 불변식(class invariant)을 보장한다면</br>
그 클래스를 사용하는 프로그래머가 다른 노력을 들이지 않아도 영원히 불변으로 남는다.

> 불변 객체는 스레드 안전하며 따로 동기화할 필요가 없다. => 안심하고 공유 할 수 있다.

여러 스레드가 동시에 사용해도 절대 훼손되지 않는다. (스레드 안전하게 만드는 가장 쉬운 방법) </br>
불변 객체에 대해서는 그 어떤 스레드도 다른 스레드에 영향을 줄 수 없으니 불변 객체는 안심하고 공유할 수 있다.

따라서 불변 클래스라면 한번 만든 인스턴스를 최대한 재활용하기를 권한다.</br>

가장 쉬운 재활용 방법은 자주 쓰이는 값들을 상수(public static final)로 제공하는 것이다.

```
public static final Complex ZERO = new Complex(0, 0);
public static final Complex ONE  = new Complex(1, 0);
public static final Complex I    = new Complex(0, 1);
```

이 방식으로 불변 클래스는 자주 사용되는 인스턴스를 캐싱하여 같은 인스턴스를 중복 생성하지 않게 해주는 정적 팩터리([item1](../chapter_01/item_01.md)) 제공 할 수 있다.</br>

정적 팩터리를 사용하면 여러 클라이언트가 인스턴스를 공유하여 메모리 사용량과 가비지 컬렉션 비용이 줄어든다.</br>
새로운 클래스를 설계할 때 public 생성자 대신 정적 팩터리를 만들어두면, 클라이언트를 수정하지 않고도 필요에 따라 캐시 기능을 나중에 덧붙일 수 있다.





> 불변 객체의 공유

→ 따라서 불변 클래스라면 한번 만든 인스턴스를 최대한 재활용하기를 권한다. 가장 쉬운 재활용 방법은 자주 쓰이는 값들을 상수(public static final)로 제공하는 것이다.

public static final Complex ZERO = new Complex(0, 0);
public static final Complex ONE  = new Complex(1, 0);
public static final Complex I    = new Complex(0, 1);
이 방식으로 불변 클래스는 자주 사용되는 인스턴스를 캐싱하여 같은 인스턴스를 중복 생성하지 않게 해주는 적정 팩터리를 제공할 수 있다. 박싱된 기본 타입 클래스 전부와 BigInteger가 여기 속한다.

정적 팩터리를 사용하면 여러 클라이언트가 인스턴스를 공유하여 메모리 사용량과 가비지 컬렉션 비용이 줄어든다. 새로운 클래스를 설계할 때 public 생성자 대신 정적 팩터리를 만들어두면, 클라이언트를 수정하지 않고도 필요에 따라 캐시 기능을 나중에 덧붙일 수 있다.

> 객체를 만들 때 다른 불변 객체들을 구성요소로 사용하면 이점이 많다.

많이 바뀌지 않는 구성요소들로 이뤄진 객체라면 그 구조가 아무리 복잡하더라도 불변식을 유지하기 훨씬 수월하기 때문이다. </br>

좋은 예로, 불변 객체는 맵의 키와 집합(Set)의 원소로 쓰기에 안성맞춤이다.</br>
맵이나 집합은 안에 담긴 값의 바뀌면 불변식이 허물어지는데, 불변 객체를 사용하면 그런 걱정은 하지 않아도 된다.</br>

불변 객체는 그 자체로 실패 원자성을 제공한다. 상태가 절대 변하지 않으니 불일치 상태에 빠질 가능성이 없다. </br>

> 실패 원자성(failure atomicity)([item76](../chapter_08/item_76.java))

메서드에서 예외가 발생한 후에도 그 객체는 여전히(메서드 호출 전과 똑같은) 유효한 상태여야 한다.

</br>

## 불변 클래스 단점

불변 클래스에서 값이 다르면 반드시 독립된 객체로 만들어야 한다.</br>
값의 가짓수가 많다면 이들을 모두 만드는 데 큰 비용을 치뤄야 한다.</br>
예컨대 백만 비트짜리 BigInteger에서 비트 하나를 바꿔야 한다고 하면,
flipBit 메서드는 새로운 BigInteger 인스턴스를 생성한다. 원본과 단지 한 비트만 다른 백만 비트짜리 인스턴스이다. 이 연산은 BigInteger의 크기에 비례해 시간과 공간을 잡아먹는다.


```
BigInteger moby = ...;
moby = moby.flipBit(0);
```

BitSet도 BigInteger처럼 임의 길이의 비트 순열을 표현하지만, BigInteger와는 달리 '가변'이다. BitSet클래스는 원하는 비트 하나만 상수 시간 안에 바꿔주는 메서드를 제공한다.

```
BitSet moby = ...;
moby.flip(0);
```

## 해결

클라이언트들이 원하는 복잡한 연산들을 정확히 예측할 수 있다면 가변 동반 클래스만으로 충분하다. 
그렇지 않다면 이 클래스를 public으로 제공해야한다. 이에 해당하는 대표적인 예가 String 클래스다.

String의 가변 동반 클래스는 바로 StringBuilder(or StringBuffer)이다.

</br>

## 다른 불변 클래스를 만드는 설계 방법

클래스가 불변임을 보장하려면 자신을 상속하지 못하게 해야 한다.</br>
자신을 상속하지 못하게 가장 쉬운 방법은 final이나 더 유연한 방법이 있다. </br>


```
public class Complex {
    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }

	// 나머지는 위와 동일
}
```

바깥에서 볼 수 없는 package-private 구현 클래스를 원하는 만큼 만들어 활용할 수 있으니 훨씬 유연하다. 패키지 바깥의 클라이언트에서 바라본 이 불변 객체는 사실상 final이다. public이나 protected 생성자가 없으니 다른 패키지에서는 이 클래스를 확장하는게 불가능하기 때문이다.

```
public static BigInteger safeInstance(BigInteger val) {
		return val.getClass() == BigInteger.class ?
						val : new BigInteger(val.toByteArray());
}
```

</br>

## 정리

> getter가 있다고 해서 무조건 setter를 만들면 안된다.

클래스는 꼭 필요한 경우가 아니라면 불변이어야 한다.

불변 클래스는 장점이 많으며, 단점이라곤 특정 상황에서의 잠재적 성능 저하 뿐이다. 단순한 값 객체는 항상 불변으로 만들자.</br> 
String, BigInteger처럼 무거운 값 객체도 불변으로 만들 수 있는지 고심해야 한다.

→ 성능 때문이라면 불변 클래스와 쌍을 이루는 가변 동반 클래스를 public 클래스로 제공하도록 하자.

한편, 모든 클래스를 불변으로 만들 수는 없다.

> 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소한으로 줄이자.

객체가 가질 수 있는 상태 수를 줄이면 그 객체를 예측하기 쉬워지고 오류가 생길 가능성이 줄어든다.</br>
그러니 꼭 변경해야 할 필드를 뺀 나머지 모두를 final로 선언할 것.

> 다른 합당한 이유가 없다면 모든 필드는 private final 이어야 한다.

생성자는 불변식 설정이 모두 완료된, 초기화가 완벽히 끝난 상태의 객체를 생성할 것.</br>
확실한 이유가 없다면 생성자와 정적 팩터리 외에는 그 어떤 초기화 메서드도 public으로 제공해서는 안된다.</br> 
객체를 재활용할 목적으로 상태를 다시 초기화하는 메서드도 안된다.
