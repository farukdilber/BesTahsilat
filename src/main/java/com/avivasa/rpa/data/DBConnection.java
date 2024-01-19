package com.avivasa.rpa.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.SkipException;

import com.avivasa.rpa.base.AbstractTest;
import com.avivasa.rpa.util.DataFinder;
import com.avivasa.rpa.utiliy.EmailSend;
import com.avivasa.rpa.utiliy.ExtentTestManager;
import com.avivasa.rpa.utiliy.log;
import com.relevantcodes.extentreports.LogStatus;

@com.mongodb.annotations.ThreadSafe
@javax.annotation.concurrent.ThreadSafe
@net.jcip.annotations.ThreadSafe
public final class DBConnection extends AbstractTest {

	static final String ORACLE_DRIVER = DataFinder.getDBInfo("ORACLE_DRIVER");
	static final String DB_URL3 = DataFinder.getDBInfo("DB_URL3");
	static final String USER3 = DataFinder.getDBInfo("USER3");
	static final String PASS3 = DataFinder.getDBInfo("PASS3");

	static final String JDBC_DRIVER = DataFinder.getDBInfo("JDBC_DRIVER");
	static final String DB_URL = DataFinder.getDBInfo("DB_URL");
	static final String USER = DataFinder.getDBInfo("USER");
	static final String PASS = DataFinder.getDBInfo("PASS");

	static final String JDBC_DRIVER2 = "com.ibm.db2.jcc.DB2Driver";
	static final String DB_URL2 = "jdbc:db2://192.168.149.25:50000/PCRM";
	static final String USER2 = "BTROBOT";
	static final String PASS2 = "VAc3WDVa";

	static final String DB_URL5 = "jdbc:as400://172.21.3.20;naming=sql;errors=full;";
	static final String USER5 = "BTROB5";
	static final String PASS5 = "BTROB51";
	static final String JDBC_DRIVER5 = "com.ibm.as400.access.AS400JDBCDriver";

	//static final String DB_URL6 = "jdbc:db2://192.168.149.25:50000/PCRM";
	static final String DB_URL6 = "jdbc:db2://avspcrm01.avivasa.local:50000/PCRM";
	static final String USER6 = "rpapcrm";
	static final String PASS6 = "drmP3d6B";
	static final String JDBC_DRIVER6 = "com.ibm.db2.jcc.DB2Driver";

	public static Connection conn = null;
	public static List<String[]> table = null;
	public static PreparedStatement stmt = null;
	public static ResultSet rs = null;

	public static PreparedStatement pstmt = null;

	public static List<String[]> selectOracle(String sql) throws SQLException {
		Connection conn = null;
		List<String[]> table = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(ORACLE_DRIVER);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL3, USER3, PASS3);
			log.info("Creating statement...");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			table = resultToTable(rs);
			log.info("Oracle Select success");

		} catch (Exception e) {

			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error SelectOracle : " + e);
			log.error("Error SelectOracle : " + e);

		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}
		return table;
	}

	public static List<String[]> selectTCRM(String sql) throws SQLException {
		Connection conn = null;
		List<String[]> table = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER2);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL2, USER2, PASS2);
			log.info("Creating statement...");

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			table = resultToTable(rs);
			log.info("TCRM Select success");

		} catch (Exception e) {

			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error SelectTCRM : " + e);
			log.error("Error SelectTCRM : " + e);

		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}
		return table;
	}

	public static List<String[]> selectPCRMCanli(String sql) throws SQLException {
		Connection conn = null;
		List<String[]> table = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(JDBC_DRIVER6);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL6, USER6, PASS6);
			log.info("Creating statement...");

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			table = resultToTable(rs);
			log.info("PCRM Select success");

		} catch (Exception e) {

			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error SelectPCRM : " + e);
			log.error("Error SelectTCRM : " + e);

		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		}
		return table;
	}

	public static synchronized void updateTCRM(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName(JDBC_DRIVER2);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL2, USER2, PASS2);

			log.info("Creating statement...");
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			log.info("TCRM Updated success");

		} catch (Exception e) {
			log.error("Error UpdateTCRM : " + e);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error UpdateTCRM : " + e);
			Assert.assertTrue(false);
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

//	public static List<String[]> selectAs400(String sql) throws SQLException {
//		Connection conn = null;
//		List<String[]> table = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			Class.forName(JDBC_DRIVER);
//			log.info("Connecting to database...");
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//			log.info("Creating statement...");
//
//			stmt = conn.prepareStatement(sql);
//			rs = stmt.executeQuery();
//			table = resultToTable(rs);
//			log.info("As400 Select success");
//
//		} catch (Exception e) {
//			log.error("Error SelectAs400 : " + e);
//			throw new SkipException("Error: SelectAs400 başarısız.");
//
//		} finally {
//			if (stmt != null)
//				stmt.close();
//			if (conn != null)
//				conn.close();
//			if (rs != null)
//				rs.close();
//		}
//
//		return table;
//	}

	public static List<String[]> selectAs400(String sql) throws SQLException {

		try {
			log.info("Creating statement...");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			table = resultToTable(rs);
			log.info("As400 Select success");

		} catch (Exception e) {
			log.error("Error SelectAs400 : " + e);
			EmailSend.as400SelectHatasi();
			throw new SkipException("Error: SelectAs400 başarısız.");
		}

		return table;
	}

	public static void openDB() {

		try {
			Class.forName(JDBC_DRIVER5);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL5, USER5, PASS5);

		} catch (Exception e) {
			log.error("Open DB Error : " + e);
			EmailSend.as400LoginHatasi();
			throw new SkipException("Error: Open DB Error.");
		}

	}

	public static void closeDB() throws SQLException {

		try {
			if (pstmt != null)
				pstmt.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			log.error("Close DB Error : " + e);
			EmailSend.as400CloseHatasi();
			throw new SkipException("Error: Close DB Error.");
		}
	}

	public static synchronized void updateAS400(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			log.info("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			log.info("Creating statement...");
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			log.info("AS400 Update success");

		} catch (Exception e) {
			log.error("Error UpdateAS400 : " + e);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Error UpdateAS400 : " + e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	private static List<String[]> resultToTable(ResultSet rs) throws SQLException {
		int nCol = rs.getMetaData().getColumnCount();
		List<String[]> table = new ArrayList<>();
		while (rs.next()) {
			String[] row = new String[nCol];
			for (int iCol = 1; iCol <= nCol; iCol++) {
				Object obj = rs.getObject(iCol);
				row[iCol - 1] = (obj == null) ? null : obj.toString();
			}
			table.add(row);
		}
		return table;
	}
}
