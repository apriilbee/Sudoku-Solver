/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp2_sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author April Dae Bation
 */
class Individual {
    private Integer[][] sudoku_grid;
    ArrayList<Integer[][]> genes;
    int size;
    private Double fitness;
    
    
    Individual(Integer[][] sudoku, int size){
        sudoku_grid = sudoku;
        this.size = size;
    }
    
    
    public Double getFitness() {
        return fitness;
    }

   
    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }
    
    public Integer[][] getSudoku_grid() {
        return sudoku_grid;
    }
    
    
    int[] getBlockScheme(int size){
        int[] dimension = new int[2];
        
        switch (size) {
            case 9:
                dimension[0] = 3;
                dimension[1] = 3;
                break;
            case 8:
                dimension[0] = 2;
                dimension[1] = 4;
                break;
            case 6:
                dimension[0] = 2;
                dimension[1] = 3;
                break;
            case 4:
                dimension[0] = 2;
                dimension[1] = 2;
                break;
            default:
                break;
        }
        
        return dimension;
    }

    ArrayList<ArrayList> getGenes() {
        ArrayList genes = new ArrayList();
        int[] dimension = getBlockScheme(size);
        
        int genes_Row = dimension[0];
        int genes_Col = dimension[1];
        
        
        for(int sudoku_Row = 0; sudoku_Row < size; ){
            for (int sudoku_Col = 0; sudoku_Col < size; ){
                ArrayList tmp = new ArrayList();
                for(int row = sudoku_Row; row < genes_Row; row++){
                    for(int col = sudoku_Col; col < genes_Col; col++){
                        tmp.add(getSudoku_grid()[row][col]);
                    }
                }
                genes.add(tmp);
                sudoku_Col+=dimension[1];
                genes_Col += dimension[1] ;
            }
            sudoku_Row += dimension[0];
            genes_Row += dimension[0];
            genes_Col = dimension[1];
        }
        return genes;
    }
    
    ArrayList<ArrayList> getHorizontal(){
        ArrayList horizontal = new ArrayList();

        for(int row = 0; row < size; row++){
            ArrayList tmp = new ArrayList();
            for(int col = 0; col < size; col++){
               tmp.add(getSudoku_grid()[row][col]);
            }
            horizontal.add(tmp);
        }
        
        return horizontal;
    }
    
    ArrayList<ArrayList> getVertical(){
        ArrayList vertical = new ArrayList();

        for(int col = 0; col < size; col++){
            ArrayList tmp = new ArrayList();
            for(int row = 0; row < size; row++){
               tmp.add(getSudoku_grid()[row][col]);
            }
            vertical.add(tmp);
        }
        
        return vertical;
    }
    
    void initializeGrid(ArrayList genes){
        int[] dimension = getBlockScheme(size);
        int genes_Row = dimension[0];
        int genes_Col = dimension[1];
        int i = 0;
        
        for(int sudoku_Row = 0; sudoku_Row < size;){
            int ctr = 0;
            ArrayList tmp = (ArrayList) genes.get(i++);
            for (int sudoku_Col = 0; sudoku_Col < size; ){
                for(int row = sudoku_Row; row < genes_Row; row++){
                    for(int col = sudoku_Col; col < genes_Col; col++){
                        if(ctr == dimension[0]*dimension[1]){
                            tmp = (ArrayList) genes.get(i++);
                            ctr = 0;
                        }
                        getSudoku_grid()[row][col] = (Integer) tmp.get(ctr++);
                    }
                }
                sudoku_Col+=dimension[1];
                genes_Col += dimension[1] ;
            }
            sudoku_Row += dimension[0];
            genes_Row += dimension[0];
            genes_Col = dimension[1];

        }
        
    }

    ArrayList getAlleles(){
        ArrayList a = new ArrayList();
        for(int i = 0; i<size; i++){
            for (int j = 0; j < size; j++){
                a.add(sudoku_grid[i][j]);
            }
        }
        return a;
    }

}
