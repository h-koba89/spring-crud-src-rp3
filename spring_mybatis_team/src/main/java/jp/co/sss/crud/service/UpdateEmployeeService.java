package jp.co.sss.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.mapper.EmployeeMapper;

/**
 * 社員更新サービス
 * 
 */
@Service
public class UpdateEmployeeService {
	@Autowired
	private EmployeeMapper mapper;

	/**
	 * 社員更新
	 * 
	 * @return true 更新が成功した場合
	 */
	public Boolean execute(Employee employee) {
		return mapper.update(employee);
	}

	public Employee findById(Integer empId) {
		return mapper.findById(empId);
	}

}
