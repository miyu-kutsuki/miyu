package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * メニューページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("Menu")
public class MenuPageAction {

	@Autowired
	HttpSession session;

	@GetMapping
	public String menuPageView(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		return "safeMenu";
	}
}
