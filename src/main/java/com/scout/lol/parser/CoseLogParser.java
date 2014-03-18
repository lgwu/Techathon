package com.scout.lol.parser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.scout.lol.model.CoseLog;

public class CoseLogParser extends AbstractParser<CoseLog>
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2857493904313695447L;

    private static final  ArrayList<String> REQUEST_KEYS =  new ArrayList<String>(Arrays.asList(
                                "PoiQuery",
                                "CategoryParam",
                                "SearchType",
                                "SearchArea",
                                "SortType",
                                "PoiStartIndex",
                                "PoiNumber",
                                "MinReturnedItems",
                                "NeedSponsoredPois",
                                "SponsorListingStartIndex",
                                "SponsorListingNumber",
                                "OnlyMostPopular",
                                "NeedUserPreviousRating",
                                "NeedUserGeneratePois",
                                "NeedHighlyRelevantPois",
                                "ContextString",
                                "UserId",
                                "TransactionId",
                                "ServiceAgent",
                                "UgcPreference",
                                "AutoExpandSearchRadius",
                                "IsDebugRequest",
                                "ClientName",
                                "ClientVersion",
                                "ContextProperty"
    )); 
    
    private static final ArrayList<String>  CATE_PARAM_KEYS =  new ArrayList<String>(Arrays.asList(
            "CategoryHierarchyId", "CategoryVersion", "CategoryId"));

    private static final ArrayList<String>  SEARCH_AERA_KEYS =  new ArrayList<String>(Arrays.asList(
            "AreaType", "Anchor", "City", "CountryCode", "CountryList", "GeoCodedAddress", "PostalCode", "Province", "RadiusInMeter", "RawAddressLine", "RoutePoints"
                    ));

    
    private static final ArrayList<String>  CONTENT_KEYS =  new ArrayList<String>(Arrays.asList(
                              "poidataset",
                              "deviceCarrier",
                              "product",
                              "supportTouch",
                              "program_code",
                              "region",
                              "version",
                              "XNAV_TIMESTAMP",
                              "carrier",
                              "requestor",
                              "application",
                              "c-server url",
                              "needreview",
                              "adengine",
                              "dataset",
                              "c-server class",
                              "searchunit",
                              "login",
                              "needsponsor",
                              "poicountrylist",
                              "GENERATE_LANE_INFO",
                              "adtype",
                              "device",
                              "locale",
                              "poiFinderVersion",
                              "isAndroid"
     ));

    
    public CoseLog parse(String line)
    {        
        CoseLog log = new CoseLog();

        log.setTimestamp(this.parseTimestamp(line));
        log.setRequestId(this.parseRequestId(line));
        
        Map<String, String> keyVals = this.parseKeyValues(line, REQUEST_KEYS, ',');
        if (null == keyVals)
        {
            return null;
        }
        
        log.setQuery(keyVals.get("PoiQuery"));
        log.setDebug(keyVals.get("IsDebugRequest"));
        
        Long userId = Long.valueOf(keyVals.get("UserId"));
        log.setUserId(userId);
        
        String categoryParam = keyVals.get("CategoryParam");
        this.parseCategoryParam(categoryParam, log);
                
        String searchArea = keyVals.get("SearchArea");
        this.parseSearchArea(searchArea, log);
        
        String context = keyVals.get("ContextString");
        this.parseContext(context, log);
        
        return log;
    }
    
    private String parseTimestamp(String line)
    {
        return line.substring(0, 8);
    }
    
    private String parseAttribute(String line, int fromIndex, String key, String endFlag)
    {
        int start = line.indexOf(key, fromIndex);
        
        int end = -1;
        if (null == endFlag)
        {
            end = line.length();
        } else
        {
            end = line.indexOf(endFlag, start);
        }
        
        if (start == -1 || end == -1)
        {
            return null;
        }
        
        return line.substring(start, end);
    }
    
    private String parseRequestId(String line)
    {
        String attribute = this.parseAttribute(line, 0, "RequestId", ",");
        String value =  attribute.split("=", 2)[1];
        return value.substring(1,  value.length()-1);
    }
    
