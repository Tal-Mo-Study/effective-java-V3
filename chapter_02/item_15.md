# Item15. 클래스와 멤버의 접근 권한을 최소화 해라
### 정보은닉의 장점
- 시스템 개발 속도를 높인다. 여러 컴포넌트를 병렬로 개발할 수 있다.
- 시스템 관리 비용을 낮춘다. 각 컴포넌트를 더 빨리 파악하여 디버깅할 수  있고, 다른 컴포넌트로 교체하는 부담도 적기 때문이다.
- 성능 개선이 필요요한 컴포넌트만 성능 최적화를 진행할 수 있다. (컴포넌트 단위의 수정만 진행할 수 있다.)
- 소프트웨어의 재사용성을 높혀준다.
- 큰 시스템의 제작 난이도를 줄여준다. (부분적으로, 컴포넌트 단위로 개발이 가능하다.)
- 

### 기본원칙 : 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다.

!!! 주의 사항
- Serializable 을 구현한 클래스에서는 그 필드들도 의도치 않게 공개 API가 될 수도 있다.(아이템 86, 87).
- 멤버 접근성을 좁히지 못하는 제약.
    - 상위 클래스의 메서드를 재정의 할 때는 접근수준을 상위 클래스 보다 좁게 설정할 수 없다.[JLS, 8.4 8.3]
- 테스트만을 위해 클래스, 인터페이스, 멤버를 공개 API로 만들어서는 안된다. (부득이할 때는 테스트 요소를 테스트 대상과 같은 패키지에 둔다. package-private)
- public 클래스의 인스턴스 필드는 되도록 public 이 아니어야 한다.(아이템 16)
- public 가변필드를 갖는 클래스는 일반적으로 스레드 안전하지 않다. 
- 길이가 0이 아닌 배열은 모두 변경이 가능하다. (객체 또한 변경이 가능하다.)
~~~java
public static final String[] value = {"a", "b"};

private static final String[] PRIVATE_VALUES = {"a", "b"};
public static final String[] value() {
    return PRIVATE_VALUE.clone();
}


List<String> privateValueList = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUE));
~~~