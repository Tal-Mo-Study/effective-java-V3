# Item05. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

[메인페이지 이동](../README.md)

```
클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면
싱글턴과 정적 유틸리티 클래스는 사용하지 않는것이 좋다. 이자원들은 클래스가 직접만들게 해서도 안된다.
대신 필요한 자원을 생성자에 넘겨주자!
의존 객체 주입 이라는 이 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 기막히게 개선해준다.
```

## 정적 유틸리티를 잘못 사용한 예제

---

> 유연하지 않고 테스트 하기 어렵다.

```
//사전에 의존한 맞춤법 검사 클래스라고 가정
public class SpellCheck {
  private static final Lexicon dictionary = ...;

  private SpellChecker() {}  //객체 생성방지

  public static boolean isValid(String word){...};
  public static List<String> suggestions(String typo){...};
}
```

## 싱글턴을 잘못 사용한 예제

> 유연하지 않고 테스트하기 어렵다.

```
//사전에 의존한 맞춤법 검사 클래스라고 가정
public class SpellCheck {
  private final Lexicon dictionary = ...;

  private SpellChecker(...) {}  //객체 생성방지
  public static SpellChecker INSTANCE = new SpellCheck(...);

  public static boolean isValid(String word){...};
  public static List<String> suggestions(String typo){...};
}

```

</br></br></br>

## 의존 객체 주입 방식

> 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식은 적합하지 않다</br>
> 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식 </br>
>
> > 장점 : 유연성과 테스트 용이성을 높여주고 확장성이 높다.</br>

```
//사전에 의존한 맞춤법 검사 클래스라고 가정
public class SpellCheck {
  private final Lexicon dictionary;

  public SpellChecker(Lexicon dictionary){
    this.dictionary = Objects.requireNonNull(dictionary);
  }

  public static boolean isValid(String word){...};
  public static List<String> suggestions(String typo){...};
}
```
