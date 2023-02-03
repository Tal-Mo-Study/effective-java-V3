# Item23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라

[메인페이지 이동](../README.md)


## 태그 달린 클래스 

두 가지 이상의 의미를 표현할 수 있으며, 그중 현재 표현하는 의미를 태그 값을 가지고 알려주는 클래스

```
class Figure {
    enum Shape { RECTANGLE, CIRCLE };

    // 태그 필드 - 현재 모양을 나타낸다.
    final Shape shape;

    // 다음 필드들은 모양이 사각형(RECTANGLE)일 때만 쓰인다.
    double length;
    double width;

    // 다음 필드는 모양이 원(CIRCLE)일 때만 쓰인다.
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}

```

태그 달린 클래스는 장황하고, 오류를 내기 쉽고, 비효율 적이다.
태그 달린 클래스는 클래스 계층구조를 어설프게 흉내낸 아류일 뿐이다.

</br>

## 태그 달린 클래스를 클래스 계층구조로 바꾸는 방법

1. 계층구조의 루트가될 추상 클래스 정의, 태그 값에 따라 달라지는 메서드들을 루트 클래스의 추상 메서드로 선언
동작 일정한 메서드, 공통 데이터 필드 루트 클래스로 올린다.

```
abstract class Figure {
    abstract double area();
}

```

2. 루트 클래스 확장한 구체 클래스를 의미별로 하나씩 정의
```
class Circle extends Figure {
    final double radius;

    Circle(double radius) { this.radius = radius; }

    @Override double area() { return Math.PI * (radius * radius); }
}

class Rectangle extends Figure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width  = width;
    }
    @Override double area() { return length * width; }
}
```

> 계층 구조 장점

- 간결하고 명확
- 타입이 의미별로 따로 존재해 변수의 의미를 명시하거나 제한 할 수 있다.

- 추상메서드를 모두 구현했는지 컴파일러가 확인해준다.(switch -case문 실수 방지)
- 타입 사이의 자연스러운 계층 관계를 반영할 수 있어서 유연성은 물론 컴파일 타임 검사능력을 높여준다.