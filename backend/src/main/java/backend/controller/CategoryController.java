package backend.controller;


import backend.exception.CategoryNotFoundException;

import backend.model.CategoryModel;

import backend.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category")
    public CategoryModel newCategoryModel(@RequestBody CategoryModel newCategoryModel){
        return categoryRepository.save(newCategoryModel);
    }
    //Display
    @GetMapping("/category")
    List<CategoryModel> getAllCategory(){
        return categoryRepository.findAll();
    }
    @GetMapping("/category/{id}")
    CategoryModel getCategoryId(@PathVariable Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));

    }

    //Update
    @PutMapping("category/{id}")
    CategoryModel updateCategory(@RequestBody CategoryModel newCategoryModel,@PathVariable Long id){
        return categoryRepository.findById(id).map(categoryModel -> {
            categoryModel.setName(newCategoryModel.getName());
            return  categoryRepository.save(categoryModel);
        }).orElseThrow(()-> new CategoryNotFoundException(id));

    }
    //Delete
    @DeleteMapping("/category/{id}")
    String deleteCategory(@PathVariable Long id){
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
        return  "user account "+ id +"delete";
    }

}
