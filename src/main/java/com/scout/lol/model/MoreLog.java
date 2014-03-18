package com.scout.lol.model;

import java.util.List;
import java.util.Map;

import org.apache.avro.reflect.Nullable;

public class MoreLog extends Log {
	@Nullable
	private String timestamp = null;
	@Nullable
	private String logtype = null;
	@Nullable
	private String reqrsp = null;
	@Nullable
	private String token = null;
	@Nullable
	private String mode = null;
	@Nullable
	private String size = null;
	@Nullable
	private List<Map<String, String>> orig = null;
	@Nullable
	private List<Map<String, String>> dest = null;
	@Nullable
	private List<Map<String, String>> setting = null;
	@Nullable
	private Map<String, String> context = null;
	@Nullable
	private String lh = null;
	@Nullable
	private List<String> edge = null;
	@Nullable
	private String tokens = null;
	@Nullable
	private String status = null;
	@Nullable
	private String time = null;
	@Nullable
	private List<String> length = null;
	@Nullable
	private String eta = null;
	@Nullable
	private List<String> staticeta = null;
	@Nullable
	private List<String> dynamiceta = null;
	@Nullable
	private String loginid = null;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getReqrsp() {
		return reqrsp;
	}

	public void setReqrsp(String reqrsp) {
		this.reqrsp = reqrsp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	public String getLh() {
		return lh;
	}

	public void setLh(String lh) {
		this.lh = lh;
	}

	public List<String> getEdge() {
		return edge;
	}

	public void setEdge(List<String> edge) {
		this.edge = edge;
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public List<Map<String, String>> getOrig() {
		return orig;
	}

	public void setOrig(List<Map<String, String>> orig) {
		this.orig = orig;
	}

	public List<Map<String, String>> getDest() {
		return dest;
	}

	public void setDest(List<Map<String, String>> dest) {
		this.dest = dest;
	}

	public List<Map<String, String>> getSetting() {
		return setting;
	}

	public void setSetting(List<Map<String, String>> setting) {
		this.setting = setting;
	}

	public List<String> getLength() {
		return length;
	}

	public void setLength(List<String> length) {
		this.length = length;
	}

	public List<String> getStaticeta() {
		return staticeta;
	}

	public void setStaticeta(List<String> staticeta) {
		this.staticeta = staticeta;
	}

	public List<String> getDynamiceta() {
		return dynamiceta;
	}

	public void setDynamiceta(List<String> dynamiceta) {
		this.dynamiceta = dynamiceta;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getLoginid() {
		return loginid;
	}
}
