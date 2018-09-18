package com.snake.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.mysnakeapp.R;
import com.snake.bean.Point;
import com.snake.constant.setting;
import com.snake.db.snakeManger;

public class SnakeView extends View {

	/* =========���ݿ�洢snake��Ϣ======= */
	private snakeManger mSnakeManger;

	/* =========�ⲿ��������������Ϸ�ı���======= */
	public final int UP = 1;// �����ߵ��ƶ�����
	public final int RIGHT = 2;
	public final int DOWN = 3;
	public final int LEFT = 4;

	private int count = 0;// �Ѷ�ϵ��

	private int direction = UP;// �ߵ��ƶ�����

	private int score = 0;// ��ǰ�÷�

	private boolean isRunning = true;// ��ͣ��Ϸ

	/* =======�滭�������======== */
	private int xCount;// �滭����������
	private int yCount;

	private int xOffSet;// ��Ϸ�����ƫ����
	private int yOffSet;

	private int size = 25;// �滭�ķ���Ĵ�С

	private Paint mPaint;// ����

	private int map[][];// ��Ż滭ͼƬ�ı�־����

	private Bitmap picBitmap[];// �����Ҫ���Ƶ�ͼƬ

	private final int WALL = 1;// ǽ��mapӳ��
	private final int SNAKE_HEAD = 2;// ��ͷ��mapӳ��
	private final int SNAKE_BODY = 3;// �����mapӳ��
	private final int MOUSE1 = 4;// �����mapӳ��
	private final int MOUSE2 = 5;// �ж�������
	private final int IMAGE = 6;// ����ͼƬӳ��

	private static int mouse2_time = 0;// ����������ֵ�ʱ��

	private static boolean showImage = false;// �Ƿ���ʾ��ЧͼƬ(�Ե�ƻ��ʱ)

	private List<Point> snake = new ArrayList<Point>();// �����洢��ǰ�ߵ�����
	private Point mouse1, mouse2;// ��ʾ����ĵ�

	/* ========���ڸ��»滭�����handler========= */

	private updateHandler handler = new updateHandler();

