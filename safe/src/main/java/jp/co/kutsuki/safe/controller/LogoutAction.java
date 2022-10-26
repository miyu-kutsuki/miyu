package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログアウト用コントローラー
 * @author miyu
 *
 */
@Controller
public class LogoutAction {
	
	@Autowired
	HttpSession session;
	
	/** post・リダイレクトでユーザー画面へ遷移 */
	@RequestMapping(value="/LogoutAction", method = RequestMethod.POST)
	public String Logout() {
		session.invalidate();
		return "redirect:/Login";
	}
	
	/** リダイレクト先の画面 */
	@RequestMapping("Login")
	public String PostLogout() {
		return "login";
	}
}
