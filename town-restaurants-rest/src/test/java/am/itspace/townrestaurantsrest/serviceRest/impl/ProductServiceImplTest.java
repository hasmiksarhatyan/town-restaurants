package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductServiceImpl productService;

    //save
    @Test
    void shouldSaveSuccess() {
        //given
        var product = getProduct();
        var expected = getProductOverview();
        var createProduct = getCreateProductDto();
        //when
        doReturn(false).when(productRepository).existsByName(anyString());
        doReturn(product).when(productMapper).mapToEntity(createProduct);
        doReturn(expected).when(productMapper).mapToResponseDto(product);
        doReturn(product).when(productRepository).save(any(Product.class));
        ProductOverview actual = productService.save(createProduct);
        //then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionAsNameAlreadyExists() {
        //given
        var createProduct = getCreateProductDto();
        //when
        doReturn(true).when(productRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> productService.save(createProduct));
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        //given
        var createProduct = getCreateProductDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(productRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> productService.save(createProduct));
    }

    //getAll
    @Test
    void shouldGetAllProducts() {
        //given
        var products = List.of(getProduct(), getProduct(), getProduct());
        var expected = List.of(getProductOverview(), getProductOverview(), getProductOverview());
        //when
        doReturn(products).when(productRepository).findAll();
        doReturn(expected).when(productMapper).mapToOverviewList(products);
        List<ProductOverview> actual = productService.getAll();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionAsProductsListIsEmpty() {
        //when
        doReturn(List.of()).when(productRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getAll());
    }

    @Test
    void shouldEntityNotFoundExceptionAsProductNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(productRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getAll());
    }

    //getById
    @Test
    void shouldGetById() {
        //given
        int productId = 1;
        var product = getProduct();
        var expected = getProductOverview();
        //when
        doReturn(Optional.of(product)).when(productRepository).findById(anyInt());
        doReturn(expected).when(productMapper).mapToResponseDto(product);
        ProductOverview actual = productService.getById(productId);
        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldEntityNotFoundExceptionAsProductNotFoundById() {
        //when
        doThrow(EntityNotFoundException.class).when(productRepository).findById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getById(anyInt()));
    }

    //update
    @Test
    void shouldUpdateProduct() {
        //given
        int productId = 1;
        var product = getProduct();
        var expected = getProductOverview();
        var editProduct = getEditProductDto();
        //when
        doReturn(Optional.of(product)).when(productRepository).findById(anyInt());
        doReturn(expected).when(productMapper).mapToResponseDto(product);
        doReturn(product).when(productRepository).save(any(Product.class));
        ProductOverview actual = productService.update(productId, editProduct);
        //then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        verify(productRepository, times(1)).save(product);
    }

    //delete
    @Test
    void deleteSuccess() {
        //when
        doReturn(true).when(productRepository).existsById(anyInt());
        productService.delete(anyInt());
        //then
        verify(productRepository).deleteById(anyInt());
    }

    @Test
    void shouldThrowExceptionAsProductNotFound() {
        //when
        when(productRepository.existsById(anyInt())).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.delete(anyInt()));
    }
}