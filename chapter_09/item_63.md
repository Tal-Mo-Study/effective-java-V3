# Item63. 문자열 연결은 느리니 주의하라

[메인페이지 이동](../README.md)


<b>문자열 연결 연산자로 문자열 n개를 잇는 시간은 n^2에 비례한다.</b>  </br>

문자열은 불변이라서 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야하므로 성능 저하는 피할 수 없는 결과다.


</br>

> 문자열 연결을 잘못 사용한 예 - 느리다.
```
public String statement(){
    String result = "";
    for(int i=0;i< nuumItems; i++)
        result += lineForItem(i);
    return result;
}
```
> StringBuilder를 사용하면 연결 성능이 크게 개선한다.
```
public String statement(){
    StringBuilder b = new StringBuilder(numItems() * LINE_WIDTH);
    for(int i=0;i< nuumItems; i++)
        b.append(lineForItem(i));
    return b.toString();
}
```

</br>

### 정리

> 원칙은 간단하다. 성능에 신경 써야 한다면 많은 문자열을 연결할 때는 문자열 연결 연산자(+)를 피하자.</br> 대신 StringBuilder의 append 메서드를 사용하라. 문자 배열을 사용하거나, 문자열을 (연결하지 않고) 하나씩 처리하는 방법도 있다.