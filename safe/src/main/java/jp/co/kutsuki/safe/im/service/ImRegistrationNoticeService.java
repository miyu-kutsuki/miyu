package jp.co.kutsuki.safe.im.service;

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * RegistrationNoticeServiceのインタフェース
 * @author kutsuki
 *
 */
public interface ImRegistrationNoticeService {
	
	public void missingPersonsRegistrationNoticeMailSend(MissingPersonsSightings missingPersonsSightings);
}
