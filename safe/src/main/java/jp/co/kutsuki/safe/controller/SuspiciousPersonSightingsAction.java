package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
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
	HttpSession session;
	
	@RequestMapping(value="/SuspiciousPersonSightingsRegistration", method = RequestMethod.POST)
	public String SuspiciousPersonSightingsView(RedirectAttributes redirectAttributes, Model model) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}
		
		//missing_persons_sightingsテーブルに登録
		SuspiciousPersonSightings suspiciousPersonSightings = (SuspiciousPersonSightings)session.getAttribute("suspiciousPersonSightings");
		suspiciousPersonSightingsRepository.setSuspiciousPersonSightingsTable(suspiciousPersonSightings);
		return "redirect:SuspiciousPersonSightings";
	}
}
