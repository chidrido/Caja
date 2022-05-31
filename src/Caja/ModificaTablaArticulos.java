package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ModificaTablaArticulos extends JFrame {

	public static int Codigomodifica;

	public ModificaTablaArticulos(JTable tbl, String Codigomodifica, String Articulomodifica, String PVPmodifica,
			String Porcentajemodifica, String PrecioAyalamodifica, String PrecioBareamodifica,
			String PrecioSolaRicamodifica, String PrecioOtrosmodifica, String Stockmodifica, String Stkminmodifica,
			String Categoriamodifica) {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);
		
		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		panelModificaTablaArticulos panelmodificatablaarticulos = new panelModificaTablaArticulos(tbl, Codigomodifica,
				Articulomodifica, PVPmodifica, Porcentajemodifica, PrecioAyalamodifica, PrecioBareamodifica,
				PrecioSolaRicamodifica, PrecioOtrosmodifica, Stockmodifica, Stkminmodifica, Categoriamodifica);

		add(panelmodificatablaarticulos);
	}
}

class panelModificaTablaArticulos extends JPanel {

	static Connection con;

	JLabel J1 = new JLabel("Codigo Barras"); // etiquetas
	JLabel J2 = new JLabel("Nombre del Articulo");
	JLabel J3 = new JLabel("PVP");
	JLabel J4 = new JLabel("%");
	JLabel J5 = new JLabel("Ayala");
	JLabel J6 = new JLabel("Barea");
	JLabel J7 = new JLabel("SolaRica");
	JLabel J8 = new JLabel("Otros");
	JLabel J9 = new JLabel("Stock");
	JLabel J10 = new JLabel("Stk min");
	JLabel J11 = new JLabel("Categoría");
	JLabel J12 = new JLabel("STOCK BAJO!");
	JLabel J13 = new JLabel("Selección");
	JLabel J14 = new JLabel("Actualizar");	
	JLabel J15 = new JLabel("Exportar");	
	JLabel J16 = new JLabel("Fecha de la última compra");
	JLabel J17 = new JLabel("Se encuentra en la Web");
	JLabel J18 = new JLabel("No se encuentra en la Web");

	JComboBox<String> menuCategoria;
	JComboBox<String> menuDistribuidor;

	JButton botonmodifica = new JButton();
	JButton botonsalir = new JButton();
	JButton botonexportar = new JButton();
	JButton botonañadirlista = new JButton();
	JButton botonordenacategoria = new JButton();

	JCheckBox checkAyala;
	JCheckBox checkBarea;
	JCheckBox checkSolaRica;
	JCheckBox checkOtros;

	JTextField textCodigomodifica = new JTextField();
	JTextField textArticuloomodifica = new JTextField();
	JTextField textPVPmodifica = new JTextField();
	JTextField textPorcentajemodifica = new JTextField();
	JTextField textPrecioAyalamodifica = new JTextField();
	JTextField textPrecioBareamodifica = new JTextField();
	JTextField textPrecioSolaRicamodifica = new JTextField();
	JTextField textPrecioOtrosmodifica = new JTextField();
	JTextField textStockmodifica = new JTextField();
	JTextField textStkminmodifica = new JTextField();
	JTextField textfechaultimacompra = new JTextField();

	static String cadena;

	MetodosModificaTablaArticulos metodosmodificatablaarticulosarticulos = new MetodosModificaTablaArticulos(); // llama
																												// a
																												// clase
																												// para
																												// modificar
																												// los
																												// datos

