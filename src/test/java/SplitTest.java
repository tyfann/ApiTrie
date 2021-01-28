/**
 * @author tyfann
 * @date 2021/1/28 11:21 上午
 */
public class SplitTest {
    public static void main(String[] args) {
        String message = "/websocketToIms/{userId}/info";
        String message1 = "/get";
        String[] messageArr = message.split("\\/");
        System.out.println("begin");
        String[] notNullArr = new String[messageArr.length-1];
        for (int i =1;i<messageArr.length;i++){
            notNullArr[i-1] = messageArr[i];
        }
//        System.out.println(notNullArr.length);
        for (int i = 0;i<notNullArr.length;i++){
//            System.out.println(notNullArr[i]);
        }
        String localPath = null;
        if (localPath == null){
            localPath = "/";
        }
        localPath = localPath + "get";
        localPath = localPath + "/";
        localPath = localPath + "father";
//        System.out.println(localPath);
        String testStr = "home";
        String[] testArr = new String[1];
        testArr[0] = "home";
        if (testArr[0] == testStr){
            System.out.println("Yes!!!");
        }

        String str = "{id}";
        if (str.contains("{") && str.contains("}")){
            System.out.println("包含了左右花括号");
        }else {
            System.out.println("不包含左右花括号");
        }
    }
}
