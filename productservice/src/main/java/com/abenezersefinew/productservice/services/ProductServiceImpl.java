package com.abenezersefinew.productservice.services;

import com.abenezersefinew.productservice.entities.Product;
import com.abenezersefinew.productservice.exceptions.ProductNotFoundException;
import com.abenezersefinew.productservice.exceptions.ProductQuantityExceedException;
import com.abenezersefinew.productservice.models.ProductRequestModel;
import com.abenezersefinew.productservice.models.ProductResponseModel;
import com.abenezersefinew.productservice.repositories.ProductRepository;
import com.abenezersefinew.productservice.services.interfaces.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    private  ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Long create(ProductRequestModel productRequestModel) {
        log.info("Creating product...");

        Product product = Product.builder()
                        .productName(productRequestModel.getName())
                        .quantity(productRequestModel.getQuantity())
                        .price(productRequestModel.getPrice())
                        .build();

        Product savedProduct = productRepository.saveAndFlush(product);
        log.info("Product created.");

        return savedProduct.getProductId();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseModel getById(Long id) {
        log.info("Finding product by id...");
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found.", "NOT_FOUND"));
        log.info("Product found.");

        ProductResponseModel productResponseModel = new ProductResponseModel();
        BeanUtils.copyProperties(product, productResponseModel);

        return productResponseModel;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reducing product quantity from inventory...");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found.", "NOT_FOUND"));

        if(product.getQuantity() < quantity) {
            throw new ProductQuantityExceedException("Product quantity exceeds the quantity in the inventory.", "BAD_REQUEST");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product quantity reduced.");
    }
}
