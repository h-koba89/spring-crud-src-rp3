package jp.co.sss.crud.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sss.crud.entity.Employee;

/**
 * 権限認証用フィルタ
 * 
 * @author System Shared
 */
public class AdminAccountCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// URIと送信方式を取得する
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();

		// 完了画面はフィルターを通過させる
		if (requestURI.contains("/complete") && requestMethod.equals("GET")) {
			chain.doFilter(request, response);
			return;
		}

		//TODO セッションからユーザー情報を取得
		HttpSession session = (HttpSession) request.getSession().getAttribute("user");
		//TODO セッションユーザーのIDと権限の変数をそれぞれ初期化
		Integer id = 0;
		Integer authority = 0;
		//TODO セッションユーザーがNULLでない場合
		if (session != null) {
			//TODO セッションユーザーからID、権限を取得して変数に代入
			Employee employee = (Employee) session.getAttribute("user");
			id = employee.getEmpId();
			authority = employee.getAuthority();
		}

		//TODO  更新対象の社員IDをリクエストから取得
		String employeeId = request.getSession().getId();
		Integer employee = 0;
		//TODO  社員IDがNULLでない場合
		if (employeeId != null) {
			//TODO 社員IDを整数型に変換
			employee = Integer.parseInt(employeeId);
		}

		//フィルター通過のフラグを初期化 true:フィルター通過 false:ログイン画面へ戻す
		boolean accessFlg = false;

		//TODO  管理者(セッションユーザーのIDが2)の場合、アクセス許可
		if (authority == 2) {
			accessFlg = true;
			//TODO  ログインユーザ自身(セッションユーザのIDと変更リクエストの社員IDが一致)の画面はアクセス許可
		} else if (id == employee) {
			accessFlg = true;
		}

		//TODO  accessFlgが立っていない場合はログイン画面へリダイレクトし、処理を終了する
		if (accessFlg) {
			//TODO  レスポンス情報を取得
			HttpServletResponse responsed = response;
			//TODO  ログイン画面へリダイレクト
			responsed.sendRedirect("/index");
			//処理を終了
			return;
		}

		chain.doFilter(request, response);
		return;

	}

}
