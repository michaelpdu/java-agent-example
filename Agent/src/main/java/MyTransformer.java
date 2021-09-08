import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class MyTransformer implements ClassFileTransformer {
    static byte[] mergeByteArray(byte[]... byteArray) {
        int totalLength = 0;
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == null) {
                continue;
            }
            totalLength += byteArray[i].length;
        }
        byte[] result = new byte[totalLength];
        int cur = 0;
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == null) {
                continue;
            }
            System.arraycopy(byteArray[i], 0, result, cur, byteArray[i].length);
            cur += byteArray[i].length;
        }
        return result;
    }

    public static byte[] getBytesFromFile(String fileName) {
        try {
            byte[] result = new byte[]{};
            InputStream is = new FileInputStream(new File(fileName));
            byte[] bytes = new byte[1024];
            int num = 0;
            while ((num = is.read(bytes)) != -1) {
                result = mergeByteArray(result, Arrays.copyOfRange(bytes, 0, num));
            }
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] transform(ClassLoader classLoader, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        if (!className.equals("Bird")) {
            return null;
        }
        return getBytesFromFile("/Users/dupei/workspace/idea/Example/Bird.class");
    }
}