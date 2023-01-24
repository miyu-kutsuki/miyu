package jp.co.kutsuki.safe.controller.suspiciouspersonsightings;

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

import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * suspicious_persons_sightingsテーブル確認画面用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class SuspiciousPersonSightingsRegistrationCheckAction {

	@Autowired
	SuspiciousPersonSightingsRepository suspiciousPersonSightingsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(value="/SuspiciousPersonSightingsRegistrationCheck", method = RequestMethod.POST)
	public String suspiciousPersonSightingsView(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@RequestParam(name = "date", required = false) LocalDate date,
			@RequestParam(name = "gender", required = false) String gender, @RequestParam(name = "age", required = false) Integer age,
			@RequestParam String detail, @RequestParam String prefectures, @RequestParam String municipalities, @RequestParam String other,
			@Validated @ModelAttribute SuspiciousPersonSightings suspiciousPersonSighting, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		//ログイン中のuser_idを取得
		FormLogin userInformation = new FormLogin();
		if(session.getAttribute("user") == null) {
			userInformation = (FormLogin) session.getAttribute("userInformation");
		}else {
			userInformation = (FormLogin) session.getAttribute("user");
		}

		//バリデーションの入力チェック
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("suspiciousPersonSightings", bindingResult);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "suspiciousPersonSightings", bindingResult);

			if(userInformation.getUser_id().equals("guests")) {
				//リダイレクトでゲスト用不審者情報登録ページへ遷移
				return "redirect:GuestsSuspiciousPersonSightings";
			}else {
				//リダイレクトで不審者登録情報ページへ遷移
				return "redirect:SuspiciousPersonSightings";
			}
		}

		SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
		suspiciousPersonSightings.setDate(date);
		suspiciousPersonSightings.setGender(gender);
		suspiciousPersonSightings.setAge(age);
		suspiciousPersonSightings.setDetail(detail);
		//全角スペースを空文字に置換
		prefectures = prefectures.replaceAll("　", "");
		//文字の間の半角スペースを空文字に変換
		prefectures = prefectures.replaceAll(" ", "");
		suspiciousPersonSightings.setPrefectures(prefectures);
		//全角スペースを空文字に置換
		municipalities = municipalities.replaceAll("　", "");
		//文字の間の半角スペースを空文字に変換
		municipalities = municipalities.replaceAll(" ", "");
		suspiciousPersonSightings.setMunicipalities(municipalities);
		suspiciousPersonSightings.setOther(other);
		suspiciousPersonSightings.setUser_id(userInformation.getUser_id());
		session.setAttribute("suspiciousPersonSightings", suspiciousPersonSightings);
		model.addAttribute("suspiciousPersonSightings", suspiciousPersonSightings);

		if(userInformation.getUser_id().equals("guests")) {
			//リダイレクトでゲスト用不審者情報の登録確認ページへ遷移
			return "forward:GuestsSuspiciousPersonSightingsRegistrationCheckAction";
		}else {
			//リダイレクトで不審者情報の登録確認ページへ遷移
			return "forward:SuspiciousPersonSightingsRegistrationCheckAction";
		}
	}
}
