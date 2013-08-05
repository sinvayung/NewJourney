/*
 * Streaming API for XML, Since Java 6.0
 * StAX是基于SAX的，同样将XML作为一组事件来处理，不过与SAX不同的是，StAX是采用拉（Pull）的方式来处理事件， 
 * 允许应用程序从事件流中拉出事件，而不是提供从解析器中接收事件的回调程序程序。
 * 
 */
package com.newjourney.java.xml;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.junit.Test;

public class StAXSample {

	@Test
	public void testRead() throws XMLStreamException, FactoryConfigurationError {
		StringReader reader = new StringReader(
				"<?xml version=\"1.0\" ?><personList><person id=\"101\"><name>Stan</name><age>26</age></person><person id=\"101\"><name>Yung</name><age>27</age></person></personList>");
		XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(reader);
		while (xmlReader.hasNext()) {
			int event = xmlReader.next();
			printEvent(xmlReader, event);
		}
	}

	private void printEvent(XMLStreamReader xmlReader, int event) throws XMLStreamException {
		switch (event) {
		case XMLStreamConstants.START_DOCUMENT:
			System.out.println("START_DOCUMENT");
			break;
		case XMLStreamConstants.START_ELEMENT:
			System.out.println("START_ELEMENT : " + xmlReader.getName().toString());
			int attributeCount = xmlReader.getAttributeCount();
			if (attributeCount > 0) {
				for (int i = 0; i < attributeCount; i++) {
					System.out.println("ATTRIBUTE : " + xmlReader.getAttributeName(i) + "=" + xmlReader.getAttributeValue(i));
				}
			}
			break;
		case XMLStreamConstants.CHARACTERS:
			System.out.println("CHARACTERS : " + xmlReader.getText());
			break;
		case XMLStreamConstants.END_ELEMENT:
			System.out.println("END_ELEMENT");
			break;
		case XMLStreamConstants.END_DOCUMENT:
			System.out.println("END_DOCUMENT");
			break;
		default:
			System.err.println(event);
			break;
		}
	}

	@Test
	public void testWrite() throws XMLStreamException, FactoryConfigurationError {
		StringWriter writer = new StringWriter();
		XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
		xmlWriter.writeStartDocument();

		xmlWriter.writeStartElement("personList");

		writePerson(xmlWriter, 101, "Stan", 26);
		writePerson(xmlWriter, 102, "Yung", 27);

		xmlWriter.writeEndElement();

		xmlWriter.writeEndDocument();

		String xmlContent = writer.toString();
		System.out.println(xmlContent);
	}

	private void writePerson(XMLStreamWriter xmlWriter, int id, String name, int age) throws XMLStreamException {
		xmlWriter.writeStartElement("person");
		xmlWriter.writeAttribute("id", String.valueOf(id));

		xmlWriter.writeStartElement("name");
		xmlWriter.writeCharacters(name);
		xmlWriter.writeEndElement();

		xmlWriter.writeStartElement("age");
		xmlWriter.writeCharacters(String.valueOf(age));
		xmlWriter.writeEndElement();

		xmlWriter.writeEndElement();
	}

}
