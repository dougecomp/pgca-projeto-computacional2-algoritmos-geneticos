/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uefs.mestrado.exercicio.computacional2;

/**
 *
 * @author douglas
 */
public class Cliente extends Ponto {
    
    private int demanda;
    
    public Cliente(int x, int y, int demanda) {
        super(x,y);
        this.demanda = demanda;
    }
    
}
