# Item54. null이 아닌, 빈 컬렉션이나 배열을 반환하라

[메인페이지 이동](../README.md)

## null 반환 코드

### [소스 보기](./java/item54/ReturnNull.java) </br></br></br>

## 빈 컬렉션 반환 코드

### [소스 보기](./java/item54/ReturnEmptyCollection.java) </br></br></br>

## 빈 배열 반환 코드

### [소스 보기](./java/item54/ReturnEmptyArray.java) </br></br></br>


## 결론

> null이 아닌, 빈 배열이나 컬렉션을 반환하라
> 
> null을 반환하는 API는 사용하기 어렵고 오류처리 코드도 늘어난다
> 
> 그렇다고 성능이 좋은 것도 아니다