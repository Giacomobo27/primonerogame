import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;
import util.Point3f;
import util.Vector3f; 
import util.primonero;
import util.pula;
import util.primonero.direction;
import java.util.Random;
import util.PlaySoundDemo;
import util.barbone;
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
public class Model {
	
	 private  primonero Player;
	 private Controller controller = Controller.getInstance();
	 private  CopyOnWriteArrayList<GameObject> EnemiesList  = new CopyOnWriteArrayList<GameObject>();
	 private  CopyOnWriteArrayList<GameObject> BulletList  = new CopyOnWriteArrayList<GameObject>();
	 public GameObject EnemyBattle;

	 public GameObject Animestill;
	 public GameObject Animestillenemy;

	 private int Score=0; 

	 public float savedPlayerX, savedPlayerY;  //saved info before transition, maybe save more stuff
    
	 public boolean playerTurn = true; 

	 private Viewer viewer;

	 public Timer inactivityTimer;
	 
	 public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }


    public void updateBattleLog(String message) {
        if (viewer != null) {
            viewer.updateBattleLog(message);
        }
    }

	 

	public Model() {
		 //setup game world 
		Player= new primonero("res/1n.png",100,100,new Point3f(500,500,0));
		//Enemies  starting with four 
		
		EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*50+400 ),((float)Math.random()*1000),0))); 
		EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*50+500 ),((float)Math.random()*100 ),0)));
		EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*100 ),((float)Math.random()*1000),0)));
		EnemiesList.add(new barbone("res/barbone.png",100,100,new Point3f(((float)Math.random()*10+50 ),((float)Math.random()*100 ),0)));
		
		// Initialize Inactivity Timer (Triggers after 3 seconds of no action)
		inactivityTimer = new Timer(3000, e -> {
			if (playerTurn) {
				System.out.println("Animation enemy finished!");
				Animestillenemy=null;
				viewer.animation=0;
			}
		});
		inactivityTimer.setRepeats(false); 
	    
	}
	
	// This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly. 
	public void gamelogic() 
	{
		//System.out.print(" gl ");
		// Player Logic first 
		playerLogic(); 
		// Enemy Logic next
		enemyLogic();
		// Bullets move next 
		bulletLogic();
		// interactions between objects 
		gameLogic(); 
	   
	}

	

	public void battlelogic()
	{
		//System.out.print(" bl ");
		if (!playerTurn) { // Enemy's turn
        enemyAttack();
		playerTurn = true; 
		// start inactivity timer
		inactivityTimer.start();
    }
	}

	private void enemyAttack() {
		if(!(EnemyBattle.isDefeated() || Player.isDefeated())){
		Timer timer = new Timer(700, e -> { // if  delay > 700, there are bugs with buttons

            Animestill=null;
		   viewer.animation=0;

		if (EnemyBattle != null) {
			Player.takeDamage(EnemyBattle.attackpower);
			if(EnemyBattle instanceof pula){
				viewer.animation=4;
			}
			if(EnemyBattle instanceof barbone){
				viewer.animation=5;
			}
			//altre animazioni ...
			updateBattleLog("Enemy attacked! You lost " + EnemyBattle.attackpower  + " HP.");
		}

		  // Give turn back to player
		  playerTurn = true;
		  if (viewer != null) viewer.updateButtonVisibility();

		((Timer) e.getSource()).stop(); // Stop timer after execution
        });
        timer.setRepeats(false); // Ensure it only runs once
        timer.start();
		
	}
	else{
		Timer timer = new Timer(700, e -> {
		updateBattleLog("Match finished!");
		((Timer) e.getSource()).stop(); // Stop timer after execution
        });
        timer.setRepeats(false); // Ensure it only runs once
        timer.start();
		//playerTurn = false;
		viewer.attackButton.setVisible(false);
		viewer.begButton.setVisible(false);
		viewer.stealButton.setVisible(false);
		viewer.seduceButton.setVisible(false);
	}
}

	private void gameLogic() { 
		//System.out.print(" gL ");
		
		// this is a way to increment across the array list data structure 

		
		//see if they hit anything 
		// using enhanced for-loop style as it makes it alot easier both code wise and reading wise too 
		for (GameObject temp : EnemiesList) 
		{
		for (GameObject Bullet : BulletList) 
		{   // collision bullet with player
			if ( Math.abs(temp.getCentre().getX()- Bullet.getCentre().getX())< temp.getWidth() 
				&& Math.abs(temp.getCentre().getY()- Bullet.getCentre().getY()) < temp.getHeight())
			{
				//EnemiesList.remove(temp);
				// if barbone  -> play sound
				if( temp instanceof barbone){
					String[] hurts = {"resources/hurt1.wav", "resources/hurt2.wav"};
					 Random random = new Random();
					String chosen = hurts[random.nextInt(hurts.length)];
					PlaySoundDemo.playSound(chosen);
				}
				//if pula -> play sound e vieni inseguito
				if( temp instanceof pula){
					pula p = (pula) temp;
					p.danger= true;
					PlaySoundDemo.playSound("resources/hurt1.wav");
				}
				BulletList.remove(Bullet);
				Score++;
			}  
		}
	}
}


