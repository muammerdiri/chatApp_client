import builders.HelloMessageBuilder;
import builders.SignatureMessageBuilder;
import messages.HelloCA;
import messages.SignaturePublicKey;
import tools.Tools;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;

/**
 * A client sends messages to the server, the server spawns a thread to communicate with the client.
 * Each communication with a client is added to an array list so any message sent gets sent to every other client
 * by looping through it.
 */

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private String contactPersonName;
    private DataInputStream inputStream=null;
    private DataOutputStream outputStream = null;
    private SignatureMessageBuilder signatureMessageBuilder;
    private HelloMessageBuilder hello;

    public Client(Socket socket, String username,String contactPersonName) {
        try {
            this.socket = socket;
            this.username = username;
            this.contactPersonName = contactPersonName;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.signatureMessageBuilder = new SignatureMessageBuilder();
            this.hello = new HelloMessageBuilder();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter,inputStream,outputStream);
        }
    }
    //! Message sending function.
    public void sendMessage() {
        try {
            // İlk olarak kullanıcı adı gönderiliyor
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(contactPersonName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            //! Server'a PublicKey'i imzalatma kodlarını buraya yaz.
            hello.setHelloCA(new HelloCA());
            byte [] arr = hello.commantMessage(Tools.fileToByteArray("public_key.pem"));
            outputStream.writeInt(arr.length);
            outputStream.write(arr);
            outputStream.flush();

            System.out.println("Gönderilen veri: "+ byteToHex(arr));
            System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");


            // input için scanner oluşturuldu.
            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String message = scanner.nextLine();
                outputStream.writeInt(message.length());
                outputStream.write(message.getBytes(StandardCharsets.UTF_8));

            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter,inputStream,outputStream);
        }
    }

    //! Message reading function
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte buffer[] = new byte[1024];
                        baos.write(buffer, 0 , inputStream.read(buffer));

                        byte result[] = baos.toByteArray();


                        String string = new String(result,StandardCharsets.UTF_8);
                        System.out.println("Byte Message: "+byteToHex(result)+"\nString Message: "+string);
                        System.out.println("/***************************************************");


                    }catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter,inputStream,outputStream);

                    }
                }
            }
        }).start();
    }


    //! Function to close all builds.
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter,InputStream in,OutputStream out) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
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

    String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x ", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
