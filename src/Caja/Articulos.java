package Caja;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Articulos extends JFrame {

	// Creamos pantalla para controlar los articulos
	public Articulos() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);
		
		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		panelArticulos panel = new panelArticulos();

		add(panel);
	}

}

class panelArticulos extends JPanel {
	// declaracion de campos y etiquetas

	Connection con;

	JLabel J1 = new JLabel("Código"); // etiquetas
	JLabel J2 = new JLabel("Nombre del Articulos");
	JLabel J3 = new JLabel("Precio");
	JLabel J4 = new JLabel("Unid.");
	JLabel J5 = new JLabel("Proveedor");
	JLabel J6 = new JLabel("%");
	JLabel J7 = new JLabel("PVP");
	JLabel J8 = new JLabel("Stk");
	JLabel J9 = new JLabel("S.min");
	JLabel J10 = new JLabel("Cat.");
	JLabel J12 = new JLabel("Buscar por Nombre :");
	JLabel J13 = new JLabel("Buscar por Código  :");

	JButton botonMostrar = new JButton("MostrarTodos");
	JButton botonAgregar = new JButton("Añadir");
	JButton botonSalir = new JButton("Salir");
	JButton botonArticulosWeb = new JButton("ArticulosWeb");
	JButton botonModificar = new JButton("Modificar");
	JButton botonCategoria = new JButton("Categoria");

	JComboBox<String> menuProveedor; // menu proveedores
	JComboBox<String> menuCategoria;

	JTextField textCodigo = new JTextField(); // campos
	JTextField textArticulo = new JTextField();
	JTextField textPrecio = new JTextField();
	JTextField textUnidades = new JTextField();
	JTextField textPorcentaje = new JTextField();
	JTextField textPVP = new JTextField();
	JTextField textStock = new JTextField();
	JTextField textStkmin = new JTextField();
	JTextField buscaNombre = new JTextField();
	JTextField buscaCodigo = new JTextField();

	// declaracion de tabla de articulos

	String[] titulos = { "Codigo barras", "Articulo", "PVP", "%", "Ayala", "Barea", "Otros", "Unid", "Stock", "Stk min",
			"", "", "" };
	DefaultTableModel model;
	JTable tbl;
	JScrollPane jscroll;

	private TableRowSorter<TableModel> trsfiltro;
	String filtro;

	MetodosArticulos metodosArticulos = new MetodosArticulos(); // instancia un objeto de la clase
																// MetodosArticulosllamado objeto

	public panelArticulos() {

		model = new DefaultTableModel(null, titulos);
		tbl = new JTable(model);
		jscroll = new JScrollPane(tbl);
		jscroll.setBorder(new LineBorder(Color.BLACK));

		jscroll.setBounds(20, 140, 1180, 405);
		add(jscroll);

		tbl.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		setLayout(null); // asignamos un layout nulo y difinimos los campos y etiquetas
		setBackground(Color.CYAN);

		J1.setBounds(55, 570, 100, 23); // codigo barra
		J1.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J1);
		
		textCodigo.setBounds(30, 595, 125, 25);
		textCodigo.setBorder(new LineBorder(Color.BLACK));
		textCodigo.setFont(new Font("Tahoma", 1, 15));
		add(textCodigo);
		
		textCodigo.addActionListener(new ActionListener() { // Realiza la accion que se le manda, en este caso es pulsar
															// intro
			public void actionPerformed(ActionEvent evt) {

				EvitaReplicas(textCodigo, tbl);
				((JTextField) evt.getSource()).transferFocus();
			}
		});

