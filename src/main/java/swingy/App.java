package swingy;

import swingy.controller.GameController;
import swingy.enums.ClassName;
import swingy.model.Model;
import swingy.schema.Hero;
import swingy.view.GameView;

public class App {
  public static void main( String args[] ) {

      Model m = new Model();
      m.addHero(new Hero("null", ClassName.JUNGKOOK));
      m.addHero(new Hero("null", ClassName.JIMIN));
      m.addHero(new Hero("null", ClassName.JHOPE));

      GameController controller = new GameController(m);
      GameView view = new GameView(controller);

      view.start();

      // Hero hero = m.getHero(7);
      // System.out.println(hero.getClassName());
   }
}