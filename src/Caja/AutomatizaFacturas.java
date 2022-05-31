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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class AutomatizaFacturas extends JFrame {

	public AutomatizaFacturas() {

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		int height = pantalla.height;

		int width = pantalla.width;

		setSize(width / 2 + 540, height / 2 + 315);

		setLocationRelativeTo(null);
		
		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		panelAutomatizaFacturas panelautomatizafacturas = new panelAutomatizaFacturas();

		add(panelautomatizafacturas);
	}
}

class panelAutomatizaFacturas extends JPanel {

	JLabel etiquetadireccion;
	JLabel etiquetabuscacodigo;
	JLabel etiquetabuscanombre;
	JLabel etiquetacodigo;
	JLabel etiquetanombreprocucto;
	JLabel etiquetaprecio;
	JLabel etiquetastock;
	JLabel etiquetaporcentaje;
	JLabel etiquetapreciototal;
	JLabel etiquetacategoria;
	JLabel etiquetapreciofactura;
	JLabel etiquetaunidadesfactura;

	JTextField textobuscacodigo;
	JTextField textobuscanombre;
	JTextField textocodigo;
	JTextField textonombreproducto;
	JTextField textoprecio;
	JTextField textostock;
	JTextField textoporcentaje;
	JTextField textopreciototal;
	JTextField textopreciofactura;
	JTextField textounidadesfactura;
	
	JButton botonborrar;
	JButton botonmostrartodo;
	JButton botoningresardatos;
	JButton botonbuscador;
	JButton botonactualizardatos;
	JButton botonguardardatos;
	JButton botoncategoria;
	JButton botonsalir;
	JButton botonlistaimprime;
	JButton botonimportar;

	JComboBox<String> combodistribuidor;
	JComboBox<String> combocategoria;

	String[] titulos = { "Cod.Barras", "Articulo", "Unid.", "Precio", "" };
	DefaultTableModel modeloAutomatiza;
	JTable tblAutomatiza;
	JScrollPane jscrollAutomatiza;

	File fichero;
	
	static String codigodebarras;
	static String codigoingreso, nombreingreso, precioingreso, stockingreso;
	
	private TableRowSorter<TableModel> trsfiltro;
	String filtro;
	
	static Connection con = null;
	
	MetodosAutomatizaFacturas metodosautomatizafacturas = new MetodosAutomatizaFacturas();

