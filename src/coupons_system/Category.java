package coupons_system;

import java.util.HashMap;
import java.util.Map;

public enum Category {
	
	Food(0),
	Electricity(1),
	Restaurant(2),
	Vacation(3);

	private int value;
    private static Map<Integer, Category> map = new HashMap<>();

    private Category(int value) {
        this.value = value;
    }

    static {
        for (Category category : Category.values()) {
            map.put(category.value, category);
        }
    }

    public static Category valueOf(int category) {
        return (Category) map.get(category);
    }

    public int getValue() {
        return value;
    }
}