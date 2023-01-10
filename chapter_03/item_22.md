# Item22. 인터페이스는 타입을 정의하는 용도로만 사용하라

[메인페이지 이동](../README.md)

```
인터페이스는 타입을 정의하는 용도로만 사용해야 한다.
상수 공개용 수단으로 사용하지 말자.
```

</br></br>

## 상수 인터페이스 안티 패턴 (지양)

> 상수 인터페이스 안티 패턴은 인터페이스를 잘못 사용한 예다.

```
public interface PhysicalConstants {
  //아보가드로 수(1/몰)
  static final double AVOGADROS_NUMBER = 6.022_149_852e23;

  //볼츠만 상수 (J/K)
  static final double BOSTZMANN_CONSTANT = 1.380_648_52e-23;

  //전자 질량 (kg)
  static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```

> 클래스 내부에서 사용하는 상수는 외부 인터페이스가 아니라 내부 구현에 해당한다. </br>
> 따라서 상수 인터페이스를 구현하는 것은 이 내부 구현을 클래스의 API로 노출하는 행위다.</br>
> final이 아닌 클래스가 상수 인터페이스를 구현한다면 모든 하위 클래스의 이름공간이 그 인터페이스가 정의한 상수로 오염된다.

</br></br>

## 위의 단점을 상쇄하는 유틸리티 클래스 버전 (지향)

```
public class PhysicalConstants{

  private PhysicalConstants(){} // 인스턴스화 방지

  //아보가드로 수(1/몰)
  static final double AVOGADROS_NUMBER = 6.022_149_852e23;

  //볼츠만 상수 (J/K)
  static final double BOSTZMANN_CONSTANT = 1.380_648_52e-23;

  //전자 질량 (kg)
  static final double ELECTRON_MASS = 9.109_383_56e-31;
}
```
