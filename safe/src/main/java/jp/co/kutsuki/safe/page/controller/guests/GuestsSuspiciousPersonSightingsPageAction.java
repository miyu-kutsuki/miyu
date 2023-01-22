package jp.co.kutsuki.safe.page.controller.guests;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * ゲスト用不審者情報登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("GuestsSuspiciousPersonSightings")
public class GuestsSuspiciousPersonSightingsPageAction {

	@Autowired
	HttpSession session;

	@ModelAttribute
	public SuspiciousPersonSightings setUpSuspiciousPersonSightings() {
		return new SuspiciousPersonSightings();
	}

	@GetMapping
	public String pageView() {

		FormLogin userInformation = new FormLogin();
		userInformation.setUser_id("guests");
		session.setAttribute("userInformation", userInformation);
		return "guestsSuspiciousPersonSightingsRegistration";
	}
}
