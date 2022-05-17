package tools;

public class ProtocolName {
    /**
     * Fixed partitions in protocol format
     */

    //! Header
    public static final int COMMAND_HEADER = (byte) 0xA5 ;
    public static final byte RESPONSE_HEADER = 0x5A;

    //! Status Word List
    public static final int SUCCESS_STATUS =  0x9000;
    public static final int WRONG_LENGTH_STATUS = 0x6700;

    //! Opcode List
    public static final byte OPCODE_SIGN_PK = 0x0A;
    public static final byte OPCODE_OTHER = 0x0B;
    public static  final byte HELLO = 0x10;




}




