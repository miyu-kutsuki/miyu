package jp.co.kutsuki.safe.im.service;

import jp.co.kutsuki.safe.entity.User;
/**
 * MailSendServiceクラスのインタフェース
 * @author kutsuki
 *
 */
public interface ImMailSendService {

	public void mailSend(User user, String title ,String template);
}
