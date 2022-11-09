package jp.co.kutsuki.safe.page.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;

/**
 * 探し人目撃情報編集ページ遷移用コントローラー
 * @author kutsuki
 *
 */
@Controller
@RequestMapping("MissingPersonSightingsEditPage")
public class MissingPersonSightingsEditPageAction {
	
	@Autowired
	InformationRepository informationRepository;
		
	@Autowired
	HttpSession session;
	
	@ModelAttribute
	public MissingPersonsSightings setUpMissingPersonsSightings() {
		return new MissingPersonsSightings();
	}
	
	@GetMapping
	public String EditPageView(Model model) {
		
		//更新するデータのidを取得
		String id = (String) session.getAttribute("id");

		Informations informations = new Informations();
		informationRepository.setMissingPersonsSightingsTable(informations, id);
		model.addAttribute("missingPersonsSightingsList", informations.getMissingPersonsSightingsList());
		
		return "missingPersonsSightingsEditPage";
	}
}
