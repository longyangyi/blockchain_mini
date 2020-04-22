import com.google.gson.GsonBuilder;

import java.security.MessageDigest;

public class StringUtil {
    //将字符串进行SHA-256哈希运算
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return bytesToString(digest.digest(input.getBytes("UTF-8")));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    //返回JSON格式数据
    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }
    //返回难度字符串目标，与散列比较。e.g.难度5将返回“00000”
    public static String getDificultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }

    public static String bytesToString(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] stringToBytes(String string) {
        byte[] bytes = string.getBytes();
        for (int i=0;i<bytes.length;i++) {
            bytes[i]-=(byte)'0';
        }
        return bytes;
    }
}

