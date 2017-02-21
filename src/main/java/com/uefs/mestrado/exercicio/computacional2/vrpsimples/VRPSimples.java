/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import java.util.ArrayList;
import java.util.Random;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author douglas
 */
public class VRPSimples {
    
    private Populacao populacao;
    private float taxaMutacao;
    private float taxaCruzamento;
    private int qtdGeracoes;
    private final XYSeries pontos;
    private final Random rand;
    
    public VRPSimples(int tamPop, int tamCromo, int qtdGer, float txCruz, float txMut) {
        
        //populacao = new Populacao(tamPop, tamCromo);
        taxaCruzamento = txCruz;
        taxaMutacao = txMut;
        qtdGeracoes = qtdGer;
        pontos = new XYSeries("Melhor Indivíduo");
        rand = new Random();
        
    }
    
    public void executar() {
        
        /*populacao.iniciarPopulacao();
        converterFitnessBinarioParaDecimal();
        calcularFitness();

        int geracoes = 0;
        while(geracoes < qtdGeracoes){

            ArrayList<Cromossomo> cromossomosSelecionados = selecionarCromossomosPorRoleta(
                            populacao, populacao.getIndividuos().size());
            ArrayList<Cromossomo> resultCruzamento = cruzamentoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());
            ArrayList<Cromossomo> resultMutacao = mutacaoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());

            populacao.getIndividuos().addAll(resultCruzamento);
            populacao.getIndividuos().addAll(resultMutacao);

            converterFitnessBinarioParaDecimal();
            calcularFitness();
            cortar();

            pontos.add(geracoes, populacao.getCromossomo(0).getValorCromossomo());
            geracoes++;
        
        }

        plotaGrafico();
        String s = "Melhor resultado obtido foi: " + getMelhorResultado().getValorCromossomo() + "\n"
                        + "Valor de Aptidão: " + getMelhorResultado().getFitness();

        JOptionPane.showMessageDialog(null, s);*/
        
    }
    
    /**
    * Realiza a mutação em um determinado ponto aleatorio para uma determinada
    * taxa de mutação
    * @param cromossomosSelecionados
    * @return
    */
    public ArrayList<Cromossomo> mutacaoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados){

        ArrayList<Cromossomo> resultMutacao = new ArrayList<Cromossomo>();

        for(int i = 0; i < cromossomosSelecionados.size(); i++){
            int ponto = rand.nextInt(cromossomosSelecionados.size() - 1);
            Cromossomo individuo = cromossomosSelecionados.get(ponto);
            //int gene = rand.nextInt(individuo.getTamanho() - 1);

            if(rand.nextFloat() < taxaMutacao){
                //individuo.aplicarMutacao();
            }
            resultMutacao.add(individuo);
        }
        return resultMutacao;
    }

    /**
     * Realiza o cruzamento em um determinado ponto aleatorio para uma determinada
     * taxa de cruzamento
     * @param cromossomosSelecionados
     * @return
     */
    public ArrayList<Cromossomo> cruzamentoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados) {

        ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
        int qtdCruzamentos = cromossomosSelecionados.size();
        int ponto;

        int cont = 0;
        while (cont < qtdCruzamentos) {
            Cromossomo pai = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));
            Cromossomo mae = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));

            /*Cromossomo filho1 = new Cromossomo(pai.getGenes().size());
            Cromossomo filho2 = new Cromossomo(mae.getGenes().size());

            ponto = rand.nextInt(pai.getTamanho()-1);
            if(rand.nextFloat() < taxaCruzamento){
                for (int i = 0; i < pai.getGenes().size(); i++) {
                    if (i < ponto) {
                            filho1.getGenes().add(i, pai.getGenes().get(i));
                            filho2.getGenes().add(i, mae.getGenes().get(i));
                    } else {
                            filho1.getGenes().add(i,mae.getGenes().get(i));
                            filho2.getGenes().add(i,pai.getGenes().get(i));
                    }
                }
                filhos.add(filho1);
                filhos.add(filho2);
            }
            cont++;*/
        }
        return filhos;

    }

    /**
     * Selecionar Cromossomos(individuos) para o possivel cruzamento ou mutação através do método da roleta
     * @param populacao
     * @param qtdSele: tamanho da populacao
     * @return
     */
    public ArrayList<Cromossomo> selecionarCromossomosPorRoleta(Populacao populacao, int qtdSele) {

        ArrayList<Cromossomo> cromossomosSelecionados = new ArrayList<Cromossomo>();
        double sum = 0;
        double sumParcial = 0;
        int cont = 0;

        /*for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
            sum += populacao.getCromossomo(i).getFitness() + 1;
        }

        while (cont < qtdSele) {
            int numAleatorio = rand.nextInt( (int)sum);
            for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
                sumParcial += populacao.getCromossomo(i).getFitness() + 1;
                if (sumParcial >= numAleatorio) {
                    cromossomosSelecionados.add(populacao.getCromossomo(i));
                    break;
                }
            }
            cont++;
         }*/

        return cromossomosSelecionados;
    }

    public Populacao getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Populacao populacao) {
        this.populacao = populacao;
    }

    public float getTaxaMutacao() {
        return taxaMutacao;
    }

    public void setTaxaMutacao(float taxaMutacao) {
        this.taxaMutacao = taxaMutacao;
    }

    public float getTaxaCruzamento() {
        return taxaCruzamento;
    }

    public void setTaxaCruzamento(float taxaCruzamento) {
        this.taxaCruzamento = taxaCruzamento;
    }

    public int getQtdGeracoes() {
        return qtdGeracoes;
    }

    public void setQtdGeracoes(int qtdGeracoes) {
        this.qtdGeracoes = qtdGeracoes;
    }
    
}
