/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpsimples.VRPSimples;

/**
 *
 * @author douglas
 */
public class ExercicioComputacional2 {
       
    public static void main(String[] args) {
        int tamanhoPopulacao = 100;
        int tamanhoCromossomo = 20; // Quantidade de veículos
        int quantidadeGeracoes = 50; // Critério de parada
        
        // Coordenadas do depósito
        int xInicio = 100;
        int yInicio = 100;
        
        float taxaCruzamento = 0.7f;
        float taxaMutacao = 0.1f;
        
        VRPSimples algoritmo = new VRPSimples(tamanhoPopulacao, tamanhoCromossomo, xInicio, yInicio, quantidadeGeracoes, taxaCruzamento, taxaMutacao);
        algoritmo.executar();
    }
    
}
