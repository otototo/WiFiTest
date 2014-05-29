/**
 * @author paulina
 * 
 * class XMLFunctions.
 * */

package data.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class XMLFunctions 
{
	public XMLFunctions()
	{
	}
	
	/**
	 *  by default NodeValue cannot be null
	 **/
	public String getStringValue(Document inDoc, String aTag) {

		String s = "";
		Node n = getNodesTextNodeByTag(inDoc, aTag);
		if (n != null) {
			s = n.getNodeValue();
		}
//		return toUTF8(s);
		return s;
	}
	
	public String getStringValue(Document aXml, String aTag, boolean canBeNull) {
		String s = "";
		try {
			s = getStringValue(aXml, aTag);
		} catch (Exception e) {
				s = "";
		}
		return s;
	}
	
	public int getIntValue(Document aXml, String aTag) 
	{
		int i;
		String s = getStringValue(aXml, aTag).trim();
		try {
			i  = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("cannot parseInt:"+e.getMessage());
		}
		return i;
	}
	

	public int getIntValue(Node parentNode, String aTag, int index) {

		int i;
		String s = getStringValueByTag(parentNode, aTag, index).trim();
		try {
			i  = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			i = -1;
		}
		return i;
	}
	
	public Document getParsedDocument(String aXmlPath) 
			throws ParserConfigurationException, SAXException, IOException 
	{	
		return getParsedDocument(new File(aXmlPath));
	}
	
	public Document getParsedDocument(File aXml) 
			throws ParserConfigurationException, SAXException, IOException 
	{
		if (aXml.exists() && aXml.canRead()) {
			DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder docBuilder = null;
			try {
				docBuilder = docBuildFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new ParserConfigurationException("ParseConfig:"+e.getMessage());
			}
			Document document = null;
			try {
				document = docBuilder.parse(aXml);
			} catch (SAXException e) {
				throw new SAXException("SAXException:"+e.getMessage());
				
			} catch (IOException e) {
				throw new IOException("IOException:"+e.getMessage());
			}
			document.getDocumentElement().normalize();	
			return document;
		}
		else {
			return null;
		}
	}
	
	public Node getNodesTextNodeByTag(Document document, String aTag) 
	{
		Node node = getNodesTextNodeByTag(document.getDocumentElement(), aTag);
		return node;
	}
	
	public Node getNodesTextNodeByTag(Node parentNode, String aTag) {
		Node node = null;
		if (((Element)parentNode).getElementsByTagName(aTag) != null) {	
			node = ((Element)parentNode).getElementsByTagName(aTag).item(0);
			if ((node != null) && (node.hasChildNodes())) {	
				node = node.getChildNodes().item(0);
			}
		}	
		return node;
	}
	
	public int getNodeCountByTag(Document document, String aTag) {
		int count = getNodeCountByTag(document.getDocumentElement(), aTag);
		return count;
	}
	


	public int getNodeCountByTag(Node parentNode, String childNodeTag) {
		int count = -1;
		if (parentNode != null) {
		NodeList list = ((Element)parentNode).getElementsByTagName(childNodeTag);		
			if (list != null) 
				count = list.getLength();
		}
		return count;
	}
	
	/**
	 * Tags & tagValues must be less or equal to count of nodeTags in srcDoc.
	 * Tags & tagValues must be the same size 
	 * 
	 * @param srcDoc - doc in which to look
	 * @param nodeTagToCount - which tags to count
	 * @param tag - sibling restriction tags
	 * @param tagValue - restriction tags values
	 * 
	 * @return counted tags with specified restricions
	 **/	
	public int getNodeCountByTag(Document srcDoc, String nodeTagToCount,
			String tag, String tagValue) {

		int count = 0;
		int nodeCount = srcDoc.getDocumentElement().getElementsByTagName(nodeTagToCount).getLength();
		NodeList restrictionList = srcDoc.getElementsByTagName(tag);
		Node restriction = null;
		String value = null;

		for (int i = 0; i < nodeCount; i++) {
			restriction = getNthChild(restrictionList, i);
			value = getStringValue(restriction);
			
			if ((value != null) && (value.equals(tagValue))) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Tags & tagValues must be less or equal to count of nodeTags in srcDoc.
	 * Tags & tagValues must be the same size 
	 * 
	 * @param srcDoc - doc in which to look
	 * @param nodeTagToCount - which tags to count
	 * @param tag - sibling restriction tags
	 * @param tagValue - restriction tags values
	 * 
	 * @return counted tags with specified restricions
	 **/	
	public int getNodeCountByTag(Document srcDoc, String nodeTagToCount,
			String tag1, String tagValue1, String tag2, String tagValue2)
	{		
		int count = 0;		
		int nodeCount = getNodeCountByTag(srcDoc, nodeTagToCount);
		
		NodeList restrictionList1 = srcDoc.getElementsByTagName(tag1);
		NodeList restrictionList2 = srcDoc.getElementsByTagName(tag2);
		Node restriction1 = null, restriction2 = null;
		String value1 = null, value2 = null;
		
		for (int i = 0; i < nodeCount; i++) {
			restriction1 = getNthChild(restrictionList1, i);
			value1 = getStringValue(restriction1);
			
			restriction2 = getNthChild(restrictionList2, i);
			value2 = getStringValue(restriction2);
			
			if ((value1 != null) && (value1.equals(tagValue1)) &&
					(value2 != null) && (value2.equals(tagValue2))) {
				count++;
			}
		}
		return count;
	}
	
	public String getAttrString(Document doc, String tagName, String attrName) {
		String temp = "";
		NodeList nodeList = doc.getElementsByTagName(tagName);
		if (nodeList != null) {
			Node node = nodeList.item(0);
			
			NamedNodeMap attributes = (NamedNodeMap)node.getAttributes();
			Attr attribute = (Attr) attributes.getNamedItem(attrName);
			if (attribute != null) {
				temp = attribute.getValue();
				if (temp != null) {
					return temp;
				}
			}
		}
		return temp;
	}


	public String getAttrString(Node node, String attrName) {
		String temp = "";
		if (node != null) {
			NamedNodeMap attributes = (NamedNodeMap)node.getAttributes();
			Attr attribute = (Attr) attributes.getNamedItem(attrName);
			if (attribute != null) {
				temp = attribute.getValue();
				if (temp != null) {
					return temp;
				}
			}
		}
		return temp;
	}
	
	public void setAttrString(Document inDoc, String aTag, String anAttrName, String anAttrString) throws IOException {
		
		String temp = "";
		NodeList nodeList = inDoc.getElementsByTagName(aTag);
		if (nodeList != null) {
			Node node = nodeList.item(0);
			
			NamedNodeMap attributes = (NamedNodeMap)node.getAttributes();
			Attr attribute = (Attr) attributes.getNamedItem(anAttrName);
			if (attribute != null) {
				temp = attribute.getValue();
				attribute.setNodeValue(anAttrString);
				temp = attribute.getValue();
			}
		}					
	}
	
	public boolean hasTag(Document inDoc, String tag) {
		return ((inDoc.getElementsByTagName(tag).item(0)) != null) ? true : false;
	}
	
	protected boolean changeNodeValue(Document doc, String tag, String value) {
		boolean ret = false;
		Node node = getNodesTextNodeByTag(doc, tag);
		if (node != null) {
			node.setNodeValue(value);
		}
		return ret;
	}
	
	protected boolean changeNodeValue(Document doc, String tag, String attr, String attrVal, String value) {
		boolean ret = false;
		
		NodeList nodeList = doc.getElementsByTagName(tag);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.hasAttributes()) {
					NamedNodeMap attributes = (NamedNodeMap)node.getAttributes();
					Attr attribute = (Attr) attributes.getNamedItem(attr);
					if (attribute != null) {
						node = node.getChildNodes().item(0);
					
						if (attribute.getValue().equals(attrVal)) {					
							node.setNodeValue(value);
							ret = true;
							break;
						}
						
					}
				}
			}
		}
		return ret;
	}
	
	public boolean saveXml(Document doc, String path) throws TransformerException {
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new TransformerConfigurationException("saveXml:"+e.getMessage());
		}
		  DOMSource source = new DOMSource(doc);
		  
