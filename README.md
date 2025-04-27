# Java Sudoku Solver using Evolutionary Algorithms

This is a **Java-based Sudoku Solver** that applies **Evolutionary Algorithms** to solve Sudoku puzzles of any `n x n` size (e.g., 4x4, 6x6, 9x9). This project was developed for a university assignment and demonstrates the use of heuristic search and AI optimization techniques.

---

## Project Structure

```
├── Constraint.java           # Defines fixed puzzle constraints
├── GeneticAlgo.java          # Abstract GA framework (parent class)
├── Individual.java           # Candidate solution representation
├── SudokuSolverReal.java     # Main execution and GA logic
├── testFunctions.java        # Experimental GA operators and tests
└── README.md                 # Project documentation
```

---

## Key Features

- Solves Sudoku puzzles using **Genetic Algorithms**
- Accepts puzzle files with customizable sizes
- Uses domain-specific constraint enforcement (fixed clues)
- Implements:
    - Tournament selection
    - Elitist survivor selection
    - PMX crossover and inverse mutation
    - Adaptive restart mechanism
- Output includes performance metrics and solution report

---

## Input Format

The solver accepts text files structured as:

```
9
6 0 0   0 3 4   0 5 2
0 0 5   0 0 0   3 0 1
0 4 0   0 0 7   0 9 0

0 0 0   8 1 0   5 0 3
9 0 0   0 0 0   0 0 7
1 0 2   0 6 3   0 0 0

0 3 0   2 0 0   0 6 0
8 0 9   0 0 0   2 0 0
2 7 0   9 8 0   0 0 4
```

The first line (`9`) defines the grid size. `0` is used for blank cells.

---

## Sample Output (Console/File)

```
A. Parameters used by the GA 
	a. Representation: Permutation
	b. Population size: 600
	c. Maximum Generation: 200
	d. Recombination Method: Partially Mapped Crossover
	e. Mutation Method: Inverse Mutation
	f. Parent Selection Method: Tournament Selection
	g. Survivor Selection Method: Elitism
	h. Probability for Recombination: 0.9
	i. Probability for Mutation: 0.05
	j. Survival Rate:0.2
B. Number of Generations Ran: 20
C. Total Running Time: 53 seconds
D. Number of Population Restarts: 2
E. Phenotype of Best Performing Individual: 
	6 9 8 1 3 4 7 5 2 
	7 2 5 6 9 8 3 4 1 
	3 4 1 5 2 7 8 9 6 
	4 6 7 8 1 9 5 2 3 
	9 8 3 4 5 2 6 1 7 
	1 5 2 7 6 3 4 8 9 
	5 3 4 2 7 1 9 6 8 
	8 1 9 3 4 6 2 7 5 
	2 7 6 9 8 5 1 3 4 
F. Puzzle Solved

```

Output is saved in a file named like `puzzle_example.in.out`.

---

## How to Run

1. Compile all files:
```bash
javac *.java
```

2. Run the solver:
```bash
java mp2_sudoku.SudokuSolverReal
```

3. Enter the name of the puzzle file (e.g., `sudoku.in`) when prompted.

---

## Educational Value

- Demonstrates real-world application of evolutionary computing
- Highlights constraint-aware GA operators
- Useful for understanding crossover, mutation, and selection schemes

---

## Project Status and Future Plans

This project was originally developed in 2016 as part of a university assignment.

It remains a working example of how evolutionary computing can be applied to constraint satisfaction problems like Sudoku. While the current implementation is procedural and monolithic, future plans include:

- Refactoring to follow the MVC (Model-View-Controller) design pattern

- Adding unit tests and modular class responsibilities

- Enhancing the fitness function for better convergence speed

- Adding a web interface or JavaFX GUI for interactive usage

- Support for saving/loading sessions and multiple difficulty levels

Community contributions are welcome if you'd like to modernize or extend the functionality!

## 📜 License

For academic use only. Developed by **April Dae Bation** as part of a university project.

---
