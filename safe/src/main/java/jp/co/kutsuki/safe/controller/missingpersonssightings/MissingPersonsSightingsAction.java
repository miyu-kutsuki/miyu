package jp.co.kutsuki.safe.controller.missingpersonssightings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.im.service.ImRegistrationNoticeService;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;

/**
 * missing_persons_sightingsテーブル登録用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonsSightingsAction {

	@Autowired
	MissingPersonsSightingsRepository missingPersonsSightingsRepository;

	@Autowired
	HttpSession session;
	
	@Autowired
	ImRegistrationNoticeService registrationNoticeService;

	@RequestMapping(value="/MissingPersonsSightingsRegistration", method = RequestMethod.POST)
	public String missingPersonsSightingsView(RedirectAttributes redirectAttributes, Model model) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//missing_persons_sightingsテーブルに登録
		MissingPersonsSightings missingPersonsSightings = (MissingPersonsSightings)session.getAttribute("missingPersonsSightings");
		missingPersonsSightingsRepository.setMissingPersonsSightingsTable(missingPersonsSightings);
		
		//該当者に通知メールの送信
		registrationNoticeService.missingPersonsRegistrationNoticeMailSend(missingPersonsSightings);

		return "redirect:MissingPersonsSightings";
	}
}
