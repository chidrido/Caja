package Caja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class IngresaDatos extends JFrame{

	public IngresaDatos(String codigoingreso, String nombreingreso, String precioingreso, String stockingreso, JComboBox<?> combodistribuidor) {

		setBounds(100, 220, 990, 450);
		
		setAlwaysOnTop(true); // hace que la ventana quede por encima de las demás

		setLocationRelativeTo(null);
		
		setUndecorated(true);

		setVisible(true);

		setResizable(false);

		PanelIngresaDatos panelingresadatos = new PanelIngresaDatos(codigoingreso, nombreingreso, precioingreso, stockingreso, combodistribuidor);

		add(panelingresadatos);
	}
}

class PanelIngresaDatos extends JPanel{
	
	JLabel etiquetatitulo;
	JLabel etiquetacodigo;
	JLabel etiquetanombre;
	JLabel etiquetaprecio;
	JLabel etiquetaporcentaje;
	JLabel etiquetaPVP;
	JLabel etiquetastock;
	JLabel etiquetastockmin;
	JLabel etiquetacategoria;
	
	JTextField textcodigo;
	JTextField textnombreproducto;
	JTextField textprecio;
	JTextField textporcentaje;
	JTextField textPVP;
	JTextField textstock;
	JTextField textstockmin;
	JTextField textcategoria;
	
	JButton botonagregar;
	JButton botonsalir;
	JButton botoncategoria;
	
	JComboBox <String> combocategoria;
	
