/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import com.uefs.mestrado.exercicio.computacional2.Cliente;
import com.uefs.mestrado.exercicio.computacional2.Ponto;
import com.uefs.mestrado.exercicio.computacional2.Veiculo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representação de um indivíduo ( possível solução )
 * @author douglas
 */
public class Cromossomo {
    
    private int tamanho;
    private Gene gene;
    private final Random aleatorio;
    private final Ponto inicio;
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    
    public Cromossomo(int tamanho, Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        
        this.tamanho = tamanho;
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.gene = new Gene(inicio,clientes, veiculos);
        this.aleatorio = new Random();
        this.inicio = inicio;
    }
    
    /**
     * Fitnesse é o somatório dos cutos das rotas
     * @return total do custo de uma solução
     */
    public double calcularFitness() {
        
        return this.calcularFitness();
        
    }
    
    public void inicializarGenes() {
        gene.inicializarValores();
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

    public Gene getGenes() {
        return gene;
    }

    public void setGenes(Gene gene) {
        this.gene = gene;
    }
    
}
