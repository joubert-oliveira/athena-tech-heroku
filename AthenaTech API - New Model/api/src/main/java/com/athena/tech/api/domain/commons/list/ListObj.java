package com.athena.tech.api.domain.commons.list;

public class ListObj <T>{
        private T[] vetor;
        private int numElem;

        public ListObj(int lengt){
            vetor = (T[]) new Object[lengt];
            numElem = 0;
        }


        public void add(T obj){
            if (numElem >= vetor.length){
                System.out.println("Vetor cheio");
            } else {
                vetor[numElem++] = obj;
            }
        }

        public void listObj() {
            if (numElem == 0){
                System.out.println("\nObjetos da lista: ");
                for (int i = 0; i < numElem; i++){
                    System.out.println(vetor[i]);
                }
            }
        }

        public int findObj(T objBuscado){
            for (int i = 0; i < numElem; i++){
                if (vetor[i].equals(objBuscado)) {
                    return i;
                }
            }
            return -1;
        }

        public boolean removeByIndex (int indice){
            if (indice < 0 || indice >= numElem) {
                System.out.println("\nIndice invalido!");
                return false;
            }
            for (int i = indice; i < numElem; i++){
                vetor[i] = vetor[i+1];
            }

            numElem--;
            return true;
        }

        public Boolean removerObj (T objRemover) {return removeByIndex(findObj(objRemover));}

        public int getLengt() { return numElem; }

        public T getObj( int indice) {
            if (indice < 0 || indice >= numElem) {
                return null;
            }else {
                return vetor[indice];
            }
        }

        public void clean() {numElem = 0;}

}
