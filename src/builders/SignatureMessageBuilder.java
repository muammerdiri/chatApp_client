package builders;


import messages.ISignaturePublicKey;

import java.security.PrivateKey;

public class SignatureMessageBuilder {
    private ISignaturePublicKey message;


    public String commantMessage(byte [] clientPublicKey, PrivateKey serverPrivateKey){
        return message.signatureCommantMessage(clientPublicKey,serverPrivateKey);
    }

    public void responseMessage(){
        message.successResponseMessage();
    }

    public ISignaturePublicKey getMessage() {
        return message;
    }

    public void setMessage(ISignaturePublicKey message) {
        this.message = message;
    }
}


