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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Buscador extends JFrame{	

	public Buscador(JTextField textocodigo, JTextField textonombreproducto, JTextField textoprecio, JTextField textostock, JTextField textoporcentaje, 
			JTextField textopreciototal, JComboBox<?> combocategoria, JComboBox<?> combodistribuidor, String codigodebarras) {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);
		
		setLocationRelativeTo(null);

		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		PanelBuscador panelbuscador = new PanelBuscador(textocodigo, textonombreproducto, textoprecio, textostock, textoporcentaje,
				textopreciototal, combocategoria, combodistribuidor, codigodebarras);

		add(panelbuscador);
	}
}

class PanelBuscador extends JPanel{
	
	static Connection con = null;
	
	JLabel buscanombre;
	JLabel buscacodigo;
	
	JTextField textbuscanombre;
	JTextField textbuscacodigo;
	
	JButton botonborrar;
	JButton botonsalir;
	
	String filtro;
	private TableRowSorter<TableModel> trsfiltro;
	
	String[] titulos = { "Cod.Barras", "Articulo", "Precio", "Stock", "%", "PVP","Cat"};
	DefaultTableModel modeloBuscador;
	JTable tblBuscador;
	JScrollPane jscrollBuscador;
	
	public PanelBuscador(JTextField textocodigo, JTextField textonombreproducto, JTextField textoprecio, JTextField textostock, JTextField textoporcentaje, 
			JTextField textopreciototal, JComboBox<?> combocategoria, JComboBox<?> combodistribuidor, String codigodebarras) {
		
		modeloBuscador = new DefaultTableModel(null, titulos);
		tblBuscador = new JTable(modeloBuscador);
		jscrollBuscador = new JScrollPane(tblBuscador);
		jscrollBuscador.setBounds(30, 125, 1165, 550);
		jscrollBuscador.setBorder(new LineBorder(Color.BLACK));
		add(jscrollBuscador);
		
		setBackground(Color.cyan);
		
		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblBuscador.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);
		
		tblBuscador.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas
		
		CambioColor cambiocolor = new CambioColor();
		tblBuscador.setDefaultRenderer(Object.class, cambiocolor); // muestra color en la celda señalada
		
		setLayout(null);
		
		buscanombre = new JLabel("Busca Producto : ");
		buscanombre.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		buscanombre.setBounds(30, 20, 200, 30);
		add(buscanombre);
		
		textbuscanombre = new JTextField();
		textbuscanombre.setBorder(new LineBorder(Color.BLACK));
		textbuscanombre.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		textbuscanombre.setBounds(230, 25, 400, 30);
		add(textbuscanombre);
		
