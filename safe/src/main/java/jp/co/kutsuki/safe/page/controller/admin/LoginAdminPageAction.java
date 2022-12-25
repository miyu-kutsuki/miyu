package jp.co.kutsuki.safe.page.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Admin;

/**
 * 管理者用ログインページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("LoginAdmin")
public class LoginAdminPageAction {

	@Autowired
	HttpSession session;

	@ModelAttribute
	public Admin setUpAdmin() {
		return new Admin();
	}

	@GetMapping
	public String loginPageView() {
		session.invalidate();
		return "loginAdmin";
	}

}
