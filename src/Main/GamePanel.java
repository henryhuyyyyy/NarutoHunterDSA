package Main;

import Entity.Entity;
import Entity.Player;
import Tiles.TileManager;
import Object.SuperObject;
import PathFinding.PathFinding;
import Monster.MON_Bat;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //World settings 50x30
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 30;

    //FulScreen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //FPS
    int FPS = 60;

    // system
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionCheck cChecker = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public PathFinding pFinder = new PathFinding(this);

    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] bat = new Entity[20];
    public Entity[] slime = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int optionsState = 3;
    public final int gameOverState = 4;
    public final int winState = 5;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setSlime();
        aSetter.setBat();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }
    public void retry(){
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setBat();
        aSetter.setSlime();
    }
    public void restart(){
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setBat();
        aSetter.setSlime();
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run(){
        //"Delta" method for GameLoop
        //60 FPS
        double drawInterval = (double) 1000000000 /FPS; //0.01666s
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                drawToTempScreen(); //G2 draw image to the tempScreen
                drawToScreen(); // draw image from the tempScreen to the screen
                delta--;
            }
        }
    }
    public void update(){
        if(gameState == playState){
            //player
            player.update();
            //bat
            for(int i = 0; i < bat.length; i++){
                if(bat[i] != null){
                    if(bat[i].alive == true && bat[i].dying== false) {
                        bat[i].update();
                    }
                    if(bat[i].alive == false) {
                        bat[i] = null;
                    }
                }
            }
            //slime
            for(int i = 0; i < slime.length; i++){
                if(slime[i] != null){
                    if(slime[i].alive == true && slime[i].dying== false) {
                        slime[i].update();
                    }
                    if(slime[i].alive == false) {
                        slime[i] = null;
                    }
                }
            }
            checkWinCondition();
        }
    }
    public void checkWinCondition(){
        boolean allBatsDead = true;
        boolean allSlimesDead = true;
        // Check all bats
        for (Entity b : bat) {
            if (b != null && !b.dying) {
                allBatsDead = false;
                break;
            }
        }
        // Check all slimes
        for (Entity s : slime) {
            if (s != null && !s.dying) {
                allSlimesDead = false;
                break;
            }
        }
        // If all bats and slimes are dead, change the game state to win state
        if (allBatsDead && allSlimesDead) {
            gameState = winState;
        }
    }
    public void drawToTempScreen() {
        // title screen
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // others
        else {
            // tile
            tileM.draw(g2);
            // object
            entityList.add(player);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            //bat
            for (int i = 0; i < bat.length; i++) {
                if (bat[i] != null) {
                    bat[i].draw(g2);
                }
            }
            //monster
            for (int i = 0; i < slime.length; i++) {
                if (slime[i] != null) {
                    slime[i].draw(g2);
                }
            }
            // player
            player.draw(g2);
            //Draw Entity List
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //Empty entity list
            entityList.clear();
            // UI
            ui.draw(g2);
        }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }
}
