public class OffByN implements CharacterComparator {
    public int N;
    public OffByN(int n) {
        this.N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == N || y - x == N;
    }
}
