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
import util.cane.direction2;
import util.primonero.direction;
import java.util.Random;
import util.PlaySoundDemo;
import util.barbone;
import util.cane;
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
public class Model {
	
	 private  primonero Player;
	 public cane Player2;
	 private Controller controller = Controller.getInstance();
	 private  CopyOnWriteArrayList<GameObject> EnemiesList  = new CopyOnWriteArrayList<GameObject>();
	 private  CopyOnWriteArrayList<GameObject> BulletList  = new CopyOnWriteArrayList<GameObject>();
	 public GameObject EnemyBattle;

	 public GameObject Animestill;
	 public GameObject Animestillenemy;

	 private int Score=0; 

	 public float savedPlayerX, savedPlayerY;  //saved info before transition, maybe save more stuff
    
	 public boolean playerTurn = false; 

	 private Viewer viewer;

	 public Timer inactivityTimer;

	 public int ylimit=0;
	 
	 public int xlimitdx=1000;
	 public int xlimitsx=0;
	 public int difficult=1;

	 
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
		Player= new primonero("res/1n.png",100,100,new Point3f(900,400,0));
		Player2= new cane("res/bludog.png",70,70,new Point3f(850,400,0));
		//Enemies  starting with four 
		
		EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*50+400 ),((float)Math.random()*600+400),0))); 
		EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(((float)Math.random()*100 ),((float)Math.random()*600+400),0)));
		EnemiesList.add(new barbone("res/barbone.png",100,100,new Point3f(((float)Math.random()*10+50 ),((float)Math.random()*600+400 ),0)));
		
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
		viewer.mapnumber=0;
		viewer.updateBack();
		//System.out.print(" bl ");
		if (!playerTurn) { // Enemy's turn
        enemyAttack();
		if(!(EnemyBattle.isDefeated() || Player.isDefeated())){
		playerTurn = true; 
		// start inactivity timer
		inactivityTimer.start();
		}
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
		viewer.updateButtonVisibility();
		updateBattleLog("Match finished!");
		((Timer) e.getSource()).stop(); // Stop timer after execution
        });
        timer.setRepeats(false); // Ensure it only runs once
        timer.start();
		//playerTurn = false;
	}
}

	private void gameLogic() { 
		//System.out.print(" gL ");
		
		// this is a way to increment across the array list data structure 


		//implement switching of maps in here

		if(viewer.mapnumber==1){ 
			// transition from 1->2
			if(Player.getCentre().getX()<150 && Player.getCentre().getY()>770){
				//change map
				viewer.mapnumber=2;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(850, 700);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
				//viewer.oldmapnumber=2;
				
			}
		}

		if(viewer.mapnumber==2){ 
			//System.out.println("Player X: " + Player.getCentre().getX() + ", Y: " + Player.getCentre().getY());

			// transition from 2->1
			if(Player.getCentre().getX()>880 && Player.getCentre().getY()>ylimit){
				System.out.print("back 2->1");
				//change map
				viewer.mapnumber=1;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(200, 800);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
			}

			//transition 2->3
			if(Player.getCentre().getX()<50 && Player.getCentre().getY()>ylimit){
				//change map
				viewer.mapnumber=3;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(300, 800);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
				//viewer.oldmapnumber=2;
				
			}
		}

		if(viewer.mapnumber==3){ 
			//System.out.println("Player X: " + Player.getCentre().getX() + ", Y: " + Player.getCentre().getY());

			// transition from 3->2
			if( Player.getCentre().getY()>850){
				System.out.print("back 3->2");
				//change map
				viewer.mapnumber=2;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(150, 800);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
			}

			//transition 3->4
			if(Player.getCentre().getY()< ylimit+50){
				//change map
				viewer.mapnumber=4;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(800, 700);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
				//viewer.oldmapnumber=2;
				
			}
		}

		if(viewer.mapnumber==4){ 
			//System.out.println("Player X: " + Player.getCentre().getX() + ", Y: " + Player.getCentre().getY());

			// transition from 4->3
			if(Player.getCentre().getX()>880 && Player.getCentre().getY()>ylimit){
				System.out.print("back 4->3");
				//change map
				viewer.mapnumber=3;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(300, 400);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
			}

			//transition 4->5
			if(Player.getCentre().getX()<50 && Player.getCentre().getY()>ylimit){
				//change map
				viewer.mapnumber=5;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(550, 850);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
				//viewer.oldmapnumber=2;
				
			}
		}


		
		if(viewer.mapnumber==5){ 
			System.out.println("Player X: " + Player.getCentre().getX() + ", Y: " + Player.getCentre().getY());

			// transition from 5->4
			if(Player.getCentre().getX()>600 && Player.getCentre().getY()>ylimit){
				System.out.print("back 5->4");
				//change map
				viewer.mapnumber=4;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(150, 800);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
			}

			//transition 5->6 
			if(Player.getCentre().getX()<350 && Player.getCentre().getY()<750){
				//change map
				viewer.mapnumber=6;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(250, 700);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
				}
			} 
			//transition 5->7
			if(Player.getCentre().getX()>600 && Player.getCentre().getY()<750){
				// if too late goto 6
				if(viewer.timeleft<0)  viewer.mapnumber=6;
				else viewer.mapnumber=7;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(150, 700);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
				}
			} 
		}

			
		if(viewer.mapnumber==6){ 
			// transition from 6->5

			//show label and say if late or wrong station
			viewer.battleLog.setVisible(true);
				if(viewer.timeleft<0){
					viewer.updateBattleLog("You lost your last train, tonight you will sleep at Roma Termini!");
				}else{
					viewer.updateBattleLog("This is the wrong Train! Go find the right one!");
				}


			if(Player.getCentre().getX()<100 && Player.getCentre().getY()>ylimit){
				System.out.print("back 6->5");
				//change map
				viewer.battleLog.setVisible(false);
				viewer.mapnumber=5;
				viewer.updateBack();
				//reset position player and dog
				resetPositionPlayers(400, 750);
				
				//reset mobs
				for(GameObject temp : EnemiesList){
					EnemiesList.remove(temp);
					System.out.println("mob rimosso");
				}
			}
		}

		if(viewer.mapnumber==7){ 
			//show label and say if late or wrong station
			viewer.battleLog.setVisible(true);
			viewer.updateBattleLog("you got to the train in time! Congrats!");
			
		}


		
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
			//check collision and avoid overlapping only with player 2
			float dx = temp.getCentre().getX() - Player2.getCentre().getX();
			float dy = temp.getCentre().getY() - Player2.getCentre().getY();
			float width = temp.getWidth();
			float height = temp.getHeight();
		
			// Check for collision
			if (Math.abs(dx) < width && Math.abs(dy) < height) {
		
				
				float pushX = (width - Math.abs(dx));  // Amount to push in X direction
				float pushY = height - Math.abs(dy) ; // Amount to push in Y direction
		
				// Determine direction of movement
				if (dx > 0) {
					//Player2.getCentre().setX(Player2.getCentre().getX() - pushX / 2);
					temp.getCentre().setX(temp.getCentre().getX() + pushX / 4);
				} else {
					//Player2.getCentre().setX(Player2.getCentre().getX() + pushX / 2);
					temp.getCentre().setX(temp.getCentre().getX() - pushX / 4);
				}
		
				if (dy > 0) {
					//Player2.getCentre().setY(Player2.getCentre().getY() - pushY / 2);
					temp.getCentre().setY(temp.getCentre().getY() + pushY / 4);
				} else {
					//Player2.getCentre().setY(Player2.getCentre().getY() + pushY / 2);
					temp.getCentre().setY(temp.getCentre().getY() - pushY / 4);
				}
		}
	}
	
}


