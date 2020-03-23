package des;

public class SBlock extends BitBlock{
    final byte[][] sblock;

    public SBlock(byte[][] sblock) {
        checkSize(NumberOfBytes.SBLOCK.length,sblock.length);
        checkSize(NumberOfBytes.SBLOCK.width,sblock[0].length);
        this.sblock = sblock;
    }

    public byte getSubstitution(byte sixBitInput){
        byte middle = getMiddleOfSix(sixBitInput);
        byte bounds = getBoundsOfSix(sixBitInput);
        return sblock[middle][bounds];
    }

    public static byte getBoundsOfSix(byte sixBitInput) {
         byte rightBound = (maskBits(sixBitInput,BitMask.LASTBIT));
         byte leftBoundShifted = (byte)(maskBits(sixBitInput,BitMask.LEFTBOUNDOFSIX)>>4);
         return (byte)(rightBound|leftBoundShifted);
    }

    public static byte getMiddleOfSix(byte sixBitInput) {
        return (byte)(maskBits(sixBitInput,BitMask.FOURTOSEVENBIT)>>1);
    }
}
