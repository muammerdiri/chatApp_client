package tools;

import java.io.*;

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
    public static byte[] fileToByteArray(File file) throws IOException {
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }
}
