
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package mp2_sudoku;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
///**
// *
// * @author April Dae Bation
// */
public class testFunctions {
    public static void main(String[] args) {
        testFunctions c = new testFunctions();
        ArrayList<Integer> a = new ArrayList<>(
                Arrays.asList(1,2,3,4,5,6,7)
        );
        ArrayList<Integer> b = new ArrayList<>(
                Arrays.asList(7,2,4,5,6,1,3)
        );
        System.out.println(c.NPointCrossover(a,b));
        
    }
    
     private ArrayList NPointCrossover(ArrayList p1, ArrayList p2) {
        ArrayList offsprings_ = new ArrayList();
        int point1 = (int) Math.ceil(Math.random()*(p1.size()-1));
        int point2 = (int) Math.ceil(Math.random()*(p1.size()-1));
        
        if(point2 < point1){
            int tmp = point1;
            point1 = point2;
            point2 = tmp;
        }
        System.out.println(point1 + " " + point2);
        
        ArrayList o1 = new ArrayList();
        ArrayList o2 = new ArrayList();
        
        List head1 = p2.subList(0, point1);
        List head2 = p1.subList(0, point1);
        for(int i = 0; i < head1.size(); i++){
            o1.add(head1.get(i));
            o2.add(head2.get(i));
        }
        
        List body1 = p1.subList(point1, point2);
        List body2 = p2.subList(point1, point2);
        for(int i = 0; i < body1.size(); i++){
            o1.add(body1.get(i));
            o2.add(body2.get(i));
        }
        
        List tail1 = p2.subList(point2, p1.size());
        List tail2 = p1.subList(point2, p2.size());
        for(int i = 0; i < tail1.size(); i++){
            o1.add(tail1.get(i));
            o2.add(tail2.get(i));
        }
        
        
        offsprings_.add(o1);
        offsprings_.add(o2);
        
        return offsprings_;
    }
    
    
    
    
    private Double countDuplicates(ArrayList tmp){
        Double fitness = 0.0;
        System.out.println("here");
        ArrayList checked = new ArrayList();
        ArrayList duplicate = new ArrayList();
        for(int i = 0; i < tmp.size(); i++){
            int allele = (int) tmp.get(i);
            if(!checked.contains(allele)){
                checked.add(allele);
                duplicate.add(allele);
                for(int j = i+1; j < tmp.size(); j++){
                    if (allele == (int)tmp.get(j)){
                        duplicate.add(tmp.get(j));
                    }
                }
                if (duplicate.size() > 1){
                    fitness += duplicate.size();
                }
                duplicate.clear();
            }
            else
                continue;
        }
        
        return fitness;
    }
}


