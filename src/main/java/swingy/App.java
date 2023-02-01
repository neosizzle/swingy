package swingy;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import swingy.controller.GameController;
import swingy.model.Model;
import swingy.view.GameView;

public class App {

   public static void main( String args[] ) {
      // check args 
      if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("gui")))
      {
         System.out.println("Usage: java -jar target/swingy-1.0-SNAPSHOT.jar console | gui");
         System.exit(1);
      }


      Model m = new Model();
      GameController controller = new GameController(m);
      GameView view = new GameView(controller, args[0]);

      view.start();
      
   }
}