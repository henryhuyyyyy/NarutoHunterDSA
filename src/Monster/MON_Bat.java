package Monster;

import java.util.Random;

import Entity.Entity;
import Main.GamePanel;

public class MON_Bat extends Entity {
    public MON_Bat(GamePanel gp) {
        super(gp);
        type = 1;
        direction = "down";
        name = "bat";
        speed = 3;
        maxLife = 2;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getBatImage();
    }
    public void getBatImage(){
        up1 = setup("/Picture/bat/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Picture/bat/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup ("/Picture/bat/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Picture/bat/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Picture/bat/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Picture/bat/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Picture/bat/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Picture/bat/bat_down_2", gp.tileSize, gp.tileSize);
    }
    @Override
    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        if(onPath == false && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if(i > 50){
                onPath = true;
            }
        }
    }

    @Override
    public void setAction(){
        if(onPath == true){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol,goalRow);
        }
        else{
            actionLockCounter++;
            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    @Override
    public void dameReact(){
        actionLockCounter = 0;
        onPath = true;
    }
}
