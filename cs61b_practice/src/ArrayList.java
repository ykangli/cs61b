public class ArrayList {
    // 创建一个新的数组，长度为：原来数组长度+1；从右到左，将每个位置后移，空出要插入的位置
    public static int[] insert(int[] arr, int item, int position) {
        int[] newArr = new int[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        for (int i = newArr.length - 1; i >= position; i--) {
            newArr[i] = newArr[i - 1];
        }
        newArr[position] = item;
        return newArr;
    }

    public static void reverse(int[] arr) {
        int flag = arr.length / 2;
        int temp = 0;
        for (int i = 0; i < flag; i++) {
            temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }

    public static int[] flatten(int[][] x) {
        int totalLength = 0;
        for (int i = 0; i < x.length; i++) {
            totalLength += x[i].length;
        }

        int[] a = new int[totalLength];
        int aIndex = 0;

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                a[aIndex] = x[i][j];
                aIndex++;
            }
        }

        return a;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        reverse(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
