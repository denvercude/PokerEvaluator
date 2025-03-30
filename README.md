# PokerEvaluator

A Java-based command-line application that evaluates and ranks players' poker hands based on a custom rule set. The program accepts card inputs, verifies them, and determines each player's best possible hand. It then sorts the players by hand strength and displays the results.

# Features

- Validates card inputs, suits, and ranks.
- Detects duplicate cards and invalid player counts.
- Creates and stores players' hands.
- Evaluates each player's best possible hand based on custom poker hand rankings.
- Assigns a numeric hand value to each player for comparison.
- Sorts players by hand strength using Bubble Sort.
- Outputs the final rankings and best hand descriptions.

# Purpose

This project was developed as part of a school assignment to practice object-oriented programming, array manipulation, and algorithm implementation in Java. The assignment focused on applying class design, data validation, and custom rule-based logic for hand evaluation in a poker game setting.
  
# Tech Stack

- Java

# Installation and Running Locally

1. Clone the repository
   - git clone https://github.com/your-username/poker-hand-evaluator.git
2. Navigate to the project directory
   - cd poker-hand-evaluator
3. Compile the source files
   - javac Poker.java Player.java Card.java HandEvaluator.java
4. Run the Program
   - java Poker 2s 3d 4h 5c 6s 7d 8h 9c Td Jd Qh Kc As
Note: Provide valid card arguments. Each card is formatted by its rank and suit, e.g., "2s" for Two of Spades, "Ah" for Ace of Hearts.

# Usage

The program accepts card inputs through the command line, with the following rules:
1. The total number of cards must be divisible by five.
2. There must be at least two and at most ten players.
3. No duplicate cards are allowed.
4. Valid ranks: 2-10, J, Q, K, A
5. Valid suits: s (Spades), c (Clubs), h (Hearts), d (Diamonds)

# Learning Outcomes
- Object-oriented programming principles in Java.
- Input validation techniques.
- Algorithm implementation (e.g., Bubble Sort).
- Card combination logic and rule-based evaluation systems.

# Future Improvements
- Future Improvements
- Optimize sorting algorithm for better performance.
- Add graphical user interface for better visualization.
- Implement additional custom poker hand rules.
- Add unit tests for all validation and evaluation methods.
