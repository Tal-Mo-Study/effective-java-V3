# Item58. 전통적인 for 문보다는 for-each 문을 사용하라

[메인페이지 이동](../README.md)


스트림이 제격인 작업이 있고 반복이 제격인 작업이 있다.


</br>


> 전통적인 for 문
```
for (Iterator<Element> i = c.iterator(); i.hasNext(); ){
    Element e = i.next();
    ... // e로 무언가를 한다.
}

for (int i=0; i <a.length; i++ ){
    ... // a[i]로 무언가를 한다.
}
```
#### 문제점

- 반복자와 인덱스 변수는 코드를 지저분하게 할 뿐 필요한건 원소들 뿐이다.
- 이처럼 쓰이는 요소 종류가 늘면 오류가 생길 가능성이 높아진다.
  - 1회 반복에서 반복자는 세 번 등장하며, 인덱스는 네번이나 등장하여 변수를 잘못 쓸 틈새가 넓어진다.
  - 잘못된 변수를 사용해도 컴파일러가 잡아주는 보장이없다.
- 컬렉션이냐 배열이냐에 따라 코드 형태가 상당히 달라진다.

</br>

> enhanced for statement

위의 문제는 for-each 문을 사용하면 모두 해결

반복자와 인덱스 변수를 사용하지 않아 코드가 깔끔하고 오류가 날 일도 없다.
하나의 관용구로 컬렉션과 배열을 모두 처리할 수 있어 어떤 컨테이너를 다루는지 신경 쓰지 않아도된다.

```
for (Element e: elements){
    ... // e로 무언가를 한다.
}
```

</br>

컬렉션이든 배열이든, for-each문을 사용해도 속도는 그대로다.
for-each문이 만들어내는 코드는 사람이 손으로 최적화한 것과 사실상 같기 때문

</br>

> 컬렉션 중첩 순회할때 for-each문의 이점이 더욱 커진다.

> [버그를 찾아보자](./java/Item_58_1.java)


```
    enum Suit { CLUB, DIAMOND, HEART, SPADE}
    enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, 
                NINE, TEN, JACK, QUEEN, KING}

                ...

    static Collection<Suit> suits = Arrays.asList(Suit.values());
    static Collection<Rank> ranks = Arrays.asList(Rank.values());

    List<Card> deck = new ArrayList<>();
    for(Iterator<Suit> i = suits.iterator(); i.hasNext();){
        for(Iterator<Rank> j = ranks.iterator(); j.hasNext();){
            deck.add(new Card(i.next(), j.next()));
        }   
    }
```

</br>

> [같은버그, 다른 증상](./java/Item_58_2.java)

예외를 던지진 않지만, 원하는 결과가 아니다.
```
    Collection<Face> faces = EnumSet.allOf(Face.class);

    for (Iterator<Face> i = faces.iterator(); i.hasNext(); )
        for (Iterator<Face> j = faces.iterator(); j.hasNext(); )
            System.out.println(i.next() + " " + j.next());
```

> [for-each문 중첩으로 해결!](./java/Item_58_2.java)
```
    for (Face f1 : faces)
        for (Face f2 : faces)
            System.out.println(f1 + " " + f2);
```

</br>

> for-each 문을 사용할 수 없는 3가지 상황

1. 파괴적인 필터링(destructive filtering): 
```
컬렉션을 순회하면서 선택된 원소를 제거해야 한다면 반복자의 remove 메서드를 호출해야한다.

자바 8부터는 Collection의 removeIf 메서드를 사용해 컬렉션을 명시적으로 순회하는 일을 피할 수 있다.
```

2. 변형(transforming)
```
리스트나 배열을 순회하면서 그 원소의 값 일부 혹은 전체를 교체해야 한다면 리스트의 반복자나 배열의 인덱스를 사용해야 한다.
```
3. 병렬 반복(parallel iteration)
```
여러 컬렉션을 병렬로 순회해야 한다면 각각의 반복자와 인덱스 변수를 사용해 엄격하고 명시적으로 제어해야한다.
```

</br>

> for-each문은 컬렉션과 배열은 물론 Iterable 인터페이스를 구현한 객체라면 무엇이든 순회할 수 있다.

```
public interface Iterable<E> {
    // 이 객체의 원소들을 순회하는 반복자를 반환한다.
    Iterator<E> iterator();
}
```

처음부터 직접 구현하기는 까다롭지만 원소들의 묶음을 표현하는 타입을 작성해야 한다면 Iterable을 구현하는 쪽으로 고민해보자.


</br>

### 정리

> 전통적인 for 문과 비교했을 때 for-each 문은 명료하고, 유연하고, 버그를 예방해준다. </br>
성능저하도 없다. 가능한 모든 곳에서 for 문이 아닌 for-each 문을 사용하자. </b> </br>