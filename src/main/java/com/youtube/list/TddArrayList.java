package com.youtube.list;

import java.util.AbstractList;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class TddArrayList<E> extends AbstractList<E>
{
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private E[] backingArray;
    private int size;

    public TddArrayList()
    {
        this.backingArray = (E[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public TddArrayList(Collection<E> elements)
    {
        this.backingArray = (E[]) new Object[elements.size()];
        this.size = elements.size();

        int index = 0;
        for (E element : elements)
        {
            backingArray[index] = element;

            index++;
        }
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public E get(int index)
    {
        throwIfIndexIsOutOfBounds(index, size - 1);

        return backingArray[index];
    }

    @Override
    public E set(int index, E element)
    {
        throwIfIndexIsOutOfBounds(index, size - 1);

        E unsetElement = backingArray[index];

        backingArray[index] = element;

        return unsetElement;
    }

    @Override
    public void add(int index, E element)
    {
        throwIfIndexIsOutOfBounds(index, size);
        ensureCapacity(size + 1);

        for (int i = size - 1; i >= index; i--)
        {
            backingArray[i + 1] = backingArray[i];
        }

        backingArray[index] = element;

        size++;
    }

    @Override
    public E remove(int index)
    {
        throwIfIndexIsOutOfBounds(index, size - 1);

        E removedElement = backingArray[index];

        for (int i = index; i < size - 1; i++)
        {
            backingArray[i] = backingArray[i + 1];
        }

        backingArray[size - 1] = null;

        size--;

        return removedElement;
    }

    int currentCapacity()
    {
        return backingArray.length;
    }

    private void throwIfIndexIsOutOfBounds(int index, int maximumIndex)
    {
        if (index < 0 || index > maximumIndex)
        {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void ensureCapacity(int desiredCapacity)
    {
        if (desiredCapacity <= currentCapacity())
        {
            return;
        }

        E[] newBackingArray = (E[]) new Object[currentCapacity() * GROWTH_FACTOR];

        for (int i = 0; i < size; i++)
        {
            newBackingArray[i] = backingArray[i];
        }

        backingArray = newBackingArray;
    }
}
