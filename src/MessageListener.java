
import messages.HelloCA;
import messages.SignaturePublicKey;
import tools.ProtocolName;
import tools.Tools;

public class MessageListener {
    private byte [] message;
    SignaturePublicKey sign;
    HelloCA hello;

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public void  messageOrientation() {

        try {
            if (message[0] == ProtocolName.COMMAND_HEADER) {
                switch (message[1]) {
                    case (ProtocolName.OPCODE_SIGN_PK):
                        sign = new SignaturePublicKey();
                        message = sign.signatureCommantMessage(publicKeyByte(message), Tools.fileToPrivateKey("private_key.pem")).getBytes();
                        break;
                    case (ProtocolName.HELLO):
                        hello = new HelloCA();
                        hello.commantMessage(message);
                        break;
                }

            } else if (message[0] == ProtocolName.RESPONSE_HEADER) {

                switch (message[1]) {
                    case (ProtocolName.OPCODE_SIGN_PK):
                        sign = new SignaturePublicKey();


                    case (ProtocolName.HELLO):

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    private   byte []publicKeyByte(byte [] dataSet){
        byte [] data=new byte[dataSet.length-6];
        for(int i =6;i< dataSet.length-6;i++){
            data[i-6] = dataSet[i];
        }
        return data;
    }


}

