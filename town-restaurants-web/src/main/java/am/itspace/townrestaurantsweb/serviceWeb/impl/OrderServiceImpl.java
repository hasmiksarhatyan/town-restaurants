package am.itspace.townrestaurantsweb.serviceWeb.impl;

import am.itspace.townrestaurantscommon.dto.order.OrderOverview;
import am.itspace.townrestaurantscommon.entity.Order;
import am.itspace.townrestaurantscommon.mapper.OrderMapper;
import am.itspace.townrestaurantscommon.repository.OrderRepository;
import am.itspace.townrestaurantsweb.serviceWeb.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderOverview> getOrders(Pageable pageable) {
        Page<Order> all = orderRepository.findAll(pageable);
        List<OrderOverview> orderOverviews = orderMapper.mapToDto(all);
        return new PageImpl<>(orderOverviews, pageable, orderOverviews.size());
    }

    @Override
    public Order addOrder(Order order) {
      return orderRepository.save(order);
    }

    @Override
    public void delete(int id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalStateException();
        }
        orderRepository.deleteById(id);
    }
}
