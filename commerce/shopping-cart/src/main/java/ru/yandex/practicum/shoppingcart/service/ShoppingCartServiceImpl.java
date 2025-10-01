package ru.yandex.practicum.shoppingcart.service;

import jakarta.ws.rs.NotAuthorizedException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.intersectionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.intersectionapi.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.shoppingcart.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.shoppingcart.mapper.ShoppingCartMapper;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.shoppingcart.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartDto addProduct(String username, Map<UUID, Long> productsIdWithCount) {
        checkUsername(username);
        ShoppingCart save;
        if (shoppingCartRepository.findByUsername(username) != null) {
            save = shoppingCartRepository.findByUsername(username);
            Map<UUID, Long> oldProducts = save.getProducts();
            for (Map.Entry<UUID, Long> newProduct : productsIdWithCount.entrySet()) {
                UUID productId = newProduct.getKey();
                Long newCount = newProduct.getValue();

                oldProducts.put(productId, newCount);
            }
        } else
            save = shoppingCartRepository.save(ShoppingCartMapper.toShoppingCart(username, productsIdWithCount));

        return ShoppingCartMapper.toShoppingCartDto(save);
    }

    @Override
    public void deactivate(String username) {
        checkUsername(username);

        ShoppingCart findByUsername = shoppingCartRepository.findByUsername(username);

        findByUsername.setActive(false);
        shoppingCartRepository.save(findByUsername);
    }

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getCartByUsername(String username) {
        checkUsername(username);

        ShoppingCart findByUsername = shoppingCartRepository.findByUsername(username);

        return ShoppingCartMapper.toShoppingCartDto(findByUsername);
    }

    @Override
    public ShoppingCartDto deleteByIds(String username, List<UUID> productIds) {
        checkUsername(username);
        ShoppingCart findByUsername = shoppingCartRepository.findByUsername(username);
        if (findByUsername == null) {
            throw new NoProductsInShoppingCartException("Пользователь " + username + " не имеет корзину покупок.");
        }

        Map<UUID, Long> products = findByUsername.getProducts();

        for (UUID productId : productIds) {
            products.remove(productId);
        }

        findByUsername.setProducts(products);

        return ShoppingCartMapper.toShoppingCartDto(shoppingCartRepository.save(findByUsername));
    }

    @Override
    public ShoppingCartDto change(String username, ChangeProductQuantityRequest changeProductQuantityRequest) {
        checkUsername(username);
        ShoppingCart findByUsername = shoppingCartRepository.findByUsername(username);

        if (findByUsername == null) {
            throw new NoProductsInShoppingCartException("Пользователь " + username + " не имеет корзину покупок.");
        }

        Map<UUID, Long> products = findByUsername.getProducts();


        products.put(changeProductQuantityRequest.getProductId(), changeProductQuantityRequest.getNewQuantity());
        return ShoppingCartMapper.toShoppingCartDto(shoppingCartRepository.save(findByUsername));

    }


    private void checkUsername(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedException("Имя пользователя не должно быть пустым");
        }
    }
}