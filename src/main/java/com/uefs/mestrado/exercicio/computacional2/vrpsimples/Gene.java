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
    
    protected final List<Cliente> clientes;
    protected final List<Veiculo> veiculos;
    protected List<Veiculo> solucao;
    protected final Ponto inicio;
    protected double[] custos;
    
    public Gene(Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        
        this.clientes = clientes;
        this.veiculos = veiculos;
        this.solucao = new ArrayList<>();
        this.inicio = inicio;
        
    }
    
    /**
     * Método para cálculo do custo total de uma solução
     * @return custo total da solução
     */
    public double calcularCusto() {
        
        double custoTotal = 0;
        double[] custosParciais = new double[veiculos.size()];
        double distancia;
        
        for (int i = 0; i < veiculos.size(); i++) {
            
            Cliente ultimoClienteVisitado = null; // Guarda o último cliente visitado pelo veículo atual i
            for (int j = 0; j < clientes.size(); j++) {
                
                if( veiculos.get(i) == solucao.get(j) ) {
                    
                    if( custosParciais[i] == 0 && ultimoClienteVisitado == null) { // distância do depósito para o primeiro cliente
                        
                        distancia = Math.sqrt(Math.pow(inicio.getX() - clientes.get(j).getX(), 2) + Math.pow(inicio.getY() - clientes.get(j).getY(), 2)); // distância euclidiana
                        
                    } else if( (j + 1) == clientes.size() ) { // Último cliente, então calcular distância do ultimo cliente para o inicio
                        
                        distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - inicio.getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - inicio.getY(), 2)); // distância euclidiana;
                        
                    } else { // No meio da rota. Calcular distância entre o cliente anterior para o atual
                        
                        distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - clientes.get(j).getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - clientes.get(j).getY(), 2)); // distância euclidiana;
                        
                    }
                    
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
                    ultimoClienteVisitado = clientes.get(j);
                    
                } else if( (j + 1) == clientes.size() && custosParciais[i] > 0 && ultimoClienteVisitado != null ) { // Caso tenha visitado algum cliente, volte para o inicio
                    distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - inicio.getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - inicio.getY() , 2)); // distância euclidiana
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
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
    
    /**
     * Fazer alguma permutação no vetor de veiculos da SOLUÇÃO
     * 
     * @param semente 
     */
    public void aplicarMutacaoUmPonto(Random semente) {
        int primeiro = semente.nextInt(veiculos.size());
        int segundo = semente.nextInt(veiculos.size());
        
        while(primeiro == segundo) {
            segundo = semente.nextInt(veiculos.size());
        }
        
        Veiculo tmp = solucao.get(primeiro); // Guarda o primeiro temporariamente
        solucao.set(primeiro, solucao.get(segundo)); // Coloca o segundo no primeiro
        solucao.set(segundo, tmp); // Guarda o temporário do primeiro no segundo
        
    }
    
    /**
     * Fazer alguma permutação no vetor de veiculos da SOLUÇÃO
     * 
     * @param semente 
     */
    public void aplicarMutacaoDoisPontos(Random semente) {
        int primeiro = semente.nextInt(veiculos.size());
        int segundo = semente.nextInt(veiculos.size());
        int terceiro = semente.nextInt(veiculos.size());
        int quarto = semente.nextInt(veiculos.size());
        
        while(primeiro == segundo) {
            segundo = semente.nextInt(veiculos.size());
        }
        
        while(terceiro == quarto) {
            quarto = semente.nextInt(veiculos.size());
        }
        
        Veiculo tmp = solucao.get(primeiro); // Guarda o primeiro temporariamente
        solucao.set(primeiro, solucao.get(segundo)); // Coloca o segundo no primeiro
        solucao.set(segundo, tmp); // Guarda o temporário do primeiro no segundo
        
        tmp = solucao.get(terceiro); // Guarda o primeiro temporariamente
        solucao.set(terceiro, solucao.get(quarto)); // Coloca o segundo no primeiro
        solucao.set(quarto, tmp); // Guarda o temporário do primeiro no segundo
        
    }
    
    /**
     * Método que monta uma lista de rotas para cada veículo presente na solução
     * @return 
     */
    public List<List<Ponto>> getRotas() {
        
        List<List<Ponto>> rotas = new ArrayList<>(veiculos.size());
        for (int i = 0; i < veiculos.size(); i++) {
            rotas.add(new ArrayList<>());
            for (int j = 0; j < clientes.size(); j++) {
                
                if( veiculos.get(i) == solucao.get(j) ) {
                    
                    if(rotas.get(i).isEmpty()) { // rota de depósito para o primeiro cliente
                        rotas.get(i).add(inicio);
                        rotas.get(i).add(clientes.get(j));
                    } else if((j + 1) == clientes.size()) { // rota de último cliente para o depósito
                        rotas.get(i).add(inicio);
                    } else { // No meio da rota. Rota entre o cliente anterior para o atual
                        rotas.get(i).add(clientes.get(j));
                    }
                    
                } else if( (j + 1) == clientes.size() && !rotas.get(i).isEmpty() ) { // Caso tenham visitado algum cliente, volte para o inicio
                    rotas.get(i).add(inicio);
                }
            }
        }
        
        return rotas;
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
