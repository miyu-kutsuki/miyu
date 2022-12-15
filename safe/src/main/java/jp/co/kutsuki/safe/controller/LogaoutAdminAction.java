package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 管理者用ログアウト用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class LogaoutAdminAction {
	
	@Autowired
	HttpSession session;

	@RequestMapping(value="/LogoutAdminAction", method = RequestMethod.GET)
	public String logout() {
		session.invalidate();
		return "redirect:LoginAdmin";
	}
}
