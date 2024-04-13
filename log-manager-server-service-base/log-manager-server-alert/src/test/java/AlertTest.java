import cn.hutool.http.HttpUtil;

public class AlertTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String resp = HttpUtil.get("http://localhost:8001/api/sys/test/alertTest");
            System.out.println(resp);
        }
    }
}
