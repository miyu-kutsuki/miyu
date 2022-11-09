package jp.co.kutsuki.safe.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.database.dao.UserDao;

/**
 * トップページ用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Safe")
public class TopPageAction {
	
	@Autowired
	UserDao userDao;

	@GetMapping
	public String TopPageView(){
		return "index";
	}

}
