package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * suspicious_person_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface SuspiciousPersonSightingsRepository {
	
	public void setSuspiciousPersonSightingsTable(SuspiciousPersonSightings suspiciousPersonSightings);
	
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable();
	
	public ArrayList<SuspiciousPersonSightings> getDateSuspiciousPersonSightingsTable(DateSearch dateSearch);
}
