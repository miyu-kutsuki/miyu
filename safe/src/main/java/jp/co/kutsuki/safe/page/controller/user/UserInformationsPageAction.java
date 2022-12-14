package jp.co.kutsuki.safe.page.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;

/**
 * ユーザーページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("UserInformations")
public class UserInformationsPageAction {

	@Autowired
	InformationRepository informationRepository;

	@Autowired
	HttpSession session;

	@GetMapping
	public String userInformationsPageView(Model model, RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}


		//ログイン中のuser_idを取得
		FormLogin userInformation = (FormLogin) session.getAttribute("user");
		model.addAttribute("user", userInformation);

		Informations informations = new Informations();

		//エラーメッセージ用変数
		String msg = "登録データがありません。";

		informationRepository.setInformationTable(informations, userInformation);

		if(informations.getMissingPersonsList().size() == 0) {
			model.addAttribute("msg1", msg);
		}else {
			model.addAttribute("missingPersonsList", informations.getMissingPersonsList());
		}

		if(informations.getMissingPersonsSightingsList().size() == 0) {
			model.addAttribute("msg2", msg);
		}else {
			model.addAttribute("missingPersonSightingsList", informations.getMissingPersonsSightingsList());
		}

		if(informations.getSuspiciousPersonSightingsList().size() == 0) {
			model.addAttribute("msg3", msg);
		}else {
			model.addAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());
		}

		return "userInformations";
	}
}
