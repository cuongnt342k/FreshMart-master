package com.dt.ducthuygreen.services;

import com.dt.ducthuygreen.dto.ProductDTO;
import com.dt.ducthuygreen.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IProductServices {
    Product create(ProductDTO productDTO, Long categoryId, MultipartFile file);

    Page<Product> getAllProduct(Pageable pageable);

    Page<Product> getAllProductBySearch(Pageable pageable, String textSearch);

    Page<Product> getAllProductByCateID(Pageable pageable, Long id);

    Product getProductById(Long id);

    Product changeIamge(Long id, MultipartFile file);

    Product save(Product product);

    Product editProductById(ProductDTO productDTO, Long categoryId, MultipartFile file);

    boolean deleteProductById(Long id);
}
