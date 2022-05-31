package Caja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class VentanaTicket extends JFrame {

	public VentanaTicket() {

		setBounds(650, 100, 520, 290);

		setAlwaysOnTop(true);

		setUndecorated(true);
		
		setResizable(false);

		setVisible(true);

		PanelTicket panelticket = new PanelTicket();

		add(panelticket);

	}
}

class PanelTicket extends JPanel {

	JLabel total;
	JLabel entregado;
	JLabel cambio;

	JTextField txttotal;
	JTextField txtentregado;
	JTextField txtcambio;

	JButton botonimprimir;
	JButton botonotroarticulo;
	JButton botonagenda;

	PanelCajero panelcajero = new PanelCajero();
	MetodosCaja metodoscaja = new MetodosCaja();

	static String entrega;
	static String devuelve;
	
	static String nombreagenda;
	static String telefonoagenda;
	static String direccionagenda;

	public PanelTicket() {

		setLayout(null);
		setBackground(Color.CYAN);

		total = new JLabel("TOTAL");
		total.setBounds(40, 30, 120, 30);
		total.setFont(new java.awt.Font(null, Font.PLAIN, 32));
		add(total);
		
		txttotal = new JTextField();
		txttotal.setBorder(new LineBorder(Color.BLACK));
		txttotal.setBounds(260, 30, 220, 35);
		txttotal.setEditable(false);
		txttotal.setFont(new Font("Tahoma", 1, 30));
		txttotal.setHorizontalAlignment(JTextField.CENTER);
		add(txttotal);

		entregado = new JLabel("ENTREGADO");
		entregado.setBounds(40, 80, 220, 30);
		entregado.setFont(new java.awt.Font(null, Font.PLAIN, 32));
		add(entregado);
		
		txtentregado = new JTextField();
		txtentregado.setBorder(new LineBorder(Color.BLACK));
		txtentregado.setBounds(260, 80, 220, 35);
		txtentregado.setFont(new Font("Tahoma", 1, 30));
		txtentregado.setHorizontalAlignment(JTextField.CENTER);
		add(txtentregado);

		txtentregado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				EntregaCuenta();
			}
		});

		cambio = new JLabel("CAMBIO");
		cambio.setBounds(40, 130, 180, 30);
		cambio.setFont(new java.awt.Font(null, Font.PLAIN, 32));
		add(cambio);
		
		txtcambio = new JTextField();
		txtcambio.setBorder(new LineBorder(Color.BLACK));
		txtcambio.setBounds(260, 130, 220, 35);
		txtcambio.setFont(new Font("Tahoma", 1, 30));
		txtcambio.setHorizontalAlignment(JTextField.CENTER);
		add(txtcambio);

		botonimprimir = new JButton();
		botonimprimir.setBorder(new LineBorder(Color.BLACK));
		botonimprimir.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-imprimir-52.png"));
		botonimprimir.setBounds(35, 200, 135, 60);
		add(botonimprimir);

		botonimprimir.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (txtcambio.getText().length() != 0) {
						//System.out.println(txtcambio.getText());
						guardaEntregadoTotalDevolucion();

						Imprimir.ImprimirTicket();

						Window w = SwingUtilities.getWindowAncestor(PanelTicket.this);
						w.dispose();
					}
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});

		botonimprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtcambio.getText().length() != 0) {
					//System.out.println(txtcambio.getText());
					guardaEntregadoTotalDevolucion();

					Imprimir.ImprimirTicket();

					Window w = SwingUtilities.getWindowAncestor(PanelTicket.this);
					w.dispose();
				}
			}
		});

		botonotroarticulo = new JButton();
		botonotroarticulo.setBorder(new LineBorder(Color.BLACK));
		botonotroarticulo.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-agregar-a-carrito-de-compras-50.png"));
		botonotroarticulo.setBounds(190, 200, 135, 60);
		add(botonotroarticulo);

		botonotroarticulo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					Window w = SwingUtilities.getWindowAncestor(PanelTicket.this);
					w.dispose();
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});

		botonotroarticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Window w = SwingUtilities.getWindowAncestor(PanelTicket.this);
				w.dispose();
			}
		});
		
		botonagenda = new JButton();
		botonagenda.setBorder(new LineBorder(Color.BLACK));
		botonagenda.setIcon(new ImageIcon("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\icons8-directorio-50.png"));
		botonagenda.setBounds(345, 200, 135, 60);
		botonagenda.setEnabled(true);
		add(botonagenda);
		
		botonagenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AgregarDireccion agregardireccion = new AgregarDireccion();
				agregardireccion.setVisible(true);
				botonagenda.setEnabled(false);				
			}
		});

		ImporteCuenta();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				txtentregado.requestFocus();
			}
		});		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(0, 0, 519, 289);
	}

	// método muestra la cuneta total en JTextfield
	public void ImporteCuenta() {
		String cuenta = panelcajero.DevuelveCuentaTotal();
		txttotal.setText(cuenta);
	}

	// método que devuelve la cantidad entregada
	public String Entrega() {

		return entrega = txtentregado.getText();
	}

	// método que devuelve la cantidad entregada
	public String Devuelve() {

		return devuelve = txtcambio.getText();
	}

	// método que calcula el total a devolver en base a lo entregado y el total
	public void EntregaCuenta() {

		try {

			DecimalFormatSymbols simbolo = new DecimalFormatSymbols(); // da formato de dos decimales con punto
			simbolo.setDecimalSeparator('.');
			DecimalFormat formato = new DecimalFormat("###0.00", simbolo);

			String entregado = txtentregado.getText();
			Float entregadodecimales = Float.parseFloat(entregado);

			String total0 = formato.format(entregadodecimales);
			entrega = total0;

			String cuenta = panelcajero.DevuelveCuentaTotal();

			Float numentrega = Float.parseFloat(entrega);
			Float numcuenta = Float.parseFloat(cuenta);

			if (numentrega < numcuenta) {
				txtentregado.setText("");
				txtentregado.requestFocus();
			} else {
				Float numtotal = numentrega - numcuenta;

				String totaldevuelve = formato.format(numtotal);

				txtcambio.setText(totaldevuelve);
				botonimprimir.requestFocus();
			}
		} catch (Exception e) {
		}
	}

	// guarda en archivo txt los parámetros entrega, total y cambio
	public void guardaEntregadoTotalDevolucion() {
		try {
			String totalcuen = txttotal.getText();
			int cantidadtotal = panelcajero.CuentaArticulos();
			String devuelvecuenta = Devuelve();

			Calendar calendario = Calendar.getInstance();

			int hora = calendario.get(Calendar.HOUR_OF_DAY);
			int minutos = calendario.get(Calendar.MINUTE);

			String dia = Integer.toString(calendario.get(Calendar.DATE));
			String mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
			String annio = Integer.toString(calendario.get(Calendar.YEAR));

			File continuar = new File("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\ticket_articulos.txt");
			FileWriter fw = new FileWriter(continuar.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(!botonagenda.isEnabled()) {
				PanelAgregarDireccion agregardireccion = new PanelAgregarDireccion();
				nombreagenda = agregardireccion.DevuelveNombre();
				telefonoagenda = agregardireccion.DevuelveTelefono();
				direccionagenda = agregardireccion.DevuelveDireccion();
			}else {
				nombreagenda = "";
				telefonoagenda = "";
				direccionagenda = "";
			}

			bw.write("                                          ");
			bw.newLine();
			bw.write("                           TOTAL : " + totalcuen);
			bw.newLine();
			bw.write("                       ENTREGADO : " + entrega);
			bw.newLine();
			bw.write("                          CAMBIO : " + devuelvecuenta);
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			bw.write("   TOTAL DE ARTICULOS : " + cantidadtotal);
			bw.newLine();
			bw.write("              " + dia + "/" + mes + "/" + annio + "   " + hora + ":" + minutos);
			bw.newLine();
			bw.newLine();
			if(nombreagenda != null) {
				bw.write("   " + nombreagenda);
				bw.newLine();
				bw.write("   " + direccionagenda);
				bw.newLine();
				bw.write("\t" + telefonoagenda);
				bw.newLine();
			}
			bw.newLine();
			bw.write("          GRACIAS POR SU COMPRA          ");
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			bw.write("                                          ");
			bw.newLine();
			
			bw.close(); // cierra archivo
			System.out.println("El archivo fue salvado correctamente!");
			
		} catch (IOException e) {
			System.out.println("ERROR: Ocurrio un problema al salvar el archivo!" + e.getMessage());
		}
	}
}