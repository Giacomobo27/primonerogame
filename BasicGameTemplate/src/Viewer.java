import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import util.GameObject;
import util.Point3f;
import util.primonero;
import util.primonero.direction;
import java.util.Random;


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
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {

	private long CurrentAnimationTime= 0; 
	private int fadeOpacity = 0; 
	public enum GameState {
		OVERWORLD, BATTLE
	}
	public GameState gameState = GameState.OVERWORLD;

	public  JButton attackButton;
	public  JButton begButton;
	public JButton seduceButton;
	public  JButton stealButton;
	public  JTextArea battleLog;
	public int animation=0;

	Model gameworld =new Model(); 
	 
	public Viewer(Model World) {
		this.gameworld=World;
		// TODO Auto-generated constructor stub

		this.setLayout(null); // Allow manual positioning of buttons

    // Create Attack Button
    attackButton = new JButton("Attack");
    attackButton.setBounds(350, 700, 150, 50);
    attackButton.addActionListener(e -> {
        if (gameworld.playerTurn && gameworld.EnemyBattle != null) {
            gameworld.EnemyBattle.takeDamage(gameworld.getPlayer().attackpower);
            gameworld.playerTurn = false; // End player's turn
			updateBattleLog("You attacked the enemy!");
			//set animation
			gameworld.inactivityTimer.stop();
			animation =1;
			gameworld.Animestillenemy=null;

			Timer timer = new Timer(700, e1 -> {
    gameworld.playerTurn = false; // End player's turn
    updateBattleLog("Opponent turn!");
    ((Timer) e1.getSource()).stop();});
	timer.setRepeats(false);
	timer.start();
	updateButtonVisibility(); 
	}
    });
    this.add(attackButton); // Add button to Viewer

    // Create Beg Button
    begButton = new JButton("Beg");
    begButton.setBounds(500, 700, 150, 50);
    begButton.addActionListener(e -> {
		if (gameworld.playerTurn && gameworld.EnemyBattle != null){
		updateBattleLog("HAITOO! You begged the enemy for 1 euro! It's useless");
		gameworld.inactivityTimer.stop();
		gameworld.playerTurn = false; 
		gameworld.Animestillenemy=null;
		Timer timer = new Timer(700, e1 -> {
			gameworld.playerTurn = false; // End player's turn
			updateBattleLog("Opponent turn!");
			((Timer) e1.getSource()).stop(); // Stop the timer after execution
		});
		timer.setRepeats(false);
		timer.start();
			updateButtonVisibility(); // Ensure buttons update correctly

		}
    });
    this.add(begButton); // Add button to Viewer

	// Create seduceButton
    seduceButton = new JButton("seduce");
    seduceButton.setBounds(650, 700, 150, 50);
    seduceButton.addActionListener(e -> {
		if (gameworld.playerTurn && gameworld.EnemyBattle != null){

			//random buff or debuff
			Random random = new Random();
			int randombuff = random.nextInt(11) -5 ;
			gameworld.getPlayer().attackpower= gameworld.getPlayer().attackpower + randombuff;
		updateBattleLog("You kissed the enemy without consent! your attack got a buff of "+ randombuff+ " points!");
		gameworld.inactivityTimer.stop();
		gameworld.playerTurn = false;
		gameworld.Animestillenemy=null;
		animation=2;

		Timer timer = new Timer(700, e1 -> {
			gameworld.playerTurn = false; // End player's turn
			updateBattleLog("Opponent turn!");
			((Timer) e1.getSource()).stop(); // Stop the timer after execution
		});
		timer.setRepeats(false);
		timer.start();
			updateButtonVisibility(); // Ensure buttons update correctly

		}
    });
    this.add(seduceButton); // Add button to Viewer

	// Create steal Button
    stealButton = new JButton("steal");
    stealButton.setBounds(200, 700, 150, 50);
    stealButton.addActionListener(e -> {
		if (gameworld.playerTurn && gameworld.EnemyBattle != null){
			gameworld.inactivityTimer.stop();
			gameworld.Animestillenemy=null;
			Random random = new Random();
			int luck = random.nextInt(5);
			if(luck>2) {
				updateBattleLog("PRIMO NERO SEMPRE RUBARE! You successfully stole enemy's money!");
				gameworld.getPlayer().hp = gameworld.getPlayer().hp + 5* luck;
			}
			else{
				updateBattleLog("PRIMO NERO SEMPRE RUBARE! You got caught stealing enemy's money!");
				gameworld.getPlayer().hp = gameworld.getPlayer().hp - 5* luck;
				animation =3;
			}
		gameworld.playerTurn = false; 
		Timer timer = new Timer(700, e1 -> {
			gameworld.playerTurn = false; // End player's turn
			updateBattleLog("Opponent turn!");
			((Timer) e1.getSource()).stop(); // Stop the timer after execution
		});
		timer.setRepeats(false);
		timer.start();
			updateButtonVisibility(); // Ensure buttons update correctly

		}
    });
    this.add(stealButton); // Add button to Viewer



	//create log
	battleLog = new JTextArea();
    battleLog.setBounds(250, 800, 500, 100);
    battleLog.setEditable(false);
    this.add(battleLog);

    // Initially hide buttons
    attackButton.setVisible(false);
    begButton.setVisible(false);
	seduceButton.setVisible(false);
	stealButton.setVisible(false);
	battleLog.setVisible(false);
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		
		this.repaint();
		// TODO Auto-generated method stub
		// repaint() eventually calls paintComponent(Graphics g), when Swing decides to refresh
		
	}

public void setFadeOpacity(int opacity) {
	this.fadeOpacity = Math.min(Math.max(opacity, 0), 255); // Ensures it's between 0-255
    repaint();
}
	
	//paintComponent(Graphics g) is called in every iteration
	// of the game loop to redraw the entire game screen.
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		CurrentAnimationTime++; // runs animation time step 
		

		if (gameState == GameState.OVERWORLD) {
			drawOverworld(g);
		} else if (gameState == GameState.BATTLE) {
			drawBattleScreen(g);

			
		}

	}

	public void drawOverworld(Graphics g) {

		
		//Draw player Game Object 
		int x = (int) gameworld.getPlayer().getCentre().getX();
		int y = (int) gameworld.getPlayer().getCentre().getY();
		int width = (int) gameworld.getPlayer().getWidth();
		int height = (int) gameworld.getPlayer().getHeight();
		String texture = gameworld.getPlayer().getTexture();
		
		//Draw background 
		//drawBackground(g);  // boolean that swaps it //stable diffusion

		drawBackground(g, "res/b1.jpeg");
		
		//Draw player
		if(gameworld.getPlayer().dir == direction.left || gameworld.getPlayer().dir == direction.up){
			//draw 1nleft
			gameworld.getPlayer().setTexture("res/1nleft.png");
		}

		if(gameworld.getPlayer().dir == direction.rigth || gameworld.getPlayer().dir == direction.down){
			//draw 1nleft
			gameworld.getPlayer().setTexture("res/1n.png");
		}
		
		drawPlayer(x, y, width, height, texture,g);  
		
		  
		//Draw Bullets 
		// change back 
		gameworld.getBullets().forEach((temp) -> 
		{ 
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		}); 
		
		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		 
	    }); 
		// for transition, may modify it
		if (fadeOpacity > 0) {
			g.setColor(new Color(0, 0, 0, fadeOpacity)); // Black with adjustable opacity
			g.fillRect(0, 0, getWidth(), getHeight());
		}


	}

	public void drawBattleScreen(Graphics g) {

		drawBackground(g, "res/spacebackground.png");  // boolean that swaps it //stable diffusion

		
		//Draw player Game Object 
		//int x = (int) gameworld.getPlayer().getCentre().getX();
		//int y = (int) gameworld.getPlayer().getCentre().getY();
		int width = (int) gameworld.getPlayer().getWidth();
		int height = (int) gameworld.getPlayer().getHeight();
		String texture = gameworld.getPlayer().getTexture();
		

		GameObject player = gameworld.getPlayer();
		GameObject enemy = gameworld.EnemyBattle;
		
		//Draw player
		if(gameworld.getPlayer().dir == direction.left || gameworld.getPlayer().dir == direction.up){
			//draw 1nleft
			gameworld.getPlayer().setTexture("res/1nleft.png");
		}

		if(gameworld.getPlayer().dir == direction.rigth || gameworld.getPlayer().dir == direction.down){
			//draw 1nleft
			gameworld.getPlayer().setTexture("res/1n.png");
		}
		
		drawPlayer(100, 200, width*3, height*3, texture,g);  
		drawHPBar(g, 50, 50, player.hp, player.maxHp, Color.GREEN, "Player HP");
		

        if (gameworld.EnemyBattle != null) {
		//draw single enemy
		drawEnemies(550, 200, (int) enemy.getWidth()*3, (int) enemy.getHeight()*3, enemy.getTexture(),g);
		//int) enemy.getCentre().getX(), (int) enemy.getCentre().getY()

		drawHPBar(g, 700, 50, enemy.hp, enemy.maxHp, Color.RED, "Enemy HP");
		
		} else {
			System.out.println("Error: No enemy set for battle.");
		}

		// for transition, may modify it
		if (fadeOpacity > 0) {
			g.setColor(new Color(0, 0, 0, fadeOpacity)); // Black with adjustable opacity
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		//lets draw animations
		if(animation==1){
			System.out.println("animation1");
			gameworld.Animestill= new GameObject("res/gun.png",120,120,new Point3f(300,350,0.0f),10);
		}
		if(animation==2){
			System.out.println("animation2");
			gameworld.Animestill= new GameObject("res/kiss.png",120,120,new Point3f(300,250,0.0f),10);
		}
		if(animation == 3){
			System.out.println("animation3");
			gameworld.Animestill= new GameObject("res/euro.png",120,120,new Point3f(300,300,0.0f),10);
		}
		if(animation == 3){
			System.out.println("animation3");
			gameworld.Animestill= new GameObject("res/euro2.png",120,120,new Point3f(300,300,0.0f),10);
		}
		if(animation == 4){
			System.out.println("animation4");
			gameworld.Animestillenemy= new GameObject("res/stick.png",120,120,new Point3f(500,300,0.0f),10);
		}
		if(animation == 5){
			System.out.println("animation5");
			gameworld.Animestillenemy= new GameObject("res/shit.png",120,120,new Point3f(400,400,0.0f),10);
		}

		    GameObject anime1= gameworld.Animestill;
			if(anime1 != null){
			drawEnemies((int) anime1.getCentre().getX(), (int) anime1.getCentre().getY(),(int) anime1.getWidth(),(int) anime1.getHeight(),anime1.getTexture(),g);
			} else{
				System.out.println("no anime1");
			}

			GameObject anime2= gameworld.Animestillenemy;
			if(anime2 != null){
			drawEnemies((int) anime2.getCentre().getX(), (int) anime2.getCentre().getY(),(int) anime2.getWidth(),(int) anime2.getHeight(),anime2.getTexture(),g);
			} else{
				System.out.println("no anime2");
			}

		
	}
	private void drawHPBar(Graphics g, int x, int y, int currentHP, int maxHP, Color color, String label) {
		int barWidth = 200;
		int barHeight = 20;
		int filledWidth = (int) ((double) currentHP / maxHP * barWidth);
	
		g.setColor(Color.BLACK);
		g.drawRect(x, y, barWidth, barHeight);  // Outline
		g.setColor(color);
		g.fillRect(x, y, filledWidth, barHeight);  // Fill bar
	
		g.setColor(Color.WHITE);
		g.drawString(label + ": " + currentHP + "/" + maxHP, x + 10, y + 15);
	}

	
	private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
			int currentPositionInAnimation= ((int) ((CurrentAnimationTime%40)/10)*32); //slows down animation so every 10 frames we get another frame so every 100ms 
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+31, 32, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	private void drawBackground(Graphics g, String filepath)
	{
		File TextureToLoad = new File(filepath);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			 g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	{
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			//64 by 128 
			 g.drawImage(myImage, x,y, x+width, y+height, 0 , 0, 63, 127, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void drawPlayer(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
			int currentPositionInAnimation= ((int) ((CurrentAnimationTime%40)/10))*32; //slows down animation so every 10 frames we get another frame so every 100ms 
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+31, 32, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		//Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time 
		// Bullets from https://opengameart.org/forumtopic/tatermands-art 
		// background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images
		
	}

	public void updateBattleLog(String message) {
		battleLog.setText(message + "\n");
	}

	public void updateButtonVisibility() {
		if (gameworld.playerTurn) {
			attackButton.setVisible(true);
			begButton.setVisible(true);
			stealButton.setVisible(true);
			seduceButton.setVisible(true);
		} else {
			attackButton.setVisible(false);
			begButton.setVisible(false);
			stealButton.setVisible(false);
			seduceButton.setVisible(false);
		}
	}




}