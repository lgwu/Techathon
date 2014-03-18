package com.scout.lol.model;

import java.util.List;

/**
 *  
 * @author lgwu
 * @date 2014-3-18
 */

public class CoseLog extends Log {
	private String logId;
	
    private String date = null;
    private String timestamp = null;
    private String requestId = null;
    private String query = null;
    
    // category param
    private int categoryHierarchyId = -1;
    private String categoryVersion = null;
    private String categoryId = null;
    
    // search type
    private String searchType = null;
    
    // search area
    private String areaType = null;
    private String anchor = null;
    private String city = null;
    private String countryCode = null;
    private String postalCode = null;
    private String province = null;
    private String radius = null;
    private String routePoints = null;
    
    // content string
    private String deviceCarrier = null;
    private String product = null;
    private String carrier = null;
    
    private Long userId = 0L;
    
    private String isDebug = null; 
	
	private List<String> value;

	public CoseLog() {
		super();
	}

	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * @return the value
	 */
	public List<String> getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(List<String> value) {
		this.value = value;
	}
	/**
	 * @param logId
	 * @param date
	 * @param value
	 */

    /**
     * @return the _date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * @param date the _date to set
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * @return the requestId
     */
    public String getRequestId()
    {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    /**
     * @return the query
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query)
    {
        this.query = query;
    }

    /**
     * @return the categoryHierarchyId
     */
    public int getCategoryHierarchyId()
    {
        return categoryHierarchyId;
    }

    /**
     * @param categoryHierarchyId the categoryHierarchyId to set
     */
    public void setCategoryHierarchyId(int categoryHierarchyId)
    {
        this.categoryHierarchyId = categoryHierarchyId;
    }

    /**
     * @return the categoryVersion
     */
    public String getCategoryVersion()
    {
        return categoryVersion;
    }

    /**
     * @param categoryVersion the categoryVersion to set
     */
    public void setCategoryVersion(String categoryVersion)
    {
        this.categoryVersion = categoryVersion;
    }

    /**
     * @return the categoryId
     */
    public String getCategoryId()
    {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    /**
     * @return the searchType
     */
    public String getSearchType()
    {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(String searchType)
    {
        this.searchType = searchType;
    }

    /**
     * @return the areaType
     */
    public String getAreaType()
    {
        return areaType;
    }

    /**
     * @param areaType the areaType to set
     */
    public void setAreaType(String areaType)
    {
        this.areaType = areaType;
    }

    /**
     * @return the anchor
     */
    public String getAnchor()
    {
        return anchor;
    }

    /**
     * @param anchor the anchor to set
     */
    public void setAnchor(String anchor)
    {
        this.anchor = anchor;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode()
    {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode()
    {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    /**
     * @return the province
     */
    public String getProvince()
    {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province)
    {
        this.province = province;
    }

    /**
     * @return the radius
     */
    public String getRadius()
    {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(String radius)
    {
        this.radius = radius;
    }

    /**
     * @return the route_points
     */
    public String getRoutePoints()
    {
        return routePoints;
    }

    /**
     * @param routePoints the route_points to set
     */
    public void setRoutePoints(String routePoints)
    {
        this.routePoints = routePoints;
    }

    /**
     * @return the deviceCarrier
     */
    public String getDeviceCarrier()
    {
        return deviceCarrier;
    }

    /**
     * @param deviceCarrier the deviceCarrier to set
     */
    public void setDeviceCarrier(String deviceCarrier)
    {
        this.deviceCarrier = deviceCarrier;
    }

    /**
     * @return the product
     */
    public String getProduct()
    {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product)
    {
        this.product = product;
    }

    /**
     * @return the carrier
     */
    public String getCarrier()
    {
        return carrier;
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
    }

    /**
     * @return the userId
     */
    public Long getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
     * @return the isDebug
     */
    public String isDebug()
    {
        return isDebug;
    }

    /**
     * @param isDebug the isDebug to set
     */
    public void setDebug(String isDebug)
    {
        this.isDebug = isDebug;
    }	
}
