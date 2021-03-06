package org.bc.npl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	public String x;
	
	public String f;
	
	public String y;
	
	public Integer sentenceId;
	
	public Integer answered;
}
