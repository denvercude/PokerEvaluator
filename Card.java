public class Card {
    private String rank;
    private char suit;
    private String color;

    public Card(String rank, char suit){
        this.rank = rank;
        this.suit = suit;
    }

    //Accessors

    public String getRank(){
        return rank;
    }

    public char getSuit(){
        return suit;
    }

    public String getColor(){
        return color;
    }
}
