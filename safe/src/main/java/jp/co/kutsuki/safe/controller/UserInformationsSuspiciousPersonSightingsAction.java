package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * 不審者目撃情報の編集アクション・終了アクション用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserInformationsSuspiciousPersonSightingsAction {
	
	@Autowired
	SuspiciousPersonSightingsRepository suspiciousPersonSightingsRepository;
	
	@Autowired
	HttpSession session;
		
	@RequestMapping(value="/UserInformationsSuspiciousPersonSightingsAction", method = RequestMethod.POST)
	public String UserInformationsView(@RequestParam(name = "edit", required = false) String edit, 
			@RequestParam(name = "end", required = false) String end) {
		
		//編集ボタンが押下されたら指定されたidのデータを更新
		if(!(edit == null)) {
			session.setAttribute("id", edit);
			return "redirect:SuspiciousPersonSightingsEditPage";
		}
		
		//終了ボタンが押下されたら指定されたidのカラムend_flagにtrueをセットする
		if(!(end == null)) {
			suspiciousPersonSightingsRepository.Delete(end);
		}
		
		return "redirect:UserInformations";
	}
}
