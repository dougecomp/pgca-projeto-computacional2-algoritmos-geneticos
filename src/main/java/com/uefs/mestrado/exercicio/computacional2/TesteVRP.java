/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpsimples.VRP;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author douglas
 */
public class TesteVRP {
    
    static String estudante = "indiara";
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
        algoritmo.setMetodoSelecao(2); // 1 - Roleta, 2 - Torneio
        algoritmo.executar();
        
        String s = arquivoCasoTeste+" "+algoritmo.getMelhorResultado().calcularFitness();
        System.out.println(s);
        return algoritmo;
    }

    public static void rodarTodos() {
        
        /*String arquivoCasoTeste = caminhoArquivo+estudante;
        arquivoCasoTeste += ".txt";*/
        
        int tamanhoPopulacao = 100;
        int quantidadeGeracoes = 100; // Critério de parada
        
        float taxaCruzamento = 0.8f;
        float taxaMutacao = 0.1f;
        
        long valorSemente = (long) 50;
        
        File folder = new File("casos-teste");
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles);
        int[] indices = {0,5,10};
        for (int i = 0; i < indices.length; i++) {
            String arquivoCasoTeste = "casos-teste/"+listOfFiles[indices[i]].getName();
            VRP algoritmo = new VRP(arquivoCasoTeste, tamanhoPopulacao, quantidadeGeracoes, taxaCruzamento, taxaMutacao, valorSemente);
            algoritmo.setMetodoCruzamento(1); // 1 - Um ponto, 2 - Dois pontos
            algoritmo.setMetodoMutacao(1); // 1 - Um ponto, 2 - Dois pontos
            algoritmo.setMetodoSelecao(2); // 1 - Roleta, 2 - Torneio
            algoritmo.executar();

            /*double media = Utils.media(algoritmo.getPopulacao());
            double desvioPadrao = Utils.desvioPadrao(algoritmo.getPopulacao(), media);
            String s = media+" "+desvioPadrao;
            System.out.println(s);*/
        }

    }
    
    public static void main(String[] args) {
        
        rodarTodos();
        
    }
    
}
