package am.itspace.townrestaurantsrest.parameters;

import am.itspace.townrestaurantscommon.dto.FileDto;
import am.itspace.townrestaurantscommon.dto.basket.BasketOverview;
import am.itspace.townrestaurantscommon.dto.creditCard.CreateCreditCardDto;
import am.itspace.townrestaurantscommon.dto.creditCard.CreditCardOverview;
import am.itspace.townrestaurantscommon.dto.event.CreateEventDto;
import am.itspace.townrestaurantscommon.dto.event.EditEventDto;
import am.itspace.townrestaurantscommon.dto.event.EventOverview;
import am.itspace.townrestaurantscommon.dto.event.EventRequestDto;
import am.itspace.townrestaurantscommon.dto.order.CreateOrderDto;
import am.itspace.townrestaurantscommon.dto.order.EditOrderDto;
import am.itspace.townrestaurantscommon.dto.order.OrderCreditCardDto;
import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.dto.payment.PaymentOverview;
import am.itspace.townrestaurantscommon.dto.product.CreateProductDto;
import am.itspace.townrestaurantscommon.dto.product.EditProductDto;
import am.itspace.townrestaurantscommon.dto.product.ProductOverview;
import am.itspace.townrestaurantscommon.dto.product.ProductRequestDto;
import am.itspace.townrestaurantscommon.dto.productCategory.CreateProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.EditProductCategoryDto;
import am.itspace.townrestaurantscommon.dto.productCategory.ProductCategoryOverview;
import am.itspace.townrestaurantscommon.dto.reserve.CreateReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.EditReserveDto;
import am.itspace.townrestaurantscommon.dto.reserve.ReserveOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.CreateRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.EditRestaurantDto;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantOverview;
import am.itspace.townrestaurantscommon.dto.restaurant.RestaurantRequestDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.CreateRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.EditRestaurantCategoryDto;
import am.itspace.townrestaurantscommon.dto.restaurantCategory.RestaurantCategoryOverview;
import am.itspace.townrestaurantscommon.dto.token.VerificationTokenDto;
import am.itspace.townrestaurantscommon.dto.user.*;
import am.itspace.townrestaurantscommon.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockData {

    //user
    public static User getUser() {
        return User.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@gmail.com")
                .password("hayk00")
                .createdAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
    }

    public static User getUserForToken() {
        return User.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@gmail.com")
                .password("password")
                .createdAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .enabled(false)
                .build();
    }

    public static User getOwnerUser() {
        return User.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@mail.com")
                .password("hayk00")
                .createdAt(LocalDateTime.now())
                .role(Role.RESTAURANT_OWNER)
                .enabled(true)
                .build();
    }

    public static User getManagerUser() {
        return User.builder()
                .id(1)
                .firstName("Hayk")
                .lastName("Yan")
                .email("hayk@mail.com")
                .password("hayk00")
                .createdAt(LocalDateTime.now())
                .role(Role.MANAGER)
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

    public static Page<User> getPageUsers() {
        return new PageImpl<>(List.of(getUser(), getUser()));
    }

    //token
    public static VerificationTokenDto getVerificationTokenDto() {
        return VerificationTokenDto.builder()
                .plainToken("123456789")
                .build();
    }

    public static VerificationToken getVerificationToken() {
        return VerificationToken.builder()
                .id(1)
                .user(getUser())
                .plainToken("123456789")
                .build();
    }

    public static VerificationToken getVToken() {
        return VerificationToken.builder()
                .id(1)
                .user(getUserForToken())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .plainToken("123456789")
                .build();
    }

    public static VerificationToken getToken() {
        return VerificationToken.builder()
                .plainToken(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().minusHours(1))
                .user(getUser())
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

    public static Restaurant getRestaurantForProduct() {
        return Restaurant.builder()
                .id(1)
                .user(getOwnerUser())
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
                .userOverview(getUserOverview())
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

    public static CreateRestaurantDto getCreateRestaurant() {
        return CreateRestaurantDto.builder()
                .name("Friends")
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
                .restaurantCategoryId(getRestaurantCategory().getId())
                .build();
    }

    public static RestaurantRequestDto getRestaurantRequestDto() {
        return RestaurantRequestDto.builder()
                .fileDto(getFileDto())
                .createRestaurantDto(getCreateRestaurantDto())
                .build();
    }

    public static Page<Restaurant> getPageRestaurants() {
        return new PageImpl<>(List.of(getRestaurant(), getRestaurant()));
    }

    public static Page<Restaurant> getNullPageRestaurants() {
        return new PageImpl<>(List.of());
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

    public static Page<RestaurantCategory> getPageRestaurantCategories() {
        return new PageImpl<>(List.of(getRestaurantCategory(), getRestaurantCategory()));
    }

    //event
    public static Event getEvent() {
        return Event.builder()
                .id(1)
                .name("Mexican party")
                .restaurant(getRestaurant())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
                .build();
    }

    public static CreateEventDto getCreateEventDto() {
        return CreateEventDto.builder()
                .name("Mexican party")
                .restaurantId(getRestaurant().getId())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
                .build();
    }

    public static CreateEventDto getCreateEvent() {
        return CreateEventDto.builder()
                .name("Italian party")
                .restaurantId(getRestaurant().getId())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
                .build();
    }

    public static EventOverview getEventOverview() {
        return EventOverview.builder()
                .id(1)
                .name("Mexican party")
                .restaurantOverview(getRestaurantOverview())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
                .build();
    }

    public static EditEventDto getEditEventDto() {
        return EditEventDto.builder()
                .name("Mexican party")
                .restaurantId(getRestaurant().getId())
                .price(5000.0)
                .description("Here will be a traditional energetic music on the guitar, trumpet, and violin.")
                .build();
    }

    public static EventRequestDto getEventRequestDto() {
        return EventRequestDto.builder()
                .fileDto(getFileDto())
                .createEventDto(getCreateEventDto())
                .build();
    }

    public static Page<Event> getPageEvents() {
        return new PageImpl<>(List.of(getEvent(), getEvent()));
    }

    public static Page<Event> getEmptyEvents() {
        return new PageImpl<>(List.of());
    }

    public static Map<Integer, List<EventOverview>> getMapEvent() {
        Map<Integer, List<EventOverview>> list = new HashMap<>();
        list.put(1, List.of(getEventOverview(), getEventOverview()));
        return list;
    }

    //file
    public static FileDto getFileDto() {
        return FileDto.builder()
                .files(null)
                .build();
    }

    public static byte[] getBytes() {
        return new byte[]{1};
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

    public static Page<ProductCategory> getPageProductCategories() {
        return new PageImpl<>(List.of(getProductCategory(), getProductCategory()));
    }

    public static Page<ProductCategory> getEmptyProductCategories() {
        return new PageImpl<>(List.of());
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

    public static Product getProductForOwner() {
        return Product.builder()
                .id(1)
                .name("Taco")
                .user(getOwnerUser())
                .price(4000.0)
                .description("Mexican dish")
                .productCategory(getProductCategory())
                .restaurant(getRestaurantForProduct())
                .build();
    }

    public static Product getProductForBasket() {
        return Product.builder()
                .id(2)
                .name("Fries")
                .price(500.0)
                .description("French dish")
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

    public static ProductRequestDto getProductRequestDto() {
        return ProductRequestDto.builder()
                .fileDto(getFileDto())
                .createProductDto(getCreateProductDto())
                .build();
    }

    public static Page<Product> getPageProducts() {
        return new PageImpl<>(List.of(getProduct(), getProduct()));
    }

    //reserve
    public static Reserve getReserve() {
        return Reserve.builder()
                .id(1)
                .user(getUser())
                .peopleCount(7)
                .phoneNumber("099122134")
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
                .reservedDate(LocalDate.parse("2022-12-10"))
                .reservedTime(LocalTime.parse("20:00:00"))
                .restaurantOverview(getRestaurantOverview())
                .status("PENDING")
                .build();
    }

    public static Page<Reserve> getPageReserves() {
        return new PageImpl<>(List.of(getReserve(), getReserve()));
    }

    //order
    public static Order getOrder() {
        return Order.builder()
                .id(1)
                .additionalAddress("Tamanayan")
                .status(OrderStatus.NEW)
                .isPaid(true)
                .additionalPhone("+37499999999")
                .paymentOption(PaymentOption.CASH)
                .products(List.of(getProduct(), getProductForBasket()))
                .totalPrice(20.0)
                .user(getUser())
                .build();
    }

    public static Order getOrderForPayment() {
        return Order.builder()
                .id(1)
                .additionalAddress("Tamanayan")
                .status(OrderStatus.NEW)
                .isPaid(true)
                .additionalPhone("+37499999999")
                .paymentOption(PaymentOption.CREDIT_CARD)
                .products(List.of(getProduct(), getProduct()))
                .totalPrice(20.0)
                .user(getUser())
                .build();
    }

    public static EditOrderDto getEditOrderDto() {
        return EditOrderDto.builder()
                .additionalAddress("Tamanayan")
                .status("NEW")
                .additionalPhone("+37499999999")
                .paymentOption("CASH")
                .productOverviews(List.of(getProductOverview(), getProductOverview()))
                .totalPrice(20.0)
                .build();
    }

    public static CreateOrderDto getCreateOrderDto() {
        return CreateOrderDto.builder()
                .additionalAddress("Tamanayan")
                .additionalPhone("+37499999999")
                .paymentOption("CASH")
                .productOverviews(List.of(getProductOverview(), getProductOverview()))
                .totalPrice(20.0)
                .build();
    }

    public static OrderOverview getOrderOverview() {
        return OrderOverview.builder()
                .id(1)
                .additionalAddress("Tamanayan")
                .status("NEW")
                .isPaid(true)
                .additionalPhone("+37499999999")
                .productOverviews(List.of(getProductOverview(), getProductOverview()))
                .totalPrice(20.0)
                .userOverview(getUserOverview())
                .build();
    }

    public static OrderCreditCardDto getOrderCreditCardDto() {
        return OrderCreditCardDto.builder()
                .createOrderDto(getCreateOrderDto())
                .creditCardDto(getCreateCreditCardDto())
                .build();
    }

    public static Page<Order> getPageOrders() {
        return new PageImpl<>(List.of(getOrder(), getOrder()));
    }

    public static Page<Order> getEmptyOrders() {
        return new PageImpl<>(List.of());
    }

    //creditCard
    public static CreditCard getCreditCard() {
        return CreditCard.builder()
                .id(1)
                .user(getUser())
                .cardHolder("Hayk Yan")
                .cardNumber("123456789102")
                .cardExpiresAt(LocalDate.now().plusDays(100))
                .cvv("1234")
                .build();
    }

    public static CreateCreditCardDto getCreateCreditCardDto() {
        return CreateCreditCardDto.builder()
                .cardHolder("Hayk Yan")
                .cardNumber("123456789102")
                .cardExpiresAt(LocalDate.EPOCH)
                .cvv("1234")
                .build();
    }

    public static CreateCreditCardDto getCreateCreditCard() {
        return CreateCreditCardDto.builder()
                .cardHolder("Hayk Yan")
                .cardNumber("123456789102")
                .cardExpiresAt(LocalDate.now().plusDays(100))
                .cvv("1234")
                .build();
    }

    public static CreditCardOverview getCreditCardOverview() {
        return CreditCardOverview.builder()
                .id(1)
                .userOverview(getUserOverview())
                .cardHolder("Hayk")
                .cardNumber("123456789102")
                .cardExpiresAt(LocalDate.EPOCH)
                .build();
    }

    public static Page<CreditCard> getPageCreditCards() {
        return new PageImpl<>(List.of(getCreditCard(), getCreditCard()));
    }

    //basket
    public static Basket getBasket() {
        return Basket.builder()
                .id(1)
                .user(getUser())
                .product(getProduct())
                .quantity(1)
                .build();
    }

    public static Basket getBasket2() {
        return Basket.builder()
                .id(null)
                .user(getUser())
                .product(getProductForBasket())
                .quantity(1)
                .build();
    }

    public static BasketOverview getBasketOverview() {
        return BasketOverview.builder()
                .id(1)
                .userOverview(getUserOverview())
                .productOverview(getProductOverview())
                .quantity(1)
                .build();
    }

    public static Page<Basket> getPageBaskets() {
        return new PageImpl<>(List.of(getBasket(), getBasket()));
    }

    //payment
    public static Payment getPayment() {
        return Payment.builder()
                .id(0)
                .paymentCreateDate(LocalDateTime.now())
                .user(getUser())
                .status(PaymentStatus.PROCESSING)
                .order(getOrderForPayment())
                .totalAmount(getOrder().getTotalPrice())
                .build();
    }

    public static PaymentOverview getPaymentOverview() {
        return PaymentOverview.builder()
                .id(1)
                .userOverview(getUserOverview())
                .orderOverview(getOrderOverview())
                .build();
    }

    public static Page<Payment> getPagePayment() {
        return new PageImpl<>(List.of(getPayment(), getPayment()));
    }
}
