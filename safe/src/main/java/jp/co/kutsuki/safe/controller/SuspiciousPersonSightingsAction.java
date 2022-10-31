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
	
	/**データベースmissing_persons_sightingsテーブルに探し人の目撃情報を登録後、 
	 * post・リダイレクトでメニュー選択ページへ遷移 */
	@RequestMapping(value="/SuspiciousPersonSightingsRegistration", method = RequestMethod.POST)
	public String SuspiciousPersonSightingsView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam LocalDate date, 
			@RequestParam String gender, @RequestParam Integer age,
			@RequestParam String detail, @RequestParam String[] place,
			RedirectAttributes redirectAttributes, Model model) {
		
		User userInformation = (User) session.getAttribute("user");
				
		//errorメッセージ用変数
		List<String> msg = new ArrayList<>();
		
		//msgのサイズ0かチェック
		if(msg.size() == 0) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(gender);
			suspiciousPersonSightings.setAge(age);
			suspiciousPersonSightings.setDetail(detail);
			//String配列からArrayListへ変換
			List<String> placeList = Arrays.asList(place);
			suspiciousPersonSightings.setPlace(placeList);
			suspiciousPersonSightings.setUser_id(userInformation.getUser_id());
			suspiciousPersonSightingsRepository.setSuspiciousPersonSightingsTable(suspiciousPersonSightings);
		}else {
			redirectAttributes.addFlashAttribute("msg", msg);
		}
		
		return "redirect:Menu";
	}

}
