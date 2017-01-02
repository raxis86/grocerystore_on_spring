package grocerystore.Services.Models;

import grocerystore.Domain.Entities.Grocery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by raxis on 25.12.2016.
 * Корзина покупок
 */
public class Cart {
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);

    /**
     * Хэш-мап для огранизации хранения товаров
     */
    private Map<Grocery,Integer> map = new HashMap<>();

    public Map<Grocery, Integer> getMap() {
        return map;
    }

    /**
     * Добавление товара
     * @param grocery - продукт
     * @param quantity - количество
     */
    public void addItem(Grocery grocery, Integer quantity){
        Integer sum=map.put(grocery,quantity);
        if(sum!=null){
            map.put(grocery,quantity+sum);
        }
    }

    public void removeItem(Grocery grocery){
        map.remove(grocery);
    }

    /**
     * Подсчет общей суммы корзины
     * @return
     */
    public BigDecimal computeTotalPrice(){
        BigDecimal priceSum = BigDecimal.valueOf(0);
        BigDecimal price=BigDecimal.valueOf(0);
        BigDecimal quan=BigDecimal.valueOf(0);

        for(HashMap.Entry entry: map.entrySet()){
            price=((Grocery)entry.getKey()).getPrice();
            quan=BigDecimal.valueOf((Integer)entry.getValue());
            priceSum=priceSum.add(price.multiply(quan));
        }
        return priceSum;
    }

    /**
     * Подсчет общего количества продуктов в корзине
     * @return
     */
    public int totalQuantity(){
        int totalQuantity=0;
        for(HashMap.Entry entry: map.entrySet()){
            totalQuantity+=(int)entry.getValue();
        }
        return totalQuantity;
    }

    public void clear(){
        map.clear();
    }

}
