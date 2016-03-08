/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp2_sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author April Dae Bation
 */
public class SudokuSolverReal extends GeneticAlgo {
    Integer sudokuSize;
    Integer[][] sudokuGrid;
    ArrayList<Constraint> constraints;
    long startTime = System.currentTimeMillis();
    long endTime;
    
    SudokuSolverReal(String sudokuFile) throws FileNotFoundException {
        Scanner read = new Scanner(new File(sudokuFile));
        read.useDelimiter("\n");
        int row = 0;
        while (read.hasNextLine()) {
            String tmp = read.nextLine();
            if (tmp.isEmpty()) {
                continue;
            } else if (tmp.length() == 1) {
                sudokuSize = Integer.parseInt(tmp);
                sudokuGrid = new Integer[sudokuSize][sudokuSize];
            } else {
                char[] c = tmp.toCharArray();
                for (int i = 0, col = 0; i < c.length; i++) {
                    if (c[i] != ' ') {
                        sudokuGrid[row][col] = Character.getNumericValue(c[i]);
                        col++;
                    }
                }
                row++;
            }
        }

        alleles = new ArrayList();
        for (int i = 0; i < sudokuSize; i++) {
            alleles.add(i + 1);
        }
        
        getConstraints();
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        boolean solved = false;
        
        String filename = "";
        while (!new File(filename).exists()){
            System.out.println("Please enter sudoku file name: ");
            filename = scan.nextLine();
        }
        
        int restarts = 0;
        int generations = 0;
        
        SudokuSolverReal s = new SudokuSolverReal(filename);
        while (restarts < 3){
                        
            s.printGrid(s.sudokuGrid);
            s.initializePopulation();
            s.evaluatePopulationFitness();  
            System.out.println("Initial population:");
            //s.printFitness(s.population);
            System.out.println("fitness:" + s.totalPopulationFitness());
            
            for (generations = 0; generations < s.maxGenerations; generations++) {
                System.out.println("\n\nGENERATION " + generations);
                s.selectSurvivors(); 
                s.selectParents();
                s.generateOffsprings();
                s.setNewPopulation();
                s.mutatePopulation();
                //s.printFitness(s.population);
                s.evaluatePopulationFitness();
                System.out.println("Total Population Fitness:" + s.totalPopulationFitness());
                System.out.println("Fittest individual:" + s.getFittestIndividual(s.population).getFitness());
                if(s.sudokuSolved()){ 
                    solved = true;
                    break;
                }
                
            }
            if (solved == true){
                System.out.println("SUDOKU SOLVED!");
                s.printGrid(s.getFittestIndividual(s.population).getSudoku_grid());
                break;
            }
            restarts++;
        }
        s.endTime   = System.currentTimeMillis();
        s.printToFile(filename,restarts, generations, solved, s.getFittestIndividual(s.population).getSudoku_grid() );
    }
    
    private void printToFile(String file_name,int restarts, int generations, boolean solved, Integer[][] grid) throws FileNotFoundException, UnsupportedEncodingException{
        String filename = "puzzle_" + file_name + ".out";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        Long dif = endTime - startTime;
        
        writer.println("A. Parameters used by the GA ");
        writer.println("\ta. Representation: Permutation\n\tb. Population size: " + population.size()
                + "\n\tc. Maximum Generation: " + maxGenerations +"\n\td. Recombination Method: Partially Mapped Crossover"
                + "\n\te. Mutation Method: Inverse Mutation\n\tf. Parent Selection Method: Tournament Selection"
                + "\n\tg. Survivor Selection Method: Elitism\n\th. Probability for Recombination: " + recombination_rate
                + "\n\ti. Probability for Mutation: " + mutation_rate + "\n\tj. Survival Rate:" + survivor_rate);
        writer.println("B. Number of Generations Ran: " + generations);
        if(dif>1000){
            dif = (endTime-startTime)/1000;
            writer.println("C. Total Running Time: " + (dif) + " seconds");
        }else{
            writer.println("C. Total Running Time: " + (dif) + " milliseconds");
        }
        writer.println("D. Number of Population Restarts: " + (restarts));
        writer.println("E. Phenotype of Best Performing Individual: ");
        for (int row = 0; row < grid.length; row++) {
            writer.print("\t");
            for (int col = 0; col < grid.length; col++) {
                writer.print(grid[row][col] + " ");
            }
            writer.println("");
        }
        if(solved == true)
            writer.println("F. Puzzle Solved");
        else
            writer.println("F. Puzzle Not Solved");
        
        writer.close();
    }
    
    
    
