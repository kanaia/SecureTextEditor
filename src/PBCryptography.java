import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class PBCryptography {
	
	public String encodePBEWithSHAAnd128BitAES_CBC_BC(String s, char[] password) throws Exception{
		byte[] input = s.getBytes(StandardCharsets.US_ASCII);

		byte[] salt = new byte[]{ 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae};
	    PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);

	    PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
	    SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithSHAAnd128BitAES-CBC-BC");
	    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

	    Cipher encryptionCipher = Cipher.getInstance("PBEWithSHAAnd128BitAES-CBC-BC");
	    encryptionCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

	    byte[] cipherText = encryptionCipher.doFinal(input);
	    
	    System.out.println(Base64.getEncoder().encodeToString(cipherText));
	    return Base64.getEncoder().encodeToString(cipherText);
	}
	
	public String decodePBEWithSHAAnd128BitAES_CBC_BC(String s, char[] password) throws Exception{
		byte[] cipherText = s.getBytes(StandardCharsets.US_ASCII);
		
		byte[] salt = new byte[]{ 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae};
		int iterationCount = 2048;
		PBEKeySpec pbeSpec = new PBEKeySpec(password, salt, iterationCount);
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithSHAAnd128BitAES-CBC-BC", "BC");
		
		Cipher cDec = Cipher.getInstance("PBEWithSHAAnd128BitAES-CBC-BC", "BC");
		SecretKey pbeKey = keyFac.generateSecret(pbeSpec);
		cDec.init(Cipher.DECRYPT_MODE, pbeKey);
		byte[] plainText = new byte[cDec.getOutputSize(cipherText.length)];

		int ctLength = cDec.update(cipherText, 0, cipherText.length, plainText, 0);
		ctLength += cDec.doFinal(plainText, ctLength);

		return new String(plainText, StandardCharsets.US_ASCII);
	}
	
	public byte[] generateSalt() throws NoSuchAlgorithmException {
	    byte salt[] = new byte[8];
	    SecureRandom saltGen = SecureRandom.getInstance("SHA1PRNG");
	    saltGen.nextBytes(salt);
	    return salt;
	}

}
