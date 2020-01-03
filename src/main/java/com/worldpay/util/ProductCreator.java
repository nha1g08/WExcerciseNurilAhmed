package com.worldpay.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.worldpay.model.Product;
import com.worldpay.service.ProductService;

@Component
public class ProductCreator {
	
	@Autowired
	ProductService productService;
	
	public void createProducts() throws IOException{
		Resource resource = new ClassPathResource("productList.csv");
		InputStream is = resource.getInputStream();
        BufferedReader br = null;
        String line = "";
        int linenumber=0;
        String csvSplitBy = ",";
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
            	if(linenumber == 0) {
            		linenumber++;
            		continue;
            	}
				Product product;
            	try {
            		String[] parts = line.split(csvSplitBy);	
                	Long id=Long.parseLong(parts[0]);
                	String description = parts[1];
                    product = new Product(id, description, null);
                	productService.create(product);
            	}catch(Exception e){
            		e.printStackTrace();
            		System.out.println("Check csv input");
            		continue;
            	}
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
