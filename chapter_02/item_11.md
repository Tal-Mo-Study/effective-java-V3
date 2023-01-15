# Item11. equals를 재정의하려거든 hashCode도 재정의하라

Object명세
- equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드는 몇 번을 호출해도 일관되게 항상 같은 값을 반환해야 한다. 단, 애플리케이션을 다시 실행한다면 이 값이 달라져도 상관없다.
- equals(Object)가 두 객체를 같다고 판단했다면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다.
- equals(Object)가 두 객체를 다르다고 판단했더라도, 두 객체의 hashCode가 서로 다른 값을 반환할 필요는 없다. 단, 다른객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아진다.

hashCode를 재정의를 잘못했을 때 크게 문제가 되는 조항은 두 번재다. 즉, 논리적으로 같은 객체는 같은 해시코드를 반환해야 한다.

아래의 PhoneNumber 코드를 HashMap 원소로 사용한다고 가정하자.
~~~java
@AllArgsConstructor
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }
}
~~~

~~~java
Map<PhoneNumber, String> map = new HashMap<>();
p.put(new PhoneNumber(707, 867, 5309), "제니");
~~~

이 코드를 사용할때 
~~~java
m.get(new PhoneNumber(707, 867, 5309));
~~~
를 실행하면 "제니"가 아닌 null을 반환한다.

좋은 hashCode를 만드는 요령
1. int 변수 result를 선언한 후 값 c로 초기화한다. 이때 c는 해당 객체의 첫번재 핵심 필드를 단계 2.a 방식으로 계산한 해시코드다(여기서 핵심 필드란 equlas 비교에 사용되는 필드를 말한다. 아이템 10 참조)
2. 해당 객체의 나머지 핵심필드 f각각에 대해 다음 작업을 수행한다.
    a. 해당 필드 c의 해시코드를 계산한다.
        - 기본 필드 타입이라면 Type.hashCode(f)를 수행한다. 여기서 Type 은 해당 기본 타입의 박싱 클래스다.
        - 참조 타입 필드면서 이 클래스의 equals 메서드가 이 필드의 equals를 재귀적으로 호출해 비교한다면, 이 필드의 hashCode를 재귀적으로 호출한다. 계산이 더 복잡해질 것 같으면, 이 필드의 표준형(canonical representation)을 만들어 그 표준형의 hashCode를 호출한다. 필드의 값이 null이면 0을 사용한다. (다른 상수도 괜찮지만 전통적으로 0을 사용한다.)
        - 필드가 배열이라면, 핵심원소 각각을 별도 필드처럼 다룬다. 이상의 규칙을 재귀적으로 적용해 각 핵심 원소의 해시코드를 계산한 다음, 단계 2.b 방식으로 갱신한다. 배열의 핵심 원소가 하나도 없다면 단순히 상수(0을 추천한다)를 사용한다. 모든 원소가 핵심 원소라면 Arrays.hashCode를 사용한다.
    b. 단계 2.a에서 계산한 hashCode c로 result를 갱신한다. 코드로는 다음과 같다. result = 31 * result + c;
3. result를 반환한다.

전형적인 hashCode
~~~java
@Override
public int hashCode() {
    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
}
~~~

한줄짜리 hashCode - 성능이 살짝 아쉽다
~~~java
@Override
public int hashCode() {
    return Object.hash(lineNum, prefix, areaCode);
}
~~~

지연 초기화 하는 hashCode 메서드 - 스레드 안정성 까지 고려해야 한다.
~~~java
@Override
public int hashCode() {
    int result = hashCode;
    if (result == 0) {
        result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        hashCode = result;
    }
    return result;
}
~~~