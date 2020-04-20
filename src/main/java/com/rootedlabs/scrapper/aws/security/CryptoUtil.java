package com.rootedlabs.scrapper.aws.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public final class CryptoUtil {

	private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5PADDING";

	private static SecretKeySpec secretKeySpec;

	private byte[] key;

	private CryptoUtil(@Value("#{'${app.secret:C10ud@2020}'}") String secretKey) {
		setKey(secretKey);
	}

	private void setKey(final String myKey) {
		MessageDigest sha = null;
		try {
			this.key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-256");
			this.key = sha.digest(this.key);
			this.key = Arrays.copyOf(this.key, 16);
			secretKeySpec = new SecretKeySpec(this.key, "AES");
		} catch (final UnsupportedEncodingException | NoSuchAlgorithmException e) {
			log.error("Error:", e);
		}
	}

	public String encrypt(final String strToEncrypt) {
		try {
			final Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			final byte[] encyptBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
			final String encodedString = Base64.encodeBase64URLSafeString(encyptBytes);
			return encodedString;
		} catch (final Exception e) {
			log.error("Error while encrypting: " + e.toString());
		}
		return strToEncrypt;
	}

	public String decrypt(final String strToDecrypt) {
		try {
			final Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return new String(cipher.doFinal(Base64.decodeBase64URLSafe(strToDecrypt)));
		} catch (final Exception e) {
			log.error("Error while decrypting: ", e);
		}
		return strToDecrypt;
	}

}