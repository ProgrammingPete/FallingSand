import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
      grid[row][col] = tool;
  
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      for(int i = 0; i < grid.length-1; i++){
         for(int j = 1; j < grid[i].length-1; j++){
         
            if(grid[i][j] == EMPTY){
            Color black =  new Color(0,0,0);
            display.setColor(i,j, black);
            }
            
            if(grid[i][j] == METAL){
            Color grey = new Color(128,128,128);
            display.setColor(i,j, grey);
            }   
            
            if(grid[i][j] == SAND){
            Color yellow = new Color(255,255,0);
            display.setColor(i,j, yellow);
            }     
            
            if(grid[i][j] == WATER){
            Color blue = new Color(0,0,255);
            display.setColor(i,j, blue);
            }    
         }
      }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
      int randRow = (int)(Math.random() * (grid.length -1));
      int randCol = (int)(Math.random() * (grid[1].length - 1));
      int rand = (int)(Math.random() * 3) + 1;
      //System.out.println(rand);      
      
      if(randRow == (grid.length - 1)){
         randRow--;               
      }      
      if(randCol == 0){
         randCol++;       
      }
      if(randCol == (grid[0].length -1)){
         randCol--;       
      }
      
      if(grid[randRow][randCol] == SAND && (grid[randRow + 1][randCol] == EMPTY || grid[randRow + 1][randCol] == WATER)){
         
         int temp = grid[randRow + 1][randCol];         
         grid[randRow + 1][randCol] = SAND;
                  
         if(temp == WATER){
            grid[randRow][randCol] = WATER;
         }else{
            grid[randRow][randCol] = EMPTY;
         }         
      }
      
      if(grid[randRow][randCol] == WATER){         
         
            
            if(rand == 1 && grid[randRow + 1][randCol] == EMPTY){                 
                  
                  
               grid[randRow + 1][randCol] = WATER;            
               grid[randRow][randCol] = EMPTY;            
            }
         
         
               if(rand == 2 && grid[randRow][randCol + 1] == EMPTY){
         
                  grid[randRow][randCol + 1] = WATER;            
                  grid[randRow][randCol] = EMPTY;           
               }
         
         
               if(rand == 3 && grid[randRow][randCol - 1] == EMPTY){
            
                  grid[randRow][randCol - 1] = WATER;
                  grid[randRow][randCol] = EMPTY;            
               }
         
      }
   }     
      
  
  
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
