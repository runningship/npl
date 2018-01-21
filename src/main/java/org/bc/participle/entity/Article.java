package org.bc.participle.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public Integer crawTargetId;

	public String title;
	
	public String text;
	
	public String link;

	public Date addtime;

	public Integer trainAccount;
	
	public Date lastTrainTime;
	
	/**
	 * 兴趣度
	 */
	public Float interest;
	
	/**
	 * 判断文章是否再也找不出超过分数要求的词
	 * 1,完成 0,未完成
	 */
	public Integer trainOver;
}
