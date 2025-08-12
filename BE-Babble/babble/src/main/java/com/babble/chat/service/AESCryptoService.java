package com.babble.chat.service;

public interface AESCryptoService {

	public String encrypt(String data) throws Exception;
    
    public String decrypt(String encryptedData) throws Exception;
    
}
