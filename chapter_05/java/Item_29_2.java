package chapter_05.java;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

// 코드 29_01-Stack을 제네릭을 사용해 재작성한 Stack
public class Item_29_2<E> {

    private E[] elements;

    private int size = 0;

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // 배열 elements는 push(E)로 넘어온 E 인스턴스만 담는다.
    // 따라서 타입 안전성을 보장하지만,
    // 이 배열의 런타임 타입은 E[]가 아닌 Object[]다!
    @SuppressWarnings("unchecked")
    public Item_29_2() {
//        elements = new E[DEFAULT_INITIAL_CAPACITY]; -> E와 같은 실체화 불가 타입으로는 배열을 만들 수 없다(컴파일 오류)
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // 일반적으로 type safe하지 않음
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null; // 다 쓴 참조 해제
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    /**
     * 이 코드는 컴파일 당시에는 에러가 발생하지 않는다.
     * 하지만 실제 코드를 실행시켜보면 ClassCastException이 발생하는데,
     * 우선 타입 캐스팅 연산자는 컴파일러가 검사하지 않기 때문에 캐스팅의 가능여부를 떠나 참조변수에 저장이 가능한지만 검사한다,
     * 그리고 컴파일 이후 타입 매개변수는 소거되기 때문에 Object타입이 되기 때문에
     * 이 이후에는 strList.add(”abc”) 같은 코드를 작성해도 문제 없는 코드라고 판단하는 것이다.
     * 이러한 경우를 힙 오염(heap pollution)이라 한다.
     */
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3);
        Object obj = list;
        List<String> strList = (List<String>) obj;
        System.out.println("strList.get(1) = " + strList.get(1));
    }
}
