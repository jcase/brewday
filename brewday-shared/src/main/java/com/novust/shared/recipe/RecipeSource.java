package com.novust.shared.recipe;


import com.google.common.collect.ImmutableList;
import com.novust.shared.dao.VerbDataDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@org.springframework.stereotype.Component
public class RecipeSource {
    Map<String, Recipe> recipeMap = newHashMap();

    @Autowired
    VerbDataDao verbDataDao;

    @PostConstruct
    public void init() {
        Recipe appleRecipe = new Recipe("apple","Apple Beer", "Tasty with a hint of apples");
        appleRecipe.addAction(new ProcedureAction(verbDataDao.getById("clean"), "Clean your kettle",0));
        appleRecipe.addAction(new ComponentAction(verbDataDao.getById("heat"), new Component("water", 5, "gallons"), 0));
        appleRecipe.addAction(new ComponentAction(verbDataDao.getById("add"), new Component("malt", 3, "pounds"), 600));
        appleRecipe.addAction(new ComponentAction(verbDataDao.getById("add"), new Component("hops", 1, "oz"), 3600));

        recipeMap.put(appleRecipe.getKey(), appleRecipe);

        Recipe bananaBeer = new Recipe("banana","Banana Beer", "Throws a curve at you");
        bananaBeer.addAction(new ProcedureAction(verbDataDao.getById("clean"), "Clean your fermenter", 0));
        bananaBeer.addAction(new ComponentAction(verbDataDao.getById("peel"), new Component("banana", 2, "units"), 0));
        bananaBeer.addAction(new ComponentAction(verbDataDao.getById("heat"), new Component("water", 3, "gallons"), 120));
        bananaBeer.addAction(new ComponentAction(verbDataDao.getById("add"), new Component("malt", 3, "pounds"), 600));
        bananaBeer.addAction(new ComponentAction(verbDataDao.getById("add"), new Component("hops", 1, "oz"), 3600));

        recipeMap.put(bananaBeer.getKey(), bananaBeer);
    }

    public Recipe get(String recipeId) {
        return recipeMap.get(recipeId);
    }

    public Collection<Recipe> getAll() {
        return ImmutableList.copyOf(recipeMap.values());
    }
}
