package Caja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MetodosModificaTablaArticulos {

	static Connection con;

	//método que guarda los datos actualizados a la base de datos
	public void ModificaTabla(JTextField textCodigomodifica, JTextField textArticuloomodifica,
			JTextField textPVPmodifica, JTextField textPorcentajemodifica, JTextField textPrecioAyalamodifica,
			JTextField textPrecioBareamodifica, JTextField textPrecioSolaRicamodifica,
			JTextField textPrecioOtrosmodifica, JTextField textStockmodifica, JTextField textStkminmodifica,
			JComboBox<?> menuCategoria) {

		String Codigomodificamod, Articuloomodificamod, PVPmodificamod, Porcentajemodificamod, PrecioAyalamodificamod,
				PrecioBareamodificamod, PrecioSolaRicamodificamod, PrecioOtrosmodificamod, Stockmodificamod,
				Stkminmodificamod, menuCategoriamod;

		Codigomodificamod = textCodigomodifica.getText();
		Articuloomodificamod = textArticuloomodifica.getText();
		PVPmodificamod = textPVPmodifica.getText();
		Porcentajemodificamod = textPorcentajemodifica.getText();
		PrecioAyalamodificamod = textPrecioAyalamodifica.getText();
		PrecioBareamodificamod = textPrecioBareamodifica.getText();
		PrecioSolaRicamodificamod = textPrecioSolaRicamodifica.getText();
		PrecioOtrosmodificamod = textPrecioOtrosmodifica.getText();
		Stockmodificamod = textStockmodifica.getText();
		Stkminmodificamod = textStkminmodifica.getText();
		menuCategoriamod = menuCategoria.getSelectedItem().toString();

		try {
			con = Conexion.ejecutarConexion();

			// actualiza el Codigo de barras
			if (textCodigomodifica.getText().length() != 0) {
				String sql0 = "UPDATE tblProductos SET Codigo = '" + Codigomodificamod + "' WHERE Articulos = '"
						+ Articuloomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst0 = con.prepareStatement(sql0);
				pst0.executeUpdate();
				pst0.close();
				// System.out.println("UPDATE tblProductos SET Articulos = '" +
				// Codigomodificamod + "' WHERE Codigo = '"+ Articuloomodificamod + "'");
			}

			// actualiza el nombre del producto
			if (textArticuloomodifica.getText().length() != 0) {
				String sql1 = "UPDATE tblProductos SET Articulos = '" + Articuloomodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst1 = con.prepareStatement(sql1);
				pst1.executeUpdate();
				pst1.close();
				// System.out.println("UPDATE tblProductos SET Articulos = '" +
				// Articuloomodificamod + "' WHERE Codigo = '"+ Codigomodificamod + "'");
			}

			// actualiza el PVP del producto
			if (textPVPmodifica.getText().length() != 0) {
				String sql2 = "UPDATE tblProductos SET PVP = '" + PVPmodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst2 = con.prepareStatement(sql2);
				pst2.executeUpdate();
				pst2.close();
			}

			// actualiza porcentaje
			if (textPorcentajemodifica.getText().length() != 0) {
				String sql3 = "UPDATE tblProductos SET Porcentaje = '" + textPorcentajemodifica.getText()
						+ "' WHERE Codigo = '" + Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst3 = con.prepareStatement(sql3);
				pst3.executeUpdate();
				pst3.close();
				// System.out.println("UPDATE tblProductos SET Porcentaje = '" +
				// textPorcentajemodifica.getText() + "' WHERE Codigo = '"+ Codigomodificamod +
				// "'");
			}

			// actualiza precio de ayala
			if (textPrecioAyalamodifica.getText().length() != 0) {
				String sql4 = "UPDATE tblProductos SET Ayala = '" + PrecioAyalamodificamod + "' WHERE Codigo = '"
						+ textCodigomodifica.getText() + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst4 = con.prepareStatement(sql4);
				pst4.executeUpdate();
				pst4.close();
				// System.out.println("UPDATE tblProductos SET Ayala = '" +
				// PrecioAyalamodificamod + "' WHERE Codigo = '"+ textCodigomodifica.getText() +
				// "'");
			}

			// actualiza precio de barea
			if (textPrecioBareamodifica.getText().length() != 0) {
				String sql5 = "UPDATE tblProductos SET Barea = '" + PrecioBareamodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst5 = con.prepareStatement(sql5);
				pst5.executeUpdate();
				pst5.close();
				// System.out.println("UPDATE tblProductos SET Barea = '" +
				// PrecioBareamodificamod + "' WHERE Codigo = '"+ Codigomodificamod + "'");
			}

			// actualiza precio de SolaRica
			if (textPrecioSolaRicamodifica.getText().length() != 0) {
				String sql6 = "UPDATE tblProductos SET SolaRica = '" + PrecioSolaRicamodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst6 = con.prepareStatement(sql6);
				pst6.executeUpdate();
				pst6.close();
				// System.out.println("UPDATE tblProductos SET SolaRica = '" +
				// PrecioSolaRicamodificamod + "' WHERE Codigo = '"+ Codigomodificamod + "'");
			}

			// actualiza precio de Otros
			if (textPrecioOtrosmodifica.getText().length() != 0) {
				String sql7 = "UPDATE tblProductos SET Otros = '" + PrecioOtrosmodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst7 = con.prepareStatement(sql7);
				pst7.executeUpdate();
				pst7.close();
				// System.out.println("UPDATE tblProductos SET Otros = '" +
				// PrecioOtrosmodificamod + "' WHERE Codigo = '"+ Codigomodificamod + "'");
			}

			// actualiza el stock
			if (textStockmodifica.getText().length() != 0) {
				String sql8 = "UPDATE tblProductos SET Stock = '" + Stockmodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst8 = con.prepareStatement(sql8);
				pst8.executeUpdate();
				pst8.close();
				// System.out.println("UPDATE tblProductos SET Stock = '" + Stockmodificamod +
				// "' WHERE Codigo = '"+ Codigomodificamod + "'");
			} else {
				String sql8 = "UPDATE tblProductos SET Stock = null WHERE Codigo = '" + Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst8 = con.prepareStatement(sql8);
				pst8.executeUpdate();
				pst8.close();
				// System.out.println("UPDATE tblProductos SET Stock = null WHERE Codigo = '"+
				// Codigomodificamod + "'");
			}

			// actualiza el stock mínimo
			if (textStkminmodifica.getText().length() != 0) {
				String sql9 = "UPDATE tblProductos SET Stkmin = '" + Stkminmodificamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst9 = con.prepareStatement(sql9);
				pst9.executeUpdate();
				pst9.close();
				// System.out.println("UPDATE tblProductos SET Stkmin = '" + Stkminmodificamod +
				// "' WHERE Codigo = '"+ Codigomodificamod + "'");
			} else {
				String sql9 = "UPDATE tblProductos SET Stkmin = null WHERE Codigo = '" + Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst9 = con.prepareStatement(sql9);
				pst9.executeUpdate();
				pst9.close();
				// System.out.println("UPDATE tblProductos SET Stkmin = null WHERE Codigo = '"+
				// Codigomodificamod + "'");
			}

			// actualiza la categoría
			if (menuCategoria.getSelectedItem().toString().length() != 0) {
				String sql10 = "UPDATE tblProductos SET Categoria = '" + menuCategoriamod + "' WHERE Codigo = '"
						+ Codigomodificamod + "'";
				con = Conexion.ejecutarConexion();
				PreparedStatement pst10 = con.prepareStatement(sql10);
				pst10.executeUpdate();
				pst10.close();
				// System.out.println("UPDATE tblProductos SET Categoria = '" + menuCategoriamod
				// + "' WHERE Codigo = '"+ Codigomodificamod + "'");
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error al actualizar");
			ex.printStackTrace();
		}

	}

	// método para añadir a la lista de ayala el producto seleccionado
	public static void AñadirListaCompra(JTextField textArticuloomodifica, JTextField textPrecioAyalamodifica,
			JComboBox<?> menuDistribuidor, JComboBox<?> menuCategoria) {

		con = Conexion.ejecutarConexion();

		String proveselec = menuDistribuidor.getSelectedItem().toString();
		String cateselect = (String) menuCategoria.getSelectedItem();

		try {
			String sql = "INSERT INTO ListaCompra" + proveselec + "(ArticuloLista, Precio" + proveselec
					+ "Lista, CategoriaLista) VALUES (?,?,?)";
			// System.out.println("INSERT INTO ListaCompra" + proveselec + "(ArticuloLista,
			// Precio" + proveselec + "Lista, CategoriaLista) VALUES (?,?,?)");

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, textArticuloomodifica.getText());
			pst.setFloat(2, Float.parseFloat(textPrecioAyalamodifica.getText()));
			pst.setString(3, cateselect);

			pst.executeUpdate();
			pst.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un fallo, y no se han guardado los datos");
			e.printStackTrace();
		}

	}
	
	//metodo para saber si el articulo está en la pagina web y si está señala el CheckBox de actualizar el archivo CSV
	public void ActualizaArticulosWeb(JTextField textCodigomodifica, JTextField textArticuloomodifica,
		JTextField textPVPmodifica, JTextField textStockmodifica) {

		try {

			con = Conexion.ejecutarConexion();
			String sql = "SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True";
			//System.out.println("SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True");

			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				String sql11 = "UPDATE tblProductos SET ActualizaWeb = True WHERE Codigo = '" + textCodigomodifica.getText() + "'";
				//System.out.println("UPDATE tblProductos SET Booleano = True WHERE Codigo = '" + textCodigomodifica.getText() + "'");

				PreparedStatement pst11 = con.prepareStatement(sql11);
				pst11.executeUpdate();
				pst11.close();

			}
			pst.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// método que detecta si un articulo a ingresar en la lista de compra está repetido
	public static void ProductoRepetido(JTextField textArticuloomodifica, JTextField textPrecioAyalamodifica,
			JComboBox<?> menuDistribuidor, JComboBox<?> menuCategoria) {

		try {
			String menu = menuDistribuidor.getSelectedItem().toString();

			con = Conexion.ejecutarConexion();
			String sql = "SELECT ArticuloLista FROM ListaCompra" + menu + " WHERE ArticuloLista = '"
					+ textArticuloomodifica.getText() + "'";
			// System.out.println("SELECT ArticuloLista FROM ListaCompra" + menu + " WHERE
			// ArticuloLista = '" + textArticuloomodifica.getText() + "'");

			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				JOptionPane.showMessageDialog(null,
						"El artículo ya está en la lista de " + menuDistribuidor.getSelectedItem().toString());

			} else {
				AñadirListaCompra(textArticuloomodifica, textPrecioAyalamodifica, menuDistribuidor, menuCategoria);
			}
			pst.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// método que informa cuando un producto está en Articulos Web
	public static void ProductoRepetidoArticulosWeb(JTextField textCodigomodifica) {

		try {

			con = Conexion.ejecutarConexion();
			String sql = "SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True";
			//System.out.println("SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True");

			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet res = pst.executeQuery();

			if (res.next()) {
				JOptionPane.showMessageDialog(null, "El artículo ya está en Artículos Web");

			} else {
				String sql11 = "UPDATE tblProductos SET Booleano = True WHERE Codigo = '" + textCodigomodifica.getText() + "'";
				//System.out.println("UPDATE tblProductos SET Booleano = True WHERE Codigo = '" + textCodigomodifica.getText() + "'");

				PreparedStatement pst11 = con.prepareStatement(sql11);
				pst11.executeUpdate();
				pst11.close();
			}
			pst.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//método para mostrar la fecha de la última compra del producto seleccionado
	public void MostrarFechaCompra(JTextField textfechaultimacompra, JTextField textCodigomodifica) {

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT FechaActualizacion FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "'";
		//System.out.println("SELECT FechaActualizacion FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "'");
		con = Conexion.ejecutarConexion();

		try {

			String dts[] = new String[1];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				textfechaultimacompra.setText(rs.getString(1)); 				
			}
			rs.close();
			st.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
}
