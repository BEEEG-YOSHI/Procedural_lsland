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
    private int waterColor1;
    private int waterColor2;
    private int strength;
    private static int seed = 11;
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
        islandNumber = MathUtils.random(3,9);
        for (int i = 0; i < islandNumber; i++) {
            strength = seed;
            int iR = MathUtils.random(3, worldIntMap.length - 4);
            int iC = MathUtils.random(3, worldIntMap[0].length - 4);
            worldIntMap[iR][iC] = strength;
            island(iR, iC, strength);
        }
    }

    public void generateFlora(){
        waterColor1 = 19;
        waterColor2 = 20;
        seed = 11;
        int minStr = 7;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == defaultColor || worldIntMap[r][c] < minStr || worldIntMap[r][c] > seed) {
                    if (Math.random() > 0.5) {
                        worldIntMap[r][c] = waterColor1;
                    } else {
                        worldIntMap[r][c] = waterColor2;
                    }
                }
            }
        }
    }

    public void island(int iR, int iC, int str) {
        this.strength = str;
        if (strength > 6) {
            recolor(iR, iC);
        }
    }

    public void recolor(int iR, int iC) {
        double rand = Math.random();

        if (iR > 1 && iR < 99 && iC > 1 && iC < 199) {
            if (worldIntMap[iR + 1][iC] < worldIntMap[iR][iC] && worldIntMap[iR][iC] >= 8) {
                if (rand <= 0.4) {
                    worldIntMap[iR + 1][iC] = worldIntMap[iR][iC];
                } else if (rand >= 0.9 && strength != 11) {
                    worldIntMap[iR + 1][iC] = worldIntMap[iR][iC] + 1;
                } else {
                    worldIntMap[iR + 1][iC] = worldIntMap[iR][iC] - 1;
                }
                island(iR + 1, iC, strength);
            }

            if (worldIntMap[iR - 1][iC] < worldIntMap[iR][iC] && worldIntMap[iR][iC] >= 8) {
                if (rand <= 0.4) {
                    worldIntMap[iR - 1][iC] = worldIntMap[iR][iC];
                } else if (rand >= 0.9 && strength != 11) {
                    worldIntMap[iR - 1][iC] = worldIntMap[iR][iC] + 1;
                } else {
                    worldIntMap[iR - 1][iC] = worldIntMap[iR][iC] - 1;
                }
                island(iR - 1, iC, strength);
            }


            if (worldIntMap[iR][iC + 1] < worldIntMap[iR][iC] && worldIntMap[iR][iC] >= 8) {
                if (rand <= 0.3) {
                    worldIntMap[iR][iC + 1] = worldIntMap[iR][iC];
                } else if (rand >= 0.9 && strength != 11) {
                    worldIntMap[iR][iC + 1] = worldIntMap[iR][iC] + 1;
                } else {
                    worldIntMap[iR][iC + 1] = worldIntMap[iR][iC] - 1;
                }
                island(iR, iC + 1, strength);
            }
            if (worldIntMap[iR][iC - 1] < worldIntMap[iR][iC] && worldIntMap[iR][iC] >= 8) {
                if (rand <= 0.3) {
                    worldIntMap[iR][iC - 1] = worldIntMap[iR][iC];
                } else if (rand >= 0.9 && strength != 11) {
                    worldIntMap[iR][iC - 1] = worldIntMap[iR][iC] + 1;
                } else {
                    worldIntMap[iR][iC - 1] = worldIntMap[iR][iC] - 1;
                }
                island(iR, iC - 1, strength);
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
        waterColor1 = 19;
        waterColor2 = 20;
        seed = 11;
        int minStr = 7;

        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == defaultColor || worldIntMap[r][c] < minStr || worldIntMap[r][c] > seed) {
                    if (Math.random() > 0.5) {
                        worldIntMap[r][c] = waterColor1;
                    } else {
                        worldIntMap[r][c] = waterColor2;
                    }
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


