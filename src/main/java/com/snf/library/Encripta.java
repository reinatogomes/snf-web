package com.snf.library;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class Encripta implements Serializable {

	private static final long serialVersionUID = 1L;

	static final Logger log = Logger.getLogger(Encripta.class);

	private static final String ALGORITHM_ENCRYPTOR = "SHA-1";

	public String encripta(String senha) {
		try {
			ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
			passwordEncryptor.setAlgorithm(ALGORITHM_ENCRYPTOR);
			passwordEncryptor.setPlainDigest(true);
			return passwordEncryptor.encryptPassword(senha);
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
	}

}
