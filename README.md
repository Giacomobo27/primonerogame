# **Roma Termini Primo Nero Adventure**

This is my **Java game project** for my game development class (Game engine made from scratch). Just finished.

## **Project Structure**
- **Model** → Handles game logic.
- **Viewer** → Manages UI.
- **MainWindow** → Controls high-level state transitions.
- **Controller.java** → Controls input keyboard.

---

## **Development Roadmap**

## **🛠 SPRINT 1 - Core Battle System Implementation**
### **🎯 Tasks Completed**
✅ **Implement `drawBattleScreen()`**  
   - Designed and linked all buttons to the **model**.  
   - Added **animations** for battle actions.  
✅ **Implement `battleIsOver()`**  
✅ **Implement `battlelogic()`**  
✅ **Turn-Based System**  
   - **Enemy Attacks Implemented:**  
     - Attack ✔  
     - Beg ✔ *(Audio: Primo Nero - Found & Inserted)*  
     - Seduce ✔ *(Heart Floating Animation - Audio Inserted)*  
     - Steal ✔ *(Money Flow Animation - Audio Inserted)*  
✅ **Battle Animations:** *(Created using LibreSprite & manually inserted)*
   - **Attack to enemy:** *Gun Shooting Animation* ✔  
   - **Seduce:** *Heart Floating Animation* ✔  
   - **Steal Success:** *Money Flow Animation* ✔  
   - **Police Attack to Player:** *Baton Strike* ✔  
   - **Drunk NPC Attack:** *Shit Throw* ✔ *(Audio Inserted)*  
✅ **Implemented Enemy AI Animation** *(Timers used for inactivity-based attack triggers)*  
✅ **Boss (Black Enemy) - Gun Shooting Attack** *(Sprite Created - To be inserted later)*  

**⏱️ Sprint 1 Finished at 5:20 AM**  

---

## **🐶 SPRINT 2 - Second Player & Audio Enhancements**
### **🎯 Tasks Completed**
✅ **Second Player (Dog Companion) Implementation**  
   - Moves around independently.  
   - **Bark mechanic:** Buffs player (attack/life boost).  
   - **Collision system added** to prevent map clipping.  
✅ **Sound Enhancements:**  
   - Background **battle music:** *Primo Nero Battle Sound* ✔  
   - **Enemy battle sounds** ✔  
   - **Implemented multithreading** for background music to **prevent lag**.  

✅ **MAJOR BUG FIXED:** **Battle Scene Buttons not responding properly**  

---

## **🎨 SPRINT 3 - Visual Improvements & Map Mechanics**
### **🎯 Tasks Completed**
✅ **Graphics Overhaul:**  
   - **Upgraded button designs** for UI consistency.  
   - **Health bar redesign** for clarity.  
✅ **Implemented Scaling & Player Limits**  
   - **Player Movement Scaling:** Added **depth effect** to character movement.  
   - **Bullet Mechanics Fixed & Optimized**  
✅ **Enhanced Background Mechanics**  
   - **Map Transition Logic Implemented** *(Back2 to Back7)* ✔  
   - **Collision Boundaries Set** *(Player & Enemies stay within valid zones)*  
   - **Dynamic Enemy Spawn System:**  
     - Enemy logic (mob spawn position) ✔  
     - Player logic (map boundaries for both P1 & P2) ✔  
     - Game logic (map transitions) ✔  

### **📌 End Goal System Implemented**
✅ **Timer Starts at "Start Game"**  
✅ **Win/Lose Conditions:**  
   - **If late:** Transition to *Back6* *(Train Missed - Game Over)* ✔  
   - **If on time:** Transition to *Back7* *(Reached Train - Victory)* ✔  

**⏱️ Sprint 3 Finished at 5:53 AM**  

---

## **🎵 SPRINT 4 - Final Enhancements & Polish**
### **🎯 Tasks Completed**
✅ **Background Music:** *GTA Rap Music Added* ✔  
✅ **Difficulty Options Implemented in Menu** *(Affects mission time limits: 180s -> 120s -> 60s)*  
✅ **GameOver Window Fix:** Ensures **proper UI rendering** and transitions.  
✅ **Sound Level Balancing:** Optimized volume for all sound effects.  
✅ **Intro Fade-In Effect Added** *(Smooth transition from black screen to game start)*  
✅ **Protagonist Character Color Change** *(Changed to White for better visibility & contrast)*  

---

## **🚀 Extra Features & Future Improvements**
🔲 **New Mob Type (Potential additional enemy AI)**  
🔲 **Gypsy NPC - "Smoke Weed" Action** *(Experimental mechanic)*  
🔲 **Enemy Spawning Behavior Improvements** *(Prevent overcrowding & refine enemy placement logic)*  
🔲 **Train Arrival Sounds:** Add **dynamic audio cues** to signal **impending train departure**.  

---

## **📌 FINAL TASKS**
🔲 **Write Project Report** *(Document copied resources & credits properly)*  
🔲 **Record Gameplay Video** *(Showcase game mechanics & features for submission)*  

---
# **Roma Termini Adventure**

## **Overview**
**Roma Termini Adventure** is an **action-adventure RPG** set in **Roma Termini Station**, where players must **reach the last train before time runs out** while navigating **hostile police officers and homeless enemies** through **turn-based battles**. The game incorporates **dynamic movement scaling, a turn-based combat system, and an AI-controlled dog companion** to aid the protagonist.

---

## **Story**
The protagonist is a **gangster at Roma Termini Station** whose goal is to **catch the last train before it's too late**. To reach the platform, he must:  
- **Avoid police officers and homeless enemies.**  
- **Engage in turn-based battles.**  
- **Manage time effectively before the train departs.**  

