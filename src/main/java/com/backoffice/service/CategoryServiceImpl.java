package com.backoffice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.mapper.CategoryMapper;
import com.backoffice.model.CategoryVO;

@Service	
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper mapper;
	
	@Override
	public List<CategoryVO> getCategoryList(){
		return mapper.getList();
	}
	
	@Override
	public void registerCategory(CategoryVO category) {
		mapper.insert(category);
	}
	
	@Override
	public CategoryVO getCategory(Long category_id) {
		return mapper.read(category_id);
	}
	
	@Override
	public boolean modifyCategory(CategoryVO category) {
		return mapper.update(category) == 1;
	}
	
	@Override
	public boolean removeCategory(Long category_id) {
		return mapper.delete(category_id) == 1;
	}

}