//    private String parseRequest(String line)
//    {
//        String attribute = this.parseAttribute(line, 0, "PoiRequest", null);
//        String value = attribute.split("=", 1)[1];
//        return value.substring(1, value.length()-1);
//    }
   
    private Map<String, String> parseKeyValues(String line, List<String> keys, char delimiter)
    {
        Map<Integer, String> posKeyMap  = new TreeMap<Integer, String>();
        for (String key: keys)
        {
            Integer pos = line.indexOf(key);
            if (pos == -1) continue;
            
            if (pos == 0 || line.charAt(pos-1) == delimiter || line.charAt(pos-1) == '[')
            {
                posKeyMap.put(pos, key);
            }
        }
        
        Map<String, String> keyValues = new HashMap<String, String>();
        ArrayList<Integer> keyStarts = new ArrayList<Integer>(posKeyMap.keySet());
        for (int i = 0; i < keyStarts.size(); ++i)
        {
            Integer keyStart = keyStarts.get(i);
            
            Integer valStart = keyStart + posKeyMap.get(keyStart).length() + 1;            
            Integer valEnd = i < keyStarts.size() - 1 ? keyStarts.get(i+1) - 1 : line.length();
            
            String value = line.substring(valStart, valEnd);
            keyValues.put(posKeyMap.get(keyStart), value);
        }
        
        return keyValues;
    }
    
    private void parseCategoryParam(String categoryParam, CoseLog log)
    {
        if (categoryParam.startsWith("[") && categoryParam.endsWith("]"))
        {
            categoryParam = categoryParam.substring(1, categoryParam.length()-1);
        }
        
        Map<String, String> keyVals = this.parseKeyValues(categoryParam, CATE_PARAM_KEYS, ',');
        
        String categoryHirId = keyVals.get("CategoryHierarchyId");
        log.setCategoryHierarchyId(null == categoryHirId ? -1 : Integer.valueOf(categoryHirId));
        log.setCategoryVersion(keyVals.get("CategoryVersion"));
        log.setCategoryId(keyVals.get("CategoryId"));
    }
    
    
    private void parseSearchArea(String searchArea, CoseLog log)
    {
        if (searchArea.startsWith("[") && searchArea.endsWith("]"))
        {
            searchArea = searchArea.substring(1, searchArea.length()-1);
        }
        
        Map<String, String> keyVals = this.parseKeyValues(searchArea, SEARCH_AERA_KEYS, ',');
        
        log.setAreaType(keyVals.get("AreaType"));
        log.setAnchor(keyVals.get("Anchor"));
        log.setCity(keyVals.get("City"));
        log.setCountryCode(keyVals.get("CountryCode"));
        log.setPostalCode(keyVals.get("PostalCode"));
        log.setProvince(keyVals.get("Province"));
        log.setRadius(keyVals.get("RadiusInMeter"));
        log.setRoutePoints(keyVals.get("RoutePoints"));
    }
    
    private void parseContext(String context, CoseLog log)
    {
        if (context.startsWith("[") && context.endsWith("]"))
        {
            context = context.substring(1, context.length()-1);
        }
        
        Map<String, String> keyVals = this.parseKeyValues(context, CONTENT_KEYS, ';');
        
        log.setDeviceCarrier(keyVals.get("deviceCarrier"));
        log.setProduct(keyVals.get("product"));
        log.setCarrier(keyVals.get("carrier"));
    }
    
    
    public static void main(String[] args) {
        CoseLogParser parser = new CoseLogParser();
        String line = "09:12:48 RequestId=[1380643968443@Thread-27],PoiRequest=[PoiQuery=Gupton William E MD,CategoryParam=,SearchType=FOR_GENERAL_POI,SearchArea=[AreaType=CITY_STATE,Anchor=,City=SACRAMENTO,CountryCode=US,CountryList=[],GeoCodedAddress=,PostalCode=,Province=CA,RadiusInMeter=-2147483648,RawAddressLine=,RoutePoints=[]],SortType=BY_RANKING,PoiStartIndex=0,PoiNumber=50,MinReturnedItems=-2147483648,NeedSponsoredPois=false,SponsorListingStartIndex=0,SponsorListingNumber=1,OnlyMostPopular=false,NeedUserPreviousRating=false,NeedUserGeneratePois=false,NeedHighlyRelevantPois=false,ContextString=[needsponsor=FALSE;searchunit=KM;poidataset=TA],UserId=12345678,TransactionId=1380643968427,ServiceAgent=[Vendor=TELENAV,Service=TELENAV_COSE,DataSet=TA],UgcPreference=,AutoExpandSearchRadius=true,IsDebugRequest=true,ClientName=PoiDemo,ClientVersion=1.0,ContextProperty=]";
        parser.parse(line);
        
        return;
    }
}
    
    
    
    
    