package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 管理者用の情報管理ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("InformationsAdmin")
public class AdminInformationsPageAction {
	
	@Autowired
	HttpSession session;

	@GetMapping
	public String PageView(RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		if(session.getAttribute("admin") == null) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:LoginAdmin";
		}

		return "adminInformations";
	}
}
