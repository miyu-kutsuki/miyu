package jp.co.kutsuki.safe.safedb.repository;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.User;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface InformationRepository {
	
	public void setInformationTable(Informations informations);
	
	public void setInformationTable(Informations informations, User user_id);
	
	public void setDateInformationTable(Informations informations, DateSearch dateSearch);
	
	public void setPlaceInformationTable(Informations informations, DateSearch dateSearch);
	
	public void setDatePlaceInformationTable(Informations informations, DateSearch dateSearch);
	
	public void setMissingPersonsTable(Informations informations, String id);
	
	public void setMissingPersonsSightingsTable(Informations informations, String id);
	
	public void setSuspiciousPersonSightingsTable(Informations informations, String id);
}
