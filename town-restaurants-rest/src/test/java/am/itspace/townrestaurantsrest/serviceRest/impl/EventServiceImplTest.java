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

    @Test
    void shouldSaveEvent() {
        //given
        var event = getEvent();
        var fileDto = getFileDto();
        var expected = getEventOverview();
        var createEvent = getCreateEventDto();
        //when
        doReturn(false).when(eventRepository).existsByName(anyString());
        doReturn(event).when(eventMapper).mapToEntity(createEvent);
        doReturn(expected).when(eventMapper).mapToOverview(event);
        doReturn(event).when(eventRepository).save(any(Event.class));
        EventOverview actual = eventService.save(createEvent, fileDto);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void saveShouldThrowExceptionAsNameExists() {
        //given
        var fileDto = getFileDto();
        var createEvent = getCreateEventDto();
        //when
        doReturn(true).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(createEvent, fileDto));
    }

    @Test
    void saveShouldThrowEntityAlreadyExistsException() {
        //given
        var file = getFileDto();
        var createEvent = getCreateEventDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(createEvent, getFileDto()));
    }

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

    @Test
    void shouldGetAllEvents() {
        //given
        var events = List.of(getEvent(), getEvent(), getEvent());
        var expected = List.of(getEventOverview(), getEventOverview(), getEventOverview());
        //when
        doReturn(events).when(eventRepository).findAll();
        doReturn(expected).when(eventMapper).mapToOverviewList(events);
        List<EventOverview> actual = eventService.getAll();
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAllShouldThrowEntityNotFoundException() {
        //when
        doReturn(List.of()).when(eventRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getAll());
    }

    @Test
    void shouldGetByRestaurantId() {
        //given
        var events = List.of(getEvent(), getEvent(), getEvent());
        var expected = List.of(getEventOverview(), getEventOverview(), getEventOverview());
        //when
        doReturn(events).when(eventRepository).findEventsByRestaurant_Id(anyInt());
        doReturn(expected).when(eventMapper).mapToOverviewList(events);
        List<EventOverview> actual = eventService.findEventsByRestaurantId(anyInt());
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getByRestaurantIdShouldThrowEntityNotFoundException() {
        //when
        doReturn(List.of()).when(eventRepository).findEventsByRestaurant_Id(anyInt());
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.findEventsByRestaurantId(anyInt()));
    }

    @Test
    void shouldEntityNotFoundExceptionAsEventNotFound() {
        //when
        doThrow(EntityNotFoundException.class).when(eventRepository).findAll();
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getAll());
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
    void shouldEntityNotFoundExceptionAsEventNotFoundById() {
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
    void shouldThrowExceptionAsEventNotFound() {
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
    void shouldThrowsExceptionAsListOfEventsIsNull() {
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

    @Test
    void shouldGetEventsList() {
        //given
        var listOfEvents = getPageEvents();
        var fetchRequest = getFetchRequestDto();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(listOfEvents).when(eventRepository).findByEventName("1", pageReq);
        List<Event> actual = eventService.getEventsList(fetchRequest);
        //then
        assertNotNull(actual);
    }

    @Test
    void shouldGetEventsListThrowException() {
        //given
        var fetchRequest = getFetchRequestDto();
        var getNullPageEvents = getNullPageEvents();
        PageRequest pageReq = PageRequest.of(1, 1, Sort.Direction.fromString("desc"), "1");
        //when
        doReturn(getNullPageEvents).when(eventRepository).findByEventName("1", pageReq);
        //then
        assertThrows(EntityNotFoundException.class, () -> eventService.getEventsList(fetchRequest));
    }
}

