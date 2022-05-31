package com.athena.tech.api.domain.commons.list;

public class QueueObj<T> {
    private int end;
    private T[] queue;

    public QueueObj(int size) {
        this.end = 0;
        this.queue = (T[]) new Object[size];
    }

    public boolean isEmpty() {
        return (end == 0);
    }

    public boolean isFull() {
        return (end == queue.length);
    }

    public void insert(T value) {
        if(!isFull()){
            queue[end++] = value;
        } else {
            throw new IllegalStateException();
        }
    }

    public T peek() {
        return queue[0];
    }

    public Integer size() {
        return end;
    }

    public T pool() {
        if(!isEmpty()){
            T response = peek();
            for (int i = 0 ; i < end - 1; i++) {
                queue[i] = queue[i + 1];
            }
            end--;
            queue[end] = null;
            return response;
        }
        return null;
    }
}