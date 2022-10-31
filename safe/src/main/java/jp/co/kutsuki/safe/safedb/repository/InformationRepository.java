package jp.co.kutsuki.safe.safedb.repository;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.Informations;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface InformationRepository {
	
	public void setInformationTable(Informations informations) ;
	
	public void setDateInformationTable(Informations informations, DateSearch dateSearch);
}
