import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MyArrayList <E> implements Serializable, Cloneable{
    transient Object[] elementData;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] DEFAULT_ARRAYLIST = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
            
    public MyArrayList(){
        this.elementData = DEFAULT_ARRAYLIST;
    }
    
    public MyArrayList(int length) {
        if(length > 0)
            this.elementData = new Object[length];
        else if(length == 0)
            this.elementData = DEFAULT_ARRAYLIST;
        else
            throw new OutOfBoundsException("Invalid length!");
    }

    public MyArrayList(Collection<? extends E> c) {
        this.elementData = c.toArray();
        
        if((size = elementData.length) != 0) {
            if(this.elementData.getClass() != Object[].class) {
                this.elementData = Arrays.copyOf(this.elementData, size, Object[].class);
            }
        } else
            this.elementData = DEFAULT_ARRAYLIST;
    }
    
    //Too lazy to realize the implementation of List, so...
    public MyArrayList(MyArrayList mal) {
        this.elementData = mal.elementData;
    }
    
    public E set(int index, E o) {
        rangeCheck(index);
        E old = (E) this.elementData[index];
        this.elementData[index] = o;
        return old;
    }
    
    public Object get(int index) {
        rangeCheck(index);
        return this.elementData[index];
    }
    
    public void add(int index, E o) { 
        rangeAddCheck(index);
        ensureCapacity(size + 1);
        if(index != size)
            System.arraycopy(this.elementData, index, this.elementData, index + 1, size - index);
        this.elementData[index] = o;
        size ++;
    }

    public boolean add(E o) {
        ensureCapacity(size + 1);
        this.elementData[size++] = o;
        return true;
    }
    
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeAddCheck(index);
        Object[] a = c.toArray();
        int length = a.length;
        ensureCapacity(size + length);
        int move = size - index;
        
        if(move > 0)
            System.arraycopy(this.elementData, index, this.elementData, index + length, move);
        System.arraycopy(a, 0, elementData, index, length);
        size += length;
        return length != 0;
    }
    
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int length = a.length;
        ensureCapacity(size + length);
        System.arraycopy(a, 0, this.elementData, size, length);
        size += length;
        return length != 0;
    }
    
    private void ensureCapacity(int minCapacity) {
        if(this.elementData == DEFAULT_ARRAYLIST)
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        if(minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
    
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if(newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if(newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        this.elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if(minCapacity > MAX_ARRAY_SIZE)
            return Integer.MAX_VALUE;
        else return MAX_ARRAY_SIZE;
    }

    public boolean remove(Object o) {
        if(o == null)
            for(int index = 0; index < size; index ++) {
                if(elementData[index] == null) {
                    fastRemove(index);
                    return true;
                } 
            } 
        else {
           for(int index = 0; index < size; index ++) {
               if(o.equals(elementData[index])) {
                   fastRemove(index);
                   return true;
               }
           }
        }
        return false;
    }
    
    private void fastRemove(int index) {
        int move = size - index - 1;
        if(move > 0)
            System.arraycopy(elementData, index + 1, elementData, index, move);
        elementData[--size] = null;
    }

    public E remove(int index) {
        rangeCheck(index);
        E old = (E) elementData[index];
        fastRemove(index);
        return old;
    }
    
    public void removeRange(int start, int end) {
        rangeCheck(end);
        rangeCheck(start);
        int move = size - end;
        if(move > 0)
            System.arraycopy(elementData, end, elementData, start, move);
        
        int num = size - (end - start);
        for(int i = num; i < size; i++)
            elementData[i] = null;
        size = num;
    }
    
    public void clear() {
        for(int index = 0; index < size; index ++)
            elementData[index] = null;
        size = 0;
    }
    
    private void rangeCheck(int index) {
        if(index < 0 || index >= size)
            throw new OutOfBoundsException("Invalid index!");
    }
    
    private void rangeAddCheck(int index) {
        if(index < 0 || index > size)
            throw new OutOfBoundsException("Invalid index for adding!");
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public String toString() {
        String s = "";
        for(int index = 0; index < size; index ++) {
            if(elementData[index] == null)
                s += "null ";
            else
                s += elementData[index].toString() + " ";
        }
        return s;
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(size);
        for(int i = 0; i < size; i++)
            oos.writeObject(elementData[i]);
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        elementData = DEFAULT_ARRAYLIST;
        ois.defaultReadObject();
        ois.readInt();
        if(size > 0) {
            ensureCapacity(size);
            Object[] o = elementData;
            for(int i = 0; i < size; i++)
                o[i] = ois.readObject();
        }
    }
    
    public static void main(String[] args) {
        MyArrayList mal = new MyArrayList(3);
        ArrayList al = new ArrayList();
        al.add("md");
        al.add("zz");
        System.out.println("Is empty? " + mal.isEmpty());
        mal.add(7);
        mal.add(null);
        mal.addAll(al);
        mal.add(4,3);
        mal.set(2, "mm");
        //mal.add(6,9);
        System.out.println("......After adding some elements......");
        System.out.println("Size: " + mal.getSize());
        System.out.println("Is empty? " + mal.isEmpty());
        System.out.println("Content: " + mal);
        //mal.remove(8);
        System.out.println("Deleting the third element: " + mal.remove(3));
        System.out.println("Content: " + mal);
        System.out.println("Deleting the element 3: " + mal.remove(new Integer(3)));
        System.out.println("Content: " + mal);
        mal.removeRange(0, 2);
        System.out.println("......After removing the elements from 0 to 1......");
        System.out.println("Content: " + mal);
        mal.clear();
        System.out.println("......After clearing the list......");
        System.out.println("Is empty? " + mal.isEmpty());
    }
}

class OutOfBoundsException extends RuntimeException {
    OutOfBoundsException(String s) {
        super(s);
    }
}