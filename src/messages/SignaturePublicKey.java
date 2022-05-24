package messages;

import tools.ProtocolName;
import tools.Tools;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class SignaturePublicKey   {


        public String signatureCommantMessage(byte [] clientPublicKey, PrivateKey serverPrivateKey)  {

        byte[] signature = null;
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");

            privateSignature.initSign(serverPrivateKey);
            privateSignature.update(clientPublicKey);

            signature = privateSignature.sign();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(signature);

    }


    public byte[] successResponseMessage(byte [] signKey) {
        byte[] lengthToByte = ByteBuffer.allocate(2).putShort((short)signKey.length).array();
        byte[] statusWord = ByteBuffer.allocate(2).putShort((short) ProtocolName.SUCCESS_STATUS).array();
        byte [] responseData = {ProtocolName.RESPONSE_HEADER,ProtocolName.OPCODE_SIGN_PK};
        responseData = Tools.byteArrayConcatenation(responseData,statusWord);
        responseData =Tools.byteArrayConcatenation(responseData,lengthToByte);
        responseData = Tools.byteArrayConcatenation(responseData,signKey);
        return responseData ;
    }

    public byte[] readResponseMessage(byte [] signKey){
            signKey = publicKeyByte(signKey);
            return signKey;
    }


    private   byte []publicKeyByte(byte [] dataSet){
        byte [] data=new byte[dataSet.length-6];
        for(int i =6;i< dataSet.length-6;i++){
            data[i-6] = dataSet[i];
        }
        return data;
    }
}
