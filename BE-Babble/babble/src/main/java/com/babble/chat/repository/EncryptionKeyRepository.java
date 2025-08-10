package com.babble.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babble.chat.model.EncryptionKeyMaster;

public interface EncryptionKeyRepository extends JpaRepository<EncryptionKeyMaster, Long>{

	EncryptionKeyMaster findTopByOrderByIdDesc();
	
}
