package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private static final int maxRoomWidth = 6; //Limiting size of room in order to achieve more reasonable layout
    private static final int maxRoomHeight = 6;
    private static final int N = 800; // 不去重之前随机构造N + RANDOM.nextInt(50) 个房间

    private Position leftBottom;
    private int widthRoom;
    private int heightRoom;


    public Room(Position leftBottom, int widthRoom, int heightRoom) {
        this.leftBottom = leftBottom;
        this.widthRoom = widthRoom;
        this.heightRoom = heightRoom;
    }

    /** 根据房间的左下角位置计算其他三个点的位置
     *  *1 . . . *2
     *  .        .
     *  .        .
     *  .        .
     *  *0 . . . *3
     */
    private Position[] cornerPosition() {
        Position[] p = new Position[4];
        p[0] = leftBottom;
        p[1] = new Position(leftBottom.x, leftBottom.y + heightRoom - 1);
        p[2] = new Position(leftBottom.x + widthRoom - 1, leftBottom.y + heightRoom - 1);
        p[3] = new Position(leftBottom.x + widthRoom - 1, leftBottom.y);
        return p;
    }

    // 判断一个点是否在房间内
    private boolean containPosition(Position p) {
        return p.x >= leftBottom.x && p.x <= leftBottom.x + widthRoom - 1 && p.y >= leftBottom.y && p.y <= leftBottom.y + heightRoom - 1;
    }


    // 判断两个房间是否重叠
    // 思路：两个房间各自的四个角的位置是否在对方的房间里
    private boolean isOverlap(Room room) {
        for (int i = 0; i < 4; i++) {
            Position[] p = room.cornerPosition();
            if (this.containPosition(p[i])) {
                return true;
            }
        }

        for (int i = 0; i < 4; i++) {
            Position[] p = this.cornerPosition();
            if (room.containPosition(p[i])) {
                return true;
            }
        }
        return false;
    }

    // 随机产生room的参数，leftBottom，width，height
    private static Room randomRoom(TETile[][] world) {
        // The distance between edge and position will over 3,
        // in case of one TETile side length.
        Random RANDOM = new Random();
        int xP = RANDOM.nextInt((int) (0.5 * (world.length - 2))) * 2;
        int yP = RANDOM.nextInt((int) (0.5 * (world[0].length - 2))) * 2;
        Position p = new Position(xP, yP);

        // Height and width are among {4, 6, 8}
        int height = (3 + RANDOM.nextInt(2)) * 2 - 1;
        int width = (3 + RANDOM.nextInt(2)) * 2 - 1;

        if (xP + width <= world.length && yP + height <= world[0].length) {
            return new Room(p, width, height);
        } else {
             return randomRoom(world);
        }
    }

    // 利用Arraylist将随机生成所有房间信息存储起来,不去重
    private static List<Room> generateRoomInfo(TETile[][] world) {
        List<Room> rooms = new ArrayList<>();
        Random RANDOM = new Random();
        int randomN = Room.N + RANDOM.nextInt(50);
        for (int i = 0; i < randomN; i++) {
            rooms.add(randomRoom(world));
        }
        return rooms;
    }

    // 对List中存储的房间进行去重,遍历比较即可
    private static List<Room> deletedOverlapRooms(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).isOverlap(rooms.get(j))) {
                    rooms.remove(j);
                    j--;
                }
            }
        }
        return rooms;
    }

    // 根据Room对象利用TETile Engine渲染出一个room效果
    private static void printRoom(Room room, TETile[][] world) {
        // 计算出房间的右上角位置
        Position rightTop = room.cornerPosition()[2];
        for (int x = room.leftBottom.x; x <= rightTop.x; x++) {
            for (int y = room.leftBottom.y; y <= rightTop.y; y++) {
                // 是否是“边缘”的格子
                if (x == room.leftBottom.x || x == rightTop.x || y == room.leftBottom.y || y == rightTop.y) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    // 利用TETile engine 对List里面存储的房间的进行渲染
    public static void printAllRooms(List<Room> rooms, TETile[][] world) {
        for (int i = 0; i < rooms.size(); i++) {
            printRoom(rooms.get(i), world);
        }
    }

    public static void  roomGenerator(TETile[][] world) {
        Random RANDOM = new Random();
        Room room = randomRoom(world);
        List<Room> rooms = generateRoomInfo(world);
        List<Room> deletedRooms = deletedOverlapRooms(rooms);
        printAllRooms(deletedRooms, world);
    }

    public static void roomInitial(TETile[][] world) {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

//    public static void main(String[] args) {
//        int WIDTH = 40;
//        int HEIGHT = 40;
//
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//
//        roomInitial(world);
//        roomGenerator(world);
//        ter.renderFrame(world);
//    }
}
