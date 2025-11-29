package com.techsmarthub.expensetracker.service;


import com.techsmarthub.expensetracker.dto.CategoryDTO;
import com.techsmarthub.expensetracker.entity.CategoryEntity;
import com.techsmarthub.expensetracker.entity.ProfileEntity;
import com.techsmarthub.expensetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        ProfileEntity profile = profileService.getCurrentProfile();

        if (categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }
        CategoryEntity newCategory = toEntity(categoryDTO, profile);
        newCategory = categoryRepository.save(newCategory);
        return toDTO(newCategory);
    }

    public List<CategoryDTO> getCategoriesForCurrentUser() {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(currentProfile.getId());
        return categories.stream().map(this::toDTO).toList();
    }

    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type) {
        ProfileEntity currentProfile = profileService.getCurrentProfile();
        return categoryRepository.findByTypeAndProfileId(type, currentProfile.getId()).stream().map(this::toDTO).toList();
    }


    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity existingCategoryEntity = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategoryEntity.setName(categoryDTO.getName());
        existingCategoryEntity.setIcon(categoryDTO.getIcon());
        categoryRepository.save(existingCategoryEntity);
        return toDTO(existingCategoryEntity);
    }

    private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profileEntity) {
        return  CategoryEntity.builder()
                .name(categoryDTO.getName())
                .type(categoryDTO.getType())
                .icon(categoryDTO.getIcon())
                .profile(profileEntity)
                .build();

    }

    private CategoryDTO toDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .type(categoryEntity.getType())
                .icon(categoryEntity.getIcon())
                .createdAt(categoryEntity.getCreatedDate())
                .updatedAt(categoryEntity.getUpdatedDate())
                .profileId(categoryEntity.getProfile() !=null ? categoryEntity.getProfile().getId() : null)
                .build();
    }
}
