package com.johnyeager.rtrading.rolimonsmonitor.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "rolimons.filter")
public class FiltrosAnuncio {

    private int minValue = 1;
    private int maxValue = 9999999;

    private Integer minOfferingItems = null;
    private Integer maxOfferingItems = null;
    private Integer minRequestingItems = null;
    private Integer maxRequestingItems = null;
    private List<String> offeringItemsFilter = new ArrayList<>();
    private List<String> requestingItemsFilter = new ArrayList<>();

    private final Map<String, Boolean> tags = new HashMap<>();

    public FiltrosAnuncio() {
        tags.put("any", null);
        tags.put("demand", null);
        tags.put("rares", null);
        tags.put("wishlist", null);
        tags.put("robux", null);
        tags.put("upgrade", null);
        tags.put("downgrade", null);
        tags.put("adds", null);
        tags.put("projecteds", null);
    }

    public int getMinValue() { return minValue; }
    public void setMinValue(int minValue) { this.minValue = minValue; }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }

    public Map<String, Boolean> getTags() { return tags; }
    public void setTags(Map<String, Boolean> tags) { this.tags.putAll(tags); }

    public Integer getMinOfferingItems() { return minOfferingItems; }
    public void setMinOfferingItems(Integer minOfferingItems) { this.minOfferingItems = minOfferingItems; }

    public Integer getMaxOfferingItems() { return maxOfferingItems; }
    public void setMaxOfferingItems(Integer maxOfferingItems) { this.maxOfferingItems = maxOfferingItems; }

    public Integer getMinRequestingItems() { return minRequestingItems; }
    public void setMinRequestingItems(Integer minRequestingItems) { this.minRequestingItems = minRequestingItems; }

    public Integer getMaxRequestingItems() { return maxRequestingItems; }
    public void setMaxRequestingItems(Integer maxRequestingItems) { this.maxRequestingItems = maxRequestingItems; }

    public List<String> getOfferingItemsFilter() { return offeringItemsFilter; }
    public void setOfferingItemsFilter(List<String> offeringItemsFilter) { this.offeringItemsFilter = offeringItemsFilter; }

    public List<String> getRequestingItemsFilter() { return requestingItemsFilter; }
    public void setRequestingItemsFilter(List<String> requestingItemsFilter) { this.requestingItemsFilter = requestingItemsFilter; }
}