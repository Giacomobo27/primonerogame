import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//import javafx.scene.text.Font;
import util.UnitTests;
import util.barbone;
import util.pula;
import util.PlaySoundDemo;

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

public class MainWindow {
	static {
        System.setProperty("sun.java2d.dpiaware", "true");
        System.setProperty("sun.java2d.uiScale", "1.0");
    }
	 private static  JFrame frame = new JFrame("Roma Termini primo nero adventure");   // Change to the name of your game 
	 private static   Model gameworld= new Model();
	 private static   Viewer canvas = new  Viewer( gameworld);
	 private KeyListener Controller =new Controller()  ; 
	 private static   int TargetFPS = 100;
	 private static boolean startGame= false; 
	 private   JLabel BackgroundImageForStartMenu ;
	 private static JPanel blackOverlay;
	 private static JLabel label1;
	 private static JButton difficultButton;
	 private static float opacity = 1.0f; 
	
	 

	public MainWindow() {   // initial setup
	        frame.setSize(1000, 1000);  
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        frame.setLayout(null);
	        frame.add(canvas);   
	        canvas.setBounds(0, 0, 1000, 1000); 
			   canvas.setBackground(new Color(255,255,255)); 
		      canvas.setVisible(false);    
		          

        // New: Set Viewer reference in Model AFTER creation
            gameworld.setViewer(canvas);
			
			blackOverlay = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2d = (Graphics2D) g;
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
					g2d.setColor(Color.BLACK);
					g2d.fillRect(0, 0, getWidth(), getHeight());
				}
			};
			blackOverlay.setBounds(0, 0, 1000, 1000);
			blackOverlay.setOpaque(false);
	
			// JLabel (text in the middle)
			label1 = new JLabel("Last Train coming at Roma Termini", SwingConstants.CENTER);
			label1.setFont(new Font("SansSerif", Font.BOLD, 24));
			label1.setForeground(Color.WHITE);
			label1.setBounds(200, 450, 600, 40);
	
	
		       
	        JButton startMenuButton = new JButton("Start Game");  
	        startMenuButton.addActionListener(new ActionListener()
	           { 
				@Override
				public void actionPerformed(ActionEvent e) { 
					startMenuButton.setVisible(false);
					BackgroundImageForStartMenu.setVisible(false); 
					canvas.setVisible(true); 
					canvas.addKeyListener(Controller);    
	            canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
					startGame=true;
					canvas.timer.start();
					canvas.loadBackground("res/back1.png",1);
					
				}});  
	        startMenuButton.setBounds(400, 550, 200, 40);


			
	        difficultButton = new JButton("Level"); 
	        difficultButton.addActionListener(new ActionListener()
	           { 
				@Override
				public void actionPerformed(ActionEvent e) { 
					if(gameworld.difficult==3) gameworld.difficult=1;
					else gameworld.difficult++;

					String level= "Normal";
	if(gameworld.difficult==1) level="Normal";
	if(gameworld.difficult==2) level="Hard";
	if(gameworld.difficult==3) level="Speedrun";
    difficultButton.setText("Level: "+level);
				}});  
	        difficultButton.setBounds(400, 650, 200, 40);



			// Load button image