	private class updateHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			if (setting.SNAKE_IS_LIVING && isRunning) {
				switch (setting.MODEL) {
				case setting.EASE:
					SnakeView.this.gameRunning();
					handler.sleep(setting.SNAKE_MOVEMENT_SPEED);
					break;
				case setting.MEDIUM:
					SnakeView.this.gameRunning();
					handler.sleep((int) (setting.SNAKE_MOVEMENT_SPEED * Math
							.pow(0.99, count)));
					mouse2_time++;
					if (count != score) {
						count = score;
					}
					break;
				case setting.HARD:
					SnakeView.this.gameRunning();
					handler.sleep((int) (setting.SNAKE_MOVEMENT_SPEED * Math
							.pow(0.99, count)));
					if (count < 140) {
						count++;
					}
					// count++;
					mouse2_time++;
					break;
				default:
				}

			}
		}

		public void sleep(long time) {

			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), time);

		}

	}

	/* ===========���غ���========== */
	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mSnakeManger = new snakeManger(context);
		if (mSnakeManger.query().size() > 0) {
			setting.SNAKE_IS_LIVING = true;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {// ��ʼ���滭����ı���
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		size = (int) (50 * setting.DENSITY / 3);

		xCount = w / size;
		yCount = h / size;

		xOffSet = (w - xCount * size) / 2;
		yOffSet = (h - yCount * size) / 2;

		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);

		// Log.i("TAG", "xCount=:" + xCount + "\tyCount=" + yCount);
		map = new int[xCount][yCount];

		picBitmap = new Bitmap[IMAGE + 1];

		// ����ͼƬ
		loadPicture(WALL, this.getResources().getDrawable(R.drawable.wall));
		loadPicture(SNAKE_HEAD, this.getResources()
				.getDrawable(R.drawable.head));
		loadPicture(SNAKE_BODY,
				this.getResources().getDrawable(R.drawable.snake));
		loadPicture(MOUSE1, this.getResources().getDrawable(R.drawable.blue));
		loadPicture(MOUSE2, this.getResources().getDrawable(R.drawable.mouse));
		loadPicture(IMAGE, this.getResources().getDrawable(R.drawable.glow2));

		createWall();
		createSnake();
		createMouse();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		for (int x = 0; x < xCount; x++) {

			for (int y = 0; y < yCount; y++) {

				// ����ǽ
				if (map[x][y] > 0) {
					canvas.drawBitmap(picBitmap[map[x][y]], xOffSet + x * size,
							yOffSet + y * size, mPaint);
				}

				if (showImage && (map[x][y] == SNAKE_HEAD)) {
					canvas.drawBitmap(picBitmap[IMAGE], xOffSet + (x - 1)
							* size, yOffSet + (y - 1) * size, mPaint);

					showImage = !showImage;
				}

				// ���Ʊ�����
				canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet + xCount
						* size, yOffSet + y * size, mPaint);

				// // ���Ʊ��
				// if (x == y) {
				// canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet
				// + xCount * size, yOffSet + y * size, mPaint);
				// canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x
				// * size, yOffSet + yCount * size, mPaint);
				// }
			}

			// ���Ʊ������
			canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x * size,
					yOffSet + yCount * size, mPaint);
		}

		// for (int x = 0; x < xCount; x++) {// ���ƺ�ǽ������
		//
		// // ����ǽ
		// canvas.drawBitmap(picBitmap[map[x][0]], xOffSet + x * size, yOffSet
		// + 0 * size, mPaint);
		// canvas.drawBitmap(picBitmap[map[x][yCount - 1]],
		// xOffSet + x * size, yOffSet + (yCount - 1) * size, mPaint);
		//
		// // ���Ʊ������
		// canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x * size,
		// yOffSet + yCount * size, mPaint);
		// }
		//
		// for (int y = 0; y < yCount; y++) {// ������ǽ������
		//
		// // ����ǽ
		// canvas.drawBitmap(picBitmap[map[0][y]], xOffSet + 0 * size, yOffSet
		// + y * size, mPaint);
		// canvas.drawBitmap(picBitmap[map[xCount - 1][y]], xOffSet
		// + (xCount - 1) * size, yOffSet + y * size, mPaint);
		//
		// // ���Ʊ�����
		// canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet + (xCount - 1)
		// * size, yOffSet + y * size, mPaint);
		// }

		// �滭���������
		canvas.drawLine(xOffSet, yOffSet + yCount * size, xOffSet + xCount
				* size, yOffSet + yCount * size, mPaint);
		canvas.drawLine(xOffSet + xCount * size, yOffSet, xOffSet + xCount
				* size, yOffSet + yCount * size, mPaint);

	}

	/* =======�Զ��帨���෽��====== */

	private void loadPicture(int key, Drawable drawable) {// ��ͼƬ����Bitmap

		if (key == IMAGE) {// ��ЧͼƬ
			Bitmap mBitmap = Bitmap.createBitmap(size * 3, size * 3,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(mBitmap);
			drawable.setBounds(0, 0, size * 3, size * 3);
			drawable.draw(canvas);
			picBitmap[key] = mBitmap;
		} else {
			Bitmap mBitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
			Canvas canvas = new Canvas(mBitmap);
			drawable.setBounds(0, 0, size, size);
			drawable.draw(canvas);
			picBitmap[key] = mBitmap;
		}

	}

	private void createWall() {// ����ǽ

		for (int x = 0; x < xCount; x++) {// ����X�����ǽ
			map[x][0] = WALL;
			map[x][yCount - 1] = WALL;

			if (setting.MODEL == setting.HARD) {// ����
				if (x < xCount / 3 || x > xCount / 3 * 2) {
					map[x][yCount / 2] = WALL;
				}
			}

		}

		for (int y = 0; y < yCount; y++) {// ����Y�����ǽ
			map[0][y] = WALL;
			map[xCount - 1][y] = WALL;

			if (setting.MODEL == setting.HARD) {// ����
				if (y < yCount / 3 || y > yCount / 3 * 2) {
					map[xCount / 2][y] = WALL;
				}
			}

		}

	}

	private void createSnake() {// ������

		snake.add(new Point(2, yCount - 6));
		snake.add(new Point(2, yCount - 5));
		snake.add(new Point(2, yCount - 4));
		snake.add(new Point(2, yCount - 3));

		// ����ʾ�ߵ�ͼƬ
		for (Point point : snake) {
			setMap(point, SNAKE_BODY);
		}
		setMap(snake.get(0), SNAKE_HEAD);

	}

	private void createMouse() {// ��������

		Random mRandomMouse = new Random();
		while (mouse1 == null) {
			mouse1 = new Point(1 + mRandomMouse.nextInt(xCount - 2),
					1 + mRandomMouse.nextInt(yCount - 2));

			if (map[mouse1.getX()][mouse1.getY()] > 0) {
				mouse1 = null;
			}

			for (Point point : snake) {
				if (point.equals(mouse1)) {
					mouse1 = null;
					break;
				}
			}
			//
			// if (mouse1.equals(mouse2)) {
			// mouse1 = null;
			// }
		}
		map[mouse1.getX()][mouse1.getY()] = MOUSE1;

		if (setting.MODEL != setting.EASE) {
			if (mouse2_time > (xCount + yCount)) {
				mouse2 = null;
				mouse2_time = 0;
			}

			while (mouse2 == null) {
				mouse2 = new Point(1 + mRandomMouse.nextInt(xCount - 2),
						1 + mRandomMouse.nextInt(yCount - 2));

				if (map[mouse2.getX()][mouse2.getY()] > 0) {
					mouse2 = null;
				}

				for (Point point : snake) {
					if (point.equals(mouse2)) {
						mouse2 = null;
						break;
					}
				}
				//
				// if (mouse2.equals(mouse1)) {
				// mouse2 = null;
				// }

			}

			map[mouse2.getX()][mouse2.getY()] = MOUSE2;
		}
	}

	private void clearGame() {// �����ǰ�滭�Ա���»滭����

		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {
				map[x][y] = 0;
			}
		}
	}

	private void setMap(Point mPoint, int pictureType) {// ���ݵ�ǰ���ݸ���map����
		// TODO Auto-generated method stub
		map[mPoint.getX()][mPoint.getY()] = pictureType;
	}

	private void gameRunning() {// ��Ϸ��������״̬
		clearGame();
		createWall();
		createMouse();
		updateSnake();
		SnakeView.this.invalidate();
	}

	private void updateSnake() {// ˢ����

		Point oldSnakeHeaad = snake.get(0);
		Point newSnakeHead = null;

		/* ======������ͷ====== */
		switch (direction) {
		case UP:
			newSnakeHead = new Point(oldSnakeHeaad.getX(),
					(oldSnakeHeaad.getY() - 1 + yCount) % yCount);
			break;
		case RIGHT:
			newSnakeHead = new Point((oldSnakeHeaad.getX() + 1) % xCount,
					oldSnakeHeaad.getY());
			break;
		case DOWN:
			newSnakeHead = new Point(oldSnakeHeaad.getX(),
					(oldSnakeHeaad.getY() + 1) % yCount);
			break;
		case LEFT:
			newSnakeHead = new Point((oldSnakeHeaad.getX() - 1 + xCount)
					% xCount, oldSnakeHeaad.getY());
			break;
		default:
		}

		/* ======�ж���Ϸ�Ƿ���Ҫ����====== */
		// if (newSnakeHead.getX() == 0 || newSnakeHead.getY() == 0
		// || newSnakeHead.getX() == xCount - 1
		// || newSnakeHead.getY() == yCount - 1) {
		if (map[newSnakeHead.getX()][newSnakeHead.getY()] == WALL) {
			if (setting.WALL_YES_NO) {// ��ǽ
				if (newSnakeHead.getX() == 0 || newSnakeHead.getY() == 0
						|| newSnakeHead.getX() == xCount - 1
						|| newSnakeHead.getY() == yCount - 1) {// ��ǽ
					switch (direction) {
					case UP:
						newSnakeHead.setY(yCount - 2);
						break;
					case RIGHT:
						newSnakeHead.setX(1);
						break;
					case DOWN:
						newSnakeHead.setY(1);
						break;
					case LEFT:
						newSnakeHead.setX(xCount - 2);
						break;
					default:
					}
				} else {// �ϰ���
					gameOver(0);
				}

			} else {// ײǽ
				gameOver(0);
			}
		} else if (newSnakeHead.equals(mouse1)) {// �Ե�����

			// // ͼƬ��˸��Ч
			// clearGame();
			// createWall();
			// // map[mouse.getX() - 1][mouse.getY() - 1] = IMAGE;
			// setMap(mouse, IMAGE);
			// SnakeView.this.invalidate();

			showImage = true;

			mouse1 = null;
			score++;

			if (setting.MODEL == setting.MEDIUM) {// һ��
				count++;
			} else if (setting.MODEL == setting.HARD) {
				count = Math.abs(score);
			}

			Intent intent = new Intent();// ���͹㲥���µ�ǰ�÷�
			intent.setAction("UpdateScore");
			getContext().sendBroadcast(intent);

			intent = new Intent("com.snake.service.MusicService");// ��������
			intent.addFlags(1);
			getContext().startService(intent);

		} else if ((setting.MODEL != setting.EASE)
				&& newSnakeHead.equals(mouse2)) {// �Ե�����������

			// // ͼƬ��˸��Ч
			// clearGame();
			// createWall();
			// // map[mouse.getX() - 1][mouse.getY() - 1] = IMAGE;
			// setMap(mouse, IMAGE);
			// SnakeView.this.invalidate();

			showImage = true;
			mouse2 = null;
			score--;
			if (setting.MODEL == setting.MEDIUM) {// һ��
				count--;
			} else if (setting.MODEL == setting.HARD) {
				count++;
			}

			Intent intent = new Intent();// ���͹㲥���µ�ǰ�÷�
			intent.setAction("UpdateScore");
			getContext().sendBroadcast(intent);

			intent = new Intent("com.snake.service.MusicService");// ��������
			intent.addFlags(1);
			getContext().startService(intent);

		} else {// ҧ���Լ�
			for (Point point : snake) {
				if (newSnakeHead.equals(point)) {
					gameOver(0);
					break;
				}
			}
		}
		/* ======�����ߵĳ���====== */
		// if (mouse1 != null) {
		// snake.add(0, newSnakeHead);
		// } else {
		// snake.add(0, newSnakeHead);
		// }

		// if (setting.MODEL == setting.HARD) {// �Ե�����������
		// if (mouse2 == null) {
		// count--;
		// }
		// } else if (setting.MODEL == setting.HARD) {
		// if (mouse2 == null) {
		// count++;
		// }
		// }
		if (mouse1 == null) {
			snake.add(0, newSnakeHead);
		} else if ((setting.MODEL != setting.EASE && mouse2 == null)) {
			snake.add(0, newSnakeHead);
			if (setting.MODEL == setting.MEDIUM) {
				snake.remove(snake.size() - 1);
				// Toast.makeText(getContext(), "����", 0).show();
			}
		} else {
			snake.add(0, newSnakeHead);
			snake.remove(snake.size() - 1);
		}
		/* ======�����ߣ��Ա�ˢ��====== */
		for (Point point : snake) {
			setMap(point, SNAKE_BODY);
		}
		setMap(snake.get(0), SNAKE_HEAD);

		/* ======������ͷͼƬ====== */

		switch (direction) {// ������ͷͼƬ
		case UP:
			loadPicture(SNAKE_HEAD,
					this.getResources().getDrawable(R.drawable.head_n));
			break;
		case RIGHT:
			loadPicture(SNAKE_HEAD,
					this.getResources().getDrawable(R.drawable.head_e));
			break;
		case DOWN:
			loadPicture(SNAKE_HEAD,
					this.getResources().getDrawable(R.drawable.head_s));
			break;
		case LEFT:
			loadPicture(SNAKE_HEAD,
					this.getResources().getDrawable(R.drawable.head_w));
			break;
		default:
			break;
		}

	}

	public void gameOver(int flag) {// ��Ϸ�������͹㲥
		Intent intent = new Intent();// ���͹㲥������Ϸ
		intent.setAction("SnakeDeath");
		getContext().sendBroadcast(intent);
		isRunning = false;
		setting.SNAKE_IS_LIVING = false;

		intent = new Intent("com.snake.service.MusicService");// ��������
		intent.addFlags(flag);
		getContext().startService(intent);
	}

	/* ===========���ⲿ�����ĺ���========= */
	public void setSnakeDirection(int direction) {// �ı��ߵ��ƶ�����

		if (isRunning && (Math.abs(this.direction - direction) != 2)) {
			this.direction = direction;
		}

		// this.direction = direction;
	}

	public int getSnakeScore() {// ���ص�ǰ�ķ�
		return score;
	}

	public void newGame() {// ����Ϸ
		snake.clear();
		score = 0;
		count = 0;
		mouse1 = null;
		mouse2 = null;
		createSnake();
		direction = UP;
		setting.SNAKE_IS_LIVING = true;
		isRunning = true;
		SnakeView.this.invalidate();
		handler.sleep(200);

		Intent intent = new Intent();// ���͹㲥���µ�ǰ�÷�
		intent.setAction("UpdateScore");
		getContext().sendBroadcast(intent);
	}

	public void stopGame() {// ��ͣ��Ϸ
		isRunning = false;

	}

	public void againGame() {// ������Ϸ

		if (mSnakeManger.query().size() <= 0) {
			if (setting.SNAKE_IS_LIVING) {
				isRunning = true;
				handler.sleep(200);
			} else {
				newGame();
			}
		} else {// �����ݿ��ж�ȡ����
			snake.clear();
			snake = new ArrayList<Point>(mSnakeManger.query());
			count = snake.get(snake.size() - 1).getX();
			mouse2_time = snake.get(snake.size() - 1).getY();
			snake.remove(snake.size() - 1);
			direction = snake.get(snake.size() - 1).getX();
			score = snake.get(snake.size() - 1).getY();
			snake.remove(snake.size() - 1);
			mouse1 = snake.get(snake.size() - 1);
			snake.remove(snake.size() - 1);
			mouse2 = snake.get(snake.size() - 1);
			snake.remove(snake.size() - 1);
			mSnakeManger.delete();
			isRunning = true;
			handler.sleep(200);
		}

		Intent intent = new Intent();// ���͹㲥���µ�ǰ�÷�
		intent.setAction("UpdateScore");
		getContext().sendBroadcast(intent);

	}

	public void saveGame() {// ������Ϸ
		mSnakeManger.delete();// ������ݿ�
		snake.add(mouse2);// �ж�����λ��
		snake.add(mouse1);// ����λ��
		snake.add(new Point(direction, score));// ��ǰ�÷�
		snake.add(new Point(count, mouse2_time));
		mSnakeManger.insert(snake);

	}

	public boolean getSnakeIsRunning() {// �ж��ߵ�����״̬
		return isRunning;
	}
}
