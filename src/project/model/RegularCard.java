package project.model;

public class RegularCard extends Card{
    private int hops;

    public RegularCard(int hops){
        this.hops = hops;
        switch (hops){
            case 1: setImage(Images.regularCardOne); break;
            case 2: setImage(Images.regularCardTwo); break;
            case 3: setImage(Images.regularCardThree); break;
            case 4: setImage(Images.regularCardFour); break;
        }
    }

    public int getHops(){ return hops; }
}
