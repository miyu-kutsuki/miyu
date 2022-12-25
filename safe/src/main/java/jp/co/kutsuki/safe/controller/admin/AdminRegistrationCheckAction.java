package jp.co.kutsuki.safe.controller.admin;

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
 * 管理者アカウント登録確認用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class AdminRegistrationCheckAction {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/AdminRegistrationCheckAction", method = RequestMethod.POST)
	public String userView(@RequestParam String admin_id, @RequestParam String password,
			@Validated @ModelAttribute Admin admin, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		//入力されたuser_idとusersテーブルのuser_idが一致した場合は該当のuser_idとpasswordを取得し代入
		//一致しない場合はid=null,user_id,password=none,end_flag=falseを代入
		Admin adminInformation = adminRepository.getAdminTable(admin_id);

		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();

		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("admin", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "admin", bindingResult);
			return "redirect:AdminRegistration";
		}

		//ユーザーIDの重複不可のためnone(null)かチェック
		if(adminInformation.getAdmin_id().equals("none")) {
			//user_idがnullかチェック
			if(admin_id.length() == 0) {
				msg.add("管理者IDが入力されていません。");
			}

			//passwordがnullかチェック
			if(password.length() == 0) {
				msg.add("パスワードが入力されていません。");
			}
		}else {
			msg.add(admin_id + "は使用できません。");
		}

		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			Admin newAdmin = new Admin();
			newAdmin.setAdmin_id(admin_id);
			newAdmin.setPassword(password);
			session.setAttribute("admin", newAdmin);
			model.addAttribute("admin", newAdmin);
			return "forward:AdminRegistrationCheck";
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
			return "redirect:AdminRegistration";
		}
	}
}