		J2.setBounds(285, 570, 255, 20); // declaramos articulos
		J2.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J2);
		
		textArticulo.setBounds(165, 595, 405, 25);
		textArticulo.setBorder(new LineBorder(Color.BLACK));
		textArticulo.setFont(new Font("Tahoma", 1, 15));
		add(textArticulo);
		
		textArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
			}
		});

		J3.setBounds(580, 570, 75, 20); // Precio
		J3.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J3);
		
		textPrecio.setBounds(580, 595, 75, 25);
		textPrecio.setBorder(new LineBorder(Color.BLACK));
		textPrecio.setFont(new Font("Tahoma", 1, 20));
		add(textPrecio);
		
		textPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
			}
		});
		
		J4.setBounds(675, 570, 65, 20); // unidades
		J4.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J4);
		
		textUnidades.setBounds(665, 595, 65, 25);
		textUnidades.setBorder(new LineBorder(Color.BLACK));
		textUnidades.setFont(new Font("Tahoma", 1, 20));
		add(textUnidades);
		
		textUnidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
				String unidad = ""; // manda el valor del JTextField textunidades
				unidad = textUnidades.getText(); // al JTextField Stock
				textStock.setText(unidad);
			}
		});
		
		J5.setBounds(740, 570, 145, 20); // proveedor
		J5.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J5);
		
		String opciones[] = new String[] { "Ayala", "Barea", "Solarica", "Otros" }; // Descripción del menu de selección
		menuProveedor = new JComboBox<String>(); // de proveedor
		menuProveedor.setBorder(new LineBorder(Color.BLACK));
		menuProveedor.setBounds(740, 595, 105, 25);
		menuProveedor.setFont(new Font("Tahoma", 1, 16));
		menuProveedor.setModel(new DefaultComboBoxModel<>(opciones));
		add(menuProveedor);
		
		menuProveedor.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) { // el foco pasa al campo porcentaje al pulsar intro
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textPorcentaje.requestFocus();
				}
			}
		});
		
		J6.setBounds(865, 570, 60, 20); // porcentaje
		J6.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J6);
		
		textPorcentaje.setBounds(855, 595, 35, 25);
		textPorcentaje.setBorder(new LineBorder(Color.BLACK));
		textPorcentaje.setFont(new Font("Tahoma", 1, 20));
		add(textPorcentaje);
		
		textPorcentaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();

				Float operacion, operacion_dos_decimales;
				String resultado;
				if(textPorcentaje.getText().length() != 0)  {
					
					Float extraePorcentaje = Float.parseFloat(textPorcentaje.getText());
					Float extraePrecio = Float.parseFloat(textPrecio.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;
				
					DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
					simbolo.setDecimalSeparator('.');
					DecimalFormat formato = new DecimalFormat("###0.00", simbolo);
				
					resultado = formato.format(operacion);
				
					textPVP.setText(resultado);
				}
			}
		});
		
		J7.setBounds(910, 570, 60, 20); // PVP
		J7.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J7);
		
		textPVP.setBounds(900, 595, 65, 25);
		textPVP.setBorder(new LineBorder(Color.BLACK));
		textPVP.setFont(new Font("Tahoma", 1, 20));
		add(textPVP);
		textPVP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
			}
		});

		J8.setBounds(975, 570, 60, 20); // stock
		J8.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J8);
		
		textStock.setBounds(975, 595, 50, 25);
		textStock.setBorder(new LineBorder(Color.BLACK));
		textStock.setFont(new Font("Tahoma", 1, 20));
		add(textStock);
		
		textStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
			}
		});

		J9.setBounds(1040, 570, 80, 20); // Stock mínimo
		J9.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J9);
		
		textStkmin.setBounds(1035, 595, 50, 25);
		textStkmin.setBorder(new LineBorder(Color.BLACK));
		textStkmin.setFont(new Font("Tahoma", 1, 20));
		add(textStkmin);
		
		textStkmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				((JTextField) evt.getSource()).transferFocus();
			}
		});

		J10.setBounds(1100, 570, 60, 20);
		J10.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(J10); // Descripción del menu de selección
		
		menuCategoria = new JComboBox<>(); // de categoría
		menuCategoria.setBorder(new LineBorder(Color.BLACK));
		menuCategoria.setBounds(1095, 595, 95, 25);
		menuCategoria.setFont(new Font(null, 1, 15));
		menuCategoria.removeAllItems();
		ArrayList<String> lista = new ArrayList<String>();
		lista = MetodosArticulos.LlenarCombo();

		for (int i = 0; i < lista.size(); i++) {
			if (menuCategoria.getSelectedIndex() == 0) {
				menuCategoria.setSelectedItem("");
			}
			AutoCompleteDecorator.decorate(menuCategoria); // metodo para autocompletar con la columna
			menuCategoria.addItem(lista.get(i));
		}

		add(menuCategoria);
		menuCategoria.getEditor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				botonAgregar.setEnabled(true);
				botonAgregar.requestFocus();
			}
		});

		botonAgregar.setBounds(1060, 635, 130, 30); // creando boton agregar
		botonAgregar.setBorder(new LineBorder(Color.BLACK));
		botonAgregar.setEnabled(false);
		botonAgregar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-añadir-imagen-24.png"));
		add(botonAgregar);

		botonAgregar.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						metodosArticulos.Guardar(evt, textCodigo, textArticulo, textPrecio, textUnidades, menuProveedor,
								textPorcentaje, textPVP, textStock, textStkmin, menuCategoria);
						MetodosArticulos.Mostrar(tbl);
						botonAgregar.setEnabled(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			public void keyReleased(KeyEvent evt) {
			}
			public void keyTyped(KeyEvent evt) {
			}
		});
		
		botonAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					metodosArticulos.Guardar(null, textCodigo, textArticulo, textPrecio, textUnidades, menuProveedor,
							textPorcentaje, textPVP, textStock, textStkmin, menuCategoria);
					MetodosArticulos.Mostrar(tbl);
					botonAgregar.setEnabled(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}			
		});

		J12.setBounds(40, 80, 400, 35); // etiqueta y campo JTextField del buscador por nombre
		J12.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(J12);
		
		buscaNombre.setBounds(320, 85, 300, 30);
		buscaNombre.setBorder(new LineBorder(Color.BLACK));
		buscaNombre.setFont(new Font("Tahoma", 1, 20));
		add(buscaNombre);

		buscaNombre.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tbl.getModel());
				tbl.setRowSorter(trsfiltro);
			}
		});

		buscaNombre.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (buscaNombre.getText()).toUpperCase();
				buscaNombre.setText(cadena);
				repaint();
				filtroNombre();
			}
		});

		J13.setBounds(40, 25, 400, 35); // etiqueta y campo JTextField del buscador por código de barras
		J13.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(J13);
		
		buscaCodigo.setBounds(320, 30, 300, 30);
		buscaCodigo.setBorder(new LineBorder(Color.BLACK));
		buscaCodigo.setFont(new Font("Tahoma", 1, 20));
		add(buscaCodigo);

		buscaCodigo.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tbl.getModel());
				tbl.setRowSorter(trsfiltro);
			}
		});

		buscaCodigo.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (buscaCodigo.getText()).toUpperCase();
				buscaCodigo.setText(cadena);
				repaint();
				filtroCodigo();
			}
		});
		
		botonArticulosWeb.setBounds(690, 30, 150, 35);
		botonArticulosWeb.setBorder(new LineBorder(Color.BLACK));
		botonArticulosWeb.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-internet-32.png"));
		add(botonArticulosWeb);

		botonArticulosWeb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				ArticulosWeb articulosweb = new ArticulosWeb();
				articulosweb.setVisible(true);

				Window w = SwingUtilities.getWindowAncestor(panelArticulos.this);
				w.dispose();
			}
		});
	
		botonSalir.setBounds(860, 30, 150, 35);
		botonSalir.setBorder(new LineBorder(Color.BLACK));
		botonSalir.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		add(botonSalir);

		botonSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Window w = SwingUtilities.getWindowAncestor(panelArticulos.this);
				w.dispose();
			}
		});

		botonModificar.setBounds(1030, 80, 150, 35);
		botonModificar.setBorder(new LineBorder(Color.BLACK));
		botonModificar.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-editar-propiedad-32.png"));
		add(botonModificar);

		botonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				botonModificar.setEnabled(false);
			}
		});

		botonCategoria.setBounds(1030, 30, 150, 35);
		botonCategoria.setBorder(new LineBorder(Color.BLACK));
		botonCategoria.setIcon(
				new ImageIcon("C:\\Users\\chidr\\\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-32.png"));
		add(botonCategoria);

		botonCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OrdenaCategoria ordenacategoria = new OrdenaCategoria(menuCategoria);
				ordenacategoria.setVisible(true);
			}
		});

		botonMostrar.setBounds(860, 80, 150, 35);
		botonMostrar.setBorder(new LineBorder(Color.BLACK));
		botonMostrar.setIcon(
				new ImageIcon("C:\\Users\\chidr\\\\OneDrive\\Desktop\\Database\\icons8-mostrar-fotogramas-32.png"));
		add(botonMostrar);

		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					botonModificar.setEnabled(true);
					buscaCodigo.setText("");
					buscaNombre.setText("");
					textCodigo.setText(""); // borra todos los campos
					textArticulo.setText("");
					textPrecio.setText("");
					textUnidades.setText("");
					textPorcentaje.setText("");
					textPVP.setText("");
					textStock.setText("");
					textStkmin.setText("");
					menuCategoria.setSelectedIndex(-1);
					buscaNombre.requestFocus();

					MetodosArticulos metodosarticulos = new MetodosArticulos();
					metodosarticulos.Mostrar(tbl);

					Robot robot = new Robot();

					robot.keyPress(KeyEvent.VK_ENTER); // Simula presionar la tecla
					robot.keyRelease(KeyEvent.VK_ENTER); // Simula soltar la tecla

				} catch (AWTException e1) {
					throw new RuntimeException("Error");
				}
			}
		});

		MetodosArticulos.Mostrar(tbl);
		tbl.addMouseListener(new EscuchaMouse());
	}

	// metodos para la barra buscadora por nombre y por articulos
	public void filtroNombre() {
		filtro = buscaNombre.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(buscaNombre.getText(), 1));
	}

	public void filtroCodigo() {
		filtro = buscaCodigo.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(buscaCodigo.getText(), 0));
	}

	// metodo que detecta si un articulo está repetido
	protected void EvitaReplicas(JTextField textCodigo, JTable tbl) {

		try {
			for (int i = 0; i < tbl.getRowCount(); i++) {
				String valor = tbl.getValueAt(i, 0).toString();
				System.out.println(valor);
				if (textCodigo.getText().replace(" ", "").equals(valor.replace(" ", ""))) {
					JOptionPane.showMessageDialog(null, "El artículo ya existe");
					break;
				} else {
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// método que produce evento en la fila seleccionada y toma el valor de todas
	// las celdas de la fila seleccionada
	class EscuchaMouse implements MouseListener {
		public void mouseClicked(MouseEvent e) {

			int seleccionfila = tbl.rowAtPoint(e.getPoint());

			String Codigomodifica;
			String Articulomodifica;
			String PVPmodifica;
			String Porcentajemodifica;
			String PrecioAyalamodifica;
			String PrecioBareamodifica;
			String PrecioSolaRicamodifica = null;
			String PrecioOtrosmodifica = null;
			String Stockmodifica = null;
			String Stockminmodifica = null;
			String Categoriamodifica = null;

			if (!botonModificar.isEnabled()) { // si el botón modificar esta pulsado y los campos no son nulos

				if ((tbl.getValueAt(seleccionfila, 0) == null)) { // condición : si el campo es nulo mando un String
																	// nulo y si no mando el dato
					Codigomodifica = "";
				} else {
					Codigomodifica = tbl.getValueAt(seleccionfila, 0).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 1) == null)) {
					Articulomodifica = "";
				} else {
					Articulomodifica = tbl.getValueAt(seleccionfila, 1).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 2) == null)) {
					PVPmodifica = "";
				} else {
					PVPmodifica = tbl.getValueAt(seleccionfila, 2).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 3) == null)) {
					Porcentajemodifica = "";
				} else {
					Porcentajemodifica = tbl.getValueAt(seleccionfila, 3).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 4) == null)) {
					PrecioAyalamodifica = "";
				} else {
					PrecioAyalamodifica = tbl.getValueAt(seleccionfila, 4).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 5) == null)) {
					PrecioBareamodifica = "";
				} else {
					PrecioBareamodifica = tbl.getValueAt(seleccionfila, 5).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 6) == null)) {
					PrecioSolaRicamodifica = "";
				} else {
					PrecioSolaRicamodifica = tbl.getValueAt(seleccionfila, 6).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 7) == null)) {
					PrecioOtrosmodifica = "";
				} else {
					PrecioOtrosmodifica = tbl.getValueAt(seleccionfila, 7).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 8) == null)) {
					Stockmodifica = "";
				} else {
					Stockmodifica = tbl.getValueAt(seleccionfila, 8).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 9) == null)) {
					Stockminmodifica = "";
				} else {
					Stockminmodifica = tbl.getValueAt(seleccionfila, 9).toString();
				}

				if ((tbl.getValueAt(seleccionfila, 10) == null)) {
					Categoriamodifica = "";
				} else {
					Categoriamodifica = tbl.getValueAt(seleccionfila, 10).toString();
				}

				ModificaTablaArticulos modificatablaarticulos = new ModificaTablaArticulos(tbl, Codigomodifica,
						Articulomodifica, PVPmodifica, Porcentajemodifica, PrecioAyalamodifica, PrecioBareamodifica,
						PrecioSolaRicamodifica, PrecioOtrosmodifica, Stockmodifica, Stockminmodifica,
						Categoriamodifica);
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
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(20, 20, 1180, 105);
		g.drawRect(20, 560, 1180, 120);
	}
}
