package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    // IOC Inversion of Control
    private final ProductRepository productRepository;

    // Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {

        LOGGER.info("Creating product {} ", request);

        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

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

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}:",id);
        productRepository.deleteById(id);
        LOGGER.info("Product id: {} has been deleted",id);
    }
}
