public class OffByOne implements CharacterComparator {
    @Override
    /** equalChars returns true for characters that are different by exactly one */
    public boolean equalChars(char x, char y) {
        return x - y == 1 || x - y == -1;
    }
}
