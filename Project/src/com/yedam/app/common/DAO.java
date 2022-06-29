package com.yedam.app.common;

import java.sql.*;


public class DAO {

	// Oracle 연결 정보
	private String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	private String oracle_url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String connectedId = "hr";
	private String connectedpwd = "hr";
	// 각 메소드에서 공통적으로 사용하는 필드

	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	public void connect() {
//		dbConfig();
		try {
			// 1. JDBC Driver 로딩
			Class.forName(jdbc_driver);
			// 2. DB 서버 접속
			conn = DriverManager.getConnection(oracle_url, connectedId, connectedpwd);
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver 로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB 접속 실패");
		}
	}

/*	private void dbConfig() {
		String resource = "config/db.properties";
		Properties properties = new Properties();

		try {
			String filePath = ClassLoader.getSystemClassLoader().getResource(resource).getPath();
			properties.load(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbc_driver = properties.getProperty("driver");
		oracle_url = properties.getProperty("url");
		connectedId = properties.getProperty("id");
		connectedpwd = properties.getProperty("password");
	}
*/
	// 4. 자원 해제
	public void disconnect() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
