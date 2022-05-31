package Caja;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrdenaCategoria extends JFrame {

	public OrdenaCategoria(JComboBox<?> menuCategoria) {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);

		setVisible(true);

		setAlwaysOnTop(true);

		setResizable(false);
		
		dispose();
		
		setUndecorated(true);	

		PanelOdenaCategoria panelordenacategoria = new PanelOdenaCategoria(menuCategoria);

		add(panelordenacategoria);

	}
}

class PanelOdenaCategoria extends JPanel {

	static String[] tituloscategoria = { "CALLE", "SECCION A", "SECCION B", "SECCION C" };
	static Object[][] listacategoria = { { "1", "Postres", "Postres", "Postres" },
			{ "2", "Mayonesas", "Conservas de Carne", "Arroz" }, { "3", "Sopas y Pastas", "Sopas y Pastas", "Harinas" },
			{ "4", "Tomate", "Conservas Vegetales", "Conservas Vegetales" },
			{ "5", "Conservas de Frutas", "Conservas de Pescado", "Conservas de Pescado" },
			{ "6", "Alimentos Animales", "Miel, Queso y Pate", "Frutos Secos" },
			{ "7", "Alimentos Infantiles y Postres", "Alimentos Infantiles y Postres", "Cereales" },
			{ "8", "Cafe", "Colacao y Crema de Cacao", "Infusiones y Azucar" },
			{ "9", "Galletas", "Galletas", "Galletas" }, { "10", "Chocolates", "Dulces", "Dulces" },
			{ "11", "Caramelos y Batidos", "Zumos", "Zumos" }, { "12", "Refrescos", "Refrecos", "Refrescos" },
			{ "13", "Licores", "Licores", "Cerveza" }, { "14", "Vinos", "Vinos", "Vinos" },
			{ "15", "Vasos y Platos Plasticos", "Menaje Cristal", "Ambientadores" },
			{ "16", "Pinturas", "Pinturas", "Insecticidas" }, { "17", "Gel", "Gel", "Bolsas y Aluminio" },
			{ "18", "Desodorentes y Pasta de Pientes", "Desodorantes", "Servilletas" },
			{ "19", "Tinte Pelo", "Jabon y Afeitado", "Rollos de Cocina y Pañuelos" },
			{ "20", "Suavizante Pelo", "Colonias y Lacas", "Papel Higienico" },
			{ "21", "Champu", "Champu", "Papel Higienico" }, { "22", "Pañales", "Compresas", "Detergentes Liquido" },
			{ "23", "Detergente Polvo", "Detergente Polvo y Liquido", "Detergente Polvo y Liquido" },
			{ "24", "Suavizante", "Suavizante", "Accesorios de Hogar y Jardin" },
			{ "25", "Lejias", "Estropajos y Bayetas", "Estropajos y Balletas" },
			{ "26", "Limpiadores(Crital,Muebles,...)", "Calzado, Cera y Desengrasantes", "Cubos y Barreños" },
			{ "27", "Limpiadores Liquidos y Fregasuelos", "Limpiadores Liquidos", "Cepillos" },
			{ "28", "Lavavajillas", "Lavavajillas", "Fregonas" },
			{ "Entrada", "Aceites, Huevos y Legunbres secas", "Vinagres y Roscos", "Pan y Biscottes" },
			{ "Camara", "Yogures y Natas", "Chacinas y Envasados", "Mantequillas y Quesos" },
			{ "Bebidas", "Leches Vegetales", "Leches", "Aguas" },
			{ "Drogueria", "Drogueria", "Drogueria", "Drogueria" }, };

	DefaultTableModel modeloCategoria;
	JTable tblCategoria;
	JScrollPane scrollCategoria;

	public PanelOdenaCategoria(JComboBox<?> menuCategoria) {

		modeloCategoria = new DefaultTableModel(listacategoria, tituloscategoria);
		tblCategoria = new JTable(modeloCategoria);
		scrollCategoria = new JScrollPane(tblCategoria);

		scrollCategoria.setBounds(20, 20, 1185, 660);
		scrollCategoria.setBorder(new LineBorder(Color.BLACK));
		add(scrollCategoria);

		tblCategoria.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas

		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblCategoria.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 20);
		th.setFont(fuente);