    private boolean sudokuSolved(){
        if (getFittestIndividual(population).getFitness() == 0)
            return true;
        return false;
    }
   
    
    private void printFitness(ArrayList<Individual> pop){
        for (int i = 0; i < pop.size(); i++){
            System.out.print(pop.get(i).getFitness() + " ");
        }
        System.out.println("");
    }
    
    private void getConstraints(){
        constraints = new ArrayList();
        Individual original = new Individual(copySudoku(),sudokuSize);
        ArrayList<ArrayList> genes = original.getGenes();
        for(int i = 0; i < genes.size(); i++){
            ArrayList<Integer> tmp = genes.get(i);
            for (int j = 0; j < tmp.size(); j++){
                if (tmp.get(j) != 0){
                    Constraint c = new Constraint(i, j, tmp.get(j));
                    constraints.add(c);
                }
            }
        }
    }
    
    private void print(ArrayList<Individual> tmp){
        
        for (int i = 0; i < tmp.size(); i++) {
           printGrid(tmp.get(i).getSudoku_grid());
           //System.out.println(population.get(generations).getFitness());
            System.out.println("");
        }
    }
    
    private Double totalPopulationFitness() {
        Double total = 0.0;
        for(int i = 0; i<population.size(); i++){
            total += population.get(i).getFitness();
        }
        return total;
    }

    private void printGrid(Integer[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println("");
        }
    }

    private void initializePopulation() {
        population = new ArrayList();
        
        if(sudokuSize == 4){
            populationSize = 30; 
        }
        else if (sudokuSize == 6){
            populationSize = 300;
        }
        else if (sudokuSize == 9){
            populationSize = 600;
        }
        else {
            populationSize = 800;
        }
        
        for (int i = 0; i < populationSize; i++) {
            Integer[][] copy = copySudoku();
            Individual cand = new Individual(copy, sudokuSize);
            initializeCandidate(cand);
            population.add(cand);
        }
    }

    private Integer[][] copySudoku() {
        Integer[][] s = new Integer[sudokuSize][sudokuSize];
        for (int row = 0; row < sudokuSize; row++) {
            System.arraycopy(sudokuGrid[row], 0, s[row], 0, sudokuSize);
        }
        return s;
    }

    private void initializeCandidate(Individual cand) {
        ArrayList subgrids = cand.getGenes();

        for (int sub = 0; sub < subgrids.size(); sub++) {
            ArrayList<Integer> tmp = (ArrayList) subgrids.get(sub);
            ArrayList c = (ArrayList) alleles.clone();

            for (int i = 0; i < tmp.size(); i++) {
                if (tmp.get(i) != 0) {
                    c.remove(tmp.get(i));
                }
            }

            for (int i = 0; i < tmp.size(); i++) {
                if (tmp.get(i) == 0) {
                    Collections.shuffle(c);
                    tmp.set(i, (Integer) c.get(0));
                    c.remove(0);
                }
            }
        }
        cand.initializeGrid(subgrids);
    }

    private void evaluatePopulationFitness() {
        for (int i = 0; i < population.size(); i++) {
            Individual cand = population.get(i);
            Double fitness = evaluateFitness(cand);
            cand.setFitness(fitness);
        }
    }
    
   

    private Double evaluateFitness(Individual cand) {
        Double fitness = 0.0;
        for(int i=0; i<cand.size; i++){
            for(int allele = 1; allele <= cand.size; allele++){
                if(!cand.getHorizontal().get(i).contains(allele)){
                    fitness++;
                }
            }
        }
        
        for(int i=0; i<cand.size; i++){
            for(int allele = 1; allele <= cand.size; allele++){
                if(!cand.getVertical().get(i).contains(allele)){
                    fitness+=2;
                }
            }
        }
        return fitness;
    }
    
    //Elitism
    private void selectSurvivors() {
        ArrayList<Individual> survivors_ = new ArrayList();
        ArrayList<Individual> pop = (ArrayList<Individual>) population.clone();
        int numberOfSurvivors = (int) Math.ceil(survivor_rate*population.size());
       
        for(int i = 0; i < numberOfSurvivors; i++){
            Individual fittest = pop.get(i);
            for(int j=0; j<pop.size(); j++){
                Individual tmp = pop.get(j);
                if(tmp.getFitness() < fittest.getFitness() && tmp!=fittest)
                    fittest = tmp;
            }
            survivors_.add(fittest);
            pop.remove(fittest);
        }
        this.survivors = survivors_;

    }
    
