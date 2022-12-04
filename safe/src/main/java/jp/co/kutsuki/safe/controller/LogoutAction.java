package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログアウト用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class LogoutAction {

	@Autowired
	HttpSession session;

	@RequestMapping(value="/LogoutAction", method = RequestMethod.GET)
	public String Logout() {
		session.invalidate();
		return "redirect:Login";
	}
}