public boolean isBattleTriggered() {
	//System.out.println(" isBattleTriggered ");
	if (EnemiesList == null || EnemiesList.isEmpty()) {
       // System.out.println("Error: EnemiesList is null or empty!");
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
		boolean stop=false;
		if(Score>70 || viewer.mapnumber==3){
			// to spawn less mobs after high score
			stop=true;
			int r=(int)Math.random()*4;
			if(r==3) stop= false;
		}
		if(viewer.mapnumber==7) stop=true; // no mob in final screen
		//for debugging
		//stop=true;
		if (EnemiesList.size()<1 && !stop)
		//dipende da mappa  a dove farli spawna
		// fixa dopo
		{
			while (EnemiesList.size()<3)
			{
				if(EnemiesList.size()%2==0)
				EnemiesList.add(new pula("res/pula.png",100,100,new Point3f(xlimitsx +80+ (float)Math.random() * ((xlimitdx - 80) - (xlimitsx+80)),((float)Math.random()*(1000-ylimit)+ylimit),0))); 
				else{
					EnemiesList.add(new barbone("res/barbone.png",100,100,new Point3f(xlimitsx +80+ (float)Math.random() * ((xlimitdx - 80) - (xlimitsx- 80)),((float)Math.random()*(1000-ylimit)+ylimit),0)));
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
			 if(Player.dir==primonero.direction.up || Player.dir==primonero.direction.left ){ 
			temp.getCentre().ApplyVector(new Vector3f(-2,0,0));
			 }
			 else if(Player.dir==primonero.direction.down || Player.dir==primonero.direction.rigth ){ 
				temp.getCentre().ApplyVector(new Vector3f(2,0,0));
				 }
			
			if (temp.getCentre().getX()<=0 || temp.getCentre().getX()>=950)
			{
			 	BulletList.remove(temp);
			} 
		} 
		
	}
	private void updateLimits(){
		if(viewer.mapnumber<1){
			ylimit=0;
			xlimitdx=1000;
			xlimitsx=0;
		}
		else if(viewer.mapnumber==1){
			xlimitsx=0;
			xlimitdx=900;
			ylimit=300;
		}
		else if(viewer.mapnumber==2){
			xlimitsx=0;
			xlimitdx=900;
			ylimit=600;
		}
		else if(viewer.mapnumber==3){
			xlimitdx=400;
			xlimitsx=200;
			ylimit=300;
		}
		else if(viewer.mapnumber==4){
			xlimitsx=0;
			xlimitdx=900;
			ylimit=600;
		}
		else if(viewer.mapnumber==5){
			xlimitdx=650;
			xlimitsx=300;
			ylimit=700;
		}
		else if(viewer.mapnumber==6){
			xlimitdx=400;
			xlimitsx=0;
			ylimit=500;
		}
		else if(viewer.mapnumber==7){
			xlimitdx=400;
			xlimitsx=0;
			ylimit=500;
		}
	}

	private void playerLogic() {
		
		updateLimits();
		//System.out.println(" pL ");
		
		//Player1 movement
		  
          if(Controller.getInstance().isKeyAPressed())
		  {
			if(Player.getCentre().getX()> xlimitsx ){
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.left;
			Player.getCentre().ApplyVector( new Vector3f(-3,0,0)); 
			}
		}
		
		if(Controller.getInstance().isKeyDPressed())
		{   if(Player.getCentre().getX()< xlimitdx ){
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.rigth;
			Player.getCentre().ApplyVector( new Vector3f(3,0,0));
		}
		}
			
		if(Controller.getInstance().isKeyWPressed())
		{
			if(Player.getCentre().getY() > ylimit){
			PlaySoundDemo.playSound("resources/footstep.wav");
			Player.dir=direction.up;
			Player.getCentre().ApplyVector( new Vector3f(0,3,0));
			}
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


		if(Controller.getInstance().isKeyFPressed())
		{
			String[] hurts = {"resources/1euro.wav", "resources/ballocinese.wav","resources/haiuto.wav","resources/haiuto2.wav", "resources/kiss2.wav", "resources/kiss3.wav", "resources/perfavore.wav", "resources/haiuto.wav"};
					 Random random = new Random();
					String chosen = hurts[random.nextInt(hurts.length)];
			PlaySoundDemo.playSound(chosen);
			Controller.getInstance().setKeyFPressed(false);
		}

        // Dog

		if(Controller.getInstance().isKey4Pressed())
		  {
			if(Player2.getCentre().getX()> xlimitsx ){
			Player2.dir= direction2.left;
			Player2.getCentre().ApplyVector( new Vector3f(-3,0,0)); 
			}
		}
		
		if(Controller.getInstance().isKey6Pressed())
		{
			if(Player2.getCentre().getX()< xlimitdx ){
			Player2.dir=direction2.rigth;
			Player2.getCentre().ApplyVector( new Vector3f(3,0,0));
			}
		}
			
		if(Controller.getInstance().isKey8Pressed())
		{
			if(Player2.getCentre().getY() > ylimit){
			Player2.dir=direction2.up;
			Player2.getCentre().ApplyVector( new Vector3f(0,3,0));
		}
	}
		if(Controller.getInstance().isKey5Pressed())
		{
			Player2.dir=direction2.down;
			Player2.getCentre().ApplyVector( new Vector3f(0,-3,0));
		}
		
		if(Controller.getInstance().isKey0Pressed())
		{
			PlaySoundDemo.playSound("resources/woof.wav");
			Player2.woof=Player2.woof +1; // increase woof
			Controller.getInstance().setKey0Pressed(false);
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

	public void resetPositionPlayers(int x, int y){
		Player.setCentre(new Point3f(x,y,0.0f));
		Player2.setCentre(new Point3f(x+30,y,0.0f));
	}



 

}

