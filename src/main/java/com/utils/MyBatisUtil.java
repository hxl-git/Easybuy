package com.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis工具类
 * @author hp
 */
public class MyBatisUtil  {
	private static SqlSessionFactory factory;
	//1读取全局配置文件
	private static 	String resource = "MyBatis-config.xml";
	private static InputStream is=null;
	static {
		try {
			 is= Resources.getResourceAsStream(resource);
			factory=new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取SqlSession实例
	 */
	public static SqlSession getSqlSessionFactory() {
		return factory.openSession(false); //false 事务自动提交
	}
	
	/**
	 * 释放资源
	 */
	public static void closeSqlSession(SqlSession SqlSession) {
		if(SqlSession!=null) {
			SqlSession.close();
		}
		
	}
	
	/**
	 * 事务提交
	 * @param args
	 */
	public static void commit(SqlSession SqlSession) {
		SqlSession.commit();
	}
	/**
	 *事务回滚
	 * @param args
	 */
	public static void rollback(SqlSession SqlSession) {
		SqlSession.rollback();
	}
	
	public static void main(String[] args) {
		if(getSqlSessionFactory()==null) {
			System.out.println("1");
		}else {
			System.out.println("2");
		}
	}
	
	
}
