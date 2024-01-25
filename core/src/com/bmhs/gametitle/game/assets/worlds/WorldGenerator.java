package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;
    private int[][] worldIntMap;
    private int waterColor;
    private  int strength;


    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        /*Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
        System.out.println(mapSeed.y + " "+ mapSeed.x);

        worldIntMap[(int)mapSeed.x][(int)mapSeed.y] = 4;

        for(int r = 0;r < worldIntMap.length; r++){
            for(int c = 0; c < worldIntMap[r].length; c++){
                Vector2 tempVector = new Vector2(r,c);
                if(tempVector.dst(mapSeed) < 10){
                    worldIntMap[r][c] = 2;
                }
            }
        }*/

        //call methods to build 2D array
        //randomize();
        iniWater();
        generateIslands();
        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
        generateWorldTextFile();
    }

    public void generateIslands(){
        int iR = MathUtils.random(3,worldIntMap.length - 4);
        int iC = MathUtils.random(3,worldIntMap[0].length - 4);
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
        if(iR > 1 && iR < 99 && iC > 1 && iC < 199) {
            if (/*worldIntMap[iR + 1][iC] < worldIntMap[iR][iC] &&*/ worldIntMap[iR + 1][iC] == waterColor) {
                worldIntMap[iR + 1][iC] = worldIntMap[iR][iC] - 1;
                island(iR + 1, iC, strength - 1);
            }/*
            if (worldIntMap[iR - 1][iC] < worldIntMap[iR][iC] && worldIntMap[iR - 1][iC] == waterColor) {
                worldIntMap[iR - 1][iC] = worldIntMap[iR][iC] - 1;
                island(iR - 1, iC, strength - 1);
            }
            if (worldIntMap[iR][iC + 1] < worldIntMap[iR][iC] && worldIntMap[iR][iC + 1] == waterColor) {
                worldIntMap[iR][iC + 1] = worldIntMap[iR][iC] - 1;
                island(iR, iC + 1, strength - 1);
            }
            if (worldIntMap[iR][iC - 1] < worldIntMap[iR][iC] && worldIntMap[iR][iC - 1] == waterColor) {
                worldIntMap[iR][iC - 1] = worldIntMap[iR][iC] - 1;
                island(iR, iC - 1, strength - 1);
            }*/
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

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.text");
        file.writeString(getWorld3DArrayToString(), false);
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
