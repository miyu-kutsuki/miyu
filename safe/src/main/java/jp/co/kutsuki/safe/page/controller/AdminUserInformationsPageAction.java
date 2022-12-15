package jp.co.kutsuki.safe.page.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.User;

/**
 * 管理者用のユーザー管理ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UsersInformationsAdmin")
public class AdminUserInformationsPageAction {
	
	@Autowired
	HttpSession session;

	@GetMapping
	public String pageView(RedirectAttributes redirectAttributes, Model model) {

		//セッション有効チェック
		if(session.getAttribute("admin") == null) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:LoginAdmin";
		}
		
		if(!(session.getAttribute("userList") == null)) {
			model.addAttribute("userList", (ArrayList<User>)session.getAttribute("userList"));
		}
		
		return "adminUserInformations";
	}
}
