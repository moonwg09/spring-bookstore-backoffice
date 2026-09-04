package com.backoffice.service;

import java.util.List;

import com.backoffice.model.AuthorVO;

public interface AuthorService {
	
	public List<AuthorVO> getAuthorList();
	
	public void registerAuthor(AuthorVO author);
	
	public AuthorVO getAuthor(Long author_id);
	
	public boolean modifyAuthor(AuthorVO author);
	
	public boolean removeAuthor(Long author_id);

}

