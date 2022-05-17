package com.dt.ducthuygreen.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dt.ducthuygreen.Utils.ConvertObject;
import com.dt.ducthuygreen.Utils.UploadFile;
import com.dt.ducthuygreen.dto.ProductDTO;
import com.dt.ducthuygreen.entities.Category;
import com.dt.ducthuygreen.entities.Product;
import com.dt.ducthuygreen.exception.NotFoundException;
import com.dt.ducthuygreen.repos.ProductRepository;
import com.dt.ducthuygreen.services.ICategoryService;
import com.dt.ducthuygreen.services.IProductServices;

@Service
@Log4j2
public class ProductServicesImpl implements IProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICategoryService categoryService;

    private UploadFile uploadFile = new UploadFile();

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> optional = productRepository.findById(productId);
        if (!optional.isPresent()) {
            throw new NotFoundException("Can not find product by id: " + productId);
        }
        return optional.get();
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.getAllByDeletedIsFalse(pageable);
    }

    @Override
    public Page<Product> getAllProductBySearch(Pageable pageable, String textSearch) {
        return productRepository.findProductsByProductNameContainsAndDeletedIsFalse(pageable, textSearch);
    }

    @Override
    public Page<Product> getAllProductByCateID(Pageable pageable, Long id) {
        return productRepository.getAllByDeletedIsFalseAndCategoryId(pageable, id);
    }

    @Override
    public Page<Product> getAllProductByListCate(Pageable pageable, List<Long> listId) {
        List<Category> categoryList = new ArrayList<>();
        listId.forEach(id -> {
            categoryList.add(categoryService.findById(id));
        });
        return productRepository.findProductsByCategoryInAndDeletedFalse(pageable, categoryList);
    }

    @Override
    public Page<Product> getAllProductByPrice(Pageable pageable, Long first, Long second) {
        return productRepository.findProductsByPriceBetweenAndDeletedFalse(pageable, first, second);
    }

    @Override
    public Product create(ProductDTO productDTO, Long categoryId, MultipartFile file) {
        Product product = new Product();
        productDTO.setSold(0);
        Category category = categoryService.findById(categoryId);

        if (category == null) {
            throw new NotFoundException("Can not find category id: " + categoryId);
        }

        product.setCategory(category);
        if (file != null) {
            product.setImage(uploadFile.getUrlFromFile(file));
        }

        return productRepository.save(ConvertObject.convertProductDTOToProduct(product, productDTO));
    }

    @Override
    public Product editProductById(ProductDTO productDTO, Long categoryId, MultipartFile file) {
        Product product = getProductById(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        if (file != null) {
            product.setImage(uploadFile.getUrlFromFile(file));
        }
        return productRepository.save(ConvertObject.convertProductDTOToProduct(product, productDTO));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProductById(Long id) {
        Product product = getProductById(id);
        try {
            product.setDeleted(true);
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            log.debug(e);
        }
        return false;
    }

    @Override
    public Product changeIamge(Long id, MultipartFile file) {
        if (file == null) {
            return null;
        }

        Product product = getProductById(id);
        if (product == null) {
            throw new NotFoundException("Can not find this product");
        }

        if (product.getImage() != null) {
            uploadFile.removeImageFromUrl(product.getImage());
        }

        product.setImage(uploadFile.getUrlFromFile(file));

        return productRepository.save(product);
    }
}
