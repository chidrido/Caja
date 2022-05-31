package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Cajero extends JFrame {

	public Cajero() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);

		setVisible(true);

		setResizable(false);
		
		dispose();
		
		setUndecorated(true);		

		PanelCajero panelcaja = new PanelCajero();

		add(panelcaja);

	}
}

class PanelCajero extends JPanel {

	String[] tituloscajero = { "ARTICULOS", "CANTIDAD", "PVP", "TOTAL", "" };
	String[] tituloslista = { "PRODUCTOS", "P", "C" };
	DefaultTableModel modeloCajero;
	DefaultTableModel modeloLista;
	JTable tblCajero;
	JTable tblLista;
	JScrollPane scrollCajero;
	JScrollPane scrollLista;

	JButton botonTerminar;
	JButton botonBasededatos;
	JButton botonCantidad;
	JButton botonarticulomanual;
	JButton botonnuevoticket;
	JButton botonAutomatiza;
	JButton botongastos;
	JButton botonagenda;
	JButton botonagregarcaja;
	JButton botonguardarlista;
	JButton botonfruta;
	JButton botonsalir;
	JButton botonborrafila;
	JButton botonborralista;
	JButton botonimprimirlista;
	JButton botonArticulosWeb;
	JButton botoncategoria;
	JButton botonactivaordenalista;

	JCheckBox checkbox;

	JComboBox<String> CombocodigoBarrasCaja;
	JComboBox<String> Combocoseleccion;
	JComboBox<String> Combocategoria;

	JTextField textTotalCaja;
	JTextField textArticuloCaja;
	JTextField textPVPcaja;
	JTextField textCantidadCaja;
	JTextField textPorcentajeCaja;
	JTextField textTotalArticuloCaja;
	JTextField textStockTotal;
	JTextField txtnuevoarticulolista;

	JLabel TotalCaja;
	JLabel CodigoCaja;
	JLabel ArticuloCaja;
	JLabel PVPcaja;
	JLabel CantidadCaja;
	JLabel TotalArticuloCaja;
	JLabel StockTotal;
	JLabel PorcentajeCaja;
	JLabel aviso;
	JLabel ayala;
	JLabel barea;
	JLabel solarica;
	JLabel otros;
	JLabel nuevoarticulolista;
	JLabel nuevopreciolista;
	JLabel nuevacategorialista;
	JLabel buscador;

	static int cantidadarticulostotal;
	static String cuentatotal;
	static int cantidadcuenta;

	static Connection con = null;

	public PanelCajero() {

		modeloCajero = new DefaultTableModel(null, tituloscajero);
		tblCajero = new JTable(modeloCajero);
		scrollCajero = new JScrollPane(tblCajero);
		scrollCajero.setBounds(440, 20, 760, 435);
		scrollCajero.setBorder(new LineBorder(Color.BLACK));
		add(scrollCajero);

		tblCajero.getColumn(tblCajero.getModel().getColumnName(1)).setMaxWidth(400); // damos tamaño a las filas de la
																						// JTable
		tblCajero.getColumn(tblCajero.getModel().getColumnName(2)).setMaxWidth(80);
		tblCajero.getColumn(tblCajero.getModel().getColumnName(3)).setMaxWidth(80);
		tblCajero.getColumn(tblCajero.getModel().getColumnName(4)).setMaxWidth(80);
		tblCajero.setFont(new java.awt.Font("Tahoma", 0, 15));
		tblCajero.setRowHeight(25);

		tblCajero.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblCajero.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 15);
		th.setFont(fuente);

		modeloLista = new DefaultTableModel(null, tituloslista);
		tblLista = new JTable(modeloLista);
		scrollLista = new JScrollPane(tblLista);
		scrollLista.setBounds(31, 125, 378, 415);
		scrollLista.setBorder(new LineBorder(Color.BLACK));
		add(scrollLista);

		tblLista.getColumn(tblLista.getModel().getColumnName(0)).setMaxWidth(295); // damos tamaño a las filas de laJTable
		tblLista.getColumn(tblLista.getModel().getColumnName(1)).setMaxWidth(40);
		tblLista.getColumn(tblLista.getModel().getColumnName(2)).setMaxWidth(40);
		tblLista.setFont(new java.awt.Font("Tahoma", 0, 14));
		tblLista.setRowHeight(22);

		tblLista.setCellSelectionEnabled(true); // no permite seleccionar casillas, solo items como botones y demás

		tblLista.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		setLayout(null);
		setBackground(Color.CYAN);

		CodigoCaja = new JLabel("Codigo de Barras:"); // campo para codigo de barras
		CodigoCaja.setFont(new java.awt.Font(null, Font.PLAIN, 15));
		CodigoCaja.setBounds(450, 535, 120, 20);
		add(CodigoCaja);