	public PanelIngresaDatos(String codigoingreso, String nombreingreso, String precioingreso, String stockingreso, JComboBox<?> combodistribuidor) {	
		
		setLayout(null);
		
		setBackground(Color.CYAN);
		
		etiquetatitulo = new JLabel("Ingresar datos");
		etiquetatitulo.setFont(new java.awt.Font(null, Font.PLAIN, 50));
		etiquetatitulo.setHorizontalAlignment(JTextField.CENTER);
		etiquetatitulo.setBounds(320, 0, 400, 70);
		add(etiquetatitulo);
		
		etiquetacodigo = new JLabel("Codigo");
		etiquetacodigo.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetacodigo.setBounds(20, 90, 150, 45);
		add(etiquetacodigo);
		
		textcodigo = new JTextField();
		textcodigo.setBorder(new LineBorder(Color.BLACK));
		textcodigo.setFont(new Font("Tahoma", 1, 40));
		textcodigo.setBounds(20, 140, 350, 50);
		add(textcodigo);
		
		textcodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textnombreproducto.requestFocus();
			}			
		});
		
		etiquetanombre = new JLabel("Nombre del producto");
		etiquetanombre.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetanombre.setBounds(20, 200, 400, 45);
		add(etiquetanombre);
		
		textnombreproducto = new JTextField();
		textnombreproducto.setBorder(new LineBorder(Color.BLACK));
		textnombreproducto.setFont(new Font("Tahoma", 1, 40));
		textnombreproducto.setBounds(20, 250, 950, 50);
		add(textnombreproducto);
		
		textnombreproducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textprecio.requestFocus();
			}			
		});
		
		etiquetaprecio = new JLabel("Precio");
		etiquetaprecio.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetaprecio.setBounds(25, 310, 100, 45);
		add(etiquetaprecio);
		
		textprecio = new JTextField();
		textprecio.setBorder(new LineBorder(Color.BLACK));
		textprecio.setFont(new Font("Tahoma", 1, 40));
		textprecio.setHorizontalAlignment(JTextField.CENTER);
		textprecio.setBounds(20, 360, 120, 50);
		add(textprecio);
		
		textprecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textporcentaje.requestFocus();
			}
		});
		
		etiquetaporcentaje = new JLabel("%");
		etiquetaporcentaje.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetaporcentaje.setBounds(180, 310, 100, 45);
		add(etiquetaporcentaje);
		
		textporcentaje = new JTextField();
		textporcentaje.setBorder(new LineBorder(Color.BLACK));
		textporcentaje.setFont(new Font("Tahoma", 1, 40));
		textporcentaje.setHorizontalAlignment(JTextField.CENTER);
		textporcentaje.setBounds(160, 360, 80, 50);
		add(textporcentaje);
		
		textporcentaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
				simbolo.setDecimalSeparator('.');
				DecimalFormat formato = new DecimalFormat("###0.00", simbolo);
				
				if(textporcentaje.getText().toString().length() != 0) {
				
					Float operacion, operacion_dos_decimales;
					String resultado;
					Float extraePorcentaje = Float.parseFloat(textporcentaje.getText());
					Float extraePrecio = Float.parseFloat(textprecio.getText());
					operacion = extraePrecio + (extraePrecio * extraePorcentaje) / 100;
					resultado = formato.format(operacion);
					textPVP.setText(resultado);
				}
				
				textPVP.requestFocus();
			}
		});
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textporcentaje.requestFocus();
			}
		}); // inicia foco
		
		etiquetaPVP = new JLabel("PVP");
		etiquetaPVP.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetaPVP.setBounds(290, 310, 120, 45);
		add(etiquetaPVP);
		
		textPVP = new JTextField();
		textPVP.setBorder(new LineBorder(Color.BLACK));
		textPVP.setFont(new Font("Tahoma", 1, 40));
		textPVP.setHorizontalAlignment(JTextField.CENTER);
		textPVP.setBounds(260, 360, 120, 50);
		add(textPVP);
		
		textPVP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textstock.requestFocus();
			}
		});
		
		etiquetastock = new JLabel("Stock");
		etiquetastock.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetastock.setBounds(405, 310, 100, 45);
		add(etiquetastock);
		
		textstock = new JTextField();
		textstock.setBorder(new LineBorder(Color.BLACK));
		textstock.setFont(new Font("Tahoma", 1, 40));
		textstock.setHorizontalAlignment(JTextField.CENTER);
		textstock.setBounds(400, 360, 100, 50);
		add(textstock);
		
		textstock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textstockmin.requestFocus();
			}
		});
		
		etiquetastockmin = new JLabel("St.min");
		etiquetastockmin.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetastockmin.setBounds(520, 310, 150, 45);
		add(etiquetastockmin);
		
		textstockmin = new JTextField();
		textstockmin.setBorder(new LineBorder(Color.BLACK));
		textstockmin.setFont(new Font("Tahoma", 1, 40));
		textstockmin.setHorizontalAlignment(JTextField.CENTER);
		textstockmin.setBounds(520, 360, 100, 50);
		add(textstockmin);
		
		textstockmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				combocategoria.requestFocus();
			}
		});
		
		etiquetacategoria = new JLabel("Categoría");
		etiquetacategoria.setFont(new java.awt.Font(null, Font.PLAIN, 35));
		etiquetacategoria.setBounds(640, 310, 160, 45);
		add(etiquetacategoria);
		
		combocategoria = new JComboBox<>();
		combocategoria.setBorder(new LineBorder(Color.BLACK));
		combocategoria.setFont(new java.awt.Font("Dialog", 0, 35));
		combocategoria.setBounds(640, 360, 160, 50);
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
		
		combocategoria.getEditor().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				botonagregar.requestFocus();
			}
		});
		
		botonagregar = new JButton("Agregar");
		botonagregar.setBorder(new LineBorder(Color.BLACK));
		botonagregar.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-actualizar-24.png"));
		botonagregar.setBounds(820, 360, 150, 50);
		add(botonagregar);	
		
		botonagregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MetodosArticulos metodosarticulos = new MetodosArticulos();
				try {
					metodosarticulos.Guardar(null, textcodigo, textnombreproducto, textprecio, textstock, combodistribuidor,
							textporcentaje, textPVP, textstock, textstockmin, combocategoria);
					
					Window w = SwingUtilities.getWindowAncestor(PanelIngresaDatos.this);
					w.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}			
		});
		
		botonagregar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						MetodosArticulos metodosarticulos = new MetodosArticulos();
						metodosarticulos.Guardar(evt, textcodigo, textnombreproducto, textprecio, textstock, combodistribuidor,
								textporcentaje, textPVP, textstock, textstockmin, combocategoria);
						
						Window w = SwingUtilities.getWindowAncestor(PanelIngresaDatos.this);
						w.dispose();

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			public void keyReleased(KeyEvent evt) {
			}
			public void keyTyped(KeyEvent evt) {
			}
		});
		
		botoncategoria = new JButton();
		botoncategoria.setBorder(new LineBorder(Color.BLACK));
		botoncategoria.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-flechas-de-ordenar-48.png"));
		botoncategoria.setBounds(690, 150, 130, 50);
		add(botoncategoria);
		
		botoncategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				OrdenaCategoria ordenacategoria = new OrdenaCategoria(combocategoria);
				ordenacategoria.setVisible(true);
			}
		});
		
		botonsalir = new JButton();
		botonsalir.setBorder(new LineBorder(Color.BLACK));
		botonsalir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-volver-48.png"));
		botonsalir.setBounds(840, 150, 130, 50);
		add(botonsalir);
		
		botonsalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				Window w = SwingUtilities.getWindowAncestor(PanelIngresaDatos.this);
				w.dispose();
			}
		});
		
		textcodigo.setText(codigoingreso);
		textnombreproducto.setText(nombreingreso);
		textprecio.setText(precioingreso);
		textstock.setText(stockingreso);
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawRect(10, 80, 970, 360);
		g.drawRect(10, 10, 970, 60);
		g.drawRect(0, 0, 989, 449);
	}
	
	public void GuardarDatosNuevos() {
		
		
		
		
		
		
	}
}
