package jp.co.kutsuki.safe.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
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
	@NotBlank(message = "ユーザーIDが入力されていません。")
	@Length(min=5, max=15, message = "ユーザーIDは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "ユーザーIDは『半角英数』『_』『-』のみ使用可能です。")
	private String user_id;

	/** パスワード */
	@NotBlank(message = "パスワードが入力されていません。")
	@Length(min=5, max=20, message = "パスワードは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "パスワードは『半角英数』『_』『-』のみ使用可能です。")
	private String password;

	/** ユーザーの削除フラグ
	 * false:使用中
	 * true:終了 */
	private Boolean end_flag;

}
