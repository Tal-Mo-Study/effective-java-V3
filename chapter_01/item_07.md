# Item07. 다 쓴 객체 참조를 해제하라

[메인페이지 이동](../README.md)

```
메모리 누수는 겉으로 잘 드러나지 않아 시스템에 오래 잠복하는 사례도 있다.
이런 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야 발견되기도 한다.
그래서 이런 종류의 문제는 예방법을 익혀두는 것이 매우 중요하다.
```

> 객체 참조를 null처리 하는 일은 예외적인 경우여야한다. </br>
> 자기 메모리를 직접 관리하는 클래스(ex. stack)라면 항시 메모리 누수에 주의해야한다. </br>
> 캐시메모리 역시 메모리 누수를 일으키는 주범이다

</br></br>

## 메모리 누수가 일어나는 위치는?

---

```
public class Stack{
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  public Stack(){
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e){
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop(){
    if(size==0) throws new EmptyStackException();
    return elements[--size];
  }

  /**
  * 원소를 위한 공간을 적어도 하나 이상 확보한다.
  * 배열크기를 추가로 늘려야할 경우 두배로 늘린다.
  */
  private void ensureCapacity(){
    if(elements.length == size)
      elements = Arrays.copyOf(elements, 2*size+1);
  }

}
```

> pop하는 부분에서 pop을 하고 난뒤에도 해당 객체는 참조해제 되지 않았다.

## 제대로 pop메서드를 구현한다면??

```
public Object pop(){
  if(size==0) throws new EmptyStackExcepiton();
  Object result = elements[--size];
  elements[size]=null; // 다 쓴 참조 해제
  return result;
}
```
