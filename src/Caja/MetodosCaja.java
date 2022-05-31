package Caja;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MetodosCaja { // metodos del cajero

	static Connection con = null;
	static int posicion = 0;
	static String cantidadanterior = null;
	static String[] titulo = { "ARTICULOS", "CANTIDAD", "P.V.P", "TOTAL", "" };

//método para mostrar en tblCajero el articulo seleccionado
	public static void MostrarCaja(DefaultTableModel modeloCaja, JTable tblCajero, JComboBox<?> CombocodigoBarrasCaja,
			JTextField textArticuloCaja, JTextField textPVPcaja, JTextField textCantidadCaja,
			JTextField textTotalArticuloCaja, JTextField textTotalCaja, JTextField textStockTotal) {

		if (CombocodigoBarrasCaja.getSelectedItem().toString().length() != 0) {

			try {

				String cadenaCombo = CombocodigoBarrasCaja.getSelectedItem().toString().replace(" ", "");
				String sql = "SELECT Articulos, PVP, Stock FROM tblProductos WHERE Codigo = '" + cadenaCombo + "'";

				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				String dts[] = new String[5];

				int num1;
				String textarticulo, textpvp, texttotal, textstock;

				DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
				simbolo.setDecimalSeparator('.');
				DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

				while (rs.next()) {
					textArticuloCaja.setEnabled(false);
					textCantidadCaja.setEnabled(false);
					textPVPcaja.setEnabled(false);
					textTotalArticuloCaja.setEnabled(false);

					String cantidad = "1"; // calcula cantidad*precio
					textCantidadCaja.setText("1");
					PanelCajero cantidad1 = new PanelCajero();
					cantidad1.RegresaCantidad(cantidad);
					cantidad = textCantidadCaja.getText();
					num1 = Integer.parseInt(cantidad);
					String cadena1 = rs.getString(2);
					Float num2 = Float.parseFloat(cadena1);
					Float multiplica = num1 * num2;
					String cadena2 = String.valueOf(formato.format(multiplica));

					dts[0] = rs.getString(1); // muestra los datos en la tabla y en campos JTextField
					textarticulo = rs.getString(1);
					dts[1] = cantidad;

					BigDecimal bd1 = new BigDecimal(cadena1);
					bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
					Float operacion_dos_decimales = bd1.floatValue();
					String resultado1 = Float.toString(operacion_dos_decimales);
					textpvp = resultado1;					

					dts[2] = formato.format(rs.getFloat(2));
					texttotal = cadena2;
					dts[3] = cadena2;
					textstock = rs.getString(3);

					for (int i = 0; i < tblCajero.getRowCount(); i++) { // averigua si dos productos son iguales y en
																		// caso
						for (int j = 0; j < tblCajero.getRowCount(); j++) { // afirmativo solo deja uno(borrando la fila
																			// nueva)

							String valor = tblCajero.getValueAt(i, 0).toString().trim();

							if (valor.replace(" ", "").equals(dts[j].replace(" ", ""))) {
								posicion = i;
								cantidadanterior = tblCajero.getValueAt(i, 1).toString().trim();

								int fila = tblCajero.getRowCount();
								modeloCaja.removeRow(fila); // borra la fila nueva
							} else {
								break;
							}
						}
					}
					
					textArticuloCaja.setText(textarticulo); // muestra los datos en los campos JTextField
					textPVPcaja.setText(textpvp);
					textTotalArticuloCaja.setText(texttotal);
					textStockTotal.setText(textstock);
				}

				try {
					if (dts[0].toString() == null) { // comprueba si el articulo está en la base de datos

					} else {
						modeloCaja.addRow(dts);
						st.close();
					}

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, "EL articulo no está en la base de datos");
				}

			} catch (Exception ex) {
				textArticuloCaja.setText(""); // muestra los datos en los campos JTextField
				textPVPcaja.setText("");
				textCantidadCaja.setText("");
				textTotalArticuloCaja.setText("");

				String cant = JOptionPane.showInputDialog("EL articulo esta repetido, ingrese cantidad :");

				try {
					DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
					simbolo.setDecimalSeparator('.');
					DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

					int cantidaddefinitiva = 0; // pregunta la cantidad nueva a introducir
					int cantidadanteriornumerica = 0;
					int cantidadnuevanumerica = 0;
					
					if(cant != null) {
					
						if(!cant.trim().equals("")) {	
							
							cantidadanteriornumerica = Integer.parseInt(cantidadanterior);
							cantidadnuevanumerica = Integer.parseInt(cant);
							cantidaddefinitiva = cantidadanteriornumerica + cantidadnuevanumerica;

							String valor = tblCajero.getValueAt(posicion, 2).toString().trim(); // calcula el valor final del
																								// producto repetido
							Float valornumerico = Float.parseFloat(valor);
							float multiplicarepetido = valornumerico * cantidaddefinitiva;

							String operacion_dos_decimales = formato.format(multiplicarepetido);

							// System.out.println("El articulo repetido esta en la posicion " + posicion + "
							// y tiene " + cantidadanteriornumerica +" articulos. Ahora el total es
							// "+cantidaddefinitiva);

							modeloCaja.setValueAt(cantidaddefinitiva, posicion, 1); // muestra los resultados obtenidos en la
																					// tabla
							modeloCaja.setValueAt(operacion_dos_decimales, posicion, 3);
							// System.out.println("EL articulo tiene ahora "+cantidaddefinitiva+" de stock y
							// da un total de "+multiplicarepetido);
							String textcant = textCantidadCaja.getText();
							textTotalArticuloCaja.setText(textcant);
						}
					}

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, "Hay un ERROR!!!");
					e.printStackTrace();

				}
				
			}
		}

		TableColumn agregarColumnCaja; // inicia las clases para dibujar boton borrar
		agregarColumnCaja = tblCajero.getColumnModel().getColumn(4);
		agregarColumnCaja.setCellEditor(new EditorCeldasCaja(tblCajero));
		agregarColumnCaja.setCellRenderer(new RenderizadoCaja(true));

		CambioColorCaja cambiocolor = new CambioColorCaja();
		tblCajero.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gros oscuro
		tblCajero.setGridColor(Color.darkGray);

		tblCajero.getColumn(tblCajero.getModel().getColumnName(1)).setMaxWidth(400); // da tamaño a las columnas
		tblCajero.getColumn(tblCajero.getModel().getColumnName(2)).setMaxWidth(80);
		tblCajero.getColumn(tblCajero.getModel().getColumnName(3)).setMaxWidth(80);
		tblCajero.getColumn(tblCajero.getModel().getColumnName(4)).setMaxWidth(80);
		// tblCajero.getColumn( tblCajero.getModel().getColumnName(5)).setMaxWidth(20);

		tblCajero.setCellSelectionEnabled(true); // selecciona una sola casilla
		tblCajero.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
		tblCajero.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

	}

	// dispone los campos para poder aplicar el método MuestraFrutaDos
	static public void MuestraFruta(DefaultTableModel modeloCajero, JTable tblCajero,
			JComboBox<?> CombocodigoBarrasCaja, JTextField textArticuloCaja, JTextField textPVPcaja,
			JTextField textPorcentajeCaja, JTextField textTotalArticuloCaja, JTextField textTotalCaja) {

		String productofruta = CombocodigoBarrasCaja.getSelectedItem().toString();
		textPVPcaja.setText("");
		textPorcentajeCaja.setText("");
		textTotalArticuloCaja.setText("");
		textArticuloCaja.setEnabled(true);
		textPVPcaja.setEnabled(true);
		textArticuloCaja.setText(productofruta);
		textArticuloCaja.requestFocus();
	}

	// método para pasar a tblCajero la fruta
	static public void MuestraFrutaDos(DefaultTableModel modeloCajero, JTable tblCajero,
			JComboBox<?> CombocodigoBarrasCaja, JTextField textArticuloCaja, JTextField textPVPcaja,
			JTextField textPorcentajeCaja, JTextField textTotalArticuloCaja, JTextField textTotalCaja) {

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		Object[] registro = new Object[100];

		Float pvp;
		if(textPVPcaja.getText().toString().length() == 0) {
			pvp = (float) 0;
		}else {		
			pvp = Float.parseFloat(textPVPcaja.getText().toString());
		}
		String pvpdosdecimales = formato.format(pvp);

		registro[0] = textArticuloCaja.getText(); // inserta en JTable los datos de los campos
		registro[1] = "1";
		registro[2] = pvpdosdecimales;
		registro[3] = pvpdosdecimales;
		registro[4] = null;

		modeloCajero.addRow(registro);

		TableColumn agregarColumnCaja; // inicia las clases para dibujar botón borrar
		agregarColumnCaja = tblCajero.getColumnModel().getColumn(4);
		agregarColumnCaja.setCellEditor(new EditorCeldas(tblCajero));
		agregarColumnCaja.setCellRenderer(new RenderizadoCaja(true));
		CambioColorCaja cambiocolor = new CambioColorCaja();
		tblCajero.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gris oscuro
		tblCajero.setGridColor(Color.darkGray);

		CombocodigoBarrasCaja.setSelectedItem("");
		textArticuloCaja.setText(""); // borra los campos
		textPVPcaja.setText("");
		CombocodigoBarrasCaja.requestFocus();

		Float subtotal = (float) 0, total = (float) 0;
		try {
			if (tblCajero.getRowCount() > 0) {
				for (int i = 0; i < tblCajero.getRowCount(); i++) {
					subtotal = Float.parseFloat(tblCajero.getValueAt(i, 3).toString()); // La columna 3 nos da el precio
					total = total + subtotal; // suma el total de la cuenta
					formato.format(total);
				}
				textTotalCaja.setText(String.valueOf(total));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// método para poder insertar productos en la base de datos
	public static void GuardatblCajero(DefaultTableModel modeloCajero, JTable tblCajero,
			JComboBox<?> CombocodigoBarrasCaja, JComboBox<?> Combocoseleccion, JTextField textArticuloCaja,
			JTextField txtPrecio, JTextField txtPorcentaje, JTextField textTotalArticuloCaja) {

		try {
			con = Conexion.ejecutarConexion();

			String proveedor = Combocoseleccion.getSelectedItem().toString();

			String sql = "INSERT INTO tblProductos(Codigo, Articulos, " + proveedor
					+ ", Porcentaje, PVP) VALUES (?,?,?,?,?)";

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, CombocodigoBarrasCaja.getSelectedItem().toString()); // inserta los datos en la base de
																					// datos
			pst.setString(2, textArticuloCaja.getText());
			if(txtPrecio.getText().toString().length() == 0) {
				pst.setString(3, "0");
			}else {
				pst.setString(3, txtPrecio.getText());
			}
			if(txtPorcentaje.getText().toString().length() == 0) {
				pst.setString(4, "0");
			}else {
				pst.setString(4, txtPorcentaje.getText());
			}
			if(textTotalArticuloCaja.getText().toString().length() == 0) {
				pst.setString(4, "0");
			}else {
				pst.setString(5, textTotalArticuloCaja.getText());
			}

			pst.executeUpdate();
			pst.close();

			JOptionPane.showMessageDialog(null, "Se ha guardado satisfactoriamente");

			CombocodigoBarrasCaja.setSelectedItem(""); // borra todos los campos
			textArticuloCaja.setText("");
			txtPrecio.setText("");
			txtPorcentaje.setText("");
			textTotalArticuloCaja.setText("");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos");
			ex.printStackTrace();
		}
	}

	// Descuenta stock de los productos de la base de datos
	public static void DescuentaStock(JTable tblCajero) {
		try {
			con = Conexion.ejecutarConexion();

			String[] cadenaarticulo = new String[50];
			String[] cadenastock = new String[50];
			int n = 0;

			for (int i = 0; i < tblCajero.getRowCount(); i++) {
				String art = String.valueOf(tblCajero.getValueAt(i, 0));
				String stock = String.valueOf(tblCajero.getValueAt(i, 1));

				cadenaarticulo[i] = art;
				cadenastock[i] = stock;
				n = n + 1;
			}

			PreparedStatement pst;
			for (int i = 0; i < n; i++) {
				String sql = "UPDATE tblProductos SET Stock = Stock - " + cadenastock[i] + " WHERE Articulos = '"
						+ cadenaarticulo[i] + "'";
				pst = con.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
		}
	}

	// metodo para autocompletar el JComboBox del codigo de barras
	public static ArrayList<String> LlenarComboCaja() {
		con = Conexion.ejecutarConexion();
		ArrayList<String> listaCodigo = new ArrayList<String>();
		String q = "SELECT * FROM tblProductos";
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = con.createStatement();
			rs1 = st1.executeQuery(q);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error!");
		}

		try {
			while (rs1.next()) {
				listaCodigo.add(rs1.getString("Codigo"));
			}
		} catch (Exception e) {

		}

		return listaCodigo;
	}

	// metodo para autocompletar el JComboBox del codigo de barras
	public static ArrayList<String> LlenarComboCajaCategoria() {
		con = Conexion.ejecutarConexion();
		ArrayList<String> listaCodigo = new ArrayList<String>();
		String q = "SELECT * FROM tblProductos";
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = con.createStatement();
			rs1 = st1.executeQuery(q);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un ERROR!");
		}

		try {
			while (rs1.next()) {
				listaCodigo.add(rs1.getString("Categoria"));
			}
		} catch (Exception e) {

		}

		return listaCodigo;
	}

}
