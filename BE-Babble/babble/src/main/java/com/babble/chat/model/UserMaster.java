package com.babble.chat.model;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "USER_MASTER")
@GenericGenerator(name = "random_id", strategy = "com.babble.chat.util.RandomIdGenerator")
public class UserMaster implements Serializable{

	private static final long serialVersionUID = -4172180757421693987L;
	
	@Id
	@GeneratedValue(generator = "random_id")
	@Column(name = "USER_ID")
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE_NUMBER")
	private Long phoneNumber;
	
	@Column(name = "STATUS")
	private Integer status;
}
