package messages;

public class SignaturePublicKey implements ISignaturePublicKey {


    @Override
    public void successResponseMessage() {
        System.out.println("Success response message.");
    }

    @Override
    public void commantMessage() {
        System.out.println("Comment Message");
    }
}
