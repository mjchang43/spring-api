package spring_api.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HexUtilTest {

	@Test
	public void encodeHexTest() throws Exception {
		
		String hexString = HexUtil.encodeHex("naruto");
		assertEquals("hex string is " + hexString, "6e617275746f", hexString);
	}
	
	@Test
	public void decodeHexTest() throws Exception {
		
		String str = HexUtil.decodeHex("6e617275746f");
		assertEquals("decode hex string is " + str, "naruto", str);
	}
}
