package com.babble.chat.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "ENCRYPTION_KEY_MASTER")
@GenericGenerator(name = "random_id", strategy = "com.babble.chat.util.RandomIdGenerator")	
public class EncryptionKeyMaster {

	@Id
	@GeneratedValue(generator = "random_id")
	@Column(name =  "ENCRYPTION_KEY_ID")
    private Long id;

    @Lob
    @Column(name = "ENCRYPTION_KEY_DATA")
    private byte[] keyData;
}
