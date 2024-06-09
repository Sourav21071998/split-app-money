package com.authentication.practice.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JwtBlacklist {

	private final Set<String> blacklist = new HashSet<>();

	public void addToken(String token) {
		blacklist.add(token);
	}

	public boolean isTokenBlacklisted(String token) {
		return blacklist.contains(token);
	}

	public int getSize() {
		return blacklist.size();
	}
}