//    public static void main(String[] args) {
//        testFunctions c = new testFunctions();
//        ArrayList<Integer> a = new ArrayList<>(
//                Arrays.asList(1,1,1,2,1,1,5)
//        );
//        ArrayList<Integer> b = new ArrayList<>(
//                Arrays.asList(1,2,3,4,5,6,7,8,9)
//        );
//     //   System.out.println(c.countUniqueAlleles(b));
//      //  c.crossover(a,b);
//        System.out.println(c.insertMutation(b));
//    }
//    private void crossover(ArrayList momChromosome, ArrayList dadChromosome) {
//        
////        ArrayList offsprings = new ArrayList();
////        
////        Double recombinationrate = Math.random();
////        if(recombinationrate < recombination){
////            offsprings.add(momChromosome);
////            offsprings.add(dadChromosome);
////            
////            return offsprings;
////        }
//        
//        int crossoverPoint = (int) Math.ceil(Math.random()*(momChromosome.size()-1));
//        ArrayList<Integer> c1 = new ArrayList();
//        ArrayList<Integer> c2 = new ArrayList();
//        
//        System.out.println(crossoverPoint);
//        
//        List head1 = momChromosome.subList(0, crossoverPoint);
//        List head2 = dadChromosome.subList(0, crossoverPoint);
//        for(int i = 0; i<head1.size(); i++){
//            c1.add((Integer) head1.get(i));
//            c2.add((Integer) head2.get(i));
//        }
//        System.out.println(c1);
//        System.out.println(c2);
//        
//        int size = crossoverPoint;
//        for(int i = crossoverPoint; size!=9; i++){
//            if(i == dadChromosome.size()){
//                i = 0;
//            }
//            Integer allele = (Integer) dadChromosome.get(i);
//            if(!c1.contains(allele)){
//                c1.add(allele);
//                size++;
//            }
//            else {
//                
//            }
//        }
//        System.out.println(c1);
//        
//        size = crossoverPoint;
//        for(int i = crossoverPoint; size!=9; i++){
//            if(i == momChromosome.size()){
//                i = 0;
//            }
//            Integer tmp = (Integer) momChromosome.get(i);
//            if(!c2.contains(tmp)){
//                c2.add(tmp);
//                size++;
//            }
//            else {
//                
//            }
//        }
//        System.out.println(c2);
//    }
//    
//    private int countUniqueAlleles(ArrayList tmp){
//        int unique = 0;
//        for(int i=0; i<tmp.size(); i++){
//            int allele = (int)tmp.get(i);
//            boolean isUnique = true;
//            for(int j=0; j<tmp.size(); j++){
//                if(allele == (int)tmp.get(j) && i!=j){
//                    isUnique = false;
//                    break;
//                }
//            }
//            if (isUnique == true)
//                unique++;
//        }
//        System.out.println();
//        return unique;
//    }
//    
//    private Double add(ArrayList tmp) {
//        Double sum = 0.0;
//        for (int i = 0; i < tmp.size(); i++) {
//            sum += (int) tmp.get(i);
//        }
//        return sum;
//    }
//      
//    private ArrayList insertMutation(ArrayList gene){
//        int point1 = 0;
//        int point2 = 0;
//        
//        while (point1 == point2){
//            point1 = (int) Math.ceil(Math.random()*(gene.size()-1));
//            point2 = (int) Math.ceil(Math.random()*(gene.size()-1));
//        }
//        
//        if (point1 > point2){
//            int tmp = point1;
//            point1 = point2;
//            point2 = tmp;
//        }
//        System.out.println(point1 + " " + point2);
//        int tmp = (int) gene.get(point2);
//        gene.remove(point2);
//        gene.add((point1+1),tmp );
//        
//        return gene;
//    }
//    
//        private ArrayList PMX(Individual mom, Individual dad) {
////        System.out.println("Parent 1 Grid: ");
////        printGrid(mom.getSudoku_grid());
////        System.out.println("Parent 2 Grid: ");
////        printGrid(dad.getSudoku_grid());
//
//        ArrayList offsprings = new ArrayList();
//        
//        int block = 0;
//        while (block < mom.getGenes().size()) {
//
//            ArrayList offsprings_1 = new ArrayList();
//            ArrayList offsprings_2 = new ArrayList();
//
//            int cp = (int) (Math.random() * mom.getGenes().get(0).size());//make a crossover point1
//            int cp2 = (int) (Math.random() * dad.getGenes().get(0).size());//make a crossover point2
//            if (cp == 0 && cp2 == mom.getGenes().get(0).size()) {
//                cp = (int) (Math.random() * mom.getGenes().get(0).size());//make a crossover point1
//                cp2 = (int) (Math.random() * dad.getGenes().get(0).size());//make a crossover point2
//            }
//            int head = 0, tail = 0;
//            if (cp < cp2) {
//                head = cp;
//                tail = cp2;
//            } else if (cp == cp2) {
//                if (cp != 0) {
//                    head = cp - 1;
//                    tail = cp2;
//                } else if (cp2 != mom.getGenes().size()) {
//                    head = cp;
//                    tail = cp2 + 1;
//                }
//
//            } else {
//                head = cp2;
//                tail = cp;
//            }
//
////            System.out.println("\n");
////            System.out.println("HEAD: " + head);
////
////            System.out.println("TAIL: " + tail);
//
//            ArrayList subgrid1 = mom.getGenes().get(block);
//
//            ArrayList subgrid2 = dad.getGenes().get(block);
//
//            System.out.println(subgrid1);
//            System.out.println(subgrid2);
//            int length = subgrid1.size();
//            //initialize to zero
//            for (int i = 0; i < length; i++) {
//                offsprings_1.add(0);
//                offsprings_2.add(0);
//            }
//
//            //copy inside cutting points
//            for (int i = head; i < tail; i++) {
//                offsprings_1.set(i, subgrid1.get(i));
//                offsprings_2.set(i, subgrid2.get(i));
//            }
//
//            offsprings_1 = permutate(subgrid2, offsprings_1, head, tail);
//            offsprings_2 = permutate(subgrid1, offsprings_2, head, tail);
//            offsprings.add(offsprings_1);
//            offsprings.add(offsprings_2);
//            //}
//            block++;
//        }
//        return offsprings;
//
//    }
//
//    public ArrayList permutate(ArrayList subgrid, ArrayList offspring, int head, int tail) {
//        int k = 0;
//        //mapping!
//        for (int i = head; i < tail; i++) {
//            if (!subgrid.get(i).equals(offspring.get(i))) {
//                if (!offspring.contains(subgrid.get(i))) {
//                    if (offspring.get(i).equals((int) 0)) {
//                        offspring.set(subgrid.indexOf(offspring.get(i)), subgrid.get(i));
//                    } else {
//                        k = (int) subgrid.get(i);
//                        while (!offspring.contains(subgrid.get(i))) {//covers loops!
//                            int j = subgrid.indexOf(k);
//                            if (offspring.get(j).equals(0)) {
//                                offspring.set(j, subgrid.get(i));
//                                break;
//                            } else {
//                                k = (int) offspring.get(j);
//                            }
//                        }//here
//                    }
//
//                }
//
//            }
//        }
//        //fill in the empty slots
//        for (int i = 0; i < offspring.size(); i++) {
//            if (!offspring.contains(subgrid.get(i))) {
//                if (offspring.get(i).equals((int) 0)) {
//                    offspring.set(i, subgrid.get(i));
//                }
//            }
//        }
//        return offspring;
//    }
//}
//
//
////private ArrayList crossover(Individual mom, Individual dad) {
//
////        System.out.println("mom:");
////     //   printGrid(mom.getSudoku_grid());
////        System.out.println("fitness:" + mom.getFitness());
////        System.out.println("dad:");
////     //   printGrid(dad.getSudoku_grid());
////        System.out.println("fitness:" + dad.getFitness());
////        System.out.println("offspring1");
////        printGrid(offspring1.getSudoku_grid());
////        System.out.println("fitness:" + evaluateFitness(offspring1));
////        System.out.println("offspring2");
////        printGrid(offspring2.getSudoku_grid());
////        System.out.println("fitness:" + evaluateFitness(offspring2));
////        System.out.println("");
//
//
//// private ArrayList mutate(Individual individual) {
//////        System.out.println("original individual");
//////        printGrid(individual.getSudoku_grid());
////        ArrayList<ArrayList> genes = individual.getGenes();
////        ArrayList<ArrayList> mutatedGenes = new ArrayList();
////        Double probability = Math.random();
////        for(int i = 0; i < genes.size(); i++){
////            ArrayList gene = genes.get(i);
////         // System.out.println("original " + gene);
////            //Double probability = Math.random();
////            if(probability < mutation_rate){
////                //System.out.println("mutate");
////                ArrayList mutatedGene = insertMutation(gene);
////                mutatedGenes.add(mutatedGene);
////            }
////            else{
////                mutatedGenes.add(gene);
////            }
////            
////        }
////        return mutatedGenes;
////    }
//
//
//private ArrayList order1Crossover(ArrayList mom_Gene, ArrayList dad_Gene) {
//        ArrayList<ArrayList> c = new ArrayList();
//        
//        Double probability = Math.random();
//        if(probability < recombination_rate){
//            int crossover = (int) Math.ceil(Math.random()*(mom_Gene.size()-1));
//            ArrayList<Integer> c1 = new ArrayList();
//            ArrayList<Integer> c2 = new ArrayList();
//
//            List head1 = mom_Gene.subList(0, crossover);
//            List head2 = dad_Gene.subList(0, crossover);
//            for(int i = 0; i<head1.size(); i++){
//                c1.add((Integer) head1.get(i));
//                c2.add((Integer) head2.get(i));
//            }
//
//            for(int i = crossover, size = crossover; size!=sudokuSize; i++){
//                if(i == dad_Gene.size()){
//                    i = 0;
//                }
//                Integer allele = (Integer) dad_Gene.get(i);
//                if(!c1.contains(allele)){
//                    c1.add(allele);
//                    size++;
//                }
//            }
//            c.add(c1);
//
//            for(int i = crossover, size = crossover; size!=sudokuSize; i++){
//                if(i == mom_Gene.size()){
//                    i = 0;
//                }
//                Integer tmp = (Integer) mom_Gene.get(i);
//                if(!c2.contains(tmp)){
//                    c2.add(tmp);
//                    size++;
//                }
//            }
//            c.add(c2);
//
//            return c;
//        }
//        else{
//            c.add(mom_Gene);
//            c.add(dad_Gene);
//            return c;
//        }
//    }