    //Tournament Selection
    private void selectParents() {
        ArrayList<Individual> parents_ = new ArrayList();
        int numberOfParents = population.size() - survivors.size();
        Random rand = new Random();
        for(int i=0, k=3; i < numberOfParents; i++){
            ArrayList<Individual> t = new ArrayList();
            for(int j=0; j<k; j++){
                Random r = new Random();
                int index = r.nextInt(population.size());
                t.add(population.get(index));
            }
            parents_.add(getFittestIndividual(t));
            t.clear();
        }
        this.parents = parents_;
    }
    
    private Individual getFittestIndividual(ArrayList<Individual> candidates) {
        Individual fittest = candidates.get(0);
        for(int i = 1; i < candidates.size(); i++){
            if (fittest.getFitness() > candidates.get(i).getFitness())
                fittest = candidates.get(i);
        }
        return fittest;
    }

    private void generateOffsprings() {
        ArrayList<Individual> offsprings_ = new ArrayList();
        for(int i=0; i < parents.size(); i+=2){
            Individual parent1 = parents.get(i);
            Individual parent2 = parents.get(i+1);
            Double probability = Math.random();
            if(probability < recombination_rate){
                ArrayList<Individual> tmp = crossover(parent1,parent2);
                offsprings_.add(tmp.get(0));
                offsprings_.add(tmp.get(1));
            }
            else {
                offsprings_.add(parent1);
                offsprings_.add(parent2);
            }
        }
        
        this.offsprings = offsprings_;
        for (int i = 0; i < offsprings.size(); i++) {
            Individual cand = offsprings.get(i);
            Double fitness = evaluateFitness(cand);
            cand.setFitness(fitness);
        }
    
    }

    private ArrayList crossover(Individual mom, Individual dad) {
        ArrayList<Individual> offsprings_ = new ArrayList();
        
        ArrayList<ArrayList> mom_genes = mom.getGenes();
        ArrayList<ArrayList> dad_genes = dad.getGenes();
        ArrayList<ArrayList> child1_genes = new ArrayList();
        ArrayList<ArrayList> child2_genes = new ArrayList();
        

       
        for(int i = 0; i < mom_genes.size(); i++){
            //ang irecombine kay kato ra dili constraints. 
            ArrayList p1 = mom_genes.get(i);
            ArrayList p2 = dad_genes.get(i);
            ArrayList cons = new ArrayList();
            // System.out.println(p1 );
            for(int j = 0; j < constraints.size(); j++){
                if(constraints.get(j).getBoxIndex() == i){
                    cons.add(constraints.get(j).getValue());
                }
            }
            
            //System.out.println(cons);
            for(int j=0; j<cons.size(); j++){
                p1.remove((Object)cons.get(j));
                p2.remove((Object)cons.get(j));
            }
            //System.out.println(p1 );
            
            ArrayList genes = PMXCrossover(p1,p2);
            ArrayList o1 = (ArrayList) genes.get(0);
            ArrayList o2 = (ArrayList) genes.get(1);
            
            for(int j = 0; j<constraints.size(); j++){
                if(constraints.get(j).getBoxIndex() == i){
                    o1.add(constraints.get(j).getPosition(), constraints.get(j).getValue());
                    o2.add(constraints.get(j).getPosition(), constraints.get(j).getValue());
                }
            }
            
            child1_genes.add(o1);
            child2_genes.add(o2);
        }
        Individual offspring1 = new Individual(copySudoku(), sudokuSize);
                //  offspring1.initializeGrid(setConstraints(child1_genes));
                offspring1.initializeGrid((child1_genes));

        Individual offspring2 = new Individual(copySudoku(), sudokuSize);
                //   offspring2.initializeGrid(setConstraints(child2_genes));
                offspring2.initializeGrid((child2_genes));


        offsprings_.add(offspring1);
        offsprings_.add(offspring2);

        return offsprings_;


    }
    
