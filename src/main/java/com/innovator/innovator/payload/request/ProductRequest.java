package com.innovator.innovator.payload.request;

import com.innovator.innovator.models.product.Block;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequest {

    private String name;
    private MultipartFile avatar;
    private List<Block> blocks;
}
