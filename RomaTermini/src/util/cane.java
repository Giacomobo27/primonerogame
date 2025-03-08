package util;

public class cane extends GameObject {

    public enum direction2{
        left,rigth,up,down;
    }
    public direction2 dir;
    public int woof = 0; // when it gets to 100, buff player

    	
    public cane(String textureLocation,int width,int height,Point3f centre) { 
        super(textureLocation,width,height,centre, 100);
        dir= null;
        super.attackpower= 0;
   }
   


}
