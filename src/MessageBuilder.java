

import messages.IMessage;

public class MessageBuilder {
    private IMessage message;


    public void commantMessage(){
        message.commantMessage();
    }

    public void responseMessage(){
        message.successResponseMessage();
    }

    public IMessage getMessage() {
        return message;
    }

    public void setMessage(IMessage message) {
        this.message = message;
    }
}


