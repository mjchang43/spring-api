package spring_api.common;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexUtil {

	public static String encodeHex(String s) {
		try {
			return Hex.encodeHexString(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decodeHex(String hexString) {
		try {
			return new String(Hex.decodeHex(hexString));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}
}
