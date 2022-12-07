package jp.co.kutsuki.safe.database.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersons;
import jp.co.kutsuki.safe.entity.User;
import jp.co.kutsuki.safe.safedb.repository.MissingPersonsRepository;

/**
 * missing_personsテーブルのDAOクラス
 * @author kutsuki
 *
 */
@Repository
public class MissingPersonsDao implements MissingPersonsRepository {

	@Autowired
	private JdbcTemplate template;

	/** missing_personsテーブルに1件登録 */
	@Transactional
	@Override
	public void setMissingPersonsTable(MissingPersons missingPersons) {
		//SQL定義
		String sql = "insert into missing_persons(date, name, gender, age, detail, prefectures, municipalities, other, user_id)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//SQL実行し登録を実施
		template.update(sql, missingPersons.getDate(), missingPersons.getName(), missingPersons.getGender(),
				missingPersons.getAge(),
				missingPersons.getDetail(), missingPersons.getPrefectures(),
				missingPersons.getMunicipalities(), missingPersons.getOther(), missingPersons.getUser_id());
	}

	/** missing_personsテーブルから
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersons> getMissingPersonsTable() {
		//SQL定義
		String sql = " select * from missing_persons where end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql);
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}

		return missingPersonsList;
	}

	/** missing_personsテーブルから
	 * ログインユーザーとuser_idが一致したデータの
	 * end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersons> getMissingPersonsTable(User user) {
		//SQL定義
		String sql = " select * from missing_persons where user_id = ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, user.getUser_id());
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}
		return missingPersonsList;
	}

	/** missing_personsテーブルから
	 * 範囲指定された日付＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersons> getDateMissingPersonsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons where date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}
		return missingPersonsList;
	}

	/** missing_personsテーブルから
	 * 指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersons> getPlaceMissingPersonsTable(DateSearch  dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%");
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}
		return missingPersonsList;
	}

	/** missing_personsテーブルから
	 * 範囲指定された日付＋指定された場所名＋end_flag==falseのみ全件取得 */
	@Override
	public ArrayList<MissingPersons> getDatePlaceMissingPersonsTable(DateSearch dateSearch) {
		//SQL定義
		String sql = " select * from missing_persons where (prefectures like ? or municipalities like ? or other like ?) "
				+ "and date >= ? and date <= ? and end_flag = false order by date ASC";
		//SQL実行し取得を実施
		SqlRowSet rs = template.queryForRowSet(sql, "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%", "%" + dateSearch.getSearchPlace() + "%",
				dateSearch.getStartDate(), dateSearch.getEndDate());
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}
		return missingPersonsList;
	}

	/** missing_personsテーブルから
	 * idが一致したデータのみ取得 */
	@Override
	public ArrayList<MissingPersons> getMissingPersonsIdTable(String id) {
		//SQL定義
		String sql = " select * from missing_persons where id = ?";
		//SQL実行し取得を実施
		Integer listId = Integer.valueOf(id);
		SqlRowSet rs = template.queryForRowSet(sql, listId);
		//結果を取得
		ArrayList<MissingPersons> missingPersonsList = new ArrayList<>();
		//rs(SQL実行取得結果)がtrueの場合のみ処理する
		while (rs.next()) {
			MissingPersons missingPersons = new MissingPersons();
			missingPersons.setId(rs.getInt("id"));
			// Date型からLocaldate型へ変換
			LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
			missingPersons.setDate(date);
			missingPersons.setName(rs.getString("name"));
			missingPersons.setGender(rs.getString("gender"));
			missingPersons.setAge(rs.getInt("age"));
			missingPersons.setDetail(rs.getString("detail"));
			missingPersons.setPrefectures(rs.getString("prefectures"));
			missingPersons.setMunicipalities(rs.getString("municipalities"));
			missingPersons.setOther(rs.getString("other"));
			missingPersons.setUser_id(rs.getString("user_id"));
			missingPersonsList.add(missingPersons);
		}
		return missingPersonsList;
	}

	/**
	 * idで指定された行のデータを更新する
	 */
	@Transactional
	@Override
	public void Update(String id, MissingPersons missingPersons) {
		//SQL定義
		String sql = "update missing_persons "
				+ "set(date, name, gender, age, detail, prefectures, municipalities, other)=(?, ?, ?, ?, ?, ?, ?, ?) where id = ?";
		//SQL実行し登録を実施
		Integer listId = Integer.valueOf(id);
		template.update(sql, missingPersons.getDate(), missingPersons.getName(), missingPersons.getGender(),
				missingPersons.getAge(),
				missingPersons.getDetail(), missingPersons.getPrefectures(),
				missingPersons.getMunicipalities(), missingPersons.getOther(), listId);
	}

	/**
	 * idで指定された行のend_flagにtrueをセットする
	 * @param id
	 */
	@Transactional
	@Override
	public void Delete(String id) {
		//SQL定義
		String sql = "update missing_persons set end_flag = true where id = ?";
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
		String sql = "update missing_persons set user_id = 'guests' where user_id = ? and end_flag = false";
		//SQL実行し登録を実施
		template.update(sql, user_id);
	}
}