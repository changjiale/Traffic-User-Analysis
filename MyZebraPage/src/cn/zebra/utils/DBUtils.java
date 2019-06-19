package cn.zebra.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {
	private static ComboPooledDataSource source = new ComboPooledDataSource();
	
	private DBUtils() {
	}

	public static DataSource getSource(){
		return source;
	}
	
	public static Connection getConn(){
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
