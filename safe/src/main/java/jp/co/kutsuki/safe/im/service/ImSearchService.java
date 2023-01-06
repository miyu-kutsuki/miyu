package jp.co.kutsuki.safe.im.service;

import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;
/**
 * SearchServiceクラスのインタフェース
 * @author kutsuki
 *
 */
public interface ImSearchService {

	public void allSearchExcute(List<String> msgList, Informations informations, boolean missingPersons,boolean missingPersonsSightings,
			boolean suspiciousPersonSightings, boolean place, RedirectAttributes redirectAttributes);

	public void userIdSearchExcute(String user_id, Informations informations, RedirectAttributes redirectAttributes);
}
