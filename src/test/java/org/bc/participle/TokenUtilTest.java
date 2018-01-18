package org.bc.participle;

import junit.framework.Assert;

import org.junit.Test;

public class TokenUtilTest {

	@Test
	public void testIsChineseString(){
		Assert.assertFalse(TokenUtils.isChinese("报》"));
		
		Assert.assertFalse(TokenUtils.isChinese("□“"));
		
		Assert.assertFalse(TokenUtils.isChinese("　　"));
		
		Assert.assertFalse(TokenUtils.isChinese("FC-1"));
	}
	
}
