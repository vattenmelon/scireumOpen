/**
 * Copyright (c) 2012 scireum GmbH - Andreas Haufler - aha@scireum.de
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package com.scireum.open.xml;

import java.util.*;

import javax.xml.transform.*;
import javax.xml.xpath.*;

import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.*;

import com.scireum.open.commons.*;

/**
 * Default implementation for XMLNode
 */
public class XMLNodeImpl implements StructuredNode {

	private Node node;
	CachedXPathAPI cachedXPathAPI = new CachedXPathAPI();

	public XMLNodeImpl(Node root) {
		node = root;
	}

	@Override
	public StructuredNode queryNode(String path)
	        throws XPathExpressionException {
		Node result = queryForNode(path);
		if (result != null) {
			return new XMLNodeImpl(node);
		}
		return null;
	}

	@Override
	public List<StructuredNode> queryNodeList(String path)
	        throws XPathExpressionException {
		NodeList selectNodeList = null;
		try {
				selectNodeList = cachedXPathAPI.selectNodeList(node, path);
		} catch (TransformerException ex) {
			throw new XPathExpressionException(ex);
		}
		List<StructuredNode> resultList = new ArrayList<StructuredNode>(
		        selectNodeList.getLength());
		for (int i = 0; i < selectNodeList.getLength(); i++) {
			resultList.add(new XMLNodeImpl(selectNodeList.item(i)));
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
		Node resultNode = queryForNode(path);
		if (resultNode != null) {
			if (resultNode instanceof Node) {
				String s = resultNode.getTextContent();
				if (s != null) {
					return s.trim();
				}
				return s;
			}
			return resultNode.toString().trim();
		}
		return null;
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

	private Node queryForNode(String path) throws XPathExpressionException{
		Node result = null;
		try {
				result = cachedXPathAPI.selectSingleNode(node, path);
		} catch (TransformerException ex) {
			throw new XPathExpressionException(ex);
		}
		return result;
	}
}