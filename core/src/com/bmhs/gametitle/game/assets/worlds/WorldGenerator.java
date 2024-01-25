package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.MathUtils;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;
    private int temp;
    private int[][] worldIntMap;
    private int waterColor;
    private  int strength;


    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        //call methods to build 2D array
        //randomize();
        iniWater();
        border();
        generateIslands();
        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }

    public void generateIslands(){
        int iR = MathUtils.random(3,96);
        int iC = MathUtils.random(3,196);
        strength = 9;
        //int strength = MathUtils.random();
        worldIntMap[iR][iC] = strength;
        island(iR,iC,strength);

    }

    public void island(int iR, int iC, int strength){
        if(strength > 5){
            recolor(iR,iC);
        }
    }

    public void recolor(int iR, int iC){
        if(worldIntMap[iR+1][iC] > worldIntMap[iR][iC] || worldIntMap[iR+1][iC] == waterColor){
            worldIntMap[iR+1][iC] = worldIntMap[iR][iC]-1;
            island(iR+1,iC,strength-1);
        }
        if(worldIntMap[iR-1][iC] > worldIntMap[iR][iC] || worldIntMap[iR-1][iC] == waterColor){
            worldIntMap[iR-1][iC] = worldIntMap[iR][iC]-1;
            island(iR-1,iC,strength-1);
        }
        if(worldIntMap[iR][iC+1] > worldIntMap[iR][iC] || worldIntMap[iR][iC+1] == waterColor){
            worldIntMap[iR][iC+1] = worldIntMap[iR][iC]-1;
            island(iR,iC+1,strength-1);
        }
        if(worldIntMap[iR][iC-1] > worldIntMap[iR][iC] || worldIntMap[iR][iC-1] == waterColor){
            worldIntMap[iR][iC-1] = worldIntMap[iR][iC]-1;
            island(iR,iC-1,strength-1);
        }
    }

    public void iniWater(){
        waterColor = 19;

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++){
                worldIntMap[r][c] = waterColor;
            }

        }
    }

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void border() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++){
                if(c < 2 || c > worldIntMap[r].length - 3 || r < 2 || r > worldIntMap.length - 3) {
                    worldIntMap[r][c] = 14;
                }
            }

        }
    }

    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

}
