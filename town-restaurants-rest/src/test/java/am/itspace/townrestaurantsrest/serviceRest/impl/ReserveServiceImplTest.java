package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
import am.itspace.townrestaurantscommon.security.CurrentUser;
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
    SecurityContextServiceImpl securityContextService;

//    @Test
//    void shouldSaveReserve() {
//        //given
//        var reserve = getReserve();
//        var expected = getReserveOverview();
//        var createReserve = getCreateReserveDto();
//        //when
//        doReturn(false).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
//        doReturn(reserve).when(reserveMapper).mapToEntity(createReserve);
//        doReturn(expected).when(reserveMapper).mapToOverview(reserve);
//        doReturn(reserve).when(reserveRepository).save(any(Reserve.class));
//        ReserveOverview actual = reserveService.save(createReserve);
//        //then
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//        verify(reserveRepository, times(1)).save(reserve);
//    }
//
//    @Test
//    void saveThrowsEntityAlreadyExistsException() {
//        //given
//        var createReserve = getCreateReserveDto();
//        //when
//        doThrow(EntityAlreadyExistsException.class).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
//        //then
//        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
//    }
//
//    @Test
//    void shouldThrowExceptionAsExistsByPhoneNumberAndReservedTimeAndReservedDate() {
//        //given
//        var createReserve = getCreateReserveDto();
//        //when
//        doReturn(true).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
//        //then
//        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
//    }
//
//    @Test
//    void shouldGetAll() {
//        //given
//        CurrentUser currentUser = new CurrentUser(getUser());
//        var reserves = List.of(getReserve(), getReserve(), getReserve());
//        var expected = List.of(getReserveOverview(), getReserveOverview(), getReserveOverview());
//        //
//        //when
//        doReturn(currentUser).when(securityContextService).getUserDetails();
//        doReturn(reserves).when(reserveRepository).findAll();
//        doReturn(expected).when(reserveMapper).mapToOverviewList(anyList());
//        List<ReserveOverview> actual = reserveService.getAll();
//        //then
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getAllShouldThrowEntityNotFoundException() {
//        //given
//        CurrentUser currentUser = new CurrentUser(getUser());
//        //when
//        doReturn(currentUser).when(securityContextService).getUserDetails();
//        doReturn(List.of()).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getAll());
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
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getAll());
//    }
//
//    //update
//    @Test
//    void shouldUpdateReserve() {
//        //given
//        int reserveId = 1;
//        var reserve = getReserve();
//        var expected = getReserveOverview();
//        var editReserve = getEditReserveDto();
//        //when
//        doReturn(Optional.of(reserve)).when(reserveRepository).findById(anyInt());
//        doReturn(expected).when(reserveMapper).mapToOverview(reserve);
//        doReturn(reserve).when(reserveRepository).save(any(Reserve.class));
//        ReserveOverview actual = reserveService.update(reserveId, editReserve);
//        //then
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//        verify(reserveRepository, times(1)).save(reserve);
//    }
//
//    //delete
//    @Test
//    void deleteReserveSuccess() {
//        //when
//        when(reserveRepository.existsById(anyInt())).thenReturn(true);
//        reserveService.delete(anyInt());
//        //then
//        verify(reserveRepository).deleteById(anyInt());
//    }
//
//    @Test
//    void shouldThrowExceptionAsReserveNotFound() {
//        //when
//        when(reserveRepository.existsById(anyInt())).thenReturn(false);
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.delete(anyInt()));
//    }
//
//    @Test
//    void shouldGetReservesList() {
//        //given
//        var listOfReserves = getPageReserves();
//        var fetchRequest = getFetchRequestDto();
//        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
//        //when
//        doReturn(listOfReserves).when(reserveRepository).findByReservePhoneNumber("1", pageReq);
//        List<Reserve> actual = reserveService.getReservesList(fetchRequest);
//        //then
//        assertNotNull(actual);
//    }
//
//    @Test
//    void shouldGetReservesListThrowException() {
//        //given
//        var fetchRequest = getFetchRequestDto();
//        var getNullPageReserves = getNullPageReserves();
//        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
//        //when
//        doReturn(getNullPageReserves).when(reserveRepository).findByReservePhoneNumber("1", pageReq);
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getReservesList(fetchRequest));
//    }
//}
}