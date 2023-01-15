# Item03. private 생성자나 열거 타입으로 싱글턴임을 보증하라

[메인페이지 이동](../README.md)

```
싱글턴(singleton) : 인스턴스를 오직 하나만 생성 할 수 있는 클래스
싱글턴의 전형적인 예로는 함수와 같은 무상태 객체나 설꼐상 유일해야 하는 시스템 컴포넌트를 들 수 있다.
```

## 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다. </br>

---

> public static final 필드 방식의 싱글턴
>
> > 장점1 : public static 접근제어자로 싱글턴임이 명백히 들어남</br>
> > 장점2 : 간결하다

```
예제소스1)
public class Yunsu{
  public static final Yunsu INSTANCE = new Yunsu();
  private Yunsu(){};
  .
  .
  .
}
```

private 생성자는 public static final Yunsu라는 필드를 초기화할때 한번만 실행된다.</br>
따라서 전체 시스템에서 하나뿐임이 보장이되지만 클라이언트는 손 쓸 방법이 없다.</br>
예외는 단 한가지, 권한이 있는 클라이언트는 리플렉션 API인 **AccessibleObject.setAccessible**을 사용해 생성자를 호출 가능하다.</br>
이러한 공격을 방어 하려면 생성자를 수정해서 두번째 객체가 생성되려 할 때 예외를 발생 시키면 된다.</br>

```
예제소스2)
public class Yunsu{
  public static final Yunsu INSTANCE = new Yunsu();
  private Yunsu(){
    if(Yunsu.INSTANCE != null){throws Exception("두번째 인스턴스 생성은안돼")};
    super();
  }
  .
  .
  .
}
```

</br></br></br>

> 정적 팩터리 메서드를 활용한 방식
>
> > 장점1: 메서드를 활용하기 때문에 수정을 통해 싱글턴이 아니게끔 변경 할 수 있다. </br>
> > 장점2: 원한다면 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다</br>
> > 장점3: 정적 팩터리의 메서드 참조를 공급자(supplier)로 사용 할 수 있다는 점</br>
> > 단, 싱글턴 클래스를 직렬화 하려면 readResolve 메서드를 제공해야한다. **그렇지 않으면 역직렬화시 새로운 인스턴스가 발생한다.**

```
public class Yunsu{
  private static final Yunsu INSTANCE = new Yunsu();
  private Yunsu(){};
  public static Yunsu getInstance(){return INSTANCE;}

  //싱글턴임을 보장해주는 readResolve메서드
  private Object readResolve(){
    //진짜 싱글턴객체를 반환하고, 새로운 객체는 G.C에 맡긴다.
    return INSTANCE;
  }
  .
  .
  .
}
```

---

</br></br></br>

> 열거 타입 방식의 싱글턴 - 바람직한 방식
>
> > 장점1 : 더 간결하고 추가노력 없이 직렬화 할 수 있다
> > 장점2 : 심지어 아주 복잡한 직렬화 상황이나 리플렉션 공격에서도 제2의 인스턴스가 생기는 일을 완벽히 막아준다.

```
public enum Yunsu{
  INSTANCE;
  .
  .
  .
}
```

대부분의 상황에서는 원소가 하나뿐인 열겨 타입이 싱글턴을 만드는 가장 좋은 방법이다.</br>
단, 만들려는 싱글턴이 Enum외의 클래스를 상속해야 한다면 이 방법은 사용 할 수 없다. </br>
_열거타입이 다른 인터페이스를 구현하도록 선언 할 수는 있다._
