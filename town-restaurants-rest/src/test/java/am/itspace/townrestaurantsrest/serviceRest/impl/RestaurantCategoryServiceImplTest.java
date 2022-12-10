package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.entity.RestaurantCategory;
import am.itspace.townrestaurantscommon.mapper.RestaurantCategoryMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantCategoryServiceImplTest {

    @InjectMocks
    RestaurantCategoryServiceImpl restaurantCategoryService;

    @Mock
    RestaurantCategoryMapper categoryMapper;

    @Mock
    RestaurantCategoryRepository restaurantCategoryRepository;

    //save
    @Test
    void shouldSaveRestaurantCategory() {
        //given
        var restaurantCategory = getRestaurantCategory();
        var expected = getRestaurantCategoryOverview();
        var createRestaurantCategory = getCreateRestaurantCategoryDto();
        //when
        doReturn(false).when(restaurantCategoryRepository).existsByName(anyString());
        doReturn(restaurantCategory).when(categoryMapper).mapToEntity(createRestaurantCategory);
        doReturn(expected).when(categoryMapper).mapToOverview(restaurantCategory);
        doReturn(restaurantCategory).when(restaurantCategoryRepository).save(any(RestaurantCategory.class));
        RestaurantCategoryOverview actual = restaurantCategoryService.save(createRestaurantCategory);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionAsNameAlreadyExists() {
        //given
        var createRestaurantCategory = getCreateRestaurantCategoryDto();
        //when
        doReturn(true).when(restaurantCategoryRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> restaurantCategoryService.save(createRestaurantCategory));
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        //given
        var createRestaurantCategory = getCreateRestaurantCategoryDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(restaurantCategoryRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> restaurantCategoryService.save(createRestaurantCategory));
    }

    //getAll

    @Test
    void shouldGetAllCategories() {
        //given
        var categories = List.of(getRestaurantCategory(), getRestaurantCategory(), getRestaurantCategory());
        var expected = List.of(getRestaurantCategoryOverview(), getRestaurantCategoryOverview(), getRestaurantCategoryOverview());
        //when
        doReturn(categories).when(restaurantCategoryRepository).findAll();
        doReturn(expected).when(categoryMapper).mapToOverviewList(categories);
        List<RestaurantCategoryOverview> actual = restaurantCategoryService.getAll();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionAsCategoriesListIsEmpty() {
        //when
        doReturn(List.of()).when(restaurantCategoryRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.getAll());
    }

    @Test
    void shouldEntityNotFoundExceptionAsCategoryNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(restaurantCategoryRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.getAll());
    }

    //getById

    @Test
    void shouldGetById() {
        //given
        var restaurantCategory = getRestaurantCategory();
        var expected = getRestaurantCategoryOverview();
        var createRestaurantCategory = getCreateRestaurantCategoryDto();
        //when
        doReturn(Optional.of(restaurantCategory)).when(restaurantCategoryRepository).findById(anyInt());
        doReturn(expected).when(categoryMapper).mapToOverview(restaurantCategory);
        RestaurantCategoryOverview actual = restaurantCategoryService.getById(anyInt());
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldEntityNotFoundExceptionAsCategoryNotFoundById() {
        //when
        doThrow(EntityNotFoundException.class).when(restaurantCategoryRepository).findById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.getById(anyInt()));
    }

    @Test
    void shouldGetCategoriesList() {
        //given
        var fetchRequest = getFetchRequestDto();
        var listOfCategories = getPageRestaurantCategories();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(listOfCategories).when(restaurantCategoryRepository).findByName("1", pageReq);
        List<RestaurantCategory> actual = restaurantCategoryService.getCategoriesList(fetchRequest);
        //then
        assertNotNull(actual);
    }

    @Test
    void shouldGetCategoriesListThrowException() {
        //given
        var fetchRequest = getFetchRequestDto();
        var getNullPageCategories = getNullPageRestaurantCategories();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(getNullPageCategories).when(restaurantCategoryRepository).findByName("1", pageReq);
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.getCategoriesList(fetchRequest));
    }

    //update

    @Test
    void shouldUpdateProductCategory() {
        //given
        var restaurantCategory = getRestaurantCategory();
        var expected = getRestaurantCategoryOverview();
        var editRestaurantCategory = getEditRestaurantCategoryDto();
        var createRestaurantCategory = getCreateRestaurantCategoryDto();
        //when
        doReturn(Optional.of(restaurantCategory)).when(restaurantCategoryRepository).findById(anyInt());
        doReturn(expected).when(categoryMapper).mapToOverview(restaurantCategory);
        doReturn(restaurantCategory).when(restaurantCategoryRepository).save(any(RestaurantCategory.class));
        RestaurantCategoryOverview actual = restaurantCategoryService.update(anyInt(), editRestaurantCategory);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    //delete

    @Test
    void deleteSuccess() {
        //when
        doReturn(true).when(restaurantCategoryRepository).existsById(anyInt());
        restaurantCategoryService.delete(anyInt());
        //then
        verify(restaurantCategoryRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void shouldNotFoundCategory() {
        //when
        doReturn(false).when(restaurantCategoryRepository).existsById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.delete(anyInt()));
    }

    @Test
    void shouldThrowExceptionAsCategoryNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(restaurantCategoryRepository).existsById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantCategoryService.delete(anyInt()));
    }
}