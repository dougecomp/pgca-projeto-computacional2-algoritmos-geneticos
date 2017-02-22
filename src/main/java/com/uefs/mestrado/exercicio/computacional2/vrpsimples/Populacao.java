/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author douglas
 */
public class Populacao {
    
    private ArrayList<Cromossomo> individuos;
    private int tamanhoPopulacao;
    private int tamanhoCromossomo;
    
    public Populacao(int tamPop, int tamCromo) {
        this.tamanhoPopulacao = tamPop;
    	this.tamanhoCromossomo = tamCromo;
    	individuos = new ArrayList<Cromossomo>();
    }
    
    /**
     * Gerar genes aleatorios para cada cromossomo
     */
    public void iniciarPopulacao(){
       
            for(int i = 0; i < tamanhoPopulacao; i++){
                Cromossomo cromossomo = new Cromossomo(tamanhoCromossomo); 
                cromossomo.inicializarGenes();
                individuos.add(cromossomo);
            }
    }
    
    /**
     * Ordenar arrayList de objetos Cromossos por ordem descendente de fitness
     */
    public void ordenarPorFitness() {
    	
    	 Collections.sort(individuos, new Comparator<Object>(){
             @Override
             public int compare(Object o1, Object o2){
                 Cromossomo c1 = (Cromossomo) o1;
                 Cromossomo c2 = (Cromossomo) o2;
                 if(c1.calcularFitness() < c2.calcularFitness())
                     return 1;
                 else
                     if(c1.calcularFitness() > c2.calcularFitness())
                         return -1;
                     else
                         return 0;
             }
         });    	 
    }

    public ArrayList<Cromossomo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Cromossomo> individuos) {
        this.individuos = individuos;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public int getTamanhoCromossomo() {
        return tamanhoCromossomo;
    }

    public void setTamanhoCromossomo(int tamanhoCromossomo) {
        this.tamanhoCromossomo = tamanhoCromossomo;
    }
    
}
