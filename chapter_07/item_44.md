# Item44. 표준 함수형 인터페이스를 사용하라

[메인페이지 이동](../README.md)


## 함수 객체를 매개변수로 받는 생성자와 메서드를 더 많이 만들어야 한다

> LinkedHashMap의 removeEldestEntry 재정의

### [소스 보기](./java/item44/CustomLinkedHashMap.java) </br></br></br>

> 람다 version

### [소스 보기](./java/item44/EldestEntryRemovalFunction.java) </br></br></br>

## 필요한 용도에 맞는게 있다면 직접 구현하지 말고, 표준 함수형 인터페이스를 사용할 것

> 표준 함수형 인터페이스
>>인터페이스	함수 시그니처	예
> 
>>UnaryOperator<T>	T apply(T t)	String::toLowerCase
> 
>>BinaryOperator<T>	T apply(T t1, T t2)	BigInteger::add
> 
>>Predicate<T>	boolean test(T t)	Collection::isEmpty
> 
>>Function<T>	R apply(T t)	Arrays::asList
> 
>>Supplier<T>	T get()	Instant::now
> 
>>Consumer<T>	void accept(T t)	System.out::println

## 전용 함수형 인터페이스를 구현하면 좋은 경우(ex: Comparator)

> 자주 쓰이며, 이름 자체가 용도를 명확히 설명해준다
> 
> 반드시 따라야 하는 규약이 있다
> 
> 유용한 디폴트 메서드를 제공할 수 있다
> 

## 직접 만든 함수형 인터페이스에는 항상 @FunctionalInterface 애너테이션을 사용하라

> 주의 사항
> 서로 다른 함수형 인터페이스를 같은 위치의 인수로 받는 메서드들을 다중 정의해서는 안된다

## 결론
> Java 8부터는 API를 설계할 때 람다도 염두에 두어야 한다
> 
> 입력값과 반환값에 함수형 인터페이스 타입을 활용하고 일반적인 경우 표준 함수형 인터페이스를 사용하되,
> 직접 함수형 인터페이스를 만들어 쓰는 편이 나은 경우가 있다