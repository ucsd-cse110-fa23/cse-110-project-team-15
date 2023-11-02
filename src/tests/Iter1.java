package tests;

import org.junit.Test;

import RecipeManagement.Recipe;
import RecipeManagement.RecipePopup.RecipePopup;

import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class Iter1 {


    @Test
    public void recipeTest() {
        Recipe rec = new Recipe();
        assertEquals(rec.getName(), null);
    }

    @Test
    public void testStudentCompareTo() {

    }
}