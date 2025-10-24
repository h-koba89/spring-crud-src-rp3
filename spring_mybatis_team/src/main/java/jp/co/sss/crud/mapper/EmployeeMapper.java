/**
 * 
 */
package jp.co.sss.crud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.crud.entity.Employee;

/**
 * 社員情報テーブルマッパー
 * 
 */
@Mapper
public interface EmployeeMapper {

	/**
	 * 社員全件検索(社員IDで並び替え)
	 * 
	 * @return 社員のエンティティリスト
	 */
	List<Employee> findAllEmployeesOrderByEmpId();

	/**	
	 * 社員名検索(社員IDで並び替え)
	 * 
	 * @param empName 社員名
	 * @return 社員名検索後のエンティティリスト
	 */
	List<Employee> findByEmpNameContainingOrderByEmpId(@Param(value = "empName") String empName);

	/**
	 * 部署検索(社員IDで並び替え)
	 * 
	 * @param deptId 部署ID
	 * @return 部署ID検索後のエンティティリスト
	 */
	List<Employee> findByDepartmentDeptIdOrderByEmpId(@Param(value = "deptId") Integer deptId);

	/**
	 * ログインのためのID、パスワード検索
	 * 
	 * @param empId
	 * @param empPass
	 * @return ログインユーザーエンティティ
	 */
	Employee findByEmpIdAndEmpPass(@Param(value = "empId") int empId, @Param(value = "empPass") String empPass);

	/**
	 * 社員主キー検索
	 * 
	 * @param empId
	 * @return 1件分の社員エンティティ
	 */
	Employee findByEmpId(@Param(value = "empId") Integer empId);

	/**
	 * 社員登録
	 * 
	 * @param employee
	 * @return TRUE：登録件数が1以上の場合
	 */
	Boolean insert(Employee employee);

	/**
	 * 社員更新
	 * 
	 * @param employee
	 * @return TRUE：更新件数が1以上の場合
	 */
	Boolean update(Employee employee);

	/**
	 * 社員物理削除
	 * 
	 * @param employeeId
	 * @return TRUE：削除件数が1以上の場合
	 */
	Boolean delete(@Param(value = "empId") Integer employeeId);

	Employee findById(Integer empId);

}
