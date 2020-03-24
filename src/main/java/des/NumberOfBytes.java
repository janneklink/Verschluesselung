package des;

public enum NumberOfBytes {
    SUBKEY(6, 1),
    TEXTBLOCK64BIT(8, 1),
    TEXTBLOCK48BIT(6, 1),
    TEXTBLOCK32BIT(4, 1),
    SBOX(16, 4),
    KEY(8, 1);

    int length;
    int width;

    NumberOfBytes(int length, int width) {
        this.length = length;
        this.width = width;
    }
}
