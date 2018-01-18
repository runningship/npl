package org.bc.participle;

import org.bc.npl.StartUpListener;
import org.bc.participle.entity.ScrawlTarget;
import org.junit.Test;

public class ScrawlTest {

	@Test
	public void testScrawlContent() throws Exception{
		StartUpListener.initDataSource();
		ScrawlService ss = new ScrawlService();
		ScrawlTarget ct = new ScrawlTarget();
		ct.link = "https://baike.baidu.com/item/%E5%A4%A7%E6%98%AD%E5%AF%BA";
		ss.scrawlContent(ct);
	}
	
	@Test
	public void testStartScrawlContent() throws Exception{
		StartUpListener.initDataSource();
		ScrawlService ss = new ScrawlService();
		ss.startScrawlContent();
	}
	
	@Test
	public void testStartScrawlContentUntiEnd() throws Exception{
		StartUpListener.initDataSource();
		ScrawlService ss = new ScrawlService();
		int count = 0;
		do{
			count = ss.startScrawlContent();
		}while(count>0);
	}
	
	@Test
	public void testScrawlLinks(){
		StartUpListener.initDataSource();
		ScrawlService ss = new ScrawlService();
		ss.startScrawlLinks();
	}
}
