package des;

public enum BitMask {
    FIRSTBIT(0b10000000),
    SECONDBIT(0b01000000),
    THIRDBIT(0b00100000),
    FOURTHBIT(0b00010000),
    FIFTHBIT(0b00001000),
    SIXTHBIT(0b00000100),
    SEVENTHBIT(0b00000010),
    LASTBIT(0b00000001),
    EMPTY(0b00000000),
    FOURTOSEVENBIT(0b00011110),
    FIRSTTOSIXBIT(0b11111100),
    SEVENANDEIGHTBIT(0b00000011),
    FIRSTTOFOURBIT(0b11110000),
    FIVETOEIGHTBIT(0b00001111),
    FIRSTANDSECONDBIT(0b11000000),
    THREETOEIGHTBIT(0b00111111);

    int mask;

    BitMask(int mask) {
        this.mask = mask;
    }

    int and(byte toMask) {
        int intToMask = toMask >= 0 ? (int) toMask : 127 - (toMask);
        return (mask & intToMask);
    }

}
