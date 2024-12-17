package Main;

import Monster.MON_Bat;
import Monster.MON_GreenSlime;
public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp ;
    }
    public void setObject(){}
    public void setBat(){
        gp.bat[0] = new MON_Bat(gp);
        gp.bat[0].worldX = 31 * gp.tileSize;
        gp.bat[0].worldY = 9 * gp.tileSize;

//        gp.bat[1] = new MON_Bat(gp);
//        gp.bat[1].worldX = 14 * gp.tileSize;
//        gp.bat[1].worldY = 23 * gp.tileSize;

    }
    public void setSlime(){
        gp.slime[0] = new MON_GreenSlime(gp);
        gp.slime[0].worldX = 14 * gp.tileSize;
        gp.slime[0].worldY = 9 * gp.tileSize;

//        gp.slime[1] = new MON_GreenSlime(gp);
//        gp.slime[1].worldX = 31 * gp.tileSize;
//        gp.slime[1].worldY = 16 * gp.tileSize;
//
//        gp.slime[2] = new MON_GreenSlime(gp);
//        gp.slime[2].worldX = 31 * gp.tileSize;
//        gp.slime[2].worldY = 23 * gp.tileSize;
    }
}