//		  StreamResult result =  new StreamResult(new File(path));
		  FileOutputStream fos;
		  StreamResult result;
		  try {
			fos = new FileOutputStream(new File(path));
		    result = new StreamResult(fos);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new TransformerException("saveXml:"+e.getMessage());
		} catch (FileNotFoundException e) {
			throw new TransformerException("saveXml:"+e.getMessage());
		}
		return true;
	}
	public Document changeXmlNode(Document doc, String tag, String value) {
		changeNodeValue(doc, tag, value);	
		return doc;
	}
	
	public Document changeXmlNode(Document doc, String tag, String attr, String attrVal, String value) {
		changeNodeValue(doc, tag, attr, attrVal, value);	
		return doc;
	}

	public Document changeXMLData(Document inDoc,
			String tag1, String value1, String tag2, 
			String value2) {

		if (inDoc != null) {
			inDoc = changeXmlNode(inDoc, tag2, value2);
			inDoc = changeXmlNode(inDoc, tag1, value1);
		}
		return inDoc;
	}
	/** nodeList.item(n).getChildNodes().item(m).getNodeValue() */
	public String getNthChildMthNodeListValue(NodeList nodeList, int n, int m) 
	{
		String value = "";
		try {
			value = nodeList.item(n).getChildNodes().item(m).getNodeValue();
		} catch (Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	/** nodeList.item(n).getChildNodes().item(m).getNodeValue() */
	public Node getNthChild(NodeList nodeList, int n) {
		Node node = nodeList.item(n);
		return node;
	}

	/** finds textNode in nodes children
	 * @return value of first text node in tree*/
	private String getStringValue(Node node) {
		Node temp = null;
		String val = "";
		while ((node!= null) && node.hasChildNodes()) {
			temp = node.getFirstChild();
			if ((temp != null) && (temp.getNodeType() == Element.TEXT_NODE)) {
				val = temp.getNodeValue();
				break;
			}
			else {
				node = temp;
			}
		}
		return val;
	}
		/** 
	 * getNthChildMthNodeListValue(tagList, i, 0) 
	 * @param doc - source document
	 * @param tag - tag to search
	 * @param i - index of tag's node child
	 * @return value of i-th child in tagList
	 * */
	private String getStringValueByTag(Document doc, String tag, int i) {
		String val;
		NodeList gameList = doc.getElementsByTagName(tag);
		val = getNthChildMthNodeListValue(gameList, i, 0);
		return val;
	}
	

	/** 
	 * getNthChildMthNodeListValue(tagList, i, 0) 
	 * @param node - source node
	 * @param tag - tag to search
	 * @param i - index of tag's node child
	 * @return value of i-th child in tagList
	 * */
	public String getStringValueByTag(Node node, String tag, int i)
	{
		String val;
		NodeList gameList = ((Element) node).getElementsByTagName(tag);
		val = getNthChildMthNodeListValue(gameList, i, 0);
		return val;
	}

	
	/** 
	 * getNthChild(tagList, i) 
	 * @param doc - source document
	 * @param tag - tag to search
	 * @param i - index of tag's node child
	 * @return value of i-th child in tagList
	 * */
	private Node getNodeByTag(Document doc, String tag, int i) 
	{
		NodeList tagList = doc.getElementsByTagName(tag);
		Node node = getNthChild(tagList, i);
		return node;
	}
	
	/** 
	 * getNthChild(tagList, i) 
	 * @param parentNode - source document
	 * @param tag - tag to search
	 * @param i - index of tag's node child
	 * @return value of i-th child in tagList. null if none found
	 * */
	public Node getNodeByTag(Node parentNode, String tag, int i) 
	{
		NodeList tagList = ((Element)parentNode).getElementsByTagName(tag);
		Node childNode = getNthChild(tagList, i);
		return childNode;
	}
	
	

	/** finds index of Node with specified value in NodeList with tag
	 * @return index of tag with value. -1 if none found*/
	private int getIndexByTagWithValue(Document doc, String value,
			String tag)
	{
		int ret = -1;
		String temp;
		NodeList nl = doc.getElementsByTagName(tag);
		for (int i = 0; i < nl.getLength(); i++) {
			temp = getStringValueByTag(doc, tag, i);
			if (value.equalsIgnoreCase(temp)) {
				ret = i;
				break;
			}
		}		
		return ret;
	}

	/** @return added element */
	public Node addElementWithTextNodeToNode(Document doc, Node NodeToWitchToAppend,
			String nodeName, String nodeText) 
	{
		Element textNode = doc.createElement(nodeName);
		Text text = doc.createTextNode(nodeText); 
		textNode.appendChild(text);
		NodeToWitchToAppend.appendChild(textNode);
		return textNode;
	}
	
	/** adds new element with nodeName to node
	 * @return added element */
	/** adds new element with nodeName to node
	 * @return added element */
	public Node addElementToNode(Document destDoc, Node nodeToAddTo,
			String nodeName) {
		Element nodeToAppend = destDoc.createElement(nodeName);
		if (nodeToAddTo != null)
		{			
			nodeToAddTo.appendChild(nodeToAppend);
		}
		return nodeToAppend;
	}

	private Document appendElementNodeToNode(Document outDoc,
			Node nodeToAppendTo, Node nodeToAppend)
	{
		nodeToAppend = outDoc.importNode(nodeToAppend, true);
		nodeToAppendTo.appendChild(nodeToAppend);		
		return outDoc;
	}
	
	
	/** appends element from nodeList with tag of index
	 * @param oudestDoctDoc - document to which node is added
	 * @param srcDoc - node from which element is appended
	 * @param nodeDest - node to which element is appended
	 * @param tag - tag of node whose child to use
	 * @param index - index of child from which to take node
	 * @return appended element */
	public Node appendElementFromXMLToNode(Document destDoc, 
			Document srcDoc, Node nodeDest, String tag, int index)
{
		Node node = srcDoc.getElementsByTagName(tag).item(index);
		if (node == null) {
			node = addElementToNode(destDoc, nodeDest, tag);
		}
		else {
			node = appendElementNodeToNode(destDoc, nodeDest, node);
		}
		return node;
	}

	/**
	 * @param fileToSave
	 * @return
	 * @throws ParserConfigurationException 
	 */
    public Document createDocument() throws ParserConfigurationException
    {
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("ROOT");
		doc.appendChild(rootElement);
		return doc;
    }

}
