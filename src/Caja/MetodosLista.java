package Caja;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class MetodosLista {

	static Connection con = null;
	String[] tituloslista = { "PRODUCTOS", "P", "C" };

	// método que muestra las listas de compras
	public static void MostrarLista(DefaultTableModel modeloLista, JTable tblLista, JComboBox<String> Comboseleccion,
			JTextField textArticuloCaja, JLabel ayala, JLabel barea, JLabel solarica, JLabel otros) {

		try {

			String proveselec = Comboseleccion.getSelectedItem().toString();
			if (proveselec == "Ayala") {
				ayala.setVisible(true);
				barea.setVisible(false);
				solarica.setVisible(false);
				otros.setVisible(false);
			} else if (proveselec == "Barea") {
				ayala.setVisible(false);
				barea.setVisible(true);
				solarica.setVisible(false);
				otros.setVisible(false);
			} else if (proveselec == "SolaRica") {
				ayala.setVisible(false);
				barea.setVisible(false);
				solarica.setVisible(true);
				otros.setVisible(false);
			} else if (proveselec == "Otros") {
				ayala.setVisible(false);
				barea.setVisible(false);
				solarica.setVisible(false);
				otros.setVisible(true);
			}

			con = Conexion.ejecutarConexion();
			String dts[] = new String[3];
			Statement st = con.createStatement();

			int contador = tblLista.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloLista.removeRow(i);
			}

			String sql = "SELECT ArticuloLista, Precio" + proveselec + "Lista, CategoriaLista FROM ListaCompra"
					+ proveselec;

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = rs.getString(3);

				modeloLista.addRow(dts);
			}
			rs.close();

			modeloLista.addTableModelListener(new TableModelListener() {

				// método para bambiar los datos de las celdas de la JTable
				public void tableChanged(TableModelEvent e) {

					if (e.getType() == TableModelEvent.UPDATE) {

						String columna = null;
						int n = 0;

						if (e.getColumn() == 1) {
							columna = "Precio" + proveselec + "Lista";
							n = n + 1;
						} else if (e.getColumn() == 2) {
							columna = "CategoriaLista";
							n = n + 2;
						}

						try {

							if (n > 0) {
								String sql = "UPDATE ListaCompra" + proveselec + " SET " + columna + " = '"
										+ modeloLista.getValueAt(e.getFirstRow(), e.getColumn())
										+ "' WHERE ArticuloLista  = '" + modeloLista.getValueAt(e.getFirstRow(), 0)
										+ "'";
								//System.out.println("UPDATE ListaCompra" + proveselec + " SET " + columna + " = '"
								//		+ modeloLista.getValueAt(e.getFirstRow(), e.getColumn())
								//		+ "' WHERE ArticuloLista = '" + modeloLista.getValueAt(e.getFirstRow(), 0)
								//		+ "'");
								con = Conexion.ejecutarConexion();
								PreparedStatement pst = con.prepareStatement(sql);
								pst.executeUpdate();
								pst.close();

							}

						} catch (Exception ev) {
							ev.printStackTrace();
						}
					}
				}
			});

			CambioColorLista cambiocolor = new CambioColorLista();
			tblLista.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gris oscuro
			tblLista.setGridColor(Color.darkGray);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// método que extrae los datos de la tblProductos mostrándolos en tblLista, y
	// los guarda en base de datos ListaCompra si no está repetido, en caso
	// contrario no se guarda ni se muestra
	public static void ExtraeListaAyala(DefaultTableModel modeloLista, JTable tblLista,
			JComboBox<String> Comboseleccion, JTextField textArticuloCaja) {

		try {
			String proveselec = Comboseleccion.getSelectedItem().toString();
			String artiselec = textArticuloCaja.getText();

			con = Conexion.ejecutarConexion();
			String sql = "SELECT ArticuloLista FROM ListaCompra" + proveselec + " WHERE ArticuloLista = '" + artiselec
					+ "'";
			// System.out.println("SELECT ArticuloLista FROM ListaCompra" + proveselec + "
			// WHERE ArticuloLista = '" + artiselec + "'");

			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				JOptionPane.showMessageDialog(null, "El artículo ya está en la lista de " + proveselec);

			} else {

				String producto = null;
				String precio = null;
				String categoria = null;

				if (proveselec == "SolaRica") {

					String sql1 = "SELECT Articulos, " + proveselec
							+ ", Categoria FROM tblProductos WHERE Articulos = '" + artiselec + "'";
					String dts[] = new String[5];
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql1);

					while (rs.next()) {
						dts[0] = rs.getString(1);
						producto = rs.getString(1);
						dts[1] = rs.getString(2);
						precio = rs.getString(2);
						dts[2] = rs.getString(3); // muestra y regoge los datos en la tabla para mandarlos a la
						categoria = rs.getString(3); // nueva tabla de compras
					}
					modeloLista.addRow(dts);
					rs.close();

				} else if (proveselec == "Otros") {

					String sql1 = "SELECT Articulos, " + proveselec
							+ ", Categoria FROM tblProductos WHERE Articulos = '" + artiselec + "'";
					String dts[] = new String[5];
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql1);

					while (rs.next()) {
						dts[0] = rs.getString(1);
						producto = rs.getString(1);
						dts[1] = rs.getString(2);
						precio = rs.getString(2);
						System.out.println(precio);
						dts[2] = rs.getString(3); // muestra y regoge los datos en la tabla para mandarlos a la
						categoria = rs.getString(3); // nueva tabla de compras
					}
					modeloLista.addRow(dts);
					rs.close();
				} else {

					String sql1 = "SELECT Articulos, Ayala, Categoria FROM tblProductos WHERE Articulos = '" + artiselec
							+ "'";
					String dts[] = new String[5];
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(sql1);

					while (rs.next()) {
						dts[0] = rs.getString(1);
						producto = rs.getString(1);
						dts[1] = rs.getString(2);
						precio = rs.getString(2);
						dts[2] = rs.getString(3); // muestra y regoge los datos en la tabla para mandarlos a la
						categoria = rs.getString(3); // nueva tabla de compras
					}
					modeloLista.addRow(dts);
					rs.close();
				}

				String sql2 = "INSERT INTO ListaCompra" + proveselec + "(ArticuloLista, Precio" + proveselec
						+ "Lista, CategoriaLista) VALUES (?,?,?)";

				pst = con.prepareStatement(sql2);

				pst.setString(1, producto);
				pst.setString(2, precio);
				pst.setString(3, categoria);

				pst.executeUpdate();
				pst.close();

			}
			pst.close();
			res.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un fallo");
			e.printStackTrace();
		}
	}

	// método que borra la lista completa seleccionada
	public static void BorraLista(DefaultTableModel modeloLista, JTable tblLista, JComboBox<String> Comboseleccion) {

		String select = Comboseleccion.getSelectedItem().toString();

		try {

			int eleccion = JOptionPane.showConfirmDialog(null, "Eliminar lista" + select + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (JOptionPane.OK_OPTION == eleccion) {
				String sql = "DELETE FROM ListaCompra" + select; // borra todas las filas y libera el espacio de estas
				PreparedStatement pst = con.prepareStatement(sql);

				pst.execute();
				pst.close();

				int contador = tblLista.getRowCount() - 1;
				for (int i = contador; i >= 0; i--) {
					modeloLista.removeRow(i);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// método para insertar manualmente un artículo en la lista seleccionada
	public static void IngresoListaManual(DefaultTableModel modeloLista, JComboBox<String> Comboseleccion,
			JTextField txtnuevoarticulolista, JComboBox<String> Combocategoria) {

		String select = Comboseleccion.getSelectedItem().toString();
		String txtcadenaarticulos = txtnuevoarticulolista.getText();
		String txtcadenacategoria = Combocategoria.getSelectedItem().toString();

		try {
			
			con = Conexion.ejecutarConexion();
			
			String sql = "INSERT INTO ListaCompra" + select + "(ArticuloLista,CategoriaLista) VALUES (?,?)";
			//System.out.println("INSERT INTO ListaCompra" + select + "(ArticuloLista,CategoriaLista) VALUES (?,?)");
			
			String dts[] = new String[3];

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, txtcadenaarticulos);
			pst.setString(2, txtcadenacategoria);

			pst.execute();
			pst.close();

			dts[0] = txtcadenaarticulos;
			dts[2] = txtcadenacategoria;

			modeloLista.addRow(dts);

			txtnuevoarticulolista.setText("");
			Combocategoria.setSelectedIndex(-1);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");
			e.printStackTrace();
		}
	}

	// método para eliminar la fila seleccionada
	public static void EliminarFila(ActionEvent ev, DefaultTableModel modeloLista, JTable tblLista,
			JComboBox<String> Comboseleccion) {
		try {

			String prove = Comboseleccion.getSelectedItem().toString();

			int fila = tblLista.getSelectedRow();
			String producto = modeloLista.getValueAt(fila, 0).toString();

			con = Conexion.ejecutarConexion();

			String sql = "DELETE FROM ListaCompra" + prove + " WHERE ArticuloLista = '" + producto + "'";
			//System.out.println("DELETE FROM ListaCompra" + prove + " WHERE ArticuloLista = '" + producto + "'");

			PreparedStatement pst = con.prepareStatement(sql);
			pst.executeUpdate();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe de seleccionar un producto");
		}
	}

	// método para guardar en formato txt la tabla de compra tblLista
	public static void guardaListaCompra(JTable tblLista, JComboBox seleccion) {
		try {

			String Lista = "C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\lista_compra.txt";
			BufferedWriter bfw = new BufferedWriter(new FileWriter(Lista));

			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("       Listado de compra en " + seleccion.getSelectedItem().toString());
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();

			for (int i = 0; i < tblLista.getRowCount(); i++) { // realiza un barrido por filas.
				for (int j = 0; j < tblLista.getColumnCount(); j++) { // realiza un barrido por columnas.

					if (tblLista.getValueAt(i, j) != null) {
						if (j == 0) {
							String temporal = tblLista.getValueAt(i, j).toString();
							String tempo = temporal + "                                         ";
							String temp = tempo.substring(0, 28);
							bfw.write(temp);
						} else {
							String temporal = tblLista.getValueAt(i, j).toString();
							String tempo = temporal + "                                         ";
							String temp = tempo.substring(0, 10);
							bfw.write(temp);
						}
					} else {
						String tempo = "VACIO" + "                                    ";
						String temp = tempo.substring(0, 10);
						bfw.write(temp);
					}
					bfw.write("\t"); // inserta un tabulado
				}
				bfw.newLine(); // inserta nueva linea.
			}
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("             FIN DE LISTA                 ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.close(); // cierra archivo
			System.out.println("El archivo fue salvado correctamente!");
		} catch (IOException e) {
			System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
		}
	}

}
