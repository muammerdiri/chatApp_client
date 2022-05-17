import java.nio.ByteBuffer;

public class MessageBuilder {

    /***************************************************************************//**
     *
     * MessageBuilder class that creates protocol-compliant message formats.
     *
     ******************************************************************************/


    public static byte[] signPkSuccessResponse(byte opcode,int dataLength,byte []data){

        byte [] responseData ={ProtocolName.RESPONSE_HEADER,opcode};
        short status = (short)ProtocolName.SUCCESS_STATUS;
        byte[] statusToByte = ByteBuffer.allocate(2).putShort(status).array();
        responseData = byteArrayConcatenation(responseData,statusToByte);

        short length = (short)dataLength;
        byte[] lengthToByte = ByteBuffer.allocate(2).putShort(length).array();
        responseData = byteArrayConcatenation(responseData,lengthToByte);

        responseData=byteArrayConcatenation(responseData,data);

        return responseData;
    }

    public static byte[] signPkComment(byte opcode,int dataLength,byte []data){
        //! Main array = { header + opcode + parameter + Length + Data }
        byte [] responseData ={ProtocolName.COMMAND_HEADER,opcode,0x00};

        short length = (short)dataLength;
        byte[] lengthToByte = ByteBuffer.allocate(2).putShort(length).array();
        responseData = byteArrayConcatenation(responseData,lengthToByte);

        responseData=byteArrayConcatenation(responseData,data);

        return responseData;
    }

    public byte [] helloComment(String username){
        //Main array = { header +opcode + Length + Data }
        byte [] responseData ={ProtocolName.COMMAND_HEADER,ProtocolName.HELLO};

        short length = (short)username.length();
        byte[] lengthToByte = ByteBuffer.allocate(2).putShort(length).array();
        responseData = byteArrayConcatenation(responseData,lengthToByte);

        responseData=byteArrayConcatenation(responseData,username.getBytes());
        return responseData;

    }



    //! ************************** Helper Functions ******************************************

    //! Array concatenation function
    private static byte[] byteArrayConcatenation(byte[]arr1,byte[]arr2){
        byte[] fullText = new byte [arr1.length+ arr2.length];
        for (int i=0; i<arr1.length + arr2.length; i++)
        {
            if (i<arr1.length)
                fullText[i]=arr1[i];
            else
                fullText[i] = arr2[i-arr1.length];
        }
        return fullText;
    }
}
