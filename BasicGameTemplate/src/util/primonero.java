package util;

public class primonero extends GameObject {

    public enum direction{
        left,rigth,up,down;
    }
    public direction dir;

    	
    public primonero(String textureLocation,int width,int height,Point3f centre) { 
        super(textureLocation,width,height,centre, 100);
        dir= null;
        super.attackpower= 15;
   }
   


}
