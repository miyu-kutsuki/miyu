package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.entity.User;
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
	public String SuspiciousPersonSightingsView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "date", required = false) LocalDate date, 
			@RequestParam String gender, @RequestParam(name = "age", required = false) Integer age,
			@RequestParam String detail, @RequestParam String prefectures, @RequestParam String municipalities, @RequestParam String other,
			@Validated @ModelAttribute SuspiciousPersonSightings suspiciousPersonSighting, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		
		//ログイン中のuser_idを取得
		User userInformation = (User) session.getAttribute("user");
		
		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("suspiciousPersonSightings", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "suspiciousPersonSightings", bindingResult);
		}else {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(gender);
			suspiciousPersonSightings.setAge(age);
			suspiciousPersonSightings.setDetail(detail);
			suspiciousPersonSightings.setPrefectures(prefectures);
			suspiciousPersonSightings.setMunicipalities(municipalities);
			suspiciousPersonSightings.setOther(other);
			suspiciousPersonSightings.setUser_id(userInformation.getUser_id());
			suspiciousPersonSightingsRepository.setSuspiciousPersonSightingsTable(suspiciousPersonSightings);
		}
		
		return "redirect:SuspiciousPersonSightings";
	}
}
