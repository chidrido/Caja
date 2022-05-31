package Caja;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class VentanaFruta extends JFrame {

	ArrayList<Object[]> listaCompleta = new ArrayList<Object[]>();

	public VentanaFruta() {

		setBounds(640, 60, 415, 605);

		setAlwaysOnTop(true); // hace que la ventana quede por encima de las demás

		setUndecorated(true);

		setLocationRelativeTo(null);

		setResizable(false); // no se puede cambiar tamaño de la ventana fruta

		setVisible(true);

		PanelVentanaFruta PanelVentanaFruta = new PanelVentanaFruta();

		add(PanelVentanaFruta);
	}
}

class PanelVentanaFruta extends JPanel {

	static String[] titulos = { "Fruta", "Cantidad", "" };
	static Object[][] frutas = { { "Platano", "", new Boolean(false) }, 
								 { "Pera conferencia", "", new Boolean(false) },
								 { "Pera agua", "", new Boolean(false) },
								 { "Uva blanca", "", new Boolean(false) },
								 { "Uva negra", "", new Boolean(false) }, 
								 { "Tomate pera", "", new Boolean(false) },
								 { "Tomate pinton", "", new Boolean(false) }, 
								 { "Tomate rama", "", new Boolean(false) },
								 { "Tomate rojo", "", new Boolean(false) }, 
								 { "Cebolla dulce", "", new Boolean(false) },
								 { "Cebolla", "", new Boolean(false) },
								 { "Cebolla morada", "", new Boolean(false) },
								 { "Cebolleta", "", new Boolean(false) }, 
								 { "Patata vieja", "", new Boolean(false) },
								 { "Patata agria", "", new Boolean(false) }, 
								 { "Patata nueva", "", new Boolean(false) },
								 { "Patata arena", "", new Boolean(false) }, 
								 { "Patata 3K", "", new Boolean(false) },
								 { "Ajo", "", new Boolean(false) }, 
								 { "Paraguayo", "", new Boolean(false) },
								 { "Melocoton", "", new Boolean(false) },
								 { "Pimiento freir", "", new Boolean(false) },
								 { "Pimiento asar rojo", "", new Boolean(false) },
								 { "Pimiento asar verde", "", new Boolean(false) },
								 { "Fresas", "", new Boolean(false) }, 
								 { "Picotas", "", new Boolean(false) },
								 { "Damascos", "", new Boolean(false) },
								 { "Manzana granel", "", new Boolean(false) },
								 { "Manzana roja", "", new Boolean(false) },
								 { "Manzana Perlin", "", new Boolean(false) },
								 { "Manzana Marlene", "", new Boolean(false) }, 
								 { "Manzana Kanzi", "", new Boolean(false) },
								 { "Royal Gala", "", new Boolean(false) }, 
								 { "Acelgas", "", new Boolean(false) },
								 { "Perejil", "", new Boolean(false) }, 
								 { "Hierbabuena", "", new Boolean(false) },
								 { "Puerro", "", new Boolean(false) }, 
								 { "Apio", "", new Boolean(false) },
								 { "Nabo", "", new Boolean(false) },
								 { "Zanahoria", "", new Boolean(false) },
								 { "Cogollo Tudela", "", new Boolean(false) }, 
								 { "Endivia", "", new Boolean(false) },
								 { "Brocoli", "", new Boolean(false) }, 
								 { "Calabacin", "", new Boolean(false) },
								 { "Berenjena", "", new Boolean(false) },
								 { "Limon", "", new Boolean(false) },
								 { "Alcachofa", "", new Boolean(false) },
								 { "Naranja zumo", "", new Boolean(false) }, 
								 { "Naranja Washi", "", new Boolean(false) },
								 { "Mandarina", "", new Boolean(false) }, 
								 { "Romanilla", "", new Boolean(false) },
								 { "Bandeja lechuga", "", new Boolean(false) }, 
								 { "Kiwi verde", "", new Boolean(false) },
								 { "Kiwi oro", "", new Boolean(false) },
								 { "Nispero", "", new Boolean(false) },
								 { "Mango", "", new Boolean(false) }, 
								 { "Ciruela", "", new Boolean(false) },
								 { "Nectarina", "", new Boolean(false) }, 
								 { "Col", "", new Boolean(false) },
								 { "Chirimolla", "", new Boolean(false) }, 
								 { "Pepino", "", new Boolean(false) },
								 { "Melon verde", "", new Boolean(false) }, 
								 { "Melon Galia", "", new Boolean(false) },
								 { "Sandia", "", new Boolean(false) }, 
								 { "Kaki", "", new Boolean(false) },
								 { "Persimo", "", new Boolean(false) },
								 { "Batata cocida", "", new Boolean(false) },
								 { "Batata cruda", "", new Boolean(false) }, 
								 { "Habichuela", "", new Boolean(false) },
								 { "Datiles", "", new Boolean(false) }, 
								 { "Castañas", "", new Boolean(false) }, };
	