	public panelAutomatizaFacturas() {
		
		modeloAutomatiza = new DefaultTableModel(null, titulos);
		tblAutomatiza = new JTable(modeloAutomatiza);
		jscrollAutomatiza = new JScrollPane(tblAutomatiza);
		jscrollAutomatiza.setBorder(new LineBorder(Color.BLACK));

		jscrollAutomatiza.setBounds(20, 285, 800, 390);
		add(jscrollAutomatiza);
		
		tblAutomatiza.getTableHeader().setReorderingAllowed(false); // mantiene las columnas fijas
		
		tblAutomatiza.setGridColor(Color.darkGray);
		setLayout(null);
		setBackground(Color.CYAN);
		
		JTableHeader th; // modifica el tipo de letra de los cabezales de las columnas
		th = tblAutomatiza.getTableHeader();
		Font fuente = new Font(null, Font.PLAIN, 14);
		th.setFont(fuente);
		
		etiquetabuscacodigo = new JLabel("Busca cod.  :");
		etiquetabuscacodigo.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		etiquetabuscacodigo.setBounds(40, 140, 180, 40);
		add(etiquetabuscacodigo);
		
		textobuscacodigo = new JTextField();
		textobuscacodigo.setBorder(new LineBorder(Color.BLACK));
		textobuscacodigo.setFont(new Font("Tahoma", 1, 25));
		textobuscacodigo.setBounds(160, 150, 280, 25);
		add(textobuscacodigo);
		
		textobuscacodigo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblAutomatiza.getModel());
				tblAutomatiza.setRowSorter(trsfiltro);
			}
		});
		
		textobuscacodigo.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (textobuscacodigo.getText()).toUpperCase();
				textobuscacodigo.setText(cadena);
				repaint();
				filtroCodigo();
			}
		});
		
		etiquetabuscanombre = new JLabel("Busca nom. :");
		etiquetabuscanombre.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		etiquetabuscanombre.setBounds(40, 180, 180, 40);
		add(etiquetabuscanombre);
		
		textobuscanombre = new JTextField();
		textobuscanombre.setBorder(new LineBorder(Color.BLACK));
		textobuscanombre.setFont(new Font("Tahoma", 1, 25));
		textobuscanombre.setBounds(160, 190, 280, 25);
		add(textobuscanombre);
		
		textobuscanombre.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent ev) {
			}

			public void keyTyped(KeyEvent ev) {
				trsfiltro = new TableRowSorter<TableModel>(tblAutomatiza.getModel());
				tblAutomatiza.setRowSorter(trsfiltro);
			}
		});

		textobuscanombre.addKeyListener(new KeyAdapter() {

			public void keyReleased(final KeyEvent e) {
				String cadena = (textobuscanombre.getText()).toUpperCase();
				textobuscanombre.setText(cadena);
				repaint();
				filtroNombre();
			}
		});

		botoningresardatos = new JButton();
		botoningresardatos.setBorder(new LineBorder(Color.BLACK));
		botoningresardatos.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-agregar-base-de-datos-24.png"));
		botoningresardatos.setBounds(690, 190, 110, 25);
		add(botoningresardatos);

		botoningresardatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(textocodigo.getText().toString().length() == 0) {		
					
					MetodosAutomatizaFacturas.botonactualizaenable(botonactualizardatos, botonguardardatos);
					
					IngresaDatos ingresodatos = new IngresaDatos(codigoingreso, nombreingreso, precioingreso, stockingreso, combodistribuidor);
					ingresodatos.setVisible(true);
					
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un artículo que no esté en la BD");
				}
			}
		});			
		
		botonbuscador = new JButton();
		botonbuscador.setBorder(new LineBorder(Color.BLACK));
		botonbuscador.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-busca-mas-24.png"));
		botonbuscador.setBounds(690, 150, 110, 25);
		add(botonbuscador);
		
		botonbuscador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textocodigo.getText().toString().length() == 0) {
					
					MetodosAutomatizaFacturas.botonactualizaenable(botonactualizardatos, botonguardardatos);
					
					textocodigo.setText("");
					textonombreproducto.setText("");
					textoprecio.setText("");
					textostock.setText("");
					textoporcentaje.setText("");
					textopreciototal.setText("");
					combocategoria.setSelectedItem("");
				
					Buscador buscador = new Buscador(textocodigo, textonombreproducto, textoprecio, textostock, textoporcentaje, textopreciototal,
						combocategoria, combodistribuidor, codigodebarras);
					buscador.setVisible(true);
					
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un artículo que no esté en la BD");
				}
			}			
		});
		
		botonimportar = new JButton();
		botonimportar.setBorder(new LineBorder(Color.BLACK));
		botonimportar.setBounds(450, 190, 110, 25);
		botonimportar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-txt-24.png"));
		add(botonimportar);

		botonimportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				metodosautomatizafacturas.ImportaFactura(modeloAutomatiza, tblAutomatiza, botonimportar, etiquetadireccion, combodistribuidor);
			}
		});
		
		botonborrar = new JButton();
		botonborrar.setBorder(new LineBorder(Color.BLACK));
		botonborrar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-eliminar-24.png"));
		botonborrar.setBounds(450, 150, 110, 25);
		add(botonborrar);

		botonborrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				metodosautomatizafacturas.BorrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);
			}
		});
		
		botonmostrartodo = new JButton();
		botonmostrartodo.setBorder(new LineBorder(Color.BLACK));
		botonmostrartodo.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-mostrar-fotogramas-32.png"));
		botonmostrartodo.setBounds(570, 150, 110, 25);
		add(botonmostrartodo);

		botonmostrartodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				metodosautomatizafacturas.ReiniciaConBotonMostrar(textobuscacodigo, textobuscanombre, textocodigo, textonombreproducto, 
					textoprecio, textostock, textoporcentaje, textopreciototal, combocategoria, botonactualizardatos, botonguardardatos);
			}
		});
		
		String opciones[] = new String[] { "Ayala", "Barea" };
		combodistribuidor = new JComboBox<>();
		combodistribuidor.setBorder(new LineBorder(Color.BLACK));
		combodistribuidor.setModel(new DefaultComboBoxModel<>(opciones));
		combodistribuidor.setFont(new java.awt.Font("Dialog", 0, 18));
		combodistribuidor.setBounds(570, 190, 110, 25);
		add(combodistribuidor);
		
		combodistribuidor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				metodosautomatizafacturas.MostrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);
			}
		});

		combodistribuidor.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {

				try {
					metodosautomatizafacturas.MostrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		botonactualizardatos = new JButton("Actualizar");
		botonactualizardatos.setBorder(new LineBorder(Color.BLACK));
		botonactualizardatos.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-actualizar-24.png"));
		botonactualizardatos.setBounds(850, 155, 140, 50);
		botonactualizardatos.setEnabled(false);
		add(botonactualizardatos);
		
		botonactualizardatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				metodosautomatizafacturas.ActualizaDatos(combodistribuidor, textopreciofactura, textoprecio, textounidadesfactura, textostock,
					textoporcentaje, textopreciototal);
				
				MetodosAutomatizaFacturas.botonguardaenable(botonactualizardatos, botonguardardatos);
			}
		});
		
		botonguardardatos = new JButton("Guardar");
		botonguardardatos.setBorder(new LineBorder(Color.BLACK));
		botonguardardatos.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-guardar-30.png"));
		botonguardardatos.setBounds(1045, 155, 140, 50);
		botonguardardatos.setEnabled(false);
		add(botonguardardatos);
		
		botonguardardatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				if(combocategoria.getSelectedItem().toString().length() != 0) {
					metodosautomatizafacturas.GuardaDatosActualizados(textocodigo, textonombreproducto, textoprecio, textostock, textoporcentaje, 
						textopreciototal, combocategoria, combodistribuidor);
						
					metodosautomatizafacturas.GuardarTablaFacturas(textonombreproducto, textopreciototal);
					
					metodosautomatizafacturas.BuscaDatosActualizadosWeb(textocodigo);
					
					metodosautomatizafacturas.MostrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);						
						
					MetodosAutomatizaFacturas.botonactualizaenable(botonactualizardatos, botonguardardatos);					
				}else {
					combocategoria.setSelectedItem("");
					metodosautomatizafacturas.GuardaDatosActualizados(textocodigo, textonombreproducto, textoprecio, textostock, textoporcentaje, 
						textopreciototal, combocategoria, combodistribuidor);
						
					metodosautomatizafacturas.GuardarTablaFacturas(textonombreproducto, textopreciototal);
					
					metodosautomatizafacturas.BuscaDatosActualizadosWeb(textocodigo);
					
					metodosautomatizafacturas.MostrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);
					
					MetodosAutomatizaFacturas.botonactualizaenable(botonactualizardatos, botonguardardatos);
				}			
			}
		});
		
		etiquetacodigo = new JLabel("Código Barras");
		etiquetacodigo.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetacodigo.setBounds(70, 20, 200, 40);
		add(etiquetacodigo);

		textocodigo = new JTextField();
		textocodigo.setBorder(new LineBorder(Color.BLACK));
		textocodigo.setHorizontalAlignment(JTextField.CENTER);
		textocodigo.setFont(new Font("Tahoma", 1, 30));
		textocodigo.setBounds(30, 60, 270, 50);
		textocodigo.setEditable(false);
		add(textocodigo);
		
		etiquetanombreprocucto = new JLabel("Nombre del producto");
		etiquetanombreprocucto.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetanombreprocucto.setBounds(580, 20, 900, 40);
		add(etiquetanombreprocucto);

		textonombreproducto = new JTextField();
		textonombreproducto.setBorder(new LineBorder(Color.BLACK));
		textonombreproducto.setFont(new Font("Tahoma", 1, 35));
		textonombreproducto.setBounds(310, 60, 880, 50);
		add(textonombreproducto);
		
		etiquetaprecio = new JLabel("Precio BD");
		etiquetaprecio.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetaprecio.setBounds(1040, 205, 200, 40);
		add(etiquetaprecio);
		
		textoprecio = new JTextField();
		textoprecio.setBorder(new LineBorder(Color.BLACK));
		textoprecio.setHorizontalAlignment(JTextField.CENTER);
		textoprecio.setFont(new Font("Tahoma", 1, 35));
		textoprecio.setBounds(1045, 245, 140, 50);
		add(textoprecio);
		
		etiquetapreciofactura = new JLabel("Precio fac.");
		etiquetapreciofactura.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetapreciofactura.setBounds(850, 205, 200, 40);
		add(etiquetapreciofactura);
		
		textopreciofactura = new JTextField();
		textopreciofactura.setBorder(new LineBorder(Color.BLACK));
		textopreciofactura.setHorizontalAlignment(JTextField.CENTER);
		textopreciofactura.setFont(new Font("Tahoma", 1, 35));
		textopreciofactura.setBounds(850, 245, 140, 50);
		add(textopreciofactura);	
		
		etiquetastock = new JLabel("Stock");
		etiquetastock.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetastock.setBounds(1070, 295, 200, 40);
		add(etiquetastock);
		
		textostock = new JTextField();
		textostock.setBorder(new LineBorder(Color.BLACK));
		textostock.setHorizontalAlignment(JTextField.CENTER);
		textostock.setFont(new Font("Tahoma", 1, 35));
		textostock.setBounds(1045, 335, 140, 50);
		add(textostock);
		
		etiquetaunidadesfactura = new JLabel("Unidades");
		etiquetaunidadesfactura.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetaunidadesfactura.setBounds(850, 295, 200, 40);
		add(etiquetaunidadesfactura);
		
		textounidadesfactura = new JTextField();
		textounidadesfactura.setBorder(new LineBorder(Color.BLACK));
		textounidadesfactura.setHorizontalAlignment(JTextField.CENTER);
		textounidadesfactura.setFont(new Font("Tahoma", 1, 35));
		textounidadesfactura.setBounds(850, 335, 140, 50);
		add(textounidadesfactura);
		
		etiquetaporcentaje = new JLabel("%");
		etiquetaporcentaje.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetaporcentaje.setBounds(1105, 385, 200, 40);
		add(etiquetaporcentaje);
		
		textoporcentaje = new JTextField();
		textoporcentaje.setBorder(new LineBorder(Color.BLACK));
		textoporcentaje.setHorizontalAlignment(JTextField.CENTER);
		textoporcentaje.setFont(new Font("Tahoma", 1, 35));
		textoporcentaje.setBounds(1045, 425, 140, 50);
		add(textoporcentaje);
		
		textoporcentaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				metodosautomatizafacturas.CalculaTotal(textoprecio, textoporcentaje, textopreciototal);				
			}			
		});
		
		botonlistaimprime = new JButton();
		botonlistaimprime.setBorder(new LineBorder(Color.BLACK));
		botonlistaimprime.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-lista-resumen-48.png"));
		botonlistaimprime.setBounds(850, 425, 140, 50);
		add(botonlistaimprime);
		
		botonlistaimprime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ImprimeFactura imprimefactura = new ImprimeFactura();
				imprimefactura.setVisible(true);
			}
		});
		
		etiquetapreciototal = new JLabel("PVP");
		etiquetapreciototal.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetapreciototal.setBounds(1085, 475, 200, 40);
		add(etiquetapreciototal);
		
		textopreciototal = new JTextField();
		textopreciototal.setBorder(new LineBorder(Color.BLACK));
		textopreciototal.setHorizontalAlignment(JTextField.CENTER);
		textopreciototal.setFont(new Font("Tahoma", 1, 35));
		textopreciototal.setBounds(1045, 515, 140, 50);
		add(textopreciototal);	
		
		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-48.png"));
		botonsalir.setBounds(850, 515, 140, 50);
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Window w = SwingUtilities.getWindowAncestor(panelAutomatizaFacturas.this);
				w.dispose();
			}
		});
		
		etiquetacategoria = new JLabel("Categoria");
		etiquetacategoria.setFont(new java.awt.Font(null, Font.PLAIN, 30));
		etiquetacategoria.setBounds(1050, 565, 200, 40);
		add(etiquetacategoria);
		
		botoncategoria = new JButton();
		botoncategoria.setBorder(new LineBorder(Color.BLACK));
		botoncategoria.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-48.png"));
		botoncategoria.setBounds(850, 605, 140, 50);
		add(botoncategoria);
		
		botoncategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OrdenaCategoria ordenacategoria = new OrdenaCategoria(combocategoria);
				ordenacategoria.setVisible(true);
			}
		});
		
		combocategoria = new JComboBox<String>();
		combocategoria.setBorder(new LineBorder(Color.BLACK));
		combocategoria.setFont(new Font("Tahoma", 1, 35));
		combocategoria.setBounds(1045, 605, 140, 50);
		add(combocategoria);
		ArrayList<String> lista = new ArrayList<String>();
		lista = MetodosArticulos.LlenarCombo();
		
		for (int i = 0; i < lista.size(); i++) {
			if (combocategoria.getSelectedIndex() == 0) {
				combocategoria.setSelectedItem("");
			}
			AutoCompleteDecorator.decorate(combocategoria); // método para autocompletar con la columna
			combocategoria.addItem(lista.get(i));
		}

		etiquetadireccion = new JLabel("Direccion del archivo");
		etiquetadireccion.setFont(new java.awt.Font(null, Font.PLAIN, 20));
		etiquetadireccion.setBounds(40, 240, 700, 30);
		add(etiquetadireccion);
		
		metodosautomatizafacturas.MostrarBaseDeDatos(modeloAutomatiza, tblAutomatiza, combodistribuidor);
		tblAutomatiza.addMouseListener(new BaseDatosMouse());
	}

	class BaseDatosMouse implements MouseListener {
		public void mouseClicked(MouseEvent e) {
						
			int seleccionfila = tblAutomatiza.rowAtPoint(e.getPoint());
			
			codigoingreso = tblAutomatiza.getValueAt(seleccionfila, 0).toString();
			nombreingreso = tblAutomatiza.getValueAt(seleccionfila, 1).toString();
			stockingreso = tblAutomatiza.getValueAt(seleccionfila, 2).toString();
			precioingreso = tblAutomatiza.getValueAt(seleccionfila, 3).toString();
			
			textocodigo.setText("");
			textonombreproducto.setText("");
			textoprecio.setText("");
			textostock.setText("");
			textoporcentaje.setText("");
			textopreciototal.setText("");
			textopreciofactura.setText("");
			textounidadesfactura.setText("");	
			combocategoria.setSelectedItem("");
			
			metodosautomatizafacturas.botonactualizaenable(botonactualizardatos, botonguardardatos);
			
			String Codigo;

			Codigo = tblAutomatiza.getValueAt(seleccionfila, 0).toString();
				
			MetodosAutomatizaFacturas.MostrarProductoSeleccionado(modeloAutomatiza, tblAutomatiza, Codigo, textocodigo, 
				textonombreproducto, textoprecio, textostock, textoporcentaje, textopreciototal, combocategoria, combodistribuidor,
				botonactualizardatos, botonguardardatos);
				
			String preciofactura, unidadesmodifica;
					
			codigodebarras = tblAutomatiza.getValueAt(seleccionfila, 0).toString();		
			unidadesmodifica = tblAutomatiza.getValueAt(seleccionfila, 2).toString();	
			preciofactura = tblAutomatiza.getValueAt(seleccionfila, 3).toString();
			
			textopreciofactura.setText(preciofactura); 
			textounidadesfactura.setText(unidadesmodifica);				
				
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
	
	// metodos para la barra buscadora por nombre y por articulos
	public void filtroNombre() {
		filtro = textobuscanombre.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(textobuscanombre.getText(), 1));
	}
	
	public void filtroCodigo() {
		filtro = textobuscacodigo.getText();
		trsfiltro.setRowFilter(RowFilter.regexFilter(textobuscacodigo.getText(), 0));
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(0, 0, 1222, 698);
		g.drawRect(20, 20, 1180, 100);
		g.drawRect(835, 135, 365, 540);
		g.drawRect(20, 135, 800, 90);
		g.drawRect(20, 240, 800, 30);
	}
	
}