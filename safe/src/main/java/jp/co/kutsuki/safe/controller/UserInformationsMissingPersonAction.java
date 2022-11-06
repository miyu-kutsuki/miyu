package jp.co.kutsuki.safe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.kutsuki.safe.safedb.repository.UserRepository;

/**
 * ユーザーページアクション用コントローラー
 * @author kutsuki
 *
 */
@Controller
public class UserInformationsMissingPersonAction {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value="/UserInformationsMissingPersonAction", method = RequestMethod.POST)
	public void UserInformationsView() {
		
	}
}
