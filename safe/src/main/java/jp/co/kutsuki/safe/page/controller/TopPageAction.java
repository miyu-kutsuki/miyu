package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	HttpSession session;

	@GetMapping
	public String topPageView(){
		session.invalidate();
		return "index";
	}

}