    private ArrayList order1Crossover(ArrayList mom_Gene, ArrayList dad_Gene) {
        ArrayList<ArrayList> c = new ArrayList();

        int crossover = (int) Math.ceil(Math.random()*(mom_Gene.size()-1));
        ArrayList<Integer> c1 = new ArrayList();
        ArrayList<Integer> c2 = new ArrayList();

        List head1 = mom_Gene.subList(0, crossover);
        List head2 = dad_Gene.subList(0, crossover);
        for(int i = 0; i<head1.size(); i++){
            c1.add((Integer) head1.get(i));
            c2.add((Integer) head2.get(i));
        }

        for(int i = crossover, size = crossover; size!=sudokuSize; i++){
            if(i == dad_Gene.size()){
                i = 0;
            }
            Integer allele = (Integer) dad_Gene.get(i);
            if(!c1.contains(allele)){
                c1.add(allele);
                size++;
            }
        }
        c.add(c1);

        for(int i = crossover, size = crossover; size!=sudokuSize; i++){
            if(i == mom_Gene.size()){
                i = 0;
            }
            Integer tmp = (Integer) mom_Gene.get(i);
            if(!c2.contains(tmp)){
                c2.add(tmp);
                size++;
            }
        }
        c.add(c2);

        return c;
    }
    
     private ArrayList PMXCrossover(ArrayList parent1, ArrayList parent2) {
        ArrayList offsprings_ = new ArrayList();
        ArrayList c1 = new ArrayList() ,c2 = new ArrayList();
        int p = 0, p2 = parent1.size(), head = 0, tail = 0, total = parent1.size();

        while (p == 0 && p2 == parent1.size()) {
            p = (int) (Math.random() * parent1.size());//make a crossover point1
            p2 = (int) (Math.random() * parent2.size());//make a crossover point2    
        }
        if (p < p2) {
            head = p;
            tail = p2;
        } else if (p == p2) {
            if (p != 0) {
                head = p - 1;
                tail = p2;
            } else if (p2 != total) {
                head = p;
                tail = p + 1;
            }
        } else {
            head = p2;
            tail = p;
        }


        //initialize offsprings_ to 0
        for (int i = 0; i < total; i++) {
                    c1.add(0);
                    c2.add(0);
                }
        //copy inside the cutting points
         for (int i = head; i < tail; i++) {
                    c1.set(i, parent1.get(i));
                    c2.set(i, parent2.get(i));
                }
         
         
        int k = 0;
        //mapping!
        for (int i = head; i < tail; i++) {
            if (!parent2.get(i).equals(c1.get(i))) {
                if (!c1.contains(parent2.get(i))) {
                    if (c1.get(i).equals((int) 0)) {
                        c1.set(parent2.indexOf(c1.get(i)), parent2.get(i));
                    } else {
                        k = (int) parent2.get(i);
                        while (!c1.contains(parent2.get(i))) {//covers loops!
                            int j = parent2.indexOf(k);
                            if (c1.get(j).equals(0)) {
                                c1.set(j, parent2.get(i));
                                break;
                            } else {
                                k = (int) c1.get(j);
                            }
                        }//here
                    }

                }

            }
        }
        //fill in the empty slots
        for (int i = 0; i < c1.size(); i++) {
            if (!c1.contains(parent2.get(i))) {
                if (c1.get(i).equals((int) 0)) {
                    c1.set(i, parent2.get(i));
                }
            }
        }
        offsprings_.add(c1);
        
        k = 0;
        //mapping!
        for (int i = head; i < tail; i++) {
            if (!parent1.get(i).equals(c2.get(i))) {
                if (!c2.contains(parent1.get(i))) {
                    if (c2.get(i).equals((int) 0)) {
                        c2.set(parent1.indexOf(c2.get(i)), parent1.get(i));
                    } else {
                        k = (int) parent1.get(i);
                        while (!c2.contains(parent1.get(i))) {//covers loops!
                            int j = parent1.indexOf(k);
                            if (c2.get(j).equals(0)) {
                                c2.set(j, parent1.get(i));
                                break;
                            } else {
                                k = (int) c2.get(j);
                            }
                        }//here
                    }

                }

            }
        }
        //fill in the empty slots
        for (int i = 0; i < c2.size(); i++) {
            if (!c2.contains(parent1.get(i))) {
                if (c2.get(i).equals((int) 0)) {
                    c2.set(i, parent1.get(i));
                }
            }
        }
        offsprings_.add(c2);
         
        
        return offsprings_;
    }
    
    private ArrayList setConstraints(ArrayList<ArrayList> genes) {
        //System.out.println("ch");
        //System.out.println(genes);
        for(int i = 0; i < genes.size(); i++){
            Constraint c = constraints.get(i);
            ArrayList ch = genes.get(c.getBoxIndex());
            if ((Integer)ch.get(c.getPosition()) != c.getValue()){
                //swap
                Integer tmp = (Integer)ch.get(c.getPosition()); 
                Integer pos = findAllele(ch, c.getValue()); 
                ch.set(pos, tmp);
                ch.set(c.getPosition(), c.getValue());
            }
            genes.set(c.getBoxIndex(),ch);
        }
        return genes;
        
    }

