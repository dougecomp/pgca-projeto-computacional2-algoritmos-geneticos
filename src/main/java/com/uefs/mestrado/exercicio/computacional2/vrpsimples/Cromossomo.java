/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import com.uefs.mestrado.exercicio.computacional2.Cliente;
import com.uefs.mestrado.exercicio.computacional2.Ponto;
import com.uefs.mestrado.exercicio.computacional2.Veiculo;
import com.uefs.mestrado.exercicio.computacional2.vrpc.GeneVRPC;
import java.util.List;
import java.util.Random;

/**
 * Representação de um indivíduo ( possível solução )
 * @author douglas
 */
public class Cromossomo {
    
    private int tamanho;
    private double fitness;
    private GeneVRPC gene;
    private final Ponto inicio;
    private final List<Cliente> clientes;
    private final List<Veiculo> veiculos;
    
    /**
     * 
     * @param tamanho
     * @param inicio
     * @param clientes
     * @param veiculos 
     */
    public Cromossomo(int tamanho, Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        
        this.tamanho = tamanho;
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.gene = new GeneVRPC(inicio,clientes, veiculos);
        this.inicio = inicio;
        
    }
    
    /**
     * Fitnesse é o somatório dos cutos das rotas
     * @return total do custo de uma solução
     */
    public double calcularFitness() {
        fitness = gene.calcularCusto();
        return fitness;
        
    }
    
    public void inicializarGenes(Random semente) {
        gene.inicializarValores(semente);
        //calcularFitness();
    }
    
    /**
     * Chamar método do gene para fazer uma permutação entre dois veículos
     * 
     * @param semente 
     */
    public void aplicarMutacaoUmPonto(Random semente) {
        gene.aplicarMutacaoUmPonto(semente);
    }
    
    /**
     * Chamar método do gene para fazer uma permutação entre dois veículos
     * 
     * @param semente 
     */
    public void aplicarMutacaoDoisPontos(Random semente) {
        gene.aplicarMutacaoDoisPontos(semente);
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public GeneVRPC getGenes() {
        return gene;
    }

    public void setGenes(GeneVRPC gene) {
        this.gene = gene;
    }
    
}
