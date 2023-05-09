package com.williamhdz.quickpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "USERS")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;
	
	@Column(name = "USERNAME")
	@NotEmpty
	private String username;
	
	@Column(name = "PASSWORD")
	@NotEmpty
	@JsonIgnore
	private String password;
	
	@Column(name = "FIRST_NAME")
	@NotEmpty
	private String firtsname;
	
	@Column(name = "LAST_NAME")
	@NotEmpty
	private String lastname;
	
	@Column(name = "ADMIN", columnDefinition = "char(3)")
	@Convert(converter = org.hibernate.type.YesNoConverter.class)
	@NotEmpty
	private Boolean admin;
	
}
