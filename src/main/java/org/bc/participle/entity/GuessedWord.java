package org.bc.participle.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GuessedWord {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	public String name;
	
	public String leftToken;
	
	public String rightToken;
	
	public Integer score;
	
	public Integer articleId;

	@Override
	public String toString() {
		return name;
	}
	
}