	static DefaultTableModel modeloFruta;
	static JTable tblFruta;
	JScrollPane jscrollFruta;
	static Connection con = null;

	JButton botonimprimir;
	JButton botonsalir;

	public PanelVentanaFruta() {
		modeloFruta = new DefaultTableModel(frutas, titulos);
		tblFruta = new JTable(modeloFruta);
		jscrollFruta = new JScrollPane(tblFruta);

		jscrollFruta.setBounds(10, 10, 395, 520);
		jscrollFruta.setBorder(new LineBorder(Color.BLACK));
		tblFruta.setBackground(Color.CYAN);
		add(jscrollFruta);

		tblFruta.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		setLayout(null);                        // asignamos un layout nulo y difinimos los campos y etiquetas
		setBackground(Color.CYAN);              // setBackground(Color.CYAN);

		tblFruta.getColumn(tblFruta.getModel().getColumnName(0)).setMaxWidth(350);
		tblFruta.getColumn(tblFruta.getModel().getColumnName(1)).setMaxWidth(70);
		tblFruta.getColumn(tblFruta.getModel().getColumnName(2)).setMaxWidth(58);
		tblFruta.setRowHeight(30);
		tblFruta.setFont(new java.awt.Font("Tahoma", 0, 25));

		CambioColorCaja cambiocolor = new CambioColorCaja();
		tblFruta.setDefaultRenderer(Object.class, cambiocolor); // pinta la celda señalada de color gros oscuro
		tblFruta.setGridColor(Color.BLACK); // cambia de color las lineas de la JTable

		botonimprimir = new JButton("Imprimir"); // crea boton para imprimir
		botonimprimir.setBorder(new LineBorder(Color.BLACK));
		botonimprimir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-imprimir-32.png"));
		botonimprimir.setBounds(10, 550, 185, 35);
		add(botonimprimir);

		botonimprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				guardaTablafruta(); // guarda JTable de fruta en txt
				Imprimir.Imprimirfruta(); // imprime el archivo txt guardado
				Window w = SwingUtilities.getWindowAncestor(PanelVentanaFruta.this);
				w.dispose();
			}
		});
		
		botonsalir = new JButton("Salir"); // crea boton para imprimir
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-30.png"));
		botonsalir.setBounds(220, 550, 185, 35);
		add(botonsalir);

		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Window w = SwingUtilities.getWindowAncestor(PanelVentanaFruta.this);
				w.dispose();
			}
		});

		TableColumn agregarColumnCaja = tblFruta.getColumnModel().getColumn(2);
		agregarColumnCaja.setCellEditor(tblFruta.getDefaultEditor(Boolean.class));
		agregarColumnCaja.setCellRenderer(tblFruta.getDefaultRenderer(Boolean.class));

	}

	// método que guarda en archivo txt la tbla de la fruta
	private void guardaTablafruta() {
		try {

			String direccion = "C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\ticket_fruta.txt";
			BufferedWriter bfw = new BufferedWriter(new FileWriter(direccion));

			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("               LISTA DE FRUTA             ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();
			bfw.write("                                          ");
			bfw.newLine();

			for (int i = 0; i < tblFruta.getRowCount(); i++) {

				if (tblFruta.getValueAt(i, 2).equals(true)) {
					bfw.write((String) (tblFruta.getValueAt(i, 0))); // solo guarda en txt los productos seleccionados
					bfw.write("       ");
					bfw.write((String) (tblFruta.getValueAt(i, 1)));
					bfw.newLine(); // inserta nueva linea.
				}
			}

			bfw.close(); // cierra archivo!
			System.out.println("El archivo fue salvado correctamente!");
		} catch (IOException e) {
			System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
		}
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 414, 604);
	}

}
