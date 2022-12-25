package jp.co.kutsuki.safe.page.controller.missingpersonssightings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * 探し人の目撃情報登録ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonsSightings")
public class MissingPersonsSightingsPageAction {

	@Autowired
	HttpSession session;

	@ModelAttribute
	public MissingPersonsSightings setUpMissingPersonsSightings() {
		return new MissingPersonsSightings();
	}

	@GetMapping
	public String pageView(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		return "missingPersonsSightingsRegistration";
	}
}
