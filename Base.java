public class Base{
    
    private boolean hasRunner;
    private int[] isNear;
    private int basePos;
    
    public Base (boolean hR, int bP, int[] near){
        hasRunner = hR;
        basePos = bP;
        isNear = near;
    }

    public boolean getHasRunner(){
        return hasRunner;
    }
    
    public int[] getIsNear(){
        return isNear;
    }

    public int getBasePos(){
        return basePos;
    }
    public void setHasRunner(boolean b){
        hasRunner = b;
    }

}