# PokerEvaluator

This repository contains a project for an advanced data structures class. The project involved writing a program that evaluates poker hands based on command line inputs. 

## Project Description

The program accepts command line arguments in the form of card identifiers (e.g., "Kd Ah 7c...") where:
- Kd stands for King of Diamonds
- Ah stands for Ace of Hearts
- 7c stands for Seven of Clubs
- etc.
  
The program processes the cards to:
1. Determine which cards belong to each player and the community pile based on the order they are entered.
2. Evaluate all possible combinations of 2 and 3 cards from each player's hand to build a 5-card hand with the community cards.
3. Assign each player the highest possible ranked hand. For example: A player may be able to build "homosapiens," where their entire hand consists of face cards.
4. Output a ranked list of players from best to worst hand.
