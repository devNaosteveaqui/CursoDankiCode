/*
 * 
 * Author: Guilherme Grillo
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener, MouseListener {

	
	public static final int WIDTH = 300, HEIGHT = 300;
	public int PLAYER = 1,OPONENTE = -1,CURRENT = OPONENTE, EMPATE = 0;
	
	public BufferedImage PLAYER_SPRITE,OPONENTE_SPRITE;
	public int[][] TABULEIRO = new int[3][3];
	public static int mx,my;
	public static boolean isPressed = false;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.addKeyListener(this);
		this.addMouseListener(this);
		try {
			PLAYER_SPRITE = ImageIO.read(getClass().getResource("/player.png"));
			OPONENTE_SPRITE = ImageIO.read(getClass().getResource("/oponente.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetTabuleiro() {
		for(int xx = 0; xx< TABULEIRO.length;xx++){
			for(int yy =0; yy<TABULEIRO.length;yy++) {
				TABULEIRO[xx][yy] = 0;
			}
		}
	}
	public int checkVictory() {
		int score = 0;
		int xx = 0, yy =0;
		int[] axis = new int[8];
		for(int j = 0; j < 3; j++) {
			score += (TABULEIRO[0][j]!=0?1:0) + (TABULEIRO[1][j]!=0?1:0) + (TABULEIRO[2][j]!=0?1:0);
			axis[0] += TABULEIRO[j][yy];
			axis[1] += TABULEIRO[xx][j];

			axis[2] += TABULEIRO[j][j];
			axis[3] += TABULEIRO[2-j][j];

			axis[4] += TABULEIRO[xx+1][j];
			axis[5] += TABULEIRO[j][yy+1];

			axis[6] += TABULEIRO[xx+2][j];
			axis[7] += TABULEIRO[j][yy+2];

		}
		for(int i = 0; i < axis.length; i++) {
			if(axis[i] == 3*PLAYER) return PLAYER;
			else if(axis[i] == 3*OPONENTE) return OPONENTE;
		}
		if(score == 9) return EMPATE;
		return -10;
	}
	public void tick() {
		if(CURRENT == PLAYER) {
			if(isPressed) {
				isPressed = false;
				mx/=100;
				my/=100;
				if(TABULEIRO[mx][my] == 0) {
					TABULEIRO[mx][my] = PLAYER;
					CURRENT = OPONENTE;
				}
			}
		}else if(CURRENT == OPONENTE){
			for(int xx = 0; xx< TABULEIRO.length; xx++) {
				for(int yy = 0; yy<TABULEIRO.length; yy++) {
					if(TABULEIRO[xx][yy]==0) {
						Node bestMove = getBestMove(xx, yy, 0, OPONENTE);
						TABULEIRO[bestMove.x][bestMove.y] = OPONENTE;
						CURRENT = PLAYER;
						return;
					}
				}
			}
		}
		if(checkVictory() == PLAYER) {
			System.out.println("PLAYER 1");
			resetTabuleiro();
		} else if(checkVictory() == OPONENTE) {
			System.out.println("PLAYER 2");
			resetTabuleiro();
		} else if(checkVictory() == EMPATE) {
			System.out.println("EMPATE");
			resetTabuleiro();
		}
	}
	public Node getBestMove(int x, int y, int depth, int turno) {
		if(checkVictory() == PLAYER) {
			return new Node(x,y,depth-10,depth);
		} else if(checkVictory() == OPONENTE) {
			return new Node(x,y,10-depth,depth);
		} else if(checkVictory() == EMPATE) {
			return new Node(x,y,0,depth);
		}
		List<Node> nodes = new ArrayList<Node>();
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy =0; yy < TABULEIRO.length; yy++) {
				if(TABULEIRO[xx][yy] == 0) {
					Node node;
					if(turno == PLAYER) {
						TABULEIRO[xx][yy] = PLAYER;
						node = getBestMove(xx,yy,depth+1,OPONENTE);
					} else {
						TABULEIRO[xx][yy] = OPONENTE;
						node = getBestMove(xx,yy,depth+1,PLAYER);
					}
					TABULEIRO[xx][yy] = 0;
					nodes.add(node);
				}

			}
		}

		Node finalNode = nodes.get(0);
		for(int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			if(turno == PLAYER) {
				if(n.score > finalNode.score) {
					finalNode = n;
				}
			} else {
				if(n.score < finalNode.score) {
					finalNode = n;
				}
			}
		}
		return finalNode;
	}
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH,HEIGHT);
		
		for(int xx = 0; xx < TABULEIRO.length; xx++) {
			for(int yy = 0; yy < TABULEIRO.length; yy++) {
				g.setColor(Color.black);
				g.drawRect(xx*100, yy*100, 100,100);
				if(TABULEIRO[xx][yy] == PLAYER) {
					g.drawImage(PLAYER_SPRITE,xx*100+25, yy*100+25,50,50,null);
				}else if(TABULEIRO[xx][yy] == OPONENTE) {
					g.drawImage(OPONENTE_SPRITE,xx*100+25, yy*100+25,50,50,null);
				}
			}
		}
		
		g.dispose();
		bs.show();
	}

	
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		
		while(true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		mx = mouseEvent.getX();
		my = mouseEvent.getY();
		isPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}
}
