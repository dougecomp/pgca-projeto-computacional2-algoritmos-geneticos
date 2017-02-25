package com.uefs.mestrado.exercicio.computacional2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

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
    final Color color;

    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }               
}

private final LinkedList<Line> lines = new LinkedList<>();

public void addLine(int x1, int x2, int x3, int x4) {
    addLine(x1, x2, x3, x4, Color.black);
}

public void addLine(int x1, int x2, int x3, int x4, Color color) {
    lines.add(new Line(x1,x2,x3,x4, color));        
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
        g.drawLine(line.x1, line.y1, line.x2, line.y2);
        //g.drawOval(line.x1, line.y1-5, 10, 10);
        //g.drawOval(line.x2, line.y2-5, 10, 10);
        //g.drawString("teste",(line.x1+line.x2)/2, (line.y1+line.y2)/2);
    }
}

public static void main(String[] args) {
    JFrame testFrame = new JFrame();
    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        comp.addLine(x1, y1, x2, y2, randomColor);
    });
    clearButton.addActionListener((ActionEvent e) -> {
        comp.clearLines();
    });
    testFrame.pack();
    testFrame.setVisible(true);
}

}