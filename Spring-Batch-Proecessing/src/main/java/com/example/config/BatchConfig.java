package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.entity.StockData;
import com.example.repository.StockDataRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private StockDataRepository stockDataRepository;
	
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	
	//create reader
	@Bean
	public FlatFileItemReader<StockData> stockDataReader(){
		
		FlatFileItemReader<StockData> itemReader = new FlatFileItemReader<>();
		
		itemReader.setResource(new FileSystemResource("/Users/swapnilk/Desktop/GITHUB/Spring-Batch/Spring-Batch-Proecessing/src/main/resource/TCS.NSE.csv"));
		itemReader.setName("stockDataReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
		
	}

	private LineMapper<StockData> lineMapper() {
		//
		DefaultLineMapper<StockData> lineMapper = new DefaultLineMapper<>();
		
		//
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		
		lineTokenizer.setStrict(false);
		
		lineTokenizer.setNames("Date","Open","High","Low","Close","Adjusted_close","Volume");

		//
		BeanWrapperFieldSetMapper<StockData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

		fieldSetMapper.setTargetType(StockData.class);
		
		//
		lineMapper.setLineTokenizer(lineTokenizer);
		
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
	}

	// create processor
	@Bean
	public StockDataProcessor stockDataProcessor() {
		return new StockDataProcessor();
	}
	
	// create writer
	@Bean
	public RepositoryItemWriter<StockData> stockDataWriter(){
		
		RepositoryItemWriter<StockData> repositoryItemWriter= new RepositoryItemWriter<>();
		
		repositoryItemWriter.setRepository(stockDataRepository);

		repositoryItemWriter.setMethodName("save");
		
		return repositoryItemWriter;
	}
	
	// create step
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step-1").<StockData,StockData>chunk(10)
				.reader(stockDataReader())
				.processor(stockDataProcessor())
				.writer(stockDataWriter())
				.build();
	}
	
	// create job
	@Bean
	public Job job() {
		
		return jobBuilderFactory.get("importStockDataJob")
				.flow(step1())
				.end()
				.build();
		
	}
}
