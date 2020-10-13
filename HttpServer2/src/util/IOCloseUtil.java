package util;

import java.io.Closeable;
import java.io.IOException;

public class IOCloseUtil {  //关闭流。
    public static void closeAll(Closeable...c){
        for (Closeable i: c){
            if (i != null){
                try {
                    i.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
