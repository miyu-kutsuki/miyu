package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.SuspiciousPersonSightings;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.SuspiciousPersonSightingsRepository;

/**
 * suspicious_person_sightingsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class SuspiciousPersonSightingsDao implements SuspiciousPersonSightingsRepository{
	
	@Autowired
	private JdbcTemplate template;
	
	/** suspicious_person_sightingsテーブルに1件登録 */
	@Transactional
	@Override
	public void setSuspiciousPersonSightingsTable(SuspiciousPersonSightings suspiciousPersonSightings) {
		//SQL定義
		String sql = "insert into suspicious_person_sightings(date, gender, age, detail, prefectures, municipalities, other, user_id) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL実行し登録を実施
		template.update(sql, suspiciousPersonSightings.getDate(), suspiciousPersonSightings.getGender(), suspiciousPersonSightings.getAge(),
				suspiciousPersonSightings.getDetail(), suspiciousPersonSightings.getPrefectures(), 
				suspiciousPersonSightings.getMunicipalities(), suspiciousPersonSightings.getOther(), suspiciousPersonSightings.getUser_id());
	}
	
	/** suspicious_person_sightingsテーブルから
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable() {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
			suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
			suspiciousPersonSightings.setOther(rs.getString("other"));
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		
		return suspiciousPersonSightingsList;
	}
	
	/** suspicious_person_sightingsテーブルから
	 * ログインユーザーとuser_idが一致したデータの
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsTable(User user) {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where user_id = ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, user.getUser_id());
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
			suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
			suspiciousPersonSightings.setOther(rs.getString("other"));
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		
		return suspiciousPersonSightingsList;
	}
	
	/** suspicious_person_sightingsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getDateSuspiciousPersonSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
			suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
			suspiciousPersonSightings.setOther(rs.getString("other"));
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		return suspiciousPersonSightingsList;
	}
	
	/** suspicious_person_sightingsテーブルから
	 * 指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getPlaceSuspiciousPersonSightingsTable(DateSearch  dateSearch) {
		//SQL定義
		String sql = "select * from suspicious_person_sightings where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%");
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
			suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
			suspiciousPersonSightings.setOther(rs.getString("other"));
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		return suspiciousPersonSightingsList;
	}

	/** suspicious_person_sightingsテーブルから
	 * 範囲指定された日付＋指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getDatePlaceSuspiciousPersonSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = "select * from suspicious_person_sightings where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", 
				dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
			suspiciousPersonSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			suspiciousPersonSightings.setDate(date);
			suspiciousPersonSightings.setGender(rs.getString("gender"));
			suspiciousPersonSightings.setAge(rs.getInt("age"));
			suspiciousPersonSightings.setDetail(rs.getString("detail"));
			suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
			suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
			suspiciousPersonSightings.setOther(rs.getString("other"));
			suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
			suspiciousPersonSightingsList.add(suspiciousPersonSightings);
		}
		return suspiciousPersonSightingsList;
	}
	
	/** missing_persons_sightingsテーブルから
	 * idが一致したデータのみ取得 */
	@Override
	public ArrayList<SuspiciousPersonSightings> getSuspiciousPersonSightingsIdTable(String id) {
		//SQL定義
		String sql = " select * from suspicious_person_sightings where id = ?";
		//SQL実行し取得を実施
		Integer listId = Integer.valueOf(id);
		SqlRowSet rs = template.queryForRowSet(sql, listId);
		//結果を取得
		ArrayList<SuspiciousPersonSightings> suspiciousPersonSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		if(!rs.isLast()) {
			while(rs.next()) {
				SuspiciousPersonSightings suspiciousPersonSightings = new SuspiciousPersonSightings();
				suspiciousPersonSightings.setId(rs.getInt("id"));
				// Date型からLocaldate型へ変換
				LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
				suspiciousPersonSightings.setDate(date);
				suspiciousPersonSightings.setGender(rs.getString("gender"));
				suspiciousPersonSightings.setAge(rs.getInt("age"));
				suspiciousPersonSightings.setDetail(rs.getString("detail"));
				suspiciousPersonSightings.setPrefectures(rs.getString("prefectures"));
				suspiciousPersonSightings.setMunicipalities(rs.getString("municipalities"));
				suspiciousPersonSightings.setOther(rs.getString("other"));
				suspiciousPersonSightings.setUser_id(rs.getString("user_id"));
				suspiciousPersonSightingsList.add(suspiciousPersonSightings);
			}
		}
		return suspiciousPersonSightingsList;
	}
	
	/**
	 * idで指定された行のデータを更新する
	 */
	@Transactional
	@Override
	public void Update(String id, SuspiciousPersonSightings suspiciousPersonSightings) {
		//SQL定義
		String sql = "update suspicious_person_sightings "
				+ "set(date, gender, age, detail, prefectures, municipalities, other)=(?, ?, ?, ?, ?, ?, ?) where id = ?";
		//SQL実行し登録を実施
		Integer listId = Integer.valueOf(id);
		template.update(sql, suspiciousPersonSightings.getDate(), suspiciousPersonSightings.getGender(), suspiciousPersonSightings.getAge(),
				suspiciousPersonSightings.getDetail(), suspiciousPersonSightings.getPrefectures(), 
				suspiciousPersonSightings.getMunicipalities(), suspiciousPersonSightings.getOther(), listId);
	}
	
	/**
	 * idで指定された行のend_flagにtrueをセットする
	 * @param id
	 */
	@Transactional
	@Override
	public void Delete(String id) {
		//SQL定義
		String sql = "update suspicious_person_sightings set end_flag = true where id = ?";
		//SQL実行し登録を実施
		Integer listId = Integer.valueOf(id);
		template.update(sql, listId);
	}

}