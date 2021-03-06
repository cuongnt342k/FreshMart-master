package com.dt.ducthuygreen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dt.ducthuygreen.dto.ItemDTO;
import com.dt.ducthuygreen.services.IItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private IItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> getAllItem() {
        return ResponseEntity.status(200).body(itemService.getAllItem());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAllItemById(@PathVariable("id") Long id) {
//        return ResponseEntity.status(200).body(itemService.getItemById(id));
//    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllItemByUserName(@PathVariable("userName") String userName) {
        return ResponseEntity.status(200).body(itemService.getItemByUserName(userName));
    }

    @PostMapping("/{productId}/{userName}")
    public ResponseEntity<?> createNewItem(
            @PathVariable("productId") Long productId,
            @PathVariable("userName") String userName,
            @RequestBody ItemDTO itemDTO) {
        return ResponseEntity.status(201).body(
                itemService.creatNewItem(itemDTO, productId, userName)
        );
    }

    @DeleteMapping("/remove/{id}")
    public String deleteItemById(@PathVariable("id") Long id) {
        itemService.deleteItemById(id);
        return "Successfully";
    }
    @PutMapping("/update")
    public String updateItem(@RequestBody ItemDTO itemDTO) {
        if (itemService.update(itemDTO)) {
            return "Successfully";
        }
        return "Error";
    }
}
