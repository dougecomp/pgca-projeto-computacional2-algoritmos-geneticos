package com.uefs.mestrado.exercicio.computacional2;

import com.uefs.mestrado.exercicio.computacional2.vrpc.GeneVRPC;
import com.uefs.mestrado.exercicio.computacional2.vrpsimples.VRP;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LinesComponent extends JComponent{

    private static class Line{
        final int x1; 
        final int y1;
        final int x2;
        final int y2;
        final int demanda;
        final Color color;
        final String label;

        public Line(int x1, int y1, int x2, int y2, Color color, String label, int demanda) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
            this.label = label;
            this.demanda = demanda;
        }               
    }

    private final LinkedList<Line> lines = new LinkedList<>();

    public void addLine(int x1, int x2, int x3, int x4, String label, int demanda) {
        addLine(x1, x2, x3, x4, Color.black,label, demanda);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color, String label, int demanda) {
        lines.add(new Line(x1,x2,x3,x4, color, label, demanda));        
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            
            // Linha da rota
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            
            // Círculos representando os clientes/depósito
            g.drawOval(line.x1, line.y1-5, 10, 10);
            g.drawOval(line.x2, line.y2-5, 10, 10);
            
            // Label do custo da rota
            g.drawString(line.label,(line.x1+line.x2)/2, (line.y1+line.y2)/2);
            
            // Label de demanda no ponto
            g.drawString(""+line.demanda+"", line.x1, line.y1-5);
        }
    }
    
    public static void iniciar() {
        
        JFrame testFrame = new JFrame();
        testFrame.setTitle("Exercicio Computacional 2 - PGCA009");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(500, 500));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton executeButton = new JButton("Executar AG");
        JButton botaoLimpar = new JButton("Limpar");
        buttonsPanel.add(executeButton);
        buttonsPanel.add(botaoLimpar);
        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        executeButton.addActionListener((ActionEvent e) -> {
            VRP algoritmo = TesteVRP.rodar();
            GeneVRPC melhor = algoritmo.getMelhorResultado().getGenes();
            List<List<Ponto>> rotas = melhor.getRotas();
            int k = 0;
            double custoCompleto = 0;
            for (List<Ponto> rota : rotas) {
                Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
                int totalDemandas = 0;
                double totalCusto = 0;
                for (int i = 0; i < rota.size(); i++) {
                    if( i + 1 < rota.size() ) { // Não tem o que desenhar após chegar no depósito
                        int x1 = (int) (rota.get(i).getX());
                        int x2 = (int) (rota.get(i+1).getX());
                        int y1 = (int) (rota.get(i).getY());
                        int y2 = (int) (rota.get(i+1).getY());
                        int demanda = 0;
                        double distancia = getDistanciaEuclidiana(rota.get(i), rota.get(i+1));
                        String label = "" + String.format("%.2f", distancia) + "";
                        if (rota.get(i) instanceof Cliente) {
                            Cliente c = (Cliente) rota.get(i);
                            demanda = c.getDemanda();
                        }
                        comp.addLine(x1, y1, x2, y2, randomColor, label, demanda);
                        totalCusto += distancia;
                        custoCompleto += distancia;
                        totalDemandas += demanda;
                    }
                }
                //int indice = rotas.indexOf(rota);
                System.out.println("Custo total da rota "+k+": "+totalCusto);
                System.out.println("Demanda total da rota "+k+": "+totalDemandas+" de ("+melhor.getVeiculos().get(k).getCapacidade()+")");
                k++;
            }
            System.out.println("Custo total de TODAS AS ROTAS: "+custoCompleto);
        });
        botaoLimpar.addActionListener((ActionEvent e) -> {
            comp.clearLines();
        });
        testFrame.pack();
        testFrame.setVisible(true);
        
    }
    
    public static void teste() {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(500, 500));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton newLineButton = new JButton("New Line");
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);
        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        newLineButton.addActionListener((ActionEvent e) -> {
            int x1 = (int) (Math.random()*500);
            int x2 = (int) (Math.random()*500);
            int y1 = (int) (Math.random()*500);
            int y2 = (int) (Math.random()*500);
            Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            comp.addLine(x1, y1, x2, y2, randomColor, null, 0);
        });
        clearButton.addActionListener((ActionEvent e) -> {
            comp.clearLines();
        });
        testFrame.pack();
        testFrame.setVisible(true);
    }

    public static double getDistanciaEuclidiana(Ponto p, Ponto q) {
        double distancia;
        distancia = Math.sqrt(Math.pow(p.getX() - q.getX(), 2) + Math.pow(p.getY() - q.getY(), 2)); // distância euclidiana;
        return distancia;
    }
    
    public static void main(String[] args) {
        
        iniciar();
        //teste();
        
    }

}