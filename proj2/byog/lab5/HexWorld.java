package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final long SEED = 42;
    private static final Random RANDOM = new Random(SEED);

    protected static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     *
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     * xxxx
     * xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     * xxxxxx
     * xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
     * Adds a row of the same tile.
     *
     * @param world the world to draw on
     * @param p     the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t     the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     *
     * @param world the world to draw on
     * @param p     the bottom left coordinate of the hexagon
     * @param s     the size of the hexagon
     * @param t     the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }

    /**
     * Picks a RANDOM tile
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.WATER;
            case 4:
                return Tileset.SAND;
            default:
                return Tileset.NOTHING;
        }
    }

    /**
     * Initialize tiles
     *
     * @param world the world to draw on
     */
    private static void fillNOTHING(TETile[][] world) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Compute the bottom-right Position of a current hexagon’s neighbors
     *
     * @param currentPos the position of the current hexagon
     * @param size       size of hexagon
     */
    public static Position bottomRight(Position currentPos, int size) {
        int offset = (hexRowWidth(size, size) - size) / 2;
        int bottomRightPosX = currentPos.x + size + offset;
        int bottomRightPosY = currentPos.y - size;
        return new Position(bottomRightPosX, bottomRightPosY);
    }

    /**
     * Compute the top-right Position of a current hexagon’s neighbors
     *
     * @param currentPos the position of the current hexagon
     * @param size       size of hexagon
     */
    public static Position topRight(Position currentPos, int size) {
        int offset = (hexRowWidth(size, size) - size) / 2;
        int bottomRightPosX = currentPos.x + size + offset;
        int bottomRightPosY = currentPos.y + size;
        return new Position(bottomRightPosX, bottomRightPosY);
    }

    /**
     * Compute the number of hexagon according to hexagon's size
     *
     * @param s size of hexagon
     */
    public static int columnNum(int s) {
        int columnNum = s + 2 * (s - 1);
        return columnNum;
    }

    /**
     * Draw a column of hexagons from top to bottom
     *
     * @param hexagonNum number of hexagon in a column
     * @param p          top hexagon's left-bottom position
     * @param size       size of hexagon
     */
    public static void drawRandomVerticalHexes(int hexagonNum, Position p, int size, TETile[][] world) {
        for (int i = 0; i < hexagonNum; i++) {
            addHexagon(world, p, size, randomTile());
            p.y = p.y - 2 * size;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        // p specifies the lower left corner of the hexagon

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];

        // initialize tiles
        fillNOTHING(hexWorld);

        // draw a hexagon
//        addHexagon(hexWorld, new Position(2,10), 3, randomTile());
//        addHexagon(hexWorld, new Position(10,20), 3, randomTile());
//        drawRandomVerticalHexes(3, new Position(10, 20), 3, hexWorld);
        int size = 3;
        Position p = new Position(60, 60);
        for (int i = 0; i < 2; i++) {
            drawRandomVerticalHexes(2, p, size, hexWorld);
            p = topRight(p, size);
        }

        ter.renderFrame(hexWorld);
    }
}
