package com.scireum.open.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.scireum.open.commons.Value;

/**
 * Default implementation for XMLNode
 */
public class XMLNodeImpl implements StructuredNode {

	private Node node;
	private static XPathCompiler xc = new SimpleXPathCompiler();

	/**
	 * Overrides the default xpath compiler.
	 */
	public static void installCompilter(XPathCompiler compiler) {
		if (compiler != null) {
			xc = compiler;
		}
	}

	public XMLNodeImpl(Node root) {
		node = root;
	}

	@Override
	public StructuredNode queryNode(String path)
			throws XPathExpressionException {
		Node result = (Node) xc.compile(path).evaluate(node,
				XPathConstants.NODE);
		if (result == null) {
			return null;
		}
		return new XMLNodeImpl(result);
	}

	@Override
	public List<StructuredNode> queryNodeList(String path)
			throws XPathExpressionException {
		NodeList result = (NodeList) xc.compile(path).evaluate(node,
				XPathConstants.NODESET);
		List<StructuredNode> resultList = new ArrayList<StructuredNode>(
				result.getLength());
		for (int i = 0; i < result.getLength(); i++) {
			resultList.add(new XMLNodeImpl(result.item(i)));
		}
		return resultList;
	}

	@Override
	public StructuredNode[] queryNodes(String path)
			throws XPathExpressionException {
		List<StructuredNode> nodes = queryNodeList(path);
		return nodes.toArray(new StructuredNode[nodes.size()]);
	}

	@Override
	public String queryString(String path) throws XPathExpressionException {
		Object result = xc.compile(path).evaluate(node, XPathConstants.NODE);
		if (result == null) {
			return null;
		}
		if (result instanceof Node) {
			String s = ((Node) result).getTextContent();
			if (s != null) {
				return s.trim();
			}
			return s;
		}
		return result.toString().trim();
	}

	@Override
	public boolean isEmpty(String path) throws XPathExpressionException {
		String result = queryString(path);
		return result == null || "".equals(result);
	}

	@Override
	public String getNodeName() {
		return node.getNodeName();
	}

	@Override
	public String toString() {
		return getNodeName();
	}

	@Override
	public Value queryValue(String path) throws XPathExpressionException {
		return Value.of(queryString(path));
	}
}
