package com.innovator.innovator.services;

import com.innovator.innovator.MultipartUploadFile;
import com.innovator.innovator.models.product.Product;
import com.innovator.innovator.repository.ProductRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Value("${upload.path.photo}")
    private String uploadPath;

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveAvatarProduct(MultipartFile avatar) throws FileUploadException {
        new MultipartUploadFile(uploadPath).saveFile(avatar);
    }

    public void flush() {
        productRepository.flush();
    }

    public List<Product> findAllByOwnerClientId(int id) {
        return productRepository.findAllByOwnerClientId(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