//ROULETTE
    //to check, call this after evaluate fitness
//    private void selectParents(){
//        ArrayList<Individual> parents = new ArrayList();
//        int numberOfParents = population.size() - survivors.size();
//        //assigns range 
//        ArrayList<Double> range = new ArrayList();
//        Double tmp = 0.0;
//        for(int i = 0; i < population.size(); i++){
//            range.add(tmp + population.get(i).getFitness());
//            tmp += population.get(i).getFitness();
//        }
//
//        //randomValue between 0 to biggest number in range
//        Random r = new Random();
//        Double min = 0.0;
//        Double max = range.get(range.size()-1);
//        int iteration = numberOfParents;
//        ArrayList selected = new ArrayList();
//        
//        for(int n = 0; n < iteration; n++){
//            double randomValue = min + (max - min) * r.nextDouble();
//        //    System.out.println(range);
//        //    System.out.println(randomValue);
//       
//            //finding the parent where random value falls on range
//            int individual = 0;
//            Double minValue = 0.0;
//            Double maxValue = 0.0;
//            for(int i = 0; i<range.size(); i++){
//                maxValue = range.get(i);
//                if(randomValue >= minValue && randomValue < maxValue){
//                    individual = i;
//                    break; 
//                }
//                minValue = maxValue;
//            }
//        //    if(!selected.contains(individual)){
//        //        selected.add(individual);
//        //        System.out.println(individual);
//                parents.add(population.get(individual));
//        //    }
//        //    else {
//        //        iteration++;
//        //    }
//        }
//        this.parents = parents;
//    }


