/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Cromossomo;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Populacao;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.VRPSimples;
import java.util.List;

/**
 *
 * @author douglas
 */
public class TesteVRPSimples {
    
    public static void main(String[] args) {
        String estudante = "abraao";
        String arquivoCasoTeste = "teste"+estudante;
        arquivoCasoTeste += ".txt";
        
        int tamanhoPopulacao = 20;
        int quantidadeGeracoes = 50; // Critério de parada
        
        float taxaCruzamento = 0.7f;
        float taxaMutacao = 0.1f;
        
        long valorSemente = (long) 50;
        
        VRPSimples algoritmo = new VRPSimples(arquivoCasoTeste, tamanhoPopulacao, quantidadeGeracoes, taxaCruzamento, taxaMutacao, valorSemente);
        algoritmo.executar();
        
        Populacao p = algoritmo.getPopulacao();
        
        String s = arquivoCasoTeste+" "+algoritmo.getMelhorResultado().calcularFitness();
        System.out.println(s);
    }
    
    private static double media(Populacao p) {
        List<Cromossomo> individuos = p.getIndividuos();
        double total = 0;
        for (Cromossomo individuo : individuos) {
            total += individuo.calcularFitness();
        }
        return total/individuos.size();
    }
    
    private static double desvioPadrao(Populacao p, double media) {
        List<Cromossomo> individuos = p.getIndividuos();
        double resultado = 0;
        for (Cromossomo individuo : individuos) {
            resultado += Math.pow(individuo.calcularFitness() - media,2);
        }
        return Math.sqrt(resultado/individuos.size());
    }
    
}
