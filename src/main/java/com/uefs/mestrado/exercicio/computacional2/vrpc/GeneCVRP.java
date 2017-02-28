/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpc;

import com.uefs.mestrado.exercicio.computacional2.Cliente;
import com.uefs.mestrado.exercicio.computacional2.Ponto;
import com.uefs.mestrado.exercicio.computacional2.Veiculo;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Gene;
import java.util.List;
import java.util.Random;

/**
 *
 * @author douglas
 */
public class GeneCVRP extends Gene {
    
    private double[] demandas;
    private boolean infactivel;
    
    public GeneCVRP(Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        super(inicio, clientes, veiculos);
        infactivel = false;
    }
    
    @Override
    public double calcularCusto() {
        double custoTotal = 0;
        double[] custosParciais = new double[veiculos.size()];
        double distancia;
        inicializarDemandas();
        
        for (int i = 0; i < veiculos.size(); i++) {
            
            Cliente ultimoClienteVisitado = null; // Guarda o último cliente visitado pelo veículo atual i
            Cliente clienteAtual = null; // Guarda o último cliente visitado pelo veículo atual i
            for (int j = 0; j < clientes.size(); j++) {
                
                if( veiculos.get(i) == solucao.get(j) ) {
                    
                    clienteAtual = clientes.get(j);
                    
                    if( custosParciais[i] == 0 ) { // distância do depósito para o primeiro cliente
                        
                        distancia = getDistanciaEuclidiana(inicio, clienteAtual);
                        //distancia = Math.sqrt(Math.pow(inicio.getX() - clienteAtual.getX(), 2) + Math.pow(inicio.getY() - clienteAtual.getY(), 2)); // distância euclidiana
                        demandas[i] += clienteAtual.getDemanda();
                        
                    } else if( (j + 1) == clientes.size() ) { // Último cliente, então calcular distância dele ate o depósito
                        
                        distancia = getDistanciaEuclidiana(ultimoClienteVisitado, inicio);
                        //distancia += getDistanciaEuclidiana(clienteAtual, inicio);
                        //distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - inicio.getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - inicio.getY(), 2)); // distância euclidiana;
                        if(demandas[i] > veiculos.get(i).getCapacidade()) { // Se a demanda ficou maior do que a capacidade do veículo i, então aplica-se uma penalidade no cálculo do custo devido a ter gerado uma solução infactível
                            distancia *= 1000;
                            infactivel = true;
                        }
                        
                    } else { // No meio da rota. Calcular distância entre o cliente anterior para o atual
                        
                        distancia = getDistanciaEuclidiana(ultimoClienteVisitado, clienteAtual);
                        //distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - clienteAtual.getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - clienteAtual.getY(), 2)); // distância euclidiana;
                        demandas[i] += clienteAtual.getDemanda();
                        
                    }
                    
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
                    ultimoClienteVisitado = clienteAtual;
                    
                } else if( (j + 1) == clientes.size() && custosParciais[i] > 0 && ultimoClienteVisitado != null ) { // Caso tenha visitado algum cliente, volte para o inicio
                    distancia = getDistanciaEuclidiana(ultimoClienteVisitado, inicio);
                    //distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - inicio.getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - inicio.getY() , 2)); // distância euclidiana
                    if (demandas[i] > veiculos.get(i).getCapacidade()) { // Se a demanda ficou maior do que a capacidade do veículo i, então aplica-se uma penalidade no cálculo do custo devido a ter gerado uma solução infactível
                        distancia *= 1000;
                        infactivel = true;
                    }
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
                }
            }
        }        
        
        return custoTotal;
    }
    
    public void inicializarDemandas() {
        demandas = new double[veiculos.size()];
        for (int i = 0; i < demandas.length; i++) {
            demandas[i] = 0;
        }
    }
    
    /**
     * 
     * Método para inicializar o gene atribuindo aleatoriamente
     * veículos a cada cliente
     * 
     * @param semente 
     */
    @Override
    public void inicializarValores(Random semente) {
    
        do{
            solucao.clear();
            infactivel = false;
            for (int i = 0; i < clientes.size(); i++) {
                int indice = semente.nextInt(veiculos.size());
                //System.out.println("Indice: "+indice);
                solucao.add(veiculos.get(indice));
            }
            //System.out.println("Solução Montada");
            calcularCusto();
        } while(infactivel);
        
    }
    
    public double[] getDemandas() {
        return demandas;
    }

    public void setDemandas(double[] demandas) {
        this.demandas = demandas;
    }
    
}
