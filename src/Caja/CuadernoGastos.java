package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class CuadernoGastos extends JFrame {

	CuadernoGastos() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);
		
		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		PanelCuadernoGastos PanelCuadernoGastos = new PanelCuadernoGastos();

		add(PanelCuadernoGastos);
	}
}

class PanelCuadernoGastos extends JPanel {

	DefaultTableModel modeloGastos;
	JTable tblGastos;
	JScrollPane scrollGastos;

	JLabel labelfechagastos;
	JLabel labelproveedorgastos;
	JLabel labelgastos;
	JLabel labelañadir;
	JLabel titulo;
	JLabel venta;
	JLabel ventames;
	JLabel gastos;
	JLabel gastosmes;
	JLabel total;
	JLabel seleccionagastos;
	JLabel buscanombre;

	JTextField txtfechagastos;
	JTextField txtproveedorgastos;
	JTextField txtgastos;
	JTextField txtventa;
	JTextField txtventames;
	JTextField txtgast;
	JTextField txtgastmes;
	JTextField txttotal;
	JTextField txtbuscagastos;

	JButton botonagregar;
	JButton botonsalir;
	JButton botonborrarhistorial;

	JComboBox<String> comboseleccion;

	String[] titulos = { "FECHA", "GASTO", "CANTIDAD" };

	Connection con = null;
	
	private TableRowSorter<TableModel> trsfiltro;
	String filtro;

	PanelCajero panelcajero = new PanelCajero();

	public PanelCuadernoGastos() {

		modeloGastos = new DefaultTableModel(null, titulos);
		tblGastos = new JTable(modeloGastos);
		scrollGastos = new JScrollPane(tblGastos);

		scrollGastos.setBounds(20, 120, 590, 445);
		scrollGastos.setBorder(new LineBorder(Color.BLACK));
		add(scrollGastos);
		
		tblGastos.setEnabled(false);

		tblGastos.getColumn(tblGastos.getModel().getColumnName(0)).setMaxWidth(120); // damos tamaño a las filas de la JTable																						
		tblGastos.getColumn(tblGastos.getModel().getColumnName(1)).setMaxWidth(360);
		tblGastos.getColumn(tblGastos.getModel().getColumnName(2)).setMaxWidth(110);
		tblGastos.setRowHeight(30);
		tblGastos.setFont(new java.awt.Font("Tahoma", 0, 22));

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblGastos.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);

		CambioColor cambiocolor = new CambioColor();
		tblGastos.setDefaultRenderer(Object.class, cambiocolor); // muestra color en la celda señalada
		tblGastos.setGridColor(Color.darkGray);

		setLayout(null);
		setBackground(Color.CYAN);

		titulo = new JLabel("Diario de caja"); // titulo de la ventana
		titulo.setBounds(400, 5, 500, 100);
		titulo.setFont(new java.awt.Font(null, Font.PLAIN, 70));
		add(titulo);

		labelfechagastos = new JLabel("Fecha"); // Crea etiqueta y campo fecha
		labelfechagastos.setBounds(65, 590, 110, 30);
		labelfechagastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(labelfechagastos);
		
		txtfechagastos = new JTextField();
		txtfechagastos.setBorder(new LineBorder(Color.BLACK));
		txtfechagastos.setBounds(35, 625, 140, 35);
		txtfechagastos.setEditable(false);
		txtfechagastos.setHorizontalAlignment(JTextField.CENTER);
		txtfechagastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(txtfechagastos);

		String fechacomple = MetodoFecha(); // llamo al método insetar fecha en campo fecha
		txtfechagastos.setText(fechacomple);

		labelproveedorgastos = new JLabel("Proveedor"); // Crea etiqueta y campo proveedor
		labelproveedorgastos.setBounds(295, 590, 180, 30);
		labelproveedorgastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(labelproveedorgastos);
		
		txtproveedorgastos = new JTextField();
		txtproveedorgastos.setBorder(new LineBorder(Color.BLACK));
		txtproveedorgastos.setBounds(185, 625, 310, 35);
		txtproveedorgastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(txtproveedorgastos);

