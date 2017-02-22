/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import com.uefs.mestrado.exercicio.computacional2.Ponto;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author douglas
 */
public class Cromossomo {
    
    private int tamanho;
    private ArrayList<Gene> genes;
    private final Random aleatorio;
    
    public Cromossomo(int tamanho) {
        
        this.tamanho = tamanho;
        genes = new ArrayList<>();
        aleatorio = new Random();
        
    }
    
    public double calcularFitness() {
        
        double fitness = 0;
        for (Gene gene : genes) {
            fitness += gene.calcularCusto();
        }
        
        return fitness;
        
    }
    
    public void inicializarGenes(ArrayList<Ponto> clientesNaoUtilizados, Ponto inicio) {
        for (int i = 0; i < tamanho; i++) {
            genes.get(i).inicializarValores(clientesNaoUtilizados, inicio);
        }
    }
    
    /**
     * A mutação não pode alterar nem a primeira
     * nem a última coordenada (início e fim sempre no depósito) 
     */
    public void aplicarMutacao() {
        
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes) {
        this.genes = genes;
    }
    
}
