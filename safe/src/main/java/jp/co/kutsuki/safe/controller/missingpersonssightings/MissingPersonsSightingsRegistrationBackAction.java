package jp.co.kutsuki.safe.controller.missingpersonssightings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * 行方不明者目撃情報の登録確認画面から入力画面へ戻る用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonsSightingsRegistrationBackAction {

	@Autowired
	HttpSession session;

	@RequestMapping(value="/MissingPersonsSightingsRegistrationBackAction", method = RequestMethod.POST)
	public String pageView(RedirectAttributes redirectAttributes, Model model) {

		//入力情報を保持して行方不明者目撃情報登録画面へ遷移
		MissingPersonsSightings missingPersonsSightings = (MissingPersonsSightings) session.getAttribute("missingPersonsSightings");
		redirectAttributes.addFlashAttribute("missingPersonsSightings", missingPersonsSightings);
		return "redirect:MissingPersonsSightings";
	}

}
