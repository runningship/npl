package org.bc.participle;

public class ThreadSessionHelper {

	private static ThreadLocal<String> dbType = new ThreadLocal<String>();
	
	public static final String Sql_Server_Db = "Sql_Server_Db";
	
	public static final String H2_Db = "H2_Db";
	
	public static String getDbType(){
		return dbType.get();
	}
	
	public static void setDbType(String db){
		dbType.set(db);
	}
	
	public static void restore(){
		dbType.remove();
	}
}
