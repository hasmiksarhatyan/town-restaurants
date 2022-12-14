package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
import am.itspace.townrestaurantscommon.repository.RestaurantRepository;
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
import java.util.Map;
import java.util.Optional;

import static am.itspace.townrestaurantsrest.parameters.MockData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    EventServiceImpl eventService;

    @Mock
    File file;

    @Mock
    FileUtil fileUtil;

    @Mock
    EventMapper eventMapper;

    @Mock
    EventRepository eventRepository;

    @Mock
    RestaurantRepository restaurantRepository;
//save
    @Test
    void shouldSaveEvent() {
        //given
        var event = getEvent();
        var expected = getEventOverview();
        var createEvent = getCreateEventDto();
        var eventRequestDto = getEventRequestDto();
        //when
        doReturn(false).when(eventRepository).existsByName(anyString());
        doReturn(event).when(eventMapper).mapToEntity(createEvent);
        doReturn(expected).when(eventMapper).mapToOverview(event);
        doReturn(event).when(eventRepository).save(any(Event.class));
        EventOverview actual = eventService.save(eventRequestDto);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void saveShouldThrowExceptionAsNameExists() {
        //given
        var eventRequestDto = getEventRequestDto();
        //when
        doReturn(true).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(eventRequestDto));
    }

    @Test
    void saveShouldThrowEntityAlreadyExistsException() {
        //given
        var eventRequestDto = getEventRequestDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(eventRequestDto));
    }
//image
    @Test
    void shouldGetEventImage() throws IOException {
        //given
        var bytes = getBytes();
        file = new File("");
        //when
        doReturn(bytes).when(fileUtil).getImage(String.valueOf(file));
        byte[] eventImage = eventService.getEventImage(anyString());
        //then
        assertNotNull(eventImage);
    }

    @Test
    void getEventImageShouldThrowException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(MyFileNotFoundException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> eventService.getEventImage(anyString()));
    }

    @Test
    void getEventImageShouldThrowIOException() throws IOException {
        //given
        file = new File("");
        //when
        doThrow(IOException.class).when(fileUtil).getImage(String.valueOf(file));
        //then
        assertThrows(MyFileNotFoundException.class, () -> eventService.getEventImage(anyString()));
    }
