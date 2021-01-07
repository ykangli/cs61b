package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * 利用flood fill算法对房间之外的空隙区域进行填充，如果相邻的是房间就不进行填充，
 * 这样就能产生互相不连接的房间和走廊
 */
public class Hallway {
    // 返回某位置的上面位置
    private static Position up(Position p) {
        return new Position(p.x, p.y + 1);
    }

    //返回某位置的下面位置
    private static Position bottom(Position p) {
        return new Position(p.x, p.y - 1);
    }

    //返回某位置的左位置
    private static Position left(Position p) {
        return new Position(p.x - 1, p.y);
    }

    //返回某位置的右位置
    private static Position right(Position p) {
        return new Position(p.x + 1, p.y);
    }

    // 用来判断一个格子的周围是不是房间（上下左右），如果这个格子上下左右都是墙，则不满足条件，寻找其他位置的格子
    private static boolean isNotFloor(Position p, TETile[][] world) {
        // 地图最左上角，此位置无左边和上边,只有下右
        if (p.x == 0 && p.y == world[0].length - 1) {
            if (world[p.x + 1][p.y].description().equals("floor") || world[p.x][p.y - 1].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 地图最左下角，此位置无左边和下边,只有上右
        if (p.x == 0 && p.y == 0) {
            if (world[p.x][p.y + 1].description().equals("floor") || world[p.x + 1][p.y].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 地图最右上角，此位置无右边和上边,只有左下
        if (p.x == world.length - 1 && p.y == world[0].length - 1) {
            if (world[p.x - 1][p.y].description().equals("floor") || world[p.x][p.y - 1].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 地图最右下角，此位置无右边和下边,只有左上
        if (p.x == world.length - 1 && p.y == 0) {
            if (world[p.x - 1][p.y].description().equals("floor") || world[p.x][p.y + 1].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 无左边的情况
        if (p.x == 0 ) {
            if (world[p.x][p.y + 1].description().equals("floor") || world[p.x][p.y - 1].description().equals("floor") ||
                    world[p.x + 1][p.y].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 无右边
        if (p.x == world.length - 1) {
            if (world[p.x][p.y + 1].description().equals("floor") || world[p.x][p.y - 1].description().equals("floor") ||
                    world[p.x - 1][p.y].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 无上边
        if (p.y == world[0].length - 1) {
            if (world[p.x][p.y - 1].description().equals("floor") ||
                    world[p.x - 1][p.y].description().equals("floor") ||
                    world[p.x + 1][p.y].description().equals("floor")) {
                return true;
            }
            return false;
        }

        // 无下边
        if (p.y == 0) {
            if (world[p.x][p.y + 1].description().equals("floor") ||
                    world[p.x - 1][p.y].description().equals("floor") ||
                    world[p.x + 1][p.y].description().equals("floor")) {
                return true;
            }
            return false;
        }

        if (world[p.x][p.y + 1].description().equals("floor") || world[p.x][p.y - 1].description().equals("floor") ||
                world[p.x - 1][p.y].description().equals("floor") ||
                world[p.y + 1][p.y].description().equals("floor")) {
            return true;
        }
        return false;
    }

    // 用来判断一个格子的周围是不是wall（上下左右），如果这个格子上下左右都是墙，则不满足条件，寻找其他位置的格子
    private static boolean isNotWall(Position p, TETile[][] world) {
        // 地图最左上角，此位置无左边和上边,只有下右
        if (p.x == 0 && p.y == world[0].length - 1) {
            if (world[p.x + 1][p.y].description().equals("wall") || world[p.x][p.y - 1].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 地图最左下角，此位置无左边和下边,只有上右
        if (p.x == 0 && p.y == 0) {
            if (world[p.x][p.y + 1].description().equals("wall") || world[p.x + 1][p.y].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 地图最右上角，此位置无右边和上边,只有左下
        if (p.x == world.length - 1 && p.y == world[0].length - 1) {
            if (world[p.x - 1][p.y].description().equals("wall") || world[p.x][p.y - 1].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 地图最右下角，此位置无右边和下边,只有左上
        if (p.x == world.length - 1 && p.y == 0) {
            if (world[p.x - 1][p.y].description().equals("wall") || world[p.x][p.y + 1].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 无左边的情况
        if (p.x == 0 ) {
            if (world[p.x][p.y + 1].description().equals("wall") || world[p.x][p.y - 1].description().equals("wall") ||
                    world[p.x + 1][p.y].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 无右边
        if (p.x == world.length - 1) {
            if (world[p.x][p.y + 1].description().equals("wall") || world[p.x][p.y - 1].description().equals("wall") ||
                    world[p.x - 1][p.y].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 无上边
        if (p.y == world[0].length - 1) {
            if (world[p.x][p.y - 1].description().equals("wall") ||
                    world[p.x - 1][p.y].description().equals("wall") ||
                    world[p.x + 1][p.y].description().equals("wall")) {
                return true;
            }
            return false;
        }

        // 无下边
        if (p.y == 0) {
            if (world[p.x][p.y + 1].description().equals("wall") ||
                    world[p.x - 1][p.y].description().equals("wall") ||
                    world[p.x + 1][p.y].description().equals("wall")) {
                return true;
            }
            return false;
        }

        if (world[p.x][p.y + 1].description().equals("wall") || world[p.x][p.y - 1].description().equals("wall") ||
                world[p.x - 1][p.y].description().equals("wall") ||
                world[p.y + 1][p.y].description().equals("wall")) {
            return true;
        }
        return false;
    }


    // 在world中随机选取一个可作为迷宫区域的小块（要求周围不能是floor）
    public static Position findRandom(TETile[][] world) {
        while (true) {
            Random RANDOM = new Random();
            int x = RANDOM.nextInt(world.length);
            int y = RANDOM.nextInt(world[0].length);
            Position p = new Position(x, y);
            if (world[x][y].description().equals("nothing") && !isNotFloor(p, world)) {
                return p;
            }
        }
    }

    // 传递进来的二维数组world应该是已经随机生成好房间，并把其他地方置为nothing的二维数组world
    // 无条件的填充算法，会把整个空隙充满，如何加条件？？？？
    // 填充该小块的位置时，要求周围不能是floor
    public static void floodFill(TETile[][] world, Position p) {
        if(p.x >= 0 && p.x < world.length && p.y >= 0 && p.y < world[0].length
                && world[p.x][p.y].description().equals("nothing") && !isNotFloor(p, world)) {
            world[p.x][p.y] = Tileset.WALL;
            floodFill(world, up(p));
            floodFill(world, bottom(p));
            floodFill(world, left(p));
            floodFill(world, right(p));
        }
    }

    //将随机生成且去重后房间的二维数组传入，遍历，进行填充
    public static void fillWorld(TETile[][] world, Position p) {

    }

}


