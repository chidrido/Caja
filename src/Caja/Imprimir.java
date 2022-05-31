package Caja;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Imprimir {

	static public void Imprimirfruta() {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\ticket_fruta.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			return;
		}

		DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc document = new SimpleDoc(inputStream, docFormat, null);

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

		if (defaultPrintService != null) {
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			try {
				printJob.print(document, attributeSet);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("No existen impresoras instaladas");
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void ImprimirTicket() {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\ticket_articulos.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			return;
		}

		DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc document = new SimpleDoc(inputStream, docFormat, null);

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

		if (defaultPrintService != null) {
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			try {
				printJob.print(document, attributeSet);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("No existen impresoras instaladas");
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void ImprimirListaCompra() {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\lista_compra.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			return;
		}

		DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc document = new SimpleDoc(inputStream, docFormat, null);

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

		if (defaultPrintService != null) {
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			try {
				printJob.print(document, attributeSet);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("No existen impresoras instaladas");
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static public void ImprimirFacturaActualizada() {

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:\\Users\\chidr\\OneDrive\\Desktop\\Database\\lista_compra.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			return;
		}

		DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc document = new SimpleDoc(inputStream, docFormat, null);

		PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

		if (defaultPrintService != null) {
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			try {
				printJob.print(document, attributeSet);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("No existen impresoras instaladas");
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
