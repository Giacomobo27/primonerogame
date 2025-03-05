import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javafx.scene.text.Font;
import util.UnitTests;

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
 */ 



public class MainWindow {
	 private static  JFrame frame = new JFrame("Roma Termini primo nero adventure");   // Change to the name of your game 
	 private static   Model gameworld= new Model();
	 private static   Viewer canvas = new  Viewer( gameworld);
	 private KeyListener Controller =new Controller()  ; 
	 private static   int TargetFPS = 100;
	 private static boolean startGame= false; 
	 private   JLabel BackgroundImageForStartMenu ;

	public MainWindow() {   // initial setup
	        frame.setSize(1000, 1000);  // you can customise this later and adapt it to change on size.  
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
	        frame.setLayout(null);
	        frame.add(canvas);    // adding JPanel viewer canvas to JFrame frame
	        canvas.setBounds(0, 0, 1000, 1000); 
			   canvas.setBackground(new Color(255,255,255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen 
		      canvas.setVisible(false);   // this will become visible after you press the key. 
		          

        // New: Set Viewer reference in Model AFTER creation
        gameworld.setViewer(canvas);
		       
	        JButton startMenuButton = new JButton("Start Game");  // start button 
	        startMenuButton.addActionListener(new ActionListener()
	           { 
				@Override
				public void actionPerformed(ActionEvent e) { 
					startMenuButton.setVisible(false);
					BackgroundImageForStartMenu.setVisible(false); 
					canvas.setVisible(true); 
					canvas.addKeyListener(Controller);    //adding the controller to the Canvas  
	            canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
					startGame=true;
				}});  
	        startMenuButton.setBounds(400, 500, 200, 40); 
	        
	        //loading background image 
	        File BackroundToLoad = new File("res/t.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
			try {
				 
				 BufferedImage myPicture = ImageIO.read(BackroundToLoad);
				 BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
				 BackgroundImageForStartMenu.setBounds(0, 0, 1000, 1000);
				frame.add(BackgroundImageForStartMenu); 
			}  catch (IOException e) { 
				e.printStackTrace();
			}   
			 
	         frame.add(startMenuButton);  
	       frame.setVisible(true);   
	}

	public static void main(String[] args) {
		MainWindow hello = new MainWindow();  //sets up environment 
		while(true)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
		{ 
			//swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it 
			
			int TimeBetweenFrames =  1000 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames; 
			
			//wait till next time step 
		 while (FrameCheck > System.currentTimeMillis()){} 
			
			
			if(startGame){
				 gameloop();
				 }
			
			//UNIT test to see if framerate matches 
		 UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS); 
			  
		}
		
		
	} 
	//Basic Model-View-Controller pattern 
	private static void gameloop() { 
		// GAMELOOP  
		
		// controller input  will happen on its own thread 
		// So no need to call it explicitly 
		if (canvas.gameState == Viewer.GameState.OVERWORLD) { // hope its not buggy
		// model update   
		gameworld.gamelogic();


		
		if (gameworld.isBattleTriggered()) {
            startBattle(); // Start battle if triggered

        }
		}

		if (canvas.gameState == Viewer.GameState.BATTLE) {
			// Run battle logic
			gameworld.battlelogic();  // to implement yet
			if (gameworld.battleIsOver()) { 
				endBattle();
			}
		}
	
		// view update 
		  canvas.updateview(); 
		
		// Both these calls could be setup as  a thread but we want to simplify the game logic for you.  
		//score update  
		 frame.setTitle("Score =  "+ gameworld.getScore()); 
		
		 
	}
	public static void pauseGame() {
        startGame = false;
    }

    // Method to resume the game
    public static void resumeGame() {
        startGame = true;
    }

	public static Viewer getCanvas() {
		return canvas;
	}

	public static void startBattle() {
		// Save player position before transitioning
		gameworld.savedPlayerX = gameworld.getPlayer().getCentre().getX();
		gameworld.savedPlayerY = gameworld.getPlayer().getCentre().getY();

		battleTransition();

		System.out.println("Entering Battle Mode...");

		gameworld.playerTurn = true;  // Ensure turn starts with player
		canvas.updateButtonVisibility();  // Update UI
	
	}
	
	public static void endBattle() {

    gameworld.playerTurn = false; // End player's turn
    canvas.updateBattleLog("BATTLE ENDED");
    

		// Restore player position after battle
		gameworld.getPlayer().getCentre().setX(gameworld.savedPlayerX);
		gameworld.getPlayer().getCentre().setY(gameworld.savedPlayerY);

		for (int i = 0; i <= 255; i += 15) {  
			canvas.setFadeOpacity(i);
			canvas.repaint();
			try {
				Thread.sleep(30); // Adjust timing for smooth transition
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i==150){
				gameworld.Animestill=null;
				gameworld.Animestillenemy=null;
				canvas.animation=0;
			}
		}
		// Switch back to overworld mode
		canvas.battleLog.setVisible(false);
		gameworld.playerTurn = false;

		if (gameworld.getPlayer().isDefeated()) {
			showGameOverScreen(); // Show Game Over screen instead of overworld
		}else{
		canvas.gameState=Viewer.GameState.OVERWORLD;
		canvas.repaint();
		}
		// if gameover goto gameover page
		

		for (int i = 255; i >= 0; i -= 15) {  
			canvas.setFadeOpacity(i);
			canvas.repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		 //redundant ?
		 /* 
		canvas.attackButton.setVisible(false);
	    canvas.begButton.setVisible(false);
		canvas.stealButton.setVisible(false);
		canvas.seduceButton.setVisible(false);
  */
		
		System.out.println("Exiting Battle Mode...");
// Restore keyboard focus, they got stealen by buttons inside battle scene
		canvas.requestFocusInWindow();  // Give focus back to game
        canvas.repaint();
	}

	public static void battleTransition() {
		for (int i = 0; i <= 255; i += 15) {  
			canvas.setFadeOpacity(i);
			canvas.repaint();
			try {
				Thread.sleep(30); // Adjust timing for smooth transition
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		canvas.gameState=Viewer.GameState.BATTLE;
		
		canvas.attackButton.setVisible(true);
	    canvas.begButton.setVisible(true);
		
		canvas.stealButton.setVisible(true);
		canvas.seduceButton.setVisible(true);

		canvas.battleLog.setVisible(true);
        canvas.repaint();

		for (int i = 255; i >= 0; i -= 15) {  
			canvas.setFadeOpacity(i);
			canvas.repaint();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		canvas.repaint();

	}


	private static void showGameOverScreen() {
		// Stop the game loop
		startGame = false;
	
		// Create a Game Over message
		JLabel gameOverLabel = new JLabel("Game Over! You Lost!", SwingConstants.CENTER);
		gameOverLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
		gameOverLabel.setForeground(Color.RED);
		gameOverLabel.setBounds(250, 300, 500, 100);
	    

		// Restart Button
		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(400, 500, 200, 50);
		restartButton.addActionListener(e -> restartGame());
	
		// Exit Button
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(400, 570, 200, 50);
		exitButton.addActionListener(e -> System.exit(0));
	
		// Clear the screen and add new elements
		frame.getContentPane().removeAll();
		frame.add(gameOverLabel);
		frame.add(restartButton);
		frame.add(exitButton);
		
		frame.revalidate();
		frame.repaint();
	}

	private static void restartGame() {
		System.out.println("Restarting game...");
	
		// Reset game variables
		gameworld = new Model(); // Create a new game world
		canvas = new Viewer(gameworld); // Create a new viewer
		frame.getContentPane().removeAll();
		frame.add(canvas);
		
		// Reset the state
		canvas.gameState = Viewer.GameState.OVERWORLD;
		startGame = true;
	
		frame.revalidate();
		frame.repaint();
	}


	

}
