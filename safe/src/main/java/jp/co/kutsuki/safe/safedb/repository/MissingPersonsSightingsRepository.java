package jp.co.kutsuki.safe.safedb.repository;

import java.util.ArrayList;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.kutsuki.safe.entity.DateSearch;
import jp.co.kutsuki.safe.entity.MissingPersonsSightings;

/**
 * missing_persons_sightingsテーブルのリポジトリ
 * @author kutsuki
 *
 */
public interface MissingPersonsSightingsRepository {
	
	public void setMissingPersonsSightingsTable(MissingPersonsSightings missingPersonsSightings);
	
	public ArrayList<MissingPersonsSightings> getMissingPersonsSightingsTable(RedirectAttributes redirectAttributes);
	
	public ArrayList<MissingPersonsSightings> getDateMissingPersonsSightingsTable
	(DateSearch dateSearch, RedirectAttributes redirectAttributes);
}
