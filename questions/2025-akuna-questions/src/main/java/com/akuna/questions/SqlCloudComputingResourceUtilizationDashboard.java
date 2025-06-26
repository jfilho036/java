package com.akuna.questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCloudComputingResourceUtilizationDashboard {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// TODO do I need this?
		Class.forName("com.mysql.cj.jdbc.Driver"); // For Connector/J 8.x

		try (Connection conn = setupConnection();
				Statement stmt = conn.createStatement()) {

			createDatabase(stmt);

			populateDatabase(stmt);

			ResultSet rs = queryDatabase(stmt);

			printResultSet(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static Connection setupConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/metrics_db";
		String user = "metrics_user";
		String password = "metrics_pass";
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	private static void populateDatabase(Statement stmt) throws SQLException {
		// Step 3: Insert sample data (Feb 2024 and other months)
		stmt.executeUpdate("""
				    INSERT INTO cpu_metrics VALUES
				    (1, '2024-02-10 10:00:00', 70.5),
				    (1, '2024-02-11 10:00:00', 65.0),
				    (2, '2024-02-10 10:00:00', 40.0),
				    (2, '2024-01-10 10:00:00', 90.0),
				    (3, '2024-02-12 10:00:00', 55.0)
				""");

		stmt.executeUpdate("""
				    INSERT INTO memory_metrics VALUES
				    (1, '2024-02-10 10:00:00', 80.0),
				    (1, '2024-02-12 10:00:00', 45.0),
				    (2, '2024-02-11 10:00:00', 60.0),
				    (3, '2024-02-13 10:00:00', 49.0)
				""");
	}

	private static void createDatabase(Statement stmt) throws SQLException {
		// Step 1: Drop existing tables
		stmt.executeUpdate("DROP TABLE IF EXISTS cpu_metrics");
		stmt.executeUpdate("DROP TABLE IF EXISTS memory_metrics");

		// Step 2: Create tables
		stmt.executeUpdate("""
				    CREATE TABLE cpu_metrics (
				        application_id INT,
				        timestamp DATETIME,
				        usage_percentage DECIMAL(5,2)
				    )
				""");

		stmt.executeUpdate("""
				    CREATE TABLE memory_metrics (
				        application_id INT,
				        timestamp DATETIME,
				        usage_percentage DECIMAL(5,2)
				    )
				""");
	}

	private static ResultSet queryDatabase(Statement stmt) throws SQLException {
		// Step 4: Run the query
		String query = """
				    SELECT
				        resource_type,
				        application_id,
				        AVG(usage_percentage) AS average_usage_percentage
				    FROM (
				        SELECT 'cpu' AS resource_type, application_id, timestamp, usage_percentage
				        FROM cpu_metrics
				        WHERE timestamp >= '2024-02-01' AND timestamp < '2024-03-01'

				        UNION ALL

				        SELECT 'memory' AS resource_type, application_id, timestamp, usage_percentage
				        FROM memory_metrics
				        WHERE timestamp >= '2024-02-01' AND timestamp < '2024-03-01'
				    ) AS all_metrics
				    GROUP BY resource_type, application_id
				    HAVING AVG(usage_percentage) > 50
				    ORDER BY application_id, resource_type
				""";

		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}

	private static void printResultSet(ResultSet rs) throws SQLException {
		System.out.printf("%-10s %-15s %-25s%n", "Type", "App ID", "Avg Usage (%)");
		System.out.println("--------------------------------------------------");

		while (rs.next()) {
			String type = rs.getString("resource_type");
			int appId = rs.getInt("application_id");
			double avgUsage = rs.getDouble("average_usage_percentage");

			System.out.printf("%-10s %-15d %-25.2f%n", type, appId, avgUsage);
		}
	}

}
