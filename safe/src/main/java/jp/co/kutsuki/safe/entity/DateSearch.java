package jp.co.kutsuki.safe.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * missing_personsテーブル
 * missing_persons_sightingsテーブル
 * suspicious_person_sightingテーブル
 * 日付検索用エンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateSearch {
	
	/** 行方不明日の範囲指定start */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	
	/** 行方不明の範囲指定end */
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;


}
