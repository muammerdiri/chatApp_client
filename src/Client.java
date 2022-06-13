import messages.HelloCA;
import messages.SignaturePublicKey;
import tools.Tools;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import java.util.Scanner;

/**
 * A client sends messages to the server, the server spawns a thread to communicate with the client.
 * Each communication with a client is added to an array list so any message sent gets sent to every other client
 * by looping through it.
 */

public class Client {

    private SSLSocket socket;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;
    private String username;
    private String contactPersonName;
    private DataInputStream inputStream=null;
    private DataOutputStream outputStream = null;
    private SignaturePublicKey signatureMessageBuilder;
    private HelloCA hello;

    public Client(SSLSocket socket, String username,String contactPersonName) {
        try {
            this.socket = socket;
            this.username = username;
            this.contactPersonName = contactPersonName;
//            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.signatureMessageBuilder = new SignaturePublicKey();
            this.hello = new HelloCA();
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
