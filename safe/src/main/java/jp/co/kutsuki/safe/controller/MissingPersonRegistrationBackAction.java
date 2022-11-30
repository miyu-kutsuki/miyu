package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.MissingPersons;

/**
 * 行方不明者情報の登録確認画面から入力画面へ戻る用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonRegistrationBackAction {
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/MissingPersonsRegistrationBackAction", method = RequestMethod.POST)
	public String UserView(RedirectAttributes redirectAttributes, Model model) {
				
		//入力情報を保持して行方不明者情報登録画面へ遷移
		MissingPersons missingPersons = (MissingPersons) session.getAttribute("missingPersons");
		redirectAttributes.addFlashAttribute("missingPersons", missingPersons);
		return "redirect:MissingPersons";

	}

}
