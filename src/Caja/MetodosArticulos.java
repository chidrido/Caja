package Caja;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class MetodosArticulos {

	static Connection con = null;
	JButton eliminar = new JButton("Eliminar");

	// metodo mostrar articulos de tblProductos en JTable
	public static void Mostrar(JTable tbl) {

		try {
			String[] titulos = { "Codigo barras", "Articulo", "PVP", "%", "Ayala", "Barea", "SolaRica", "Otros",
					"Stock", "Stkmin", "Categoria", "Borrar", ""};
			con = Conexion.ejecutarConexion();

			DefaultTableModel modeloArticulo = new DefaultTableModel(null, titulos);

			Object dts[] = new Object[13];
			String sql = "SELECT * FROM tblProductos";
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(8));
				dts[3] = rs.getString(7);
				dts[4] = formato.format(rs.getFloat(3));
				dts[5] = formato.format(rs.getFloat(4));
				dts[6] = formato.format(rs.getFloat(5));
				dts[7] = formato.format(rs.getFloat(6));
				dts[8] = rs.getString(9);
				dts[9] = rs.getString(10);
				dts[10] = rs.getString(11);
				dts[12] = rs.getObject(17, Boolean.class);

				modeloArticulo.addRow(dts);
			}
			rs.close();
			st.close();
			
			modeloArticulo.addTableModelListener(new TableModelListener() {

				// metodo para cambiar los datos de las celdas de la JTable
				public void tableChanged(TableModelEvent e) {

					if (e.getType() == TableModelEvent.UPDATE) {

						String columna = null;
						int n = 0;

						if (e.getColumn() == 0) { // asignamos un indice para poder usar SQL más facilmente
							columna = "Codigo";
						} else if (e.getColumn() == 1) {
							columna = "Articulos";
							n = 1;
						} else if (e.getColumn() == 2) {
							columna = "PVP";
							n = 2;
						} else if (e.getColumn() == 3) {
							columna = "Porcentaje";
							n = 3;
						} else if (e.getColumn() == 4) {
							columna = "Ayala";
							n = 4;
						} else if (e.getColumn() == 5) {
							columna = "Barea";
							n = 5;
						} else if (e.getColumn() == 6) {
							columna = "SolaRica";
							n = 6;
						} else if (e.getColumn() == 7) {
							columna = "Otros";
							n = 7;
						} else if (e.getColumn() == 8) {
							columna = "Stock";
							n = 8;
						} else if (e.getColumn() == 9) {
							columna = "Stkmin";
							n = 9;
						} else if (e.getColumn() == 10) {
							columna = "Categoria";
							n = 10;
						} else if (e.getColumn() == 11) {
							columna = "Eliminar";
							n = 11;
						} else if (e.getColumn() == 12) {
							columna = "Booleano";
							n = 12;
						}

						try {
							if (n == 0) {
								if(modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
									String sql0 = "UPDATE tblProductos SET " + columna + " = null WHERE Articulos = '" + modeloArticulo.getValueAt(e.getFirstRow(), 1) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Articulos = '" + modeloArticulo.getValueAt(e.getFirstRow(), 1) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql0);
									pst.executeUpdate();
									pst.close();
								}else {
									String sql0 = "UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
										+ "' WHERE Articulos = '" + modeloArticulo.getValueAt(e.getFirstRow(), 1) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
									//	+ "' WHERE Articulos = '" + modeloArticulo.getValueAt(e.getFirstRow(), 1) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql0);
									pst.executeUpdate();
									pst.close();
								}
							} else if (n == 1) {
								if(modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
									String sql1 = "UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql1);
									pst.executeUpdate();
									pst.close();
								}else {
									String sql1 = "UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
										+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
									//+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql1);
									pst.executeUpdate();
									pst.close();
								}
							}else if (n == 2) {
								if(modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
									String sql1 = "UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql1);
									pst.executeUpdate();
									pst.close();
											
									ActualizaWeb(modeloArticulo, e);
								}else {
									String sql1 = "UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
										+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
									//+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst = con.prepareStatement(sql1);
									pst.executeUpdate();
									pst.close();
											
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();											
											
									ActualizaWeb(modeloArticulo, e);
								}
							} else if (n >= 3 && n <= 10 ) {
								if(modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
									String sql2 = "UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst1 = con.prepareStatement(sql2);
									pst1.executeUpdate();
									pst1.close();									
									
								}else {
									String sql2 = "UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
										+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET " + columna + " = '" + modeloArticulo.getValueAt(e.getFirstRow(), e.getColumn())
									//+ "' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst1 = con.prepareStatement(sql2);
									pst1.executeUpdate();
									pst1.close();
								}
							} else if (n == 11) {
								int eleccion = JOptionPane.showConfirmDialog(null,
										"Esta seguro de que quiere eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
								if (JOptionPane.OK_OPTION == eleccion) {
									String sql11 = "DELETE FROM tblProductos WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("DELETE FROM tblProductos WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
									con = Conexion.ejecutarConexion();
									PreparedStatement pst11 = con.prepareStatement(sql11);
									pst11.executeUpdate();
									pst11.close();

									int fila = e.getFirstRow();
									modeloArticulo.removeRow(fila);
								}
							} else if (n == 12) {
								int seleccion = JOptionPane.showOptionDialog(null, "Selecciona la categoría de la página Web", "Selector de Distribuidores",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] 
										{ "Alimentación", "Droguería", "Cámara", "Bebida", "Frutería", "Cancelar" }, // null para YES, NO y CANCEL
										"opcion 1");
								String cadenaseleccion = String.valueOf(seleccion);

								if (cadenaseleccion.equals("0")) {
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();			
									
									String sql13 = "UPDATE tblProductos SET CategoriaWeb = 'Alimentacion' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET CategoriaWeb = Alimentación WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

									con = Conexion.ejecutarConexion();
									PreparedStatement pst13 = con.prepareStatement(sql13);
									pst13.executeUpdate();
									pst13.close();
								} else if (cadenaseleccion.equals("1")) {
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();		
									
									String sql13 = "UPDATE tblProductos SET CategoriaWeb = 'Drogueria' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Drogueria' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

									con = Conexion.ejecutarConexion();
									PreparedStatement pst13 = con.prepareStatement(sql13);
									pst13.executeUpdate();
									pst13.close();
								} else if (cadenaseleccion.equals("2")) {
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();					
									
									String sql13 = "UPDATE tblProductos SET CategoriaWeb = 'Camara' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Camara' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

									con = Conexion.ejecutarConexion();
									PreparedStatement pst13 = con.prepareStatement(sql13);
									pst13.executeUpdate();
									pst13.close();
								} else if (cadenaseleccion.equals("3")) {
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();				
									
									String sql13 = "UPDATE tblProductos SET CategoriaWeb = 'Bebida' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Fruta' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

									con = Conexion.ejecutarConexion();
									PreparedStatement pst13 = con.prepareStatement(sql13);
									pst13.executeUpdate();
									pst13.close();
								}else if (cadenaseleccion.equals("4")) {
									String sql12 = "UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET Booleano = true WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");
											
									con = Conexion.ejecutarConexion();
									PreparedStatement pst12 = con.prepareStatement(sql12);
									pst12.executeUpdate();
									pst12.close();			
									
									String sql14 = "UPDATE tblProductos SET CategoriaWeb = 'Fruteria' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
									//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Bebida' WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

									con = Conexion.ejecutarConexion();
									PreparedStatement pst14 = con.prepareStatement(sql14);
									pst14.executeUpdate();
									pst14.close();
								}
							}							
						} catch (SQLException ev) {
							JOptionPane.showMessageDialog(null, "Error al actualizar");
							ev.printStackTrace();
						}
					}
				}
			});

			tbl.setModel(modeloArticulo);

			JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
			th = tbl.getTableHeader();
			Font fuente = new Font(null, Font.PLAIN, 13);
			th.setFont(fuente);
			
			TableColumn agregarColumn;			
			agregarColumn = tbl.getColumnModel().getColumn(11); // llama a los metodos para mostrar el boton eliminar
			agregarColumn.setCellEditor(new EditorCeldas(tbl));
			agregarColumn.setCellRenderer(new Renderizado(true));
			
			agregarColumn = tbl.getColumnModel().getColumn(12); // llama a los metodos para mostrar el chekbox
			agregarColumn.setCellEditor(tbl.getDefaultEditor(Boolean.class));
			agregarColumn.setCellRenderer(tbl.getDefaultRenderer(Boolean.class));

			CambioColor cambiocolor = new CambioColor();
			tbl.setDefaultRenderer(Object.class, cambiocolor); // muestra color en la celda señalada
			tbl.setGridColor(Color.darkGray);

			// da tamaño a las casillas
			tbl.getColumn(tbl.getModel().getColumnName(0)).setMaxWidth(145);
			tbl.getColumn(tbl.getModel().getColumnName(1)).setMaxWidth(370);
			tbl.getColumn(tbl.getModel().getColumnName(2)).setMaxWidth(60);
			tbl.getColumn(tbl.getModel().getColumnName(3)).setMaxWidth(50);
			tbl.getColumn(tbl.getModel().getColumnName(4)).setMaxWidth(60);
			tbl.getColumn(tbl.getModel().getColumnName(5)).setMaxWidth(60);
			tbl.getColumn(tbl.getModel().getColumnName(6)).setMaxWidth(60);
			tbl.getColumn(tbl.getModel().getColumnName(7)).setMaxWidth(60);
			tbl.getColumn(tbl.getModel().getColumnName(8)).setMaxWidth(50);
			tbl.getColumn(tbl.getModel().getColumnName(9)).setMaxWidth(50);
			tbl.getColumn(tbl.getModel().getColumnName(10)).setMaxWidth(90);
			tbl.getColumn(tbl.getModel().getColumnName(11)).setMaxWidth(80);
			tbl.getColumn(tbl.getModel().getColumnName(12)).setMaxWidth(30);
			tbl.setFont(new java.awt.Font(null, 0, 18));
			tbl.setRowHeight(28);

			tbl.setCellSelectionEnabled(true); // selecciona una sola casilla
			tbl.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
			tbl.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

		} catch (Exception ex) {

			ex.printStackTrace();

		}
	}
	
	//método para activar el checkbox de actualización en la sección ArticulosWeb
	public static void ActualizaWeb(DefaultTableModel modeloArticulo, TableModelEvent e){
		try {
			String sql = "SELECT Articulos FROM tblProductos WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "' AND Booleano = True";
			//System.out.println("SELECT Articulos FROM tblProductos WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "' AND Booleano = True");

			con = Conexion.ejecutarConexion();
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				String sqlWeb = "UPDATE tblProductos SET ActualizaWeb = True WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'";
				//System.out.println("UPDATE tblProductos SET ActualizaWeb = True WHERE Codigo = '" + modeloArticulo.getValueAt(e.getFirstRow(), 0) + "'");

				con = Conexion.ejecutarConexion();
				PreparedStatement pstWeb = con.prepareStatement(sqlWeb);
				pstWeb.executeUpdate();
				pstWeb.close();
		}
		pst.close();
		res.close();
		}catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	// método para insertar articulos con todas sus caracteristicas en la base de datos
	public void Guardar(KeyEvent evt, JTextField textCod, JTextField textArt, JTextField textPre, JTextField textUni,
			JComboBox<?> menuProve, JTextField textPorcen, JTextField textPV, JTextField textSt, JTextField textStm,
			JComboBox<?> menuCate) throws SQLException {

		String proveedor = menuProve.getSelectedItem().toString();

		try {
			con = Conexion.ejecutarConexion();
			
			String sql = "INSERT INTO tblProductos(Codigo, Articulos, " + proveedor +", Porcentaje, PVP, Stock, Stkmin, Categoria) VALUES (?,?,?,?,?,?,?,?)";
			System.out.println("INSERT INTO tblProductos(Codigo, Articulos, " + proveedor +", Porcentaje, PVP, Stock, Stkmin, Categoria) VALUES (?,?,?,?,?,?,?,?)");
				
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, textCod.getText());
			
			if(textArt.getText().toString().length() != 0) {
				pst.setString(2, textArt.getText());
			}else {
				pst.setString(2, null);
			}
			if(textPre.getText().toString().length() != 0) {
				pst.setFloat(3, Float.parseFloat(textPre.getText()));
			}else {
				pst.setNull(3, Types.FLOAT);
			}
			if(textPorcen.getText().toString().length() != 0) {
				pst.setFloat(4, Float.parseFloat(textPorcen.getText()));
			}else {
				pst.setNull(4, Types.FLOAT);
			}
			if (textPV.getText().length() != 0) {
				pst.setFloat(5, Float.parseFloat(textPV.getText()));
			}else {
				pst.setNull(5, Types.FLOAT);
			}
			if (textSt.getText().length() == 0) {
				pst.setString(6, null);
			} else {
				pst.setInt(6, Integer.parseInt(textSt.getText()));
			}
			if (textStm.getText().length() == 0) {
				pst.setString(7, null);
			} else {
				pst.setInt(7, Integer.parseInt(textStm.getText()));
			}
			if(menuCate.getItemCount() == 0) {
				pst.setString(8, menuCate.getSelectedItem().toString());
			}else {
				pst.setString(8, null);
			}
			
			pst.executeUpdate();
			pst.close();
			JOptionPane.showMessageDialog(null, "Se ha guardado satisfactoriamente");
			
			
			textCod.setText(""); // borra todos los campos
			textArt.setText("");
			textPre.setText("");
			textUni.setText("");
			textPorcen.setText("");
			textPV.setText("");
			textSt.setText("");
			textStm.setText("");
			menuCate.setSelectedIndex(-1);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Hay un error, el único campo imprescindible es el de Código");
			ex.printStackTrace();
		}
	}
	

	// metodo para mostrar en JComboBox los datos de la tabla categoria
	public static ArrayList<String> LlenarCombo() {
		con = Conexion.ejecutarConexion();
		ArrayList<String> lista = new ArrayList<String>();
		String q = "SELECT * FROM tblProductos";
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = con.createStatement();
			rs1 = st1.executeQuery(q);
		} catch (Exception e) {
			System.out.println("Hay un error");
		}

		try {
			while (rs1.next()) {
				lista.add(rs1.getString("Categoria"));
			}

		} catch (Exception e) {
		}

		return lista;
	}
}
