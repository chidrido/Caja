package Caja;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MetodosAutomatizaFacturas {
	
	static Connection con = null;
	
	public void MostrarBaseDeDatos(DefaultTableModel modeloAutomatiza, JTable tblAutomatiza, JComboBox<?> combodistribuidor) {

		try {
			String[] titulos = { "Cod.Barras", "Articulo", "Unid.", "Precio", "" };
			con = Conexion.ejecutarConexion();
			
			int contador = tblAutomatiza.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloAutomatiza.removeRow(i);
			}

			Object dts[] = new Object[5];
			String sql = "SELECT * FROM tblFacturas" + combodistribuidor.getSelectedItem().toString();
			//System.out.println("SELECT * FROM tblFacturas" + combodistribuidor.getSelectedItem().toString());
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				dts[2] = rs.getInt(3);
				dts[3] = formato.format(rs.getFloat(4));
				dts[4] = rs.getObject(5, Boolean.class);

				modeloAutomatiza.addRow(dts);
			}
			
			tblAutomatiza.setModel(modeloAutomatiza);
			
			TableColumn agregarColumna1;
			agregarColumna1 = tblAutomatiza.getColumnModel().getColumn(4); // llama a los metodos para mostrar el
																				// chekbox
			agregarColumna1.setCellEditor(tblAutomatiza.getDefaultEditor(Boolean.class));
			agregarColumna1.setCellRenderer(tblAutomatiza.getDefaultRenderer(Boolean.class));
			
			CambioColor cambiocolor = new CambioColor();
			tblAutomatiza.setDefaultRenderer(Object.class, cambiocolor); // muestra color en la celda señalada

			tblAutomatiza.getColumn(tblAutomatiza.getModel().getColumnName(0)).setMaxWidth(160);
			tblAutomatiza.getColumn(tblAutomatiza.getModel().getColumnName(1)).setMaxWidth(450);
			tblAutomatiza.getColumn(tblAutomatiza.getModel().getColumnName(2)).setMaxWidth(70);
			tblAutomatiza.getColumn(tblAutomatiza.getModel().getColumnName(3)).setMaxWidth(70);
			tblAutomatiza.getColumn(tblAutomatiza.getModel().getColumnName(4)).setMaxWidth(40);
			tblAutomatiza.setRowHeight(30);
			tblAutomatiza.setFont(new java.awt.Font("Tahoma", 0, 18));

			tblAutomatiza.setCellSelectionEnabled(true); // selecciona una sola casilla
			tblAutomatiza.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
			tblAutomatiza.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell"); // pinta la celda señalada de color gris
																			// oscuro
			tblAutomatiza.setGridColor(Color.darkGray);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//método para mostrar en tblAutomatiza y en los JTextFiels el articulo seleccionado
	public static void MostrarProductoSeleccionado(DefaultTableModel modeloAutomatiza, JTable tblAutomatiza, String Codigomodifica,
			JTextField textocodigo, JTextField textonombreproducto, JTextField textoprecio, JTextField textostock,
			JTextField textoporcentaje, JTextField textopreciototal, JComboBox<?> combocategoria, JComboBox<?> combodistribuidor, 
			JButton botonactualizardatos, JButton botonguardardatos) {

		con = Conexion.ejecutarConexion();
		
		try {
			String cadenaCombo = combodistribuidor.getSelectedItem().toString();
			String sql = "SELECT Codigo, Articulos, " + cadenaCombo + ", Stock, Porcentaje, PVP, Categoria FROM tblProductos WHERE Codigo = '" + Codigomodifica + "'";
				
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			String codigo, articulo, precio, stock, porcentaje, preciototal, categoria;

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			if (rs.next()) {
				
				do {	
					codigo = rs.getString(1);
					textocodigo.setText(codigo);
				
					articulo = rs.getString(2);
					textonombreproducto.setText(articulo);
					
					if(cadenaCombo == "Ayala") {
						precio = formato.format(rs.getFloat(3));
						if(precio != null){
							textoprecio.setText(precio);
						}else {
							textoprecio.setText("");
						}
					}else if(cadenaCombo == "Barea"){
						precio = formato.format(rs.getFloat(3));
						if(precio != null){
							textoprecio.setText(precio);	
						}else {
							textoprecio.setText("");
						}
					}else if(cadenaCombo == "SolaRica"){
						precio = formato.format(rs.getFloat(3));
						if(precio != null){
							textoprecio.setText(precio);
						}else {
							textoprecio.setText("");
						}
					}else if(cadenaCombo == "Otros"){
						precio = formato.format(rs.getFloat(3));
						if(precio != null){
							textoprecio.setText(precio);
						}else {
							textoprecio.setText("");
						}	
					}
				
					stock = rs.getString(4);
					textostock.setText(stock);
				
					porcentaje = rs.getString(5);
					textoporcentaje.setText(porcentaje);
				
					preciototal = formato.format(rs.getFloat(6));				
					textopreciototal.setText(preciototal);
				
					categoria = rs.getString(7);
					combocategoria.setSelectedItem(categoria);	
				} while(rs.next());
				rs.close();
				st.close();
				
			} else {
				
				botonguardaenable(botonactualizardatos, botonguardardatos);
				JOptionPane.showMessageDialog(null, "¡No se encuentra en la base de datos!");			      
			}
			rs.close();
			st.close();
		} catch (Exception ex) {				
			ex.printStackTrace();
		}
	}
	
	//método que transfiere los campos precio y unidades de la factura hacia los campos precio y stock de la BD.
	public void ActualizaDatos(JComboBox<?> combodistribuidor, JTextField textopreciofactura, JTextField textoprecio, JTextField textounidadesfactura,
			JTextField textostock, JTextField textoporcentaje, JTextField textopreciototal) {
		
		String preciofactura, unidadesfactura, stock, suma;
		int intunidadeas, intstock, intsumastock;
		
		try {		
			preciofactura = textopreciofactura.getText();
			unidadesfactura = textounidadesfactura.getText();
			stock = textostock.getText();
			
			textoprecio.setText(preciofactura);
		
			intunidadeas = Integer.parseInt(unidadesfactura);		//suma unidades y stock
		
			if(textostock.getText().length() != 0) {
				intstock = Integer.parseInt(stock);
				intsumastock = intunidadeas + intstock;
				suma = String.valueOf(intsumastock);	
				textostock.setText(suma);
			}else {			
				intstock = 0;
				intsumastock = intunidadeas + intstock;
				suma = String.valueOf(intsumastock);	
				textostock.setText(suma);
			}
			
			CalculaTotal(textoprecio, textoporcentaje, textopreciototal);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "¡Seleccione un producto!");
		}
	}
	
	//método que calcula el total multiplicando el precio por el porcentaje 
	public void CalculaTotal(JTextField textoprecio, JTextField textoporcentaje, JTextField textopreciototal) {
		
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);
		
		float precio, cadenaporcentaje, multiplica;
		
		try {
			precio = Float.parseFloat(textoprecio.getText());
			cadenaporcentaje = Float.parseFloat(textoporcentaje.getText());
			multiplica = precio + (precio*cadenaporcentaje)/100;
		
			String result = formato.format(multiplica);
		
			textopreciototal.setText(result);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "¡El artículo no está en la base de datos!");
		}
	}
	
	//método que guarda los datos actualizados
	public void GuardaDatosActualizados(JTextField textocodigo, JTextField textonombreproducto, JTextField textoprecio, JTextField textostock, 
		JTextField textoporcentaje, JTextField textopreciototal, JComboBox<?> combocategoria, 
		JComboBox<?> combodistribuidor) {

		String textocodigomod, textonombreproductomod, textopreciomod, textstockmod, textoporcentajemod, textopreciototalmod,
		combocategoriamod, combodistribuidormod;
			
			
		try {
			con = Conexion.ejecutarConexion();
				
			textocodigomod = textocodigo.getText();
			textonombreproductomod = textonombreproducto.getText();
			textopreciomod = textoprecio.getText();
			textstockmod = textostock.getText();
			textoporcentajemod = textoporcentaje.getText();
			textopreciototalmod = textopreciototal.getText();
			combocategoriamod = combocategoria.getSelectedItem().toString();
			combodistribuidormod = combodistribuidor.getSelectedItem().toString();
				 
				
			//actualiza el nombre del producto				
			String sql1 = "UPDATE tblProductos SET Articulos  = '" + textonombreproductomod + "' WHERE Codigo = '" + textocodigomod + "'";
			//System.out.println("UPDATE tblProductos SET Articulos  = '" + textonombreproductomod + "' WHERE Codigo = '" + textocodigomod + "'");
			con = Conexion.ejecutarConexion();
			PreparedStatement pst1 = con.prepareStatement(sql1);
			pst1.executeUpdate();
			pst1.close();					
				
			//actualiza precio
			if (textoprecio.getText().length() != 0) {
				String sql2 = "UPDATE tblProductos SET " + combodistribuidormod + " = '" + textopreciomod + "' WHERE Codigo = '" + textocodigomod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst2 = con.prepareStatement(sql2);
				pst2.executeUpdate();
				pst2.close();
				//System.out.println("UPDATE tblProductos SET " + combodistribuidormod + " = '" + textopreciomod + "' WHERE Codigo = '" + textocodigomod + "'");
			}
					
			// actualiza Stock
			if (textostock.getText().length() != 0) {
				String sql3 = "UPDATE tblProductos SET Stock = '" + textstockmod + "' WHERE Codigo = '" + textocodigomod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst3 = con.prepareStatement(sql3);
				pst3.executeUpdate();
				pst3.close();
				//System.out.println("UPDATE tblProductos SET Stock = '" + textstockmod + "' WHERE Codigo = '" + textocodigomod + "'");
			}
				
			// actualiza porcentaje del producto
			if (textoporcentaje.getText().length() != 0) {
				String sql4 = "UPDATE tblProductos SET Porcentaje = '" + textoporcentajemod + "' WHERE Codigo = '" + textocodigomod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst4 = con.prepareStatement(sql4);
				pst4.executeUpdate();
				pst4.close();
				//System.out.println("UPDATE tblProductos SET Categoria = '" + textoporcentajemod + "' WHERE Codigo = '" + textocodigomod + "'");
			}else {
				textoporcentajemod = "";
				String sql4 = "UPDATE tblProductos SET Porcentaje = '" + textoporcentajemod + "' WHERE Codigo = '" + textocodigomod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst4 = con.prepareStatement(sql4);
				pst4.executeUpdate();
				pst4.close();
			}
				
			// actualiza precioTotal del producto
			String sql5 = "UPDATE tblProductos SET PVP = '" + textopreciototalmod + "' WHERE Codigo = '" + textocodigomod + "'";
			con = Conexion.ejecutarConexion();
			PreparedStatement pst5 = con.prepareStatement(sql5);
			pst5.executeUpdate();
			pst5.close();
			//System.out.println("UPDATE tblProductos SET PVP = '" + textopreciototalmod + "' WHERE Codigo = '" + textocodigomod + "'");								
				
			// actualiza categoria del producto
			String sql6 = "UPDATE tblProductos SET Categoria = '" + combocategoriamod + "' WHERE Codigo = '" + textocodigomod + "'";
			con = Conexion.ejecutarConexion();
			PreparedStatement pst6 = con.prepareStatement(sql6);
			pst6.executeUpdate();
			pst6.close();
			//System.out.println("UPDATE tblProductos SET SolaRica = '" + combocategoriamod + "' WHERE Codigo = '" + textocodigomod + "'");	
				
			//actualiza fecha
			Calendar calendario = Calendar.getInstance();

			String dia = Integer.toString(calendario.get(Calendar.DATE));
			String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
			String annio = Integer.toString(calendario.get(Calendar.YEAR));

			String fechacompleta = dia + "/" + mes + "/" + annio;
			
			String sql7 = "UPDATE tblProductos SET FechaActualizacion = '" + fechacompleta + "' WHERE Codigo = '" + textocodigomod + "'";
			con = Conexion.ejecutarConexion();
			PreparedStatement pst7 = con.prepareStatement(sql7);
			pst7.executeUpdate();
			pst7.close();
			//System.out.println("UPDATE tblProductos SET FechaActualizacion = '" + fechacompleta + "' WHERE Codigo = '" + textocodigomod + "'");	
			
			JOptionPane.showMessageDialog(null, "Los datos han sido guardados correctamente");
				
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar, debe rellenar los campos: Codigo, Nombre y PrecioFinal");
			textocodigo.setText("");
			textonombreproducto.setText("");
			textoprecio.setText("");
			textostock.setText("");
			textoporcentaje.setText("");
			textopreciototal.setText("");
			combocategoria.setSelectedItem("");
			combodistribuidor.setSelectedItem("");
		}			
	}
	
	//método para buscar en la BD si el producto está agregado a ArticulosWeb 
	public void BuscaDatosActualizadosWeb(JTextField textocodigo) {
		
		try {

			con = Conexion.ejecutarConexion();
			String sql = "SELECT Articulos FROM tblProductos WHERE Codigo = '" + textocodigo.getText() + "' AND Booleano = True";
			//System.out.println("SELECT Articulos FROM tblProductos WHERE Codigo = '" + textocodigo.getText() + "' AND Booleano = True");

			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				
				GuardaDatosActualizadosWeb(textocodigo);
				
			}
			pst.close();
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//método para marcar en el CheckBox de ActualizaWeb de la BD los datos actualizados de la factura
	public void GuardaDatosActualizadosWeb(JTextField textocodigo) {
		
		try {
			con = Conexion.ejecutarConexion();
			
			String sqlWeb = "UPDATE tblProductos SET ActualizaWeb = True WHERE Codigo = '" + textocodigo.getText() + "' AND Booleano = True";
			//System.out.println("UPDATE tblProductos SET Booleano = True WHERE Codigo = '" + textocodigo.getText() + "' AND Booleano = True");

			PreparedStatement pstWeb = con.prepareStatement(sqlWeb);
			pstWeb.executeUpdate();
			pstWeb.close();
			
		}catch(Exception e) {
			e.printStackTrace();			
		}		
	}
		
		//método para guardar los datos comprados en TablaFacturas (tabla para imprimir)
	public void GuardarTablaFacturas(JTextField textonombreproducto, JTextField textopreciototal) {
			
		String nombre = textonombreproducto.getText();
		String PVP = textopreciototal.getText();
			
		try {
				
			String sql = "INSERT INTO TablaFacturas(Articulos, PVP) VALUES (?,?)";
			//System.out.println("INSERT INTO TablaFacturas(Articulos, PVP) VALUES (?,?)");
			
			PreparedStatement pst = con.prepareStatement(sql);				
			pst.setString(1, nombre);
			pst.setString(2, PVP);
									
			pst.executeUpdate();
			pst.close();
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	//métodos para habilitar y deshabilitar los botones
	public static void botonguardaenable(JButton botonactualizardatos, JButton botonguardardatos) {
			
		botonactualizardatos.setEnabled(false);
		botonguardardatos.setEnabled(true);
	}
		
	public static void botonactualizaenable(JButton botonactualizardatos, JButton botonguardardatos) {
			
		botonactualizardatos.setEnabled(true);
		botonguardardatos.setEnabled(false);
	}		
		
	public void ReiniciaConBotonMostrar(JTextField textobuscacodigo, JTextField textobuscanombre, JTextField textocodigo,
		JTextField textonombreproducto, JTextField textoprecio, JTextField textostock, JTextField textoporcentaje, 
		JTextField textopreciototal, JComboBox<?> combocategoria,JButton botonactualizardatos, JButton botonguardardatos) {
			
		try {
			textobuscacodigo.setText("");
			textobuscanombre.setText("");
			textocodigo.setText("");
			textonombreproducto.setText("");
			textoprecio.setText("");
			textostock.setText("");
			textoporcentaje.setText("");
			textopreciototal.setText("");
			combocategoria.setSelectedIndex(-1);
			
			textobuscacodigo.requestFocus();
				
			botonactualizaenable(botonactualizardatos, botonguardardatos);	
	
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER); // Simula presionar la tecla
			robot.keyRelease(KeyEvent.VK_ENTER); // Simula soltar la tecla
				
		} catch (AWTException e1) {
			throw new RuntimeException("Error");
		}			
	}
		
	//método que introduce en tblAutomatiza la factura en formato txt o pdf
	public void ImportaFactura(DefaultTableModel modeloAutomatiza, JTable tblAutomatiza, JButton botonimportar, JLabel etiquetadireccion,
		JComboBox<?> combodistribuidor) {
			
		JFileChooser fc=new JFileChooser();
		int seleccion=fc.showOpenDialog(botonimportar);
			
		if(seleccion==JFileChooser.APPROVE_OPTION){
			File fichero=fc.getSelectedFile();
			etiquetadireccion.setText(fichero.getAbsolutePath());
				
				
			InsertaDatosTabla(fichero, modeloAutomatiza);
				
			try {
				con = Conexion.ejecutarConexion();
				String distribuidor = combodistribuidor.getSelectedItem().toString();
				
				for(int i = 0; i < tblAutomatiza.getRowCount(); i++) {
					
					String sql = "INSERT INTO tblFacturas" + distribuidor + "(Codigo, Nombre, Unidades, PrecioCIVA) VALUES (?,?,?,?)";
					//System.out.println("INSERT INTO tblFacturas" + distribuidor + "(Codigo, Nombre, Unidades, PrecioCIVA) VALUES (?,?,?,?)");
						
					PreparedStatement pst = con.prepareStatement(sql);				
					pst.setString(1, modeloAutomatiza.getValueAt(i, 0).toString());
					pst.setString(2, modeloAutomatiza.getValueAt(i, 1).toString());
					pst.setFloat(3, Float.valueOf(modeloAutomatiza.getValueAt(i, 2).toString()));
					pst.setFloat(4, Float.valueOf(modeloAutomatiza.getValueAt(i, 3).toString()));
												
					pst.executeUpdate();
					pst.close();					
				}
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Hay un error en : " + e.getMessage());
				e.printStackTrace();
			}			
		}
	}
	
		
	//pasa el archivo en formato txt u otro a una JTable
	public void InsertaDatosTabla(File fichero, DefaultTableModel modeloAutomatiza) {
		BufferedReader br = null;
	    try{
	    	br = new BufferedReader(new FileReader(fichero));
	        String line = br.readLine();
	            
	        for(int row = 0; row < 10 ; row++){
	        	for(int column = 0; column <= 5 ;column++){

	        		while (line != null ){
	                	String [] rowfields = line.split("\\t+|\\n");
	                    modeloAutomatiza.addRow(rowfields);
	                    line = br.readLine();
	                }			                           
	            }
	        }
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    }
	}
		
	//método para borrar la base de datos Factura(distribuidor)
	public void BorrarBaseDeDatos(DefaultTableModel modeloAutomatiza, JTable tblAutomatiza, JComboBox<?> combodistribuidor) {
			
		String select = combodistribuidor.getSelectedItem().toString();
			
		try {
				
			con = Conexion.ejecutarConexion();
				
			int eleccion = JOptionPane.showConfirmDialog(null, "Eliminar Factura de " + select + "?", "Confirmar",
				JOptionPane.YES_NO_OPTION);
			
			if (JOptionPane.OK_OPTION == eleccion) {
					
				String sql = "DELETE FROM tblFacturas" + select;
				PreparedStatement pst = con.prepareStatement(sql);
					
				pst.execute();
				pst.close();
					
				int contador = tblAutomatiza.getRowCount() - 1;
				for (int i = contador; i >= 0; i--) {
					modeloAutomatiza.removeRow(i);
				}		
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
