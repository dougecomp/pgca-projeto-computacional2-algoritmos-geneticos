/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2.vrpsimples;

import com.uefs.mestrado.exercicio.computacional2.Cliente;
import com.uefs.mestrado.exercicio.computacional2.Ponto;
import com.uefs.mestrado.exercicio.computacional2.Veiculo;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
    private final XYSeries dados;
    private Ponto deposito;
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private int qtdVeiculos;
    private int qtdClientes;
    private int tamanhoPopulacao;
    private Random semente;
    
    public VRPSimples(String arquivoCasoTeste, int tamPop, int qtdGer, float txCruz, float txMut, long valorSemente) {
        
        taxaCruzamento = txCruz;
        taxaMutacao = txMut;
        qtdGeracoes = qtdGer;
        tamanhoPopulacao = tamPop;
        clientes = new ArrayList<>();
        veiculos = new ArrayList<>();
        
        semente = new Random(valorSemente);
        
        try {
            getCasoTeste(arquivoCasoTeste, " ");
        } catch(IOException e) {
            System.out.println("Não foi possível ler arquivo do caso de teste.");
            System.exit(0);
        }
        
        dados = new XYSeries("Melhor Indivíduo");
        
    }
    
    /**
     * Método para executar o algoritmo genético
     * 
     */
    public void executar() {
        
        populacao = new Populacao(this.tamanhoPopulacao, this.clientes, this.veiculos);
        populacao.iniciarPopulacao(deposito, semente);
        //populacao.printAllFitness();
        
        int geracoes = 0;
        while(geracoes < qtdGeracoes) {

            ArrayList<Cromossomo> cromossomosSelecionados = selecionarCromossomosPorRoleta(populacao, populacao.getIndividuos().size());
            ArrayList<Cromossomo> resultCruzamento = cruzamentoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());
            ArrayList<Cromossomo> resultMutacao = mutacaoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());

            populacao.getIndividuos().addAll(resultCruzamento);
            populacao.getIndividuos().addAll(resultMutacao);

            descartarPiores();
            
            dados.add(geracoes, populacao.getIndividuos().get(0).calcularFitness());
            geracoes++;
        
        }

        //plotaGrafico();
        String s = "Melhor resultado obtido foi com custo igual a: " + getMelhorResultado().calcularFitness();
        System.out.println(s);
        //JOptionPane.showMessageDialog(null, s);*/
        
    }
    
    /**
    * Realiza a mutação em um determinado ponto aleatorio para uma determinada
    * taxa de mutação. A mutação não pode alterar nem o início nem o fim da rota.
    * @param cromossomosSelecionados
    * @return
    */
    public ArrayList<Cromossomo> mutacaoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados) {

        ArrayList<Cromossomo> resultMutacao = new ArrayList<Cromossomo>();

        for(int i = 0; i < cromossomosSelecionados.size(); i++){
            int ponto = semente.nextInt(cromossomosSelecionados.size() - 1);
            Cromossomo individuo = cromossomosSelecionados.get(ponto);
            int gene = semente.nextInt(individuo.getTamanho() - 1);

            if(semente.nextFloat() < taxaMutacao){
                individuo.aplicarMutacao(semente);
            }
            resultMutacao.add(individuo);
        }
        return resultMutacao;
    }

    /**
     * Realiza o cruzamento em um determinado ponto aleatorio para uma determinada
     * taxa de cruzamento.
     * @param cromossomosSelecionados
     * @return
     */
    public ArrayList<Cromossomo> cruzamentoUmPonto(ArrayList<Cromossomo> cromossomosSelecionados) {

        ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();
        int qtdCruzamentos = cromossomosSelecionados.size();
        int ponto;

        int cont = 0;
        while (cont < qtdCruzamentos) {
            Cromossomo pai = cromossomosSelecionados.get(semente.nextInt(cromossomosSelecionados.size() - 1));
            Cromossomo mae = cromossomosSelecionados.get(semente.nextInt(cromossomosSelecionados.size() - 1));

            Cromossomo filho1 = new Cromossomo(clientes.size(),deposito,clientes,veiculos);
            Cromossomo filho2 = new Cromossomo(clientes.size(),deposito,clientes,veiculos);

            ponto = semente.nextInt(pai.getTamanho()-1);
            if(semente.nextFloat() > taxaCruzamento) {
                for (int i = 0; i < clientes.size(); i++) {
                    if (i < ponto) {
                        filho1.getGenes().getSolucao().add(i, pai.getGenes().getSolucao().get(i));
                        filho2.getGenes().getSolucao().add(i, mae.getGenes().getSolucao().get(i));
                    } else {
                        filho1.getGenes().getSolucao().add(i,mae.getGenes().getSolucao().get(i));
                        filho2.getGenes().getSolucao().add(i,pai.getGenes().getSolucao().get(i));
                    }
                }
                filhos.add(filho1);
                filhos.add(filho2);
            }
            cont++;
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

        ArrayList<Cromossomo> cromossomosSelecionados = new ArrayList<>();
        double sum = 0;
        double sumParcial = 0;
        int cont = 0;

        for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
            sum += populacao.getIndividuos().get(i).calcularFitness();
        }

        while (cont < qtdSele) {
            int numAleatorio = semente.nextInt( (int)sum) + 1;
            sumParcial = 0;
            for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
                sumParcial += populacao.getIndividuos().get(i).calcularFitness();
                if (sumParcial >= numAleatorio) {
                    cromossomosSelecionados.add(populacao.getIndividuos().get(i));
                    cont++;
                    break;
                }
            }
         }

        return cromossomosSelecionados;
    }
    
    /**
     * Retirar piores individuos
     * Como é para minimizar o custo, então quão menor for o fitness, melhor será a solução
     * 
     */
    public void descartarPiores() {
        populacao.ordenarPorFitnessAscendente();

        for (int i = populacao.getIndividuos().size() - 1; i >= populacao.getTamanhoPopulacao(); i--) {
            populacao.getIndividuos().remove(i);
        }
    }

    /**
     * Retorna o indivíduo com a melhor solucao
     * encontrada para uma execução do algoritmo genético
     * Como é para minimizar o custo, então quão menor for o fitness, melhor será a solução
     * @return 
     */
    public Cromossomo getMelhorResultado() {

        populacao.ordenarPorFitnessAscendente();

        return populacao.getIndividuos().get(0);

    }
    
    /**
     * Método de leitura do arquivo do estudo do caso de teste
     * como definido em sala
     * K [num carros]
     * C1 [capacidade carro 1]
     * C2
     * ...
     * Ck
     * N [num clientes]
     * X0 Y0 [coordenadas do depósito central]
     * X1 Y1 D1 [coordenadas e demanda do cliente1]
     * ...
     * Xn Yn Dn 
     * @param filename
     * @param separator
     * @throws IOException 
     */
    private void getCasoTeste(String filename, String separator) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                
        String line = reader.readLine();
        this.qtdVeiculos = Integer.parseInt(line);
        int i = 0;
        line = reader.readLine();
        while(i < this.qtdVeiculos){ // captura das capacidades dos veículos
            int capacidade = Integer.parseInt(line);

            veiculos.add(new Veiculo(capacidade));
            
            line = reader.readLine();
            i++;
        }
        
        this.qtdClientes = Integer.parseInt(line);
        i = 0;
        line = reader.readLine();
        while(i < this.qtdClientes) { // captura das coordenadas do depósito, dos clientes e demandas
            int x;
            int y;
            int demanda;
            String[] partes = line.split(separator);
            
            x = Integer.parseInt(partes[0]);            
            y = Integer.parseInt(partes[1]);
            
            if(i == 0) { // quando estiver capturando as coordenadas do depósito, colocar zero na demanda
                this.deposito = new Ponto(x, y);
            } else {
                demanda = Integer.parseInt(partes[2]);
                this.clientes.add(new Cliente(x, y,demanda));
            }
            
            line = reader.readLine();
            i++;
        }
        
        reader.close();
        
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

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Ponto getDeposito() {
        return deposito;
    }

    public void setDeposito(Ponto deposito) {
        this.deposito = deposito;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public int getQtdVeiculos() {
        return qtdVeiculos;
    }

    public void setQtdVeiculos(int qtdVeiculos) {
        this.qtdVeiculos = qtdVeiculos;
    }

    public int getQtdClientes() {
        return qtdClientes;
    }

    public void setQtdClientes(int qtdClientes) {
        this.qtdClientes = qtdClientes;
    }
    
    
}
