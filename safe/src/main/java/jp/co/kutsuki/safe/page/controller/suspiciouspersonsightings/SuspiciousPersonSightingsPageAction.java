package jp.co.kutsuki.safe.page.controller.suspiciouspersonsightings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * 不審者の目撃情報登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("SuspiciousPersonSightings")
public class SuspiciousPersonSightingsPageAction {

	@Autowired
	HttpSession session;

	@ModelAttribute
	public SuspiciousPersonSightings setUpSuspiciousPersonSightings() {
		return new SuspiciousPersonSightings();
	}

	@GetMapping
	public String pageView(Model model) {

		FormLogin userInformation = new FormLogin();
		if(session.getAttribute("user") == null) {
			userInformation.setUser_id("guests");
		}else {
			userInformation = (FormLogin) session.getAttribute("user");
		}
		model.addAttribute("userInformation", userInformation);
		return "suspiciousPersonSightingsRegistration";
	}
}
