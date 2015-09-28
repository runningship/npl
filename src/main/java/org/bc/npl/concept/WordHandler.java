package org.bc.npl.concept;

public abstract class WordHandler<T> {

	private T target;

	public Class<?> getTargetClass(){
		return target.getClass();
	}

	public abstract Object process(T obj2);
	
}
