package jp.co.kutsuki.safe.page.controller.informationlookup;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 探し人目撃情報情報検索ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonSightingsSearch")
public class MissingPersonSightingsSearchPageAction {

	@Autowired
	HttpSession session;

	@GetMapping
	public String pageView(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//遷移先の判定用
		session.setAttribute("transition", "missingPersonSightings");
		return "missingPersonSightingsSearch";
	}
}
