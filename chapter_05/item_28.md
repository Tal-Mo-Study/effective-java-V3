# Item28. 배열보다는 리스트를 사용하라

[메인페이지 이동](../README.md)

## 배열과 리스트의 차이

</br>

### 1. 공변, 불공변

> 배열은 공변

공변: 함께 변한다. </br>
Sub가 Super의 하위 타입이라면 배열 Sub[]는 배열 Super[]의 하위 타입이된다.


```
Object[] obectArray = new Long[1]; 

objectArray[0] = "타입이 달라 넣을 수 없다."; // java.lang.ArrayStoreException 던진다.

```

> 제네릭은 불공변

> 제네릭 타입: 클래스와 인터페이스 선언에 타입 매개변수가 쓰이면 이를 제네릭 클래스 혹은 제네릭 인터페이스라 하고, 이를 통틀어 제네릭 타입이라한다.


서로 다른 타입 Type1과 Type2가 있을 때, List<Type1>과 List<Type2>는 상위타입도, 하위 타입도 아니다.

```
List<Object> ol = new ArrayList<Long>(); //호환되지 않아 컴파일 시점에 실패

ol.add("String");
```

### 2. 실체화

> 배열은 실체화된다.

배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다. </br>

ex) Long 배열에 String값을 넣으려 할때 ArrayStoreException 발생


> 제네릭은 런타임에 타입 정보 소거

원소 타입을 컴파일 타입에만 검사하며 런타임에는 알수조차 없다.

소거는 제네릭 지원전의 레거시 코드와 제네릭 타입을 함께 사용할 수 있게 해주는 메커니즘으로, 자바 5가 제네릭으로 순조롭게 전환될 수 있도록 해줬다. [item26](../chapter_05/item_26.md)

</br>

### 제네릭 배열 생성을 허용하지 않는 이유

```
List<String>[] stringLists = new List<String>[1]; //제네릭 배열 생성된다고 가정 - 컴파일되지 않는다. 

List<Integer> intList = List.of(42);

Object[] objects = stringLists; // 배열은 공변으로 할당가능

objects[0] = intList; // 제네릭은 소거 방식으로 구현되어서 성공한다.
                      // 런타임에 List<Integer> 인스턴스 타입 -> List,
                      // List<String>[] 인스턴스 타입 -> List[]가 되어 
                      //배열의 ArrayStoreException 발생하지 않음

String s = s[0].get(0); 
// List<String> 인스턴스만 담겠다고 선언한 stringLists 배열에 List<Integer> 인스턴스가 저장되어있고, 꺼낼때 컴파일러가 자동으로 Integer를 String으로 형변환하려하는데, 런타임시에 ClassCastException이 발생한다.

=> 제네릭 배열이 생성되지 않도록 컴파일오류를 내야한다.
```


정리

```
배열과 제네릭에는 매우 다른 타입 규칙이 적용된다.
배열은 공변이고 실체화 되는 반면, 제네릭은 불공변이고 타입 정보가 소거된다.
배열은 런타임에는 타입 안전하고 컴파일 타입에는 불안전, 제네릭은 반대다.

둘을 섞어쓰다가 컴파일 오류나 경고를 만나면, 가장 먼저 배열을 리스트로 대체하는 방법을 적용해보자.

```

