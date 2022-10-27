package jp.co.kutsuki.safe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	/**データベースmissing_personsテーブルに探し人情報を登録後、 
	 * post・リダイレクトでメニュー選択ページへ遷移 */
	@RequestMapping(value="/MissingPersonsRegistration", method = RequestMethod.POST)
	public String UserView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam LocalDate date, @RequestParam String name, @RequestParam String gender, @RequestParam Integer age,
			@RequestParam String detail, @RequestParam String[] place,
			RedirectAttributes redirectAttributes, Model model) {
		
		User userInformation = (User) session.getAttribute("user");
				
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setDate(date);
			missingPersons.setName(name);
			missingPersons.setGender(gender);
			missingPersons.setAge(age);
			missingPersons.setDetail(detail);
			//String配列からArrayListへ変換
			List<String> placeList = Arrays.asList(place);
			missingPersons.setPlace(placeList);
			missingPersons.setUser_id(userInformation.getUser_id());
			missingPersonsRepository.setMissingPersonsTable(missingPersons);
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		
		return "redirect:Menu";
	}
}
