package jp.co.sss.crud.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログインチェック用フィルタ
 * 
 * @author System Shared
 */
public class LoginCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//TODO セッションからユーザー情報を取得
		HttpSession session = request.getSession();
		Object employee = session.getAttribute("user");
		//TODO ユーザーがNULLの場合、ログイン画面にリダイレクトする
		if (employee == null) {
			response.sendRedirect("/spring_crud/");
			return;
		}
		// 次の処理へ移行
		chain.doFilter(request, response);

	}
}
