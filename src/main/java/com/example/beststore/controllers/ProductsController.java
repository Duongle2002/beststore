package com.example.beststore.controllers;

import com.example.beststore.models.Product;
import com.example.beststore.services.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;

    @GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute("product") Product product,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        // Xử lý file upload
        if (!imageFile.isEmpty()) {
            try {
                // Đặt tên file ngẫu nhiên để tránh trùng lặp
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                // Đường dẫn lưu trữ file
                String uploadDir = "E:/beststore/public/images/";
                File file = new File(uploadDir + fileName);
                imageFile.transferTo(file);

                // Lưu đường dẫn vào cơ sở dữ liệu
                product.setImageFilename(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Thêm xử lý lỗi nếu cần
            }
        }

        product.setCreatedAt(new Date());
        repo.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") int id,
                                @ModelAttribute("product") Product product,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        // Xử lý file upload mới nếu có
        if (!imageFile.isEmpty()) {
            try {
                // Đặt tên file ngẫu nhiên để tránh trùng lặp
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                // Đường dẫn lưu trữ file
                String uploadDir = "E:/beststore/public/images/";
                File file = new File(uploadDir + fileName);
                imageFile.transferTo(file);

                // Lưu đường dẫn mới vào cơ sở dữ liệu
                product.setImageFilename(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Thêm xử lý lỗi nếu cần
            }
        } else {
            // Giữ lại hình ảnh cũ nếu không tải lên hình ảnh mới
            Product existingProduct = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
            product.setImageFilename(existingProduct.getImageFilename());
        }

        product.setId(id);
        product.setCreatedAt(new Date()); // Cập nhật lại ngày tạo (có thể giữ nguyên ngày cũ nếu cần)
        repo.save(product);
        return "redirect:/products";
    }
}
