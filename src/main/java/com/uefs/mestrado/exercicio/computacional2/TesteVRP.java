/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Cromossomo;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Populacao;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.VRP;
import java.util.List;

/**
 *
 * @author douglas
 */
public class TesteVRP {
    
    static String estudante = "douglas";
    static String caminhoArquivo = "casos-teste/teste";
    
    public static VRP rodar() {
        
        String arquivoCasoTeste = caminhoArquivo+estudante;
        arquivoCasoTeste += ".txt";
        
        int tamanhoPopulacao = 100;
        int quantidadeGeracoes = 100; // Critério de parada
        
        float taxaCruzamento = 0.8f;
        float taxaMutacao = 0.1f;
        
        long valorSemente = (long) 50;
        
        VRP algoritmo = new VRP(arquivoCasoTeste, tamanhoPopulacao, quantidadeGeracoes, taxaCruzamento, taxaMutacao, valorSemente);
        algoritmo.setMetodoCruzamento(1); // 1 - Um ponto, 2 - Dois pontos
        algoritmo.setMetodoMutacao(1); // 1 - Um ponto, 2 - Dois pontos
        algoritmo.setMetodoSelecao(1); // 1 - Roleta, 2 - Torneio
        algoritmo.executar();
        
        String s = arquivoCasoTeste+" "+algoritmo.getMelhorResultado().calcularFitness();
        System.out.println(s);
        return algoritmo;
    }

    public static void main(String[] args) {
        
        String arquivoCasoTeste = caminhoArquivo+estudante;
        arquivoCasoTeste += ".txt";
        
        int tamanhoPopulacao = 100;
        int quantidadeGeracoes = 500; // Critério de parada
        
        float taxaCruzamento = 0.9f;
        float taxaMutacao = 0.05f;
        
        long valorSemente = (long) 50;
        
        VRP algoritmo = new VRP(arquivoCasoTeste, tamanhoPopulacao, quantidadeGeracoes, taxaCruzamento, taxaMutacao, valorSemente);
        algoritmo.executar();
        
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
