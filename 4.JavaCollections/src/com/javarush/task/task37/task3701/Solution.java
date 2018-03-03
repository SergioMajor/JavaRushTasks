package com.javarush.task.task37.task3701;

import java.util.*;
import java.util.function.Consumer;

/* 
Круговой итератор
*/
public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        RoundIterator() {}

        public boolean hasNext() {
            return cursor != Solution.this.size();
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= Solution.this.size()) {
                cursor = i = 0;
                return Solution.this.get(lastRet = i - 1);
            }
            Object[] elementData = Solution.this.toArray();
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;

            if (!hasNext())
                cursor = 0;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                Solution.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            final int size = Solution.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = (Object[]) Solution.this.get(cursor);
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((T) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
