package com.babble.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babble.chat.model.UserMaster;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

	UserMaster getByPhoneNumberAndStatus(Long phoneNumber, int status);

}
