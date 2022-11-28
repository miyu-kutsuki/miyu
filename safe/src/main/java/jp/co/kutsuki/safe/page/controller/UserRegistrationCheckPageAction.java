package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.User;
/**
 * ユーザー登録確認画面遷移用のコントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UserRegistrationCheck")
public class UserRegistrationCheckPageAction {
	
	@ModelAttribute
	public User setUpUser() {
		return new User();
	}
	
	@GetMapping
	public String pageView() {
		return "userRegistrationCheck";
	}

}
