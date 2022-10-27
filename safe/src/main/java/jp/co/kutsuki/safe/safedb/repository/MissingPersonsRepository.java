package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersons;

/**
 * missing_personsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsRepository {
	
	public void setMissingPersonsTable(MissingPersons missingPersons) ;
	
	public ArrayList<MissingPersons> getMissingPersonsTable(RedirectAttributes redirectAttributes) ;
	
	public ArrayList<MissingPersons> getDateMissingPersonsTable(DateSearch dateSearch, RedirectAttributes redirectAttributes) ;
	
}
