# **Roma Termini Primo Nero Adventure**

This is my **Java game project** for my game development class. Just finished.

## **Project Structure**
- **Model** → Handles game logic.
- **Viewer** → Manages UI.
- **MainWindow** → Controls high-level state transitions.
- **battle.java** → Currently not used.

---

## **Development Roadmap**

### **SPRINT 1 – Core Battle System**
- **Implement `drawBattleScreen()`**
  - Buttons linked to Model (✅ Done)
  - Battle animations (✅ Done)

- **Implement `battleIsOver()`** (✅ Done)
- **Implement turn-based battle system** (✅ Done)
  - Enemy attacks (✅ Done)
  - Player actions:
    - Attack (✅ Done)
    - Beg (✅ Done)
    - Seduce (✅ Done)
    - Steal (✅ Done)

- **Battle Animations (via Model)**
  - Attack: Gun shooting (✅ Done)
  - Seduce: Floating heart (✅ Done)
  - Steal: Money flow (✅ Done)
  - Police Attack: Baton strike (✅ Done)
  - Drunk Attack: Shit throw (✅ Done)
  - Boss (Black Mob): Gun shooting (⏳ Later)

- **Implemented inactivity timer for enemy animations**

Finished at **5:20 AM**.

---

### **SPRINT 2 – Expanding the World**
- **New Features**
  - Second player (dog)
    - Roams the map and barks.
    - Buffs player’s attack or health if barked enough.

- **Audio Enhancements**
  - Sound effects: "Primo Nero" voice lines, background music.
  - Enemy battle sounds.

- **Game Features**
  - Difficulty selection at start.

- **UI Improvements**
  - Prettier buttons, health bar, and text.
  - Add Black Mob enemy.

- **GameOver Screen**
  - Functional but needs polish (restart + exit buttons).

---

### **SPRINT 3 – Finishing the Game**
- **Final Graphics & Gameplay**
  - Detailed maps, design Roma Termini background pixel style
  - Endgame mechanics:
    - Earn enough points before the train departs.
    - Use points to buy a ticket and escape Roma Termini.

---

### **Final Steps – Cleanup & Submission**
- Document copied assets.
- Record gameplay video.
- Clean up the code.

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
- **JAR file execution capability**  
- **A device with a keyboard for input**  

### **💾 Running the Game from the JAR File**
To play the game, use the following command in the **terminal (Mac/Linux) or command prompt (Windows)**:  

```bash
java -jar RomaTermmi.jar
```

If double-clicking the **JAR file** does not work, ensure that **Java is installed and set as the default program for .jar files**.  

### **🖥️ Running in VS Code**
If you prefer to run the game from VS Code:  
1. Open the **project folder** in VS Code.  
2. Ensure you have **Java Extensions** installed.  
3. Locate the `MainWindow.java` file and **run it**.  

---

## **How to Create an Executable JAR**
To package the game into a runnable **JAR file**, follow these steps:

1. Compile the project:
   ```bash
   javac -d bin -cp src src/MainWindow.java
   ```
2. Navigate to the `bin` directory:
   ```bash
   cd bin
   ```
3. Create the JAR file:
   ```bash
   jar cfe RomaTerminiAdventure.jar MainWindow *
   ```
4. Run the game using:
   ```bash
   java -jar RomaTerminiAdventure.jar
   ```

If using **VS Code**, you can use the **Java Extension Pack** to build the JAR file via the **Export Runnable JAR** option.

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
