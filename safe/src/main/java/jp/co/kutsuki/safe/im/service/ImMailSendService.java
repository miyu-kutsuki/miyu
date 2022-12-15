package jp.co.kutsuki.safe.im.service;

import jp.co.kutsuki.safe.entity.User;

public interface ImMailSendService {
	
	public void mailSend(User user, String title ,String template);
}
