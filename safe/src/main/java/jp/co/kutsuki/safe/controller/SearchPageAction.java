package jp.co.kutsuki.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 検索ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Informations")
public class SearchPageAction {
	
	@GetMapping
	public String LoginPageView() {
		return "informations";
	}
}
