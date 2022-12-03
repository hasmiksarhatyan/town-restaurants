package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsweb.serviceWeb.ProductCategoryService;
import am.itspace.townrestaurantsweb.serviceWeb.ProductService;
import am.itspace.townrestaurantsweb.serviceWeb.RestaurantService;

import am.itspace.townrestaurantsweb.utilWeb.FileUtil;
import am.itspace.townrestaurantsweb.utilWeb.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final RestaurantService restaurantService;
    private final ProductCategoryService productCategoryService;

    @GetMapping
    public String products(@RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "sort", defaultValue = "") String sort,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "15") int size,
                           ModelMap modelMap) {

        Page<ProductOverview> products = productService.sortProduct(PageRequest.of(page - 1, size), sort, id);
        modelMap.addAttribute("categories", productCategoryService.findAll());
        modelMap.addAttribute("products", products);
        modelMap.addAttribute("pageNumbers", PageUtil.getTotalPages(products));
        return "products";
    }

    @GetMapping("/my")
    public String productsMy(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "6") int size,
                             ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        Page<ProductCategoryOverview> categories = productCategoryService.findAll(PageRequest.of(page - 1, size));
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("pageNumbers", PageUtil.getTotalPages(categories));
        if (currentUser.getUser().getRole() == Role.MANAGER) {
            List<ProductOverview> products = productService.findAll();
            modelMap.addAttribute("products", products);

        }
        if (currentUser.getUser().getRole() == Role.RESTAURANT_OWNER) {
            List<ProductOverview> productsMy = productService.findProductByUser(currentUser.getUser());
            modelMap.addAttribute("products", productsMy);
        }
        return "manager/products";
    }


    @GetMapping("/add")
    public String addProductPage(ModelMap modelMap) {

        modelMap.addAttribute("restaurants", restaurantService.findAll());
        modelMap.addAttribute("categories", productCategoryService.findAll());
        return "manager/addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute CreateProductDto dto, @RequestParam("productImage") MultipartFile[] files,
                             @AuthenticationPrincipal CurrentUser currentUser) throws IOException {

        productService.addProduct(dto, files,currentUser.getUser());
        return "redirect:/products/my";
    }


    @GetMapping(value = "/getImages", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        return productService.getProductImage(fileName);
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") int id, ModelMap modelMap) {

        modelMap.addAttribute("categories", productCategoryService.findAll());
        modelMap.addAttribute("restaurants", restaurantService.findAll());
        modelMap.addAttribute("product", productService.findById(id));
        return "manager/editProduct";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute EditProductDto dto,
                              @RequestParam("productImage") MultipartFile[] files) throws IOException {

        productService.editProduct(dto, id, files);
        return "redirect:/products/my";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {

        productService.deleteProduct(id, currentUser.getUser());
        return "redirect:/products/my";

    }
}