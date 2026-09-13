package com.sujal.shopingwebsitebackend1.Service;

import com.sujal.shopingwebsitebackend1.Model.Product;
import com.sujal.shopingwebsitebackend1.Repo.productsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class productService {
    @Autowired
    private productsRepo repo;

    public List<Product> giveProducts(){
        return repo.findAll();
    }

    public Product getproduct(int id) {
        return repo.findById(id).orElse(new Product());
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImagename(image.getOriginalFilename());
        product.setImagetype(image.getContentType());
        product.setImagedata(image.getBytes());
        return repo.save(product);
    }

    public Product getimage(int id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteitem(int id) {
        repo.deleteById(id);
    }

    public Product putProduct(Product product, MultipartFile imagefile) throws IOException {
        product.setImagename(imagefile.getName());
        product.setImagetype(imagefile.getContentType());
        product.setImagedata(imagefile.getBytes());
        return repo.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> searchKeyword(String keyword) {
        return repo.searchKeyword(keyword);
    }
}
