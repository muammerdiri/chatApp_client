import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.Security;
import java.util.Scanner;
import com.sun.net.ssl.internal.ssl.Provider;

public class Main {
    public static void main(String[] args) throws IOException {

        Security.addProvider(new Provider());

        //sunucunun sertifikasını ve genelini içeren trustStore dosyasını belirtme.
        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");

        //trustStore dosyasının parolasını belirleme
        System.setProperty("javax.net.ssl.trustStorePassword","123456");

        //Sadece el sıkışma sürecinin ayrıntılarının dökümünü göstermek içindir (Zorunlu değil).
        //System.setProperty("javax.net.debug","all");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tek kullanımlık isim giriniz:");
        String username = scanner.nextLine();
        System.out.print("Konuşmak istediğiniz kişinin kullanıcı adını giriniz:");
        String personUsername = scanner.nextLine();


        //SSLSSocketFactory, ssl bağlamını kurar ve SSLSocket oluşturur
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();

        //SSLServerFactory önceden kurulmuş ssl bağlamını kullanarak SSLSocket oluşturun ve sunucuya bağlanın
        SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket("localhost",1234);

        Client client = new Client(sslSocket, username,personUsername);
        client.listenForMessage();
        client.sendMessage();
    }
}
