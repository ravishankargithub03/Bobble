package com.babble.chat.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.babble.chat.model.GroupMaster;

public interface GroupMasterRepository extends JpaRepository<GroupMaster, Long> {

	@Query(value =  """
			select 
				T1.group_id		as groupId,
				T1.group_code		as groupCode,
				T1.group_name		as groupName,
				T1.narrative		as narrative,
				T3.user_id		as masterId,
				T3.first_name		as masterFirstName,
				T3.last_name		as masterLastName,
				T3.phone_number		as masterPhoneNumber
				from group_master T1
				join group_member T2 on T2.group_id = T1.group_id
				join user_master T3 on T3.user_id = T1.master
				where T2.member_id = :myNumber AND T1.status = :status
			""", nativeQuery = true)
	List<Map<String, Object>> getGroupDetailsList(@Param("myNumber") Long myNumber, @Param("status") Integer status);

}
