import com.example.xiao.logmanager.server.common.util.MD5Util;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import com.example.xiao.logmanager.server.common.util.jwt.JwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Arrays;

public class GeneratePasswordTest {
    @Test
    public void test() {
        System.out.println(MD5Util.hashWithSalt("admin123"));
    }

}
