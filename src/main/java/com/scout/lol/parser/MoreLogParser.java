package com.scout.lol.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scout.lol.model.MoreLog;

public class MoreLogParser extends AbstractParser<MoreLog> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3992041546921240458L;

	@Override
	public MoreLog parse(String line) {
		if (null == line){
			return null; 
		}
		line = line.trim();
		if (line.isEmpty()){
			return null;
		}
		
		MoreLog ml = new MoreLog();
		//get timestamp
		int start = 0;
		int end = 23;
		if (line.length()<23){
			return null;
		}else{
			ml.setTimestamp(line.substring(start,end).trim());
		}
		
		//get logtype
		start = end+1;
		end = line.indexOf(":",start);
		if (end == -1){
			return null;
		}else{
			ml.setLogtype(line.substring(start,end).trim());
		}
		
		//get req or rsp
		start = line.indexOf("[REQ]",end);
		if (start == -1){
			start = line.indexOf("[RSP]",end);
		}
		if (start == -1){
			return null;
		}
		start += 1;
		end = start + 3;
		ml.setReqrsp(line.substring(start,end).trim());
		
		////parse the left string to map before set them to vars
		Map<String, List<String>> kvs = splitOthers(line.substring(end+2).trim(), " ");
		if (null != kvs){
			for (String key:kvs.keySet()){
				List<String> vals = kvs.get(key);
				String val = vals.get(0);//most of the vars only need one
				if (key.toUpperCase().equals("TOKEN")){
					ml.setToken(val);
				}else if (key.toUpperCase().equals("ID")){
					ml.setId(val);
				}else if (key.toUpperCase().equals("MODE")){
					ml.setMode(val);
				}else if (key.toUpperCase().equals("SIZE")){
					ml.setSize(val);
				}else if (key.toUpperCase().equals("ORIG")){
					List<Map<String,String>> listKVs = new ArrayList<Map<String,String>>();
					for (String sub:vals){
						listKVs.add(splitByDeli(sub,","));
					}
					ml.setOrig(listKVs);
				}else if (key.toUpperCase().equals("DEST")){
					List<Map<String,String>> listKVs = new ArrayList<Map<String,String>>();
					for (String sub:vals){
						listKVs.add(splitByDeli(sub,","));
					}
					ml.setDest(listKVs);
				}else if (key.toUpperCase().equals("SETTING")){
					List<Map<String,String>> listKVs = new ArrayList<Map<String,String>>();
					for (String sub:vals){
						listKVs.add(splitByDeli(sub,","));
					}
					ml.setSetting(listKVs);
				}else if (key.toUpperCase().equals("CONTEXT")){
					ml.setContext(splitByDeli(val, ";"));
					//set login id
					if (ml.getContext()!= null){
						ml.setLoginid(ml.getContext().get("login"));
					}
				}else if (key.toUpperCase().equals("LH")){
					ml.setLh(val);
				}else if (key.toUpperCase().equals("EDGE")){
					ml.setEdge(splitEdges(val, ","));
				}else if (key.toUpperCase().equals("TOKENS")){
					ml.setTokens(val);
				}else if (key.toUpperCase().equals("STATUS")){
					ml.setStatus(val);
				}else if (key.toUpperCase().equals("TIME")){
					ml.setTime(val);
				}else if (key.toUpperCase().equals("LENGTH")){
					ml.setLength(vals);
				}else if (key.toUpperCase().equals("ETA")){
					ml.setEta(val);
				}else if (key.toUpperCase().equals("STATICETA")){
					ml.setStaticeta(vals);
				}else if (key.toUpperCase().equals("DYNAMICETA")){
					ml.setDynamiceta(vals);
				}
			}
		}
		
		return ml;
	}
	
	public static List<String> splitEdges(String line, String deli){
		List<String> edges = new ArrayList<String>();
		if (null == line){
			return edges;
		}
		line = line.trim();
		String[] arr = line.split(deli);
		for (String edge:arr){
			edge = edge.trim();
			if (!edge.isEmpty()){
				edges.add(edge);
			}
		}
		return edges;
	}
	
	public static Map<String,String> splitByDeli(String line, String deli){
		Map<String,String> kvs = new HashMap<String, String>();
		if (null == line){
			return kvs;
		}
		line = line.trim();
		//if it's "2947146,-9864487,0,259,4,13806367490,0;2947147,-9864488,0,259,10,13806367480,0;2947147,-9864488,0,259,4,13806367470,0;"
		if (line.indexOf("=") == -1){
			kvs.put("0", line);
			return kvs;
		}		
		
		String[] arr = line.split(deli);
		if (arr.length==5 && arr[3].startsWith("LL=")){
			arr[3] += deli + arr[4];
		}
		for (String sub:arr){
			int index = sub.indexOf("=");
			if (index != -1){
				kvs.put(sub.substring(0,index).trim(), sub.substring(index+1).trim());
			}
		}
		return kvs;
	}
	
	public static Map<String,List<String>> splitOthers(String line, String deli){
		Map<String,List<String>> kvs = new HashMap<String, List<String>>();
		if (null == line){
			return kvs;
		}
		line = line.trim();
		if (line.isEmpty()){
			return kvs;
		}
		
		int start = 0;
		int equal, end;
		while (start<line.length()){
			equal = line.indexOf("=",start);
			if (equal == -1){
				System.err.println(String.format("Parse failed in %s at index %d",line,start));
				return kvs;
			}
			String key = line.substring(start,equal);
			end = line.indexOf("]",equal);
			if (end == -1){
				System.err.println(String.format("Parse failed in %s at index %d",line,equal));
				return kvs;
			}
			String val = line.substring(equal+2,end);
			
			if (!kvs.containsKey(key.trim())){
				kvs.put(key.trim(), new ArrayList<String>());
			}
			kvs.get(key.trim()).add(val.trim());
			
			
			start = line.indexOf(deli,end);
			if (start == -1){
				return kvs;
			}
		}
		
		return kvs;
	}

}
