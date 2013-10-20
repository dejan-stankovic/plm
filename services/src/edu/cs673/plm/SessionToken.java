/***************************************************************
Filename: SessionToken.java
Author: Christian Heckendorf
Created Date: 9/28/13
Purpose: Generate and decode a session token
Features: All session protected features
***************************************************************/
package edu.cs673.plm;

import java.lang.StringBuilder;
import java.security.MessageDigest;
import java.util.Formatter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;
import javax.xml.bind.DatatypeConverter;

public class SessionToken {
	private long uid;
	private long exp;

	/***************************************************************
	Function name: SessionToken
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Initializes an object
	***************************************************************/
	public SessionToken(long uid){
		this.uid = uid;
		this.exp = UnixTime.currentTime()+UnixTime.HOUR;
	}

	/***************************************************************
	Function name: hexStringToByteArray
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Converts a hex string to byte array
	***************************************************************/
	public static byte[] hexStringToByteArray(String data){
		return DatatypeConverter.parseHexBinary(data);
	}

	/***************************************************************
	Function name: byteArrayToHex
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Converts a byte array into a hex string
	***************************************************************/
	private String byteArrayToHex(byte[] data){
		Formatter f = new Formatter();
		for(byte b : data){
			f.format("%02x",b);
		}
		return f.toString();
	}

	/***************************************************************
	Function name: generateHash
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Generates the hash of a byte array (string)
	***************************************************************/
	private String generateHash(byte[] data) throws java.security.NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		return byteArrayToHex(md.digest(data));
	}

	/***************************************************************
	Function name: getDataBytes
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Converts the uid and exp into a byte array with a hash
	***************************************************************/
	private byte[] getDataBytes() throws java.io.UnsupportedEncodingException, java.security.NoSuchAlgorithmException{
		StringBuilder sb = new StringBuilder();
		sb.append(uid);
		sb.append(",");
		sb.append(exp);
		sb.append(",");
		sb.append(generateHash(sb.toString().getBytes("UTF-8")));
		return sb.toString().getBytes("UTF-8");
	}
	
	/***************************************************************
	Function name: generateToken
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: The main method to generate a session token
	***************************************************************/
	public String generateToken(){
		try{
			SecKey sk = new SecKey();
			Cipher c = Cipher.getInstance("AES");
			SecretKeySpec k = (SecretKeySpec)sk.getKey();
			c.init(Cipher.ENCRYPT_MODE,k);
			return byteArrayToHex(c.doFinal(getDataBytes()));
		} catch (Exception e){
			System.out.println(e.toString());
			return null;
		}
	}

	/***************************************************************
	Function name: parseToken
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Converts a token string back to uid and exp if valid
	***************************************************************/
	public void parseToken(String token){
		String[] separate = token.split(",");

		if(separate.length!=3){
			uid = 0;
			return;
		}

		try{
			uid = Long.parseLong(separate[0]);
			exp = Long.parseLong(separate[1]);

			if(token.compareTo(new String(getDataBytes()))!=0){
				uid = 0;
			}
			else if(exp<UnixTime.currentTime()){
				uid = 0;
			}
		} catch(Exception e){
			System.out.println(e.toString());
			uid = 0;
		}
	}

	/***************************************************************
	Function name: SessionToken
	Author: Christian Heckendorf
	Created Date: 9/28/13
	Purpose: Constructor to convert string to uid and exp
	***************************************************************/
	public SessionToken(String token){
		try{
			String tokstr;
			SecKey sk = new SecKey();
			Cipher c = Cipher.getInstance("AES");
			SecretKeySpec k = (SecretKeySpec)sk.getKey();
			c.init(Cipher.DECRYPT_MODE,k);
			tokstr = new String(c.doFinal(hexStringToByteArray(token)),"UTF-8");

			parseToken(tokstr);
		} catch (Exception e){
			System.out.println(e.toString());
			uid=0;
		}
	}

	public long getUid(){
		return uid;
	}

	public long getExp(){
		return exp;
	}
}
