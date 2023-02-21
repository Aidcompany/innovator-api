package com.innovator.innovator.controllers;

import com.innovator.innovator.HelpfullyService;
import com.innovator.innovator.models.User;
import com.innovator.innovator.models.product.Product;
import com.innovator.innovator.payload.request.ProductRequest;
import com.innovator.innovator.payload.response.MessageResponse;
import com.innovator.innovator.repository.BlockRepository;
import com.innovator.innovator.services.ProductService;
import com.innovator.innovator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final BlockRepository blockRepository;

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId) {
        Optional<Product> product = productService.findById(productId);
        if (product.isEmpty())
            return new ResponseEntity<>(new MessageResponse("Product not found"), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(product.get());

    }

    @GetMapping("/getProducts/{clientId}")
    public ResponseEntity<?> getProductsByClientId(@PathVariable int clientId) {
        return ResponseEntity.ok(productService.findAllByOwnerClientId(clientId));
    }

    @PostMapping(value = "/addProduct/{clientId}")
    public ResponseEntity<?> addProduct(@PathVariable int clientId,
                                        @ModelAttribute ProductRequest productRequest) throws FileUploadException {
        User user = userService.findById(clientId).get();

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setBlocks(productRequest.getBlocks());
        product.setOwner(user);
        product.setAvatar(HelpfullyService.getCurrentBaseUrl() + "/api/news/photo/" + productRequest.getAvatar().getOriginalFilename());

        productService.saveAvatarProduct(productRequest.getAvatar());

        product = productService.save(product);

        for (var block : productRequest.getBlocks()) {
            block.setProduct(product);
            blockRepository.save(block);
        }

        return ResponseEntity.ok(product);
    }

    @PutMapping("/acceptProduct/{productId}/{clientId}")
    public ResponseEntity<?> acceptProduct(@PathVariable int productId, @PathVariable int clientId) {
        Optional<Product> product = productService.findById(productId);
        Optional<User> user = userService.findById(clientId);
        if (product.isEmpty()) return new ResponseEntity<>(new MessageResponse("Product not found"), HttpStatus.NOT_FOUND);
        if (user.isEmpty()) return new ResponseEntity<>(new MessageResponse("User not found"), HttpStatus.NOT_FOUND);

        product.get().getShared().add(user.get());

        return ResponseEntity.ok(productService.save(product.get()));
    }

    @PutMapping("/removeProductUser/{productId}/{clientId}")
    public ResponseEntity<?> removeProductUser(@PathVariable int productId, @PathVariable int clientId) {
        Optional<Product> product = productService.findById(productId);
        Optional<User> user = userService.findById(clientId);
        if (product.isEmpty()) return new ResponseEntity<>(new MessageResponse("Product not found"), HttpStatus.NOT_FOUND);
        if (user.isEmpty()) return new ResponseEntity<>(new MessageResponse("User not found"), HttpStatus.NOT_FOUND);

        product.get().getShared().remove(user.get());

        return ResponseEntity.ok(productService.save(product.get()));
    }

//    @DeleteMapping("/deleteProduct/{id}")
//    public ResponseEntity<MessageResponse> deleteProductById(@PathVariable int id ) {
//        productService.deleteById(id);
//        return ResponseEntity.ok(new MessageResponse("Product deleted!!!"));
//    }
}
