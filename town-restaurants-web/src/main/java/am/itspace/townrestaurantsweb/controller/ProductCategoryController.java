package am.itspace.townrestaurantsweb.controller;

import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantsweb.serviceWeb.ProductCategoryService;
import am.itspace.townrestaurantsweb.utilWeb.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productCategories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public String productCategories(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size,
                                    ModelMap modelMap) {
        Page<ProductCategoryOverview> categories = productCategoryService.findAll(PageRequest.of(page - 1, size));
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("pageNumbers", PageUtil.getTotalPages(categories));
        return "/manager/productCategories";
    }


    @GetMapping("/add")
    public String addProductCategoryPage() {
        return "/manager/addProductCategory";
    }

    @PostMapping("/add")
    public String addProductCategory(@ModelAttribute @Valid CreateProductCategoryDto dto, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute("message", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return "/manager/addProductCategory";
        }
        productCategoryService.addProductCategory(dto);
        return "redirect:/productCategories";
    }


    @GetMapping("/edit/{id}")
    public String editProductCategoryPage(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("category", productCategoryService.findById(id));
        return "/manager/editProductCategory";
    }

    @PostMapping("/edit/{id}")
    public String editProductCategory(@PathVariable("id") int id, @ModelAttribute EditProductCategoryDto dto) {
        productCategoryService.editProductCategory(dto, id);
        return "redirect:/productCategories";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductCategory(@PathVariable("id") int id) {
        productCategoryService.deleteProductCategory(id);
        return "redirect:/productCategories";
    }
}