package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * メニューページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Menu")
public class MenuPageAction {
	
	@Autowired
	HttpSession session;
	
	@GetMapping
	public String LoginPageView() {
		return "safeMenu";
	}
}
