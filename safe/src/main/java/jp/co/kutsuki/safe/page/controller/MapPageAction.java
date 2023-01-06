package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 地図検索ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MapSearch")
public class MapPageAction {

	@GetMapping
	public String loginPageView() {

		return "map";
	}
}
