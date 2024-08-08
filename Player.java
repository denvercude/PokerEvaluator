public class Player {
    private Card[] hand;
    private String bestHand;

    private int handValue;

    private int playerNumber;

    //Constructor
    public Player(Card[] hand, int number){
        this.hand = hand;
        this.playerNumber = number;
    }

    //Accessors
    public int getPlayerNumber(){
        return playerNumber;
    }
    public Card[] getHand(){
        return hand;
    }
    public int getHandValue(){
        return handValue;
    }
    public String getBestHand(){
        return bestHand;
    }

    //Mutators
    public void setBestHand(String bestHand){
        this.bestHand = bestHand;
    }

    public void setHandValue(int value){
        handValue = value;
    }
}
