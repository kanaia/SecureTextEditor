import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Cryptography {

	public String encryptAES(String s, String cipherMode, String padding) throws Exception {
		String ciphermethod = "AES/" + cipherMode + "/" + padding;
		byte[] input = s.getBytes(StandardCharsets.US_ASCII);
		byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
				0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

		byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance(ciphermethod, "BC");

		if (cipherMode.equals("ECB")) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		}
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptAES(String s, String cipherMode, String padding) throws Exception {
		String ciphermethod = "AES/" + cipherMode + "/" + padding;
		byte[] input = Base64.getDecoder().decode(s);
		byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c,
				0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
		byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance(ciphermethod, "BC");

		if (cipherMode.equals("ECB")) {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		}
		byte[] plainText = new byte[cipher.getOutputSize(input.length)];

		int ctLength = cipher.update(input, 0, input.length, plainText, 0);
		ctLength += cipher.doFinal(plainText, ctLength);

		return new String(plainText, StandardCharsets.US_ASCII);
	}

	public String encryptDES(String s, String cipherMode, String padding) throws Exception {
		String ciphermethod = "DES/" + cipherMode + "/" + padding;
		byte[] input = Base64.getDecoder().decode(s);
		byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef };

		byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
		

		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance(ciphermethod, "BC");

		if (cipherMode.equals("ECB")) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		}
		byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptDES(String s, String cipherMode, String padding) throws Exception {
		String ciphermethod = "DES/" + cipherMode + "/" + padding;
		byte[] input = Base64.getDecoder().decode(s);
		byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef };

		byte[] ivBytes = new byte[] { 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance(ciphermethod, "BC");

		if (cipherMode.equals("ECB")) {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} else {
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		}
		byte[] plainText = new byte[cipher.getOutputSize(input.length)];

		int ctLength = cipher.update(input, 0, input.length, plainText, 0);
		ctLength += cipher.doFinal(plainText, ctLength);

		return Base64.getEncoder().encodeToString(plainText);
	}

}
