package com.babble.chat.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.babble.chat.model.MessageMaster;

public interface MessageMasterRepository extends JpaRepository<MessageMaster, Long> {

	@Query(value = """
			SELECT 
				T1.message_id				AS messageId,
				T1.content					AS content,
				T1.sender					AS senderId,
				T3.phone_number				AS recipients,
				T1.created_on 				AS createdOn
			FROM message_master T1
			JOIN recipients T2 			ON T2.message_id = T1.message_id
			JOIN user_master T3			ON T3.user_id = T2.recipients_id
			JOIN user_master T4			ON T4.user_id = T1.sender 
			WHERE ((T1.sender = :myNumber AND T3.phone_number = :contactNumber) 
			OR (T2.recipients_id = :myNumber AND T4.phone_number = :contactNumber)) 
			AND T1.status <> :status
			""", nativeQuery = true)
	List<Map<String, Object>> getMyMessages(@Param("myNumber") Long myNumber, @Param("contactNumber") Long contactNumber, @Param("status") Integer status);

	@Query(value = """
			SELECT 
				T1.message_id				AS messageId,
				T1.content					AS content,
				T1.sender					AS recipientsId,
				T2.recipients_id			AS myId
				FROM message_master T1
				JOIN recipients T2 			ON T2.message_id = T1.message_id
				WHERE T1.sender = :contactNumber AND T2.recipients_id = :myNumber AND T1.status <> :status
			""", nativeQuery = true)
	List<Map<String, Object>> getContactMessages(@Param("contactNumber") Long contactNumber, @Param("myNumber") Long myNumber, @Param("status") Integer status);

	@Query(value = """
			SELECT 
				T1.message_id				AS messageId,
				T1.content					AS content,
				T1.sender					AS myId,
				T2.group_id					AS groupId
				FROM message_master T1
				JOIN group_recipients T2 			ON T2.message_id = T1.message_id
				WHERE T1.sender = :myNumber AND T2.group_id = :groupId AND T1.status <> :status
			""", nativeQuery = true)
	List<Map<String, Object>> getMyGroupMessages(@Param("myNumber") Long myNumber, @Param("groupId") Long groupId, @Param("status") Integer status);

	@Query(value = """
			SELECT 
				T1.message_id				AS messageId,
				T1.content					AS content,
				T1.sender					AS senderId,
				T2.group_id					AS groupId,
				T1.created_on 				AS createdOn
				FROM message_master T1
				JOIN group_recipients T2 			ON T2.message_id = T1.message_id
				WHERE T2.group_id = :groupId AND T1.status <> :status
			""", nativeQuery = true)
	List<Map<String, Object>> getGroupMemberMessages(@Param("groupId") Long groupId, @Param("status") Integer status);

	@Query(value = """
			SELECT
			   T4.phone_number AS groupId,
			   NULL AS groupCode,
			   CONCAT(T4.first_name, ' ', T4.last_name) AS groupName,
			   T4.user_id AS masterId,
			   T4.first_name AS masterFirstName,
			   T4.last_name AS masterLastName,
			   T4.phone_number AS masterPhoneNumber,
			   MAX(T1.created_on) AS created_on
			FROM message_master T1
			JOIN user_master T2 ON T2.user_id = T1.sender AND T1.status = 1 AND T2.status = 1
			JOIN recipients T3 ON T3.message_id = T1.message_id
			JOIN user_master T4 ON T4.user_id = T3.recipients_id AND T4.status = 1
			WHERE T2.user_id = :myNumber
			GROUP BY T4.user_id, T4.first_name, T4.last_name, T4.phone_number
			
			UNION ALL
			
			SELECT
			   T2.phone_number AS groupId,
			   NULL AS groupCode,
			   CONCAT(T2.first_name, ' ', T2.last_name) AS groupName,
			   T2.user_id AS masterId,
			   T2.first_name AS masterFirstName,
			   T2.last_name AS masterLastName,
			   T2.phone_number AS masterPhoneNumber,
			   MAX(T1.created_on) AS created_on
			FROM message_master T1
			JOIN user_master T2 ON T2.user_id = T1.sender AND T1.status = 1 AND T2.status = 1
			JOIN recipients T3 ON T3.message_id = T1.message_id
			JOIN user_master T4 ON T4.user_id = T3.recipients_id AND T4.status = 1
			WHERE T4.user_id = :myNumber
			GROUP BY T2.user_id, T2.first_name, T2.last_name, T2.phone_number
			
			UNION ALL
			
			SELECT
			   T1.group_id AS groupId,
			   T1.group_code AS groupCode,
			   T1.group_name AS groupName,
			   T3.user_id AS masterId,
			   T3.first_name AS masterFirstName,
			   T3.last_name AS masterLastName,
			   T3.phone_number AS masterPhoneNumber,
			   MAX(T4.created_on) AS created_on  -- Get the most recent created_on
			FROM group_master T1
			JOIN group_member T2 ON T2.group_id = T1.group_id AND T1.status = 1 AND T2.status = 1
			JOIN user_master T3 ON T3.user_id = T1.master AND T3.status = 1
			LEFT JOIN group_recipients T5 ON T5.group_id = T1.group_id
			LEFT JOIN message_master T4 ON T4.message_id = T5.message_id AND T4.status = 1
			WHERE T2.member_id = :myNumber AND (T5.group_id IS NOT NULL OR T4.sender IS NOT NULL)
			GROUP BY T1.group_id, T1.group_code, T1.group_name, T3.user_id, T3.first_name, T3.last_name, T3.phone_number
			ORDER BY created_on DESC
			""", nativeQuery = true)
	List<Map<String, Object>> getMessageByDetailsList(@Param("myNumber") Long myNumber);

}
