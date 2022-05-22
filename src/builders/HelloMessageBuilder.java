package builders;

import messages.IHelloCA;

public class HelloMessageBuilder {
    IHelloCA helloCA;



    public byte[] commantMessage(byte[] data){
        return helloCA.commentMessage(data);
    }

    public byte [] responseMessage(byte opcode, int dataLength, byte[] data){
        return helloCA.successResponseMessage( opcode,  dataLength, data);
    }

    public IHelloCA getHelloCA() {
        return helloCA;
    }

    public void setHelloCA(IHelloCA helloCA) {
        this.helloCA = helloCA;
    }
}