		textbuscanombre.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblBuscador.getModel());
				tblBuscador.setRowSorter(trsfiltro);
			}
		});

		textbuscanombre.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (textbuscanombre.getText()).toUpperCase();
				textbuscanombre.setText(cadena);
				repaint();
				filtroNombre();
			}
		});
		
		buscacodigo = new JLabel("Busca Codigo    : ");
		buscacodigo.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		buscacodigo.setBounds(30, 70, 200, 30);
		add(buscacodigo);
		
		textbuscacodigo = new JTextField();
		textbuscacodigo.setBorder(new LineBorder(Color.BLACK));
		textbuscacodigo.setFont(new Font("Tahoma", 1, 25));
		textbuscacodigo.setBounds(230, 75, 400, 30);
		add(textbuscacodigo);
		
		textbuscacodigo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblBuscador.getModel());
				tblBuscador.setRowSorter(trsfiltro);
			}
		});
		
		textbuscacodigo.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (textbuscacodigo.getText()).toUpperCase();
				textbuscacodigo.setText(cadena);
				repaint();
				filtroCodigo();
			}
		});
		
		botonborrar = new JButton();
		botonborrar.setBorder(new LineBorder(Color.BLACK));
		botonborrar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-mostrar-fotogramas-32.png"));
		botonborrar.setBounds(640, 25, 120, 30);
		add(botonborrar);
		
		botonborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					textbuscanombre.setText("");
					textbuscacodigo.setText("");
				
					textbuscanombre.requestFocus();
					
					Robot robot = new Robot();					
					
					robot.keyPress(KeyEvent.VK_ENTER); // Simula presionar la tecla
					robot.keyRelease(KeyEvent.VK_ENTER); // Simula soltar la tecla
					
				} catch (AWTException e1) {
					throw new RuntimeException("Error");
				}
			}					
		});
		
		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		botonsalir.setBounds(640, 75, 120, 30);
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Window w = SwingUtilities.getWindowAncestor(PanelBuscador.this);
				w.dispose();
			}			
		});
		
		MostrarBaseDeDatosEnBuscador(modeloBuscador, tblBuscador, combodistribuidor, codigodebarras);
		tblBuscador.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				int seleccionfila = tblBuscador.rowAtPoint(e.getPoint());
				
				String codigo, nombre, precio, stock, porcentaje, PVP, categoria;
				
				if(tblBuscador.getValueAt(seleccionfila, 0) == null) {
					codigo = "";
				}else {
					codigo = tblBuscador.getValueAt(seleccionfila, 0).toString();	
					textocodigo.setText(codigo);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 1).toString() == null) {
					nombre = "";
				}else {
					nombre = tblBuscador.getValueAt(seleccionfila, 1).toString();	
					textonombreproducto.setText(nombre);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 2).toString() == null) {
					precio = "";
				}else {
					precio = tblBuscador.getValueAt(seleccionfila, 2).toString();
					textoprecio.setText(precio);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 3) == null) {
					stock = "";
				}else {
					stock = tblBuscador.getValueAt(seleccionfila, 3).toString();
					textostock.setText(stock);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 4) == null) {
					porcentaje = "";
				}else {
					porcentaje = tblBuscador.getValueAt(seleccionfila, 4).toString();
					textoporcentaje.setText(porcentaje);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 5) == null) {
					PVP = ""; 
				}else {
					PVP = tblBuscador.getValueAt(seleccionfila, 5).toString();	
					textopreciototal.setText(PVP);
				}
				
				if(tblBuscador.getValueAt(seleccionfila, 6) == null) {
					categoria = "";
				}else {
					categoria = tblBuscador.getValueAt(seleccionfila, 6).toString();
					combocategoria.setSelectedItem(categoria);
				}
				
				Window w = SwingUtilities.getWindowAncestor(PanelBuscador.this);
				w.dispose();
				
				ActualizaNombre(codigodebarras, textonombreproducto);
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
	}
	
	//método que actualiza solo el código de barras 
	public void ActualizaNombre(String codigodebarras, JTextField textonombreproducto) {
		
		int confirmar = JOptionPane.showConfirmDialog(null,"Quiere actualizar el codigo de barras? ", "Confirmar", JOptionPane.YES_NO_OPTION);
		
		if (JOptionPane.OK_OPTION == confirmar) {
			
			try {
				String textonombremod = textonombreproducto.getText();
			
				con = Conexion.ejecutarConexion();
			
				String sql = "UPDATE tblProductos SET Codigo = '" + codigodebarras + "' WHERE Articulos = '" + textonombremod + "'";
				
				PreparedStatement pst;
			
				pst = con.prepareStatement(sql);
				pst.executeUpdate();
				pst.close();
				
				JOptionPane.showMessageDialog(null, "Debe actualizar el producto");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}
	
	//método para mostrar los datos de tblProductos en la JTable
	public void MostrarBaseDeDatosEnBuscador(DefaultTableModel modeloBuscador, JTable tblBuscador, JComboBox<?> combodistribuidor, String codigodebarras) {

		try {
			con = Conexion.ejecutarConexion();
			
			int contador = tblBuscador.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloBuscador.removeRow(i);
			}

			Object dts[] = new Object[7];
			String sql = "SELECT * FROM tblProductos";
			//System.out.println("SELECT * FROM tblFacturas");
			Statement st = con.createStatement();

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);
				if(combodistribuidor.getSelectedItem().toString() == "Ayala") {
					dts[2] = formato.format(rs.getFloat(3));
				}else {
					dts[2] = formato.format(rs.getFloat(4));
				}
				
				dts[3] = rs.getInt(9);
				dts[4] = rs.getInt(7);
				dts[5] = formato.format(rs.getFloat(8));
				dts[6] = rs.getString(11);

				modeloBuscador.addRow(dts);				
				tblBuscador.setModel(modeloBuscador);
			}

			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(0)).setMaxWidth(215);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(1)).setMaxWidth(530);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(2)).setMaxWidth(70);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(3)).setMaxWidth(65);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(4)).setMaxWidth(70);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(5)).setMaxWidth(70);
			tblBuscador.getColumn(tblBuscador.getModel().getColumnName(6)).setMaxWidth(130);
			
			tblBuscador.setRowHeight(40);
			tblBuscador.setFont(new java.awt.Font("Tahoma", 0, 25));

			tblBuscador.setCellSelectionEnabled(true); // selecciona una sola casilla
			tblBuscador.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
			tblBuscador.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell"); // pinta la celda señalada de color gris
																			// oscuro
			tblBuscador.setGridColor(Color.darkGray);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
	}
	
	// metodos para la barra buscadora por nombre y por código
		public void filtroNombre() {
			filtro = textbuscanombre.getText();
			trsfiltro.setRowFilter(RowFilter.regexFilter(textbuscanombre.getText(), 1));
		}
		
		public void filtroCodigo() {
			filtro = textbuscacodigo.getText();
			trsfiltro.setRowFilter(RowFilter.regexFilter(textbuscacodigo.getText(), 0));
		}
}
