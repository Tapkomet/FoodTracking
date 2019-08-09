package ua.training.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.FoodDao;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FoodServiceTest {

    private FoodService foodService;

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private FoodDao foodDao;

    @Before
    public void init() throws NoSuchFieldException {
        initMocks(this);
        foodService = new FoodService();
        FieldSetter.setField(foodService, foodService.getClass().getDeclaredField("daoFactory"), daoFactory);

        when(daoFactory.createFoodDao()).thenReturn(foodDao);
    }

    @Test
    public void testGetAllFoods() throws SQLException {
        List<Food> expected = Arrays.asList(
                new Food.Builder(0).build(),
                new Food.Builder(1).build(),
                new Food.Builder(2).build());

        when(foodDao.findAll()).thenReturn(expected);

        List<Food> actual = foodService.getAllFoods();

        verify(foodDao, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFoodCount() throws SQLException {
        int expected = 5;

        when(foodDao.getCount()).thenReturn(expected);

        int actual = foodService.getFoodCount();

        verify(foodDao, times(1)).getCount();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetFoodById() throws SQLException {
        int foodId = 0;
        Food expected = new Food.Builder(foodId).build();

        when(foodDao.findById(foodId)).thenReturn(expected);

        Food actual = foodService.getFoodById(foodId);

        verify(foodDao, times(1)).findById(eq(foodId));
        assertEquals(expected, actual);
    }

    @Test
    public void testCreate() throws SQLException {
        Food food = new Food.Builder(0)
                .build();

        foodService.create(food);

        verify(foodDao, times(1)).create(eq(food));
    }

    @Test
    public void testUpdate() throws SQLException {
        Food food = new Food.Builder(0)
                .build();

        foodService.update(food);

        verify(foodDao, times(1)).update(eq(food));
    }

    @Test
    public void testDelete() throws SQLException {
        int code = 0;

        foodService.delete(code);

        verify(foodDao, times(1)).delete(eq(code));
    }

    @Test
    public void testFoodsSorted() throws SQLException {
        String sortBy = "id";
        int rowsOnPage = 2;
        int offset = 3;
        List<Food> expected = Arrays.asList(
                new Food.Builder(0).build(),
                new Food.Builder(1).build(),
                new Food.Builder(2).build());

        when(foodDao.findNumberSorted(eq(sortBy), eq(rowsOnPage), eq(offset))).thenReturn(expected);

        List<Food> actual = foodService.getFoodsSorted(sortBy, rowsOnPage, offset);

        verify(foodDao, times(1)).findNumberSorted(eq(sortBy), eq(rowsOnPage), eq(offset));
        assertEquals(expected, actual);
    }

}
