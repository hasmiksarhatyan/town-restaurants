package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.entity.Reserve;
import am.itspace.townrestaurantscommon.mapper.ReserveMapper;
import am.itspace.townrestaurantscommon.repository.ReserveRepository;
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
class ReserveServiceImplTest {

    @Mock
    ReserveRepository reserveRepository;

    @Mock
    ReserveMapper reserveMapper;

    @InjectMocks
    ReserveServiceImpl reserveService;

    //save
    @Test
    void shouldSaveReserve() {
        //given
        var reserve = getReserve();
        var expected = getReserveOverview();
        var createReserve = getCreateReserveDto();
        //when
        doReturn(false).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
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
        doThrow(EntityAlreadyExistsException.class).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
    }

    @Test
    void shouldThrowExceptionAsExistsByPhoneNumberAndReservedTimeAndReservedDate() {
        //given
        var createReserve = getCreateReserveDto();
        //when
        doReturn(true).when(reserveRepository).existsByPhoneNumberAndReservedTimeAndReservedDate(anyString(), anyString(), anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> reserveService.save(createReserve));
    }
//
//    //getAll
//    //////nayi sa
//    @Test
//    void getAllReservesShouldThrowEntityNotFoundException() {
//        //when
//        doReturn(List.of()).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getAll(any()));
//    }
/////nayii saa
//    @Test
//    void getAllShouldThrowEntityNotFoundException() {
//        //when
//        doReturn(List.of()).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getAll(anyInt()));
//    }
///nayi saaa
//    @Test
//    void shouldEntityNotFoundExceptionAsReserveNotFound() {
//        //when
//        doThrow(EntityNotFoundException.class).when(reserveRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> reserveService.getAll(anyInt()));
//    }

    //update
    @Test
    void shouldUpdateReserve() {
        //given
        int reserveId = 1;
        var reserve = getReserve();
        var expected = getReserveOverview();
        var createReserve = getCreateReserveDto();
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