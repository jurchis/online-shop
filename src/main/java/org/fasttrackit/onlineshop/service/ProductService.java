package org.fasttrackit.onlineshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.GetProductsRequest;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    // IOC Inversion of Control
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;



    // Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(SaveProductRequest request) {

        LOGGER.info("Creating product {} ", request);
        Product product = objectMapper.convertValue(request, Product.class);

        //below statements are doing the same that the above ObjectMapper above is doing
//        Product product = new Product();
//        product.setDescription(request.getDescription());
//        product.setName(request.getName());
//        product.setPrice(request.getPrice());
//        product.setQuantity(request.getQuantity());
//        product.setImageUrl(request.getImageUrl());

        return productRepository.save(product);
    }

    public Product getProduct(long id) {

        LOGGER.info("Retrieving Product {}", id);

        //using optional
        return productRepository.findById(id)
                //lambda expression
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product " + id + " does not exist."));
    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product {}: {}", id, request);

        Product retrievedProduct = getProduct(id);

        //from spring framework
        BeanUtils.copyProperties(request, retrievedProduct);

        return productRepository.save(retrievedProduct);
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products: {}", request);
        if (request != null && request.getPartialName() !=null && request.getMinimumQuantity() != null){
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(), request.getMinimumQuantity(), pageable);
        } else if (request != null && request.getPartialName() != null) {
            return productRepository.findByNameContaining(request.getPartialName(), pageable);
        } else{
            return productRepository.findAll(pageable);
        }
    }


    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}:", id);
        productRepository.deleteById(id);
        LOGGER.info("Product id: {} has been deleted", id);
    }
}


