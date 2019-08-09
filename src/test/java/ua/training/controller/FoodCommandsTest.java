package ua.training.controller;

import mockit.MockUp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import ua.training.controller.commands.FoodCommands;
import ua.training.controller.util.Regex;
import ua.training.model.entity.Food;
import ua.training.model.service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static ua.training.controller.commands.FoodCommands.FoodFields.*;

public class FoodCommandsTest {

    private FoodCommands foodCommands;

    @Mock
    private FoodService foodService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Before
    public void init() throws ServletException, IOException {
        initMocks(this);
        foodCommands = new FoodCommands(foodService);

        foodCommands = Mockito.spy(new FoodCommands(foodService));

        Mockito.doNothing().when(foodCommands).forward(eq(request), eq(response), any());
    }

    @Test
    public void testAdd() throws ServletException, IOException, SQLException {
        new MockUp<Regex>() {
            @mockit.Mock
            public boolean isNumberWrong(String numberString) {
                return false;
            }
        };

        int foodId = 0;
        when(request.getParameter(FOOD_ID.field)).thenReturn(String.valueOf(foodId));
        int calories = 0;
        when(request.getParameter(CALORIES.field)).thenReturn(String.valueOf(calories));
        int protein = 0;
        when(request.getParameter(PROTEIN.field)).thenReturn(String.valueOf(protein));
        int fat = 0;
        when(request.getParameter(FAT.field)).thenReturn(String.valueOf(fat));
        int carbohydrates = 0;
        when(request.getParameter(CARBOHYDRATES.field)).thenReturn(String.valueOf(carbohydrates));
        String name = "test";
        when(request.getParameter(NAME.field)).thenReturn(name);

        Food food = new Food.Builder(foodId)
                .foodName(name)
                .calories(calories)
                .fat(fat)
                .protein(protein)
                .carbohydrates(carbohydrates)
                .build();

        foodCommands.add(request, response);

        verify(foodService, times(1)).create(eq(food));
    }

    @Test
    public void addWithWrongNumber() throws ServletException, IOException, SQLException {
        new MockUp<Regex>() {
            @mockit.Mock
            public boolean isNumberWrong(String numberString) {
                return true;
            }
        };

        int foodId = 0;
        when(request.getParameter(FOOD_ID.field)).thenReturn(String.valueOf(foodId));
        int calories = -1;
        when(request.getParameter(CALORIES.field)).thenReturn(String.valueOf(calories));
        int protein = 0;
        when(request.getParameter(PROTEIN.field)).thenReturn(String.valueOf(protein));
        int fat = 0;
        when(request.getParameter(FAT.field)).thenReturn(String.valueOf(fat));
        int carbohydrates = 0;
        when(request.getParameter(CARBOHYDRATES.field)).thenReturn(String.valueOf(carbohydrates));
        String name = "test";
        when(request.getParameter(NAME.field)).thenReturn(name);

        Food food = new Food.Builder(foodId)
                .foodName(name)
                .calories(calories)
                .fat(fat)
                .protein(protein)
                .carbohydrates(carbohydrates)
                .build();

        foodCommands.add(request, response);

        verify(foodService, times(0)).create(eq(food));
    }

}