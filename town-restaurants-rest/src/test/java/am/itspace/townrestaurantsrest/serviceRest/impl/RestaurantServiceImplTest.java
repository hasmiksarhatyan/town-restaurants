package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.entity.Restaurant;
import am.itspace.townrestaurantscommon.mapper.RestaurantMapper;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantMapper restaurantMapper;

//    //save
//    @Test
//    void shouldSaveRestaurant() {
//        //given
//        var restaurant = getRestaurant();
//        var expected = getRestaurantOverview();
//        var createRestaurant = getCreateRestaurantDto();
//        //when
//        doReturn(false).when(restaurantRepository).existsByName(anyString());
//        doReturn(restaurant).when(restaurantMapper).mapToEntity(createRestaurant);
//        doReturn(expected).when(restaurantMapper).mapToResponseDto(restaurant);
//        doReturn(restaurant).when(restaurantRepository).save(any(Restaurant.class));
//        RestaurantOverview actual = restaurantService.save(createRestaurant);
//        //then
//        assertNotNull(actual);
//        assertEquals(expected.getName(), actual.getName());
//        verify(restaurantRepository, times(1)).save(restaurant);
//    }

//    @Test
//    void saveThrowsEntityAlreadyExistsException() {
//        //given
//        var createRestaurantDto = getCreateRestaurantDto();
//        //when
//        doThrow(EntityAlreadyExistsException.class).when(restaurantRepository).existsByName(anyString());
//        //then
//        assertThrows(EntityAlreadyExistsException.class, () -> restaurantService.save(createRestaurantDto));
//    }
//
//    @Test
//    void shouldThrowExceptionAsNameExists() {
//        //given
//        var createRestaurantDto = getCreateRestaurantDto();
//        //when
//        doReturn(true).when(restaurantRepository).existsByName(anyString());
//        //then
//        assertThrows(EntityAlreadyExistsException.class, () -> restaurantService.save(createRestaurantDto));
//    }

    //getAll
    @Test
    void shouldGetAllRestaurants() {
        //given
        var restaurants = List.of(getRestaurant(), getRestaurant(), getRestaurant());
        var expected = List.of(getRestaurantOverview(), getRestaurantOverview(), getRestaurantOverview());
        //when
        doReturn(restaurants).when(restaurantRepository).findAll();
        doReturn(expected).when(restaurantMapper).mapToResponseDtoList(restaurants);
        List<RestaurantOverview> actual = restaurantService.getAll();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldThrowEntityNotFoundException() {
        //when
        doReturn(List.of()).when(restaurantRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getAll());
    }

    @Test
    void shouldEntityNotFoundExceptionAsRestaurantNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(restaurantRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getAll());
    }

    //getById
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

    //update
    @Test
    void shouldUpdateRestaurant() {
        //given
        int restaurantId = 1;
        var restaurant = getRestaurant();
        var editRestaurantDto = getEditRestaurantDto();
        var expected = getRestaurantOverview();
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

    //delete
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