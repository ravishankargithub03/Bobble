package com.babble.chat.model;

import java.time.LocalDateTime;
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
@Table(name = "MESSAGE_MASTER")
@GenericGenerator(name = "random_id", strategy = "com.babble.chat.util.RandomIdGenerator")
public class MessageMaster {	

	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(generator = "random_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "SENDER")
	private UserMaster sender;
	
	@Column(name = "CONTENT")
    private String content;
    
	@ManyToMany
	@JoinTable(
			name = "RECIPIENTS", 
			joinColumns = @JoinColumn(name = "MESSAGE_ID"), 
			inverseJoinColumns = @JoinColumn(name = "RECIPIENTS_ID")
			)
	private Set<UserMaster> recipients;

	@ManyToMany
	@JoinTable(
			name = "GROUP_RECIPIENTS", 
			joinColumns = @JoinColumn(name = "MESSAGE_ID"), 
			inverseJoinColumns = @JoinColumn(name = "GROUP_ID")
			)
	private Set<GroupMaster> groupRecipients;
	
	@Column(name = "STATUS")
    private Integer Status;
	
	@Column(name = "CREATED_ON")
	private LocalDateTime createdOn;
	
}
