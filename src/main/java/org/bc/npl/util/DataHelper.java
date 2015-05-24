package org.bc.npl.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.bc.npl.core.Block;
import org.bc.npl.entity.Aggregation;
import org.bc.sdak.SimpDaoTool;
import org.bc.sdak.utils.LogUtil;
import org.bc.web.ThreadSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DataHelper {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	public static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMMdd");
	public static final String User_Default_Password = "123456";
	private static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	static{
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}
	
	public static boolean isSubConceptOf(String child , String text){
		List<Map> list = SimpDaoTool.getGlobalCommonDaoService().listAsMap("select elem as elem , sets as sets from Aggregation aggr where elem=? and sets=? " , child , text );
		if(list==null || list.isEmpty()){
			return false;
		}
		return true;
	}
	
	public static JSONObject toDrawableTree(Block block){
		JSONObject root = new JSONObject();
		root.put("text", block.text);
		if(block.left!=null){
			root.put("left", toDrawableTree(block.left));
		}
		if(block.right!=null){
			root.put("right", toDrawableTree(block.right));
		}
		return root;
	}
}
