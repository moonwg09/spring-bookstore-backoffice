package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.PublisherMapper;
import com.backoffice.model.PublisherVO;

@Service
public class PublisherServiceImpl implements PublisherService{
	
	@Autowired
	private PublisherMapper mapper;
	
	@Override
	public List<PublisherVO> getPublisherList(){
		return mapper.getList();
	}
	
	@Override
	public void registerPublisher(PublisherVO publisher) {
		mapper.insert(publisher);
	}
	
	@Override
	public PublisherVO getPublisher(Long publisher_id) {
		return mapper.read(publisher_id);
	}
	
	@Override
	public boolean modifyPublisher(PublisherVO publisher) {
		return mapper.update(publisher) == 1;
	}
	
	@Override
	public boolean removePublisher(Long publisher_id) {
		return mapper.delete(publisher_id) == 1;
	}

}

