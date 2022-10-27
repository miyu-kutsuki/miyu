package jp.co.kutsuki.safe.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.Informations;
import jp.co.kutsuki.safe.safedb.repository.InformationRepository;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

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
	
	/** missing_personsテーブル
	 * missing_persons_sightingsテーブル
	 * suspicious_person_sightingテーブルから
	 * end_flag==falseのみ全件取得  */
	public void setInformationTable(Informations informations, RedirectAttributes redirectAttributes) {
		//missing_personsテーブルのend_flag==falseのみを取得
		informations.setMissingPersonsList(missingPersonsRepository.getMissingPersonsTable(redirectAttributes));
	}

}
