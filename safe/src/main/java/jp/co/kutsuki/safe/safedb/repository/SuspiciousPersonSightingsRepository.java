package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.FormLogin;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;

/**
 * suspicious_person_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface SuspiciousPersonSightingsRepository {

	public void setSuspiciousPersonSightingsTable(SuspiciousPersonSightings suspiciousPersonSightings);

	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable();

	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable(FormLogin user_id);

	public ArrayList<SuspiciousPersonSightings> getDateSuspiciousPersonSightingsTable(DateSearch dateSearch);

	public ArrayList<SuspiciousPersonSightings> getPlaceSuspiciousPersonSightingsTable(DateSearch  dateSearch);

	public ArrayList<SuspiciousPersonSightings> getDatePlaceSuspiciousPersonSightingsTable(DateSearch dateSearch);

	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsIdTable(String id);

	public void update(String id, SuspiciousPersonSightings suspiciousPersonSightings);

	public void delete(String id);

	public void deleteUser(String user_id);
}