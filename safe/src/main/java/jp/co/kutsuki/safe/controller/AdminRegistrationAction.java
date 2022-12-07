package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Admin;
import jp.co.kutsuki.safe.safedb.repository.AdminRepository;

/**
 *管理者アカウント登録用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class AdminRegistrationAction {
	
	@Autowired
	AdminRepository adminRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/AdminRegistrationAction", method = RequestMethod.POST)
	public String UserView(RedirectAttributes redirectAttributes, Model model) {

		//セッション切れかチェック
		if(session.getAttribute("admin") == null) {
			return "redirect:Safe";
		}

		//usersテーブルにuser_id,passwordを登録
		Admin adminInformation = (Admin) session.getAttribute("admin");
		adminRepository.setAdminTable(adminInformation);
		model.addAttribute("admin", adminInformation);
		return "redirect:LoginAdmin";

	}
}
