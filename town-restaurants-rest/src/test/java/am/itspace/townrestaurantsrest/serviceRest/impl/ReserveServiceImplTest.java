package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Product;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.exception.EntityAlreadyExistsException;
import am.itspace.townrestaurantsrest.exception.EntityNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveServiceImplTest {

    @InjectMocks
    ReserveServiceImpl reserveService;

    @Mock
    ReserveMapper reserveMapper;

    @Mock
    ReserveRepository reserveRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    SecurityContextServiceImpl securityContextService;

    @Test
    void shouldSaveReserve() {
        //given
        var reserve = getReserve();
        var expected = getReserveOverview();
        var createReserve = getCreateReserveDto();
        //when
        doReturn(false).when(reserveRepository).existsByPhoneNumber(anyString());
        doReturn(reserve).when(reserveMapper).mapToEntity(createReserve);
        doReturn(expected).when(reserveMapper).mapToOverview(reserve);
        doReturn(reserve).when(reserveRepository).save(any(Reserve.class));
        ReserveOverview actual = reserveService.save(createReserve);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(reserveRepository, times(1)).save(reserve);
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        //given
        var createReserve = getCreateReserveDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(reserveRepository).existsByPhoneNumber(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
    }

    @Test
    void shouldThrowExceptionAsExistsByPhoneNumber() {
        //given
        var createReserve = getCreateReserveDto();
        //when
        doReturn(true).when(reserveRepository).existsByPhoneNumber(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
    }
//////

    @Test
    void shouldGetAllReserves() {
        //given
        var listOfPageableReserves = getPageReserves();
        var expected = List.of(getReserveOverview(), getReserveOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableReserves).when(reserveRepository).findAll(pageable);
        doReturn(expected).when(reserveMapper).mapToOverviewList(anyList());
        List<ReserveOverview> actual = reserveService.getAllReserves(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllReservesShouldThrowException() {
        //given
        Page<Product> empty = Page.empty();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(empty).when(reserveRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> reserveService.getAllReserves(1, 1, "name", "DESC"));
    }

    ///
    @Test
    void shouldGetByRole() {
        //given
        CurrentUser currentUser = new CurrentUser(getManagerUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
        //
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.MANAGER));
        doReturn(reserves).when(reserveRepository).findAll();
        doReturn(expected).when(reserveMapper).mapToOverviewList(anyList());
        List<ReserveOverview> actual = reserveService.getByRole();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetByCustomer() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
        //
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.CUSTOMER));
        doReturn(reserves).when(reserveRepository).findAll();
        doReturn(expected).when(reserveMapper).mapToOverviewList(anyList());
        doReturn(reserves).when(reserveRepository).findByUser(currentUser.getUser());
        List<ReserveOverview> actual = reserveService.getByRole();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetByOwner() {
        //given
        var restaurants = List.of(getRestaurant(), getRestaurant());
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
        //
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(reserves).when(reserveRepository).findAll();
        doReturn(restaurants).when(restaurantRepository).findAll();
        doReturn(reserves).when(reserveRepository).findByRestaurantId(1);
        doReturn(expected).when(reserveMapper).mapToOverviewList(anyList());
        List<ReserveOverview> actual = reserveService.getByRole();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }


    @Test
    void getByRoleShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
        //
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(List.of()).when(reserveRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> reserveService.getByRole());
    }

    @Test
    void getByCustomerShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
        //
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.CUSTOMER));
        doReturn(reserves).when(reserveRepository).findAll();
        doReturn(List.of()).when(reserveRepository).findByUser(currentUser.getUser());
        //then
        assertThrows(EntityNotFoundException.class, () -> reserveService.getByRole());
    }

    @Test
    void getByOwnerShouldThrowException() {
        //given
        CurrentUser currentUser = new CurrentUser(getOwnerUser());
        var reserves = List.of(getReserve(), getReserve(), getReserve());
        //when
        doReturn(currentUser).when(securityContextService).getUserDetails();
        assertThat(currentUser.getUser().getRole(), Matchers.is(Role.RESTAURANT_OWNER));
        doReturn(reserves).when(reserveRepository).findAll();
        doReturn(List.of()).when(restaurantRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> reserveService.getByRole());
    }

//    @Test
//    void getAllShouldThrowEntityNotFoundException() {
//        //given
//        CurrentUser currentUser = new CurrentUser(getUser());
//        //when
//        doReturn(currentUser).when(securityContextService).getUserDetails();
//        doReturn(List.of()).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getByRole());
//    }
//
//    @Test
//    void shouldEntityNotFoundExceptionAsReserveNotFound() {
//        //given
//        CurrentUser currentUser = new CurrentUser(getUser());
//        //when
//        doReturn(currentUser).when(securityContextService).getUserDetails();
//        doThrow(EntityNotFoundException.class).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getByRole());
//    }

    //update
    @Test
    void shouldUpdateReserve() {
        //given
        int reserveId = 1;
        var reserve = getReserve();
        var expected = getReserveOverview();
        var editReserve = getEditReserveDto();
        //when
        doReturn(Optional.of(reserve)).when(reserveRepository).findById(anyInt());
        doReturn(expected).when(reserveMapper).mapToOverview(reserve);
        doReturn(reserve).when(reserveRepository).save(any(Reserve.class));
        ReserveOverview actual = reserveService.update(reserveId, editReserve);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(reserveRepository, times(1)).save(reserve);
    }

    //delete
    @Test
    void deleteReserveSuccess() {
        //when
        when(reserveRepository.existsById(anyInt())).thenReturn(true);
        reserveService.delete(anyInt());
        //then
        verify(reserveRepository).deleteById(anyInt());
    }

    @Test
    void shouldThrowExceptionAsReserveNotFound() {
        //when
        when(reserveRepository.existsById(anyInt())).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> reserveService.delete(anyInt()));
    }
}