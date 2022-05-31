package Caja;

import java.sql.*;
import java.sql.DriverManager;

// creando la clase conexion para conectar a la base de datos en Access

public class Conexion {
	static Connection conn = null;
	static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";

	static String url = "jdbc:ucanaccess://C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\Database3.accdb";

	public static Connection ejecutarConexion() {
		try {
			if (conn == null) {
				Class.forName(driver);
				conn = DriverManager.getConnection(url);
				// JOptionPane.showMessageDialog(null, "Conectado");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			conn = null;
		}
		return conn;
	}
}
