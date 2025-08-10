package com.babble.chat.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.babble.chat.model.ContactMaster;
import com.babble.chat.model.UserMaster;

public interface ContactMasterRepository extends JpaRepository<ContactMaster, Long> {

	Set<ContactMaster> findByMemberAndStatus(UserMaster member, Integer status);

	@Query("""
			SELECT cm FROM ContactMaster cm WHERE cm.me = :me AND cm.status = :status
			""")
	Set<ContactMaster> findByMeAndStatus(@Param("me") UserMaster me, @Param("status") int status);
	
	@Query("""
			SELECT cm FROM ContactMaster cm WHERE cm.member = :me AND cm.status = :status
			""")
	Set<ContactMaster> findByMemberAndStatus(@Param("me") UserMaster me, @Param("status") int status);

}
