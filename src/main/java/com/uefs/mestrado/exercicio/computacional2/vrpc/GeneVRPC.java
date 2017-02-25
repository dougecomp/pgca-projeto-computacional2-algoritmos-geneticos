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

/**
 *
 * @author douglas
 */
public class GeneVRPC extends Gene {
    
    private double[] demandas;
    
    public GeneVRPC(Ponto inicio, List<Cliente> clientes, List<Veiculo> veiculos) {
        super(inicio, clientes, veiculos);
    }
    
    @Override
    public double calcularCusto() {
        double custoTotal = 0;
        double[] custosParciais = new double[veiculos.size()];
        double distancia;
        inicializarDemandas();
        
        for (int i = 0; i < veiculos.size(); i++) {
            
            Cliente ultimoClienteVisitado = null; // Guarda o último cliente visitado pelo veículo atual i
            for (int j = 0; j < clientes.size(); j++) {
                
                if( veiculos.get(i) == solucao.get(j) ) {
                    
                    if( custosParciais[i] == 0 ) { // distância do depósito para o primeiro cliente
                        
                        distancia = Math.sqrt(Math.pow(inicio.getX() - clientes.get(j).getX(), 2) + Math.pow(inicio.getY() - clientes.get(j).getY(), 2)); // distância euclidiana
                        demandas[i] += clientes.get(j).getDemanda();
                    } else if( (j + 1) == clientes.size() ) { // Último cliente, então calcular distância do ultimo cliente para o inicio
                        
                        distancia = Math.sqrt(Math.pow(inicio.getX() - ultimoClienteVisitado.getX(), 2) + Math.pow(inicio.getY() - ultimoClienteVisitado.getY(), 2)); // distância euclidiana;
                        if(demandas[i] > veiculos.get(i).getCapacidade()) { // Se a demanda ficou maior do que a capacidade do veículo i, então aplica-se uma penalidade no cálculo do custo devido a ter gerado uma solução infactível
                            distancia *= 5;
                        }
                    } else { // No meio da rota. Calcular distância entre o cliente anterior para o atual
                        
                        distancia = Math.sqrt(Math.pow(ultimoClienteVisitado.getX() - clientes.get(j).getX(), 2) + Math.pow(ultimoClienteVisitado.getY() - clientes.get(j).getY(), 2)); // distância euclidiana;
                        demandas[i] += clientes.get(j).getDemanda();
                    }
                    
                    custosParciais[i] += distancia;
                    custoTotal += distancia;
                    ultimoClienteVisitado = clientes.get(j);
                    
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
    
}
