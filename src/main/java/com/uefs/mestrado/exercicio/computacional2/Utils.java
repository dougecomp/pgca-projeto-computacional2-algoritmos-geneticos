/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Cromossomo;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.Populacao;
import java.util.List;

/**
 *
 * @author douglas
 */
public class Utils {
    
    public static double getDistanciaEuclidiana(Ponto p, Ponto q) {
        double distancia;
        distancia = Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2)); // distância euclidiana;
        return distancia;
    }
    
    public static double media(Populacao p) {
        List<Cromossomo> individuos = p.getIndividuos();
        double total = 0;
        for (Cromossomo individuo : individuos) {
            total += individuo.calcularFitness();
        }
        return total/individuos.size();
    }
    
    public static double desvioPadrao(Populacao p, double media) {
        List<Cromossomo> individuos = p.getIndividuos();
        double resultado = 0;
        for (Cromossomo individuo : individuos) {
            resultado += Math.pow(individuo.calcularFitness() - media,2);
        }
        return Math.sqrt(resultado/individuos.size());
    }
    
}
