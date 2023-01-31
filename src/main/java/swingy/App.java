package swingy;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import swingy.controller.GameController;
import swingy.enums.ArtifactQuality;
import swingy.enums.ArtifactType;
import swingy.enums.ClassName;
import swingy.model.Model;
import swingy.schema.Artifact;
import swingy.schema.Hero;
import swingy.view.GameView;

public class App {

   private static Validator validator;

   public static void setUpValidator() {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();
  }

   public static void main( String args[] ) {
      // check args 
      if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui")))
      {
         System.out.println("Usage: java -jar target/swingy-1.0-SNAPSHOT.jar console | gui");
         System.exit(1);
      }

      Model m = new Model();
      Hero newHero = new Hero("init1", ClassName.JUNGKOOK);
      newHero.setId(m.addHero(newHero));
      m.addHero(new Hero("init2", ClassName.JIMIN));
      m.addHero(new Hero("init3", ClassName.JHOPE));


      GameController controller = new GameController(m);
      GameView view = new GameView(controller, args[0]);

      view.start();
      
   }
}