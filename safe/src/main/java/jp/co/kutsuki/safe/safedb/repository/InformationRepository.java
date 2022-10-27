package jp.co.kutsuki.safe.safedb.repository;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface InformationRepository {
	
	public void setInformationTable(Informations informations, RedirectAttributes redirectAttributes) ;
}
