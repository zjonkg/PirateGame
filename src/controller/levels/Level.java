package controller.levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.entities.Crabby;
import view.main.Game;
import model.objects.Candle;
import model.objects.CandleLight;
import model.objects.GameContainer;
import model.objects.LargeChain;
import model.objects.Potion;
import model.objects.SmallChain;
import model.objects.Spike;
import model.utils.HelpMethods;
import static model.utils.HelpMethods.GetLevelData;
import static model.utils.HelpMethods.GetCrabs;
import static model.utils.HelpMethods.GetPlayerSpawn;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabs;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;
    private ArrayList<Candle> candle;
    private ArrayList<CandleLight> candleLight;
    private ArrayList<SmallChain> smallChain;
    private ArrayList<LargeChain> largeChain;
    private ArrayList<GameContainer> containers;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        createSmallChains();
        createLargeChains();
        createCandle();
        createCandleLight();
        calcLvlOffsets();
        calcPlayerSpawn();

    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void createCandle() {
        candle = HelpMethods.GetCandle(img);
    }

    private void createCandleLight() {
        candleLight = HelpMethods.GetCandleLight(img);
    }

    private void createSmallChains() {
        smallChain = HelpMethods.GetSmallChains(img);
    }

    private void createLargeChains() {
        largeChain = HelpMethods.GetLargeChains(img);
    }

    private void createContainers() {
        containers = HelpMethods.GetContainers(img);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotions(img);
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<SmallChain> getSmallChain() {
        return smallChain;
    }

    public ArrayList<LargeChain> getLargeChain() {
        return largeChain;
    }

    public ArrayList<Candle> getCandle() {
        return candle;
    }

    public ArrayList<CandleLight> getCandleLight() {
        return candleLight;
    }

}
