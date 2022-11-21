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

import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

/**
 * missing_personsテーブル登録用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class MissingPersonAction {
	
	@Autowired
	MissingPersonsRepository  missingPersonsRepository;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/MissingPersonsRegistration", method = RequestMethod.POST)
	public String MissingPersonView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "date", required = false) LocalDate date, 
			@RequestParam String name, @RequestParam String gender, @RequestParam(name = "age", required = false) Integer age,
			@RequestParam String detail, @RequestParam String prefectures, @RequestParam String municipalities, @RequestParam String other,
			@Validated @ModelAttribute MissingPersons missingPerson, BindingResult bindingResult,
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
			redirectAttributes.addFlashAttribute("missingPersons", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "missingPersons", bindingResult);
		}else {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setDate(date);
			missingPersons.setName(name);
			missingPersons.setGender(gender);
			missingPersons.setAge(age);
			missingPersons.setDetail(detail);
			missingPersons.setPrefectures(prefectures);
			missingPersons.setMunicipalities(municipalities);
			missingPersons.setOther(other);
			missingPersons.setUser_id(userInformation.getUser_id());
			missingPersonsRepository.setMissingPersonsTable(missingPersons);
		}
		
		return "redirect:MissingPersons";
	}
}
