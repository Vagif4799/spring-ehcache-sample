package com.vagif.controller;

import com.vagif.service.ProductService;
import com.vagif.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Method will bring details via product id
     * @param productId
     * @return
     */
    @GetMapping(value = "/{product-id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable("product-id") int productId) {
        Optional<Product> product = productService.getProductById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
    }

    /**
     * Method will update product via product id
     * @param productId
     * @param productName
     * @return
     */
    @PutMapping(value = "/{product-id}/{product-name}")
    public ResponseEntity<Product> updateTicketById(@PathVariable("product-id") int productId, @PathVariable("product-name") String productName) {
        Optional<Product> product = productService.getProductById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(productService.updateProductById(product.get(), productName), HttpStatus.OK);
    }

    /**
     * Method that will delete product via its id
     * @param productId
     * @return
     */
    public ResponseEntity<Product> deleteProductById(@PathVariable("product-id") int productId){
        Optional<Product> product = productService.getProductById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProductById(productId);
        return new ResponseEntity<Product>(HttpStatus.ACCEPTED);
    }
}
