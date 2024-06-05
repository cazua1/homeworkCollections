import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {
    private class MyArrayListIterator<T> implements Iterator<T> {
        int size = size();
        int currentPointer;

        MyArrayListIterator() {
            currentPointer = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPointer < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) get(currentPointer++);
        }
    }

    private final int defaultCapacity = 10;
    private int size = 0;
    private T[] data;


    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        }
        data = (T[]) new Object[capacity];
    }

    public MyArrayList() {
        data = (T[]) new Object[defaultCapacity];
    }

    public MyArrayList(MyArrayList<T> list) {
        data = (T[]) new Object[list.size];
        addAll(list);
    }

    public static <T extends Comparable<T>> void sort(MyArrayList<T> list) {
        list.sort();
    }

    public void add(T item) {
        increaseCapacity(size + 1);
        data[size++] = item;
    }

    public void addAll(MyArrayList<? extends T> collection) {
        increaseCapacity(size + collection.size());
        Iterator<? extends T> iterator = collection.iterator();
        for (int i = 0; i < collection.size(); i++) {
            data[size++] = iterator.next();
        }
    }

    public T get(int index) {
        if (isOutOfIndex(index)) {
            return data[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(int index) {
        if (isOutOfIndex(index)) {
            int numberOfItemsAfterIndex = size - index - 1;
            System.arraycopy(data, index + 1, data, index, numberOfItemsAfterIndex);
            data[size--] = null;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator<>();
    }

    public void sort() {
        boolean sorted;
        do {
            sorted = false;
            for (int i = 0; i < size - 1; i++) {
                if (((Comparable<T>) data[i]).compareTo(data[i + 1]) > 0) {
                    T temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    sorted = true;
                }
            }
        } while (sorted);
    }

    private void increaseCapacity(int minCapacity) {
        int oldCapacity = data.length;

        if (minCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be non-negative");
        } else if (oldCapacity < minCapacity) {
            int newCapacity = Math.max(minCapacity, oldCapacity * 2);
            T[] oldData = (T[]) new Object[oldCapacity];
            System.arraycopy(data, 0, oldData, 0, oldCapacity);
            data = (T[]) new Object[newCapacity];
            System.arraycopy(oldData, 0, data,0, size);
        }
    }

    private boolean isOutOfIndex(int index) {
        return index >= 0 && index < size;
    }
}