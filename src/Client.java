import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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


    public Client(Socket socket, String username,String contactPersonName) {
        try {
            this.socket = socket;
            this.username = username;
            this.contactPersonName = contactPersonName;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputStream = new DataOutputStream(socket.getOutputStream());
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

            //! Server'a PublicKey'i imzalatma kodlarını buraya yaz.



            bufferedWriter.write(contactPersonName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

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

                        String res = Arrays.toString(result);
                        String string = new String(result,StandardCharsets.UTF_8);
                        System.out.println("Byte Message: "+res+"\nString Message: "+string);
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



}
