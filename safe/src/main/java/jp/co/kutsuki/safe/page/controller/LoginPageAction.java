package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.FormLogin;

/**
 * ログインページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Login")
public class LoginPageAction {

	@Autowired
	HttpSession session;

	@ModelAttribute
	public FormLogin setUpUser() {
		return new FormLogin();
	}

	@GetMapping
	public String loginPageView() {
		session.invalidate();
		return "login";
	}
}
