package com.example.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.entity.StockData;

public class StockDataProcessor implements ItemProcessor<StockData, StockData>{

	@Override
	public StockData process(StockData item) throws Exception {
		
		//logic 
		
		return null;
	}

}
