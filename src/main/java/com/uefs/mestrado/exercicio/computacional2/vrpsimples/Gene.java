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
 *
 * @author douglas
 */
public class Gene {    
    
    private final List<Cliente> clientes;
    private final List<Veiculo> veiculos;
    private List<Veiculo> solucao;
    private final Ponto inicio;
    private double[] custos;
    
    public Gene(Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.solucao = new ArrayList<>();
        this.inicio = inicio;
        
    }
    
    public double calcularCusto() {
        
        double custoTotal = 0;
        double[] custosParciais = new double[veiculos.size()];
        double distancia;
        inicializarCustos();
        for (int i = 0; i < veiculos.size(); i++) {
            Cliente ultimoClienteVisitado = null; // Guarda o último cliente visitado pelo veículo atual i
            for (int j = 0; j < clientes.size(); j++) {
                if(veiculos.get(i) == solucao.get(j)) {
                    if( custosParciais[i] == 0 ) { // distância do depósito para o primeiro cliente
                        distancia = Math.sqrt(Math.pow(inicio.x - clientes.get(j).x, 2) + Math.pow(inicio.y - clientes.get(j).y, 2)); // distância euclidiana
                    } else if( (j + 1) == clientes.size() ) { // Último cliente, então calcular distância do ultimo cliente para o inicio
                        distancia = Math.sqrt(Math.pow(inicio.x - ultimoClienteVisitado.x, 2) + Math.pow(inicio.y - ultimoClienteVisitado.y, 2)); // distância euclidiana;
                    } else { // No meio da rota. Calcular distância entre o cliente anterior para o atual
                        distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.x - clientes.get(j).x, 2) + Math.pow(ultimoClienteVisitado.y - clientes.get(j).y, 2)); // distância euclidiana;
                    }
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
                    ultimoClienteVisitado = clientes.get(j);
                }
            }
        }        
        
        return custoTotal;
    }
    
    /**
     * 
     * Método para inicializar o gene atribuindo aleatoriamente
     * veículos a cada cliente
     * 
     * @param semente 
     */
    public void inicializarValores(Random semente) {
        for (int i = 0; i < clientes.size(); i++) {
            solucao.add(veiculos.get(semente.nextInt(veiculos.size())));
        }
        
    }

    public void inicializarCustos() {
        custos = new double[veiculos.size()];
        for (int i = 0; i < custos.length; i++) {
            custos[i] = 0;
        }
    }
    
    /**
     * Fazer alguma permutação no vetor de veiculos da SOLUÇÃO
     * 
     * @param semente 
     */
    public void aplicarMutacao(Random semente) {
        int primeiro = semente.nextInt(veiculos.size());
        int segundo = semente.nextInt(veiculos.size());
        while(primeiro == segundo) {
            segundo = semente.nextInt(veiculos.size());
        }
        Veiculo tmp = solucao.get(primeiro); // Guarda o primeiro temporariamente
        solucao.set(primeiro, solucao.get(segundo)); // Coloca o segundo no primeiro
        solucao.set(segundo, tmp); // Guarda o temporário do primeiro no segundo
        
    }
    
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Veiculo> getSolucao() {
        return solucao;
    }

    public void setSolucao(List<Veiculo> solucao) {
        this.solucao = solucao;
    }
    
}
