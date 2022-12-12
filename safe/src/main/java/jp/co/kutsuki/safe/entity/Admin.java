package jp.co.kutsuki.safe.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * adminテーブル用エンティティ
 * @author kutsuki
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

	/** adminテーブル用識別ID */
	@Id
	private Integer id;

	/** 管理者ID */
	@NotBlank(message = "管理者IDが入力されていません。")
	@Length(min=4, max=15, message = "管理者IDは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "管理者IDは『半角英数』『_』『-』のみ使用可能です。")
	private String admin_id;

	/** パスワード */
	@NotBlank(message = "パスワードが入力されていません。")
	@Length(min=4, max=20, message = "パスワードは{min}〜{max}桁で入力して下さい。")
	@Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "パスワードは『半角英数』『_』『-』のみ使用可能です。")
	private String password;

	/** 管理者の削除フラグ
	 * false:使用中
	 * true:終了 */
	private Boolean end_flag;
}
