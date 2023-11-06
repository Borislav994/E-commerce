package com.luv2code.ecommerce.ws;

import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.jpa.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductCategoryWS {

    @Autowired
    public ProductCategoryService categoryService;

    @GetMapping("/product-category")
    public List<ProductCategory> findAll(){

        return  categoryService.findAll();
    }

    @GetMapping("/product-category/{categoryId}")
    public ProductCategory findById(@PathVariable int categoryId) {

        ProductCategory category = categoryService.findById(categoryId);
        if(category == null) {
            throw new RuntimeException("ProductCategory id not found - " + categoryId);
        }
        return  category;
    }

    @PostMapping("/product-category")
    public ProductCategory addProductCategory(@RequestBody ProductCategory theProductCategory) {

        // just in case if someone pass an id in JSON... set id to 0
        // this is to force save instead of update
        theProductCategory.setId(0);

        categoryService.save(theProductCategory);

        return theProductCategory;
    }

    @PutMapping("/product-category")
    public ProductCategory updateProductCategory(@RequestBody ProductCategory theProductCategory) {

        categoryService.save(theProductCategory);

        return theProductCategory;
    }

    @DeleteMapping("/product-category/{categoriesId}")
    public String deleteProduct(@PathVariable int categoriesId) {

        ProductCategory tempProductCategory = categoryService.findById(categoriesId);

        if(tempProductCategory == null) {

            throw new RuntimeException("ProductCategory id not found - " + categoriesId);
        }

        categoryService.deleteById(categoriesId);

        return "Deleted product id - " + categoriesId;
    }

}
