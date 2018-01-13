package org.bc.participle;

import java.net.URL;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bc.participle.entity.Article;
import org.bc.participle.entity.ScrawlTarget;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.Page;
import org.bc.sdak.Transactional;
import org.bc.sdak.TransactionalServiceHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrawlService{

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
    private void doScrawlLinks(ScrawlTarget ct){
        try {
            URL url = new URL(ct.link);
            Document doc = Jsoup.parse(url, 5000);
            crawlLinks(doc , url);
        } catch (Exception e) {
            e.printStackTrace();
            if(ct.failTimes==null){
                ct.failTimes = 1;
            }else{
                ct.failTimes++;
            }
            ct.lastError = e.getMessage();
        }
        ct.linkScrawlStatus = 1;
        dao.saveOrUpdate(ct);
    }
    
    public void startScrawlContent() throws Exception{
    	Page<ScrawlTarget> page = new Page<ScrawlTarget>();
    	page = dao.findPage(page, "from ScrawlTarget where contentScrawlStatus = ? or contentScrawlStatus is null", 0);
    	for(ScrawlTarget target : page.result){
    		scrawlContent(target);
    	}
    }
    
    @Transactional
    public void scrawlContent(ScrawlTarget ct) throws Exception{
    	URL url = new URL(ct.link);
        Document doc = Jsoup.parse(url, 5000);
        String body = doc.body().select(".main-content .para").text();
        if(StringUtils.isEmpty(body)){
            System.out.println();
        }
        //要判断重复
        Article article = new Article();
        article.addtime = new Date();
        article.link = ct.link;
        article.text = body;
        dao.saveOrUpdate(article);
        ct.contentScrawlStatus = 1;
    }
    
    public void startScrawlLinks(){
    	Page<ScrawlTarget> page = new Page<ScrawlTarget>();
    	page = dao.findPage(page, "from ScrawlTarget where linkScrawlStatus = ? or linkScrawlStatus is null", 0);
    	for(ScrawlTarget target : page.result){
    		doScrawlLinks(target);
    	}
    }
    
    private void crawlLinks(Document doc , URL parent){
        Elements links = doc.body().select(".main-content a");
        for (Element link : links) {
            System.out.println(link.text() + link.attr("href"));
            String href = link.attr("href");
            if(!href.contains("item")){
                continue;
            }
            ScrawlTarget target = new ScrawlTarget();
            target.link = "https://baike.baidu.com"+href;
            ScrawlTarget po = dao.getUnique(ScrawlTarget.class , target);
            if(po==null){
                target.linkScrawlStatus = 0;
                target.title = link.text();
                target.addtime = new Date();
                dao.saveOrUpdate(target);
            }else{
            	po.linkScrawlStatus = 1;
            }
        }
    }
}
