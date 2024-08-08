import java.util.Objects;

public class HandEvaluator {
    private static String bestHand = "Non-Rainbow";
    private static int bestHandValue = 1;

    private static final String [] handTypes = {"Non-Rainbow", "Rainbow", "Swingers", "Monochromatic",
            "3 pair", "Monarchy", "Even", "Odd", "Flush", "Triplets", "Overfull House", "Homosapiens",
            "Kingdom", "Orgy", "Politics", "Dinner Party"};
    private static final int[] handStrengths = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    public static void updateBestHand(String potentialBestHand){
        for(int i = 0; i < handTypes.length; i++){
            if(handTypes[i].equals(potentialBestHand) && handStrengths[i] > bestHandValue){
                bestHand = potentialBestHand;
                bestHandValue = handStrengths[i];
            }
        }
    }

    public static String getBestHand(){
        return bestHand;
    }

    public static void setBestHand(String hand, int value){
        bestHand = hand;
        bestHandValue = value;
    }

    public static int getHandValue(String playerBestHand){
        for(int i = 0; i < handTypes.length; i++){
            if(Objects.equals(playerBestHand, handTypes[i])){
                return handStrengths[i];
            }
        }
        return -1;
    }

}
