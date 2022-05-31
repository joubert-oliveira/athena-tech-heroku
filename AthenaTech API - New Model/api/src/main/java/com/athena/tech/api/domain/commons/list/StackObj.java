package com.athena.tech.api.domain.commons.list;

public class StackObj<T> {
    private int topo;
    private T[] vetor;

    public StackObj(int size) {
        this.topo = -1;
        this.vetor = (T[]) new Object[size];
    }

    public void push(T valor) {
        if(!isFull()){
            vetor[++topo] = valor;
        } else {
            throw new IllegalStateException("Pilha cheia");
        }
    }

    public T pop() {
        if(!isEmpity()){
            return vetor[topo--];
        } else {
            return null;
        }
    }

    public boolean isEmpity() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == vetor.length - 1;
    }

    public T peek() {
        return vetor[topo];
    }
}



