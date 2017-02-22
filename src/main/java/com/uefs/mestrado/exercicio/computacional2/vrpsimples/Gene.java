/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import com.uefs.mestrado.exercicio.computacional2.Ponto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author douglas
 */
public class Gene {    
    
    // Conjunto de rotas de um dos veículos
    private List<Pair<Ponto,Ponto>> valores;
    
    public Gene() {
        valores = new ArrayList<>();
    }
    
    public double calcularCusto() {
        
        double custo = 0;
        double distancia;
        for (Pair<Ponto, Ponto> valor : valores) { // valor é a ligação entre dois pontos da rota
            Ponto esquerda = valor.getEsquerda();
            Ponto direita = valor.getDireita();
            distancia = Math.sqrt(Math.pow(esquerda.x - direita.x, 2) + Math.pow(esquerda.y - direita.y, 2)); // distância euclidiana
            custo += distancia;
        }
        
        return custo;
        
    }
    
}