	public panelModificaTablaArticulos(JTable tbl, String Codigomodifica, String Articulomodifica, String PVPmodifica,
			String Porcentajemodifica, String PrecioAyalamodifica, String PrecioBareamodifica,
			String PrecioSolaRicamodifica, String PrecioOtrosmodifica, String Stockmodifica, String Stkminmodifica,
			String Categoriamodifica) {

		setLayout(null);
		setBackground(Color.CYAN);

		J1.setBounds(45, 10, 200, 35); // codigo barra
		J1.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(J1);
		
		textCodigomodifica.setBounds(20, 50, 250, 50);
		textCodigomodifica.setBorder(new LineBorder(Color.BLACK));
		textCodigomodifica.setHorizontalAlignment(JTextField.LEFT);
		textCodigomodifica.setEditable(false);
		textCodigomodifica.setFont(new Font("Tahoma", 1, 30));
		add(textCodigomodifica);

		textCodigomodifica.addActionListener(new ActionListener() { // Realiza la accion que se le manda, en este caso
																	// es pulsar intro
			public void actionPerformed(ActionEvent evt) {

				textArticuloomodifica.requestFocus();
			}
		});

		J2.setBounds(570, 10, 540, 30); // declaramos articulos
		J2.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(J2);
		
		textArticuloomodifica.setBounds(280, 50, 920, 50);
		textArticuloomodifica.setBorder(new LineBorder(Color.BLACK));
		textArticuloomodifica.setFont(new Font("Tahoma", 1, 40));
		textArticuloomodifica.setHorizontalAlignment(JTextField.LEFT);
		add(textArticuloomodifica);

		textArticuloomodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textPVPmodifica.requestFocus();
			}
		});

		J3.setBounds(570, 300, 250, 50); // PVP
		J3.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J3);
		
		textPVPmodifica.setBounds(510, 340, 170, 50);
		textPVPmodifica.setBorder(new LineBorder(Color.BLACK));
		textPVPmodifica.setFont(new Font("Tahoma", 1, 40));
		textPVPmodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textPVPmodifica);

		textPVPmodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

			}
		});

		J4.setBounds(370, 305, 200, 40); // porcentaje
		J4.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J4);
		
		textPorcentajemodifica.setBounds(305, 340, 170, 50);
		textPorcentajemodifica.setBorder(new LineBorder(Color.BLACK));
		textPorcentajemodifica.setFont(new Font("Tahoma", 1, 40));
		textPorcentajemodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textPorcentajemodifica);

		textPorcentajemodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				checkAyala.setEnabled(true);
				checkBarea.setEnabled(true);

				if (checkAyala.isSelected() == true) {

					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioAyalamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);

				} else if (checkBarea.isSelected() == true) {

					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioBareamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				} else if (checkSolaRica.isSelected() == true) {
					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioSolaRicamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				} else if (checkOtros.isSelected() == true) {
					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioOtrosmodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				}
			}
		});

		checkAyala = new JCheckBox();
		checkAyala.setBorder(new LineBorder(Color.BLACK));
		checkAyala.setBounds(225, 170, 20, 20);
		add(checkAyala);

		J5.setBounds(90, 120, 200, 40); // PrecioAyala
		J5.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J5);
		
		textPrecioAyalamodifica.setBounds(25, 160, 180, 50);
		textPrecioAyalamodifica.setBorder(new LineBorder(Color.BLACK));
		textPrecioAyalamodifica.setHorizontalAlignment(JTextField.CENTER);
		textPrecioAyalamodifica.setFont(new Font("Tahoma", 1, 40));
		add(textPrecioAyalamodifica);

		textPrecioAyalamodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (checkAyala.isSelected() == true) {

					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioAyalamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				}
			}
		});

		checkBarea = new JCheckBox();
		checkBarea.setBounds(225, 260, 20, 20);
		add(checkBarea);

		J6.setBounds(90, 205, 240, 50); // PrecioBarea
		J6.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J6);
		
		textPrecioBareamodifica.setBounds(25, 250, 180, 50);
		textPrecioBareamodifica.setBorder(new LineBorder(Color.BLACK));
		textPrecioBareamodifica.setFont(new Font("Tahoma", 1, 40));
		textPrecioBareamodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textPrecioBareamodifica);

		textPrecioBareamodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (checkBarea.isSelected() == true) {
					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioBareamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				}
			}
		});

		checkSolaRica = new JCheckBox();
		checkSolaRica.setBounds(225, 350, 20, 20);
		add(checkSolaRica);

		J7.setBounds(70, 300, 240, 40); // PrecioSolaRica
		J7.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J7);
		
		textPrecioSolaRicamodifica.setBounds(25, 340, 180, 50);
		textPrecioSolaRicamodifica.setBorder(new LineBorder(Color.BLACK));
		textPrecioSolaRicamodifica.setFont(new Font("Tahoma", 1, 40));
		textPrecioSolaRicamodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textPrecioSolaRicamodifica);

		textPrecioSolaRicamodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (checkSolaRica.isSelected() == true) {

					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioSolaRicamodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				}
			}
		});

		checkOtros = new JCheckBox();
		checkOtros.setBounds(225, 440, 20, 20);
		add(checkOtros);

		J8.setBounds(80, 390, 240, 40); // PrecioOtros
		J8.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J8);
		
		textPrecioOtrosmodifica.setBounds(25, 430, 180, 50);
		textPrecioOtrosmodifica.setBorder(new LineBorder(Color.BLACK));
		textPrecioOtrosmodifica.setFont(new Font("Tahoma", 1, 40));
		textPrecioOtrosmodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textPrecioOtrosmodifica);

		textPrecioOtrosmodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (checkAyala.isSelected() == true) {

					Float operacion;
					Float extraePorcentaje = Float.parseFloat(textPorcentajemodifica.getText());
					Float extraePrecio = Float.parseFloat(textPrecioOtrosmodifica.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;

					RecortaDosDecimalesFloat(operacion);

					textPVPmodifica.setText(cadena);
				}
			}
		});

		J9.setBounds(80, 480, 160, 40); // stock
		J9.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J9);
		
		textStockmodifica.setBounds(25, 520, 180, 50);
		textStockmodifica.setBorder(new LineBorder(Color.BLACK));
		textStockmodifica.setFont(new Font("Tahoma", 1, 40));
		textStockmodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textStockmodifica);

		textStockmodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

			}
		});

		J10.setBounds(80, 570, 180, 40); // Stock mínimo
		J10.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J10);
		
		textStkminmodifica.setBounds(25, 610, 180, 50);
		textStkminmodifica.setBorder(new LineBorder(Color.BLACK));
		textStkminmodifica.setFont(new Font("Tahoma", 1, 40));
		textStkminmodifica.setHorizontalAlignment(JTextField.CENTER);
		add(textStkminmodifica);

		textStkminmodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				menuCategoria.requestFocus();
			}
		});

		J11.setBounds(320, 120, 150, 40);
		J11.setFont(new java.awt.Font(null, Font.PLAIN, 25)); // JComboBox
		add(J11); // Descripción del menu de selección
		
		menuCategoria = new JComboBox<>(); // de categoría
		menuCategoria.setBorder(new LineBorder(Color.BLACK));
		menuCategoria.setBounds(305, 160, 170, 50);
		menuCategoria.setFont(new Font("Tahoma", 1, 35));
		menuCategoria.removeAllItems();
		add(menuCategoria);
		ArrayList<String> lista = new ArrayList<String>();
		lista = MetodosArticulos.LlenarCombo();

		for (int i = 0; i < lista.size(); i++) {
			if (menuCategoria.getSelectedIndex() == 0) {
				menuCategoria.setSelectedItem("");
			}
			AutoCompleteDecorator.decorate(menuCategoria); // método para autocompletar con la columna
			menuCategoria.addItem(lista.get(i));
		}

		menuCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				botonmodifica.requestFocus();
			}
		});
		// etiqueta del menu de selección del distribuidor
		J13.setBounds(540, 120, 170, 40);
		J13.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J13);

		String opciones[] = new String[] { "Ayala", "Barea", "Solarica", "Otros" }; // Descripción del menu de selección
		menuDistribuidor = new JComboBox<String>(); // de proveedor
		menuDistribuidor.setBorder(new LineBorder(Color.BLACK));
		menuDistribuidor.setModel(new DefaultComboBoxModel<>(opciones));
		menuDistribuidor.setFont(new java.awt.Font("Dialog", 0, 40));
		menuDistribuidor.setBounds(510, 160, 170, 50); // botón exportar a web
		add(menuDistribuidor);

		botonañadirlista.setBounds(510, 245, 170, 60);
		botonañadirlista.setBorder(new LineBorder(Color.BLACK));
		botonañadirlista.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-editar-propiedad-50.png"));
		add(botonañadirlista);

		botonañadirlista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int eleccion = JOptionPane.showConfirmDialog(null,
						"Insertar en la Lista de Compra" + menuDistribuidor.getSelectedItem().toString() + "?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == eleccion) {

					// metodosmodificatablaarticulosarticulos.AñadirListaCompra(textArticuloomodifica,
					// textPrecioAyalamodifica, menuDistribuidor, menuCategoria);
					MetodosModificaTablaArticulos.ProductoRepetido(textArticuloomodifica, textPrecioAyalamodifica,
							menuDistribuidor, menuCategoria);
				}
			}
		});

		botonordenacategoria.setBounds(305, 245, 170, 60); // boton para acceder a la tabla de ordenar por categoría
		botonordenacategoria.setBorder(new LineBorder(Color.BLACK));
		botonordenacategoria.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-48.png"));
		add(botonordenacategoria);
		botonordenacategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				OrdenaCategoria ordenacategoria = new OrdenaCategoria(menuCategoria);
				ordenacategoria.setVisible(true);
			}
		});

		J12.setBounds(800, 400, 350, 50); // etiqueta de stock bajo
		J12.setFont(new java.awt.Font(null, Font.PLAIN, 50));
		J12.setForeground(Color.red);
		J12.setVisible(false);
		add(J12);
		
		J14.setBounds(440, 395, 170, 50); // etiqueta web
		J14.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J14);

		botonmodifica.setBounds(305, 440, 375, 90); // boton modificar
		botonmodifica.setBorder(new LineBorder(Color.BLACK));
		botonmodifica.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-update-60.png"));
		add(botonmodifica);

		botonmodifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				metodosmodificatablaarticulosarticulos.ModificaTabla(textCodigomodifica, textArticuloomodifica,
						textPVPmodifica, textPorcentajemodifica, textPrecioAyalamodifica, textPrecioBareamodifica,
						textPrecioSolaRicamodifica, textPrecioOtrosmodifica, textStockmodifica, textStkminmodifica,
						menuCategoria);
				
				metodosmodificatablaarticulosarticulos.ActualizaArticulosWeb(textCodigomodifica, textArticuloomodifica,
						textPVPmodifica, textStockmodifica);
			}
		});

		J15.setBounds(445, 525, 170, 50); // etiqueta web
		J15.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(J15);

		botonexportar.setBounds(305, 570, 375, 90); // botón exportar a web
		botonexportar.setBorder(new LineBorder(Color.BLACK));
		botonexportar.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-carpeta-de-internet-80.png"));
		add(botonexportar);

		botonexportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int eleccion = JOptionPane.showConfirmDialog(null, "Exportar el producto a la pagina web?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == eleccion) {
					MetodosModificaTablaArticulos.ProductoRepetidoArticulosWeb(textCodigomodifica);
				}
			}
		});
		
		botonsalir.setBounds(1035, 600, 160, 60); // crea el JBUtton "SALIR"
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-48.png"));
		add(botonsalir);

		botonsalir.addActionListener(new ActionListener() { // al salir se cierra la ventana y vuelve a
																	// articulos en la tienda
			public void actionPerformed(ActionEvent e) {

				MetodosArticulos.Mostrar(tbl);

				Window w = SwingUtilities.getWindowAncestor(panelModificaTablaArticulos.this);
				w.dispose();
			}
		});
		
		J16.setBounds(760, 150, 450, 40);
		J16.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(J16);
		
		textfechaultimacompra.setBounds(830, 200, 270, 50);
		textfechaultimacompra.setBorder(new LineBorder(Color.BLACK));
		textfechaultimacompra.setEditable(false);
		textfechaultimacompra.setHorizontalAlignment(JTextField.CENTER);
		textfechaultimacompra.setFont(new Font("Tahoma", 1, 40));
		add(textfechaultimacompra);
		
		J17.setBounds(780, 300, 450, 40);
		J17.setVisible(false);
		J17.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(J17);
		
		J18.setBounds(760, 300, 450, 40);
		J18.setVisible(false);
		J18.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(J18);

		// mostramos los datos de la tblProductos y los mandamos al método que guarda los datos en tblProductos
		textCodigomodifica.setText(Codigomodifica);
		textArticuloomodifica.setText(Articulomodifica);
		textPVPmodifica.setText(PVPmodifica);
		textPorcentajemodifica.setText(Porcentajemodifica);
		textPrecioAyalamodifica.setText(PrecioAyalamodifica);
		textPrecioBareamodifica.setText(PrecioBareamodifica);
		textPrecioSolaRicamodifica.setText(PrecioSolaRicamodifica);
		textPrecioOtrosmodifica.setText(PrecioOtrosmodifica);
		textStockmodifica.setText(Stockmodifica);
		textStkminmodifica.setText(Stkminmodifica);
		menuCategoria.setSelectedItem(Categoriamodifica);

		StockBajo(textStockmodifica, textStkminmodifica); // llama a método StockBajo
		
		metodosmodificatablaarticulosarticulos.MostrarFechaCompra(textfechaultimacompra, textCodigomodifica);
		DetectaArticulosWeb(textCodigomodifica, J17, J18);
	}

	// método para mostrar el aviso de stock bajo si el stkmin es menos o igual que stk
	public void StockBajo(JTextField textStockmodifica, JTextField textStkminmodifica) {

		if (textStockmodifica.getText().length() != 0 && textStkminmodifica.getText().length() != 0) {

			String stk = textStockmodifica.getText().toString();
			String stk_b = textStkminmodifica.getText().toString();

			int int_stk = Integer.parseInt(stk);
			int int_stk_b = Integer.parseInt(stk_b);

			if (int_stk <= int_stk_b) {
				J12.setVisible(true);
			} else {
				J12.setVisible(false);
			}
		}
	}
	
	//método que detecta y muestra en pantalla si el artículo está en la Web
		public static void DetectaArticulosWeb(JTextField textCodigomodifica, JLabel J17, JLabel J18) {

			try {

				con = Conexion.ejecutarConexion();
				String sql = "SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True";
				//System.out.println("SELECT Articulos FROM tblProductos WHERE Codigo = '" + textCodigomodifica.getText() + "' AND Booleano = True");

				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet res = pst.executeQuery();

				if (res.next()) {
					J17.setVisible(true);
				}else {
					J18.setVisible(true);
				}
				pst.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public String RecortaDosDecimalesFloat(Float num) {

		Float operacion_dos_decimales;

		BigDecimal bd = new BigDecimal(num);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		operacion_dos_decimales = bd.floatValue();
		cadena = Float.toString(operacion_dos_decimales);

		return cadena;
	}

	// dibuja rectangulos
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(10, 10, 1200, 100);
		g.drawRect(740, 140, 450, 125);
		g.drawRect(740, 285, 450, 80);
		g.drawRect(740, 385, 450, 80);
		g.drawRect(10, 120, 250, 560);
		g.drawRect(270, 120, 440, 560);
		g.drawRect(720, 120, 490, 560);
	}

}