		txtproveedorgastos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtgastos.requestFocus();
			}
		});

		labelgastos = new JLabel("Gasto");                                     // Crea etiqueta y campo gastos por proveedor
		labelgastos.setBounds(510, 590, 120, 30);
		labelgastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(labelgastos);
		
		txtgastos = new JTextField();
		txtgastos.setBorder(new LineBorder(Color.BLACK));
		txtgastos.setBounds(505, 625, 90, 35);
		txtgastos.setHorizontalAlignment(JTextField.CENTER);
		txtgastos.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(txtgastos);

		txtgastos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				botonagregar.requestFocus();
			}
		});
		
		labelañadir = new JLabel("Añadir");                                      // Crea etiqueta y campo gastos por proveedor
		labelañadir.setBounds(635, 590, 120, 30);
		labelañadir.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(labelañadir);

		botonagregar = new JButton();                                            // crea el botón para insertar en la tabla los datos
		botonagregar.setBorder(new LineBorder(Color.BLACK));
		botonagregar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-guardar-30.png"));
		botonagregar.setBounds(605, 625, 130, 35);
		add(botonagregar);

		botonagregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GuardaGastos(modeloGastos, txtfechagastos, txtproveedorgastos, txtgastos); // llama al método que guarda los campos en la tabla
				CuentaTotal();

				txtproveedorgastos.setText("");
				txtgastos.setText("");
			}
		});

		botonagregar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					GuardaGastos(modeloGastos, txtfechagastos, txtproveedorgastos, txtgastos);
					CuentaTotal();

					txtproveedorgastos.setText("");
					txtgastos.setText("");
				}
			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		buscanombre = new JLabel("Buscador"); //// Crea etiqueta y campo gastos por proveedor
		buscanombre.setBounds(850, 590, 120, 30);
		buscanombre.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(buscanombre);
		
		txtbuscagastos = new JTextField();
		txtbuscagastos.setBorder(new LineBorder(Color.BLACK));
		txtbuscagastos.setBounds(780, 625, 255, 35);
		txtbuscagastos.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		add(txtbuscagastos);

		txtbuscagastos.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblGastos.getModel());
				tblGastos.setRowSorter(trsfiltro);
			}
		});

		txtbuscagastos.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (txtbuscagastos.getText()).toUpperCase();
				txtbuscagastos.setText(cadena);
				repaint();
				filtroNombre();
			}
		});

		venta = new JLabel("Venta del día"); // crea etiqueta y campo de venta del día
		venta.setBounds(665, 155, 300, 40);
		venta.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(venta);
		
		txtventa = new JTextField();
		txtventa.setBorder(new LineBorder(Color.BLACK));
		txtventa.setFont(new Font("Tahoma", 1, 45));
		txtventa.setBounds(650, 200, 250, 60);
		txtventa.setHorizontalAlignment(JTextField.CENTER);
		txtventa.setEditable(false);
		add(txtventa);

		ventames = new JLabel("Venta del mes"); // crea etiqueta y campo de venta del día
		ventames.setBounds(950, 155, 310, 40);
		ventames.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(ventames);
		
		txtventames = new JTextField();
		txtventames.setBorder(new LineBorder(Color.BLACK));
		txtventames.setFont(new Font("Tahoma", 1, 45));
		txtventames.setBounds(940, 200, 240, 60);
		txtventames.setHorizontalAlignment(JTextField.CENTER);
		txtventames.setEditable(false);
		add(txtventames);
		
		gastos = new JLabel("Gastos del día"); // crea etiqueta y campo de gastos del día
		gastos.setBounds(660, 280, 350, 40);
		gastos.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(gastos);
		
		txtgast = new JTextField();
		txtgast.setBorder(new LineBorder(Color.BLACK));
		txtgast.setFont(new Font("Tahoma", 1, 45));
		txtgast.setBounds(650, 325, 240, 60);
		txtgast.setHorizontalAlignment(JTextField.CENTER);
		txtgast.setEditable(false);
		add(txtgast);
		
		gastosmes = new JLabel("Gastos del mes"); // crea etiqueta y campo de gastos del día
		gastosmes.setBounds(940, 280, 350, 40);
		gastosmes.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(gastosmes);
		
		txtgastmes = new JTextField();
		txtgastmes.setBorder(new LineBorder(Color.BLACK));
		txtgastmes.setFont(new Font("Tahoma", 1, 45));
		txtgastmes.setBounds(940, 325, 240, 60);
		txtgastmes.setHorizontalAlignment(JTextField.CENTER);
		txtgastmes.setEditable(false);
		add(txtgastmes);

		total = new JLabel("Total caja"); // crea etiqueta y campo de ganancias
		total.setBounds(690, 410, 300, 40);
		total.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(total);
		
		txttotal = new JTextField();
		txttotal.setBorder(new LineBorder(Color.BLACK));
		txttotal.setFont(new Font("Tahoma", 1, 45));
		txttotal.setBounds(650, 455, 240, 60);
		txttotal.setHorizontalAlignment(JTextField.CENTER);
		txttotal.setEditable(false);
		add(txttotal);
		
		seleccionagastos = new JLabel("Selec. gastos"); // crea etiqueta y campo de ganancias
		seleccionagastos.setBounds(955, 410, 300, 40);
		seleccionagastos.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		add(seleccionagastos);
		
		String opciones[] = new String[] { "TOTALES", "ACTUALES"};
		comboseleccion = new JComboBox<>(); 
		comboseleccion.setBorder(new LineBorder(Color.BLACK));
		comboseleccion.setBounds(940, 455, 240, 60);
		comboseleccion.setFont(new Font("Tahoma", 1, 40));
		
		comboseleccion.setModel(new DefaultComboBoxModel<>(opciones));
		add(comboseleccion);

		comboseleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (comboseleccion.getSelectedItem().toString() == "TOTALES") { 
					modeloGastos.setRowCount(0); 											 // limpia la tabla de gastos
					MostrarCuadernoGastos(tblGastos);										 // muestra los gastos totales
				} else if (comboseleccion.getSelectedItem().toString() == "ACTUALES") {
					modeloGastos.setRowCount(0);
					MostrarCuadernoGastosDia(tblGastos); 									 // muestra los gastos del dia de hoy
				}
			}
		});
		
		botonborrarhistorial = new JButton();                                            // crea el botón para insertar en la tabla los datos
		botonborrarhistorial.setBorder(new LineBorder(Color.BLACK));
		botonborrarhistorial.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-atención-26.png"));
		botonborrarhistorial.setBounds(1078, 590, 115, 35);
		add(botonborrarhistorial);
		
		botonborrarhistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				EliminarHistorialCuentas(modeloGastos, tblGastos);
			}
		});
		
		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		botonsalir.setBounds(1078, 635, 115, 35);
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window w = SwingUtilities.getWindowAncestor(PanelCuadernoGastos.this);
				w.dispose();
			}			
		});

		CuentaTotal();

		MostrarCuadernoGastos(tblGastos); // muestra los gastos totales
		SumaVentasmes();
	}
	
	//método que muestra los datos en los JTextField
	public void CuentaTotal() {
		
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);
		
		String cuentatotal = SumaTotalDia();
		String ventatotalmes = SumaVentasmes();
		String sumagastosmes = SumaGastosmes();

		if (cuentatotal != null) {
			Float floatcuentatotal = Float.parseFloat(cuentatotal);
			txtventa.setText(formato.format(floatcuentatotal));

			String gastototal = SumaGastosDia();

			if (gastototal != null) {
				Float floatgastototal = Float.parseFloat(gastototal);
				txtgast.setText(formato.format(floatgastototal));

				Float sumatotal = floatcuentatotal - floatgastototal;
				txttotal.setText(formato.format(sumatotal));
			}
		}
		
		if (ventatotalmes != null) {
		
			Float floatventatotalmes = Float.parseFloat(ventatotalmes);
			txtventames.setText(formato.format(floatventatotalmes));
		}
		
		if (sumagastosmes != null) {
			
			Float floatgastostotalmes = Float.parseFloat(sumagastosmes);
			txtgastmes.setText(formato.format(floatgastostotalmes));
		}
	}

	// método que muestra los datos introducidos en los campos fecha, proveedor, y gastos
	public void MostrarCuadernoGastos(JTable tblGastos) {

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT * FROM ListaGastos";
		con = Conexion.ejecutarConexion();

		try {

			String dts[] = new String[3];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla Diario de Caja
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(3));

				modeloGastos.addRow(dts);
			}
			rs.close();
			st.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// método para mostrar los gastos del dia actual
	public void MostrarCuadernoGastosDia(JTable tblGastos) {

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		String fechacompleta = dia + "/" + mes + "/" + annio;

		String sql = "SELECT * FROM ListaGastos WHERE Fecha = '" + fechacompleta + "'";
		con = Conexion.ejecutarConexion();

		try {

			String dts[] = new String[5];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla Diario de Caja
				dts[1] = rs.getString(2);
				dts[2] = formato.format(rs.getFloat(3));

				modeloGastos.addRow(dts);
			}
			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// método que suma el total del día
	public Float SumaTotalGastos() {

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		String fechacompleta = dia + "/" + mes + "/" + annio;
		Float total = (float) 0;

		for (int i = 0; i < tblGastos.getRowCount(); i++) {

			if (tblGastos.getValueAt(i, 0).equals(fechacompleta)) {
				total = total + Float.parseFloat(tblGastos.getValueAt(i, 2).toString());
			}
		}
		return total;
	}

	// método que guarda en la tabla de la base de datos y también los muestra en la JTable gastos
	public void GuardaGastos(DefaultTableModel modeloGastos, JTextField fecha, JTextField proveedor,
			JTextField gastos) {

		String cadenafecha, cadenaproveedor;
		Float cadenagastos;

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		cadenafecha = fecha.getText();
		cadenaproveedor = proveedor.getText();
		cadenagastos = Float.parseFloat(gastos.getText().toString());

		try {
			con = Conexion.ejecutarConexion();

			String sql1 = "INSERT INTO ListaGastos(FECHA,GASTO,CANTIDAD) VALUES (?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql1);

			String numgastos = String.valueOf(cadenagastos);

			pst.setString(1, cadenafecha);
			pst.setString(2, cadenaproveedor);
			pst.setString(3, numgastos);

			pst.executeUpdate();
			pst.close();

			String sql2 = "SELECT MAX(ID) FROM ListaGastos;";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql2);
			String dts[] = new String[3];

			while (rs.next()) {

				dts[0] = cadenafecha;
				dts[1] = cadenaproveedor;
				dts[2] = numgastos;

				modeloGastos.addRow(dts);
			}
			rs.close();
			st.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// método que crea la fecha del día
	public String MetodoFecha() {

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		String fechacompleta = dia + "/" + mes + "/" + annio;

		return fechacompleta;
	}
	
	public String SumaGastosDia() {

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		String fechacompleta = dia + "/" + mes + "/" + annio;

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT sum(Cantidad) FROM ListaGastos WHERE Fecha = '" + fechacompleta + "'";
		// System.out.println("SELECT sum(TotalDia) FROM ControlVentas WHERE Fecha = '"
		// + fechacompleta + "'");
		con = Conexion.ejecutarConexion();

		String valorgasto = null;

		try {

			String dts[] = new String[5];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1);
				valorgasto = dts[0];
			}

			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return valorgasto;
	}

	// método que suma los totales de las cuentas del día para pasarlo a Diario de Caja
	public String SumaTotalDia() {

		Calendar calendario = Calendar.getInstance();

		String dia = Integer.toString(calendario.get(Calendar.DATE));
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(calendario.get(Calendar.YEAR));

		String fechacompleta = dia + "/" + mes + "/" + annio;

		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT sum(TotalDia) FROM ControlVentas WHERE Fecha = '" + fechacompleta + "'";
		//System.out.println("SELECT sum(TotalDia) FROM ControlVentas WHERE Fecha = '" + fechacompleta + "'");
		con = Conexion.ejecutarConexion();

		String valor = null;

		try {

			String dts[] = new String[1];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1);
				valor = dts[0];
			}

			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return valor;
	}

	public String SumaVentasmes() {

		Calendar calendario = Calendar.getInstance();
		
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);		
		String annio = Integer.toString(calendario.get(Calendar.YEAR));
		
		String fechacompleta = "__" + "/" + mes + "/" + annio;
		
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT sum(TotalDia) FROM ControlVentas WHERE Fecha LIKE'" + fechacompleta + "'";
		//System.out.println("SELECT sum(TotalDia) FROM ControlVentas WHERE Fecha LIKE '" + fechacompleta + "'");
		
		con = Conexion.ejecutarConexion();

		String valorventames = null;

		try {

			String dts[] = new String[1];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1);
				valorventames = dts[0];
			}

			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return valorventames;		
	}
	
	public String SumaGastosmes() {

		Calendar calendario = Calendar.getInstance();
		
		String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);		
		String annio = Integer.toString(calendario.get(Calendar.YEAR));
		
		String fechacompleta = "__" + "/" + mes + "/" + annio;
		
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
		simbolo.setDecimalSeparator('.');
		DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

		String sql = "SELECT sum(Cantidad) FROM ListaGastos WHERE Fecha LIKE'" + fechacompleta + "'";
		//System.out.println("SELECT sum(Cantidad) FROM ListaGastos WHERE Fecha LIKE'" + fechacompleta + "'");
		
		con = Conexion.ejecutarConexion();

		String valorgastosmes = null;

		try {

			String dts[] = new String[1];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1);
				valorgastosmes = dts[0];
			}

			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return valorgastosmes;		
	}
	
	public void EliminarHistorialCuentas(DefaultTableModel modeloGastos, JTable tblGastos) {
		
		try {

			int eleccion = JOptionPane.showConfirmDialog(null, "Está seguro que quiere eliminar historial de cuentas?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (JOptionPane.OK_OPTION == eleccion) {
				String sql = "DELETE FROM ControlVentas"; // borra todas las filas y libera el espacio de estas
				System.out.println("DELETE FROM ControlVentas");
				PreparedStatement pst = con.prepareStatement(sql);

				pst.execute();
				pst.close();
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// metodos para la barra buscadora por nombre y por articulos
		public void filtroNombre() {
			filtro = txtbuscagastos.getText();
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscagastos.getText(), 1));
		}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(20, 20, 1185, 85);
		g.drawRect(625, 120, 580, 445);
		g.drawRect(20, 580, 730, 100);
		g.drawRect(765, 580, 285, 100);
		g.drawRect(1065, 580, 140, 100);
	}
}
