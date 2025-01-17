package entity;

import items.Item;
import items.heal_potion;
import main.GamePanel;
import utils.Drawable;
import utils.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Player extends Entity {

  private static final BufferedImage [] image = new BufferedImage[4];

  private int state;

  static {
    try {
      image [0] = ImageIO.read(Objects.requireNonNull(Player.class.getResource("/player/Character.png")));
      image [1] = ImageIO.read(Objects.requireNonNull(Player.class.getResource("/player/Character2.png")));
      image [2] = ImageIO.read(Objects.requireNonNull(Player.class.getResource("/player/Character3.png")));
      image [3] = ImageIO.read(Objects.requireNonNull(Player.class.getResource("/player/Character4.png")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Position nextPosition;
  private final HashSet<Direction> directions;

  public LinkedList<Item> getInventory() {
    return inventory;
  }

  public void addItemToInventory(Item item) {
    inventory.add(item);
  }

  private LinkedList<Item> inventory;


  public Player() {
    super(10, 4, 5);

    this.nextPosition = this.getPosition();
    this.directions = new HashSet<>();
    this.inventory = new LinkedList<>();
    this.state = 0;
  }

  public void update() {
    computeNextPosition();
  }

  public void addDirection(Direction direction) {
    this.directions.add(direction);
  }

  public void removeDirection(Direction direction) {
    this.directions.remove(direction);
  }

  public void computeNextPosition() {
    nextPosition = this.getPosition();
    if (this.directions.contains(Direction.UP)) {
      this.state = 2;
      nextPosition = new Position(nextPosition.getX(), nextPosition.getY() - this.getSpeed());
    }
    if (this.directions.contains(Direction.DOWN)) {
      this.state = 3;
      nextPosition = new Position(nextPosition.getX(), nextPosition.getY() + this.getSpeed());
    }
    if (this.directions.contains(Direction.LEFT)) {
      this.state = 1;
      nextPosition = new Position(nextPosition.getX() - this.getSpeed(), nextPosition.getY());
    }
    if (this.directions.contains(Direction.RIGHT)) {
      this.state = 0;
      nextPosition = new Position(nextPosition.getX() + this.getSpeed(), nextPosition.getY());
    }
  }

  public boolean collideNextBoundingBox(Drawable other) {
    int width = getWidth();
    int height = getHeight();
    int otherWidth = other.getWidth();
    int otherHeight = other.getHeight();
    int x = nextPosition.getX();
    int y = nextPosition.getY();
    int otherX = other.getPosition().getX();
    int otherY = other.getPosition().getY();
    return x + width > otherX && x < otherX + otherWidth && y + height > otherY && y < otherY + otherHeight;
  }

  @Override
  public int getStrength() {
    return super.getStrength() + totalGivenStrength();
  }

  private int totalGivenStrength() {
    int total = 0;
    for (Item item : inventory) {
      total += item.getGivenStrength();
    }
    return total;
  }

  public void draw(Graphics2D g2) {
    draw(g2, getPosition(), GamePanel.tileSize, GamePanel.tileSize);
  }

  @Override
  public void draw(Graphics2D graphics2D, Position position, int width, int height) {
    // affiche le personnage avec l'image "image", avec les coordonn�es x et y, et de taille tileSize (16x16) sans �chelle, et 48x48 avec �chelle)
    switch (state){
      case 1:
        graphics2D.drawImage(image[1], position.getX(), position.getY(), width, height, null);
        break;
      case 2:
        graphics2D.drawImage(image[2], position.getX(), position.getY(), width, height, null);
        break;
      case 3:
        graphics2D.drawImage(image[3], position.getX(), position.getY(), width, height, null);
        break;
      default:
        graphics2D.drawImage(image[0], position.getX(), position.getY(), width, height, null);
    }
    if (GamePanel.DEBUG) {
      drawBoundings(graphics2D, Color.BLUE);
    }
  }

  public void move() {
    setPosition(nextPosition);
    this.nextPosition = getPosition();
  }

  public boolean hasPotion(){
    for(Item item : inventory){
      if (item instanceof heal_potion){
        return true;
      }
    }
    return false;
  }

  public void removePotion(){
    int i = 0;
    for(Item item : inventory){
      if (item instanceof heal_potion){
        inventory.remove(i);
      }
      i++;
    }
  }

}
