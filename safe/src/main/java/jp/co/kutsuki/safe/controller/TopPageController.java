package jp.co.kutsuki.safe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.kutsuki.safe.database.dao.UserDataDao;
import jp.co.kutsuki.safe.entity.User;

@Controller
@RequestMapping("/safe")
public class TopPageController {
	
	@Autowired
	UserDataDao userDao;

	
	@GetMapping
	public String TopPageView(Model model, User user){
		
		User userInformation = userDao.getUserTable("miyu3819");
		
		if(!(userInformation.getId() == null)) {
			System.out.println(userInformation);
		}else {
			System.out.println("ユーザーが見つかりません。");
		}

		model.addAttribute("user", userInformation);
		
		return "index";
	}
}
