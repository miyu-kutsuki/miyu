package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.User;

/**
 * ログインページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Login")
public class LoginPageAction {
	
	@ModelAttribute
	public User setUpUser() {
		return new User();
	}
	
	@GetMapping
	public String LoginPageView() {
		return "login";
	}
}
