package swingy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import swingy.enums.ClassName;
import swingy.model.Model;
import swingy.schema.Hero;

public class GameController {
	Model model;
	private static Validator validator;

	public static void setUpValidator() {
	   ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	   validator = factory.getValidator();
   }

	// gets all herores form db
	public ArrayList<Hero> getHeroesList() {
		return model.getAllHeros();
	}

	// handles hero selection action
	public Hero handleSelect(int id){
		Hero selected;

		selected = model.getHero(id);
		return selected;
	}

	// handles hero craetion action
	public Hero handleCreate(String name, String class_str)
	{
		Hero newHero = null;

		Map<String, ClassName> classMap = new HashMap<String, ClassName>();
		classMap.put("JIMIN", ClassName.JIMIN);
		classMap.put("JUNGKOOK", ClassName.JUNGKOOK);
		classMap.put("JHOPE", ClassName.JHOPE);
		classMap.put("VI", ClassName.VI);
		classMap.put("KIM_JUNG_UN", ClassName.KIM_JUNG_UN);
		classMap.put("TAEYUNG", ClassName.TAEYUNG);

		if (!classMap.keySet().contains(class_str))
			throw new ConstraintViolationException("Invalid className", null);
		
		newHero = new Hero(name, classMap.get(class_str));

		Set<ConstraintViolation<Hero>> constraintSet = validator.validate(newHero);
		if (constraintSet.size() > 0) throw new ConstraintViolationException(constraintSet);
		newHero.setId(model.addHero(newHero));
		return newHero; 
	}

	public GameController(Model m)
	{
		setUpValidator();
		this.model = m;
	}
}
