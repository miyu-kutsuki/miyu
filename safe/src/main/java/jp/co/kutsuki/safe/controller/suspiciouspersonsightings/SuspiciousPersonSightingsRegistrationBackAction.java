package jp.co.kutsuki.safe.controller.suspiciouspersonsightings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * 不審者目撃情報の登録確認画面から入力画面へ戻る用のコントローラー
 * @author kutsuki
 *
 */
@Controller
public class SuspiciousPersonSightingsRegistrationBackAction {

	@Autowired
	HttpSession session;

	@RequestMapping(value="/SuspiciousPersonSightingsRegistrationBackAction", method = RequestMethod.POST)
	public String pageView(RedirectAttributes redirectAttributes, Model model) {

		//入力情報を保持して行方不明者目撃情報登録画面へ遷移
		SuspiciousPersonSightings suspiciousPersonSightings = (SuspiciousPersonSightings)session.getAttribute("suspiciousPersonSightings");
		redirectAttributes.addFlashAttribute("suspiciousPersonSightings", suspiciousPersonSightings);

		if(session.getAttribute("userInformation") == null) {
			//リダイレクトで不審者登録情報ページへ遷移
			return "redirect:SuspiciousPersonSightings";
		}else {
			FormLogin userInformation = (FormLogin) session.getAttribute("userInformation");
			if(userInformation.getUser_id().equals("guests")) {
				//リダイレクトでゲスト用不審者情報登録ページへ遷移
				return "redirect:GuestsSuspiciousPersonSightings";
			}
			return "redirect:Login";
		}
	}
}
