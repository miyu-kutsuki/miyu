package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.MissingPersons;

/**
 * 探し人の登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersons")
public class MissingPersonsPageAction {
	
	@ModelAttribute
	public MissingPersons setUpMissingPersons() {
		return new MissingPersons();
	}
	
	@GetMapping
	public String LoginPageView() {
		return "missingPersonRegistration";
	}
}
