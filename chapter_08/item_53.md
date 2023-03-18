# Item53. 가변인수는 신중히 사용하라

[메인페이지 이동](../README.md)


```
가변인수 메서드는 명시한 타입의 인수를 0개이상 받을 수 있다.

가변인수를 호출하면, 가장 먼저 인수의 개수와 길이가 같은 배열을 만들고 인수들을 이 배열에 저장하여 가변인수 메서드에 건내준다.
```

</br>

> [간단한 활용 예](./java//Item_53.java) 
```
    static int sum(int... args){
        int sum=0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }
```


> 인수가 1개 이상이어야 할 떄

> [잘못된 예시](./java//Item_53.java) 

```
    static int minV1(int... args){
        if(args.length == 0)
            throw new IllegalArgumentException("인수가 1개 이상 필요");
        int min = args[0];
        for(int i=1;i<args.length;i++){
            if(args[i]<min) min = args[i];
        }
        return min;
    }
```

#### 문제점

1. 인수를 0개만 넣어 호출시 런타임에 실패한다는점.

2. 코드 지저분
    - args 유효성 검사를 명시적으로 해야한다.
    - min의 초기값을 Integer.MAX_VALUE로 설정 안한다면, for-each문을 사용할 수 없다.

> [해결 방법](./java//Item_53.java) 

첫번째로 평범한 매개변수를 받고, 가변인수는 두 번째로 받으면 된다.

```
    static int minV2(int firstArg, int... remainingArgs){
        int min = firstArg;
        for (int i : remainingArgs) {
            if (i < min) min = i;
        }
        return min;
    }
```

</br>

가변인수는 인수 개수가 정해지지 않았을 때 유용하다.
(printf, 리플렉션)


</br>

> 성능

가변인수 메서드는 호출될 때마다 배열을 새로 하나 할당하고 초기화하기 때문에

성능에 민감한 상황이라면 가변인수가 걸림돌이 될 수 있다.



</br>

> 이 비용을 감당할 수는 없지만 가변인수의 유연성이 필요할 때 선택할 수 있는 패턴

ex) 해당 메서드의 95%가 인수를 3개 이하로 사용한다고 가정</br>
=> 인수가 0개인 것부터 4개인것까지 총 4개 다중적의 하면 된다.

```
public void foo() {}
public void foo(int a1) {}
public void foo(int a1, int a2) {}
public void foo(int a1, int a2, int a3) {}
public void foo(int a1, int a2, int a3, int... rest) {}
```

<a href=https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html> EnumSet의 정적 팩터리</a>도 이 기법을 사용해 열거 타입 집합 생성 비용을 최소화한다. </br>

=> EnumSet은 비트필드를 대체하면서 성능까지 유지해야하므로 아주 적절하게 활용한 예이다.




</br>

### 정리

> 인수 개수가 일정하지 않은 메서드를 정의해야 한다면 가변인수가 반드시 필요하다. 메서드를 정의할 때 필수 매개변수는 가변인수 앞에 두고, 가변인수를 사용할 때는 성능 문제까지 고려하자. </b> </br>