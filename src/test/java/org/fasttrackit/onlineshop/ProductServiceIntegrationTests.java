package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.steps.ProductSteps;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequest_thenProductIsSaved() {
        //To do not repeat ourselves we CTRL+ALT+M / refactor->extract ->method
        productSteps.createProduct();
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException() {
        SaveProductRequest request = new SaveProductRequest();
        // leaving request properties with default null values
        //to validate the negative flow

        productService.createProduct(request);
    }

    @Test
    //is recommended for a test to be independent from another
    //independent tests can be run in parallel
    public void testGetProduct_whenExistingProduct_thenReturnProduct() {
        Product createdProduct = productSteps.createProduct();

        Product retrievedProduct = productService.getProduct(createdProduct.getId());

        //actual  vs expected
        assertThat(retrievedProduct, notNullValue());
        assertThat(retrievedProduct.getId(), is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(), is(createdProduct.getName()));
        assertThat(retrievedProduct.getDescription(), is(createdProduct.getDescription()));
        assertThat(retrievedProduct.getPrice(), is(createdProduct.getPrice()));
        assertThat(retrievedProduct.getQuantity(), is(createdProduct.getQuantity()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenNotExistingProduct_thenThrowResourceNotFound() {
        productService.getProduct(0);
    }

    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() {
        Product createdProduct = productSteps.createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(createdProduct.getName() + " updated");
        request.setDescription(createdProduct.getDescription() + " updated");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 10);

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getDescription(), is(request.getDescription()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteProduct_whenExistingProduct_thenProductIsDeleted() {
        Product product = productSteps.createProduct();

        productService.deleteProduct(product.getId());

        productService.getProduct(product.getId());

    }
}
