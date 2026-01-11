package com.example.shopping.service;

import com.example.shopping.model.Product;
import com.example.shopping.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String CSV_SEPARATOR = ";";

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    public void loadProductReferentialFromFlatFile(InputStream stream){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                Product product = new Product();
                product.setProductName(line.substring(0, 9).trim());
                product.setProductPrice(Double.parseDouble(line.substring(9, 13).trim()));

                productRepository.save(product);
            }

        } catch (IOException ex) {
            log.error("Error during file processing {}", ex.getMessage());
        }
    }


}
