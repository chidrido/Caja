package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ListaCompra extends JFrame {

	public ListaCompra() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);

		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		PanelListaCompra panellistacompra = new PanelListaCompra();

		add(panellistacompra);

	}
}

class PanelListaCompra extends JPanel {

	String[] tituloslistacompra = { "NOMBRE DE ARTICULO", "PRECIO", "CAT.", "" };

	DefaultTableModel modeloListaCompra;
	JTable tblListaCompra;
	JScrollPane scrollListaCompra;

	JTextField txtbuscador;
	JTextField txtagregar;

	JComboBox<String> comboseleccion;

	JButton botonimprimir;
	JButton botonsalir;
	JButton botonmostrartodo;
	JButton botonagregar;
	JButton borrafila;
	JButton borralista;
	JButton botonmarcacheckbox;
	JButton botondesmarcacheckbox;
	JButton botonpasarlista;
	JButton botoncategoria;

	JLabel etiquetabuscador;
	JLabel etiquetaagregar;
	JLabel etiquetamarcar;
	JLabel etiquetaimprimir;
	JLabel etiquetaborrar;
	
	JComboBox<String> combocategoria;

	private TableRowSorter<TableModel> trsfiltro;
	String filtro;

	static Connection con = null;

	public PanelListaCompra() {

		modeloListaCompra = new DefaultTableModel(null, tituloslistacompra);
		tblListaCompra = new JTable(modeloListaCompra);
		scrollListaCompra = new JScrollPane(tblListaCompra);
		scrollListaCompra.setBounds(25, 25, 635, 655);
		scrollListaCompra.setBorder(new LineBorder(Color.BLACK));
		add(scrollListaCompra);

		tblListaCompra.setBackground(Color.WHITE);

		setLayout(null);
		setBackground(Color.CYAN);

		tblListaCompra.getColumn(tblListaCompra.getModel().getColumnName(0)).setMaxWidth(400); // damos tamaño a las
																								// filas de la JTable
		tblListaCompra.getColumn(tblListaCompra.getModel().getColumnName(1)).setMaxWidth(80);
		tblListaCompra.getColumn(tblListaCompra.getModel().getColumnName(2)).setMaxWidth(80);
		tblListaCompra.getColumn(tblListaCompra.getModel().getColumnName(3)).setMaxWidth(80);
		tblListaCompra.setFont(new java.awt.Font("Tahoma", 0, 15));
		tblListaCompra.setRowHeight(30);
		tblListaCompra.setFont(new java.awt.Font("Tahoma", 0, 18));

		TableColumn agregarColumnCaja = tblListaCompra.getColumnModel().getColumn(3);
		agregarColumnCaja.setCellEditor(tblListaCompra.getDefaultEditor(Boolean.class));
		agregarColumnCaja.setCellRenderer(tblListaCompra.getDefaultRenderer(Boolean.class));

		CambioColorCaja cambiocolor = new CambioColorCaja();
		tblListaCompra.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gros oscuro
		tblListaCompra.setGridColor(Color.BLACK);

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblListaCompra.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);

		tblListaCompra.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas
		tblListaCompra.setAutoCreateRowSorter(true);

		comboseleccion = new JComboBox<>();
		comboseleccion.setBorder(new LineBorder(Color.BLACK));
		comboseleccion.setBounds(700, 40, 100, 50);
		String opciones[] = new String[] { "Ayala", "Barea", "Solarica", "Otros" };
		comboseleccion.setModel(new DefaultComboBoxModel<>(opciones));
		comboseleccion.setFont(new java.awt.Font("Dialog", 0, 25));
		add(comboseleccion);