//
// private void selectSurvivors() {
//        ArrayList<Individual> survivors_ = new ArrayList();
//
//        ArrayList<Double> fitness = new ArrayList();
//        for (int i = 0; i < population.size(); i++) {
//            fitness.add(population.get(i).getFitness());
//        }
//        //System.out.println(fitness);
//        Collections.sort(fitness);
//       // Collections.reverse(fitness);
//       
//        int numberOfSurvivors = (int) Math.ceil(survivor_rate * population.size());
//       
//        ArrayList<Individual> pop = (ArrayList) population.clone();
//        for (int i = 0; i < numberOfSurvivors; i++) {
//            Double f = fitness.get(i);
//            for (int j = 0; j < pop.size(); j++) {
//                if (f.equals(pop.get(j).getFitness())) {
//                    survivors_.add(pop.get(j));
//                    pop.remove(j);
//                    break;
//                }
//            }
//        }
//        this.survivors = survivors_;
     //   System.out.println(fitness);
//        for (int i = 0; i < survivors.size(); i++) {
//            printGrid(survivors.get(i).getSudoku_grid());
//            System.out.println(survivors.get(i).getFitness());
//            System.out.println("\n");
//        }
        //HERE, icheck pd if same ra ba ang alleles sa isa ka individual or noes. if no, add. if same, iskip
        //lang sa. then next. if hurot na ang population, choose nalang ddto sa giskipan.
