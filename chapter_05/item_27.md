# Item27. 비검사 경고를 제거하라

[메인페이지 이동](../README.md)

```
비검사 경고는 중요하니 무시하지 말자.
모든 비검사 경고는 런타임에 ClassCastException을 일으킬 수 있는 잠재적 가능성을 뜻하니 최선을 다해 제거하라.
경고를 없앨 방법을 찾지 못하겠다면, 그 코드가 타입 안전함을 증명하고 가능한 한 범위를 좁혀서
@SuppressWarnings("unchecked") 애너테이션으로 경고를 숨겨라.
그런 다음 경고를 숨기기로한 근거를 주석으로 남겨라
```

</br></br>

## 제네릭을 사용할때 마주치는 비검사 경고를 제거하라!

> 잘못된코드 사용시 컴파일 단계에서 친절하게 설명해준다
>
> > 단, JVM 옵션으로 -Xlint:uncheck 옵션을 추가해야한다.

```
// 잘못된 예시
Set<Lark> exaltation = new HashSet();



콘솔 경고창
Venery.java:4:: warning: [unchecked] unchecked conversion
    Set<Lark> exaltation = new HashSet();
                           ↑
  required: Set<Lark>
  found: HashSet


//올바른 방법, jdk1.7 부터는 제네릭 타입을 생략 가능하다
Set<Lark> exaltation = new HashSet<>();
```

> 가능한 모든 비검사 경고를 제거해야 한다.
> 그래야 그 코드의 타입 안정성을 보장 할 수 있다.
> 단! 경고를 제거 할 순 없지만 타입 안정성을 확신 할 수 있다면 @SuppressWarnings("unchecked") 어노테이션을 사용하라

</br></br>

## @SuppressWarnings("unchecked") 사용예제

> 기존 비검사 경고를 처리하지 않은 소스

```
public <T> T[] toArray(T[] a){
  if(a.length < size)
    return (T[]) Arrays.copyOf(elements, size, a.getClass());
  System.arraycopy(elements, 0, a, 0, size);
  if(a.length > size)
    a[size] = null;
  return a;
}
```

> 지역변수를 추가해 @SuppressWarnings 의 범위를 좁힌 소스코드 </br>
> 해당 어노테이션을 사용하는 근거를 주석으로 남긴다

```
public <T> T[] toArray(T[] a) {
  if(a.length < size){
    // 생성한 배열과 매개변수로 받은 배열의 타입이 모두T[]로 같으므로
    // 올바른 형변환이다
    @SuppressWarnings("unchecked")
    T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
    return result;
  }
  System.arraycopy(elements, 0, a, 0, size);
  if(a.length > size)
    a[size] = null;
  return a;
}
```
