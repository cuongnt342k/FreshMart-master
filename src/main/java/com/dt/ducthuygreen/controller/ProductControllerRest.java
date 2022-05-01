package com.dt.ducthuygreen.controller;

import com.dt.ducthuygreen.dto.ProductDTO;
import com.dt.ducthuygreen.services.IProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/products")
public class ProductControllerRest {
    @Autowired
    private IProductServices IProductServices;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllProduct(Pageable pageable, @RequestParam(required = false) String textSearch) {
        if (textSearch != null)
            return ResponseEntity.status(200).body(IProductServices.getAllProductBySearch(pageable, textSearch));
        return ResponseEntity.status(200).body(IProductServices.getAllProduct(pageable));
    }

//    @GetMapping("/{id}")
//    public String getProductById(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("product", productServices.getProductById(id));
//        return "detail";
//    }

    @GetMapping("/category")
    @ResponseBody
    public ResponseEntity<?> getProductByCateId(Pageable pageable, @RequestParam(required = false) Long cateID) {
        if (cateID == null) {
            return ResponseEntity.status(200).body(IProductServices.getAllProduct(pageable));
        }
        return ResponseEntity.status(200).body(IProductServices.getAllProductByCateID(pageable, cateID));
    }

    @PostMapping("/addProduct")
    @ResponseBody
    public ResponseEntity<?> createNewProduct(
            @RequestParam(name = "category", required = false) Long cateId,
            @Valid @ModelAttribute ProductDTO productDTO,
            @RequestParam(name = "multipartFile", required = false) MultipartFile image
    ) {
        return ResponseEntity.status(201).body(IProductServices.create(productDTO, cateId, image));
    }

    @PostMapping("/{productId}/image")
    @ResponseBody
    public ResponseEntity<?> changeImage(
            @PathVariable("productId") Long productId,
            @RequestParam(name = "image", required = false) MultipartFile image
    ) {
        return ResponseEntity.status(201).body(IProductServices.changeIamge(productId, image));
    }

    @PutMapping("/editProduct")
    @ResponseBody
    public ResponseEntity<?> editProductById(
            @RequestParam(name = "category", required = false) Long cateId,
            @Valid @ModelAttribute ProductDTO productDTO,
            @RequestParam(name = "multipartFile", required = false) MultipartFile image
    ) {
        return ResponseEntity.status(200)
                .body(IProductServices.editProductById(productDTO, cateId, image));
    }

    @DeleteMapping("/deleteProduct/{id}")
    @ResponseBody
    public String deleteProductById(@PathVariable("id") Long id) {
        if (IProductServices.deleteProductById(id)) return "Successfully";
        return "Error";
    }

}
