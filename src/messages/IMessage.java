package messages;

public interface IMessage {
//    byte [] successResponseMessage(byte opcode,int dataLength,byte []data);
//    byte [] commentMessage(byte opcode,int dataLength,byte []data);
    void successResponseMessage();
    void commantMessage();
}
