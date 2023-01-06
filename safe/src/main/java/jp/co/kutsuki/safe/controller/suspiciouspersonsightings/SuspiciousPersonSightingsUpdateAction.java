package jp.co.kutsuki.safe.controller.suspiciouspersonsightings;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * 不審者目撃情報の変更・更新用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class SuspiciousPersonSightingsUpdateAction {

	@Autowired
	SuspiciousPersonSightingsRepository  suspiciousPersonSightingsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/SuspiciousPersonSightingsUpdate", method = RequestMethod.POST)
	public String suspiciousPersonSightingsUpdate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "date", required = false) LocalDate date,
			@RequestParam String gender, @RequestParam(name = "age", required = false) Integer age,
			@RequestParam String detail, @RequestParam String prefectures, @RequestParam String municipalities, @RequestParam String other,
			@Validated @ModelAttribute SuspiciousPersonSightings suspiciousPersonsSightings, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		//セッション有効チェック
		boolean check = (boolean)session.getAttribute("check");
		if(check) {
			redirectAttributes.addFlashAttribute("msg", "セッションが無効です。");
			return "redirect:Login";
		}

		//ログイン中のuser_idを取得
		FormLogin userInformation = (FormLogin) session.getAttribute("user");
		//更新するデータのidを取得
		String id = (String) session.getAttribute("id");

		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("suspiciousPersonSightings", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "suspiciousPersonSightings", bindingResult);
			return "redirect:SuspiciousPersonSightingsEditPage";
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
			suspiciousPersonSightingsRepository.update(id, suspiciousPersonSightings);

			return "redirect:UserInformations";
		}
	}
}
