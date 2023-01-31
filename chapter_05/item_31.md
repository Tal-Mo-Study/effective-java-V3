# 한정적 와일드 카드를 사용해 API 유연성을 높이라 (PECS)

매개변수화 타입은 불공변이다. 
서로 다른 타입 Type1과 Type2가 있을때 List<Type1>은 List<Type2>의 하위 타입도 상위 타입도 아니다.

```java 
// 와일드카드 타입을 사용하지 않은 pushAll 메서드 - 결함이있다!
public void pushAll(Iterable<E> src) { 
    for (E e : src) push(e);
}
```

Integer는 Number의 하위타입이니 잘동작한다. 아니, 논리적으로는 잘 동작해야 할 것 같다.
```java
Stack<Number> numberStack = new Stack<>();
Iterable<Integer> integers = ...; 
numberStack.pushAll(integers);
```

하지만 실제로는 다음의 오류 메시지가 뜬다. 매개변수화 타입이 불공변이기 때문이다.
```java
StackTest.java:7: error: incompatible types: Iterable<Integer> 
cannot be converted to Iterable<Number>
        numberStack.pushAll(integers);
                            ^
```

자바는 이런 상황에 대처할 수 있는 한정적 와일드카드 타입이라는 특별한 매개변수화 타입을 지원한다.
pushAll의 입력 매개변수 타입은
'E의 Iterable'이 아니라'E의 하위타입의 Iterable'이어야 하며, 와일드 카드타입 Iterable<? extends E>가 정확히 이런 뜻 이다.(사실 extends 라는 키워드는 이 상황에 딱어울리지는 않는다. 하위 타입이란 자기 자신도 포함하지만, 그렇다고 자신을 확장(extends)한 것은 아니기 때문이다.)
```java
// E생산자(producer) 매개변수에 와일드카드 타입 적용3
public void pushAll (Iterable<? extends E> src) { 
    for (E e : src) push(e); 
}
```

popAll메서드는 stack안의 모든 원소를 주어진 컬렉션으로 옮겨 담는다. 다음처럼 작성했다고 해보자.
```java
public void popAll (Collection<E> dst) {
    while (!isEmpty()) dst.add(pop());
}
```

```java
// 컴파일 하면 Collection<object>는 Collection<Number>의 하위타입이 아니다.
Stack Number> numberStack = new Stack<>();
Collection<Object> obiects = ...; 
numberStack.popAll(objects);
```

이번에도 와일드카드 타입으로 해결할 수 있다.
이번에는 popAll의 입력 매개변수의 타입이 'E의 Collection'이 아니라 'E의 상위 타입의 Collection'이어야한다. (모든타입은 자기 자신의 상위타입이다) 와일드 카드 타입을 사용한 Collection<? super E>가 정확히 이런 의미다. 이를 popAll에 적용해보자.
```java
// E 소비자(consumer) 매개변수에 와일드카드 타입 적용
public void popAll(Collection<? super E> dst){
        while(!isEmpty()) dst.add(pop());
}
```
```java
// stream의 map 코드
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
```


유연성을 극대화 하려면 원소의 생산자나 소비자용 입력 매개변수에 와일드카드 타입을 사용하라.
입력 매개변수가 생산자와 소비자 역할을 동시에 한다면 와일드카드 타입을 써도 좋을게 없다.
``` 펙스(PECS)*: producer-extends, consumer-super ```
매개변수화 타입 T가 생산자라면<? extends T>를 사용하고, 소비자라면<? super T> 를 사용하라.

반환타입에는 한정적 와일드카드 타입을 사용하면 안된다. 유연성을 높여주기는 커녕 클라이언트 코드에서도 와일드카드 타입을 써야하기 때문이다.