File buttonImageFile = new File("res/window.png"); // Replace with actual image path
try {
    BufferedImage buttonImage = ImageIO.read(buttonImageFile);
    ImageIcon buttonIcon = new ImageIcon(buttonImage.getScaledInstance(200, 40, Image.SCALE_SMOOTH)); // Adjust size

    startMenuButton.setIcon(buttonIcon);
    startMenuButton.setText("Start Game");
	startMenuButton.setFont(new Font("SansSerif", Font.BOLD, 20));  
	startMenuButton.setHorizontalTextPosition(SwingConstants.CENTER);
    startMenuButton.setVerticalTextPosition(SwingConstants.CENTER); 
	startMenuButton.setForeground(Color.LIGHT_GRAY);


	difficultButton.setIcon(buttonIcon);
	String level= "Normal";
	if(gameworld.difficult==1) level="Normal";
	if(gameworld.difficult==2) level="Hard";
	if(gameworld.difficult==3) level="Speedrun";
    difficultButton.setText("Level: "+level);
	difficultButton.setFont(new Font("SansSerif", Font.BOLD, 20));  
	difficultButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center horizontally
    difficultButton.setVerticalTextPosition(SwingConstants.CENTER); // Center vertically
	difficultButton.setForeground(Color.LIGHT_GRAY);
} catch (IOException e) {
    e.printStackTrace();
}
			
			
	        
	        //loading background image 
	        File BackroundToLoad = new File("res/t.jpg");  
			frame.add(label1);
			frame.add(blackOverlay); 
			try {
				 
				 BufferedImage myPicture = ImageIO.read(BackroundToLoad);
				 BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
				 BackgroundImageForStartMenu.setBounds(0, 0, 1000, 1000);
				frame.add(BackgroundImageForStartMenu); 
			}  catch (IOException e) { 
				e.printStackTrace();
			}   

	         frame.add(startMenuButton);  
			 frame.add(difficultButton);
	       frame.setVisible(true);   
  
	}

	public static void main(String[] args) {
		MainWindow hello = new MainWindow();  //sets up environment 
		// Start fade-out effect
		fadeOutEffect();
		PlaySoundDemo.playBackgroundSound("resources/terminibeat.wav", 1.0f);
		while(true)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
		{ 
			//swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it 
			
			int TimeBetweenFrames =  1000 / TargetFPS;
			long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames; 
			
			//wait till next time step 
		 while (FrameCheck > System.currentTimeMillis()){} 
		 
		 
		 
			
			
			if(startGame){
				difficultButton.setVisible(false);
				 gameloop();
				 }
			
			//UNIT test to see if framerate matches 
		 UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS); 
			  
		}
		
		
	} 
	//Basic Model-View-Controller pattern 
	private static void gameloop() { 
		
		if (canvas.gameState == Viewer.GameState.OVERWORLD) { // hope its not buggy
		
		gameworld.gamelogic();
		canvas.updateButtonVisibility();


		
		if (gameworld.isBattleTriggered()) {
            startBattle();
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
	canvas.updateButtonVisibility();
    

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
				// Switch back to overworld mode
		canvas.battleLog.setVisible(false);
			}
		}
		

		if (gameworld.getPlayer().isDefeated()) {
			showGameOverScreen(); // Show Game Over screen instead of overworld
		}else{
		canvas.gameState=Viewer.GameState.OVERWORLD;
		
		System.out.println("oldmapnumber:"+ canvas.oldmapnumber);
		canvas.mapnumber=canvas.oldmapnumber;
		canvas.updateBack();
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

		
		System.out.println("Exiting Battle Mode...");

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
		canvas.oldmapnumber=canvas.mapnumber;
		System.out.println("oldmapnumber:"+ canvas.oldmapnumber);
		canvas.mapnumber=0;
		canvas.updateBack();
		canvas.gameState=Viewer.GameState.BATTLE;
		
		canvas.updateButtonVisibility();

		canvas.battleLog.setVisible(true);
		canvas.updateBattleLog("Battle start!");
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

		Timer timer = new Timer(400, e1 -> {
		
		if( gameworld.EnemyBattle instanceof pula){
				PlaySoundDemo.playSound("resources/police1.wav", 0.8f);
			}
		else if(gameworld.EnemyBattle instanceof barbone){
				String[] bs = { "resources/b1.wav","resources/b2.wav","resources/b3.wav","resources/b4.wav","resources/b5.wav"};
					 Random random = new Random();
					String chosen = bs[random.nextInt(bs.length)];
			PlaySoundDemo.playSound(chosen);
		}
		((Timer) e1.getSource()).stop(); // Stop the timer after execution
	});
	timer.setRepeats(false);
	timer.start();

	}


	private static void showGameOverScreen() {
		 
		 startGame = false;

		 opacity = 1.0f; 
		 blackOverlay.repaint();
	
		 label1.setText("Game Over! You lost your train.");
		 label1.setFont(new Font("SansSerif", Font.BOLD, 40));
		 label1.setForeground(Color.white);
		 label1.setBounds(150, 450, 700, 50);
		
		 frame.getContentPane().removeAll();
		 frame.add(label1);
		 frame.add(blackOverlay);
		 
		 label1.setVisible(true);
	 
		 frame.revalidate();
		 frame.repaint();
	 
	}



    private static void fadeOutEffect() {
        Timer timer = new Timer(50, e -> {
            opacity -= 0.007f; 
            if (opacity <= 0) {
                opacity = 0;
                ((Timer) e.getSource()).stop(); 
                frame.remove(blackOverlay); 
				frame.remove(label1);
                frame.repaint();
            } else {
                blackOverlay.repaint();
            }
        });
        timer.start();
    }


	

}
