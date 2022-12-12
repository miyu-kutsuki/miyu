package jp.co.kutsuki.safe.safedb.repository;

import org.springframework.data.repository.CrudRepository;

import jp.co.kutsuki.safe.entity.Questions;

/**
 * questionsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface QuestionsCrudRepository extends CrudRepository<Questions, Integer>{
	
}
