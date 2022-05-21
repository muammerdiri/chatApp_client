package builders;

import messages.IHelloCA;

public class HelloMessageBuilder {
    IHelloCA helloCA;

    public HelloMessageBuilder(IHelloCA helloCA) {
        this.helloCA = helloCA;
    }

    public void commantMessage(byte opcode, int dataLength, byte[] data){
        helloCA.commentMessage( opcode,  dataLength, data);
    }

    public void responseMessage(byte opcode, int dataLength, byte[] data){
        helloCA.successResponseMessage( opcode,  dataLength, data);
    }

    public IHelloCA getHelloCA() {
        return helloCA;
    }

    public void setHelloCA(IHelloCA helloCA) {
        this.helloCA = helloCA;
    }
}
