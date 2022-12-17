package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.mapper.ProductMapper;
import am.itspace.townrestaurantscommon.repository.ProductCategoryRepository;
import am.itspace.townrestaurantscommon.repository.ProductRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantscommon.utilCommon.FileUtil;
import am.itspace.townrestaurantsrest.exception.AuthenticationException;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.MyFileNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    File file;

    @Mock
    FileUtil fileUtil;

    @Mock
    ProductMapper productMapper;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    ProductCategoryRepository productCategoryRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldSaveProduct() {
        //given
        var product = getProduct();
        var expected = getProductOverview();
        var createProduct = getCreateProductDto();
        var productRequestDto = getProductRequestDto();
        //when
        doReturn(false).when(productRepository).existsByName(anyString());
        doReturn(product).when(productMapper).mapToEntity(createProduct);
        doReturn(expected).when(productMapper).mapToResponseDto(product);
        doReturn(product).when(productRepository).save(any(Product.class));
        ProductOverview actual = productService.save(productRequestDto);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void saveShouldThrowException() {
        //given
        var product = getProduct();
        var createProduct = getCreateProductDto();
        var productRequestDto = getProductRequestDto();
        //when
        doReturn(false).when(productRepository).existsByName(anyString());
        doReturn(product).when(productMapper).mapToEntity(createProduct);
        doThrow(MyFileNotFoundException.class).when(productRepository).save(any(Product.class));
        //then
        assertThrows(MyFileNotFoundException.class, () -> productService.save(productRequestDto));
    }

    @Test
    void saveShouldThrowExceptionAsNameAlreadyExists() {
        //given
        var productRequestDto = getProductRequestDto();
        //when
        doReturn(true).when(productRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> productService.save(productRequestDto));
    }

    @Test
    void saveShouldThrowEntityAlreadyExistsException() {
        //given
        var productRequestDto = getProductRequestDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(productRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> productService.save(productRequestDto));
    }

    @Test
    void shouldGetAllProducts() {
        //given
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableProducts).when(productRepository).findAll(pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getAllProducts(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllProductsShouldThrowException() {
        //given
        Page<Product> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(productRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getAllProducts(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetByRestaurant() {
        //given
        var restaurantId = 1;
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableProducts).when(productRepository).findAllByRestaurantId(restaurantId, pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getByRestaurant(1, 1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getEventsByRestaurantShouldThrowException() {
        //given
        var restaurantId = 1;
        Page<Product> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(productRepository).findAllByRestaurantId(restaurantId, pageable);
        ;
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getByRestaurant(1, 1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetAllByRole() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(listOfPageableProducts).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getAllByRole(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllByCustomer() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.CUSTOMER));
        doReturn(listOfPageableProducts).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getAllByRole(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetAllByOwner() {
        //given
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(listOfPageableProducts).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getAllByRole(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllByRoleShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        Page<Product> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(empty).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getAllByRole(1, 1, "name", "DESC"));
    }

    @Test
    void getAllByRoleShouldThrowExceptionAsCurrentUserIsNull() {
        //when
        doThrow(AuthenticationException.class).when(securityContextService).getUserDetails();
        //then
        assertThrows(AuthenticationException.class, () -> productService.getAllByRole(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetByOwner() {
        //given
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        var listOfPageableProducts = getPageProducts();
        var expected = List.of(getProductOverview(), getProductOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(listOfPageableProducts).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(productMapper).mapToOverviewList(anyList());
        List<ProductOverview> actual = productService.getByOwner(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getByOwnerShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        Page<Product> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(empty).when(productRepository).findAllByUser(currentUser.getUser(), pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> productService.getByOwner(1, 1, "name", "DESC"));
    }

    @Test
    void getByOwnerShouldThrowExceptionAsCurrentUserIsNull() {
        //when
        doThrow(AuthenticationException.class).when(securityContextService).getUserDetails();
        //then
        assertThrows(AuthenticationException.class, () -> productService.getByOwner(1, 1, "name", "DESC"));
    }

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

    @Test
    void shouldGetProductImage() throws IOException {
        //given
        file = new File("");
        var bytes = getBytes();
        //when
        doReturn(bytes).when(fileUtil).getImage(String.valueOf(file));
        byte[] productImage = productService.getProductImage(anyString());
        //then
        assertNotNull(productImage);
    }

    @Test
    void getProductImageShouldThrowException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(MyFileNotFoundException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> productService.getProductImage(anyString()));
    }

    @Test
    void getProductImageShouldThrowIOException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(IOException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> productService.getProductImage(anyString()));
    }

    @Test
    void shouldUpdateProduct() {
        //given
        var product = getProduct();
        var expected = getProductOverview();
        var editProduct = getEditProductDto();
        //when
        doReturn(Optional.of(product)).when(productRepository).findById(anyInt());
        doReturn(expected).when(productMapper).mapToResponseDto(product);
        doReturn(product).when(productRepository).save(any(Product.class));
        ProductOverview actual = productService.update(anyInt(), editProduct);
        //then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        verify(productRepository, times(1)).save(product);
    }

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
