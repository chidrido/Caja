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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

import Caja.panelArticulos.EscuchaMouse;

public class ArticulosWeb extends JFrame {

	public ArticulosWeb() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);

		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		PanelArticulosWeb panelarticulosweb = new PanelArticulosWeb();

		add(panelarticulosweb);
	}
}

class PanelArticulosWeb extends JPanel {

	String[] titulos = { "Cod.Barras", "ArticulosWeb", "PVP", "Stock", "Categoria", "Borrar", "" };
	DefaultTableModel modeloArticulosWeb;
	JTable tblArticulosWeb;
	JScrollPane jscrollArticulosWeb;
	
	JTextField txtbuscacodigo;
	JTextField txtbuscanombre;

	JButton botonsalir;
	JButton botonbasededatos;
	JButton botonconvertir;
	JButton botonlimpiar;
	JButton botoncambiacategoria;
	JButton botonmostrartodo;

	JLabel etiquetatitulo;
	JLabel etiquetabuscacodigo;
	JLabel etiquetabuscanombre;
	
	JComboBox<String> comboseleccion;
	
	private TableRowSorter<TableModel> trsfiltro;
	String filtro;

	Connection con = null;
	
	MetodosArticulosWeb metodosarticulosweb = new MetodosArticulosWeb();

	public PanelArticulosWeb() {

		modeloArticulosWeb = new DefaultTableModel(null, titulos);
		tblArticulosWeb = new JTable(modeloArticulosWeb);
		jscrollArticulosWeb = new JScrollPane(tblArticulosWeb);

		jscrollArticulosWeb.setBounds(300, 110, 910, 580);
		jscrollArticulosWeb.setBorder(new LineBorder(Color.BLACK));
		add(jscrollArticulosWeb);
		
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(0)).setMaxWidth(150);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(1)).setMaxWidth(370);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(2)).setMaxWidth(80);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(3)).setMaxWidth(80);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(4)).setMaxWidth(123);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(5)).setMaxWidth(80);
		tblArticulosWeb.getColumn(tblArticulosWeb.getModel().getColumnName(6)).setMaxWidth(30);
		tblArticulosWeb.setFont(new java.awt.Font("Tahoma", 0, 18));
		tblArticulosWeb.setRowHeight(30);
		
		tblArticulosWeb.setEnabled(false);				// la tabla comienza deshabilitada, la podemos habilitar con el boton cambiacatregoria

		TableColumn agregarColumna;
		agregarColumna = tblArticulosWeb.getColumnModel().getColumn(5); // llama a los metodos para mostrar el botón eliminar
		agregarColumna.setCellEditor(new EditorCeldasCaja(tblArticulosWeb));
		agregarColumna.setCellRenderer(new RenderizadoCaja(true));

		TableColumn agregarColumna1;
		agregarColumna1 = tblArticulosWeb.getColumnModel().getColumn(6); // llama a los metodos para mostrar el chekbox
		agregarColumna1.setCellEditor(tblArticulosWeb.getDefaultEditor(Boolean.class));
		agregarColumna1.setCellRenderer(tblArticulosWeb.getDefaultRenderer(Boolean.class));

		CambioColorArticulosWeb cambiocolor = new CambioColorArticulosWeb();
		tblArticulosWeb.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gris oscuro
		tblArticulosWeb.setGridColor(Color.darkGray);

		tblArticulosWeb.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblArticulosWeb.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 18);
		th.setFont(fuente);

		setLayout(null);
		setBackground(Color.CYAN);
		
		etiquetatitulo = new JLabel("Artículos Web");
		etiquetatitulo.setFont(new Font(null, Font.PLAIN, 55));
		etiquetatitulo.setBounds(680, 25, 400, 60);
		add(etiquetatitulo);

		etiquetabuscacodigo = new JLabel("Busca código   :");
		etiquetabuscacodigo.setFont(new Font(null, Font.PLAIN, 30));
		etiquetabuscacodigo.setBounds(20, 15, 250, 35);
		add(etiquetabuscacodigo);
		
		txtbuscacodigo = new JTextField();
		txtbuscacodigo.setBorder(new LineBorder(Color.BLACK));
		txtbuscacodigo.setFont(new Font("Tahoma", 1, 20));
		txtbuscacodigo.setBounds(240, 20, 250, 30);
		add(txtbuscacodigo);
		
		txtbuscacodigo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {				
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblArticulosWeb.getModel());
				tblArticulosWeb.setRowSorter(trsfiltro);
			}
		});

		txtbuscacodigo.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (txtbuscacodigo.getText()).toUpperCase();
				txtbuscacodigo.setText(cadena);
				repaint();
				filtroCodigo(); // llama al método del buscador
			}
		});
		
		etiquetabuscanombre= new JLabel("Busca nombre :");
		etiquetabuscanombre.setBounds(20, 55, 250, 30);
		etiquetabuscanombre.setFont(new Font(null, Font.PLAIN, 30));
		add(etiquetabuscanombre);
		
		txtbuscanombre = new JTextField();
		txtbuscanombre.setBorder(new LineBorder(Color.BLACK));
		txtbuscanombre.setBounds(240, 60, 250, 30);
		txtbuscanombre.setFont(new Font("Tahoma", 1, 30));
		add(txtbuscanombre);

		txtbuscanombre.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblArticulosWeb.getModel());
				tblArticulosWeb.setRowSorter(trsfiltro);
			}
		});

		txtbuscanombre.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (txtbuscanombre.getText()).toUpperCase();
				txtbuscanombre.setText(cadena);
				repaint();
				filtroNombre();
			}
		});
		
		String opciones[] = new String[] { "MOSTRAR TODO", "ACTUALIZADOS", "Alimentacion", "Drogueria", "Camara", "Fruta" };
		comboseleccion = new JComboBox<>();
		comboseleccion.setBorder(new LineBorder(Color.BLACK));
		comboseleccion.setBounds(20, 120, 260, 60);
		comboseleccion.setFont(new Font("Tahoma", 1, 28));
		comboseleccion.setModel(new DefaultComboBoxModel<>(opciones));
		add(comboseleccion);
		
		comboseleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(comboseleccion.getSelectedItem().toString() == "MOSTRAR TODO") {
					modeloArticulosWeb.setRowCount(0);
					metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
				}else if(comboseleccion.getSelectedItem().toString() == "ACTUALIZADOS") {
					modeloArticulosWeb.setRowCount(0);
					metodosarticulosweb.MostrarActualizados(modeloArticulosWeb, tblArticulosWeb);					
				}else {
					modeloArticulosWeb.setRowCount(0);
					metodosarticulosweb.MostrarPorCategoriasWeb(modeloArticulosWeb, tblArticulosWeb, comboseleccion);
				}
				
			}			
		});
		
		botonlimpiar = new JButton();
		botonlimpiar.setBorder(new LineBorder(Color.BLACK));
		botonlimpiar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-delete-50.png"));
		botonlimpiar.setBounds(155, 190, 125, 60);
		add(botonlimpiar);

		botonlimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				metodosarticulosweb.LimpiarCheckBox(modeloArticulosWeb, tblArticulosWeb);
				
				modeloArticulosWeb.setRowCount(0);
				metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
			}
		});
		
		botonconvertir = new JButton();
		botonconvertir.setBorder(new LineBorder(Color.BLACK));
		botonconvertir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-export-csv-50.png"));
		botonconvertir.setBounds(20, 190, 125, 60);
		add(botonconvertir);

		botonconvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int eleccion = JOptionPane.showConfirmDialog(null, "Convertir a CSV?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == eleccion) {
					MetodosArticulosWeb.ExportarJTable(modeloArticulosWeb, tblArticulosWeb);
				}
			}
		});
		
		botoncambiacategoria = new JButton();
		botoncambiacategoria.setBorder(new LineBorder(Color.BLACK));
		botoncambiacategoria.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-editar-propiedad-50.png"));
		botoncambiacategoria.setBounds(20, 260, 125, 60);
		add(botoncambiacategoria);

		botoncambiacategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				botoncambiacategoria.setEnabled(false);
				tblArticulosWeb.setEnabled(true);
			}
		});
		
		botonmostrartodo = new JButton();
		botonmostrartodo.setBorder(new LineBorder(Color.BLACK));
		botonmostrartodo.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-showing-video-frames-50.png"));
		botonmostrartodo.setBounds(155, 260, 125, 60);
		add(botonmostrartodo);

		botonmostrartodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbuscacodigo.setText("");
				txtbuscanombre.setText("");
				
				botoncambiacategoria.setEnabled(true);
				tblArticulosWeb.setEnabled(false);
				
				txtbuscacodigo.requestFocus();
				try {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ENTER); // Simula presionar la tecla
					robot.keyRelease(KeyEvent.VK_ENTER); // Simula soltar la tecla
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		botonbasededatos = new JButton();
		botonbasededatos.setBorder(new LineBorder(Color.BLACK));
		botonbasededatos.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-base-de-datos-50.png"));
		botonbasededatos.setBounds(20, 620, 125, 60);
		add(botonbasededatos);

		botonbasededatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Articulos articulos = new Articulos();
				articulos.setVisible(true);

				Window w = SwingUtilities.getWindowAncestor(PanelArticulosWeb.this);
				w.dispose();
			}
		});

		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(
				new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-48.png"));
		botonsalir.setBounds(155, 620, 125, 60);
		add(botonsalir);

		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Window w = SwingUtilities.getWindowAncestor(PanelArticulosWeb.this);
				w.dispose();
			}
		});

		modeloArticulosWeb.addTableModelListener(new TableModelListener() { // crea evento al pulsar sobre las casillas
																			// de JTable cajero
			public void tableChanged(TableModelEvent e) {
				EliminaCeldaWeb(modeloArticulosWeb, tblArticulosWeb, e); // llama a método que activa las celdas
			}
		});
		
		tblArticulosWeb.addMouseListener(new EscuchaMouse());
		metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
	}
	
	class EscuchaMouse implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			
			String Codigomodifica;
			int seleccionfila = tblArticulosWeb.rowAtPoint(e.getPoint());
			int seleccioncolumna = tblArticulosWeb.columnAtPoint(e.getPoint());
			
			if (!botoncambiacategoria.isEnabled()) {
				Codigomodifica = tblArticulosWeb.getValueAt(seleccionfila, 0).toString();
				
				if(seleccioncolumna == 4) {
				
					try {
						int seleccion = JOptionPane.showOptionDialog(null, "Selecciona la categoría de la página Web", "Selector de Distribuidores",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] 
										{ "Alimentación", "Droguería", "Cámara", "Bebida", "Fruteria", "Cancelar" }, // null para YES, NO y CANCEL
								"opcion 1");
						String cadenaseleccion = String.valueOf(seleccion);

						if (cadenaseleccion.equals("0")) {
							String sql0 = "UPDATE tblProductos SET CategoriaWeb = 'Alimentacion' WHERE Codigo = '" + Codigomodifica + "'";
							//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Alimentación' WHERE Codigo = '" + Codigomodifica + "'");

							con = Conexion.ejecutarConexion();
							PreparedStatement pst0 = con.prepareStatement(sql0);
							pst0.executeUpdate();
							pst0.close();
							modeloArticulosWeb.setRowCount(0);
							metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
						} else if (cadenaseleccion.equals("1")) {
							String sql1 = "UPDATE tblProductos SET CategoriaWeb = 'Drogueria' WHERE Codigo = '" + Codigomodifica + "'";
							//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Droguería' WHERE Codigo = '" + Codigomodifica + "'");
							modeloArticulosWeb.setRowCount(0);
							con = Conexion.ejecutarConexion();
							PreparedStatement pst1 = con.prepareStatement(sql1);
							pst1.executeUpdate();
							pst1.close();
							metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
						} else if (cadenaseleccion.equals("2")) {
							String sql2 = "UPDATE tblProductos SET CategoriaWeb = 'Camara' WHERE Codigo = '" + Codigomodifica + "'";
							//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Camara' WHERE Codigo = '" + Codigomodifica + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst2 = con.prepareStatement(sql2);
							pst2.executeUpdate();
							pst2.close();
							modeloArticulosWeb.setRowCount(0);
							metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
						} else if (cadenaseleccion.equals("3")) {
							String sql3 = "UPDATE tblProductos SET CategoriaWeb = 'Bebida' WHERE Codigo = '" + Codigomodifica + "'";
							//System.out.println("UPDATE tblProductos SET CategoriaWeb = 'Fruta' WHERE Codigo = '" + Codigomodifica + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst3 = con.prepareStatement(sql3);
							pst3.executeUpdate();
							pst3.close();
							modeloArticulosWeb.setRowCount(0);
							metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
						}else if (cadenaseleccion.equals("4")) {
							String sql4 = "UPDATE tblProductos SET CategoriaWeb = 'Fruteria' WHERE Codigo = '" + Codigomodifica + "'";
							//System.out.println("UPDATE tblProductos SET CategoriaWeb = Bebida WHERE Codigo = '" + Codigomodifica + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst4 = con.prepareStatement(sql4);
							pst4.executeUpdate();
							pst4.close();
							modeloArticulosWeb.setRowCount(0);
							metodosarticulosweb.Mostrar(modeloArticulosWeb, tblArticulosWeb);
						}				
					
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}
		public void mouseEntered(MouseEvent e) {			
		}
		public void mouseExited(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
	}


	// método para eliminar la fila seleccionada
	public void EliminaCeldaWeb(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb, TableModelEvent e) {

		if (e.getType() == TableModelEvent.UPDATE) {

			try {
				if (e.getColumn() == 5) { // al pulsar el JButton renderizado borra la fila selecionada
					int eleccion = JOptionPane.showConfirmDialog(null,
							"Quiere quitar el producto de la página web?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (JOptionPane.OK_OPTION == eleccion) {
						String sql10 = "UPDATE tblProductos SET Booleano = False WHERE Codigo = '"
								+ modeloArticulosWeb.getValueAt(e.getFirstRow(), 0) + "'";
						System.out.println("UPDATE tblProductos SET Booleano = False WHERE Codigo = '"
								+ modeloArticulosWeb.getValueAt(e.getFirstRow(), 0) + "'");
						con = Conexion.ejecutarConexion();
						PreparedStatement pst = con.prepareStatement(sql10);
						pst.executeUpdate();

						int fila = e.getFirstRow();
						modeloArticulosWeb.removeRow(fila);

						pst.close();
					}

				}
			} catch (Exception evt) {
				JOptionPane.showMessageDialog(null, "Hay ocurrido un ERROR!");
				evt.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(10, 10, 490, 90);
		g.drawRect(510, 10, 700, 90);
		g.drawRect(10, 110, 280, 580);
	}
	
	public void filtroCodigo() {
		filtro = txtbuscacodigo.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscacodigo.getText(), 0));
	}
	
	// método para buscar por nombre del artículo
	public void filtroNombre() {
		filtro = txtbuscanombre.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscanombre.getText(), 1));
	}

}