//get
    @Test
    void shouldGetAllEvents() {
        //given
        var listOfPageableEvents = getPageEvents();
        var expected = List.of(getEventOverview(), getEventOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableEvents).when(eventRepository).findAll(pageable);
        doReturn(expected).when(eventMapper).mapToOverviewList(anyList());
        List<EventOverview> actual = eventService.getAllEvents(1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllEventsShouldThrowException() {
        //given
        var getNullPageableEvents = getNullPageEvents();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(getNullPageableEvents).when(eventRepository).findAll(pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getAllEvents(1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetEventsByRestaurantId() {
        //given
        var restaurantId = 1;
        var listOfPageableEvents = getPageEvents();
        var expected = List.of(getEventOverview(), getEventOverview());
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(listOfPageableEvents).when(eventRepository).findEventsByRestaurantId(restaurantId, pageable);
        doReturn(expected).when(eventMapper).mapToOverviewList(anyList());
        List<EventOverview> actual = eventService.getEventsByRestaurantId(1, 1, 1, "name", "DESC");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getEventsByRestaurantIdShouldThrowException() {
        //given
        var restaurantId = 1;
        var getNullPageableEvents = getNullPageEvents();
        PageRequest pageable = PageRequest.of(1, 1, Sort.Direction.fromString("DESC"), "name");
        //when
        doReturn(getNullPageableEvents).when(eventRepository).findEventsByRestaurantId(restaurantId, pageable);
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getEventsByRestaurantId(1, 1, 1, "name", "DESC"));
    }

    @Test
    void shouldGetById() {
        //given
        var event = getEvent();
        var expected = getEventOverview();
        //when
        doReturn(Optional.of(event)).when(eventRepository).findById(anyInt());
        doReturn(expected).when(eventMapper).mapToOverview(event);
        EventOverview actual = eventService.getById(anyInt());
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getByIdShouldThrowExceptionAsEventNotFoundById() {
        //when
        doThrow(EntityNotFoundException.class).when(eventRepository).findById(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getById(anyInt()));
    }

    @Test
    void shouldUpdateRestaurant() {
        //given
        var event = getEvent();
        var editEvent = getEditEventDto();
        var expected = getEventOverview();
        //when
        doReturn(Optional.of(event)).when(eventRepository).findById(anyInt());
        doReturn(expected).when(eventMapper).mapToOverview(event);
        doReturn(event).when(eventRepository).save(any(Event.class));
        EventOverview actual = eventService.update(anyInt(), editEvent);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void deleteEventSuccess() {
        //given
        int eventId = 1;
        //when
        when(eventRepository.existsById(eventId)).thenReturn(true);
        eventService.delete(eventId);
        //then
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void deleteEventShouldThrowExceptionAsEventNotFound() {
        //given
        int eventId = 1;
        //when
        when(eventRepository.existsById(eventId)).thenReturn(false);
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.delete(eventId));
    }

    @Test
    void shouldSortEventsByRestaurant() {
        //given
        var events = List.of(getEvent(), getEvent());
        var expected = getMapEvent();
        var restaurants = List.of(getRestaurant(), getRestaurant());
        var eventOverviewList = List.of(getEventOverview(), getEventOverview());
        //when
        doReturn(restaurants).when(restaurantRepository).findAll();
        doReturn(events).when(eventRepository).findEventsByRestaurant_Id(anyInt());
        doReturn(eventOverviewList).when(eventMapper).mapToOverviewList(anyList());
        Map<Integer, List<EventOverview>> actual = eventService.sortEventsByRestaurant();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void sortEventsByRestaurantShouldThrowException() {
        //given
        var events = List.of(getEvent(), getEvent());
        var restaurants = List.of(getRestaurant(), getRestaurant());
        //when
        doReturn(restaurants).when(restaurantRepository).findAll();
        doReturn(events).when(eventRepository).findEventsByRestaurant_Id(anyInt());
        doReturn(List.of()).when(eventMapper).mapToOverviewList(anyList());
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.sortEventsByRestaurant());
    }

    @Test
    void shouldThrowsExceptionAsListOfRestaurantIsNull() {
        //when
        doReturn(List.of()).when(restaurantRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.sortEventsByRestaurant());
    }
}

//    @Test
//    void shouldGetAllEvents() {
//        //given
//        var events = List.of(getEvent(), getEvent(), getEvent());
//        var expected = List.of(getEventOverview(), getEventOverview(), getEventOverview());
//        //when
//        doReturn(events).when(eventRepository).findAll();
//        doReturn(expected).when(eventMapper).mapToOverviewList(events);
//        List<EventOverview> actual = eventService.getAllEvents(anyInt(),anyInt(),anyString(),anyString());
//        //then
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void getAllShouldThrowEntityNotFoundException() {
//        //when
//        doReturn(List.of()).when(eventRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> eventService.getAllEvents(anyInt(),anyInt(),anyString(),anyString()));
//    }


//    @Test
//    void getByRestaurantIdShouldThrowEntityNotFoundException() {
//        //when
//        doReturn(List.of()).when(eventRepository).findEventsByRestaurant_Id(anyInt());
//        //then
//        assertThrows(EntityNotFoundException.class, () -> eventService.findEventsByRestaurantId(anyInt(),anyInt(),anyInt(),anyString(),anyString()));
//    }
//
//    @Test
//    void shouldEntityNotFoundExceptionAsEventNotFound() {
//        //when
//        doThrow(EntityNotFoundException.class).when(eventRepository).findAll();
//        //then
//        assertThrows(EntityNotFoundException.class, () -> eventService.findEventsByRestaurantId(anyInt(),anyInt(),anyInt(),anyString(),anyString()));
//    }