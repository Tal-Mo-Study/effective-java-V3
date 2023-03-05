# Item35. ordinal 메서드 대신 인스턴스 필드를 사용하라

[메인페이지 이동](../README.md)

```
열거 타입 상수에 연결된 값은 ordinal 메서드로 얻지 말고, 인스턴스 필드에 저장하자.
EnumSet과 EnumMap같은 열거 타입 기반의 범용 자료구조에 쓸 목적이 아니라면 ordinal 메서드는 절대 사용하지 말자.
```

<br>

## 열거 타입 상수값은 인스턴스 필드에 저장

---

>매개변수화 타입을 받는 정적 유틸리티 메서드는 보통 제네릭임

```
Code 35-1 : 동작은 하지만 유지보수하기 끔찍한 코드
 → 상수 선언 순서를 바꾸는 순간 numberOfMusicians 메서드는 오작동하고, 이미 사용중인 정수와 값이 같은 상수는 추가 불가
 → 또한, 값을 중간에 비워둘 수도 없음. 쓰지 않는 더미 상수를 같이 추가해야 원하는 값을 추가할 수 있다.

코드가 깔끔하지 못할 뿐 아니라, 쓰이지 않는 값이 많아질수록 실용성이 떨어짐
```

<br><br>

>Code

[Java Code Item_35](./java/Item_35)