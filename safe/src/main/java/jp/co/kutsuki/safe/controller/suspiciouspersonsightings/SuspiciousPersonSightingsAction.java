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
import jp.co.kutsuki.safe.im.service.ImRegistrationNoticeService;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * suspicious_person_sightingsテーブル登録用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class SuspiciousPersonSightingsAction {

	@Autowired
	SuspiciousPersonSightingsRepository suspiciousPersonSightingsRepository;
	
	@Autowired
	ImRegistrationNoticeService registrationNoticeService;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/SuspiciousPersonSightingsRegistration", method = RequestMethod.POST)
	public String suspiciousPersonSightingsView(RedirectAttributes redirectAttributes, Model model) {

		//suspicious_persons_sightingsテーブルに登録
		SuspiciousPersonSightings suspiciousPersonSightings = (SuspiciousPersonSightings)session.getAttribute("suspiciousPersonSightings");
		suspiciousPersonSightingsRepository.setSuspiciousPersonSightingsTable(suspiciousPersonSightings);
		
		//該当者に通知メールの送信
		registrationNoticeService.suspiciousPersonSightingsRegistrationNoticeMailSend(suspiciousPersonSightings);

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