		comboseleccion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				MostrarListaCompra(modeloListaCompra, tblListaCompra, comboseleccion);
			}
		});

		comboseleccion.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {

				try {
					MostrarListaCompra(modeloListaCompra, tblListaCompra, comboseleccion);

				} catch (Exception ex) {

					ex.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		combocategoria = new JComboBox<>();
		combocategoria.setBorder(new LineBorder(Color.BLACK));
		combocategoria.setFont(new java.awt.Font("Dialog", 0, 35));
		combocategoria.setBounds(815, 40, 110, 50);
		combocategoria.removeAllItems();
		ArrayList<String> lista = new ArrayList<String>();
		lista = MetodosArticulos.LlenarCombo();

		for (int i = 0; i < lista.size(); i++) {
			if (combocategoria.getSelectedIndex() == 0) {
				combocategoria.setSelectedItem("");
			}
			AutoCompleteDecorator.decorate(combocategoria); // método para autocompletar con la columna
			combocategoria.addItem(lista.get(i));
		}
		
		add(combocategoria);
		
		botoncategoria = new JButton(); // crea botón salir
		botoncategoria.setBorder(new LineBorder(Color.BLACK));
		botoncategoria.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-32.png"));
		botoncategoria.setBounds(940, 40, 110, 50);
		add(botoncategoria);

		botoncategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				OrdenaCategoria ordenacategoria = new OrdenaCategoria(combocategoria);
				ordenacategoria.setVisible(true);
			}
		});
		
		botonsalir = new JButton(); // crea botón salir
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		botonsalir.setBounds(1065, 40, 110, 50);
		add(botonsalir);

		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Window w = SwingUtilities.getWindowAncestor(PanelListaCompra.this);
				w.dispose();
			}
		});
		
		etiquetabuscador = new JLabel("Buscador:"); // etiqueta del buscador
		etiquetabuscador.setBounds(700, 127, 180, 40);
		etiquetabuscador.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(etiquetabuscador);
		
		txtbuscador = new JTextField(); // Campo del buscador
		txtbuscador.setBorder(new LineBorder(Color.BLACK));
		txtbuscador.setBounds(840, 130, 210, 40);
		txtbuscador.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(txtbuscador);

		txtbuscador.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblListaCompra.getModel());
				tblListaCompra.setRowSorter(trsfiltro);
			}
		});

		txtbuscador.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (txtbuscador.getText()).toUpperCase();
				txtbuscador.setText(cadena);
				repaint();
				filtroNombre(); // llama al método del buscador
			}
		});
		
		botonmostrartodo = new JButton(); // crea botón salir
		botonmostrartodo.setBorder(new LineBorder(Color.BLACK));
		botonmostrartodo.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-mostrar-fotogramas-32.png"));
		botonmostrartodo.setBounds(1065, 130, 110, 40);
		add(botonmostrartodo);
		
		botonmostrartodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					txtbuscador.requestFocus();
					txtbuscador.setText("");
					
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ENTER); // Simula presionar la tecla
					robot.keyRelease(KeyEvent.VK_ENTER); // Simula soltar la tecla
				}catch(Exception e) {
					e.printStackTrace();
				}
			}			
		});
		
		etiquetaagregar = new JLabel("Ingreso de artículos en la lista"); // etiqueta del buscador
		etiquetaagregar.setBounds(795, 190, 300, 40);
		etiquetaagregar.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(etiquetaagregar);
		
		txtagregar = new JTextField(); // Campo del buscador
		txtagregar.setBorder(new LineBorder(Color.BLACK));
		txtagregar.setBounds(700, 230, 350, 40);
		txtagregar.setFont(new java.awt.Font(null, Font.PLAIN, 40));
		add(txtagregar);

		txtagregar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					botonagregar.requestFocus();
				}
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {				
			}
		});

		botonagregar = new JButton(); // crea botón salir
		botonagregar.setBorder(new LineBorder(Color.BLACK));
		botonagregar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-editar-fila-30.png"));
		botonagregar.setBounds(1065, 230, 110, 40);
		add(botonagregar);

		botonagregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				if(txtagregar.getText().equals("") && combocategoria.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Debe rellenar los campos de ingreso de artículos y su categoría");
				}else if (!txtagregar.getText().equals("") && combocategoria.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Debe insertar la categoría");
				} else if (txtagregar.getText().equals("") && combocategoria.getSelectedItem() != null) {
					JOptionPane.showMessageDialog(null, "Debe insertar el nombre del producto");
				} else {
					MetodosLista.IngresoListaManual(modeloListaCompra, comboseleccion, txtagregar, combocategoria);
					txtagregar.setText("");
					combocategoria.setSelectedItem(null);	
				}				
			}
		});
		
		botonagregar.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
					
					if (!txtagregar.getText().equals("") && combocategoria.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Debe insertar la categoría");
					} else if (txtagregar.getText().equals("") && combocategoria.getSelectedItem() != null) {
						JOptionPane.showMessageDialog(null, "Debe insertar el nombre del producto");
					} else {
						MetodosLista.IngresoListaManual(modeloListaCompra, comboseleccion, txtagregar, combocategoria);
						txtagregar.setText("");
						combocategoria.setSelectedItem(null);	
					}			
			}
		});

		etiquetamarcar = new JLabel("No borre productos si usa la opción ordenar");
		etiquetamarcar.setBounds(750, 300, 450, 25);
		etiquetamarcar.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		add(etiquetamarcar);

		botonmarcacheckbox = new JButton();
		botonmarcacheckbox.setBorder(new LineBorder(Color.BLACK));
		botonmarcacheckbox
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-double-tick-50.png"));
		botonmarcacheckbox.setBounds(705, 330, 220, 70);
		add(botonmarcacheckbox);

		botonmarcacheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < tblListaCompra.getRowCount(); i++) {
					if (tblListaCompra.getValueAt(i, 3) != "false") {
						tblListaCompra.setValueAt(true, i, 3);
					}
				}
			}
		});		

		botondesmarcacheckbox = new JButton();
		botondesmarcacheckbox.setBorder(new LineBorder(Color.BLACK));
		botondesmarcacheckbox.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-delete-50.png"));
		botondesmarcacheckbox.setBounds(950, 330, 220, 70);
		add(botondesmarcacheckbox);

		botondesmarcacheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < tblListaCompra.getRowCount(); i++) {
					if (tblListaCompra.getValueAt(i, 3) != "true") {
						tblListaCompra.setValueAt(false, i, 3);
					}
				}
			}
		});

		etiquetaborrar = new JLabel("Seleccione los productos a borrar"); // etiqueta para avisar como borrar los
																			// productos
		etiquetaborrar.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		etiquetaborrar.setBounds(790, 430, 450, 25);
		add(etiquetaborrar);

		borrafila = new JButton(); // crea el botón para borrar una fila
		borrafila.setBorder(new LineBorder(Color.BLACK));
		borrafila.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-borrar-80.png"));
		borrafila.setBounds(705, 460, 220, 70);
		add(borrafila);

		borrafila.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				int eleccion = JOptionPane.showConfirmDialog(null,
						"Seguro que quieres borrar las filas seleccionadas ?", "Confirmar", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == eleccion) {
					EliminarFilaCompra(ev, modeloListaCompra, tblListaCompra, comboseleccion); // llama al método para borrar fila de MetodosLista
					MostrarListaCompra(modeloListaCompra, tblListaCompra, comboseleccion); // llama al método para mostrar la lista que está en esta clase
				}
			}
		});

		borralista = new JButton(); // vrea el botón para borrar la lista completa
		borralista.setBorder(new LineBorder(Color.BLACK));
		borralista.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-eliminar-64.png"));
		borralista.setBounds(950, 460, 220, 70);
		add(borralista);

		borralista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MetodosLista.BorraLista(modeloListaCompra, tblListaCompra, comboseleccion); // llama al método para borrar fila de MetodosLista
			}
		});
		
		botonpasarlista = new JButton("");
		botonpasarlista.setBorder(new LineBorder(Color.BLACK));
		botonpasarlista
				.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-data-transfer-50.png"));
		botonpasarlista.setBounds(705, 590, 220, 70);
		add(botonpasarlista);

		botonpasarlista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int seleccion = JOptionPane.showOptionDialog(null, // null para componente padre nulo
						"Selecciona el almacen", "Selector de Distribuidores", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, // null para icono por defecto.
						new Object[] { "Ayala", "Barea", "SolaRica", "Otros", "Cancelar"}, // null para YES, NO y CANCEL
						"opcion 1");
				String cadenaseleccion = String.valueOf(seleccion);
				// System.out.println(cadenaseleccion);
				String almacen;
				if (cadenaseleccion.equals("0")) {
					almacen = "Ayala";
					CompruebaPasarProductosAOtraLista(almacen);
				} else if (cadenaseleccion.equals("1")) {
					almacen = "Barea";
					CompruebaPasarProductosAOtraLista(almacen);
				} else if (cadenaseleccion.equals("2")) {
					almacen = "SolaRica";
					CompruebaPasarProductosAOtraLista(almacen);
				} else if (cadenaseleccion.equals("3")) {
					almacen = "Otros";
					CompruebaPasarProductosAOtraLista(almacen);
				}
			}
		});
		
		etiquetaimprimir = new JLabel("Se imprimiran los productos seleccionados"); // etiqueta para avisar que productos se imprimiran
		etiquetaimprimir.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		etiquetaimprimir.setBounds(750, 560, 460, 25);
		add(etiquetaimprimir);

		botonimprimir = new JButton();
		botonimprimir.setBorder(new LineBorder(Color.BLACK));
		botonimprimir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-imprimir-52.png"));
		botonimprimir.setBounds(950, 590, 220, 70);
		add(botonimprimir);

		botonimprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int guardalista = JOptionPane.showConfirmDialog(null,
						"Quieres imprimir la lista de " + comboseleccion.getSelectedItem().toString() + " ?",
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == guardalista) {

					GuardaListaCompra(tblListaCompra, comboseleccion);
					Imprimir.ImprimirListaCompra();
				}
			}
		});
		
		modeloListaCompra.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
					
				CambiaCeldaListaCompra(modeloListaCompra, tblListaCompra, e);
			}			
		});		
		
		MostrarListaCompra(modeloListaCompra, tblListaCompra, comboseleccion);
		
	}

	// método que muestra las listas de compras
	public static void MostrarListaCompra(DefaultTableModel modeloListaCompra, JTable tblListaCompra,
			JComboBox<String> comboseleccion) {

		try {			
			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);		

			String proveselec = comboseleccion.getSelectedItem().toString();

			con = Conexion.ejecutarConexion();
			Object dts[] = new Object[4];
			Statement st = con.createStatement();

			int contador = tblListaCompra.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloListaCompra.removeRow(i);
			}

			String sql = "SELECT ArticuloLista, Precio" + proveselec + "Lista, CategoriaLista, Booleano FROM ListaCompra" + proveselec;

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = formato.format(rs.getFloat(2));
				dts[2] = rs.getString(3);
				dts[3] = rs.getObject(4, Boolean.class);

				modeloListaCompra.addRow(dts);
			}			
			rs.close();
			st.close();
			
			tblListaCompra.setModel(modeloListaCompra);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CambiaCeldaListaCompra(DefaultTableModel modeloListaCompra, JTable tblListaCompra, TableModelEvent e) {
		
		if (e.getType() == TableModelEvent.UPDATE) {
			
			String columna = null;
			String seleccion = comboseleccion.getSelectedItem().toString();
			int n = 0;

			if (e.getColumn() == 0) { // asignamos un indice para poder usar SQL más facilmente
				columna = "ArticuloLista";
			} else if (e.getColumn() == 1) {
				columna = "Precio" + seleccion + "Lista";
				n = 1;
			} else if (e.getColumn() == 2) {
				columna = "CategoriaLista";
				n = 2;
			} else if (e.getColumn() == 3) {
				columna = "Booleano";
				n = 3;
			}
				
			try {
				
				if(n == 0) {
					String sql0 = "UPDATE ListaCompra" + seleccion + " SET " + columna + " = '"
						+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn())
						+ "' WHERE Precio" + seleccion + "Lista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 1) + "' "
						+ "AND CategoriaLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 2) + "'";
					//System.out.println("UPDATE ListaCompra" + seleccion + " SET " + columna + " = '" 
					//	+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn()) + "' WHERE Precio" + seleccion + "Lista = '" +
					//	modeloListaCompra.getValueAt(e.getFirstRow(), 1) + "' AND CategoriaLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 2) + "'");
					con = Conexion.ejecutarConexion();
					PreparedStatement pst = con.prepareStatement(sql0);
					pst.executeUpdate();
					pst.close();
				
				}else if (n > 0 && n <= 2) {
					String sql1 = "UPDATE ListaCompra" + seleccion + " SET " + columna + " = '"
						+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn())
						+ "' WHERE ArticuloLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 0) + "'";
					//System.out.println("UPDATE tblLista" + seleccion + " SET " + columna + " = '"
					//	+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn())
					//	+ "' WHERE ArticuloLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 0) + "'");
					con = Conexion.ejecutarConexion();
					PreparedStatement pst = con.prepareStatement(sql1);
					pst.executeUpdate();
					pst.close();
				}else if(n == 3) {
					String sql2 = "UPDATE ListaCompra" + seleccion + " SET " + columna + " = '"
						+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn())
						+ "' WHERE ArticuloLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 0) + "'";
					//System.out.println("UPDATE tblLista" + seleccion + " SET " + columna + " = '"
					//	+ modeloListaCompra.getValueAt(e.getFirstRow(), e.getColumn())
					//	+ "' WHERE ArticuloLista = '" + modeloListaCompra.getValueAt(e.getFirstRow(), 0) + "'");
					con = Conexion.ejecutarConexion();
					PreparedStatement pst = con.prepareStatement(sql2);
					pst.executeUpdate();
					pst.close();
				}
			}catch(Exception evt) {
				evt.printStackTrace();							
			}							
		}
	}

	// método para pasar de una lista a otra
	public void PasarProductosAOtraLista(String distribuidor, int i) {

		con = Conexion.ejecutarConexion();

		try {
			String sql = "INSERT INTO ListaCompra" + distribuidor + "(ArticuloLista, Precio" + distribuidor
					+ "Lista, CategoriaLista) VALUES (?,?,?)";
			//System.out.println("INSERT INTO ListaCompra" + distribuidor + "(ArticuloLista, Precio" + distribuidor
			//		+ "Lista, CategoriaLista) VALUES (?,?,?)");

			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, tblListaCompra.getValueAt(i, 0).toString());

			if (tblListaCompra.getValueAt(i, 1) == null) {
				pst.setString(2, null);
			} else {
				pst.setString(2, tblListaCompra.getValueAt(i, 1).toString());
			}

			if (tblListaCompra.getValueAt(i, 2) == null) {
				pst.setString(3, null);
			} else {
				pst.setString(3, tblListaCompra.getValueAt(i, 2).toString());
			}

			pst.executeUpdate();
			pst.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un fallo, y no se han guardado los datos");
			e.printStackTrace();
		}
	}

	// método para comprobar que el articulo no esté repetido al pasar de una lista a otra
	public void CompruebaPasarProductosAOtraLista(String distribuidor) {

		try {
			con = Conexion.ejecutarConexion();

			for (int i = 0; i < tblListaCompra.getRowCount(); i++) {
				
				if (tblListaCompra.getValueAt(i, 3).toString() == "true") {
					
					String sql = "SELECT ArticuloLista FROM ListaCompra" + distribuidor + " WHERE ArticuloLista = '"
							+ tblListaCompra.getValueAt(i, 0) + "'";
					//System.out.println("SELECT ArticuloLista FROM ListaCompra" + distribuidor
					//		+ " WHERE ArticuloLista = '" + tblListaCompra.getValueAt(i, 0) + "'");

					PreparedStatement pst = con.prepareStatement(sql);
					ResultSet res = pst.executeQuery();
					
					String pasandoproducto = tblListaCompra.getValueAt(i, 0).toString();

					if (res.next()) {
						JOptionPane.showMessageDialog(null, "El artículo " + pasandoproducto.trim() + " ya está en la lista de " + distribuidor);
					} else {
						PasarProductosAOtraLista(distribuidor, i);
					}
					pst.close();
					res.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// método para eliminar la fila seleccionada
	public static void EliminarFilaCompra(ActionEvent ev, DefaultTableModel modeloListaCompra, JTable tblListaCompra,
			JComboBox<String> comboseleccion) {

		try {
			con = Conexion.ejecutarConexion();
			String prove = comboseleccion.getSelectedItem().toString();

			for (int i = 0; i < tblListaCompra.getRowCount(); i++) {

				String producto = modeloListaCompra.getValueAt(i, 0).toString();
				String sql = "DELETE FROM ListaCompra" + prove + " WHERE ArticuloLista = '" + producto + "' AND Booleano = true";
				//System.out.println("DELETE FROM ListaCompra" + prove + " WHERE ArticuloLista = '" + producto + "' AND Booleano = true");

				PreparedStatement pst = con.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe de seleccionar un producto");
		}
	}

	// método para buscar por nombre del artículo
	public void filtroNombre() {
		filtro = txtbuscador.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscador.getText(), 0));
	}

	// dibuja rectangulos
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(680, 25, 515, 80);
		g.drawRect(680, 115, 515, 70);
		g.drawRect(680, 195, 515, 90);
		g.drawRect(680, 295, 515, 120);
		g.drawRect(680, 425, 515, 120);
		g.drawRect(680, 555, 515, 120);
	}

	// método para guardar en formato txt la tabla de compra tblLista
	public static void GuardaListaCompra(JTable tblListaCompra, JComboBox<?> comboseleccion) {
		try {

			String Lista = "C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\lista_compra.txt";
			BufferedWriter bfw = new BufferedWriter(new FileWriter(Lista));

			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("       Listado de compra en " + comboseleccion.getSelectedItem().toString());
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();

			for (int i = 0; i < tblListaCompra.getRowCount(); i++) { // realiza un barrido por filas.
				for (int j = 0; j < tblListaCompra.getColumnCount() - 1; j++) { // realiza un barrido por columnas.
					
					if (tblListaCompra.getValueAt(i, 3).toString() == "true") {
						System.out.println(tblListaCompra.getValueAt(i, 3));
						
						if (tblListaCompra.getValueAt(i, j) != null) {
							if (j == 0) {
								String temporal = tblListaCompra.getValueAt(i, j).toString();
								//System.out.println(tblListaCompra.getValueAt(i, j).toString());
								String tempo = temporal + "                                         ";
								String temp = tempo.substring(0, 38);
								//System.out.println(temp);
								bfw.write(temp);
							} else {
								String temporal = tblListaCompra.getValueAt(i, j).toString();
								//System.out.println(tblListaCompra.getValueAt(i, j).toString());
								String tempo = temporal + "                                         ";
								String temp = tempo.substring(0, 8);
								bfw.write(temp);
								//System.out.println(temp);
							}
						} else {
							String tempo = "VACIO" + "                                    ";
							String temp = tempo.substring(0, 10);
							bfw.write(temp);
						}
						bfw.write("\t"); // inserta un tabulado
					}
				}
				if (tblListaCompra.getValueAt(i, 3).toString() == "true") {
					bfw.newLine(); // inserta nueva linea.
				}
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
