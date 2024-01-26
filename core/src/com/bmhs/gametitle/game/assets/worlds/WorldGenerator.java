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
    private int strength;
    private int defaultColor;
    int islandNumber;


    public WorldGenerator(int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        iniWorld();
        generateIslands();
        iniWater();
        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
        generateWorldTextFile();
    }

    public void generateIslands() {
        strength = 11;
        islandNumber = MathUtils.random(3,9);
        for (int i = 0; i < islandNumber; i++) {
            int iR = MathUtils.random(3, worldIntMap.length - 4);
            int iC = MathUtils.random(3, worldIntMap[0].length - 4);
            worldIntMap[iR][iC] = strength;
            island(iR, iC, strength);
        }
    }

    public void island(int iR, int iC, int strength) {
        if (strength > 6) {
            recolor(iR, iC);
        }
    }

    public void recolor(int iR, int iC) {
        double strChance = MathUtils.random(1);

        if (iR > 1 && iR < 99 && iC > 1 && iC < 199) {
            if (worldIntMap[iR + 1][iC] < worldIntMap[iR][iC]) {
                System.out.println(worldIntMap[iR][iC]);
                worldIntMap[iR + 1][iC] = worldIntMap[iR][iC] - 1;
                if (strChance <= 0.5) {
                    island(iR + 1, iC, strength);
                } else if (strChance >= 0.8 || strength != 11) {
                    island(iR + 1, iC, strength + 1);
                } else {
                    island(iR + 1, iC, strength - 1);
                }
            }
            if (worldIntMap[iR - 1][iC] < worldIntMap[iR][iC]) {
                worldIntMap[iR - 1][iC] = worldIntMap[iR][iC] - 1;
                if (strChance <= 0.5) {
                    island(iR - 1, iC, strength);
                } else if (strChance >= 0.8 || strength != 11) {
                    island(iR - 1, iC, strength + 1);
                } else {
                    island(iR - 1, iC, strength - 1);
                }
            }
            if (worldIntMap[iR][iC + 1] < worldIntMap[iR][iC]) {
                worldIntMap[iR][iC + 1] = worldIntMap[iR][iC] - 1;
                if (strChance <= 0.5) {
                    island(iR, iC + 1, strength);
                } else if (strChance >= 0.8 || strength != 11) {
                    island(iR, iC + 1, strength + 1);
                } else {
                    island(iR, iC + 1, strength - 1);
                }
            }
            if (worldIntMap[iR][iC - 1] < worldIntMap[iR][iC]) {
                worldIntMap[iR][iC - 1] = worldIntMap[iR][iC] - 1;
                if (strChance <= 0.5) {
                    island(iR, iC - 1, strength);
                } else if (strChance >= 0.9 || strength != 11) {
                    island(iR, iC - 1, strength + 1);
                } else {
                    island(iR, iC - 1, strength - 1);
                }
            }

        }


    }

    public void iniWorld() {
        defaultColor = 0;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = defaultColor;
            }
        }
    }

    public void iniWater() {
        waterColor = 19;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == defaultColor) {
                    worldIntMap[r][c] = waterColor;
                }
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


        public void randomize () {
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size - 1);
                }
            }
        }

        public WorldTile[][] generateWorld() {
            WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
                }
            }
            return worldTileMap;
        }

    }


