package jp.co.kutsuki.safe.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * トップページ用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Safe")
public class TopPageAction {

	@GetMapping
	public String topPageView(){
		return "index";
	}

}
