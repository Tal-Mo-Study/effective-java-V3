# 인터페이스는 구현하는 쪽을 생각해 설계하라
자바8에서는 핵심 컬렉션 인터페이스들에 다수의 디폴트 메서드가 추가되었다. 
주로 람다(7장참조)를 활용하기 위해서다. 자바 라이브러리의 디폴트 메서드는 코드 품질이 높고 범용적이라 대부분 상황에서 잘 작동한다. 
하지만 생각할수있는 모든 상황에서 불변식을 해치지 않는 디폴트 메서드를 작성하기란 어려운법이다.


인터페이스를 설계할때는 세심한 주의를 기울인다.
** 인터페이스는 마치 API와 같이 생각해야한다. 변경이 필요할때 마음대로 변경을 진행하면 안되며, 해당 인터페이스를 구현하거나 사용한 class도 고려해야 한다.**

아래 코드는 디폴트 메서드가(컴파일이 성공하더라도) 기존 구현체에 런타임 오류를 일으키는 예제다.
apache.commons.collections4.collection.SynchronizedCollection.java 의 코드

~~~java
// desc : 자바8의 Collection 인터페이스에 추가된 디폴트 메서드
default boolean removeIf(Predicate<? super E> filter) { 
    Obiects.requireNonNull(filter);
    boolean result = false;
    for (Iterator<E> it = iterator(); it.hasNext(); ){
        if(filter.test(it.next())) {
            it.remove();
            result=true;
        }
    }
    return result; 
}
~~~
~~~java
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Decorates another {@link Collection} to synchronize its behaviour
 * for a multi-threaded environment.
 * <p>
 * Iterators must be manually synchronized:
 * <pre>
 * synchronized (coll) {
 *   Iterator it = coll.iterator();
 *   // do stuff with iterator
 * }
 * </pre>
 * <p>
 * This class is Serializable from Commons Collections 3.1.
 *
 * @param <E> the type of the elements in the collection
 * @since 3.0
 * @version $Id: SynchronizedCollection.java 1479401 2013-05-05 21:51:47Z tn $
 */
public class SynchronizedCollection<E> implements Collection<E>, Serializable {

    /** Serialization version */
    private static final long serialVersionUID = 2412805092710877986L;

    /** The collection to decorate */
    private final Collection<E> collection;
    /** The object to lock on, needed for List/SortedSet views */
    protected final Object lock;

    /**
     * Factory method to create a synchronized collection.
     *
     * @param <T> the type of the elements in the collection
     * @param coll  the collection to decorate, must not be null
     * @return a new synchronized collection
     * @throws IllegalArgumentException if collection is null
     * @since 4.0
     */
    public static <T> SynchronizedCollection<T> synchronizedCollection(final Collection<T> coll) {
        return new SynchronizedCollection<T>(coll);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor that wraps (not copies).
     *
     * @param collection  the collection to decorate, must not be null
     * @throws IllegalArgumentException if the collection is null
     */
    protected SynchronizedCollection(final Collection<E> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        this.collection = collection;
        this.lock = this;
    }

    /**
     * Constructor that wraps (not copies).
     *
     * @param collection  the collection to decorate, must not be null
     * @param lock  the lock object to use, must not be null
     * @throws IllegalArgumentException if the collection is null
     */
    protected SynchronizedCollection(final Collection<E> collection, final Object lock) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        this.collection = collection;
        this.lock = lock;
    }

    /**
     * Gets the collection being decorated.
     *
     * @return the decorated collection
     */
    protected Collection<E> decorated() {
        return collection;
    }

    //-----------------------------------------------------------------------

    public boolean add(final E object) {
        synchronized (lock) {
            return decorated().add(object);
        }
    }

    public boolean addAll(final Collection<? extends E> coll) {
        synchronized (lock) {
            return decorated().addAll(coll);
        }
    }

    public void clear() {
        synchronized (lock) {
            decorated().clear();
        }
    }

    public boolean contains(final Object object) {
        synchronized (lock) {
            return decorated().contains(object);
        }
    }

    public boolean containsAll(final Collection<?> coll) {
        synchronized (lock) {
            return decorated().containsAll(coll);
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return decorated().isEmpty();
        }
    }

    /**
     * Iterators must be manually synchronized.
     * <pre>
     * synchronized (coll) {
     *   Iterator it = coll.iterator();
     *   // do stuff with iterator
     * }
     * </pre>
     *
     * @return an iterator that must be manually synchronized on the collection
     */
    public Iterator<E> iterator() {
        return decorated().iterator();
    }

    public Object[] toArray() {
        synchronized (lock) {
            return decorated().toArray();
        }
    }

    public <T> T[] toArray(final T[] object) {
        synchronized (lock) {
            return decorated().toArray(object);
        }
    }

    public boolean remove(final Object object) {
        synchronized (lock) {
            return decorated().remove(object);
        }
    }

    public boolean removeAll(final Collection<?> coll) {
        synchronized (lock) {
            return decorated().removeAll(coll);
        }
    }

    public boolean retainAll(final Collection<?> coll) {
        synchronized (lock) {
            return decorated().retainAll(coll);
        }
    }

    public int size() {
        synchronized (lock) {
            return decorated().size();
        }
    }

    @Override
    public boolean equals(final Object object) {
        synchronized (lock) {
            if (object == this) {
                return true;
            }
            return object == this || decorated().equals(object);
        }
    }

    @Override
    public int hashCode() {
        synchronized (lock) {
            return decorated().hashCode();
        }
    }

    @Override
    public String toString() {
        synchronized (lock) {
            return decorated().toString();
        }
    }

}
~~~