package com.babble.chat.model;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GROUP_MASTER")
@GenericGenerator(name = "random_id", strategy = "com.babble.chat.util.RandomIdGenerator")
public class GroupMaster implements Serializable{
	
	private static final long serialVersionUID = 6222824996945527992L;
	
	@Id
	@Column(name = "GROUP_ID")
	@GeneratedValue(generator = "random_id")
	private Long id;
	
	@Column(name = "GROUP_CODE")
	private String groupCode;
	
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Column(name = "NARRATIVE")
	private String narrative;
	
	@ManyToOne
	@JoinColumn(name = "MASTER")
	private UserMaster master;
	
	@ManyToMany
	@JoinTable(
			name = "GROUP_MEMBER", 
			joinColumns = @JoinColumn(name = "GROUP_ID"), 
			inverseJoinColumns = @JoinColumn(name = "MEMBER_ID")
			)
	private Set<UserMaster> member;
	
	@Column(name = "STATUS")
	private Integer status;
	
}
