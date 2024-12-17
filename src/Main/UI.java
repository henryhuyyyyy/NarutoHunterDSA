package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Object.OBJ_Heart;
import Object.SuperObject;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public int commandNum = 0;
    int subState = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // Create hub object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.setColor(Color.white);

        //title state
        if(gp.gameState == gp.titleState){drawTitleScreen();}
        //play state
        if(gp.gameState == gp.playState){drawPlayerLife();}
        //pause state
        if(gp.gameState == gp.pauseState){drawPlayerLife();drawPauseScreen();}
        //Option State
//        if(gp.gameState == gp.optionsState){drawOptionsScreen();}
        //Game Over State
        if(gp.gameState == gp.gameOverState){drawGameOverScreen();}
        //End game state
        if(gp.gameState == gp.winState){drawWinScreen();}
    }
    public void drawPlayerLife(){
        // gp.player.life = 3; //use to check
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // Draw max life
        while (i< gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        //Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw current life
        while (i< gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i< gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
                i++;
                x += gp.tileSize;
            }
        }
    }
    public void drawTitleScreen(){
        g2.setColor(new Color (0, 0, 0) ) ;
        g2.fillRect (0, 0, gp. screenWidth, gp. screenHeight) ;

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        String text = "Last Time Adventure";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x+5, y+5);
        //Main color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //char image
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1,x,y,gp.tileSize*2,gp.tileSize*2,null);

        //Menu
        g2.setFont (g2.getFont().deriveFont(Font.BOLD,40F ));
        text ="NEW GAME";
        x = getXforCenteredText (text) ;
        y += gp.tileSize*4;
        g2.drawString (text, x, y) ;
        if(commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenteredText (text) ;
        y+= gp.tileSize;
        g2.drawString (text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight/2;
        g2.drawString(text, x , y );
    }
//    public void drawOptionsScreen(){
//        g2.setColor(Color.white);
//        g2.setFont(g2.getFont().deriveFont(32f));
//
//        //Sub window
//        int frameX = gp.tileSize*6;
//        int frameY = gp.tileSize;
//        int frameWidth = gp.tileSize*8;
//        int frameHeight = gp.tileSize*10;
//        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
//
//        switch (subState){
    ////            case 0: options_top(frameX, frameY); break;
//            case 1:  break;
//            case 2:  break;
//        }
//        gp.keyH.enterPressed = false;
//    }
    public void drawGameOverScreen(){

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //Main
        g2.setColor(Color.WHITE);
        g2.drawString(text,x - 4,y - 4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">",x - 40,y);
        }

        //Back to title
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">",x - 40,y);
        }

    }
    public void drawWinScreen(){

        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text, text2, text3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Winner!!";
        text2 = "Nguyen Binh Phuong Huy - ITCSIU21189";
        text3 = "Phan Nguyen Khanh Nguyen - ITCSIU21209";

        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text,x,y);
        //Main
        g2.setColor(Color.WHITE);
        g2.drawString(text,x - 4,y - 4);

//        // name2
//        g2.setColor(Color.WHITE);
//        g2.drawString(text2,x - 4,y - 10);
//
//        // name3
//        g2.setColor(Color.WHITE);
//        g2.drawString(text3,x - 4,y - 12);

        //Back to title
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Back To Title Screen";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum == 0){
            g2.drawString(">",x - 40,y);
        }

    }
    //    public void options_top(int frameX, int frameY){
//        int textX;
//        int textY;
//
//        //Title
//        String text = "Options";
//        textX = getXforCenteredText(text);
//        textY = frameY + gp.tileSize;
//        g2.drawString(text,textX,textY);
//
//        //Music
//        textX = frameX + gp.tileSize;
//        textY += gp.tileSize*2;
//        //textY += gp.tileSize;
//        g2.drawString("Music", textX, textY);
//        if(commandNum == 0){
//            g2.drawString(">",textX - 25,textY);
//        }
//
//        //SE
//        textY += gp.tileSize;
//        g2.drawString("SE", textX, textY);
//        if(commandNum == 1){
//            g2.drawString(">",textX - 25,textY);
//        }
//
//        //End Game
//        textY += gp.tileSize;
//        g2.drawString("End Game", textX, textY);
//        if(commandNum == 2){
//            g2.drawString(">",textX - 25,textY);
//            if(gp.keyH.enterPressed == true){
//                gp.playSE(7);
//                subState = 0;
//                gp.gameState = gp.titleState;
//                gp.stopMusic();
//                gp.playMusic(0);
//                gp.restart();
//            }
//        }
//
//        //Resume
//        textY += gp.tileSize*2;
//        g2.drawString("Resume Game", textX, textY);
//        if(commandNum == 3){
//            g2.drawString(">",textX - 25,textY);
//            if(gp.keyH.enterPressed == true){
//                gp.gameState = gp.playState;
//                commandNum = 0;
//            }
//        }
//
//        //Music Volume
//        textX = frameX + (int)(gp.tileSize*4.5);
//        textY = frameY + gp.tileSize*2 + 24;
//        g2.setStroke(new BasicStroke(3));
//        g2.drawRect(textX,textY,120,24);
//        int volumeWidth = 24 * gp.music.volumeScale;
//        g2.fillRect(textX,textY,volumeWidth,24);
//
//        //SE Volume
//        textY += gp.tileSize;
//        g2.drawRect(textX,textY,120,24);
//        volumeWidth = 24 * gp.se.volumeScale;
//        g2.fillRect(textX,textY,volumeWidth,24);
//
//        gp.config.saveConfig();
//    }
    // make game full screen
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
