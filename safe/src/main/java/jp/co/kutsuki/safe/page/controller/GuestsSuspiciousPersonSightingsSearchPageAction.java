package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.User;

/**
 * ゲスト用不審者情報検索ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("GuestsSuspiciousPersonSightingsSearch")
public class GuestsSuspiciousPersonSightingsSearchPageAction {

	@Autowired
	HttpSession session;

	@GetMapping
	public String PageView() {

		User userInformation = new User();
		if((User) session.getAttribute("user") == null) {
			userInformation.setUser_id("guests");
		}
		session.setAttribute("userInformation", userInformation);
		return "guestsSuspiciousPersonSightingsSearch";
	}
}