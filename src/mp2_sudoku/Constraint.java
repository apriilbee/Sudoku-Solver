/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp2_sudoku;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Constraint {
    private int boxIndex;
    private int position;
    private int value;
    
    Constraint(int gene, int position, int value){
        this.boxIndex = gene;
        this.position = position;
        this.value = value;
    }

    /**
     * @return the chromosome
     */
    public int getBoxIndex() {
        return boxIndex;
    }

    /**
     * @param boxIndex the chromosome to set
     */
    public void setBoxIndex(int boxIndex) {
        this.boxIndex = boxIndex;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
