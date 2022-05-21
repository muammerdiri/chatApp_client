package tools;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Tools {

    //! Array concatenation function
    public static byte[] byteArrayConcatenation(byte[]arr1,byte[]arr2){
        byte[] fullText = new byte [arr1.length+ arr2.length];
        for (int i=0; i<arr1.length + arr2.length; i++)
        {
            if (i<arr1.length)
                fullText[i]=arr1[i];
            else
                fullText[i] = arr2[i-arr1.length];
        }
        return fullText;
    }

    /**
     * Converting file to byte array
     */
    public static byte[] fileToByteArray(String path) throws IOException {
        var file = new File(path);
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }

    /**
     * Converting file to PrivateKey
     */
    public static RSAPrivateKey fileToPrivateKey(String path) throws Exception {
        File file = new File(path);
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        //byte[] encoded = Base64.decodeBase64(privateKeyPEM);
        byte[] decodedBytes = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
