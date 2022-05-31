package Caja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AgregarDireccion extends JFrame{
	
	public AgregarDireccion() {
		
		setBounds(200, 100, 1000, 410);

		setVisible(true);

		setAlwaysOnTop(true);

		setResizable(false);
		
		dispose();
		
		setUndecorated(true);	

		PanelAgregarDireccion panelagregardireccion = new PanelAgregarDireccion();

		add(panelagregardireccion);
	}
}

class PanelAgregarDireccion extends JPanel {
	
	JLabel etiquetabuscanombre;
	
	JTextField txtbuscanombre;
	
	JButton botonsalir;
	
	DefaultTableModel modeloAgregarDireccion;
	JTable tblAgregarDireccion;
	JScrollPane scrollAgregarDireccion;
	
	String[] titulos = { "NOMBRE", "TELEFONO", "DIRECCION"};
	
	private TableRowSorter<TableModel> trsfiltro;
	String filtro;
	
	static String nombre;
	static String telefono;
	static String direccion;
	
	Connection con = null;
	
	public PanelAgregarDireccion() {
		
		modeloAgregarDireccion = new DefaultTableModel(null, titulos);
		tblAgregarDireccion = new JTable(modeloAgregarDireccion);
		scrollAgregarDireccion = new JScrollPane(tblAgregarDireccion);

		scrollAgregarDireccion.setBounds(20, 20, 960, 340);
		add(scrollAgregarDireccion);
		
		CambioColorAgenda cambiocoloragenda = new CambioColorAgenda();
		tblAgregarDireccion.setDefaultRenderer(Object.class, cambiocoloragenda); // muestra color en la celda señalada
		tblAgregarDireccion.setGridColor(Color.darkGray);

		tblAgregarDireccion.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblAgregarDireccion.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);

		setLayout(null);

		tblAgregarDireccion.getColumn(tblAgregarDireccion.getModel().getColumnName(0)).setMaxWidth(380);
		tblAgregarDireccion.getColumn(tblAgregarDireccion.getModel().getColumnName(1)).setMaxWidth(150);
		tblAgregarDireccion.getColumn(tblAgregarDireccion.getModel().getColumnName(2)).setMaxWidth(430);
		tblAgregarDireccion.setRowHeight(40);
		tblAgregarDireccion.setFont(new java.awt.Font("Tahoma", 0, 25));
		
		etiquetabuscanombre = new JLabel("Busca nombre :");
		etiquetabuscanombre.setBounds(20, 365, 250, 30);
		etiquetabuscanombre.setFont(new Font(null, Font.PLAIN, 30));
		add(etiquetabuscanombre);
		
		txtbuscanombre = new JTextField();
		txtbuscanombre.setBounds(240, 370, 300, 30);
		txtbuscanombre.setFont(new Font("Tahoma", 1, 30));
		add(txtbuscanombre);

		txtbuscanombre.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblAgregarDireccion.getModel());
				tblAgregarDireccion.setRowSorter(trsfiltro);
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
		
		botonsalir = new JButton();
		botonsalir.setBounds(880, 370, 100, 30);
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window w = SwingUtilities.getWindowAncestor(PanelAgregarDireccion.this);
				w.dispose();
			}
		});
		
		MuestraAgenda(modeloAgregarDireccion, tblAgregarDireccion);

		tblAgregarDireccion.addMouseListener(new MouseListener() { // al producirse un evento en las columnas con JButton
			public void mouseClicked(MouseEvent e) { // se selecciona la categoria y la manda a JComboBox

				int fila = tblAgregarDireccion.rowAtPoint(e.getPoint());
				
				nombre = tblAgregarDireccion.getValueAt(fila, 0).toString();
				telefono = tblAgregarDireccion.getValueAt(fila, 1).toString();
				direccion = tblAgregarDireccion.getValueAt(fila, 2).toString();
				
				Window w = SwingUtilities.getWindowAncestor(PanelAgregarDireccion.this);
				w.dispose();
				
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
	
	public String DevuelveNombre() {
		
		return nombre;
	}
	
	public String DevuelveTelefono() {
		
		return telefono;
	}

	public String DevuelveDireccion() {
	
		return direccion;
	}
	
	// método que muestra la tabla agenda
	public void MuestraAgenda(DefaultTableModel modeloAgregarDireccion, JTable tblAgregarDireccion) {
			

		String sql = "SELECT * FROM tblAgenda WHERE Categoria = 'Clientes'";
		System.out.println("");
		con = Conexion.ejecutarConexion();

		try {

			String dts[] = new String[3];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

			dts[0] = rs.getString(1); // muestra tdos los productos en la tabla Diario de Caja
			dts[1] = rs.getString(2);
			dts[2] = rs.getString(3);
					
			modeloAgregarDireccion.addRow(dts);
			}
			rs.close();
			st.close();
				
			tblAgregarDireccion.setModel(modeloAgregarDireccion);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	
	// método para buscar por nombre del artículo
	public void filtroNombre() {
		filtro = txtbuscanombre.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscanombre.getText(), 0));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, 999, 409);
	}
}
