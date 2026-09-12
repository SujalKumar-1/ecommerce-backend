package com.sujal.shopingwebsitebackend1.Controller;

import com.sujal.shopingwebsitebackend1.Model.Product;
import com.sujal.shopingwebsitebackend1.Service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class products {

    @Autowired
    private productService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> products(){
        return new ResponseEntity<>(service.giveProducts(), HttpStatus.ACCEPTED);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> product(@PathVariable("id") int id){
        Product product = service.getproduct(id);
        if(product.getId()>0) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> productimage(@PathVariable("id") int id){
        Product product = service.getimage(id);
            if(product.getId()>0) {
                return new ResponseEntity<>(product.getImagedata(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
    @PostMapping("/product")
    public ResponseEntity<?> addproduct(@RequestPart("product") Product product, @RequestPart("imagefile") MultipartFile imagefile){
        System.out.println("ADD PRODUCT CONTROLLER CALLED");
        try {
            Product result = service.addProduct(product,imagefile);
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        if(id>0){
            service.deleteitem(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PutMapping("product/{id}")
    public ResponseEntity<?> UpdateProduct(@PathVariable("id") int id,@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile imageFile){
        try {
            Product result = service.putProduct(product,imageFile);
            return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchKeyword(@RequestParam String keyword){
        List<Product> products = service.searchKeyword(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}

