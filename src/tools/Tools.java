package tools;

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
}
