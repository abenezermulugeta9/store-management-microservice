package com.abenezersefinew.productservice.controllers;

import com.abenezersefinew.productservice.models.ProductRequestModel;
import com.abenezersefinew.productservice.models.ProductResponseModel;
import com.abenezersefinew.productservice.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseModel> getById(@PathVariable("id") Long id) {
        ProductResponseModel productResponseModel = productService.getById(id);
        return new ResponseEntity<>(productResponseModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ProductRequestModel productRequestModel) {
        Long productId = productService.create(productRequestModel);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/reduce-quantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity) {
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



