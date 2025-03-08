import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
   jin giacomo 24216191
 */ 

//Singeton pattern
public class Controller implements KeyListener {
        
	   private static boolean KeyAPressed= false;
	   private static boolean KeySPressed= false;
	   private static boolean KeyDPressed= false;
	   private static boolean KeyWPressed= false;
	   private static boolean KeySpacePressed= false;
	   private static boolean KeyFPressed= false;


	   private static boolean Key8Pressed = false;
	   private static boolean Key4Pressed = false;
	   private static boolean Key5Pressed = false;
	   private static boolean Key6Pressed = false;
	   private static boolean Key0Pressed = false;
   
    

	   
	   
	   private static final Controller instance = new Controller();
	   
	 public Controller() { 
	}
	 
	 public static Controller getInstance(){
	        return instance;
	    }
	   
	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) { 
		 
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
			case 'a':setKeyAPressed(true);break;  
			case 's':setKeySPressed(true);break;
			case 'w':setKeyWPressed(true);break;
			case 'd':setKeyDPressed(true);break;
			case 'f':setKeyFPressed(true);break;
			case ' ':setKeySpacePressed(true);break;   
			case '8': Key8Pressed = true; break;  
            case '4': Key4Pressed = true; break;
            case '5': Key5Pressed = true; break;
            case '6': Key6Pressed = true; break;
            case '0': Key0Pressed = true; break;  
		    default:
		    	//System.out.println("Controller test:  Unknown key pressed");
		        break;
		}  
		
	 // You can implement to keep moving while pressing the key here . 
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
			case 'a':setKeyAPressed(false);break;  
			case 's':setKeySPressed(false);break;
			case 'w':setKeyWPressed(false);break;
			case 'd':setKeyDPressed(false);break;
			case 'f':setKeyFPressed(false);break;
			case ' ':setKeySpacePressed(false);break;
			case '8': Key8Pressed = false; break;  
            case '4': Key4Pressed = false; break;
            case '5': Key5Pressed = false; break;
            case '6': Key6Pressed = false; break;
            case '0': Key0Pressed = false; break; 
		    default:
		    	//System.out.println("Controller test:  Unknown key pressed");
		        break;
		}  
		 //upper case 
	
	}


	public boolean isKeyAPressed() {
		return KeyAPressed;
	}
	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}
	public boolean isKeySPressed() {
		return KeySPressed;
	}
	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}
	public boolean isKeyDPressed() {
		return KeyDPressed;
	}
	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}
	public boolean isKeyWPressed() {
		return KeyWPressed;
	}
	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}



	public boolean isKeyFPressed() {
		return KeyFPressed;
	}
	public void setKeyFPressed(boolean keyFPressed) {
		KeyFPressed = keyFPressed;
	}
	
	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}
	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	} 

	
	public boolean isKey8Pressed() {
		return Key8Pressed;
	}
	public void setKey8Pressed(boolean key8Pressed) {
		Key8Pressed = key8Pressed;
	}
	public boolean isKey4Pressed() {
		return Key4Pressed;
	}
	public void setKey4Pressed(boolean key4Pressed) {
		Key4Pressed = key4Pressed;
	}
	public boolean isKey5Pressed() {
		return Key5Pressed;
	}
	public void setKey5Pressed(boolean key5Pressed) {
		Key5Pressed = key5Pressed;
	}
	public boolean isKey6Pressed() {
		return Key6Pressed;
	}
	public void setKey6Pressed(boolean key6Pressed) {
		Key6Pressed = key6Pressed;
	}
	public boolean isKey0Pressed() {
		return Key0Pressed;
	}
	public void setKey0Pressed(boolean key0Pressed) {
		Key0Pressed = key0Pressed;
	} 
	
	
	 
}
