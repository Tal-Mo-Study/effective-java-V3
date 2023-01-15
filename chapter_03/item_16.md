# Item16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

[메인페이지 이동](../README.md)

```
접근자 메서드 = Getter, Setter
public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다.
불변 필드라면 노출해도 덜 위험하지만 완전히 안심 할 수는 없다.
하지만 pcakage-private클래스나 private 중첩 클래스에서는 종종 필드를 노출하는 편이 나을 때도 있다.
```

## 아래처럼 작성된 코드는 퇴보한 클래스 코드 작성방법이다

---

> 데이터 필드에 직접 접근 할 수 있으니 캡슐화의 이점을 제공하지 못하며 외부에서의 접근에 취약하다.

```
public class Point{
  public double x;
  public double y;
}
```

</br>

## 접근자와 변경자 메서드를 활용해 클래스 내의 데이터를 캡슐화 한다.

---

> 패키지 바깥에서 접근할 수 있는 클래스라면 접근자를 제공함으로써 캡슐화의 이점과 유연성을 갖게 된다. </br>

```
public class Point{
  private double x;
  private double y;

  public double getX(){return x};
  public double getY(){return y};
  public void setX(double x){this.x = x;}
  public void setY(double y){this.y = y;}
}
```

</br>

## 불변 필드를 노출한 public 클래스 (비추)

---

> 직접 노출할 때의 단점이 조금은 줄어들지만 여전히 좋은 방식이 아니다. </br>
> API를 변경하지 않고는 표현식을 바꿀 수 없고, 필드를 읽을 때 부수 작업을 수행할 수 없다는 단점은 여전하다. </br>
> 단, 불변식은 보장 할 수 있게 된다.......

```
public final class Time{
  private static final int HOURS_PER_DAY    = 24;
  private static final int MINUTES_PER_HOUR = 60;

  public final int hour;
  public final int minute;

  public Time(int hour, int minute){
    if(hour<0 || hour >= HOURS_PER_DAY)
      throw new IllegalArgumentException("시간: "+ hour);
    if(minute<0 || minute >= MINUTES_PER_HOUR)
      throw new IllegalArgumentException("분: "+minute);
    this.hour = hour;
    this.minute = minute;
  }
}
```

</br>

## 대표적인 예로 은행 계좌 예제가 있다.

---

> 아래와 같은 방식으로 만들게 되면 계좌 잔액에 대해 문제가 발생할 여지가 충분하다.

```
public class Account {

    public int accountNo;
    public int name;
    public int money;

    public Account(int accountNo, int name, int money) {
        this.accountNo = accountNo;
        this.name = name;
        this.money = money;
    }
}
```

> 은행 계좌의 특성을 고려해서 반영한 소스 코드

```
public class Account {

    private int accountNo;
    private int name;
    private int money;

    public Account(int accountNo, int name, int money) {
        this.accountNo = accountNo;
        this.name = name;
        this.money = money;
    }
    public int getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }
    public int getName() {
        return name;
    }
    public void setName(int name) {
        this.name = name;
    }
    public int getMoney() {
        return money;
    }
    //출금
    public void withDraw(int amount){
        if(amount>this.money)
            throw new IllegalArgumentException("계좌의 잔액이 부족합니다.");
        money-=amount;
    }
    //입금
    public void deposit(int amount){
        if(amount<=0)
            throw new IllegalArgumentException("입금액이 잚 못 되었습니다.");
        money+=amount;
    }
}

```
