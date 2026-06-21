# 🐍 Multiplayer Snake Game

**Discipline:** SCC0504 - Object-Oriented Programming  
**Technology:** Java + LibGDX (Desktop Target)

## 👥 Group Identification
| Name | NUSP |
| :--- | :--- |
| Natália Carvalho | 15497232 |
| Nina Cunha Pinheiro | 13686500 |
| Thiago Ramos Pinto Silva | 14609446 |

---

## 1. Requirements
The project fulfills all the core requirements proposed in the assignment description:
* **Game Setup:** LibGDX desktop project featuring a Main Menu, Instructions screen, and dynamic High Score screens.
* **Core Mechanics:** Two snakes move on a grid. They grow longer and speed up slightly when eating randomly spawned food.
* **Wrap-around:** Snakes exiting one side of the screen re-enter from the opposite side.
* **Collision Detection:** A snake dies if it hits its own body or the opponent's body.
* **Two-Player Controls:** Player 1 uses Arrow Keys; Player 2 uses WASD.
* **Scoring & End Conditions:** The game keeps track of the score (food eaten), displays it in real-time, and ends when a collision happens, showing the winner.
* **Sound Effects:** Asynchronous audio integration for collecting food and game-over (death) events.
* **Data Persistence:** The top 5 high scores are serialized and saved locally to `highscores.dat`.

**Extra/Custom Requirements Implemented:**
* **Retro Aesthetics:** Implementation of a strict 4-color GameBoy palette managed by a custom `ColorManager`.
* **Arcade Name Input:** The user can input a 3-letter string to save their record, mimicking classic arcade machines.

---

## 2. Project Description
The application is structured using a Screen-based architecture provided by LibGDX, separating the game states (Menus, Gameplay, Game Over) into distinct classes. 

* **The Core Game Loop (`GameScreen` & `GameBoard`):** The `GameBoard` handles the real-time update loop (`delta` time). It manages the exact matrix positions of the snakes and the food. 
* **Entities (`Snake` & `Food`):** These classes act as pure Data/Logic models. The `Snake` class uses a `LinkedList<Vector2>` to track its body parts and calculate the next movement vector.
* **Visuals & Audio:** `SnakeGameMain` holds the global `SpriteBatch` and `ShapeRenderer`. The rendering logic loops through the entity coordinates and draws the corresponding pixel-art rectangles.

**High-Level Architecture Diagram (Textual Representation):**

    [SnakeGameMain (Global Resources)]
           |---> [MenuScreen] ---> [InstructionsScreen] / [HighScoreScreen]
           |---> [GameScreen] ---> Uses [GameBoard] (Logic Hub)
                                      |---> [Snake] (P1 & P2)
                                      |---> [Food] (Collectible)
                                      |---> [SoundManager] (Audio)
           |---> [GameOverScreen] ---> [NewHighScoreScreen] ---> [ScoreManager] (I/O)


---

## 3. Comments About the Code
The project was built emphasizing **Object-Oriented Programming (OOP) best practices**, specifically focusing on High Cohesion and Low Coupling:
* **Encapsulation & Single Responsibility:** Entities like `Snake` and `Food` have no graphical dependencies (no LibGDX rendering code inside them). They only handle mathematical coordinates. `GameBoard` is the only class responsible for drawing them.
* **Manager Pattern:** Peripheral logics were decoupled into managers. `ScoreManager` abstracts all file I/O operations (Java Serialization), and `SoundManager` encapsulates audio loading and playback.
* **Code Documentation:** All core and logic classes contain extensive **Javadoc** comments explaining their behavior, parameters, and return types to aid future reviewers.

---

## 4. Test Plan
We adopted a hybrid testing strategy, combining automated unit testing for business logic and manual testing for visual/audio rendering.

**A. Automated Unit Tests (JUnit 5):**
We implemented an extensive suite of automated tests (`SnakeTest`, `FoodTest`, `ScoreManagerTest`, `HighScoreEntryTest`) testing isolated components:
* *Movement & Growth:* Verify if the snake grows correctly and prevents 180-degree immediate reverse turns.
* *Boundaries:* Ensure the `Food` class never spawns coordinates out of the grid or overlapping a snake's body.
* *Data Persistence:* Verify if `ScoreManager` correctly sorts descending scores, trims the list to 5 items, and persists data across mock application restarts.

**B. Manual UI/UX Tests:**
* Play a full match testing simultaneous keyboard inputs (P1 and P2 moving at the exact same frame).
* Force a head-to-head collision to verify the "Tie" scenario.
* Navigate through all screens to check for memory leaks (verifying if `dispose()` is called).
* Submit a new high score, close the game, reopen it, and check the High Scores screen.

---

## 5. Test Results
* **Automated Tests:** `BUILD SUCCESSFUL`. All JUnit 5 tests passed with 100% success rate. The headless LibGDX extension successfully allowed mathematical testing without opening a graphical window.
* **Manual Tests:** * The game speed correctly increments up to a predefined limit.
    * Sounds play smoothly without freezing the UI thread.
    * Wrap-around walls work flawlessly on all 4 borders.
    * Top 5 Serialization accurately ignores lower scores and properly saves 3-letter inputs.

---

## 6. Build Procedures
To build and run this project, you do not need any external IDE configuration. The project uses Gradle wrapper, making it plug-and-play.

**Prerequisites:**
* Java Development Kit (JDK) 17 or higher installed.
* `JAVA_HOME` environment variable configured.

**Step-by-Step Guide:**
1. Clone the repository or extract the ZIP file:
   `git clone <repository_url>`
   `cd <project_folder>`
2. **To Run the Game:** Open your terminal in the root folder and execute:
   * **Linux/Mac:** `./gradlew run`
   * **Windows:** `gradlew.bat run`
3. **To Run the Automated Tests:** In the same terminal, execute:
   * **Linux/Mac:** `./gradlew test`
   * **Windows:** `gradlew.bat test`

*(Note: On the first run, Gradle will automatically download the LibGDX framework and JUnit dependencies. An internet connection is required).*

---

## 7. Problems
During development, we faced and solved a few notable challenges:
1. **Gradle Source Sets:** Initially, Gradle attempted to compile our JUnit test files into the main desktop artifact, causing build failures. We solved this by properly configuring the `sourceSets` in `build.gradle` to isolate `main` and `test` directories.
2. **Asset Loading:** When running the game via certain IDEs, the working directory couldn't find the `assets/` folder. We solved this by explicitly declaring the `resources` directory in the Gradle configuration.
3. **UI Freezing on Game Over:** We originally used `Thread.sleep()` to wait for the death sound effect to finish before changing screens. This caused the LibGDX render thread to freeze. We fixed it by leveraging the asynchronous nature of `Sound.play()` and instantly transitioning the screen.

---

## 8. Comments
Developing this project was an excellent opportunity to apply OOP principles in a real-time environment. Handling state management, learning how the LibGDX continuous rendering loop works, and setting up automated testing in a graphical application were the most rewarding challenges for our group.