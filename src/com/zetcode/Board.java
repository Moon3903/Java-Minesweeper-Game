package com.zetcode;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel {

	private static final long serialVersionUID = -8164806567825451054L;
	
	private final int NUM_IMAGES = 13;
    private int CELL_SIZE;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private int N_MINES;
    private int N_ROWS;
    private int N_COLS;

    private int BOARD_WIDTH = 500;
    private int BOARD_HEIGHT = 530;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel statusbar;
    private final JLabel timebar;
    
    private Timer timer;
    private int second;
    private int minute;
    
    private boolean inMenu = true;
    private Menu menu;
    
    private HighScore hs;

    public Board(JLabel statusbar,JLabel timebar) {
    	this.timebar = timebar;
		this.statusbar = statusbar;
		System.out.print("ok\n");
		initMenu();
    }
    
    private void initMenu() {
    	setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        addMouseListener(new MinesAdapter());
        menu = new Menu();
        System.out.print("test\n");
    }
    
    private void initGame(int row,int col,int mines,String diff,int size){
    	N_ROWS = row;
    	N_COLS = col;
    	N_MINES = mines;
    	CELL_SIZE = size;
        initBoard(diff);
    }
    
    private void initBoard(String diff) {

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {

            var path = "src/resources/"+ diff + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        timer1();
        
        newGame();
    }

    private void newGame() {

        int cell;
        second = 0;
        minute = 0;

        var random = new Random();
        inGame = true;
        minesLeft = N_MINES;

        allCells = N_ROWS * N_COLS;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {

            field[i] = COVER_FOR_CELL;
        }

        statusbar.setText(Integer.toString(minesLeft));
        timebar.setText("00:00"+hs.getHS());

        int i = 0;

        while (i < N_MINES) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
        
        timer.start();
    }

    private void find_empty_cells(int j) {

        int current_col = j % N_COLS;
        int cell;

        if (current_col > 0) {
            cell = j - N_COLS - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS - 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - N_COLS;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + N_COLS;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + N_COLS + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }
    
    public void timer1(){
    	//mengatur waktu
        int delay = 1000; // one second 
         ActionListener taskPerformer = new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
            	 second+=1;
            	 StringBuilder gameTime = new StringBuilder();
            	 if(second == 60) {
            		 minute+=1;
            		 second=0;
            	 }
            	 if(minute<10) 
            		 gameTime.append('0'+Integer.toString(minute)+':');
            	 
            	 else 
            		 gameTime.append(Integer.toString(minute)+':');
            	 
            	 if(second<10) 
            		 gameTime.append('0'+Integer.toString(second));
            	 
            	 else 
            		 gameTime.append(Integer.toString(second));
            	 gameTime.append(hs.getHS());
                 timebar.setText(gameTime.toString());
            	 repaint();
             }
         };
         timer = new Timer(delay, taskPerformer);

   }

    @Override
    public void paintComponent(Graphics g) {
    	
    	if(inMenu) {
    		menu.render(g);
    	}
    	else {
    		int uncover = 0;
            
            for (int i = 0; i < N_ROWS; i++) {

                for (int j = 0; j < N_COLS; j++) {

                    int cell = field[(i * N_COLS) + j];

                    if (inGame && cell == MINE_CELL) {

                        inGame = false;
                    }

                    if (!inGame) {

                        if (cell == COVERED_MINE_CELL) {
                            cell = DRAW_MINE;
                        } else if (cell == MARKED_MINE_CELL) {
                            cell = DRAW_MARK;
                        } else if (cell > COVERED_MINE_CELL) {
                            cell = DRAW_WRONG_MARK;
                        } else if (cell > MINE_CELL) {
                            cell = DRAW_COVER;
                        }

                    } else {

                        if (cell > COVERED_MINE_CELL) {
                            cell = DRAW_MARK;
                        } else if (cell > MINE_CELL) {
                            cell = DRAW_COVER;
                            uncover++;
                        }
                    }

                    g.drawImage(img[cell], (j * CELL_SIZE),
                            (i * CELL_SIZE), this);
                }
            }

            if (uncover == 0 && inGame) {

                inGame = false;
                statusbar.setText("Game won");
                timer.stop();
                hs.compareHS(second+minute*60);

            } else if (!inGame) {
            	timer.stop();
                statusbar.setText("Game lost");
            }
    	}
        
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            
            if (inMenu == true) {
            	
                if (x >= 110 && x <= 380) {
                    if (y >= 150 && y <= 250) {
                        inMenu = false;
                        hs = new HighScore("easy.txt");
                        initGame(10,10,10,"easy/",50);
                    }
                }

                if (x >= 110 && x <= 380) {
                    if (y >= 260 && y <= 360) {
                        inMenu = false;
                        hs = new HighScore("medium.txt");
                        initGame(20,20,50,"medium/",25);
                    }
                }

                if (x >= 110 && x <= 380) {
                    if (y >= 370 && y <= 470) {
                    	inMenu = false;
                    	hs = new HighScore("hard.txt");
                    	initGame(25,25,100,"hard/",20);
                    }
                }
            }
            else {
            	int cCol = x / CELL_SIZE;
                int cRow = y / CELL_SIZE;

                boolean doRepaint = false;

                if (!inGame) {

                    newGame();
                    repaint();
                }

                if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                    if (e.getButton() == MouseEvent.BUTTON3) {

                        if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {

                            doRepaint = true;

                            if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {

                                if (minesLeft > 0) {
                                    field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                    minesLeft--;
                                    String msg = Integer.toString(minesLeft);
                                    statusbar.setText(msg);
                                } else {
                                    statusbar.setText("No marks left");
                                }
                            } else {

                                field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                                minesLeft++;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            }
                        }

                    } else {

                        if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {

                            return;
                        }

                        if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
                                && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

                            field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                            doRepaint = true;

                            if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                                inGame = false;
                            }

                            if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                                find_empty_cells((cRow * N_COLS) + cCol);
                            }
                        }
                    }

                    if (doRepaint) {
                        repaint();
                    }
                }
            }
        }
    }
}
