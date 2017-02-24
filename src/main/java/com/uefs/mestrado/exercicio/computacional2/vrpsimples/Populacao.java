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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author douglas
 */
public class Populacao {
    
    private List<Cromossomo> individuos;
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private int tamanhoPopulacao;
    private int tamanhoCromossomo;
    
    public Populacao(int tamPop, List<Cliente> clientes, List<Veiculo> veiculos) {
        this.tamanhoPopulacao = tamPop;
    	this.tamanhoCromossomo = clientes.size();
    	individuos = new ArrayList<Cromossomo>();
        this.veiculos = veiculos;
        this.clientes = clientes;
    }
    
    /**
     * Gerar genes aleatorios para cada cromossomo
     * @param inicio
     */
    public void iniciarPopulacao(Ponto inicio) {
       
        for(int i = 0; i < tamanhoPopulacao; i++){
            Cromossomo cromossomo = new Cromossomo(tamanhoCromossomo, inicio, clientes, veiculos); 
            cromossomo.inicializarGenes();
            individuos.add(cromossomo);
        }
        
    }
    
    /**
     * Ordenar arrayList de objetos Cromossos por ordem descendente de fitness
     * Cortar os indivíduos com custo maior
     */
    public void ordenarPorFitness() {
    	
    	Collections.sort(individuos, new Comparator<Object>(){
            @Override
            public int compare(Object o1, Object o2){
                Cromossomo c1 = (Cromossomo) o1;
                Cromossomo c2 = (Cromossomo) o2;
                if(c1.calcularFitness() < c2.calcularFitness())
                    return 1;
                else
                    if(c1.calcularFitness() > c2.calcularFitness())
                        return -1;
                    else
                        return 0;
            }
        });    	 
    }

    public List<Cromossomo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Cromossomo> individuos) {
        this.individuos = individuos;
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public int getTamanhoCromossomo() {
        return tamanhoCromossomo;
    }

    public void setTamanhoCromossomo(int tamanhoCromossomo) {
        this.tamanhoCromossomo = tamanhoCromossomo;
    }
    
}
