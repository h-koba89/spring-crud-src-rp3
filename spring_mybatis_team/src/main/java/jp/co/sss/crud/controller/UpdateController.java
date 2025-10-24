package jp.co.sss.crud.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.SearchForDepartmentByDeptIdService;
import jp.co.sss.crud.service.SearchForEmployeesByEmpIdService;
import jp.co.sss.crud.service.UpdateEmployeeService;

/**
 * 社員更新コントローラー
 */
@Controller
public class UpdateController {

	/**
	 * 社員IDを基に社員情報を検索するサービス
	 */
	@Autowired
	SearchForEmployeesByEmpIdService searchForEmployeesByEmpIdService;

	/**
	 * 社員情報を更新するサービス
	 */
	@Autowired
	UpdateEmployeeService updateEmployeeService;

	/**
	 * 部署IDを基に部署情報を検索するサービス
	 */
	@Autowired
	SearchForDepartmentByDeptIdService searchForDepartmentByDeptIdService;

	/**
	 * 社員情報の変更内容入力画面を出力
	 *
	 * @param empId
	 *            社員ID
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/input", method = RequestMethod.GET)
	public String inputUpdate(Integer empId, @ModelAttribute EmployeeForm employeeForm, HttpSession session) {

		//TODO 社員IDに紐づく社員情報を検索し、Employee型の変数に代入する
		//Service 処理を行う　引数をもらって結果（戻り値）を返す
		Employee employee = updateEmployeeService.findById(empId);

		//TODO 検索した社員情報をformに積め直す(BeanCopyクラスを用いてもよい)	
		BeanUtils.copyProperties(employee, employeeForm);

		Employee sessionUserEmployee = (Employee) session.getAttribute("user");
		if (sessionUserEmployee != null && sessionUserEmployee.getEmpId().equals(empId)) {
			session.setAttribute("user", employee); //DBから取得した最新情報で更新
		}

		// 更新確認画面のビュー名を返す
		return "update/update_input";
	}

	/**
	 * 社員情報の変更確認画面を出力
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/check", method = RequestMethod.POST)
	public String checkUpdate(@Valid @ModelAttribute EmployeeForm employeeForm, BindingResult result, Model model) {
		// TODO 入力チェックでエラーが発生した場合
		if (result.hasErrors()) {
			//if (false) {
			// エラーがある場合は入力画面に戻る
			return "update/update_input";
		} else {
			// TODO 部署IDから部署情報を検索する
			Department department = searchForDepartmentByDeptIdService.findByDeptId(employeeForm.getDeptId());

			// TODO 部署名をモデルに追加する
			model.addAttribute("deptName", department.getDeptName());

			// 更新確認画面のビュー名を返す
			return "update/update_check";
		}
	}

	/**
	 * 変更内容入力画面に戻る
	 *
	 * @param employeeForm 変更対象の社員情報
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/back", method = RequestMethod.POST)
	public String backInputUpdate(@ModelAttribute EmployeeForm employeeForm) {
		//  更新入力画面のビュー名を返す
		return "update/update_input";
	}

	/**
	 * 社員情報の変更実行
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @return 完了画面URLへリダイレクト
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.POST)
	public String completeUpdate(EmployeeForm employeeForm, HttpSession session) {

		// TODO フォームの内容をEmployeeエンティティにコピー
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeForm, employee);

		// TODO 権限がnullの場合、デフォルトの権限を設定        
		if (employee.getAuthority() == null) {
			employee.setAuthority(1);
		}
		// TODO 社員情報を更新する
		updateEmployeeService.update(employee);
		// TODO セッションからユーザー情報を取得
		Employee sessionUser = (Employee) session.getAttribute("user");
		// TODO ログイン中のユーザーが自分の情報を更新した場合、セッション情報も更新
		if (sessionUser == null) {
			sessionUser = new Employee();
			sessionUser.setAuthority(1);

			// TODO セッションに保存されているユーザーの社員名を更新
		}
		if (sessionUser.getEmpId() != null && sessionUser.getEmpId().equals(employee.getEmpId())) {
			sessionUser.setEmpName(employee.getEmpName());
			sessionUser.setAuthority(employee.getAuthority());
		}
		session.setAttribute("user", sessionUser);
		return "redirect:/update/complete";
	}

	/**
	 * 社員情報の変更完了画面
	 *
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.GET)
	public String completeUpdate() {
		//  更新完了画面のビュー名を返す
		return "update/update_complete";

	}

}
