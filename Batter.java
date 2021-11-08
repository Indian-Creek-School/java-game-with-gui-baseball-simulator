public class Batter {
    private int strikes = 0;
    private int hitPos = 0;

    public int getHitPos(){
        return hitPos;
    }

    public int getStrikes(){
        return strikes;
    }

    public void newBatter(){
        strikes = 0;
    }

    public int swing(boolean hit){
        if (hit == true){
            hitPos = (int) (Math.random()*9)+1;
        } else {
            strikes++;
        }
        return hitPos;
    }
}