### **Game Conditions**
✅ **Win Condition:** Reach the **train line before the timer reaches zero**.  
❌ **Lose Condition:** If the timer runs out, **the train is missed, and the player loses**.  

---

## **Key Features & Mechanics**

### **1️⃣ Overworld Exploration & Movement Scaling**
- The game world is **based on real-world images** of **Roma Termini Station**, edited into pixel art.  
- **Dynamic scaling of the protagonist's sprite** as they move, creating a **depth effect**.  
- **A second player helper (dog companion)** moves independently and **assists the player**.  

### **2️⃣ Turn-Based Battle System**
- Battles trigger when encountering **police officers or homeless NPCs**.  
- Combat options:  
  - **Attack (Gun Shooting Animation)**  
  - **Beg (Dialogue + Random Outcomes)**  
  - **Seduce (Heart Floating Animation)**  
  - **Steal (Money Flow Animation)**  
- Enemies have **unique attack patterns**, such as:  
  - **Police Baton Attack**  
  - **Drunk NPC "Shit" Attack**  

### **3️⃣ Level Progression & Time-Limited Mission**
- A **countdown timer** determines if the player **reaches the train on time**.  
- If time **runs out**, **the train leaves, and the game is lost**.  

### **4️⃣ Dynamic Difficulty Selection**
Players can **change difficulty before starting**:  
| Mode       | Time Available |  
|------------|--------------|  
| **Normal**  | 180 seconds  |  
| **Hard**    | 120 seconds  |  
| **Speedrun** | 60 seconds  |  

---

## **Game Enhancements & Features**

### **🎮 Core Gameplay**
✅ **Turn-Based Battle System** with enemy-specific **attack patterns & animations**.  
✅ **Player Scaling Mechanics:** Character **dynamically resizes** based on movement.  
✅ **AI-Controlled Dog Companion**: Assists player during battles.  
✅ **Game Timer:** Forces **strategic decision-making** to reach the train on time.  

### **🖼️ Visual & UI Improvements**
✅ **Hand-drawn sprites** for **characters, animations, and battle effects** (created with **LibreSprite**).  
✅ **Pixel-edited backgrounds** made from **real photos of Roma Termini Station** (taken by a friend in Rome).  
✅ **Collision boundaries** prevent movement outside playable areas.  
✅ **Smooth scene transitions** (fade-in intro, battle-to-overworld transition).  

### **🔊 Sound & Animation**
✅ **Background Music:** *Boom Bap Freestyle x Old School Rap Type Beat - Banknotes*  
✅ **Real-world sound effects:** *Rome railway station ambiance, train sounds, police encounters*.  
✅ **Custom Sound Effects:**  
   - **Train departure sounds**  
   - **Gunfire (attack animation)**  
   - **Baton hit (police attack)**  
   - **Drunken NPC attack ("Shit" throw effect)**  
✅ **Multithreading for Background Sound:** **Prevents lagging of the main game thread**.  

### **🕹️ Multiple Controllers & Input Handling**
✅ **Player 1:**  
   - **WASD for movement**  
   - **SPACEBAR to shoot money**  
   - **F for celebration sounds**
     
✅ **Player 2 (Dog Companion):**  
   - **Arrow keys (4856) for movement**  
   - **0 for barking**  
✅ **Turn-Based Combat:** **Button-based inputs for selecting battle actions**.  

---

## **How to Run the Game**

### **🛠 Requirements**
- **Java JDK 17+**  
- **A device with a keyboard for input**
  

### **💾 Running the Game for Noobies**

 - Download the whole zip file into your computer
 - Extract the content
 - Go inside the folder RomaTermini without moving any file inside
 - Double click on RomaTermini.jar to run the game

### **if clicking doesnt run the game:
 - use then the following command in the **terminal (Mac/Linux) or command prompt (Windows)**:

```bash
java -jar RomaTermmi.jar
```


### **🖥️ Running in VS Code**
If you prefer to run the game from VS Code:  
1. Open the **project folder** in VS Code.  
2. Ensure you have **Java Extensions** installed.  
3. Locate the `MainWindow.java` file and **run it**.  

---

## **Development Notes**
- The game follows an **MVC (Model-View-Controller) architecture**:
  - `Model`: **Handles game logic** (battle system, time limits, enemy AI).  
  - `Viewer`: **Manages UI rendering** (overworld, battles, scaling effects).  
  - `MainWindow`: **Controls state transitions** (menu, game over, battle entry).  
- **Game loop is optimized** to maintain a **stable FPS**.  
- **Swing Timers & Threads** used for smooth animation & sound playback.  

---

## **References & Credits**
✅ **Game Assets:** [OpenGameArt.org](https://opengameart.org)  
✅ **Fonts & UI Elements:** Custom + OpenGameArt.org  
✅ **Sound Effects:** Freesound.org, YouTube, real-world recordings  
✅ **Programming Assistance:** ChatGPT 4o (Fading Effects & Multithreading)  

---

## **Final Notes**
**Roma Termini Adventure** is an **engaging RPG-style game** that transforms a **simple shooter into a dynamic adventure with unique mechanics and strategic gameplay**.

🎮 **Key Highlights:**  
✅ **Turn-based combat mechanics**  
✅ **Handmade sprites & animations**  
✅ **Sound mixing & multithreading**  
✅ **AI-powered dog companion**  
✅ **Time-based mission for added urgency**  

---

⚡ **Developed by:** *Jin Giacomo - 24216191*  
📅 **Submission Date:** *March 3rd, 2025*  
