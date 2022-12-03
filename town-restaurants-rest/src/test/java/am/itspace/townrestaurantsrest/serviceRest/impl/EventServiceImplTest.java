package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.entity.Event;
import am.itspace.townrestaurantscommon.mapper.EventMapper;
import am.itspace.townrestaurantscommon.repository.EventRepository;
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
class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    EventMapper eventMapper;

    @InjectMocks
    EventServiceImpl eventService;

    @Test
    void shouldSaveEvent() {
        //given
        var event = getEvent();
        var expected = getEventOverview();
        var createEvent = getCreateEventDto();
        //when
        doReturn(false).when(eventRepository).existsByName(anyString());
        doReturn(event).when(eventMapper).mapToEntity(createEvent);
        doReturn(expected).when(eventMapper).mapToOverview(event);
        doReturn(event).when(eventRepository).save(any(Event.class));
        EventOverview actual = eventService.save(createEvent);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void saveThrowsEntityAlreadyExistsException() {
        //given
        var createEvent = getCreateEventDto();
        //when
        doThrow(EntityAlreadyExistsException.class).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(createEvent));
    }

    @Test
    void shouldThrowExceptionAsNameExists() {
        //given
        var createEvent = getCreateEventDto();
        //when
        doReturn(true).when(eventRepository).existsByName(anyString());
        //then
        assertThrows(EntityAlreadyExistsException.class, () -> eventService.save(createEvent));
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
        var createEvent = getCreateEventDto();
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
}