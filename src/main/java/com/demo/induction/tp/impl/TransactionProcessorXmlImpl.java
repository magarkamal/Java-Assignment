package com.demo.induction.tp.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.demo.induction.tp.Transaction;
import com.demo.induction.tp.TransactionProcessor;
import com.demo.induction.tp.Violation;

public class TransactionProcessorXmlImpl implements TransactionProcessor {

	List<Transaction> trList = new ArrayList<>();
	List<Violation> violationList = new ArrayList<>();;

	@Override
	public void importTransactions(InputStream is) {

		System.out.println("######## Import Transaction ########");

		Transaction tr;

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			factory.setNamespaceAware(true);
			factory.setValidating(false);
			DocumentBuilder db = factory.newDocumentBuilder();
			Document document = db.parse(is);

			document.getDocumentElement().normalize();
			System.out.println("Root element: " + document.getDocumentElement().getNodeName());

			NodeList nodes = document.getElementsByTagName("Transaction");

			for (int i = 0; i < nodes.getLength(); i++) {

				Node nNode = nodes.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					tr = new Transaction();
					Element eElement = (Element) nNode;

					tr.setType(eElement.getAttribute("type"));
					tr.setAmount(new BigDecimal(eElement.getAttribute("amount")));
					tr.setNarration(eElement.getAttribute("narration"));
					trList.add(tr);
				}

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		

		} catch (SAXException e) {
			e.printStackTrace();
		

		} catch (IOException e) {
			e.printStackTrace();
		

		} catch (Exception e) {
			e.printStackTrace();
			
		}

		tr = new Transaction();
		tr.setTransactionList(trList);

	}

	@Override
	public List<Transaction> getImportedTransactions() {
		System.out.println("######## Transaction List ########");

		return this.trList;
	}

	@Override
	public List<Violation> validate() {
		System.out.println("######## Validate Transaction ########");
		int i = 1;
		Violation violation = new Violation();
		List<Transaction> transactionsList = this.trList;
		for (Transaction transaction : transactionsList) {

			if (transaction.getType().equalsIgnoreCase("d") || transaction.getType().equalsIgnoreCase("c")) {
				violation.setOrder(i);

			} else {
				violation.setOrder(i);
				violation.setProperty("type");
				violation.setDescription("type invalid");
			}
			violationList.add(violation);
			i++;
		}
		Boolean result = isBalanced();
		if (result.booleanValue() == false) {
			violation.setProperty("amount");
			violation.setDescription("amount not balanced");
			violationList.add(violation);
		}
		return this.violationList;
	}

	@Override
	public boolean isBalanced() {
		System.out.println("######## Total Transaction Amount ########");
		BigDecimal drTot = new BigDecimal("0");
		BigDecimal crTot = new BigDecimal("0");

		List<Transaction> transactionsList = this.trList;

		for (Transaction transaction : transactionsList) {
			if (transaction.getType().equalsIgnoreCase("d")) {
				drTot = drTot.add(new BigDecimal(transaction.getAmount().toString()));
			} else {
				crTot = crTot.add(new BigDecimal(transaction.getAmount().toString()));
			}
		}

		System.out.println("Debit:" + drTot);
		System.out.println("Credit:" + crTot);
		if (drTot.compareTo(crTot) == 0) {
			return true;
		} else {
			return false;
		}

	}

}
