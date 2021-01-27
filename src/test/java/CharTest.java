import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.lang.reflect.Array;

/**
 * @author tyfann
 * @date 2021/1/27 3:59 下午
 */
public class CharTest {
    public static void main(String[] args) {
        String routing = "*/package/servicePack/getServicePackMaxSort";
        char[] chrs = routing.toCharArray();
        int index = chrs[0];
//        System.out.println(index);

        File file = new File("nodelist.txt");
        StringArray textArray = new StringArray();
        if (file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuffer sb = new StringBuffer();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    textArray.add(text);
                    sb.append(text);
                }
//                System.out.println(textArray.get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println((char) 65);
    }
}
