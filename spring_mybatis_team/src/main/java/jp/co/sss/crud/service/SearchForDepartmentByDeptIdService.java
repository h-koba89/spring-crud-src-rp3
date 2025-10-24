package jp.co.sss.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.mapper.DepartmentMapper;

@Service
public class SearchForDepartmentByDeptIdService {

	@Autowired
	DepartmentMapper mapper;

	public Department execute(Integer deptId) {
		return mapper.findByDeptId(deptId);
	}

	public Department findByDeptId(Integer deptId) {
		return mapper.findByDeptId(deptId);
	}

}
