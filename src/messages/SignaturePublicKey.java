package messages;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class SignaturePublicKey implements ISignaturePublicKey {


    @Override
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

    @Override
    public byte[] successResponseMessage() {
        return new byte[0];
    }
}
