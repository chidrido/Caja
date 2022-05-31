package Caja;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MetodosArticulosWeb {

	static Connection con = null;

	// método para mostrar los productos, pvp y stock de la base de datos
	// (tblProductosWeb)
	public void Mostrar(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb) {

		try {
			String[] titulos = { "Cod.Barras", "ArticulosWeb", "PVP", "Stock", "CategoriaWeb", "Borrar", "",};
			con = Conexion.ejecutarConexion();

			Object dts[] = new Object[7];
			String sql = "SELECT * FROM tblProductos WHERE Booleano = True";
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(8));
				dts[3] = rs.getInt(9);
				dts[4] = rs.getString(19);
				dts[6] = rs.getObject(18, Boolean.class);

				modeloArticulosWeb.addRow(dts);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//método que muestra los datos actualizados para pasarlos a CSV
	public void MostrarActualizados(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb) {

		try {
			String[] titulos = { "Cod.Barras", "ArticulosWeb", "PVP", "Stock", "Borrar", "" };
			con = Conexion.ejecutarConexion();

			Object dts[] = new Object[7];
			String sql = "SELECT * FROM tblProductos WHERE Booleano = True AND ActualizaWeb = True";
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(8));
				dts[3] = rs.getInt(9);
				dts[4] = rs.getString(19);
				dts[6] = rs.getObject(18, Boolean.class);

				modeloArticulosWeb.addRow(dts);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//método que muestra los datos de las diferentes categorias
	public void MostrarPorCategoriasWeb(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb, JComboBox<String> comboseleccion) {

		try {
			String seleccion = comboseleccion.getSelectedItem().toString();
			
			String[] titulos = { "Cod.Barras", "ArticulosWeb", "PVP", "Stock", "Borrar", "" };
			con = Conexion.ejecutarConexion();

			Object dts[] = new Object[7];
			String sql = "SELECT * FROM tblProductos WHERE Booleano = True AND CategoriaWeb = '" + seleccion + "'";
			//System.out.println("SELECT * FROM tblProductos WHERE Booleano = True AND CategoriaWeb = '" + seleccion + "'");
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(8));
				dts[3] = rs.getInt(9);
				dts[4] = rs.getString(19);
				dts[6] = rs.getObject(18, Boolean.class);

				modeloArticulosWeb.addRow(dts);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//método para borrar todos los CheckBox
	public void LimpiarCheckBox(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb) {
		
		int eleccion = JOptionPane.showConfirmDialog(null, "Quiere desmarcar los productos actualizados?", "Confirmar",
				JOptionPane.YES_NO_OPTION);
		if (JOptionPane.OK_OPTION == eleccion) {
		
			try {
				con = Conexion.ejecutarConexion();

				for (int i = 0; i < tblArticulosWeb.getRowCount(); i++) {

					if (tblArticulosWeb.getValueAt(i, 6) != "True") {
						
						String producto = modeloArticulosWeb.getValueAt(i, 0).toString();
						String sql = "UPDATE tblProductos SET ActualizaWeb = False WHERE Codigo = '" + producto + "'";

						PreparedStatement pst = con.prepareStatement(sql);
						pst.executeUpdate();
						pst.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// método para exportar JTable de ArticulosWeb
	public static void ExportarJTable(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb) {

		try {
			File archivo_csv = new File("C:\\Users\\chidr\\OneDrive\\Desktop\\famisuper\\famisuper\\aplicaciones\\administrador\\static\\CSV\\Archivo_csv.csv");
				if (archivo_csv.exists()) {
					archivo_csv.delete();
					FileWriter fichero = new FileWriter("C:\\Users\\chidr\\OneDrive\\Desktop\\famisuper\\famisuper\\aplicaciones\\administrador\\static\\CSV\\Archivo_csv.csv");
					
					for (int i = 0; i < tblArticulosWeb.getColumnCount() - 2; i++) {
				
							fichero.write(tblArticulosWeb.getColumnName(i) + ";");
				
					}
					fichero.write("\n");
					
					for (int i = 0; i < tblArticulosWeb.getRowCount(); i++) {
						for (int j = 0; j < tblArticulosWeb.getColumnCount() - 2; j++) {
							fichero.write(tblArticulosWeb.getValueAt(i, j) + ";");
						}
						fichero.write("\n");
					}
					fichero.close();
				}else {
					
					FileWriter fichero = new FileWriter("C:\\Users\\chidr\\OneDrive\\Desktop\\famisuper\\famisuper\\aplicaciones\\administrador\\static\\CSV\\Archivo_csv.csv");
			
					for (int i = 0; i < tblArticulosWeb.getColumnCount() - 2; i++) {
				
							fichero.write(tblArticulosWeb.getColumnName(i) + ";");
				
					}
					fichero.write("\n");
					
					for (int i = 0; i < tblArticulosWeb.getRowCount(); i++) {
						for (int j = 0; j < tblArticulosWeb.getColumnCount() - 2; j++) {
							fichero.write(tblArticulosWeb.getValueAt(i, j) + ";");
						}
						fichero.write("\n");
					}
					fichero.close();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
