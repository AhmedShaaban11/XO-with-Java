import java.util.Scanner;

public class Main {
  public static class Player {
    protected String name;
    protected char symbol;

    public Player(String nm, char smbl) {
      name = nm;
      symbol = smbl;
    }

    public int[] get_move() {
      Scanner in = new Scanner(System.in);
      int[] arr = new int[2];
      System.out.println("Enter the row number:");
      arr[0] = in.nextInt() - 1;
      System.out.println("Enter the col number:");
      arr[1] = in.nextInt() - 1;
      return arr;
    }

    public String get_name() {
      return name;
    }

    public char get_symbol() {
      return symbol;
    }
  }

  public static abstract class Board {
    protected int n;
    protected char smbl;
    protected char[][] grid;

    private void fill_board() {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          grid[i][j] = smbl;
        }
      }
    }

    public Board(int n) {
      this.n = n;
      smbl = '.';
      grid = new char[n][n];
      fill_board();
    }

    public abstract boolean update_board(int x, int y, char smbl);

    public abstract boolean is_winner(char smbl);

    public abstract boolean is_draw();

    public void display_board() {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          System.out.print(grid[i][j] + " ");
        }
        System.out.println();
      }
    }
  }

  public static class XO extends Board {
    public XO() {
      super(3);
    }

    public boolean update_board(int x, int y, char smbl) {
      if (x >= n || y >= n || x < 0 || y < 0) {
        System.out.println("Pos isn't valid!");
        return false;
      } else if (grid[x][y] != this.smbl) {
        System.out.println("Pos is already taken!");
        return false;
      }
      grid[x][y] = smbl;
      return true;
    }

    public boolean is_winner(char smbl) {
      if ((grid[0][0] == smbl && grid[1][1] == smbl && grid[2][2] == smbl)
          || (grid[0][2] == smbl && grid[1][1] == smbl && grid[2][0] == smbl)) {
        return true;
      }
      for (int i = 0; i < n; ++i) {
        if ((grid[i][0] == smbl && grid[i][1] == smbl && grid[i][2] == smbl)
            || (grid[0][i] == smbl && grid[1][i] == smbl && grid[2][i] == smbl)) {
          return true;
        }
      }
      return false;
    }

    public boolean is_draw() {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          if (grid[i][j] == smbl) {
            return false;
          }
        }
      }
      return true;
    }
  }

  public static class Game {
    private Board board;
    private Player[] players;
    private int turn;

    public Game(Board board, Player[] players) {
      turn = 0;
      this.board = board;
      this.players = players;
    }

    public void play_game() {
      System.out.println("Player 1: " + players[0].get_name() + " (X)");
      System.out.println("Player 2: " + players[1].get_name() + " (O)");
      do {
        System.out.println("==============================");
        board.display_board();
        System.out.println("---------------");
        System.out.printf("Player %s (%c) turn!\n", players[turn].get_name(), players[turn].get_symbol());
        boolean is_updated;
        do {
          int[] mve = players[turn].get_move();
          is_updated = board.update_board(mve[0], mve[1], players[turn].get_symbol());
        } while (!is_updated);
        if (board.is_winner(players[turn].get_symbol())) {
          System.out.printf("Player %s (%c) is winner!\n", players[turn].get_name(), players[turn].get_symbol());
          return;
        }
        if (turn == 0) {
          turn = 1;
        } else {
          turn = 0;
        }
      } while (!board.is_draw());
      System.out.println("Draw!");
    }
  }

  public static void main(String[] args) {
    String name1, name2;
    Scanner in = new Scanner(System.in);
    System.out.println("Enter 1st player name:");
    name1 = in.nextLine();
    System.out.println("Enter 2nd player name:");
    name2 = in.nextLine();
    Player[] players = new Player[2];
    players[0] = new Player(name1, 'X');
    players[1] = new Player(name2, 'O');
    Game gme = new Game(new XO(), players);
    gme.play_game();
  }
}