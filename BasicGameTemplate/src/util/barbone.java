package util;

public class barbone extends GameObject {


    public boolean danger;
    	
    public barbone(String textureLocation,int width,int height,Point3f centre) { 
        super(textureLocation,width,height,centre,30);
        danger= false;
        super.attackpower= 3;
   }
   


}
