package com.matty.vending_machine;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;

enum Note {
    TEN(10), TWENTY(20), FIFTY(50), HUNDRED(100);
    private final int value;
    Note(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

enum Coin {
    ONE(1), TWO(2), FIVE(5), TEN(10);
    private final int value;
    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}


class Product {
    String name;
    int price;
}


//Composite pattern
class Inventory {
    private final ConcurrentHashMap<Product, Integer> productStockMap;

    public Inventory() {
        productStockMap = new ConcurrentHashMap<>();
    }

    public void addProduct(Product product, Integer quantity) {
        productStockMap.put(product, productStockMap.getOrDefault(product, 0) + quantity);
    }

    public boolean isAvaiable(Product product) {
        return productStockMap.getOrDefault(product, 0) > 0;
    }

    public ConcurrentHashMap<Product, Integer> getProductStockMap() {
        return this.productStockMap;
    }

    public void addInventory(Inventory inventory) {
        inventory.getProductStockMap().forEach(this::addProduct);
    }


    public int getProductQuantity(Product product) {
        return this.productStockMap.getOrDefault(product, 0);
    }
}

abstract class VendingMachineState {
    protected final VendingMachine context;

    VendingMachineState(VendingMachine context) {
        this.context = context;
    }

    public abstract void  insertMoney(int amount);
    public abstract void selectProduct(Product product);
    public abstract void dispence();
}

class IdleState extends VendingMachineState {
    public IdleState(VendingMachine context) {
       super(context);
    }

    @Override
    public void insertMoney(int amount) {
        this.context.setTotalMoney(this.context.getTotalMoney() + amount);
        this.context.setState(new ReadyState(this.context));
        System.out.println("Money inserted successfully ! " + this.context.getTotalMoney());
    }

    @Override
    public void selectProduct(Product product) {
        System.out.println("Insert Money first!");
    }

    @Override
    public void dispence() {
        System.out.println("Insert Money first!");
    }
}

class ReadyState extends VendingMachineState {

    ReadyState(VendingMachine context) {
        super(context);
    }

    @Override
    public void insertMoney(int amount) {
        System.out.println("Already in Ready state. Additional money inserted: " + amount);
        this.context.setTotalMoney(this.context.getTotalMoney() + amount);
    }

    @Override
    public void selectProduct(Product product) {
        if (this.context.getInventory().getProductQuantity(product) > 0) {
            this.context.setSelectedProduct(product);
        } else {
            System.out.println("Product is not available!");
        }
    }

    @Override
    public void dispence() {

    }
}


public class VendingMachine {
    private Product selectedProduct;
    @Setter
    @Getter
    private int totalMoney;
    @Getter
    private Inventory inventory;

    @Setter
    private VendingMachineState state;

    public VendingMachine() {
        this.totalMoney = 0;
        this.inventory = new Inventory();
        this.state = new IdleState(this);
    }

    public void setSelectedProduct(Product product) {
        this.state.selectProduct(product);
    }

    public void insertMoney(int money) {
        this.state.insertMoney(money);
    }

    public void dispense() {
        this.state.dispence();
    }
}