    private Integer findAllele(ArrayList ch, Integer tmp) {
        int pos;
        for(pos = 0; pos < ch.size(); pos++){
            if(ch.get(pos) == tmp)
                break;
        }
        return pos;
    }

    private void setNewPopulation() {
        population.clear();
        population.addAll(survivors);
        population.addAll(offsprings);
        evaluatePopulationFitness();
    }
   

    private void mutatePopulation() {
        ArrayList<Individual> mutatedPopulation = new ArrayList();
        for(int i = 0; i < population.size(); i++){
            Double probability = Math.random();
            if(probability < mutation_rate){
                Individual ind = new Individual(copySudoku(),sudokuSize);
                      ind.initializeGrid(setConstraints(mutate(population.get(i))));
                      //ind.initializeGrid(mutate(population.get(generations)));
                mutatedPopulation.add(ind);
            }
            else {
                mutatedPopulation.add(population.get(i));
            }
        }
        population.clear();
       
        population.addAll(mutatedPopulation);
        evaluatePopulationFitness();
    }

    private ArrayList mutate(Individual individual) {
        ArrayList<ArrayList> genes = individual.getGenes();
        ArrayList<ArrayList> mutatedGenes = new ArrayList();
        ArrayList cons = new ArrayList();
        for(int i = 0; i < genes.size(); i++){
            ArrayList gene = genes.get(i);
           
            for(int j = 0; j < constraints.size(); j++){
                if(constraints.get(j).getBoxIndex() == i){
                    cons.add(constraints.get(j).getValue());
                }
            }
            
            for(int j=0; j<cons.size(); j++){
                gene.remove((Object)cons.get(j));
            }
            
            ArrayList mutatedGene = inverseMutation(gene);
            for(int j = 0; j<constraints.size(); j++){
                if(constraints.get(j).getBoxIndex() == i){
                    mutatedGene.add(constraints.get(j).getPosition(), constraints.get(j).getValue());
                }
            }
            cons.clear();
            mutatedGenes.add(mutatedGene);
        }
        return mutatedGenes;
    }
    
    private ArrayList inverseMutation(ArrayList gene) {
        ArrayList mutatedGene = new ArrayList();
        int point1 = (int) Math.ceil(Math.random()*(gene.size()-1));
        int point2 = (int) Math.ceil(Math.random()*(gene.size()-1));

        if (point1 > point2){
            int tmp = point1;
            point1 = point2;
            point2 = tmp;
        }
        
        List head = gene.subList(0, point1);
        for(int i = 0; i < head.size(); i++){
            mutatedGene.add(head.get(i));
        }
        
        List reverse = gene.subList(point1, point2);
        
        Collections.reverse(reverse);
        for(int i = 0; i < reverse.size(); i++){
            mutatedGene.add(reverse.get(i));
        }
       
        List tail = gene.subList(point2, gene.size());
 
        for(int i = 0; i < tail.size(); i++){
            mutatedGene.add(tail.get(i));
        }

        return mutatedGene;
    }
    
     private ArrayList insertMutation(ArrayList gene){
        int point1 = 0;
        int point2 = 0;
        
        while (point1 == point2){
            point1 = (int) Math.ceil(Math.random()*(gene.size()-1));
            point2 = (int) Math.ceil(Math.random()*(gene.size()-1));
        }
        
        if (point1 > point2){
            int tmp = point1;
            point1 = point2;
            point2 = tmp;
        }
      
        int tmp = (int) gene.get(point2);
        gene.remove(point2);
        gene.add((point1+1),tmp );
        
        return gene;
    }
     
     private ArrayList swapMutation(ArrayList gene , int boxIndex) {
        ArrayList mutatedGene = new ArrayList();
        ArrayList box_constraints = new ArrayList();
        
        if (gene.size() == 1){
            return gene;
        }
        
        int point1 = 0;
        int point2 = 0; 

        point1 = (int) Math.ceil(Math.random() * (gene.size() - 1));
        point2 = (int) Math.ceil(Math.random() * (gene.size() - 1));
        
        int temp = (int) gene.get(point1);
        gene.set(point1, gene.get(point2));
        gene.set(point2, temp);

        return gene;
    }
}
