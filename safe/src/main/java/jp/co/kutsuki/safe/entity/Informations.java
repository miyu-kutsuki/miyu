package jp.co.kutsuki.safe.entity;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブルのエンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Informations {

	/** missing_personsテーブルを保管するリスト */
	private ArrayList<MissingPersons> missingPersonsList;

	/** missing_persons_sightingsテーブルを保管するリスト */
	private ArrayList<MissingPersonsSightings> missingPersonsSightingsList;

	/** suspicious_person_sightingテーブルを保管するリスト */
	private ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList;

}
