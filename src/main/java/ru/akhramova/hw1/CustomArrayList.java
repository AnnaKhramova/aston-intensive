package ru.akhramova.hw1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> {

    private Object[] array;

    private int length;

    private int free;

    public CustomArrayList() {
        this.array = new Object[2];
        this.length = array.length;
        this.free = array.length;
    }

    public CustomArrayList(E[] array) {
        this.array = array;
        this.length = array.length;
        this.free = 0;
    }

    public CustomArrayList(Collection<E> c) {
        Object[] a = c.toArray();
        if (a.length != 0) {
            array = Arrays.copyOf(a, length, Object[].class);
        }
    }

    private void expansion() {
        this.array = Arrays.copyOf(this.array, this.length * 2);
    }

    private void swap(Object[] array, int index, E el) {
        if (index < array.length - 1) {
            Object tmp = array[index];
            array[index] = el;
            if (tmp != null) {
                swap(array, index + 1, (E) tmp);
            }
        }
    }

    public void add(int index, E element) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("����� �� ������� ������");
        }
        if (this.free == 0) {
            expansion();
        }
        if (array[index] == null) {
            if (array[index - 1] == null) {
                throw new IllegalArgumentException("�������� ������");
            }
            swap(array, index, element);
            free--;
        }
    }

    public void addAll(Collection<? extends E> c) {
        for (var el : c) {
            add(length - free, el);
        }
    }

    public void clear() {
        for (int i = 0; i < length; i++) {
            array[i] = null;
        }
        free = length;
    }

    public E get(int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("����� �� ������� ������");
        }
        return (E) array[index];
    }

    public boolean isEmpty() {
        return free == length;
    }

    public void remove(int index) {
        swap(array, index, null);
        free++;
    }

    public void remove(Object o) {
        for (int i = 0; i < length; ) {
            if (array[i].equals((E) o)) {
                swap(array, i, null);
                i = length;
            }
        }
    }

    public void sort(Comparator<? super E> c) {
        if (c == null) {
            quickSort(array, 0, length - 1, Comparator.naturalOrder());
        } else {
            quickSort(array, 0, length - 1, c);
        }
    }

    public <E> void quickSort(Object[] source, int leftBorder, int rightBorder, Comparator c) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        Object pivot = source[(leftMarker + rightMarker) / 2];
        do {
            // ������� ����� ������ ����� ������� ���� ������� ������, ��� pivot
            while (c.compare(source[leftMarker], pivot) > 0) {
                leftMarker++;
            }
            // ������� ������ ������, ���� ������� ������, ��� pivot
            while (c.compare(source[rightMarker], pivot) > 0) {
                rightMarker--;
            }
            // ��������, �� ����� �������� ������� ��������, �� ������� ��������� �������
            if (leftMarker <= rightMarker) {
                // ����� ������ ����� ������ ������� ������ ���� �� ������ ��������� swap
                if (leftMarker < rightMarker) {
                    Object tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                // �������� �������, ����� �������� ����� �������
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // ��������� ���������� ��� ������
        if (leftMarker < rightBorder) {
            quickSort(source, leftMarker, rightBorder, c);
        }
        if (leftBorder < rightMarker) {
            quickSort(source, leftBorder, rightMarker, c);
        }
    }
}
