import java.util.Objects;

public class Poker {
    public static void main(String[] args) {
        //Check args for error cases
        if (!checkCardCount(args)) {
            throw new IllegalArgumentException("Invalid input: Invalid amount of cards.");
        }
        if (!checkPlayerCount(args)) {
            throw new IllegalArgumentException("Invalid input: Invalid player count.");
        }
        if (!checkRankAndSuits(args)) {
            throw new IllegalArgumentException("Invalid input: Rank or Suit does not exist.");
        }
        if (!checkDuplicateCards(args)) {
            throw new IllegalArgumentException("Invalid input: Duplicate Cards.");
        }
        //Create card array.
        Card[] cardArray = new Card[args.length];

        for (int i = 0; i < args.length; i++) {
            String rank = args[i].length() == 3 ? args[i].substring(0, 2) : args[i].substring(0, 1);
            char suit = args[i].charAt(args[i].length() - 1);
            Card newCard = new Card(rank, suit);
            cardArray[i] = newCard;
        }

        //Create player array.
        int numberOfPlayers = cardArray.length / 5;
        int playerCount = 0;
        Player[] playerArray = new Player[numberOfPlayers];

        int cardCount = 0;

        for (int i = 0; i < cardArray.length; i++) {
            if (cardCount == 4) {
                Card[] hand = new Card[5];
                int k = 0;
                for (int j = i - 4; j <= i; j++) {
                    hand[k] = cardArray[j];
                    k++;
                }

                Player newPlayer = new Player(hand, playerCount);
                playerArray[playerCount] = newPlayer;
                playerCount++;
                cardCount = 0;
            } else {
                cardCount++;
            }
        }

        //Find the best hand for each player

        for (int i = 1; i < playerArray.length; i++) {
            String bestHand = getBestHand(playerArray[i].getHand(), playerArray[0].getHand());
            playerArray[i].setBestHand(bestHand);
            HandEvaluator.setBestHand("Non-Rainbow", 1);
        }

        //Assign hand value for each player
        for (Player player : playerArray) {
            player.setHandValue(HandEvaluator.getHandValue(player.getBestHand()));
        }
        //Bubble Sort player array
        for (int i = 0; i < playerArray.length - 1; i++) {
            boolean swap = false;
            for (int j = 0; j < playerArray.length - i -1; j++) {
                if(playerArray[j].getHandValue() < playerArray[j + 1].getHandValue()) {
                    Player temp = playerArray[j];
                    playerArray[j] = playerArray[j + 1];
                    playerArray[j + 1] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
        System.out.println();
        int endRank = 1;
        for(int i = 0; i < playerArray.length - 1; i++){
            if(i > 0 && playerArray[i].getHandValue() == playerArray[i - 1].getHandValue()){
                System.out.println(endRank + ". Player " + playerArray[i].getPlayerNumber() + " - "
                        + playerArray[i].getBestHand());
            }else{
                if(i > 0){
                    endRank++;
                }
                System.out.println(endRank + ". Player " + playerArray[i].getPlayerNumber()+ " - "
                        + playerArray[i].getBestHand());
            }
        }
    }

    public static boolean checkRankAndSuits(String[] args){
        // return value
        boolean validArguments = true;

        // Define valid ranks and suits
        String[] validRanks = new String[13];
        for(int i = 0; i < 8; i++){
            validRanks[i] = String.valueOf(i + 2);
        }
        validRanks[8] = "T";
        validRanks[9] = "K";
        validRanks[10] = "Q";
        validRanks[11] = "J";
        validRanks[12] = "A";

        char[] validSuits = new char[] {'h', 'd', 's', 'c'};

        // Check string length
        for (String arg : args) {
            if (arg.length() != 2) {
                validArguments = false;
                break;
            }
        }

        //Check each rank and suit
        for(String arg : args){
            String rank = arg.substring(0, 1);
            char suit = arg.charAt(arg.length() - 1);

            boolean rankFound = false;
            boolean suitFound = false;

            for(String validRank : validRanks){
                if(rank.equals(validRank)){
                    rankFound = true;
                    break;
                }
            }
            for(char validSuit : validSuits){
                if(suit == validSuit){
                    suitFound = true;
                    break;
                }
            }
            if(!rankFound || !suitFound){
                validArguments = false;
                break;
            }
        }
        return validArguments;
    }

    public static boolean checkCardCount(String[] args){
        if(args == null){
            return false;
        }

        int cardCount = args.length;

        return (cardCount % 5) == 0;
    }

    public static boolean checkPlayerCount(String[] args){
        boolean validPlayerCount = true;

        // Divide total amount of cards by 5
        // Subtract 1 for community card hand
        int playerCount = (args.length / 5) - 1;

        // Setting max players to 10, check that playerCount does not exceed
        if(playerCount < 2 || playerCount > 10){
            validPlayerCount = false;
        }
        return validPlayerCount;
    }

    public static boolean checkDuplicateCards(String[] args){
        int j = 0;
        String[] seenCard = new String[args.length];
        for(String arg : args){
            for(int i = 0; i < j; i++){
                if(arg.equals(seenCard[i])){
                    return false;
                }
            }
            seenCard[j] = arg;
            j++;
        }
        return true;
    }

    public static String getBestHand(Card[] playerHand, Card[] communityHand){

        //Check for a ranked hand in all possible combinations of 2 cards in the player's hand.
        for(int i = 0; i < playerHand.length - 1; i++){
            for(int j = i + 1; j < playerHand.length; j++){
                Card[] twoCardCombo = new Card[]{playerHand[i], playerHand[j]};
                if(checkDinnerParty(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Dinner Party");
                }
                if(checkPolitics(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Politics");
                }
                if(checkOrgy(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Orgy");
                }
                if(checkKingdom(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Kingdom");
                }
                if(checkHomosapiens(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Homosapiens");
                }
                if(checkOverfull(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Overfull House");
                }
                if(checkTriplets(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Triplets");
                }
                if(checkFlush(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Flush");
                }
                if (checkOdd(twoCardCombo, communityHand)) {
                    HandEvaluator.updateBestHand("Odd");
                }
                if (checkEven(twoCardCombo, communityHand)) {
                    HandEvaluator.updateBestHand("Even");
                }
                if (checkMonarchy(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Monarchy");
                }
                if (check3Pair(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("3 Pair");
                }
                if (checkMonochromatic(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Monochromatic");
                }
                if (checkSwingers(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Swingers");
                }
                if (checkRainbow(twoCardCombo, communityHand)){
                    HandEvaluator.updateBestHand("Rainbow");
                }
            }
        }

        //Check for a ranked hand in all possible combinations of 3 cards in the player's hand
        for(int i = 0; i < playerHand.length - 2; i++){
            for(int j = i + 1; j < playerHand.length - 1; j++){
                for(int k = j + 1; k < playerHand.length; k++){
                    Card[] threeCardCombo = new Card[]{playerHand[i], playerHand[j], playerHand[k]};
                    if(checkDinnerParty(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Dinner Party");
                    }
                    if(checkPolitics(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Politics");
                    }
                    if(checkOrgy(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Orgy");
                    }
                    if(checkKingdom(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Kingdom");
                    }
                    if(checkHomosapiens(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Homosapiens");
                    }
                    if(checkOverfull(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Overfull House");
                    }
                    if(checkTriplets(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Triplets");
                    }
                    if(checkFlush(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Flush");
                    }
                    if (checkOdd(threeCardCombo, communityHand)) {
                        HandEvaluator.updateBestHand("Odd");
                    }
                    if (checkEven(threeCardCombo, communityHand)) {
                        HandEvaluator.updateBestHand("Even");
                        System.out.println(playerHand[0].getRank() + " set to Even.");
                    }
                    if (checkMonarchy(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Monarchy");
                    }
                    if (check3Pair(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("3 Pair");
                    }
                    if (checkMonochromatic(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Monochromatic");
                    }
                    if (checkSwingers(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Swingers");
                    }
                    if (checkRainbow(threeCardCombo, communityHand)){
                        HandEvaluator.updateBestHand("Rainbow");
                    }
                }
            }
        }
        return HandEvaluator.getBestHand();
    }

    public static boolean checkDinnerParty(Card[] playerCombo, Card[] communityHand){
        //Counters for kings and queens
        int kingCount = 0;
        int queenCount = 0;

        //Count all kings and queens in playerCombo
        for(Card card : playerCombo){
            if(Objects.equals(card.getRank(), "K")){
                kingCount++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                kingCount++;
            }
        }

        //Count all kings and queens in communityHand
        for(Card card : communityHand){
            if(Objects.equals(card.getRank(), "K")){
                kingCount++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                kingCount++;
            }
        }

        return (kingCount + queenCount) >= 6;
    }

    public static boolean checkPolitics(Card[] playerCombo, Card[] communityHand){
        boolean hasMonarchy = false;

        int[] kingSuits = new int[4];
        int[] queenSuits = new int[4];
        int[] jackSuits = new int[4];

        //Get all face card suits from playerCombo
        for(Card card : playerCombo){
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "J")){
                jackSuits[getSuitIndex(card.getSuit())]++;
            }
        }

        //Get all face card suits from communityCards
        for(Card card : communityHand){
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "J")){
                jackSuits[getSuitIndex(card.getSuit())]++;
            }
        }

        //Check if there are 2 monarchies
        int monarchyCount = 0;
        for(int i = 0; i < 4; i++){
            if(kingSuits[i] > 0 && queenSuits[i] > 0 && jackSuits[i] > 0){
                monarchyCount++;
            }
        }

        return monarchyCount >= 2;
    }
    public static boolean checkOrgy(Card[] playerCombo, Card[] communityHand){
        int totalJacks = 0;
        int totalQueens = 0;

        //count all jacks and queens in playerCombo
        for(Card card : playerCombo){
            if(Objects.equals(card.getRank(), "J")){
                totalJacks++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                totalQueens++;
            }
        }

        for(Card card : communityHand){
            if(Objects.equals(card.getRank(), "J")){
                totalJacks++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                totalQueens++;
            }
        }

        return (totalJacks + totalQueens) >= 6;
    }
    public static boolean checkKingdom(Card[] playerCombo, Card[] communityHand){
        //3 counters for face suits and non face suits
        int[] kingSuits = new int[4];
        int[] queenSuits = new int[4];
        int[] jackSuits = new int[4];
        int[] nonFaceSuits = new int[4];

        //Count all suits for playerCombo
        for(Card card : playerCombo){
            switch(card.getRank()){
                case "K" -> {
                    kingSuits[getSuitIndex(card.getSuit())]++;
                }
                case "Q" -> {
                    queenSuits[getSuitIndex(card.getSuit())]++;
                }
                case "J" -> {
                    jackSuits[getSuitIndex(card.getSuit())]++;
                }
                default -> {
                    nonFaceSuits[getSuitIndex(card.getSuit())]++;
                }
            }
        }

        //Count all suits for communityHand
        for(Card card : communityHand){
            switch(card.getRank()){
                case "K" -> {
                    kingSuits[getSuitIndex(card.getSuit())]++;
                }
                case "Q" -> {
                    queenSuits[getSuitIndex(card.getSuit())]++;
                }
                case "J" -> {
                    jackSuits[getSuitIndex(card.getSuit())]++;
                }
                default -> {
                    nonFaceSuits[getSuitIndex(card.getSuit())]++;
                }
            }
        }

        //Check if there's a Kingdom
        for(int i = 0; i < 4; i++){
            if(kingSuits[i] > 0 && queenSuits[i] > 0 && jackSuits[i] > 0 && nonFaceSuits[i] >= 3){
                return true;
            }
        }

        return false;
    }

    public static boolean checkHomosapiens(Card[] playerCombo, Card[] communityHand){
        //2 counters for face cards in player and community hands
        int playerFaceCards = 0;
        int communityFaceCards = 0;

        //Count face cards in playerCombo
        for(Card card : playerCombo){
            switch(card.getRank()){
                case "K", "Q", "J" -> {playerFaceCards++;}
                default -> {continue;}
            }
        }

        //Count face cards in communityHand
        for(Card card : communityHand){
            switch(card.getRank()){
                case "K", "Q", "J" -> {communityFaceCards++;}
                default -> {continue;}
            }
        }

        return (playerFaceCards + communityFaceCards) >= 6;
    }
    public static boolean checkOverfull(Card[] playerCombo, Card[] communityHand){
        //Counter array for ranks
        int[] rankCounts = new int[13];

        //Count each rank in playerCombo
        for(Card card : playerCombo){
            rankCounts[getRankIndex(card.getRank())]++;
        }

        //Count each rank in communityHand
        for(Card card : communityHand){
            rankCounts[getRankIndex(card.getRank())]++;
        }

        //Check if there exists both a four of a kind and a pair in the available ranks.
        boolean fourOfKind = false;
        boolean pair = false;
        for(int rank : rankCounts){
            if(rank == 4){
                fourOfKind = true;
            }
            if(rank >= 2 && rank < 4){
                pair = true;
            }
        }
        return (fourOfKind && pair);
    }
    public static boolean checkTriplets(Card[] playerCombo, Card[] communityHand){
        //Counter array for ranks
        int[] rankCounts = new int[13];

        //Count each rank in playerCombo
        for(Card card : playerCombo){
            rankCounts[getRankIndex(card.getRank())]++;
        }

        //Count each rank in communityHand
        for(Card card : communityHand){
            rankCounts[getRankIndex(card.getRank())]++;
        }

        //Check for 2 ranks greater than 3
        int numberOfTriplets = 0;
        for(int rank : rankCounts){
            if(rank >= 3){
                numberOfTriplets++;
            }
        }

        return numberOfTriplets >= 2;
    }
    public static boolean checkFlush(Card[] playerCombo, Card[] communityHand){
        //Counter array for suits
        int[] suitCounts = new int[4]; //s, c, h, d

        //Count each suit in playerCombo
        for(Card card : playerCombo){
            suitCounts[getSuitIndex(card.getSuit())]++;
        }

        //Count each suit in communityHand

        for(Card card : communityHand){
            suitCounts[getSuitIndex(card.getSuit())]++;
        }

        for (int suitCount : suitCounts) {
            if (suitCount >= 6) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkOdd(Card[] playerCombo, Card[] communityHand){
        //Counter for even numbered cards
        int oddCounter = 0;

        //Count even cards in playerCombo
        for(Card card : playerCombo){
            if(isOdd(card)){
                oddCounter++;
            }
        }

        //Count even cards in communityHand
        for(Card card : communityHand){
            if(isOdd(card)){
                oddCounter++;
            }
        }
        return oddCounter >= 6;
    }

    public static boolean isOdd(Card card){
        switch(card.getRank()){
            case "A", "3", "5", "7", "9" -> {
                return true;
            }
            default -> {return false;}
        }
    }
    public static boolean checkEven(Card[] playerCombo, Card[] communityHand){
        //Counter for even numbered cards
        int evenCounter = 0;

        //Count even cards in playerCombo
        for(Card card : playerCombo){
            if(isEven(card)){
                evenCounter++;
            }
        }

        //Count even cards in communityHand
        for(Card card : communityHand){
            if(isEven(card)){
                evenCounter++;
            }
        }
        return evenCounter >= 6;
    }

    public static boolean isEven(Card card){
        switch(card.getRank()){
            case "2", "4", "6", "8", "10" -> {
                return true;
            }
            default -> {return false;}
        }
    }
    public static boolean checkMonarchy(Card[] playerCombo, Card[] communityHand){
        boolean hasMonarchy = false;

        int[] kingSuits = new int[4];
        int[] queenSuits = new int[4];
        int[] jackSuits = new int[4];

        //Get all face card suits from playerCombo
        for(Card card : playerCombo){
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "J")){
                jackSuits[getSuitIndex(card.getSuit())]++;
            }
        }

        //Get all face card suits from communityCards
        for(Card card : communityHand){
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[getSuitIndex(card.getSuit())]++;
            }
            if(Objects.equals(card.getRank(), "J")){
                jackSuits[getSuitIndex(card.getSuit())]++;
            }
        }

        //Check if there is a monarchy
        for(int i = 0; i < 4; i++){
            if(kingSuits[i] > 0 && queenSuits[i] > 0 && jackSuits[i] > 0){
                hasMonarchy = true;
                break;
            }
        }

        //Check that there are at least 3 non-face cards left to build the 6 card hand
        int nonFaceCount = 0;

        for(Card card : playerCombo){
            String currentRank = card.getRank();
            switch (currentRank) {
                case "K", "Q", "J" -> {
                    continue;
                }
                default -> nonFaceCount++;
            }
        }

        for(Card card : communityHand){
            String currentRank = card.getRank();
            switch (currentRank) {
                case "K", "Q", "J" -> {
                    continue;
                }
                default -> nonFaceCount++;
            }
        }

        if(nonFaceCount < 3){
            hasMonarchy = false;
        }

        return hasMonarchy;
    }
    public static boolean check3Pair(Card[] playerCombo, Card[] communityHand){
        boolean has3Pair = false;

        //An array to track counts of rank occurrences
        int[] rankCounts = new int[13];

        //Count each rank occurrences in playerCombo
        for(Card card : playerCombo){
            int rankIndex = getRankIndex(card.getRank());
            rankCounts[rankIndex]++;
        }

        //Count each rank occurrence in communityHand
        for(Card card : communityHand){
            int rankIndex = getRankIndex(card.getRank());
            rankCounts[rankIndex]++;
        }

        int pairCount = 0;
        for(int rankCount : rankCounts){
            if(rankCount >= 2){
                pairCount++;
            }
        }

        if(pairCount >= 3){
            has3Pair = true;
        }

        return has3Pair;
    }
    public static boolean checkMonochromatic(Card[] playerCombo, Card[] communityHand){
        boolean isMonochromatic = false;
        int redCount = 0;
        int blackCount = 0;

        //Count the amount of red and black cards in playerCombo
        for(Card card : playerCombo){
            if(card.getSuit() == 'h' || card.getSuit() == 'd'){
                redCount++;
            }else{
                blackCount++;
            }
        }
        //Count the amount of red and black cards in communityHand
        for(Card card : communityHand){
            if(card.getSuit() == 'h' || card.getSuit() == 'd'){
                redCount++;
            }else{
                blackCount++;
            }
        }
        if(redCount >= 6 || blackCount >= 6){
            isMonochromatic = true;
        }
        return isMonochromatic;
    }
    public static boolean checkSwingers(Card[] playerCombo, Card[] communityHand){
        boolean hasSwingers = false;

        //Arrays to keep track of the number of suits for both King and Queen
        int[] kingSuits = new int[4];
        int[] queenSuits = new int[4];

        //Find all King and Queen suits in player's hand
        for(Card card : playerCombo){
            int suitIndex = getSuitIndex(card.getSuit());
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[suitIndex]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[suitIndex]++;
            }
        }

        //Find all King and Queen suits in community cards
        for(Card card : communityHand){
            int suitIndex = getSuitIndex(card.getSuit());
            if(Objects.equals(card.getRank(), "K")){
                kingSuits[suitIndex]++;
            }
            if(Objects.equals(card.getRank(), "Q")){
                queenSuits[suitIndex]++;
            }
        }

        //Check once for each suit how many pairs can be made from kingSuits and queenSuits
        int pairCount = 0;
        for(int i = 0; i < 4; i++){
            int suitPairs = 0;
            while(kingSuits[i] > 0 && queenSuits[i] > 0){
                suitPairs++;
                kingSuits[i]--;
                queenSuits[i]--;
            }
            pairCount += suitPairs;
        }

        if(pairCount >= 2){
            hasSwingers = true;
        }
        return hasSwingers;
    }
    public static boolean checkRainbow(Card[] playerCombo, Card[] communityHand){
        //Return boolean:
        boolean isRainbow = true;

        //Create an array to store counts of suits: 0 - Spades, 1 - Clubs, 2 - Hearts, 3 - Diamonds
        int[] suitCount = new int[]{0, 0, 0, 0};

        //Count all suits in player's hand
        for(Card card : playerCombo){
            suitCount[getSuitIndex(card.getSuit())]++;
        }

        //Count all suits in community hand
        for(Card card : communityHand){
            suitCount[getSuitIndex(card.getSuit())]++;
        }

        //Check if all suits are present, if not, set isRainbow to false
        for (int j : suitCount) {
            if (j == 0) {
                isRainbow = false;
                break;
            }
        }
        return isRainbow;
    }

    private static int getRankIndex(String rank){
        return switch(rank){
            case "A" -> 0;
            case "2" -> 1;
            case "3" -> 2;
            case "4" -> 3;
            case "5" -> 4;
            case "6" -> 5;
            case "7" -> 6;
            case "8" -> 7;
            case "9" -> 8;
            case "T" -> 9;
            case "J" -> 10;
            case "Q" -> 11;
            case "K" -> 12;
            default -> -1;
        };
    }
    private static int getSuitIndex(char suit){
        return switch (suit) {
            case 's' -> 0;
            case 'c' -> 1;
            case 'h' -> 2;
            case 'd' -> 3;
            default -> -1;
        };
    }
}
