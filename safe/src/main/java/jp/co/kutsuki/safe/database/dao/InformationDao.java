package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class InformationDao implements InformationRepository{
	
	@Autowired
	MissingPersonsRepository  missingPersonsRepository;
	
	@Autowired
	MissingPersonsSightingsRepository  missingPersonsSightingsRepository;
	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingテーブルから
	 * end_flag==falseのみ全件取得  */
	public void setInformationTable(Informations informations) {
		//missing_personsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getMissingPersonsTable());
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getMissingPersonsSightingsTable());
	}
	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingテーブルから
	 * end_flag==falseのみ全件取得  */
	public void setDateInformationTable(Informations informations, DateSearch dateSearch) {
		//missing_personsテーブルの範囲指定された日付＋end_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getDateMissingPersonsTable(dateSearch));
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getDateMissingPersonsSightingsTable(dateSearch));
	}
	
	


}
