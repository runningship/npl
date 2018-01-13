package org.bc.participle.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScrawlTarget {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;

	public String link;

	public Date addtime;
	
	public Integer failTimes;
	
	public String lastError;
	
	/**
     * 1已爬取，0未爬取
     */
	public Integer linkScrawlStatus;
	
	/**
     * 1已爬取，0未爬取
     */
	public Integer contentScrawlStatus;

	public String title;
}
