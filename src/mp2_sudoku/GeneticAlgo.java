/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp2_sudoku;

import java.util.ArrayList;

/**
 *
 * @author April Dae Bation
 */
public class GeneticAlgo {
    ArrayList<Integer> alleles;
    ArrayList<Individual> population;
    ArrayList<Individual> survivors;
    ArrayList<Individual> parents;
    ArrayList<Individual> offsprings;
    int maxGenerations = 200;
    Double recombination_rate = 0.9;
    Double survivor_rate = 0.2;
    Double mutation_rate = 0.05;
    int populationSize;
}