		CombocodigoBarrasCaja = new JComboBox<>();
		CombocodigoBarrasCaja.setBorder(new LineBorder(Color.BLACK));
		CombocodigoBarrasCaja.setFont(new java.awt.Font("Dialog", 0, 18));
		CombocodigoBarrasCaja.setBounds(570, 535, 160, 25);
		add(CombocodigoBarrasCaja);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CombocodigoBarrasCaja.requestFocus();
			}
		}); // inicia foco

		CombocodigoBarrasCaja.removeAllItems();
		ArrayList<String> listaCodigo = new ArrayList<String>(); // autocompletar en campo codigo de barras
		listaCodigo = MetodosCaja.LlenarComboCaja();

		for (int i = 0; i < listaCodigo.size(); i++) {

			if (CombocodigoBarrasCaja.getSelectedIndex() == 0) {
				CombocodigoBarrasCaja.setSelectedItem("");
			}

			AutoCompleteDecorator.decorate(CombocodigoBarrasCaja); // metodo para autocompletar con la columna
			CombocodigoBarrasCaja.addItem(listaCodigo.get(i));
		}

		CombocodigoBarrasCaja.getEditor().addActionListener(new ActionListener() { // llama a metodos para hacer las
																					// operaciones en cajero
			public void actionPerformed(ActionEvent e) {

				String comprueba = CombocodigoBarrasCaja.getSelectedItem().toString().replace(" ", "");
				try {
					if (botonagregarcaja.isEnabled()) {
						textArticuloCaja.requestFocus();
					} else if (comprueba.matches("[0-9]*")) {
						MetodosCaja.MostrarCaja(modeloCajero, tblCajero, CombocodigoBarrasCaja, textArticuloCaja,
								textPVPcaja, textCantidadCaja, textTotalArticuloCaja, textTotalCaja, textStockTotal);
						CombocodigoBarrasCaja.setSelectedItem("");
						CombocodigoBarrasCaja.requestFocus();
						CuentaTotal();
					} else {
						MetodosCaja.MuestraFruta(modeloCajero, tblCajero, CombocodigoBarrasCaja, textArticuloCaja,
								textPVPcaja, textPorcentajeCaja, textTotalArticuloCaja, textTotalCaja);
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "El campo esta vacio");
					ex.printStackTrace();
				}
			}
		});

		Combocoseleccion = new JComboBox<>(); // crea el combo de seleccion de proveedores
		Combocoseleccion.setBorder(new LineBorder(Color.BLACK));
		Combocoseleccion.setFont(new java.awt.Font(null, Font.PLAIN, 25)); // JComboBox
		Combocoseleccion.setBounds(30, 35, 110, 30);
		add(Combocoseleccion);
		Combocoseleccion.addItem("Ayala");
		Combocoseleccion.addItem("Barea");
		Combocoseleccion.addItem("SolaRica");
		Combocoseleccion.addItem("Otros");

		Combocoseleccion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				MetodosLista.MostrarLista(modeloLista, tblLista, Combocoseleccion, textArticuloCaja, ayala, barea,
						solarica, otros);
			}
		});

		Combocoseleccion.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {

				try {
					MetodosLista.MostrarLista(modeloLista, tblLista, Combocoseleccion, textArticuloCaja, ayala, barea,
							solarica, otros);

				} catch (Exception ex) {

					ex.printStackTrace();
				}
				CombocodigoBarrasCaja.requestFocus();
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		ArticuloCaja = new JLabel("Articulo"); // campo nombre del articulo
		ArticuloCaja.setBounds(650, 570, 100, 20);
		ArticuloCaja.setFont(new Font(null, Font.PLAIN, 15));
		add(ArticuloCaja);
		
		textArticuloCaja = new JTextField();
		textArticuloCaja.setBorder(new LineBorder(Color.BLACK));
		textArticuloCaja.setBounds(460, 590, 405, 25);
		textArticuloCaja.setHorizontalAlignment(JTextField.LEFT);
		textArticuloCaja.setFont(new Font("Tahoma", 1, 20));
		add(textArticuloCaja);

		textArticuloCaja.addKeyListener(new KeyListener() { // llama a metodos para hacer las operaciones en cajero
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					textPVPcaja.requestFocus();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

		});
		CuentaTotal();

		PVPcaja = new JLabel("P.V.P/precio"); // campo PVP de articulo
		PVPcaja.setBounds(878, 570, 150, 20);
		PVPcaja.setFont(new Font(null, Font.PLAIN, 15));
		add(PVPcaja);
		
		textPVPcaja = new JTextField();
		textPVPcaja.setBorder(new LineBorder(Color.BLACK));
		textPVPcaja.setBounds(880, 590, 80, 25);
		textPVPcaja.setFont(new Font("Tahoma", 1, 20));
		textPVPcaja.setHorizontalAlignment(JTextField.CENTER);
		add(textPVPcaja);

		textPVPcaja.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (botonagregarcaja.isEnabled()) {
						textPorcentajeCaja.requestFocus();
					} else {
						MetodosCaja.MuestraFrutaDos(modeloCajero, tblCajero, CombocodigoBarrasCaja, textArticuloCaja,
								textPVPcaja, textPorcentajeCaja, textTotalArticuloCaja, textTotalCaja);
					}
					CuentaTotal();
				}
			}
			public void keyReleased(KeyEvent e) {
			}
			public void keyTyped(KeyEvent e) {
			}
		});

		CantidadCaja = new JLabel("Unid."); // Campo cantidad de articulos que por defecto es 1
		CantidadCaja.setBounds(985, 570, 80, 20);
		CantidadCaja.setFont(new Font(null, Font.PLAIN, 15));
		add(CantidadCaja);
		
		textCantidadCaja = new JTextField();
		textCantidadCaja.setBorder(new LineBorder(Color.BLACK));
		textCantidadCaja.setHorizontalAlignment(JTextField.CENTER);
		textCantidadCaja.setBounds(975, 590, 50, 25);
		textCantidadCaja.setFont(new Font("Tahoma", 1, 20));
		add(textCantidadCaja);

		textCantidadCaja.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textPorcentajeCaja.requestFocus();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		PorcentajeCaja = new JLabel("%"); // campo y etiqueta de porcentaje
		PorcentajeCaja.setBounds(1055, 570, 50, 20);
		PorcentajeCaja.setFont(new Font(null, Font.PLAIN, 15));
		add(PorcentajeCaja);
		
		textPorcentajeCaja = new JTextField();
		textPorcentajeCaja.setBorder(new LineBorder(Color.BLACK));
		textPorcentajeCaja.setHorizontalAlignment(JTextField.CENTER);
		textPorcentajeCaja.setBounds(1040, 590, 50, 25);
		textPorcentajeCaja.setFont(new Font("Tahoma", 1, 20));
		add(textPorcentajeCaja);

		textPorcentajeCaja.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Float prec;
					if(textPVPcaja.getText().length() == 0) {
						prec = (float)0;
					}else {
						prec = Float.parseFloat(textPVPcaja.getText());
					}
					
					int porc;
					if(textPVPcaja.getText().length() == 0) {
						porc = 0;
					}else {
						porc = Integer.parseInt(textPorcentajeCaja.getText());
					}
					
					Float cuantafinal = prec + (prec * porc) / 100;
					String cadenacuenfinal = String.valueOf(cuantafinal);
					BigDecimal bd = new BigDecimal(cadenacuenfinal);
					bd = bd.setScale(2, RoundingMode.HALF_UP);
					Float tot = bd.floatValue();
					String result = Float.toString(tot);
					botonagregarcaja.requestFocus();

					textTotalArticuloCaja.setText(result);
					
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		TotalArticuloCaja = new JLabel("Total"); // campo precio total del articulo
		TotalArticuloCaja.setBounds(1125, 570, 60, 20);
		TotalArticuloCaja.setFont(new Font(null, Font.PLAIN, 15));
		add(TotalArticuloCaja);
		
		textTotalArticuloCaja = new JTextField();
		textTotalArticuloCaja.setBorder(new LineBorder(Color.BLACK));
		textTotalArticuloCaja.setBounds(1105, 590, 80, 25);
		textTotalArticuloCaja.setHorizontalAlignment(JTextField.CENTER);
		textTotalArticuloCaja.setFont(new Font("Tahoma", 1, 20));
		add(textTotalArticuloCaja);

		TotalCaja = new JLabel("TOTAL"); // campo de precio total de la compra
		TotalCaja.setBounds(1000, 470, 80, 30);
		TotalCaja.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(TotalCaja);
		
		textTotalCaja = new JTextField();
		textTotalCaja.setBorder(new LineBorder(Color.BLACK));
		textTotalCaja.setEditable(false);
		textTotalCaja.setHorizontalAlignment(JTextField.CENTER);
		textTotalCaja.setBounds(1090, 470, 95, 30);
		textTotalCaja.setFont(new Font("Tahoma", 1, 25));
		add(textTotalCaja);

		botonagregarcaja = new JButton("Guardar"); // boton para insertar en base de datos productos nuevos
		botonagregarcaja.setBorder(new LineBorder(Color.BLACK));
		botonagregarcaja.setBounds(1070, 535, 100, 25);
		add(botonagregarcaja);

		botonagregarcaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				MetodosCaja.GuardatblCajero(modeloCajero, tblCajero, CombocodigoBarrasCaja, Combocoseleccion,
						textArticuloCaja, textPVPcaja, textPorcentajeCaja, textTotalArticuloCaja);
				CancelaOperacion();
				CombocodigoBarrasCaja.requestFocus();
			}
		});

		botonagregarcaja.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					MetodosCaja.GuardatblCajero(modeloCajero, tblCajero, CombocodigoBarrasCaja, Combocoseleccion,
							textArticuloCaja, textPVPcaja, textPorcentajeCaja, textTotalArticuloCaja);
					CancelaOperacion();
					CombocodigoBarrasCaja.requestFocus();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		botonCantidad = new JButton("Cantidad"); // boton para cambiar la cantidad del ultimo articulo
		botonCantidad.setBorder(new LineBorder(Color.BLACK));
		botonCantidad.setBounds(945, 535, 100, 25);
		add(botonCantidad);
		botonCantidad.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent evt) {

				String cantidad = JOptionPane.showInputDialog("Cantidad?"); // pide al pulsar click mediante un panel de
																			// dialogo la cantidad a cambiar
				try {
					if (cantidad.length() != 0) {
						RegresaCantidad(cantidad);
						CuentaTotal();
						CombocodigoBarrasCaja.requestFocus();
					} else {
						CombocodigoBarrasCaja.requestFocus();
					}
				} catch (Exception e) {
					return;
				}
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		botonCantidad.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) { // pide al pulsar intro mediante un panel de dialogo la cantidad a
													// cambiar
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					String cantidad = JOptionPane.showInputDialog("Cantidad?");
					RegresaCantidad(cantidad);
					CuentaTotal();
					CombocodigoBarrasCaja.requestFocus();
				}
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}
		});

		CuentaTotal();

		botonTerminar = new JButton("Terminar"); // boton para imprimir la cuenta del cliente
		botonTerminar.setBorder(new LineBorder(Color.BLACK));
		botonTerminar.setBounds(450, 470, 140, 30);		
		botonTerminar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-recibir-efectivo-32.png"));
		add(botonTerminar);

		botonTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				guardaTablaarticulos(tblCajero);

				cantidadarticulostotal = 0;
				CuentaArticulos();
				
				VentanaTicket ventanaticket = new VentanaTicket(); // llama al panel de cobros VentanaTicket

				textStockTotal.setText("");

				botonnuevoticket.requestFocus();
			}
		});

		botonnuevoticket = new JButton("Nue.Ticket"); // borra la cuenta anterior y descuenta stock
		botonnuevoticket.setBorder(new LineBorder(Color.BLACK));
		botonnuevoticket.setBounds(1060, 635, 130, 30);
		botonnuevoticket
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-agregar-recibo-32.png"));
		add(botonnuevoticket);

		botonnuevoticket.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) { // pide al pulsar intro mediante un panel de dialogo la cantidad a
													// cambiar
				int newticket = JOptionPane.showConfirmDialog(null, "Comenazar nueva cuenta?");
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (newticket == JOptionPane.OK_OPTION) {
						FinalizarCuenta();
						CombocodigoBarrasCaja.requestFocus();
					}
				}
			}

			public void keyReleased(KeyEvent evt) {
			}

			public void keyTyped(KeyEvent evt) {
			}
		});

		botonnuevoticket.addActionListener(new ActionListener() { // finaliza la cuenta
			public void actionPerformed(ActionEvent arg0) {
				int newticket = JOptionPane.showConfirmDialog(null, "Comenazar nueva cuenta?");

				if (newticket == JOptionPane.OK_OPTION) {

					FinalizarCuenta();
					CombocodigoBarrasCaja.requestFocus();
				}
			}
		});

		botonBasededatos = new JButton("Base de Datos"); // boton para cerrar el panel de caja y volver a la ventana principal
		botonBasededatos.setBorder(new LineBorder(Color.BLACK));
		botonBasededatos.setBounds(580, 635, 150, 30);
		botonBasededatos
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-base-de-datos-26.png"));
		add(botonBasededatos);

		botonBasededatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Articulos articulos = new Articulos();
				articulos.setVisible(true);
			}
		});

		botonArticulosWeb = new JButton("Articulos Web"); // boton para acceder a articulos web
		botonArticulosWeb.setBorder(new LineBorder(Color.BLACK));
		botonArticulosWeb
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-internet-32.png"));
		botonArticulosWeb.setBounds(900, 635, 150, 30);
		add(botonArticulosWeb);

		botonArticulosWeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArticulosWeb articulosweb = new ArticulosWeb();
				articulosweb.setVisible(true);

			}
		});

		botonsalir = new JButton("Salir"); // crea el boton salir de la aplicación
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-salida-24.png"));
		botonsalir.setBounds(450, 635, 120, 30);
		add(botonsalir);

		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int salir = JOptionPane.showConfirmDialog(null, "¿Salir de la aplicación?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == salir) {
					System.exit(0);
				}
			}
		});
		
		botonAutomatiza = new JButton("Actualizar"); // crea el boton para ir a la pantalla de seleccion de categoria
		botonAutomatiza.setBorder(new LineBorder(Color.BLACK));
		botonAutomatiza.setBounds(740, 635, 150, 30);
		botonAutomatiza.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-actualizar-24.png"));
		add(botonAutomatiza);

		botonAutomatiza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				AutomatizaFacturas automatizafacturas = new AutomatizaFacturas();
				automatizafacturas.setVisible(true);
			}
		});

		botongastos = new JButton("Cuentas"); // accedemos al cuaderno de gastos
		botongastos.setBorder(new LineBorder(Color.BLACK));
		botongastos.setBounds(30, 635, 120, 30);
		botongastos.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-transferencia-de-dinero-32.png"));
		add(botongastos);

		botongastos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CuadernoGastos cuadernogastos = new CuadernoGastos();
				cuadernogastos.setVisible(true);
			}
		});
		
		botonagenda = new JButton("Agenda"); // accedemos al cuaderno de gastos
		botonagenda.setBorder(new LineBorder(Color.BLACK));
		botonagenda.setBounds(160, 635, 120, 30);
		botonagenda.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-directorio-32.png"));
		add(botonagenda);

		botonagenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Agenda agenda = new Agenda();
				agenda.setVisible(true);
			}
		});

		botonfruta = new JButton("Fruta"); // accedemos a la lista de frutas
		botonfruta.setBorder(new LineBorder(Color.BLACK));
		botonfruta.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-frambuesa-32.png"));
		botonfruta.setBounds(290, 635, 120, 30);
		add(botonfruta);

		botonfruta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VentanaFruta ventanafruta = new VentanaFruta();
				ventanafruta.setVisible(true);
			}
		});

		aviso = new JLabel("¡¡AVISO, STOCK BAJO!!"); // aviso de stock bajo
		aviso.setBounds(785, 475, 250, 25);
		aviso.setFont(new java.awt.Font("Tahoma", 1, 18));
		aviso.setForeground(Color.red);
		aviso.setVisible(false);
		add(aviso);

		StockTotal = new JLabel("STOCK"); // muestra en pantalla la cantidad de stock
		StockTotal.setBounds(615, 470, 140, 30);
		StockTotal.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(StockTotal);
		
		textStockTotal = new JTextField();
		textStockTotal.setBorder(new LineBorder(Color.BLACK));
		textStockTotal.setHorizontalAlignment(JTextField.CENTER);
		textStockTotal.setFont(new Font("Tahoma", 1, 30));
		textStockTotal.setBounds(710, 470, 60, 30);
		textStockTotal.setEditable(false);
		add(textStockTotal);

		textStockTotal.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
			}

			public void insertUpdate(DocumentEvent arg0) {

				String s = textStockTotal.getText();
				int a = Integer.parseInt(s);
				
				if (a <= 1) { // condición para mostrar aviso de STOCK BAJO!!
					aviso.setVisible(true);
					Toolkit tk = Toolkit.getDefaultToolkit ();
					tk.beep ();
				} else {
					aviso.setVisible(false);
				}
			}
			
			public void removeUpdate(DocumentEvent arg0) {
			}
		});

		botonguardarlista = new JButton(""); // añade producto a la lista de compra personalizada
		botonguardarlista.setBorder(new LineBorder(Color.BLACK));
		botonguardarlista.setBounds(240, 35, 80, 30);
		botonguardarlista
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-editar-fila-30.png"));
		add(botonguardarlista);

		botonguardarlista.addActionListener(new ActionListener() { // el botónguardalista admite varías funcionalidades,
																	// guarda en lista desde
			public void actionPerformed(ActionEvent e) { // codigo de barras y desde campo nuevo artículo

				if (txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() == null) {
					if (textArticuloCaja.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "No hay ningún producto seleccionado");
					} else {
						MetodosLista.ExtraeListaAyala(modeloLista, tblLista, Combocoseleccion, textArticuloCaja);
						CombocodigoBarrasCaja.requestFocus();
					}
				} else if (!txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Debe insertar la categoría");
				} else if (txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() != null) {
					JOptionPane.showMessageDialog(null, "Debe insertar el nombre del producto");
				} else {
					MetodosLista.IngresoListaManual(modeloLista, Combocoseleccion, txtnuevoarticulolista,
							Combocategoria);
					txtnuevoarticulolista.setText("");
					Combocategoria.setSelectedItem(null);
					CombocodigoBarrasCaja.requestFocus();
				}
			}
		});

		botonguardarlista.addKeyListener(new KeyListener() { // el botónguardalista admite varías funcionalidades,
																// guarda en lista desde
			public void keyPressed(KeyEvent e) { // codigo de barras y desde campo nuevo artículo

				if (txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() == null) {
					if (textArticuloCaja.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "No hay ningún producto seleccionado");
					} else {
						MetodosLista.ExtraeListaAyala(modeloLista, tblLista, Combocoseleccion, textArticuloCaja);
						CombocodigoBarrasCaja.requestFocus();
					}
				} else if (!txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Debe insertar la categoría");
				} else if (txtnuevoarticulolista.getText().equals("") && Combocategoria.getSelectedItem() != null) {
					JOptionPane.showMessageDialog(null, "Debe insertar el nombre del producto");
				} else {
					MetodosLista.IngresoListaManual(modeloLista, Combocoseleccion, txtnuevoarticulolista,
							Combocategoria);
					txtnuevoarticulolista.setText("");
					Combocategoria.setSelectedItem(null);
					CombocodigoBarrasCaja.requestFocus();
				}

			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		nuevoarticulolista = new JLabel("Inserte producto y categoría"); // etiqueta de orientacion a la tabla lista de compra
		nuevoarticulolista.setBounds(30, 68, 250, 25);
		nuevoarticulolista.setFont(new java.awt.Font(null, Font.PLAIN, 15));
		add(nuevoarticulolista);
		
		txtnuevoarticulolista = new JTextField(); // campo para insertar un articulo en las listas de compra
		txtnuevoarticulolista.setBorder(new LineBorder(Color.BLACK));
		txtnuevoarticulolista.setFont(new Font("Tahoma", 1, 20));
		txtnuevoarticulolista.setBounds(30, 90, 255, 25);
		add(txtnuevoarticulolista);

		txtnuevoarticulolista.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {

					Combocategoria.requestFocus();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		botoncategoria = new JButton(); // crea el boton para ir a la pantalla de seleccion de categoria
		botoncategoria.setBorder(new LineBorder(Color.BLACK));
		botoncategoria.setBounds(330, 35, 80, 30);
		botoncategoria.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-32.png"));
		add(botoncategoria);

		botoncategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				OrdenaCategoria ordenacategoria = new OrdenaCategoria(Combocategoria);
				ordenacategoria.setVisible(true);
			}
		});

		Combocategoria = new JComboBox<>(); // JComboBox para ordenar por calles en los almacenes
		Combocategoria.setBorder(new LineBorder(Color.BLACK));
		Combocategoria.setBounds(300, 90, 107, 25);
		Combocategoria.setFont(new java.awt.Font(null, Font.PLAIN, 20)); // JComboBox
		add(Combocategoria);

		Combocategoria.removeAllItems();
		ArrayList<String> listaCategoria = new ArrayList<String>(); // autocompletar en campo codigo de barras
		listaCategoria = MetodosCaja.LlenarComboCajaCategoria();

		for (int i = 0; i < listaCategoria.size(); i++) {

			if (Combocategoria.getSelectedIndex() == 0) {
				
				Combocategoria.setSelectedItem("");
			}

			AutoCompleteDecorator.decorate(Combocategoria); // metodo para autocompletar con la columna
			Combocategoria.addItem(listaCategoria.get(i));
		}

		Combocategoria.getEditor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				botonguardarlista.requestFocus();
			}
		});

		botonimprimirlista = new JButton(); // imprime las listas de compra
		botonimprimirlista.setBorder(new LineBorder(Color.BLACK));
		botonimprimirlista.setBounds(215, 550, 60, 35);
		add(botonimprimirlista);
		botonimprimirlista
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-imprimir-32.png"));
		botonimprimirlista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int guardalista = JOptionPane.showConfirmDialog(null,
						"Quieres imprimir la lista de " + Combocoseleccion.getSelectedItem().toString() + " ?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == guardalista) {

					MetodosLista.guardaListaCompra(tblLista, Combocoseleccion);
					Imprimir.ImprimirListaCompra();
				}
			}
		});

		botonborralista = new JButton(); // borra por completo las listas de compra
		botonborralista.setBorder(new LineBorder(Color.BLACK));
		botonborralista.setBounds(355, 550, 55, 35);
		botonborralista.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-basura-32.png"));
		add(botonborralista);

		botonborralista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MetodosLista.BorraLista(modeloLista, tblLista, Combocoseleccion);
			}
		});

		botonactivaordenalista = new JButton();
		botonactivaordenalista.setBorder(new LineBorder(Color.BLACK));
		botonactivaordenalista.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-asignación-entregado-32.png"));
		botonactivaordenalista.setBounds(285, 550, 60, 35);
		add(botonactivaordenalista);

		botonactivaordenalista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ListaCompra listacompra = new ListaCompra();
				listacompra.setVisible(true);
			}
		});

		ayala = new JLabel("LISTA DE AYALA"); // crea etiquetas para saber en que lista de compra estamos
		ayala.setBounds(40, 555, 200, 25);
		ayala.setFont(new java.awt.Font("Tahoma", 1, 20));
		ayala.setVisible(false);
		add(ayala);

		barea = new JLabel("LISTA DE BAREA");
		barea.setBounds(40, 555, 200, 25);
		barea.setFont(new java.awt.Font("Tahoma", 1, 20));
		barea.setVisible(false);
		add(barea);

		solarica = new JLabel("LISTA DE SOLARICA");
		solarica.setBounds(35, 555, 200, 25);
		solarica.setFont(new java.awt.Font("Tahoma", 1, 17));
		solarica.setVisible(false);
		add(solarica);

		otros = new JLabel("OTRA LISTA");
		otros.setBounds(50, 555, 200, 25);
		otros.setFont(new java.awt.Font("Tahoma", 1, 20));
		otros.setVisible(false);
		add(otros);

		botonborrafila = new JButton(""); // borra un producto de la lista, pero antes hay que señalarlo
		botonborrafila.setBorder(new LineBorder(Color.BLACK));
		botonborrafila.setBounds(150, 35, 80, 30);
		botonborrafila.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-borrar-24.png"));
		add(botonborrafila);

		botonborrafila.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				MetodosLista.EliminarFila(ev, modeloLista, tblLista, Combocoseleccion);
				MetodosLista.MostrarLista(modeloLista, tblLista, Combocoseleccion, textArticuloCaja, ayala, barea,
						solarica, otros);
				CombocodigoBarrasCaja.requestFocus();
			}
		});

		checkbox = new JCheckBox(); // chekbox para reinicializar los campos
		checkbox.setBorder(new LineBorder(Color.BLACK));
		checkbox.setBounds(750, 537, 20, 20);
		add(checkbox);

		checkbox.addActionListener(new ActionListener() { // crea evento al pulsar sobre ckeckbox
			public void actionPerformed(ActionEvent evt) {

				CancelaOperacion();
			}
		});

		botonarticulomanual = new JButton("Art. manual"); // con este botón podemos meter un nuevo artículo
		botonarticulomanual.setBorder(new LineBorder(Color.BLACK));
		botonarticulomanual.setBounds(790, 535, 130, 25); // en la base de datos pero sin stock
		add(botonarticulomanual);

		botonarticulomanual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ElegirAgregar();
			}
		});

		botonarticulomanual.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent evt) { // pide al pulsar intro mediante un panel de dialogo la cantidad a
													// cambiar
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					ElegirAgregar();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		// El producto seleccionado padrá agregarse a la lista de compra
		tblCajero.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				int seleccionfila = tblCajero.rowAtPoint(e.getPoint());
				
				BorraCampos();				

				textArticuloCaja.setText(tblCajero.getValueAt(seleccionfila, 0).toString());
				
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}			
		});

		modeloCajero.addTableModelListener(new TableModelListener() { // crea evento al pulsar sobre las casillas de
																		// JTable cajero
			public void tableChanged(TableModelEvent e) {
				CambiaCelda(modeloCajero, tblCajero, e); // llama a método que activa las celdas
			}
		});

		MetodosLista.MostrarLista(modeloLista, tblLista, Combocoseleccion, textArticuloCaja, ayala, barea, solarica,
				otros);
		CuentaTotal();
		ElegirAgregar();

	} // fin del constructor

	// método que descuenta stock y crea cuenta nueva
	public void FinalizarCuenta() {

		MetodosCaja.DescuentaStock(tblCajero); // llama al método descuenta stock
		aviso.setVisible(false); // elimina el aviso de stock bajo
		modeloCajero.setRowCount(0); // borra la JTable entera
		textStockTotal.setText("");
		textTotalCaja.setText("");

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		try {

			String fechacompleta = dia + "/" + mes + "/" + annio;

			con = Conexion.ejecutarConexion();

			String sql = "INSERT INTO ControlVentas(Fecha,TotalDia) VALUES (?,?)";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, fechacompleta);
			pst.setString(2, DevuelveCuentaTotal());

			pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		CombocodigoBarrasCaja.requestFocus();
	}

	// método para cambiar la cantidad de un articulo en caja y para sumar el total
	// de los articulos
	public void RegresaCantidad(String cantidad) {
		try {			
			if (cantidad.matches("[0-9]*")) {
				textCantidadCaja.setText(cantidad);
				int cantidad1 = Integer.parseInt(cantidad);
				int indiceUltimaFila = modeloCajero.getRowCount() - 1;

				DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
				simbolo.setDecimalSeparator('.');
				DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

				if (cantidad1 > 1) {

					modeloCajero.setValueAt(cantidad, indiceUltimaFila, 1); // recoge la cantidad del campo cantidad
					cantidad = textCantidadCaja.getText();

					Object cadena1 = modeloCajero.getValueAt(indiceUltimaFila, 2); // realiza la operacion para multiplicar
																				// pvp*cantidad
					Float num2 = Float.parseFloat(String.valueOf(cadena1));
					Float multiplica = cantidad1 * num2;
					String cadena2 = String.valueOf(formato.format(multiplica));
					modeloCajero.setValueAt(cantidad, indiceUltimaFila, 1);
					modeloCajero.setValueAt(cadena2, indiceUltimaFila, 3);
					textTotalArticuloCaja.setText(cadena2);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No hay articulos en la cuenta");
			//e.printStackTrace();
		}
	}

	// método para sumar el valor total de la columna precio
	public void CuentaTotal() {
		Float subtotal = (float) 0, total = (float) 0;

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		try {
			if (tblCajero.getRowCount() > 0) {
				for (int i = 0; i < tblCajero.getRowCount(); i++) {
					subtotal = Float.parseFloat(tblCajero.getValueAt(i, 3).toString()); // La columna 3 nos da el precio
					total = total + subtotal;
				}

				String result = formato.format(total);
				textTotalCaja.setText(String.valueOf(result));
				cuentatotal = String.valueOf(result);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// método que devuelve el total de la cuenta
	public String DevuelveCuentaTotal() {

		return cuentatotal;
	}

	// método que cuenta los articulos
	public int CuentaArticulos() {

		for (int i = 0; i < tblCajero.getRowCount(); i++) {
			Object cantidadarticulos = tblCajero.getValueAt(i, 1).toString();
			String cadenacantidadarticulos = String.valueOf(cantidadarticulos);
			cantidadcuenta = Integer.parseInt(cadenacantidadarticulos);
			cantidadarticulostotal = cantidadarticulostotal + cantidadcuenta;
		}

		return cantidadarticulostotal;
	}

	// método que borra todos los campos
	public void BorraCampos() {
		CombocodigoBarrasCaja.setSelectedItem("");
		textArticuloCaja.setText("");
		textPVPcaja.setText("");
		textCantidadCaja.setText("");
		textPorcentajeCaja.setText("");
		textTotalArticuloCaja.setText("");
		textStockTotal.setText("");
	}

	// restablece los campos a estado inicial
	public void CancelaOperacion() {
		CombocodigoBarrasCaja.setEnabled(true);
		botonarticulomanual.setEnabled(true);
		botonagregarcaja.setEnabled(false);
		botonTerminar.setEnabled(true);
		botonCantidad.setEnabled(true);
		botonBasededatos.setEnabled(true);
		botonnuevoticket.setEnabled(true);
		textArticuloCaja.setEnabled(false);
		textPVPcaja.setEnabled(false);
		textCantidadCaja.setEnabled(false);
		textPorcentajeCaja.setEnabled(false);
		textTotalArticuloCaja.setEnabled(false);

		BorraCampos();
	}

	// define los distintos estados según el checkbox este activo o desactivo
	public void ElegirAgregar() {
		if (checkbox.isSelected() == false) { // checkbox con palometa

			CombocodigoBarrasCaja.setEnabled(true);

			botonarticulomanual.setEnabled(true);
			botonTerminar.setEnabled(true);
			botonCantidad.setEnabled(true);
			botonBasededatos.setEnabled(true);
			botonagregarcaja.setEnabled(false);
			botonnuevoticket.setEnabled(true);

			textArticuloCaja.setEnabled(false);
			textPVPcaja.setEnabled(false);
			textCantidadCaja.setEnabled(false);
			textPorcentajeCaja.setEnabled(false);
			textTotalArticuloCaja.setEnabled(false);

			BorraCampos();

			CombocodigoBarrasCaja.requestFocus();

		} else { // chekbox sin palometa
			CombocodigoBarrasCaja.setEnabled(true);
			botonarticulomanual.setEnabled(false);
			botonTerminar.setEnabled(false);
			botonCantidad.setEnabled(false);
			botonBasededatos.setEnabled(false);
			botonagregarcaja.setEnabled(true);
			botonnuevoticket.setEnabled(false);

			textArticuloCaja.setEnabled(true);
			textPVPcaja.setEnabled(true);
			textCantidadCaja.setEnabled(false);
			textPorcentajeCaja.setEnabled(true);
			textTotalArticuloCaja.setEnabled(true);

			BorraCampos();

			CombocodigoBarrasCaja.requestFocus();
		}
	}

	// dibuja rectangulos
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(440, 515, 760, 165);
		g.drawRect(20, 20, 400, 580);
		g.drawRect(20, 620, 400, 60);	
	}

	// método para cambiar celdas dependientes unas de otras
	public void CambiaCelda(DefaultTableModel modeloCajero, JTable tblCajero, TableModelEvent e) {

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		if (e.getType() == TableModelEvent.UPDATE) {
			try {
				Object evalua = null;

				if (e.getColumn() == 1) { // al cambiar la cantidad modifica el total de fila

					int filaevento = e.getFirstRow();
					int columnaevento = e.getColumn();
					evalua = tblCajero.getValueAt(filaevento, columnaevento);
					int cambiocantidadfila = e.getFirstRow();

					String PVPfila = String.valueOf(modeloCajero.getValueAt(cambiocantidadfila, 2));
					Float evaluaPVP = Float.parseFloat(PVPfila);
					String evaluacadena = String.valueOf(evalua);
					int evaluaCantidad = Integer.parseInt(evaluacadena);

					Float multiplicafila = evaluaPVP * evaluaCantidad;
					String result = formato.format(multiplicafila);

					modeloCajero.setValueAt(result, cambiocantidadfila, 3);

				} else if (e.getColumn() == 2) { // al cambiar el precio modifica el total de fila
					int filaevento = e.getFirstRow();
					int columnaevento = e.getColumn();
					evalua = tblCajero.getValueAt(filaevento, columnaevento);
					int cambioPVPfila = e.getFirstRow();

					String cantidadfila = String.valueOf(modeloCajero.getValueAt(cambioPVPfila, 1));
					Float evaluaCantidad = Float.parseFloat(cantidadfila);

					String evaluacadena = String.valueOf(evalua);
					Float evaluaPVP = Float.parseFloat(evaluacadena);
					Float multiplicafila = evaluaCantidad * evaluaPVP;
					String cadenamultiplica = formato.format(multiplicafila);
					// System.out.println("La cantidad de " + evaluaCantidad + " * " + evaluaPVP + "
					// = " + multiplicafila);

					modeloCajero.setValueAt(cadenamultiplica, cambioPVPfila, 3);

				} else if (e.getColumn() == 4) { // al pulsar el JButton renderizado borra la fila selecionada
					int filaevento = e.getFirstRow();
					int columnaevento = e.getColumn();
					evalua = tblCajero.getValueAt(filaevento, columnaevento);
					int eliminarfila = JOptionPane.showConfirmDialog(null,
							"Esta seguro de que quiere eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == eliminarfila) {
						modeloCajero.removeRow(filaevento);
					}
					if (tblCajero.getRowCount() == 0) {
						textTotalCaja.setText("0.00");
						aviso.setVisible(false);
					}

				}
				CuentaTotal();
			} catch (Exception evt) {
				JOptionPane.showMessageDialog(null, "Hay ocurrido un ERROR!");
				evt.printStackTrace();
			}
		}
	}

	// Guarda en archivo txt tblCajero y encabezado
	public void guardaTablaarticulos(JTable tblCajero) {
		try {

			String ticket_articulos = "C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\ticket_articulos.txt";
			BufferedWriter bfw = new BufferedWriter(new FileWriter(ticket_articulos));

			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("           ALIMENTACION ADRIANO           ");
			bfw.newLine();
			bfw.write("      C/MANUEL GONZALEZ RODRIGUEZ,N14     ");
			bfw.newLine();
			bfw.write("      TLF : 955997472  (SANTIPONCE)       ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("    LLEVAMOS PEDIDOS A DOMICILIO GRATIS   ");
			bfw.newLine();
			bfw.write("            A PARTIR DE 25 EUROS          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("    DESCRIPCION        UNI.PRECIO IMPORTE ");
			bfw.newLine();

			for (int i = 0; i < tblCajero.getRowCount(); i++) {
				for (int j = 0; j < tblCajero.getColumnCount() - 1; j++) {

					String temporal = tblCajero.getValueAt(i, j).toString();

					if (j < tblCajero.getColumnCount() - 4) { // si esta en la fila 0 inserta varios espacios
						String tempo = temporal + "                         "; // y recorta la cadena a 20 caracteres
						String temp = tempo.substring(0, 23);
						bfw.write(temp);
					} else if (j > tblCajero.getColumnCount() - 4 && j < tblCajero.getColumnCount()) { // si esta en la
																										// linea 1,2 y 3
																										// suma varios
																										// espacios
						String tempo = temporal + "                         "; // y recorta la cadena a 4 caracteres
						String temp = tempo.substring(0, 4);
						bfw.write(temp);
					} else {
						bfw.write(temporal);
					}

					if (j < tblCajero.getColumnCount() - 4) { // inserta un tabulado
						bfw.write("\t");
					} else {
						bfw.write("   "); // inserta varios espacios en blanco
					}
				}
				bfw.newLine(); // inserta nueva linea.
			}

			bfw.close(); // cierra archivo
			System.out.println("El archivo fue salvado correctamente!");
		} catch (IOException e) {
			System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
		}
	}

}
