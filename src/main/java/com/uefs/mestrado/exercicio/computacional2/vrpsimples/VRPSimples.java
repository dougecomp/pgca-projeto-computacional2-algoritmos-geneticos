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
    private final Random rand;
    private Ponto deposito;
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private int qtdVeiculos;
    private int qtdClientes;
    private int tamanhoPopulacao;
    
    public VRPSimples(String arquivoCasoTeste, int tamPop, int qtdGer, float txCruz, float txMut) {
        
        taxaCruzamento = txCruz;
        taxaMutacao = txMut;
        qtdGeracoes = qtdGer;
        tamanhoPopulacao = tamPop;
        clientes = new ArrayList<>();
        veiculos = new ArrayList<>();
        
        try {
            getCasoTeste(arquivoCasoTeste, " ");
        } catch(IOException e) {
            System.out.println("Não foi possível ler arquivo do caso de teste.");
            System.exit(0);
        }
        
        dados = new XYSeries("Melhor Indivíduo");
        rand = new Random();
        
    }
    
    /**
     * Método para executar o algoritmo genético
     * 
     */
    public void executar() {
        
        populacao = new Populacao(this.tamanhoPopulacao, this.clientes, this.veiculos);
        populacao.iniciarPopulacao(deposito);
        
        int geracoes = 0;
        while(geracoes < qtdGeracoes){

            ArrayList<Cromossomo> cromossomosSelecionados = selecionarCromossomosPorRoleta(
            populacao, populacao.getIndividuos().size());
            ArrayList<Cromossomo> resultCruzamento = cruzamentoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());
            ArrayList<Cromossomo> resultMutacao = mutacaoUmPonto((ArrayList<Cromossomo>)cromossomosSelecionados.clone());

            populacao.getIndividuos().addAll(resultCruzamento);
            populacao.getIndividuos().addAll(resultMutacao);

            cortar();

            dados.add(geracoes, populacao.getIndividuos().get(0).calcularFitness());
            geracoes++;
        
        }

        /*plotaGrafico();
        String s = "Melhor resultado obtido foi com custo igual a: " + getMelhorResultado().calcularFitness();

        JOptionPane.showMessageDialog(null, s);*/
        
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
            int ponto = rand.nextInt(cromossomosSelecionados.size() - 1);
            Cromossomo individuo = cromossomosSelecionados.get(ponto);
            int gene = rand.nextInt(individuo.getTamanho() - 1);

            if(rand.nextFloat() < taxaMutacao){
                individuo.aplicarMutacao();
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
//        while (cont < qtdCruzamentos) {
//            Cromossomo pai = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));
//            Cromossomo mae = cromossomosSelecionados.get(rand.nextInt(cromossomosSelecionados.size() - 1));
//
//            Cromossomo filho1 = new Cromossomo(pai.getGenes().size());
//            Cromossomo filho2 = new Cromossomo(mae.getGenes().size());
//
//            ponto = rand.nextInt(pai.getTamanho()-1);
//            if(rand.nextFloat() < taxaCruzamento) {
//                for (int i = 0; i < pai.getGenes().size(); i++) {
//                    if (i < ponto) {
//                        filho1.getGenes().add(i, pai.getGenes().get(i));
//                        filho2.getGenes().add(i, mae.getGenes().get(i));
//                    } else {
//                        filho1.getGenes().add(i,mae.getGenes().get(i));
//                        filho2.getGenes().add(i,pai.getGenes().get(i));
//                    }
//                }
//                filhos.add(filho1);
//                filhos.add(filho2);
//            }
//            cont++;
//        }
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

        for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
            sum += populacao.getIndividuos().get(i).calcularFitness() /*+ 1*/;
        }

        while (cont < qtdSele) {
            int numAleatorio = rand.nextInt( (int)sum);
            for (int i = 0; i < populacao.getTamanhoPopulacao(); i++) {
                sumParcial += populacao.getIndividuos().get(i).calcularFitness()/* + 1*/;
                if (sumParcial >= numAleatorio) {
                    cromossomosSelecionados.add(populacao.getIndividuos().get(i));
                    break;
                }
            }
            cont++;
         }

        return cromossomosSelecionados;
    }
    
    /**
     * Retirar os individuos com os menores valores de fitness
     */
    public void cortar() {
        populacao.ordenarPorFitness();

        for (int i = populacao.getIndividuos().size() - 1; i >= populacao.getTamanhoPopulacao(); i--) {
            populacao.getIndividuos().remove(i);
        }
    }

    /**
     * Retorna o indivíduo com melhor fitness, ou seja, a melhor solucao
     * encontrada para uma execução do algoritmo genético
     *
     * @return
     */
    public Cromossomo getMelhorResultado() {

        populacao.ordenarPorFitness();

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
