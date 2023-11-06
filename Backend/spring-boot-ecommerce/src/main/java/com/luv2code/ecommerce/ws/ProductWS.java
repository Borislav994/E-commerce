package com.luv2code.ecommerce.ws;

import com.luv2code.ecommerce.entity.PagedData;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductWS {

    @Autowired
    public ProductService productService;

    @GetMapping("/products")
    public List<Product> findAll(){

        return  productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public Product findById(@PathVariable int productId) {

        Product product = productService.findById(productId);
        if(product == null) {
            throw new RuntimeException("Product id not found - " + productId);
        }
        return  product;
    }


    @PostMapping("/products")
    public Product addProduct(@RequestBody Product theProduct) {

        // just in case if someone pass an id in JSON.. set id to 0
        // this is to force save instead of update
        theProduct.setId(0);

        productService.save(theProduct);

        return theProduct;
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product theProduct) {

        productService.save(theProduct);

        return theProduct;
    }

    @DeleteMapping("products/{productId}")
    public String deleteProduct(@PathVariable int productId) {

        Product tempProduct = productService.findById(productId);

        if(tempProduct == null) {

            throw new RuntimeException("Product id not found - " + productId);
        }

        productService.deleteById(productId);

        return "Deleted product id - " + productId;
    }

    /*@GetMapping("/products/category")
    public List<Product> findByCategoryId(@RequestParam Integer categoryId, ) {

        List<Product> products = productService.findByCategoryId(categoryId);
        if(products.isEmpty()) {
            throw new RuntimeException("Category id not found - " + categoryId);
        }
        return  products;
    }*/

    @GetMapping("/products/search/findByCategoryId")
    public PagedData findByCategoryId(@RequestParam Integer categoryId,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        return productService.findByCategoryId(categoryId, page, size);
    }
/*
    @GetMapping("/products/category")
    public Page<Product> findByCategoryId(@RequestParam int categoryId, Pageable pageable) {
        return productService.findByCategoryId(categoryId, pageable);
    }*/




    @GetMapping("/products/search/findByNameContaining")
    public PagedData findByNameContaining(@RequestParam String name,
                                          @RequestParam int page,
                                          @RequestParam int size) {

       return productService.findByNameContaining(name, page, size);

    }



}
