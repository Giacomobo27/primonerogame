package util;

public class pula extends GameObject {


    public boolean danger;

    	
    public pula(String textureLocation,int width,int height,Point3f centre) { 
        super(textureLocation,width,height,centre,50);
        danger= false;
        super.attackpower= 5;
   }
   


}
