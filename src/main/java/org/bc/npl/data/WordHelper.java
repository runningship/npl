package org.bc.npl.data;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bc.npl.StartUpListener;
import org.bc.npl.entity.Word;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.SimpDaoTool;

public class WordHelper {

	private static  CommonDaoService dao = SimpDaoTool.getGlobalCommonDaoService();
	public static void main(String[] args) throws IOException, URISyntaxException{
		StartUpListener.initDataSource();
		importZi();
	}
	public  static void importZi() throws IOException, URISyntaxException{
		List<String> lines = FileUtils.readLines(new File(WordHelper.class.getResource("zi.txt").toURI()));
		for(String line : lines){
			for(String zi : line.split(" ")){
				if(StringUtils.isEmpty(zi)){
					continue;
				}
				addZiToDB(zi);
				System.out.println(zi);
			}
		}
	}
	
	private static void addZiToDB(String zi){
		Word po = dao.getUniqueByKeyValue(Word.class, "text", zi);
		if(po==null){
			Word word = new Word();
			word.text = zi;
			dao.saveOrUpdate(word);
		}
	}
}
