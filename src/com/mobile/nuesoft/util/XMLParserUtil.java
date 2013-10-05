package com.mobile.nuesoft.util;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

public class XMLParserUtil {

	public static final String TAG = "XMLParserUtil";

	public static String getNodeValue(final Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node data = childNodes.item(i);
			if (data.getNodeType() == Node.TEXT_NODE) {
				return data.getNodeValue();
			}
		}

		return "";
	}

	public static String getNodeValue(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.TEXT_NODE)
						return data.getNodeValue();
				}
			}
		}
		return "";
	}

	public static Node getNode(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}

		return null;
	}

	public static String getNodeAttr(String attrName, Node node) {
		NamedNodeMap attrs = node.getAttributes();
		for (int y = 0; y < attrs.getLength(); y++) {
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	public static String getNodeAttr(String tagName, String attrName,
	        NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			Node node = nodes.item(x);
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				NodeList childNodes = node.getChildNodes();
				for (int y = 0; y < childNodes.getLength(); y++) {
					Node data = childNodes.item(y);
					if (data.getNodeType() == Node.ATTRIBUTE_NODE) {
						if (data.getNodeName().equalsIgnoreCase(attrName))
							return data.getNodeValue();
					}
				}
			}
		}

		return "";
	}

	public static ArrayList<Node> getComponentNodesFromBody(final Node root) {
		ArrayList<Node> list = new ArrayList<Node>();
		NodeList rootChildList = root.getChildNodes();

		for (int i = 0; i < rootChildList.getLength(); i++) {
			Node n = rootChildList.item(i);
			if (n.getNodeName().equals("component")) {
				list.add(n);
			}
		}

		return list;
	}
	
	public static ArrayList<Node> getNamedNodes(final String nodeName, final Node rootNode) {
		ArrayList<Node> list = new ArrayList<Node>();
		NodeList rootChildList = rootNode.getChildNodes();

		for (int i = 0; i < rootChildList.getLength(); i++) {
			Node n = rootChildList.item(i);
			if (n.getNodeName().equals(nodeName)) {
				list.add(n);
			}
		}

		return list;
	}

	public static Node getCDADocumentBodySection(final Node root) {
		NodeList rootList = root.getChildNodes();

		for (int i = 0; i < rootList.getLength(); i++) {
			Node n1 = rootList.item(i);
			if (n1.getNodeName().equals("component")) {
				if (n1.hasChildNodes()) {
					NodeList n1_children = n1.getChildNodes();
					for (int j = 0; j < n1_children.getLength(); j++) {
						Node n2 = n1_children.item(j);
						if (n2.getNodeName().equals("structuredBody")) {
							return n2;
						}
					}
				}
			}
		}

		return null;
	}
}
