# 로 타입은 사용하지 말라

클래스와 인터페이스 선언에 타입매개변수(type parameter)가쓰이면, 이를 제네릭 클래스 혹은 제네릭 인터페이스라한다.

제네릭을 지원하기 전에는 컬렉션을 다음과 같이 선언했다. 자바9 에서도 여전히 동작하지만 좋은 예라고 볼 순 없다.
~~~java
컬렉션의 로타입- 따라하지말것!
// Stamp 인스턴스만 취급한다.
private final Collection stamps = ...:

// 실수로 동전을 넣는다.
stamps.add(newCoinf...)); // "uncheckedcall"경고를 내뱉는다.


반복자의 로타입- 따라하지말것!
for (Iterator i = stamps.iterator(); i.hasNext(); ) {
    Stamp stamp= (Stamp) i.next(); // CLassCastException을던진다. stamp. cancel();
}
~~~

ClassCastException이 발생하면 stamps에 동전을 넣은 지점을 찾기 위해 코드 전체를 훑어봐야 할 수도 있다. 
// Stamp 인스턴스만 취급한다. 주석은 컴파일러가 이해하지 못하니 별 도움이 되지 못한다.


매개변수화된 컬렉션타입- 타입 안전성 확보!
~~~java
Collection stamps = new Collection<String>();

stamps.add(new Coin(...)) // 실수로 stamps에 Coin을 넣으면 "unchecked call" 경고를 발생시킨다.
error: incompatible types: Coin cannot be converted to Stamp
stamps.add(new Coin());
               ^

for (Iterator i = stamps.iterator(); i.hasNext(); ) {
    Stamp stamp = (Stamp) i.next(); // ClassCastException
    stamp.cancel();
}

Collection<Stamp> stamps = ... // 타입 안정성을 확보
~~~

로 타입(타입매개변수가 없는 제네릭 타입>을 쓰는걸 언어차원에서 막아놓지는 않았지만 절대로 써서는 안된다. 로타입을 쓰면 제네릭이 안겨주는 안전성과 표현력을 모두 잃게 된다. 
- 로 타입을 사용할때 문제는 실수라 다른 타입을 넣었을때 알아차리기 어려운 ide 경고만 발생한다는 것 이다.
- 제네릭 타입을 사용하면 컴파일시에 오류가 발생해 무엇이 잘못되었는지 정확하게 인지시켜 준다.

List<Object> 같은 매개변수화타입을 사용할때와 달리 List 같은 로 타입을 사용하면 타입 안전성을 잃게된다.

~~~java
// 런타임에실패한다. -unsafeAdd 메서드가 로 타입(List)을사용
public static void main(String[] args){
    List<String> strings=new ArrayList<>();
    unsafeAdd(strings,Integer.value0f(42));
    String s=strings.get(0); // 컴파일러가 자동으로 형변환 코드를 넣어준다.
}

private static void unsafeAdd (List list , Object o) {
    list.add(o); 
}

// 이 코드는 컴파일은 되지만 로 타입인 List를 사용하여 다음과 같은 경고가 발생한다.
warning: [unchecked] unchecked call to add(E) as a member of the raw type List
list.add(o);
         ^

// 이제 로타입인 List를 매개변수화 타입인 List<Object>로 바꾼 다음 다시 컴파일해보자. 이번에는 다음 오류메시지가 출력되며 컴파일조차 되지않는다.
error: incompatible types: List<String> cannot be converted to List<Obiect>
unsafeAdd (strings, Integer. value0f(42));
    ^
~~~


2개의 집합(set)을 받아 공통원소를 반환하는 메서드를 작성한다고 해보자.
다음은 제네릭을 처음 접하는 사람이 작성할법한 코드다.
~~~java
// 모르는 타입의 원소도 받는 로타입을 사용했다.
static int numElementsInCommon(Set s1, Set s2) {
    int result = 0;
    for (Obiect 01 : s1)
        if (s2. contains (01)) result++;
    return result;
}
~~~

비한정적 와일드 카드 타입을 사용하라.- 타입 안전하며 유연하다.
~~~java
static int numelementsInCommon(Set<?> s1, Set<?> set2) { ... }
~~~

Set<?>와 로 타입인 Set의 차이는무엇일까? 물음표가 무언가 멋진 일을 해주는걸까?
특징을 간단히 말하자면 와일드카드 타입은 안전하고, 로 타입은 안전하지않다.
로 타입 컬렉션에는 아무 원소나 넣을 수 있으니 타입 불변식을 훼손하기쉽다(unsafeAdd참조).
반면, CoLlection<?>에는 (nuLl 외에는) 어떤 원소도 넣을 수 없다. 
다른 원소를 넣으려 하면 컴파일할 때 다음의 오류메시지를 보게 될 것이다.
~~~java
error: incompatible types: String cannot be converted to CAP#1
c.add("verboten");
       ^
where CAP#1 is a fresh type-variable: 
    CAP#1 extends Obiect from capture of ?
~~~

로 타입을 쓰지 말라는 규칙에도 소소한 예외가 몇개있다. 
cLass 리터럴에는 로 타입을 써야한다. 자바 명세는 class리터럴에 매개변수화 타입을 사용 하지 못하게했다.( 배열과 기본타입은 허용한다 ) 
예를 들어 List.clas, String[].class, int.class는 허용하고 List<String>.class와 List<?>.class는 허용하지 않는다.

두번째 예외는 런타임에는 제네릭타입 정보가 지워지므로 instanceof 연산자는 비한정적 와일드카드 타입이외의 매개변수화 타입에는 적용할 수 없다. 
그리고 로 타입이든 비한정적 와일드카드 타입이든 instanceof는 완전히 똑같이 동작한다. 
비한정적 와일드카드 타입의 꺾쇠괄호와 물음표는 아무런 역할 없이 코드만 지저분하게 만드므로, 차라리 로 타입을 쓰는편이 깔끔하다.
