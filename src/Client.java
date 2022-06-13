

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.Scanner;

public class Client {

    private SSLSocket socket;
    private String username;
    private String contactPersonName;
    private DataInputStream inputStream=null;
    private DataOutputStream outputStream = null;

    public Client(SSLSocket socket, String username,String contactPersonName) {
        try {
            this.socket = socket;
            this.username = username;
            this.contactPersonName = contactPersonName;
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            closeEverything(socket,inputStream,outputStream);
        }
    }
    //! Message sending function.
    public void sendMessage() {
        try {
            // İlk olarak kullanıcı adı gönderiliyor
            outputStream.writeUTF(username);
            outputStream.flush();
            outputStream.writeUTF(contactPersonName);
            outputStream.flush();

            // input için scanner oluşturuldu.
            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                System.out.println("Mesajınız: ");
                String message = scanner.nextLine();
                outputStream.writeUTF(message);

            }
        } catch (IOException e) {
            closeEverything(socket, inputStream,outputStream);
        }
    }

    //! Message reading function
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {

                        String recivedMessage = inputStream.readUTF();
                        System.err.println(contactPersonName+" Gelen Mesaj: "+ recivedMessage);

                        if(recivedMessage.equals("close")) {
                            closeEverything(socket,inputStream,outputStream);
                        }
                    }catch (IOException e){
                        closeEverything(socket,inputStream,outputStream);

                    }
                }
            }
        }).start();
    }


    //! Function to close all builds.
    public void closeEverything(SSLSocket socket, InputStream in,OutputStream out) {
        try {

            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
