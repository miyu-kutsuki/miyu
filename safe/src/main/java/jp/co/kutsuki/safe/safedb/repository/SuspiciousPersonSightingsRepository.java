package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.entity.User;

/**
 * suspicious_person_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface SuspiciousPersonSightingsRepository {
	
	public void setSuspiciousPersonSightingsTable(SuspiciousPersonSightings suspiciousPersonSightings);
	
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable();
	
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable(User user_id);
	
	public ArrayList<SuspiciousPersonSightings> getDateSuspiciousPersonSightingsTable(DateSearch dateSearch);
	
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsIdTable(String id);
	
	public void Update(String id, SuspiciousPersonSightings suspiciousPersonSightings);
	
	public void Delete(String id);
}
