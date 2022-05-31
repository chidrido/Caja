package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javax.swing.KeyStroke;
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

public class Agenda extends JFrame{
	
	public Agenda() {
	
	Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

	int height = pantalla.height;

	int width = pantalla.width;

	setSize(width / 2 + 540, height / 2 + 315);

	setLocationRelativeTo(null);
	
	setUndecorated(true);

	setVisible(true);

	setResizable(false);

	panelAgenda panelagenda = new panelAgenda();

	add(panelagenda);
	
	}
}

class panelAgenda extends JPanel {
	
	JLabel etiquetatitulo;
	JLabel etiquetanombre;
	JLabel etiquetatelefono;
	JLabel etiquetadireccion;
	JLabel etiquetaemail;
	JLabel etiquetabuscanombre;
	
	JTextField txtnombre;
	JTextField txttelefono;
	JTextField txtdireccion;
	JTextField txtemail;
	JTextField txtbuscanombre;
	
	JButton botonagregar;
	JButton botonsalir;
	
	JComboBox<String> comboseleccion;
	
	DefaultTableModel modeloAgenda;
	JTable tblAgenda;
	JScrollPane scrollAgenda;
	
	private TableRowSorter<TableModel> trsfiltro;
	String filtro;
	
	String[] titulos = { "NOMBRE", "TLF.", "DIRECCION", "EMAIL", ""};
	
	Connection con = null;
	
	public panelAgenda() {
		
		modeloAgenda = new DefaultTableModel(null, titulos);
		tblAgenda = new JTable(modeloAgenda);
		scrollAgenda = new JScrollPane(tblAgenda);

		scrollAgenda.setBounds(10,80, 910, 515);
		scrollAgenda.setBorder(new LineBorder(Color.BLACK));
		add(scrollAgenda);
		
		setLayout(null);
		setBackground(Color.CYAN);

		tblAgenda.getColumn(tblAgenda.getModel().getColumnName(0)).setMaxWidth(270); // damos tamaño a las filas de la JTable																						
		tblAgenda.getColumn(tblAgenda.getModel().getColumnName(1)).setMaxWidth(100);
		tblAgenda.getColumn(tblAgenda.getModel().getColumnName(2)).setMaxWidth(240);
		tblAgenda.getColumn(tblAgenda.getModel().getColumnName(3)).setMaxWidth(225);
		tblAgenda.getColumn(tblAgenda.getModel().getColumnName(4)).setMaxWidth(75);
		tblAgenda.setRowHeight(30);
		tblAgenda.setFont(new java.awt.Font("Tahoma", 0, 16));
		
		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblAgenda.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);
		
		CambioColorAgenda cambiocoloragenda = new CambioColorAgenda();
		tblAgenda.setDefaultRenderer(Object.class, cambiocoloragenda); // muestra color en la celda señalada
		tblAgenda.setGridColor(Color.darkGray);
		
		TableColumn agregarColumn;			
		agregarColumn = tblAgenda.getColumnModel().getColumn(4); // llama a los metodos para mostrar el boton eliminar
		agregarColumn.setCellEditor(new EditorCeldasAgenda(tblAgenda));
		agregarColumn.setCellRenderer(new RenderizadoCaja(true));
		
		tblAgenda.setCellSelectionEnabled(true); // selecciona una sola casilla
		tblAgenda.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
		tblAgenda.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");
		
		etiquetatitulo = new JLabel("Agenda");
		etiquetatitulo.setBounds(540, 5, 200, 60);
		etiquetatitulo.setFont(new java.awt.Font(null, Font.PLAIN, 50));
		add(etiquetatitulo);
		
		etiquetanombre = new JLabel("Nombre");
		etiquetanombre.setBounds(20, 610, 150, 30);
		etiquetanombre.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(etiquetanombre);
		
		txtnombre = new JTextField();
		txtnombre.setBorder(new LineBorder(Color.BLACK));
		txtnombre.setBounds(20, 645, 370, 30);
		txtnombre.setFont(new java.awt.Font(null, Font.PLAIN, 18));
		add(txtnombre);
		
		txtnombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txttelefono.requestFocus();
			}			
		});
		
		etiquetatelefono = new JLabel("Telefono");
		etiquetatelefono.setBounds(400, 610, 150, 30);
		etiquetatelefono.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(etiquetatelefono);
		
		txttelefono = new JTextField();
		txttelefono.setBorder(new LineBorder(Color.BLACK));
		txttelefono.setBounds(400, 645, 110, 30);
		txttelefono.setFont(new java.awt.Font(null, Font.PLAIN, 18));
		add(txttelefono);
		
		txttelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtdireccion.requestFocus();
			}			
		});
		
		etiquetadireccion = new JLabel("Direccion");
		etiquetadireccion.setBounds(520, 610, 150, 30);
		etiquetadireccion.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(etiquetadireccion);
		
		txtdireccion = new JTextField();
		txtdireccion.setBorder(new LineBorder(Color.BLACK));
		txtdireccion.setBounds(520, 645, 370, 30);
		txtdireccion.setFont(new java.awt.Font(null, Font.PLAIN, 18));
		add(txtdireccion);
		
		txtdireccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtemail.requestFocus();				
			}			
		});	
		
		etiquetaemail = new JLabel("Email");
		etiquetaemail.setBounds(900, 610, 150, 30);
		etiquetaemail.setFont(new java.awt.Font(null, Font.PLAIN, 25));
		add(etiquetaemail);
		
		txtemail = new JTextField();
		txtemail.setBorder(new LineBorder(Color.BLACK));
		txtemail.setBounds(900, 645, 190, 30);
		txtemail.setFont(new java.awt.Font(null, Font.PLAIN, 18));
		add(txtemail);
		
		txtemail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						
				botonagregar.setEnabled(true);
				botonagregar.requestFocus();
			}			
		});	
		
		botonagregar = new JButton("Agregar");
		botonagregar.setBorder(new LineBorder(Color.BLACK));
		botonagregar.setBounds(1100, 645, 100, 30);
		botonagregar.setEnabled(false);
		botonagregar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-añadir-imagen-24.png"));
		add(botonagregar);
		
		botonagregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int seleccioncat = JOptionPane.showOptionDialog(null, "Selecciona la categoría", null,
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] 
						{ "CLIENTE", "DISTRIBUIDOR", "CANCELAR" }, // null para YES, NO y CANCEL
						"opcion 1");
					String cadenaseleccion = String.valueOf(seleccioncat);
					String selec;
					if (cadenaseleccion.equals("0")) {
						selec = "CLIENTES";
						GuardaGastos(modeloAgenda, txtnombre, txttelefono, txtdireccion, txtemail, selec);	
					}else if (cadenaseleccion.equals("1")) {
						selec = "DISTRIBUIDORES";
						GuardaGastos(modeloAgenda, txtnombre, txttelefono, txtdireccion, txtemail, selec);	
					}
					
					txtnombre.setText("");
					txttelefono.setText("");
					txtdireccion.setText("");
					
					botonagregar.setEnabled(false);	
			}
		});
		
		botonagregar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int seleccioncategoria = JOptionPane.showOptionDialog(null, "Selecciona la categoría", null,
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] 
							{ "CLIENTE", "DISTRIBUIDOR", "CANCELAR" }, // null para YES, NO y CANCEL
							"opcion 1");
					String cadenaseleccion = String.valueOf(seleccioncategoria);
					String selec;
					if (cadenaseleccion.equals("0")) {
						selec = "CLIENTES";
						GuardaGastos(modeloAgenda, txtnombre, txttelefono, txtdireccion, txtemail, selec);
						
					}else if (cadenaseleccion.equals("1")) {
						selec = "DISTRIBUIDORES";
						GuardaGastos(modeloAgenda, txtnombre, txttelefono, txtdireccion, txtemail, selec);
					}
						
					txtnombre.setText("");
					txttelefono.setText("");
					txtdireccion.setText("");
					txtemail.setText("");
					
					botonagregar.setEnabled(false);						
				}
			}
			public void keyReleased(KeyEvent evt) {
			}
			public void keyTyped(KeyEvent evt) {
			}
		});
		
		String opciones[] = new String[] { "CLIENTES", "DISTRIBUIDORES"};
		comboseleccion = new JComboBox<>(); 
		comboseleccion.setBorder(new LineBorder(Color.BLACK));
		comboseleccion.setBounds(940, 100, 260, 30);
		comboseleccion.setFont(new Font("Tahoma", 1, 25));
		
		comboseleccion.setModel(new DefaultComboBoxModel<>(opciones));
		add(comboseleccion);

		comboseleccion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				modeloAgenda.setRowCount(0);
				MostrarAgenda(modeloAgenda, tblAgenda, comboseleccion);		
			}
		});
		
		etiquetabuscanombre = new JLabel("Busca nombre :");
		etiquetabuscanombre.setBounds(940, 150, 250, 30);
		etiquetabuscanombre.setFont(new Font(null, Font.PLAIN, 30));
		add(etiquetabuscanombre);
		
		txtbuscanombre = new JTextField();
		txtbuscanombre.setBorder(new LineBorder(Color.BLACK));
		txtbuscanombre.setBounds(940, 190, 250, 30);
		txtbuscanombre.setFont(new Font("Tahoma", 1, 30));
		add(txtbuscanombre);

		txtbuscanombre.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent ev) {
			}
			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblAgenda.getModel());
				tblAgenda.setRowSorter(trsfiltro);
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
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setBounds(1080, 520, 120, 60);
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-50 (1).png"));
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window w = SwingUtilities.getWindowAncestor(panelAgenda.this);
				w.dispose();
			}
		});
		
		modeloAgenda.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
					CambiaCeldaWeb(modeloAgenda, tblAgenda, e); // llama a método que activa las celdas
			}
		});
		
		MostrarAgenda(modeloAgenda, tblAgenda, comboseleccion);	
	}
	
	// método que muestra la tabla agenda tblAgenda
	public void MostrarAgenda(DefaultTableModel modeloAgenda, JTable tblAgenda, JComboBox<String> comboseleccion) {

		try {
			
			String seleccion = comboseleccion.getSelectedItem().toString();
			
			int contador = tblAgenda.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloAgenda.removeRow(i);
			}
			
			String sql = "SELECT * FROM tblAgenda WHERE Categoria = '" + seleccion + "'";
			//System.out.println("SELECT * FROM tblAgenda WHERE Categoria = '" + seleccion + "'");
			con = Conexion.ejecutarConexion();

			String dts[] = new String[4];
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla Diario de Caja
				dts[1] = rs.getString(2);
				dts[2] = rs.getString(3);
				dts[3] = rs.getString(4);
				
				modeloAgenda.addRow(dts);
			}
			rs.close();
			st.close();
			
			tblAgenda.setModel(modeloAgenda);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// método para cambiar la fila seleccionada
	public void CambiaCeldaWeb(DefaultTableModel modeloArticulosWeb, JTable tblArticulosWeb, TableModelEvent e) {

		if (e.getType() == TableModelEvent.UPDATE) {

			try {
				String columna = null;
				int n = 0;
				
				if (e.getColumn() == 0) { // asignamos un indice para poder usar SQL más facilmente
					columna = "Nombre";
				} else if (e.getColumn() == 1) {
					columna = "Telefono";
					n = 1;
				} else if (e.getColumn() == 2) {
					columna = "Direccion";							
					n = 2;
				} else if (e.getColumn() == 3) {
					columna = "Email";
					n = 3;
				} else if (e.getColumn() == 4) {
					columna = "Nombre";
					n = 4;
				}
				
				try {
					if(n == 0) {							
						if(modeloAgenda.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
							String sql0 = "UPDATE tblAgenda SET " + columna + " = null WHERE Telefono = '" + modeloAgenda.getValueAt(e.getFirstRow(), 1) + "'";
							//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Telefono = '" + modeloAgenda.getValueAt(e.getFirstRow(), 1) + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst0 = con.prepareStatement(sql0);
							pst0.executeUpdate();
							pst0.close();
						}else {
							String sql0 = "UPDATE tblAgenda SET " + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + 
								"' WHERE Telefono = '" + modeloAgenda.getValueAt(e.getFirstRow(), 1) + "'";
							//System.out.println("UPDATE tblAgenda SET " + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) +
							//		"' WHERE Telefono = '" + modeloAgenda.getValueAt(e.getFirstRow(), 1) + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst0 = con.prepareStatement(sql0);
							pst0.executeUpdate();
							pst0.close();
						}
					}else if(n >= 1 && n <= 3) {
						if(modeloAgenda.getValueAt(e.getFirstRow(), e.getColumn()).equals("")) {
							String sql1 = "UPDATE tblAgenda SET " + columna + " = null WHERE Nombre = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'";
							//System.out.println("UPDATE tblProductos SET " + columna + " = null WHERE Nombre = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst1 = con.prepareStatement(sql1);
							pst1.executeUpdate();
							pst1.close();
						}else {
							String sql1 = "UPDATE tblAgenda SET " + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), e.getColumn()) + 
								"' WHERE Nombre = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'";
							//System.out.println("UPDATE tblAgenda SET " + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), e.getColumn()) +
							//		"' WHERE Nombre = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'");
							con = Conexion.ejecutarConexion();
							PreparedStatement pst1 = con.prepareStatement(sql1);
							pst1.executeUpdate();
							pst1.close();
						}
					}else if(n == 4) {
						int eleccion = JOptionPane.showConfirmDialog(null, "Esta seguro de que quiere eliminar los datos?", "Confirmar", JOptionPane.YES_NO_OPTION);

						if (JOptionPane.OK_OPTION == eleccion) {
							String sql3 = "DELETE FROM tblAgenda WHERE " + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'";
							//System.out.println("DELETE FROM tblAgenda WHERE "  + columna + " = '" + modeloAgenda.getValueAt(e.getFirstRow(), 0) + "'");
								
							con = Conexion.ejecutarConexion();
							PreparedStatement pst3 = con.prepareStatement(sql3);
							pst3.executeUpdate();

							int fila = e.getFirstRow();
							modeloAgenda.removeRow(fila);
								
							pst3.close();								
						}
					}		
					
				} catch (Exception evt) {
					JOptionPane.showMessageDialog(null, "Hay ocurrido un ERROR!");
					evt.printStackTrace();
				}
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// método que guarda en la tabla de la base de datos y también los muestra en tblAgenda
	public void GuardaGastos(DefaultTableModel modeloAgenda, JTextField txtnombre, JTextField txttelefono, JTextField txtdireccion,
		JTextField txtemail, String cadenaseleccion) {

		String cadenanombre, cadenatelefono, cadenadireccion, cadenaemail;

		cadenanombre = txtnombre.getText();
		cadenatelefono = txttelefono.getText();
		cadenadireccion = txtdireccion.getText();
		cadenaemail = txtemail.getText();
			
		try {
			con = Conexion.ejecutarConexion();

			String sql = "INSERT INTO tblAgenda(Nombre,Telefono,Direccion,Email,Categoria) VALUES (?,?,?,?,?)";
			//System.out.println("INSERT INTO tblAgenda(Nombre,Telefono,Direccion,Categoria,Email) VALUES (?,?,?,?,?)");
			
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, cadenanombre);
			pst.setString(2, cadenatelefono);
			pst.setString(3, cadenadireccion);
			pst.setString(4, cadenaemail);
			pst.setString(5, cadenaseleccion);

			pst.executeUpdate();
			pst.close();
			
			String dts[] = new String[5];
			
			dts[0] = cadenanombre;
			dts[1] = cadenatelefono;
			dts[2] = cadenadireccion;
			dts[3] = cadenaemail;
			dts[4] = cadenaseleccion;

			modeloAgenda.addRow(dts);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	// método para buscar por nombre del artículo
	public void filtroNombre() {
		filtro = txtbuscanombre.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(txtbuscanombre.getText(), 0));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(10, 10, 1200, 60);
		g.drawRect(930, 80, 280, 515);
		g.drawRect(10, 605, 1200, 80);
	}
	
}

