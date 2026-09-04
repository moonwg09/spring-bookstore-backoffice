package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.AuthorMapper;
import com.backoffice.model.AuthorVO;

@Service
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	private AuthorMapper mapper;
	
	@Override
    public List<AuthorVO> getAuthorList() {
        return mapper.getList();
    }

    @Override
    public void registerAuthor(AuthorVO author) {
        mapper.insert(author);
    }

    @Override
    public AuthorVO getAuthor(Long author_id) {
        return mapper.read(author_id);
    }

    @Override
    public boolean modifyAuthor(AuthorVO author) {
        return mapper.update(author) == 1;
    }

    @Override
    public boolean removeAuthor(Long author_id) {
        return mapper.delete(author_id) == 1;
    }

}