//    }
//
//import java.util.ArrayList;
//import java.util.Collections;
//import mp2_sudoku.Individual;

//UPDATED ROULETTE
//private void selectParents(){
//        ArrayList<Individual> parents = new ArrayList();
//        int numberOfParents = population.size() - survivors.size();
//        //assigns range 
//        ArrayList<Double> range = new ArrayList();
//        Double tmp = 0.0;
//        for(int i = 0; i < population.size(); i++){
//            range.add(tmp + (100 - population.get(i).getFitness()));
//            tmp += population.get(i).getFitness();
//        }
//
//        //randomValue between 0 to biggest number in range
//        Random r = new Random();
//        Double min = 0.0;
//        Double max = range.get(range.size()-1);
//        int iteration = numberOfParents;
//        ArrayList selected = new ArrayList();
//        
//        for(int n = 0; n < iteration; n++){
//            double randomValue = min + (max - min) * r.nextDouble();
//        //    System.out.println(range);
//        //    System.out.println(randomValue);
//       
//            //finding the parent where random value falls on range
//            int individual = 0;
//            Double minValue = 0.0;
//            Double maxValue = 0.0;
//            for(int i = 0; i<range.size(); i++){
//                maxValue = range.get(i);
//                if(randomValue >= minValue && randomValue < maxValue){
//                    individual = i;
//                    break; 
//                }
//                minValue = maxValue;
//            }
//        //    if(!selected.contains(individual)){
//        //        selected.add(individual);
//        //        System.out.println(individual);
//                parents.add(population.get(individual));
//        //    }
//        //    else {
//        //        iteration++;
//        //    }
//        }
//        this.parents = parents;
//    }
//    


//private void selectParents() {
//        ArrayList<Individual> parents = new ArrayList();
//        int numberOfParents = population.size() - survivors.size();
//        for(int i=0, k=2; i < numberOfParents; i++){
//            ArrayList<Individual> t = new ArrayList();
//            //System.out.println("Tournament " + i);
//            for(int j=0; j<k; j++){
//                Random r = new Random();
//                int index = r.nextInt(population.size());
//                //System.out.println(j+".) "+ index);
//                t.add(population.get(index));
//            }
//            parents.add(getFittestIndividual(t));
//        }
//        
//        this.parents = parents;
//    }

//
//
//   private Double evaluateFitness(Individual cand) {
//
//        Double fitness = 0.0;
//        Integer total = sudokuSize;
//        HashSet setHori = new HashSet();
//        HashSet setVert = new HashSet();
//        for (int i = 0; i < cand.size; i++) {
//            for (int j = 0; j < cand.size; j++) {
//                int k = (int) cand.getHorizontal().get(i).get(j);
//                if (setHori.add(k) == true) {
//                    setHori.add(k);
//                } else {
//                    fitness++;
//                }
//            }
//            //System.out.println(fitness);
//            setHori.clear();
//        }
//
//        for (int i = 0; i < cand.size; i++) {
//            for (int j = 0; j < cand.size; j++) {
//                int k = (int) cand.getVertical().get(i).get(j);
//                if (setVert.add(k) == true) {
//                    setVert.add(k);
//                } else {
//                    fitness++;
//                }
//            }
//            //System.out.println(fitness);
//            setVert.clear();
//        }
//
//        return fitness;
//    }