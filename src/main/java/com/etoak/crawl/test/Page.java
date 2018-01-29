package com.etoak.crawl.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Page {

	private byte[] content;
	private String html; // 网页源码字符串
	private Document doc;// 网页Dom文档
	private String charset;// 字符编码
	private String url;// url路径
	private String contentType;// 内容类型

	public Page(byte[] content, String url, String contentType) {
		this.content = content;
		this.url = url;
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public String getUrl() {
		return url;
	}

	public String getContentType() {
		return contentType;
	}

	public byte[] getContent() {
		return content;
	}

	/**
	 * 返回网页的源码字符串
	 * 
	 * @return 网页的源码字符串
	 */
	public String getHtml() {
		if (html != null) {
			return html;
		}
		if (content == null) {
			return null;
		}
		if (charset == null) {
			charset = CharsetDetector.guessEncoding(content); // 根据内容来猜测 字符编码
		}
		try {
			this.html = new String(content, charset);
			return html;
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * 得到文档
	 */
	public Document getDoc() {
		if (doc != null) {
			return doc;
		}
		try {
			this.doc = Jsoup.parse(getHtml(), url);
			return doc;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取网页中满足指定css选择器的所有元素的指定属性的集合 例如通过attrs("img[src]","abs:src")可获取网页中所有图片的链接
	 * 
	 * @param cssSelector
	 * @param attrName
	 * @return
	 */
	public ArrayList<String> attrs(String cssSelector, String attrName) {
		ArrayList<String> result = new ArrayList<String>();
		Elements eles = select(cssSelector);
		for (Element ele : eles) {
			if (ele.hasAttr(attrName)) {
				result.add(ele.attr(attrName));
			}
		}
		return result;
	}

	/**
	 * 获取网页中满足指定css选择器的所有元素的指定属性的集合 例如通过attr("img[src]","abs:src")可获取网页中第一个图片的链接
	 * 
	 * @param cssSelector
	 * @param attrName
	 * @return
	 */
	public String attr(String cssSelector, String attrName) {
		return select(cssSelector).attr(attrName);
	}

	public Elements select(String cssSelector) {
		return this.getDoc().select(cssSelector);
	}

	public Element select(String cssSelector, int index) {
		Elements eles = select(cssSelector);
		int realIndex = index;
		if (index < 0) {
			realIndex = eles.size() + index;
		}
		return eles.get(realIndex);
	}

}
