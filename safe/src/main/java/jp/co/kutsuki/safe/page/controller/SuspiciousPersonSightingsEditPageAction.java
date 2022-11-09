package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;

/**
 * 不審者目撃情報編集ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("SuspiciousPersonSightingsEditPage")
public class SuspiciousPersonSightingsEditPageAction {
	
	@Autowired
	InformationRepository informationRepository;
		
	@Autowired
	HttpSession session;
	
	@ModelAttribute
	public SuspiciousPersonSightings setUpSuspiciousPersonSightings() {
		return new SuspiciousPersonSightings();
	}
	
	@GetMapping
	public String EditPageView(Model model) {
		
		//更新するデータのidを取得
		String id = (String) session.getAttribute("id");

		Informations informations = new Informations();
		informationRepository.setSuspiciousPersonSightingsTable(informations, id);
		model.addAttribute("suspiciousPersonSightingsList", informations.getSuspiciousPersonSightingsList());
		
		return "suspiciousPersonSightingsEditPage";
	}
}
