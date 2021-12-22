package com.jentrent.tracker.dao;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PasswordUtil{

//	private static final String keyFileName = "punchlist-keyfile";
//	private static final String keyFileDirName = "com.jentrent.coig_dir";
//	private static String keyFileDir = System.getProperty(keyFileDirName);
//	private static File keyFile;
//
//	static{
//
//		System.setProperty("com.jentrent.tracker.coig_dir", "/Users/jtrent/Dev");
//
//		keyFileDir = System.getProperty(keyFileDirName);
//
//		if(keyFileDir != null){
//			keyFile = new File(keyFileDir, keyFileName);
//
//			if(!keyFile.exists()){
//
//				try{
//					createKeyFile();
//				}catch(Exception e){
//					e.printStackTrace();
//					throw new RuntimeException(e);
//				}
//
//			}
//
//		}else{
//			throw new RuntimeException(keyFileDirName + " is not set as system property");
//		}
//
//	}
//
//	private static void createKeyFile() throws Exception{
//
//		byte[] raw = generateKey().getEncoded();
//		String key = byteArrayToHexString(raw);
//		Writer out = new OutputStreamWriter(new FileOutputStream(keyFile, false));
//
//		try{
//			out.write(key);
//		}finally{
//			out.close();
//		}
//
//	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException{

		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);
		return kgen.generateKey();
	}

	private static String byteArrayToHexString(byte buf[]){

		StringBuffer strbuf = new StringBuffer(buf.length * 2);

		for(int i = 0; i < buf.length; i++){

			if(((int) buf[i] & 0xff) < 0x10){
				strbuf.append("0");
			}

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	private static byte[] hexStringToByteArray(String s){

		int len = s.length();
		byte[] data = new byte[len / 2];

		for(int i = 0; i < len; i += 2){
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}

		return data;
	}

	public static String encrypt(String str){

		return str;
//		String encryptedHexaStr = null;
//
//		try{
//			SecretKey skeySpec = getKeySpec();
//			Cipher cipher = Cipher.getInstance("AES");
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//			byte[] encryptedBytes = cipher.doFinal(str.getBytes());
//			encryptedHexaStr = byteArrayToHexString(encryptedBytes);
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//
//		return encryptedHexaStr;
	}

	public static String decrypt(String str) throws Exception{

		return str;
//		String decryptedStr = null;
//
//		try{
//			SecretKey skeySpec = getKeySpec();
//			Cipher cipher = Cipher.getInstance("AES");
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//			byte[] decryptedBytes = cipher.doFinal(hexStringToByteArray(str));
//			decryptedStr = new String(decryptedBytes).toString();
//		}catch(Exception e){
//			throw new Exception(e);
//		}
//
//		return decryptedStr;
	}

//	private static SecretKey getKeySpec() throws Exception{
//
//		String pwd = getKeyFile();
//		byte[] bts = hexStringToByteArray(pwd);
//		SecretKeySpec skeySpec = new SecretKeySpec(bts, "AES");
//		return skeySpec;
//	}
//
//	private static String getKeyFile() throws Exception{
//
//		String key = null;
//		InputStream is = null;
//		InputStreamReader reader = null;
//		BufferedReader in = null;
//
//		try{
//			is = new FileInputStream(keyFile);
//			StringBuffer buf = new StringBuffer();
//			reader = new InputStreamReader(is);
//			in = new BufferedReader(reader);
//			String line = null;
//
//			while((line = in.readLine()) != null){
//
//				if(line.length() != 0){
//					buf.append(line);
//				}
//
//			}
//
//			key = buf.toString();
//		}finally{
//
//			if(in != null){
//				in.close();
//			}
//
//			if(reader != null){
//				reader.close();
//			}
//
//			if(is != null){
//				is.close();
//			}
//
//		}
//
//		return key;
//	}

	private PasswordUtil(){

	}

}
