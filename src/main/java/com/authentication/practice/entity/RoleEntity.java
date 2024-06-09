package com.authentication.practice.entity;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ROLE_MASTER_TABLE")
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements GrantedAuthority{
	
	private static final long serialVersionUID = 1873762023339867539L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Set<UserEntity> users;

	@Override
	public String getAuthority() {
		return name;
	}
	
	

}
