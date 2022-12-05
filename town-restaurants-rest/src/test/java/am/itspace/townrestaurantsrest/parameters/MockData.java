package am.itspace.townrestaurantsrest.parameters;

import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantscommon.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MockData {

    //user
    public static User getUser() {
        return User.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@mail.com")
                .password("hayk00")
                .createdAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
    }

    public static ChangePasswordDto getChangePasswordDto() {
        return ChangePasswordDto.builder()
                .oldPassword("12345678")
                .newPassword1("87654321")
                .build();
    }

    public static UserAuthDto getAuthDto() {
        return UserAuthDto.builder()
                .email("hayk@mail.com")
                .password("hayk00")
                .build();
    }

    public static UserAuthResponseDto getAuthResponseDto() {
        return UserAuthResponseDto.builder()
                .token("some token")
                .build();
    }

    public static CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@mail.com")
                .password("hayk00")
                .enabled(true)
                .build();
    }

    public static UserOverview getUserOverview() {
        return UserOverview.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@mail.com")
                .role(Role.CUSTOMER)
                .build();
    }

    public static EditUserDto getEditUserDto() {
        return EditUserDto.builder()
                .firstName("Hayk")
                .lastName("Yan")
                .build();
    }

    public static UserAuthResponseDto getUserAuthResponseDto() {
        return UserAuthResponseDto.builder()
                .token("1234")
                .build();
    }

    //restaurant
    public static Restaurant getRestaurant() {
        return Restaurant.builder()
                .id(1)
                .user(getUser())
                .name("Limone")
                .phone("099112233")
                .address("Tamanyan")
                .deliveryPrice(2000.0)
                .email("limone@gmail.com")
                .restaurantCategory(getRestaurantCategory())
                .build();
    }

    public static RestaurantOverview getRestaurantOverview() {
        return RestaurantOverview.builder()
                .id(1)
                .user(getUser())
                .name("Limone")
                .phone("099112233")
                .address("Tamanyan")
                .deliveryPrice(2000.0)
                .restaurantCategoryOverview(getRestaurantCategoryOverview())
                .email("limone@gmail.com")
                .build();
    }

    public static CreateRestaurantDto getCreateRestaurantDto() {
        return CreateRestaurantDto.builder()
                .name("Limone")
                .phone("099112233")
                .address("Tamanyan")
                .deliveryPrice(2000.0)
                .email("limone@gmail.com")
                .restaurantCategoryId(getRestaurantCategory().getId())
                .build();
    }

    public static EditRestaurantDto getEditRestaurantDto() {
        return EditRestaurantDto.builder()
                .name("Limone")
                .phone("099112233")
                .address("Tamanyan")
                .deliveryPrice(2000.0)
                .email("limone@gmail.com")
                .restaurantCategory(getRestaurantCategory())
                .build();
    }

    //restaurantCategory
    public static RestaurantCategory getRestaurantCategory() {
        return RestaurantCategory.builder()
                .id(1)
                .name("snack")
                .build();
    }

    public static CreateRestaurantCategoryDto getCreateRestaurantCategoryDto() {
        return CreateRestaurantCategoryDto.builder()
                .name("snack")
                .build();
    }

    public static RestaurantCategoryOverview getRestaurantCategoryOverview() {
        return RestaurantCategoryOverview.builder()
                .id(1)
                .name("snack")
                .build();
    }

    public static EditRestaurantCategoryDto getEditRestaurantCategoryDto() {
        return EditRestaurantCategoryDto.builder()
                .name("snack")
                .build();
    }

    //event
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public static LocalDateTime getStartDate(LocalDateTime startDate) {
        return startDate;
    }

    LocalDateTime startDate;

    public static Event getEvent() {
        return Event.builder()
                .id(1)
                .name("Mexican party")
                .restaurant(getRestaurant())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
//                .eventDateTime(LocalDateTime.parse("2022-10-10"))
                .build();
    }

    public static CreateEventDto getCreateEventDto() {
        return CreateEventDto.builder()
                .name("Mexican party")
                .restaurantId(getRestaurant().getId())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
//                .eventDateTime("2022-12-10")
                .build();
    }

    public static EventOverview getEventOverview() {
        return EventOverview.builder()
                .id(1)
                .name("Mexican party")
                .restaurantOverview(getRestaurantOverview())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
//                .eventDateTime(LocalDateTime.parse("2022-10-10"))
                .build();
    }

    public static EditEventDto getEditEventDto() {
        return EditEventDto.builder()
                .name("Mexican party")
                .restaurantId(getRestaurant().getId())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
//                .eventDateTime("2022-12-10")
                .build();
    }

    //productCategory

    public static ProductCategory getProductCategory() {
        return ProductCategory.builder()
                .id(1)
                .name("snack")
                .build();
    }

    public static ProductCategoryOverview getProductCategoryOverview() {
        return ProductCategoryOverview.builder()
                .id(1)
                .name("snack")
                .build();
    }

    public static CreateProductCategoryDto getCreateProductCategoryDto() {
        return CreateProductCategoryDto.builder()
                .name("snack")
                .build();
    }

    public static EditProductCategoryDto getEditProductCategoryDto() {
        return EditProductCategoryDto.builder()
                .name("snack")
                .build();
    }

    //product
    public static Product getProduct() {
        return Product.builder()
                .id(1)
                .name("Taco")
                .price(4000.0)
                .description("Mexican dish")
                .productCategory(getProductCategory())
                .restaurant(getRestaurant())
                .build();
    }

    public static EditProductDto getEditProductDto() {
        return EditProductDto.builder()
                .name("taco")
                .price(4000.0)
                .description("Mexican dish")
                .productCategoryId(getProductCategory().getId())
                .restaurantId(getRestaurant().getId())
                .build();
    }

    public static ProductOverview getProductOverview() {
        return ProductOverview.builder()
                .id(1)
                .name("taco")
                .price(4000.0)
                .description("Mexican dish")
                .productCategoryOverview(getProductCategoryOverview())
                .restaurantOverview(getRestaurantOverview())
                .build();
    }

    public static CreateProductDto getCreateProductDto() {
        return CreateProductDto.builder()
                .name("taco")
                .price(4000.0)
                .description("Mexican dish")
                .productCategoryId(getProductCategory().getId())
                .restaurantId(getRestaurant().getId())
                .build();
    }

    //reserve
    public static Reserve getReserve() {
        return Reserve.builder()
                .id(1)
                .user(getUser())
                .peopleCount(7)
                .phoneNumber("099122134")
//                .reservedAt(LocalDateTime.parse("222-10-10T20:30:00.00"))
                .reservedDate(LocalDate.parse("2022-12-10"))
                .reservedTime(LocalTime.parse("20:00:00"))
                .restaurant(getRestaurant())
                .status(ReserveStatus.PENDING)
                .build();
    }

    public static EditReserveDto getEditReserveDto() {
        return EditReserveDto.builder()
                .status("PENDING")
                .peopleCount(7)
                .reservedDate("2022-12-10")
                .reservedTime("20:00:00")
                .phoneNumber("099122134")
                .build();
    }

    public static CreateReserveDto getCreateReserveDto() {
        return CreateReserveDto.builder()
                .phoneNumber("099122134")
                .peopleCount(7)
                .reservedDate("2022-12-10")
                .reservedTime("20:00:00")
                .restaurantId(getRestaurant().getId())
                .build();
    }

    public static ReserveOverview getReserveOverview() {
        return ReserveOverview.builder()
                .id(1)
                .userOverview(getUserOverview())
                .peopleCount(7)
                .phoneNumber("099122134")
//                .reservedAt(LocalDateTime.parse("222-10-10T20:30:00.00"))
                .reservedDate(LocalDate.parse("2022-12-10"))
                .reservedTime(LocalTime.parse("20:00:00"))
                .restaurantOverview(getRestaurantOverview())
                .status("PENDING")
                .build();
    }

    //token

    public static VerificationToken getVerificationToken(){
        return VerificationToken.builder()
                .id(1)
                .user(getUser())
                .plainToken("123456789")
                .build();
    }
}
