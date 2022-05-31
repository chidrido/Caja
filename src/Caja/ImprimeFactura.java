package Caja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ImprimeFactura extends JFrame {

	public ImprimeFactura() {

		setBounds(100, 220, 880, 600);
		
		//setAlwaysOnTop(true); // hace que la ventana quede por encima de las demás
		
		setUndecorated(true);

		setLocationRelativeTo(null);

		setVisible(true);

		setResizable(false);

		panelImprimeFactura panelimprimefactura = new panelImprimeFactura();

		add(panelimprimefactura);
	}
}

class panelImprimeFactura extends JPanel {
	
	JLabel etiquetatitulo;
	
	JButton botonimprimir;
	JButton botonborrar;
	JButton botonsalir;
	
	String[] titulos = { "Nombres de los articulos", "Precios" };
	DefaultTableModel modeloImprimeFactura;
	JTable tblImprimeFactura;
	JScrollPane jscrollImprimeFactura;
	
	static Connection con = null;
	
	public panelImprimeFactura(){
		
		modeloImprimeFactura = new DefaultTableModel(null, titulos);
		tblImprimeFactura = new JTable(modeloImprimeFactura);
		jscrollImprimeFactura = new JScrollPane(tblImprimeFactura);

		jscrollImprimeFactura.setBounds(20, 90, 700, 490);
		jscrollImprimeFactura.setBorder(new LineBorder(Color.BLACK));
		add(jscrollImprimeFactura);
		
		setLayout(null);
		
		setBackground(Color.CYAN);
		
		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblImprimeFactura.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);
		
		tblImprimeFactura.getTableHeader().setReorderingAllowed(false);
		
		tblImprimeFactura.getColumn(tblImprimeFactura.getModel().getColumnName(0)).setMaxWidth(620);
		tblImprimeFactura.getColumn(tblImprimeFactura.getModel().getColumnName(1)).setMaxWidth(80);
		
		ColorListaFactura cambiocolor = new ColorListaFactura();
		tblImprimeFactura.setDefaultRenderer(Object.class, cambiocolor); // muestra color en la celda señalada
		
		etiquetatitulo = new JLabel("Productos Actualizados");
		etiquetatitulo.setBounds(160, 10, 600, 60);
		etiquetatitulo.setFont(new java.awt.Font(null, Font.PLAIN, 55));
		add(etiquetatitulo);
		
		botonimprimir = new JButton();
		botonimprimir.setBorder(new LineBorder(Color.BLACK));
		botonimprimir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-imprimir-52.png"));
		botonimprimir.setBounds(750, 200, 100, 60);
		add(botonimprimir);
		
		botonimprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				GuardaListaTxt(tblImprimeFactura);
				Imprimir.ImprimirFacturaActualizada();
				Window w = SwingUtilities.getWindowAncestor(panelImprimeFactura.this);
				w.dispose();
			}
		});
		
		botonborrar = new JButton();
		botonborrar.setBorder(new LineBorder(Color.BLACK));
		botonborrar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-basura-llena-48 (2).png"));
		botonborrar.setBounds(750, 310, 100, 60);
		add(botonborrar);
		
		botonborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BorraLista(modeloImprimeFactura, tblImprimeFactura);
			}			
		});
		
		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-50 (1).png"));
		botonsalir.setBounds(750, 420, 100, 60);
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Window w = SwingUtilities.getWindowAncestor(panelImprimeFactura.this);
				w.dispose();
			}			
		});
		
		MostrarListaFactura(modeloImprimeFactura, tblImprimeFactura);
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 879, 599);
	}
	
	public void MostrarListaFactura(DefaultTableModel modeloImprimeFactura, JTable tblImprimeFactura) {
		
		try {
			String[] titulos = { "Cod.Barras", "Articulo", "Unid.", "Precio", "" };
			con = Conexion.ejecutarConexion();
			
			int contador = tblImprimeFactura.getRowCount() - 1;
			for (int i = contador; i >= 0; i--) {
				modeloImprimeFactura.removeRow(i);
			}
			
			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			Object dts[] = new Object[5];
			String sql = "SELECT * FROM TablaFacturas";
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				dts[0] = rs.getString(1); // muestra tdos los productos en la tabla de gestor de articulos
				dts[1] = rs.getString(2);

				modeloImprimeFactura.addRow(dts);
				
				tblImprimeFactura.setModel(modeloImprimeFactura);
			}

			tblImprimeFactura.getColumn(tblImprimeFactura.getModel().getColumnName(0)).setMaxWidth(620);
			tblImprimeFactura.getColumn(tblImprimeFactura.getModel().getColumnName(1)).setMaxWidth(145);
			tblImprimeFactura.setRowHeight(30);
			tblImprimeFactura.setFont(new java.awt.Font("Tahoma", 0, 20));

			tblImprimeFactura.setCellSelectionEnabled(true); // selecciona una sola casilla
			tblImprimeFactura.setSurrendersFocusOnKeystroke(false); // facilita la edicion de la casilla
			tblImprimeFactura.getInputMap(javax.swing.JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell"); // pinta la celda señalada de color gris
																			// oscuro
			tblImprimeFactura.setGridColor(Color.darkGray);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void BorraLista(DefaultTableModel modeloImprimeFactura, JTable tblLista) {

		try {

			int eleccion = JOptionPane.showConfirmDialog(null, "Eliminar lista ?", "Confirmar",
					JOptionPane.YES_NO_OPTION);

			if (JOptionPane.OK_OPTION == eleccion) {
				String sql = "DELETE FROM TablaFacturas"; // borra todas las filas y libera el espacio de estas
				PreparedStatement pst = con.prepareStatement(sql);

				pst.execute();
				pst.close();

				int contador = tblLista.getRowCount() - 1;
				for (int i = contador; i >= 0; i--) {
					modeloImprimeFactura.removeRow(i);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// método para guardar en formato txt la tabla de compra tblLista
		public static void GuardaListaTxt(JTable tblImprimeFactura) {
			
			int confirmar = JOptionPane.showConfirmDialog(null,"Quiere imprimir la tabla? ", "Confirmar", JOptionPane.YES_NO_OPTION);
			
			if (JOptionPane.OK_OPTION == confirmar) {
				try {				
					String Lista = "C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\lista_actualizada.txt";
					BufferedWriter bfw = new BufferedWriter(new FileWriter(Lista));

					bfw.write("                                          ");
					bfw.newLine();
					bfw.write("\t    Lista de productos actualizadas      ");
					bfw.newLine();
					bfw.newLine();
					bfw.write("\t");
				
					for (int i = 0; i < tblImprimeFactura.getRowCount(); i++) { // realiza un barrido por filas.
						for (int j = 0; j < tblImprimeFactura.getColumnCount(); j++) { // realiza un barrido por columnas.

							if (tblImprimeFactura.getValueAt(i, j) != null) {
								if (j == 0) {
									String temporal = tblImprimeFactura.getValueAt(i, j).toString();
									String tempo = temporal + "                                         ";
									String temp = tempo.substring(0, 28);
									bfw.write(temp);
								} else {
									String temporal = tblImprimeFactura.getValueAt(i, j).toString();
									String tempo = temporal + "                                         ";
									String temp = tempo.substring(0, 10);
									bfw.write("\t");
									bfw.write(temp);
									bfw.newLine(); // inserta nueva linea.
								}
							} else {
								String tempo = "VACIO" + "                                    ";
								String temp = tempo.substring(0, 10);
								bfw.write(temp);
							}
							bfw.write("\t"); // inserta un tabulado
						}
					}
					bfw.write("                                          ");
					bfw.newLine();
					bfw.write("\t\t             FIN DE LISTA             ");
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
					JOptionPane.showMessageDialog(null, "Se imprimio correctamente!");
				} catch (IOException e) {
					System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
				}			
			}
		}
}