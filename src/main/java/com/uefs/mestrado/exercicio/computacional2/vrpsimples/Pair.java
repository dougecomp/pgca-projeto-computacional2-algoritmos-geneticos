/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

/**
 *
 * @author douglas
 */
public class Pair<E,D> {
    
    private E e;
    private D d;
    
    public Pair(E e, D d) {
        this.e = e;
        this.d = d;
    }

    public E getEsquerda() {
        return e;
    }

    public void setEsquerda(E e) {
        this.e = e;
    }

    public D getDireita() {
        return d;
    }

    public void setDireita(D d) {
        this.d = d;
    }
    
}
