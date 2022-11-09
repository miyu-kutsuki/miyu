package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class InformationDao implements InformationRepository{
	
	@Autowired
	MissingPersonsRepository  missingPersonsRepository;
	
	@Autowired
	MissingPersonsSightingsRepository  missingPersonsSightingsRepository;
	
	@Autowired
	SuspiciousPersonSightingsRepository suspiciousPersonSightingsRepository;
	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingsテーブルから
	 * end_flag==falseのみ全件取得  */
	@Override
	public void setInformationTable(Informations informations) {
		//missing_personsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getMissingPersonsTable());
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getMissingPersonsSightingsTable());
		//suspicious_person_sightingsテーブルのend_flag==falseのみを取得
		informations.setSuspiciousPersonSightingsList(suspiciousPersonSightingsRepository.getSuspiciousPersonSightingsTable());
	}
	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingsテーブルから
	 * ログインユーザーとuser_idが一致するデータ
	 * end_flag==falseのみ全件取得  */
	@Override
	public void setInformationTable(Informations informations, User user) {
		//missing_personsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getMissingPersonsTable(user));
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getMissingPersonsSightingsTable(user));
		//suspicious_person_sightingsテーブルのend_flag==falseのみを取得
		informations.setSuspiciousPersonSightingsList(suspiciousPersonSightingsRepository.getSuspiciousPersonSightingsTable(user));
	}

	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingsテーブルから
	 * 指定された日付の範囲の
	 * end_flag==falseのみ全件取得  */
	@Override
	public void setDateInformationTable(Informations informations, DateSearch dateSearch) {
		//missing_personsテーブルの範囲指定された日付＋end_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getDateMissingPersonsTable(dateSearch));
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getDateMissingPersonsSightingsTable(dateSearch));
		//suspicious_person_sightingsテーブルのend_flag==falseのみを取得
		informations.setSuspiciousPersonSightingsList(suspiciousPersonSightingsRepository.getDateSuspiciousPersonSightingsTable(dateSearch));
	}
	
	/**
	 * missing_personsテーブルid指定されたデータを取得
	 * @param informations
	 * @param id
	 */
	@Override
	public void setMissingPersonsTable(Informations informations, String id) {
		//missing_personsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getMissingPersonsIdTable(id));
	}
	
	/**
	 * missing_persons_sightingsテーブルid指定されたデータを取得
	 * @param informations
	 * @param id
	 */
	@Override
	public void setMissingPersonsSightingsTable(Informations informations, String id) {
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsSightingsList(missingPersonsSightingsRepository.getMissingPersonsSightingsIdTable(id));
	}
	
	/**
	 * suspicious_person_sightingsテーブルid指定されたデータを取得
	 * @param informations
	 * @param id
	 */
	@Override
	public void setSuspiciousPersonSightingsTable(Informations informations, String id) {
		//missing_persons_sightingsテーブルのend_flag==falseのみを取得
		informations.setSuspiciousPersonSightingsList(suspiciousPersonSightingsRepository.getSuspiciousPersonSightingsIdTable(id));

	}

}
