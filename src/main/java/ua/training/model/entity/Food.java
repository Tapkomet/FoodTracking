package ua.training.model.entity;

import lombok.Data;

import java.util.Comparator;
/**
 * This class implements the Food object type and its various methods
 *
 * @author Roman Kobzar
 * @version 1.0
 * @since 2019-09-09
 */
@Data
public class Food {
    private int id;
    private String name;
    private int calories;
    private int protein;
    private int fat;
    private int carbohydrates;


    public static Comparator<Food> FoodIdComparator = (s1, s2) -> {
        int id1 = s1.getId();
        int id2 = s2.getId();
        return id1 - id2;
    };

    public static Comparator<Food> FoodNameComparator = (s1, s2) -> {
        String name1 = s1.getName().toUpperCase();
        String name2 = s2.getName().toUpperCase();
        return name1.compareTo(name2);
    };

    public static Comparator<Food> FoodCaloriesComparator = (s1, s2) -> {
        long calories1 = s1.getCalories();
        long calories2 = s2.getCalories();
        return Long.compare(calories1, calories2);
    };

    public static Comparator<Food> FoodProteinComparator = (s1, s2) -> {
        long protein1 = s1.getProtein();
        long protein2 = s2.getProtein();
        return Long.compare(protein1, protein2);
    };

    public static Comparator<Food> FoodFatComparator = (s1, s2) -> {
        long fat1 = s1.getFat();
        long fat2 = s2.getFat();
        return Long.compare(fat1, fat2);
    };

    public static Comparator<Food> FoodCarbohydratesComparator = (s1, s2) -> {
        long carbohydrates1 = s1.getCarbohydrates();
        long carbohydrates2 = s2.getCarbohydrates();
        return Long.compare(carbohydrates1, carbohydrates2);
    };

    @Override
    public String toString() {
        return "Food{" +
                "food_id=" + id +
                ", name='" + name +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                '}';
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        final Food other = (Food) o;
        if (this.id != other.id) return false;
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) return false;
        if (this.calories != other.calories) return false;
        if (this.protein != other.protein) return false;
        if (this.fat != other.fat) return false;
        if (this.carbohydrates != other.carbohydrates) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + this.id;
        result = result * PRIME + this.calories;
        result = result * PRIME + this.protein;
        result = result * PRIME + this.fat;
        result = result * PRIME + this.carbohydrates;
        return result;
    }


    public static class Builder {

        private int id;
        private String name;
        private int calories;
        private int protein;
        private int fat;
        private int carbohydrates;

        public Builder(int id) {
            this.id = id;
        }

        public Builder foodName(String name) {
            this.name = name;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder protein(int protein) {
            this.protein = protein;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder carbohydrates(int carbohydrates) {
            this.carbohydrates = carbohydrates;
            return this;
        }

        public Food build() {
            Food food = new Food();
            food.id = this.id;
            food.name = this.name;
            food.calories = this.calories;
            food.protein = this.protein;
            food.fat = this.fat;
            food.carbohydrates = this.carbohydrates;
            return food;
        }
    }
}
