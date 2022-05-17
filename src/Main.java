import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tek kullanımlık isim giriniz:");
        String username = scanner.nextLine();
        System.out.print("Konuşmak istediğiniz kişinin kullanıcı adını giriniz:");
        String personUsername = scanner.nextLine();


        Socket socket = new Socket("localhost", 1234);

        Client client = new Client(socket, username,personUsername);
        client.listenForMessage();
        client.sendMessage();
    }
}
