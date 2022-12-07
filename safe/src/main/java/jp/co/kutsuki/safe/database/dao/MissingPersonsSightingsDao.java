package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsSightingsRepository;

/**
 * missing_persons_sightingsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class MissingPersonsSightingsDao implements MissingPersonsSightingsRepository{

	@Autowired
	private JdbcTemplate template;

	/** missing_persons_sightingsテーブルに1件登録 */
	@Transactional
	@Override
	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings) {
		//SQL定義
		String sql = "insert into missing_persons_sightings(date, gender, age, detail, prefectures, municipalities, other, user_id) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL実行し登録を実施
		template.update(sql, missingPersonsSightings.getDate(), missingPersonsSightings.getGender(), missingPersonsSightings.getAge(),
				missingPersonsSightings.getDetail(), missingPersonsSightings.getPrefectures(),
				missingPersonsSightings.getMunicipalities(), missingPersonsSightings.getOther(), missingPersonsSightings.getUser_id());
	}

	/** missing_persons_sightingsテーブルから
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable() {
		//SQL定義
		String sql = "select * from missing_persons_sightings where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		return missingPersonsSightingsList;
	}

	/** missing_persons_sightingsテーブルから
	 * ログインユーザーとuser_idが一致したデータの
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable(User user) {
		//SQL定義
		String sql = "select * from missing_persons_sightings where user_id = ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, user.getUser_id());
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}

		return missingPersonsSightingsList;
	}

	/** missing_persons_sightingsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = "select * from missing_persons_sightings where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while(rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		return missingPersonsSightingsList;
	}

	/** missing_persons_sightingsテーブルから
	 * 指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getPlaceMissingPersonsSightingsTable(DateSearch  dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons_sightings where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%");
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		return missingPersonsSightingsList;
	}

	/** missing_persons_sightingsテーブルから
	 * 範囲指定された日付＋指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getDatePlaceMissingPersonsSightingsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons_sightings where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%",
				dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
			missingPersonsSightings.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersonsSightings.setDate(date);
			missingPersonsSightings.setGender(rs.getString("gender"));
			missingPersonsSightings.setAge(rs.getInt("age"));
			missingPersonsSightings.setDetail(rs.getString("detail"));
			missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
			missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
			missingPersonsSightings.setOther(rs.getString("other"));
			missingPersonsSightings.setUser_id(rs.getString("user_id"));
			missingPersonsSightingsList.add(missingPersonsSightings);
		}
		return missingPersonsSightingsList;
	}

	/** missing_persons_sightingsテーブルから
	 * idが一致したデータのみ取得 */
	@Override
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsIdTable(String id) {
		//SQL定義
		String sql = "select * from missing_persons_sightings where id = ?";
		//SQL実行し取得を実施
		Integer listId = Integer.valueOf(id);
		SqlRowSet rs = template.queryForRowSet(sql, listId);
		//結果を取得
		ArrayList<MissingPersonsSightings> missingPersonsSightingsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		if(!rs.isLast()) {
			while(rs.next()) {
				MissingPersonsSightings missingPersonsSightings = new MissingPersonsSightings();
				missingPersonsSightings.setId(rs.getInt("id"));
				// Date型からLocaldate型へ変換
				LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
				missingPersonsSightings.setDate(date);
				missingPersonsSightings.setGender(rs.getString("gender"));
				missingPersonsSightings.setAge(rs.getInt("age"));
				missingPersonsSightings.setDetail(rs.getString("detail"));
				missingPersonsSightings.setPrefectures(rs.getString("prefectures"));
				missingPersonsSightings.setMunicipalities(rs.getString("municipalities"));
				missingPersonsSightings.setOther(rs.getString("other"));
				missingPersonsSightings.setUser_id(rs.getString("user_id"));
				missingPersonsSightingsList.add(missingPersonsSightings);
			}
		}
		return missingPersonsSightingsList;
	}

	/**
	 * idで指定された行のデータを更新する
	 */
	@Transactional
	@Override
	public void Update(String id, MissingPersonsSightings missingPersonsSightings) {
		//SQL定義
		String sql = "update missing_persons_sightings "
				+ "set(date, gender, age, detail, prefectures, municipalities, other)=(?, ?, ?, ?, ?, ?, ?) where id = ?";
		//SQL実行し登録を実施
		Integer listId = Integer.valueOf(id);
		template.update(sql, missingPersonsSightings.getDate(), missingPersonsSightings.getGender(), missingPersonsSightings.getAge(),
				missingPersonsSightings.getDetail(), missingPersonsSightings.getPrefectures(),
				missingPersonsSightings.getMunicipalities(), missingPersonsSightings.getOther(), listId);
	}

	/**
	 * idで指定された行のend_flagにtrueをセットする
	 * @param id
	 */
	@Transactional
	@Override
	public void Delete(String id) {
		//SQL定義
		String sql = "update missing_persons_sightings set end_flag = true where id = ?";
		//SQL実行し登録を実施
		Integer listId = Integer.valueOf(id);
		template.update(sql, listId);
	}

	/** ユーザーが退会した場合
	 * 該当ユーザーが登録した行方不明者情報のユーザーを"guests"に変更する */
	@Transactional
	@Override
	public void DeleteUser(String user_id) {
		//SQL定義
		String sql = "update missing_persons_sightings set user_id = 'guests' where user_id = ? and end_flag = false";
		//SQL実行し登録を実施
		template.update(sql, user_id);
	}
}