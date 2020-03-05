package com.vagif.service;

import com.vagif.DAO.ProductRepo;
import com.vagif.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;
    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Method that will bring product via it's id
     * @param productId
     * @return
     * @Cachable annotation adds the catching behaviour
     * If it receives multiple requests, then the results are shared from cached storage
     */
    @Cacheable(value = "productsCache", key = "#p0")
    public Optional<Product> getProductById(int productId) {
        simulateSlowService();
        return productRepo.findById(productId);
    }

    /**
     * Method that will update product via product id
     * @param product
     * @param productName
     * @return
     * @CachePut updates the cached value
     */
    @CachePut(value = "productsCache")
    public Product updateProductById(Product product, String productName) {
        product.setName(productName);
        productRepo.save(product);
        return product;
    }

    /**
     * Method that will delete product via its id
     * @param productId
     * @CacheEvict annotation removes entries from cached storage
     */
    @CacheEvict(value = "productsCache", key = "#p0")
    public void deleteProductById(int productId) {
        productRepo.deleteById(productId);
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