		setLayout(null);

		tblCategoria.getColumn(tblCategoria.getModel().getColumnName(0)).setMaxWidth(95);
		tblCategoria.getColumn(tblCategoria.getModel().getColumnName(1)).setMaxWidth(360);
		tblCategoria.getColumn(tblCategoria.getModel().getColumnName(2)).setMaxWidth(360);
		tblCategoria.getColumn(tblCategoria.getModel().getColumnName(3)).setMaxWidth(360);
		tblCategoria.setRowHeight(40);
		tblCategoria.setFont(new java.awt.Font("Tahoma", 0, 25));

		tblCategoria.addMouseListener(new MouseListener() { // al producirse un evento en las columnas con JButton
			public void mouseClicked(MouseEvent e) { // se selecciona la categoria y la manda a JComboBox

				int fila = tblCategoria.rowAtPoint(e.getPoint());
				int columna = tblCategoria.columnAtPoint(e.getPoint());

				if (fila == 0) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A1A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A1B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A1C");
					}
				} else if (fila == 1) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A2A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A2B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A2C");
					}
				} else if (fila == 2) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A3A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A3B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A3C");
					}
				} else if (fila == 3) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A4A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A4B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A4C");
					}
				} else if (fila == 4) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A5A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A5B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A5C");
					}
				} else if (fila == 5) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A6A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A6B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A6C");
					}
				} else if (fila == 6) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A7A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A7B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A7C");
					}
				} else if (fila == 7) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A8A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A8B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A8C");
					}
				} else if (fila == 8) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A9A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A9B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A9C");
					}
				} else if (fila == 9) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("A10A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("A10B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("A10C");
					}
				} else if (fila == 10) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("B11A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("B11B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("B11C");
					}
				} else if (fila == 11) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("B12A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("B12B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("B12C");
					}
				} else if (fila == 12) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("B13A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("B13B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("B13C");
					}
				} else if (fila == 13) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("B14A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("B14B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("B14C");
					}
				} else if (fila == 14) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D15A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D15B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D15C");
					}
				} else if (fila == 15) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D16A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D16B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D16C");
					}
				} else if (fila == 16) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D17A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D17B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D17C");
					}				
				} else if (fila == 17) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D18A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D18B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D18C");
					}
				} else if (fila == 18) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D19A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D19B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D19C");
					}
				} else if (fila == 19) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D20A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D20B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D20C");
					}
				} else if (fila == 20) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D21A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D21B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D21C");
					}
				} else if (fila == 21) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D22A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D22B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D22C");
					}
				} else if (fila == 22) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D23A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D23B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D23C");
					}
				} else if (fila == 23) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D24A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D24B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D24C");
					}
				} else if (fila == 24) {
					if (columna == 2) {
						menuCategoria.setSelectedItem("D25A");
					} else if (columna == 4) {
						menuCategoria.setSelectedItem("D25B");
					} else if (columna == 6) {
						menuCategoria.setSelectedItem("D25C");
					}
				} else if (fila == 25) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D26A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D26B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D26C");
					}
				} else if (fila == 26) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D27A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D27B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D27C");
					}
				} else if (fila == 27) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("D28A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("D28B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("D28C");
					}
				} else if (fila == 28) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("Entrada A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("Entrada B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("Entrada C");
					}
				} else if (fila == 29) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("Cámara A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("Cámara B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("Cámara C");
					}
				} else if (fila == 30) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("Bebida A");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("Bebida B");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("Bebida C");
					}
				} else if (fila == 31) {
					if (columna == 1) {
						menuCategoria.setSelectedItem("Drogueria");
					} else if (columna == 2) {
						menuCategoria.setSelectedItem("Drogueria");
					} else if (columna == 3) {
						menuCategoria.setSelectedItem("Drogueria");
					}
				}

				menuCategoria.requestFocus();

				Window w = SwingUtilities.getWindowAncestor(PanelOdenaCategoria.this);
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
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
	}

}
