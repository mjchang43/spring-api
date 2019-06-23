package spring_api.common;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class Des3UtilTest {
	
	@Test
	public void Encrypt3DesCBCTest() throws Exception {
		
		//加密
        String cipherStr= Des3Util.Encrypt3DesCBC("naruto test");
        System.out.println(cipherStr);
        assertEquals("hex string is " + cipherStr, "6e617275746f", cipherStr);
	}
	
	@Test
	public void Decrypt3DesCBCTest() throws Exception {
		
		//解密
//        String dataStr= Des3Util.Decrypt3DesCBC(cipherStr);
//        System.out.println(dataStr);
//        assertEquals("hex string is " + hexString, "6e617275746f", hexString);
	}
}
