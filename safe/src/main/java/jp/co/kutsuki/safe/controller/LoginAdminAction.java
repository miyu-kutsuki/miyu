package jp.co.kutsuki.safe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Admin;
import jp.co.kutsuki.safe.safedb.repository.AdminRepository;

/**
 * ログインアクション用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class LoginAdminAction {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/LoginAdminAction", method = RequestMethod.POST)
	public String AdminView(@RequestParam String admin_id, @RequestParam String password,
			@Validated @ModelAttribute Admin admin, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();

		//入力されたadmin_idとadminテーブルのadmin_idが一致した場合は該当のadmin_idとpasswordを取得し代入
		//一致しない場合はid=null,admin_id,password=none,end_flag=falseを代入
		Admin adminInformation = adminRepository.getAdminTable(admin_id);

		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("admin", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "admin", bindingResult);
			return "redirect:LoginAdmin";
		}

		//admin_idがnullかチェック
		if(!(admin_id.length() == 0)) {
			//admin_idが一致しているかチェック
			if(!adminInformation.getAdmin_id().equals(admin_id)) {
				msg.add("管理者IDが違います。");
			}
		}else {
			msg.add("管理者IDが入力されていません。");
		}

		//passwordがnullかチェック
		if(!(password.length() == 0)) {
			//passwordが一致しているかチェック
			if(!adminInformation.getPassword().equals(password)) {
				msg.add("パスワードが違います。");
			}
		}else {
			msg.add("パスワードが入力されていません。");
		}

		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			session.setAttribute("admin", adminInformation);
			adminInformation = (Admin) session.getAttribute("admin");
			model.addAttribute("admin", adminInformation);
			return "redirect:AdminMenu";
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:LoginAdmin";
		}
	}
}
