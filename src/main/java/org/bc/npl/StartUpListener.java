package org.bc.npl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.JspFactory;

import org.bc.cache.ConfigCache;
import org.bc.participle.ThreadSessionHelper;
import org.bc.sdak.MutilSessionFactoryBuilder;
import org.bc.sdak.SQL2008Dialect;
import org.bc.sdak.SessionFactoryBuilder;
import org.bc.sdak.SessionFactoryMapper;
import org.bc.web.ModuleManager;
import org.bc.web.PublicFieldSupportingELResolver;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;

public class StartUpListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		initDataSource();
		initModule();
		JspFactory.getDefaultFactory()
        .getJspApplicationContext(event.getServletContext())
        .addELResolver(new PublicFieldSupportingELResolver());
	}

	private void initModule() {
		ModuleManager.add("org.bc.rhino");
		ModuleManager.add("org.bc.npl");
	}

	public static synchronized void initDataSource(){
		MutilSessionFactoryBuilder.sfm = new SessionFactoryMapper(){
			@Override
			public String getKey() {
				String dbType = ThreadSessionHelper.getDbType();
				if(dbType==null){
					dbType = ThreadSessionHelper.Sql_Server_Db;
				}
				return dbType;
			}

			@Override
			public Map<String, String> getSettings() {
				Map<String,String> settings = new HashMap<String , String>();
//				settings.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/ihouse?characterEncoding=utf-8");
//				settings.put(AvailableSettings.USER, "root");
//				settings.put(AvailableSettings.PASS, "");
				settings.put(AvailableSettings.SHOW_SQL, "false");
//				settings.put(AvailableSettings.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//				settings.put(AvailableSettings.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
				
//				settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.SQLServer2008Dialect");
				if(ThreadSessionHelper.Sql_Server_Db.equals(getKey())){
					settings.put(AvailableSettings.DIALECT, SQL2008Dialect.class.getName());
				}else if(ThreadSessionHelper.H2_Db.equals(getKey())){
					settings.put(AvailableSettings.DIALECT, H2Dialect.class.getName());
				}
				
				
//				settings.put(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");
//				settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
				
				settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
				settings.put(AvailableSettings.POOL_SIZE, "1");
				settings.put(AvailableSettings.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.EhCacheRegionFactory");
				settings.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, "true");
				
//				settings.put(AvailableSettings.PROXOOL_XML, "proxool.xml");//相对目录为classes
				settings.put(AvailableSettings.PROXOOL_XML, ConfigCache.get(getKey()+"_proxool_xml", getKey()+"_proxool.xml"));//相对目录为classes
				settings.put(AvailableSettings.PROXOOL_EXISTING_POOL, "false");
				settings.put(AvailableSettings.PROXOOL_POOL_ALIAS, getKey()+"_npl");
				SessionFactoryBuilder.applySettings(settings);
				//org.bc.npl;org.bc.textreco;
				settings.put("annotated.packages", "org.bc.participle.entity");
				return settings;
			}
		};
		
	}
}
