package spring_api.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Des3Util {

	private static final String KEY_STRING="isiGuRWo36Gny+Y0yOU+rhYfL4kvUiOD";
    private static final String KEY_IV="99999999";//加密IV偏移量
    private static final String KEY_ALGORITHM = "DESede";//3DES加密算法
    private static final String PADDING_PATTERN="DESede/CBC/PKCS5Padding";//填充模式

	
    /**
     * 3Des CBC 模式加密
     * @param data 明文
     * @return Base64編碼字符串密文
     * @throws Exception
     */
    public static String Encrypt3DesCBC(String data)throws Exception{
        Cipher cipher = Cipher.getInstance(PADDING_PATTERN);
        IvParameterSpec ips = new IvParameterSpec(KEY_IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, get3DesKey(), ips);
        byte[] bOut = cipher.doFinal(data.getBytes());
        return new BASE64Encoder().encode(bOut);
    }

    /**
     * 3DES解密
     * @param cipherBase64Str Base64編碼字符串密文
     * @return 解密後內容
     * @throws Exception
     */
    public static String Decrypt3DesCBC(String cipherBase64Str) throws Exception {
        byte[] cipherStrByte= new BASE64Decoder().decodeBuffer(cipherBase64Str);
        Cipher cipher = Cipher.getInstance(PADDING_PATTERN);
        IvParameterSpec ips = new IvParameterSpec(KEY_IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, get3DesKey(), ips);
        byte[] bOut = cipher.doFinal(cipherStrByte);
        return new String(bOut, "UTF-8");

    }

    /**
     * 獲取3Des密鑰對象
     * @return
     * @throws Exception
     */
    public static Key get3DesKey()throws Exception{
        //將keyString從Base64編碼字符串轉為原始正常的byte[]
        byte[] key= new BASE64Decoder().decodeBuffer(KEY_STRING);
        System.out.println("=========key============" + Hex.encodeHexString(key));
        //實例化DES密鑰規則
        DESedeKeySpec spec = new DESedeKeySpec(key);
        //實例化3DES（desede）密鑰工廠
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成密鑰
        return keyfactory.generateSecret(spec);
    }
	
}
