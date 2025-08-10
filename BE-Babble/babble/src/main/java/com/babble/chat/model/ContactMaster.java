package com.babble.chat.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "CONTACT_MASTER")
@GenericGenerator(name = "random_id", strategy = "com.babble.chat.util.RandomIdGenerator")
public class ContactMaster {
	
	@Id
	@Column(name = "CONTACT_ID")
	@GeneratedValue(generator = "random_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ME")
	private UserMaster me;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER")
	private UserMaster member;
	
	@Column(name = "STATUS")
	private Integer status;
	
}
