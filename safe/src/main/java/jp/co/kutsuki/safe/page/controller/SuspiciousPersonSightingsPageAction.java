package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String pageView(RedirectAttributes redirectAttributes) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		return "suspiciousPersonSightingsRegistration";
	}
}
