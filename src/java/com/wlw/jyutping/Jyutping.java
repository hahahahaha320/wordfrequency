package com.wlw.jyutping;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import lombok.Data;

public @Data class Jyutping {

	private Integer id; 
	private String word; 
	private Integer times; 
	private String pingyin; 
	private Integer tone; 
	private String allPingyin; 
	private String goodPingyin; 
	private String hanyuPingyin; 
	private Integer hanyuTone;
	private String hanyuPingyinFull; 
	private String hanyuPingyinFulls; 
	private String hanyuPingyins;
	private String src; 
	private Date ctm; 
	
	public Jyutping()	{
	}
	public Jyutping(String word)	{
		this.word = word;
	}
	
	public String toString()	{
		return ToStringBuilder.reflectionToString(this);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getPingyin() {
		return pingyin;
	}
	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}
	public Integer getTone() {
		return tone;
	}
	public void setTone(Integer tone) {
		this.tone = tone;
	}
	public String getAllPingyin() {
		return allPingyin;
	}
	public void setAllPingyin(String allPingyin) {
		this.allPingyin = allPingyin;
	}
	public String getGoodPingyin() {
		return goodPingyin;
	}
	public void setGoodPingyin(String goodPingyin) {
		this.goodPingyin = goodPingyin;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public Date getCtm() {
		return ctm;
	}
	public void setCtm(Date ctm) {
		this.ctm = ctm;
	}
	public String getHanyuPingyin() {
		return hanyuPingyin;
	}
	public void setHanyuPingyin(String hanyuPingyin) {
		this.hanyuPingyin = hanyuPingyin;
	}
	public Integer getHanyuTone() {
		return hanyuTone;
	}
	public void setHanyuTone(Integer hanyuTone) {
		this.hanyuTone = hanyuTone;
	}
	public String getHanyuPingyinFull() {
		return hanyuPingyinFull;
	}
	public void setHanyuPingyinFull(String hanyuPingyinFull) {
		this.hanyuPingyinFull = hanyuPingyinFull;
	}
	public String getHanyuPingyinFulls() {
		return hanyuPingyinFulls;
	}
	public void setHanyuPingyinFulls(String hanyuPingyinFulls) {
		this.hanyuPingyinFulls = hanyuPingyinFulls;
	}
	public String getHanyuPingyins() {
		return hanyuPingyins;
	}
	public void setHanyuPingyins(String hanyuPingyins) {
		this.hanyuPingyins = hanyuPingyins;
	}
	
	
}