public boolean isBattleTriggered() {
	//System.out.println(" isBattleTriggered ");
	if (EnemiesList == null || EnemiesList.isEmpty()) {
        System.out.println("Error: EnemiesList is null or empty!");
        return false;
    }
	if (Player == null) {
        System.out.println("Error: Player is null!");
        return false;
    }
    for (GameObject temp : EnemiesList) {
		if(temp == null) {
            System.out.println("Warning: A null object found in EnemiesList!");
            continue;
        }
		if ( Math.abs(temp.getCentre().getX()- Player.getCentre().getX())< temp.getWidth() 
		&& Math.abs(temp.getCentre().getY()- Player.getCentre().getY()) < temp.getHeight()){
			System.out.println("Enter BAttle22!!");
			Player.attackpower=15;

			EnemyBattle= temp;
			System.out.println("enemy assigned");
			return true;
		}
    }
    return false;
}

public boolean battleIsOver() {
	//System.out.println(" battleIsOver ");

	if(EnemyBattle == null){
		System.out.println("Error: EnemiesList is null!");
        return false;
	}
	if (Player == null) {
        System.out.println("Error: Player is null!");
        return false;
    }
	if( EnemyBattle.isDefeated() || Player.isDefeated()){
		// delay?
		//updateBattleLog("BATTLE ENDED");


		// Remove the enemy from the game
		EnemiesList.remove(EnemyBattle);
		EnemyBattle = null;
		incrementScore(10);
	    return true;
	}
	else return false;

}



	private void enemyLogic() {
		//System.out.println(" eL ");
		// TODO Auto-generated method stub
		for (GameObject temp : EnemiesList) 
		{
			Random rand = new Random();
			float randomx = -1.0f + 2.0f *rand.nextFloat();
			float randomy = -1.0f + 2.0f *rand.nextFloat();
		    // Move enemies 
			if( temp instanceof pula){
			pula p1 = (pula) temp;
			if (p1.danger== false){ 
				temp.getCentre().ApplyVector(new Vector3f( randomx, -randomy ,0.f));
			}
			else{ // if pula hitten, move towards player
				Point3f playerpos = Player.getCentre();
				Point3f pulapos = p1.getCentre();
				Vector3f direction = playerpos.MinusPoint(pulapos);
				//normalize

				float xd = direction.getX();
				float yd = direction.getY();
				float zd = direction.getZ();

				float length = (float) Math.sqrt(xd * xd + yd * yd + zd * zd);
				if (length != 0) {
					xd /= length;
					yd /= length;
					zd /= length;
				}
				direction = new Vector3f(xd,yd,zd);
				float speed = 1.0f; // Adjust step size
				Vector3f step = new Vector3f(direction.getX()*speed, - direction.getY()*speed, direction.getZ()*speed);
				temp.getCentre().ApplyVector(step);
			}
		}
			else{
			temp.getCentre().ApplyVector(new Vector3f( randomx, -randomy ,0.f));
			}
		
			 
			 
			//see if they get to the top of the screen ( remember 0 is the top 
			if (temp.getCentre().getY()==900.0f)  // current boundary need to pass value to model 
			{
				EnemiesList.remove(temp);
				
				// enemies win so score decreased 
				Score--;
			} 
		}
		
		if (EnemiesList.size()<2)
		{
			while (EnemiesList.size()<6)
			{
				if(EnemiesList.size()%2==0)
				EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*1000),((float)Math.random()*100+400),0))); 
				else{
					EnemiesList.add(new barbone("res/barbone.png",100,100,new Point3f(((float)Math.random()*100+400),((float)Math.random()*1000),0)));
				}
			}
		}
	}

	

	private void bulletLogic() {
		//System.out.println(" bL ");
		// TODO Auto-generated method stub
		// move bullets 
	  
		for (GameObject temp : BulletList) 
		{
		    //check to move them
			  
			temp.getCentre().ApplyVector(new Vector3f(0,2,0));
			//see if they hit anything 
			
			//see if they get to the top of the screen ( remember 0 is the top 
			if (temp.getCentre().getY()==0)
			{
			 	BulletList.remove(temp);
			} 
		} 
		
	}

	private void playerLogic() {
		//System.out.println(" pL ");
		
	
		//check for movement and if you fired a bullet 
		  
          if(Controller.getInstance().isKeyAPressed())
		  {
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.left;
			Player.getCentre().ApplyVector( new Vector3f(-3,0,0)); 
		}
		
		if(Controller.getInstance().isKeyDPressed())
		{
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.rigth;
			Player.getCentre().ApplyVector( new Vector3f(3,0,0));
		}
			
		if(Controller.getInstance().isKeyWPressed())
		{
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.up;
			Player.getCentre().ApplyVector( new Vector3f(0,3,0));
		}
		
		if(Controller.getInstance().isKeySPressed())
		{
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.down;
			Player.getCentre().ApplyVector( new Vector3f(0,-3,0));
		}
		
		if(Controller.getInstance().isKeySpacePressed())
		{
			PlaySoundDemo.playSound("resources/laser.wav");
			CreateBullet();
			Controller.getInstance().setKeySpacePressed(false);
		} 
		
	}

	private void CreateBullet() {
		BulletList.add(new GameObject("res/euro.png",64,128,new Point3f(Player.getCentre().getX(),Player.getCentre().getY(),0.0f),10));
		
	}

	public primonero getPlayer() {
		return Player;
	}

	public CopyOnWriteArrayList<GameObject> getEnemies() {
		return EnemiesList;
	}
	
	public CopyOnWriteArrayList<GameObject> getBullets() {
		return BulletList;
	}

	public int getScore() { 
		return Score;
	}
    
	public void incrementScore(int points) {
		Score += points;
		System.out.println("Score increased by " + points + ". New Score: " + Score);
	}

	public void resetInactivityTimer() {
		if (inactivityTimer.isRunning()) {
			inactivityTimer.stop();
		}
		inactivityTimer.start();
	}



 

}

