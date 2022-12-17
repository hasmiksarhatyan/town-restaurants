package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantCategoryRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantscommon.utilCommon.FileUtil;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import am.itspace.townrestaurantsrest.exception.MyFileNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    File file;

    @Mock
    FileUtil fileUtil;

    @Mock
    private RestaurantMapper restaurantMapper;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Mock
    RestaurantCategoryRepository restaurantCategoryRepository;

    @Test
    void shouldSaveRestaurant() {
        //given
        var restaurant = getRestaurant();
        var expected = getRestaurantOverview();
        var createRestaurant = getCreateRestaurantDto();
        var restaurantsRequestDto = getRestaurantRequestDto();
        //when
        doReturn(false).when(restaurantRepository).existsByName(anyString());
        doReturn(restaurant).when(restaurantMapper).mapToEntity(createRestaurant);
        doReturn(expected).when(restaurantMapper).mapToResponseDto(restaurant);
        doReturn(restaurant).when(restaurantRepository).save(any(Restaurant.class));
        RestaurantOverview actual = restaurantService.save(restaurantsRequestDto);
        //then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        //given
        var restaurantsRequestDto = getRestaurantRequestDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(restaurantRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> restaurantService.save(restaurantsRequestDto));
    }

    @Test
    void shouldThrowExceptionAsNameExists() {
        //given
        var restaurantsRequestDto = getRestaurantRequestDto();
        //when
        doReturn(true).when(restaurantRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> restaurantService.save(restaurantsRequestDto));
    }

    @Test
    void shouldGetAll() {
        //given
        var listOfPageableRestaurants = getPageRestaurants();
        var expected = List.of(getRestaurantOverview(), getRestaurantOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableRestaurants).when(restaurantRepository).findAll(pageable);
        doReturn(expected).when(restaurantMapper).mapToResponseDtoList(anyList());
        List<RestaurantOverview> actual = restaurantService.getAll(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldThrowException() {
        //given
        var emptyRestaurants = getEmptyRestaurants();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(emptyRestaurants).when(restaurantRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getAll(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetRestaurantsByUser() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var listOfPageableRestaurants = getPageRestaurants();
        var expected = List.of(getRestaurantOverview(), getRestaurantOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(listOfPageableRestaurants).when(restaurantRepository).findRestaurantsByUser(currentUser.getUser(), pageable);
        doReturn(expected).when(restaurantMapper).mapToResponseDtoList(anyList());
        List<RestaurantOverview> actual = restaurantService.getRestaurantsByUser(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getRestaurantsByUserShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var emptyRestaurants = getEmptyRestaurants();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        doReturn(emptyRestaurants).when(restaurantRepository).findRestaurantsByUser(currentUser.getUser(), pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getRestaurantsByUser(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetById() {
        //given
        int restaurantId = 1;
        var restaurant = getRestaurant();
        var expected = getRestaurantOverview();
        //when
        doReturn(Optional.of(restaurant)).when(restaurantRepository).findById(anyInt());
        doReturn(expected).when(restaurantMapper).mapToResponseDto(restaurant);
        RestaurantOverview actual = restaurantService.getById(restaurantId);
        //then
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldEntityNotFoundExceptionAsRestaurantNotFoundById() {
        //when
        doThrow(EntityNotFoundException.class).when(restaurantRepository).findById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getById(anyInt()));
    }

    @Test
    void shouldGetRestaurantImage() throws IOException {
        //given
        var bytes = getBytes();
        file = new File("");
        //when
        doReturn(bytes).when(fileUtil).getImage(String.valueOf(file));
        byte[] restaurantImage = restaurantService.getRestaurantImage(anyString());
        //then
        assertNotNull(restaurantImage);
    }

    @Test
    void getRestaurantImageShouldThrowException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(MyFileNotFoundException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> restaurantService.getRestaurantImage(anyString()));
    }

    @Test
    void getRestaurantImageShouldThrowIOException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(IOException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> restaurantService.getRestaurantImage(anyString()));
    }

    @Test
    void shouldUpdateRestaurant() {
        //given
        int restaurantId = 1;
        var restaurant = getRestaurant();
        var expected = getRestaurantOverview();
        var editRestaurantDto = getEditRestaurantDto();
        //when
        doReturn(Optional.of(restaurant)).when(restaurantRepository).findById(anyInt());
        doReturn(expected).when(restaurantMapper).mapToResponseDto(restaurant);
        doReturn(restaurant).when(restaurantRepository).save(any(Restaurant.class));
        RestaurantOverview actual = restaurantService.update(restaurantId, editRestaurantDto);
        //then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        verify(restaurantRepository, times(1)).save(restaurant);
    }

    @Test
    void deleteRestaurantSuccess() {
        //when
        when(restaurantRepository.existsById(anyInt())).thenReturn(true);
        restaurantService.delete(anyInt());
        //then
        verify(restaurantRepository).deleteById(anyInt());
    }

    @Test
    void shouldThrowExceptionAsRestaurantNotFound() {
        //when
        when(restaurantRepository.existsById(anyInt())).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.delete(anyInt()));
    }
}
