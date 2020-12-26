package byog.Core;

import byog.Core.Position;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {
    private static final int maxRoomWidth = 6; //Limiting size of room in order to achieve more reasonable layout
    private static final int maxRoomHeight = 6;

    private int width = 60; // WIDTH and HEIGHT represent canvas's width and height
    private  int height = 40;
    private byog.Core.Position doorPosition; // Position of door(Exit) ???
    private Random random;
    private TETile[][] world;

    /**
     * Returns WorldGenerator object with random seed specified
     *
     * @param w        width of generated world
     * @param h        height of generated world
     * @param initialX x coordinate of initial LOCKED_DOOR
     * @param initialY y coordinate of initial LOCKED_DOOR
     * @param seed     random seed used to generate world
     */
    public WorldGenerator(int w, int h, int initialX, int initialY, long seed) {
        this.width = w;
        this.height = h;
        this.doorPosition = new Position(initialX, initialY);
        random = new Random(seed);
    }

    // Initializes a world filling everything with NOTHING
    private void initialize() {
        world = new TETile[width][height];
        for (int w = 0; w < width; w += 1) {
            for (int h = 0; h < height; h += 1) {
                world[w][h] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Room Generator object
     * 思路：房间是由格子组成的，因此边缘的格子就是 wall。在利用 TETile 对象渲染每个格子时，遇到这些位于边缘的格子，就可以使用不同的
     *      TETile 对象，从而产生 wall。利用房间左下角和右上角的格子的横纵坐标，可以判断是否为所谓的“边缘”格子。
     *      利用随机产生的 leftBottom 和 rightTop ，从而产生随机的 room
     * @param leftBottom    location of room's left-bottom
     * @param rightTop      location of room's right-top
     */
    private void Room(Position leftBottom, Position rightTop) {
        // most left-bottom gird‘s x coordinate in the room
        for (int x = leftBottom.x; x <= rightTop.x; x++) {
            for (int y = leftBottom.y; y <= rightTop.y; y++) {
                // 是否是“边缘”的格子
                if (x == leftBottom.x || x == rightTop.x || y == leftBottom.y || y == rightTop.y) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }


    /**
     * Door Generator object
     * 思路：门在最南边的墙上，随机产生 doorPosition,从而确定门的位置
     * @param  doorPosition   location of room's left-bottom
     */
    private void Door(Position doorPosition) {
        world[doorPosition.x][doorPosition.y] = Tileset.LOCKED_DOOR;
    }
}
