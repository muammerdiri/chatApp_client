package messages;

public interface IHelloCA  {
    byte[] successResponseMessage(byte opcode, int dataLength, byte[] data);
    byte[] commentMessage(byte opcode, int dataLength, byte[] data);
}
