package shop.mtcoding.blog.core.util;

public class Script {

    public static String back(String msg) {
        String errMsg = """
                <script>
                alert('$msg');
                history.back();
                
                </script>
                """.replace("$msg", msg);

        return errMsg;
    }

    public static String href(String msg, String url) { //메세지 띄우고 로그인 페이지로 넘거가게 하는ㄴ게 href
        String errMsg = """
                <script>
                alert('$msg');
                loaction.href = '$url';
                
             
                </script>
                """.replace("$msg", msg)
                .replace("$url", url);

        return errMsg;
    }


}
