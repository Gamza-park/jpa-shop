package jpa.jpashop.service;

import jpa.jpashop.domain.*;
import jpa.jpashop.repository.ItemRepository;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * Order
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // get Entity
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // Order Reload
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // make OrderItem
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // make Order
        Order order = Order.createOrder(member, delivery, orderItem);

        // save Order
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * Cancle
     */
    @Transactional
    public void cancle(Long orderId) {

        // Order Entity Check
        Order order = orderRepository.findOne(orderId);

        // Order Cancle
        order.cancel();
    }

    /**
     * Search
     */
//    public List<Order> findOrder(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }

}
