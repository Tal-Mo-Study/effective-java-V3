# Item06. 불필요한 객체 생성을 피하라

[메인페이지 이동](../README.md)

```
1. 값비싼 객체 재활용등
2. wrapper class 보다는 primitive type을 사용하자
  오토박싱, 언박싱은 기본타입과 참조타입의 구분을 흐려주지만 완전히 없애주는것은 아니다
3. Literal Pool을 최대한 활용하자
  "김윤수" >>>>> new String("김윤수")
   Boolean.valueOf("true") >>>>> new Boolean("true")
4. immutable class 대신 mutable class를 활용하자
```

</br></br>

## 값비싼 객체 재활용

---

> 대표적으로 String.matches는 정규표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만</br>
> 성능이 중요한 상황에서 반복해 사용하기에는 적절하지 않다.
>
> > 이유 : 메서드 내부에서 사용하는 Pattern 인스턴스가 한번 쓰고 버려지기 때문

```
예제 1)
static boolean isRomanNumeral(String s){
  return s.matches("regex");
}

예제 2)
public class RomanNumerals {
  private static final Pattern ROMAN = Pattern.compile("regex");

  private RomanNumerals(){};

  static boolean isRomanNumeral(String s){
    return ROMAN.matcher(s).matches();
  }
}
```

> 6.5배의 성능향상이 기대된다.

</br></br></br>

## 끔찍이 느리다~! 객체가 만들어지는 위치를 찾았나용

---

```
Long sum = 0L;
for(long i=0; i<=Integer.MAX_VALUE; i++){
  sum += i;
}
```

> Long sum 변수가 문제이다
>
> > 단순 연산하는데 있어서 Long이라는 wrapper class(참조형) 을 사용 했기때문에 불필요한 변수 2^31개나 생성 된것

</br></br></br>

## String 에서 + 연산 대신 StringBuilder, StringBuffer를 사용하자

> 비슷한 맥락이다 Immutable << Mutable
