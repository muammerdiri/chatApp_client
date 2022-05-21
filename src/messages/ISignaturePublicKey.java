package messages;

import java.security.PrivateKey;

public interface ISignaturePublicKey  {
    String signatureCommantMessage(byte [] clientPublicKey, PrivateKey serverPrivateKey);
    byte[] successResponseMessage();

}
