package jp.co.kutsuki.safe.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * usersテーブル用エンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	/** userテーブル用識別ID */
	@Id
	private Integer id;
	
	/** ユーザーID */
	private String user_id;
	
	/** パスワード */
	private String password;
	
	/** ユーザーの削除フラグ
	 * false:使用中
	 * true:終了 */
	private Boolean end_flag;
	
}
