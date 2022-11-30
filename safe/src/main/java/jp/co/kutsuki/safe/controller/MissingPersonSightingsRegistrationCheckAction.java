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

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;

/**
 * missing_persons_sightingsテーブル確認画面用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonSightingsRegistrationCheckAction {
	
	@Autowired
	MissingPersonsSightingsRepository missingPersonsSightingsRepository;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/MissingPersonsSightingsRegistrationCheck", method = RequestMethod.POST)
	public String MissingPersonsSightingsView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "date", required = false) LocalDate date, 
			@RequestParam(name = "gender", required = false) String gender, @RequestParam(name = "age", required = false) Integer age,
			@RequestParam String detail, @RequestParam String prefectures, @RequestParam String municipalities, @RequestParam String other,
			@Validated @ModelAttribute MissingPersonsSightings missingPersonsSighting, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		
		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}
		
		//ログイン中のuser_idを取得
		User userInformation = (User) session.getAttribute("user");
		
		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("missingPersonsSightings", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "missingPersonsSightings", bindingResult);
			return "redirect:MissingPersonsSightings";
		}else {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(gender);
			missingPersonsSightings.setAge(age);
			missingPersonsSightings.setDetail(detail);
			missingPersonsSightings.setPrefectures(prefectures);
			missingPersonsSightings.setMunicipalities(municipalities);
			missingPersonsSightings.setOther(other);
			missingPersonsSightings.setUser_id(userInformation.getUser_id());
			session.setAttribute("missingPersonsSightings", missingPersonsSightings);
			model.addAttribute("missingPersonsSightings", missingPersonsSightings);
			return "forward:MissingPersonSightingsRegistrationCheckAction";
		}
	}
}
