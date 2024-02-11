import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class Base64Test {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String base64encodedString = Base64.getEncoder().encodeToString(
                "Java 8 Base64 编码解码 - Java8新特性 - 二哥！".getBytes("utf-8"));
        System.out.println(base64encodedString);
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println(new String(base64decodedBytes, "utf-8"));


        String a=Base64Utils.encodeToString("Java 8 Base64 编码解码 - Java8新特性 - 二哥！".getBytes("utf-8"));
        System.out.println(a);
        System.out.println(new String(Base64Utils.decodeFromString(a),"utf-8"));
    }
}
