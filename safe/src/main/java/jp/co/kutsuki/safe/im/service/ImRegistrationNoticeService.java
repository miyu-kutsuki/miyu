package jp.co.kutsuki.safe.im.service;

import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * RegistrationNoticeServiceのインタフェース
 * @author kutsuki
 *
 */
public interface ImRegistrationNoticeService {
	
	public void missingPersonsRegistrationNoticeMailSend(MissingPersonsSightings missingPersonsSightings);
	
	public void suspiciousPersonSightingsRegistrationNoticeMailSend(SuspiciousPersonSightings suspiciousPersonSightings);
}
