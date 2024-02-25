package com.example.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.entity.StockData;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	
	//create reader
	
	public FlatFileItemReader<StockData> reader(){
		
		FlatFileItemReader<StockData> itemReader = new FlatFileItemReader<>();
		
		itemReader.setResource(new FileSystemResource("/Users/swapnilk/Desktop/GITHUB/Spring-Batch/data/TCS.NSE.csv"));
		itemReader.setName("stockDataReader");
		itemReader.setLinesToSkip(1);
		return itemReader;
		
	}

}
