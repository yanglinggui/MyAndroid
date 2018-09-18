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

	/* =========数据库存储snake信息======= */
	private snakeManger mSnakeManger;

	/* =========外部需求用来操作游戏的变量======= */
	public final int UP = 1;// 控制蛇的移动方向
	public final int RIGHT = 2;
	public final int DOWN = 3;
	public final int LEFT = 4;

	private int count = 0;// 难度系数

	private int direction = UP;// 蛇的移动方向

	private int score = 0;// 当前得分

	private boolean isRunning = true;// 暂停游戏

	/* =======绘画界面变量======== */
	private int xCount;// 绘画表格的线条数
	private int yCount;

	private int xOffSet;// 游戏界面的偏移量
	private int yOffSet;

	private int size = 25;// 绘画的方格的大小

	private Paint mPaint;// 画笔

	private int map[][];// 存放绘画图片的标志数组

	private Bitmap picBitmap[];// 存放需要绘制的图片

	private final int WALL = 1;// 墙的map映射
	private final int SNAKE_HEAD = 2;// 蛇头的map映射
	private final int SNAKE_BODY = 3;// 蛇身的map映射
	private final int MOUSE1 = 4;// 老鼠的map映射
	private final int MOUSE2 = 5;// 中毒的老鼠
	private final int IMAGE = 6;// 动画图片映射

	private static int mouse2_time = 0;// 带毒老鼠出现的时间

	private static boolean showImage = false;// 是否显示特效图片(吃到苹果时)

	private List<Point> snake = new ArrayList<Point>();// 用来存储当前蛇的数据
	private Point mouse1, mouse2;// 表示老鼠的点

	/* ========由于更新绘画界面的handler========= */

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

	/* ===========重载函数========== */
	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mSnakeManger = new snakeManger(context);
		if (mSnakeManger.query().size() > 0) {
			setting.SNAKE_IS_LIVING = true;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {// 初始化绘画界面的变量
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

		// 加载图片
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

				// 绘制墙
				if (map[x][y] > 0) {
					canvas.drawBitmap(picBitmap[map[x][y]], xOffSet + x * size,
							yOffSet + y * size, mPaint);
				}

				if (showImage && (map[x][y] == SNAKE_HEAD)) {
					canvas.drawBitmap(picBitmap[IMAGE], xOffSet + (x - 1)
							* size, yOffSet + (y - 1) * size, mPaint);

					showImage = !showImage;
				}

				// 绘制表格横线
				canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet + xCount
						* size, yOffSet + y * size, mPaint);

				// // 绘制表格
				// if (x == y) {
				// canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet
				// + xCount * size, yOffSet + y * size, mPaint);
				// canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x
				// * size, yOffSet + yCount * size, mPaint);
				// }
			}

			// 绘制表格竖线
			canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x * size,
					yOffSet + yCount * size, mPaint);
		}

		// for (int x = 0; x < xCount; x++) {// 绘制横墙及竖线
		//
		// // 绘制墙
		// canvas.drawBitmap(picBitmap[map[x][0]], xOffSet + x * size, yOffSet
		// + 0 * size, mPaint);
		// canvas.drawBitmap(picBitmap[map[x][yCount - 1]],
		// xOffSet + x * size, yOffSet + (yCount - 1) * size, mPaint);
		//
		// // 绘制表格竖线
		// canvas.drawLine(xOffSet + x * size, yOffSet, xOffSet + x * size,
		// yOffSet + yCount * size, mPaint);
		// }
		//
		// for (int y = 0; y < yCount; y++) {// 绘制竖墙及横线
		//
		// // 绘制墙
		// canvas.drawBitmap(picBitmap[map[0][y]], xOffSet + 0 * size, yOffSet
		// + y * size, mPaint);
		// canvas.drawBitmap(picBitmap[map[xCount - 1][y]], xOffSet
		// + (xCount - 1) * size, yOffSet + y * size, mPaint);
		//
		// // 绘制表格横线
		// canvas.drawLine(xOffSet, yOffSet + y * size, xOffSet + (xCount - 1)
		// * size, yOffSet + y * size, mPaint);
		// }

		// 绘画最后两条线
		canvas.drawLine(xOffSet, yOffSet + yCount * size, xOffSet + xCount
				* size, yOffSet + yCount * size, mPaint);
		canvas.drawLine(xOffSet + xCount * size, yOffSet, xOffSet + xCount
				* size, yOffSet + yCount * size, mPaint);

	}

	/* =======自定义辅助类方法====== */

	private void loadPicture(int key, Drawable drawable) {// 将图片生成Bitmap

		if (key == IMAGE) {// 特效图片
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

	private void createWall() {// 生成墙

		for (int x = 0; x < xCount; x++) {// 生成X方向的墙
			map[x][0] = WALL;
			map[x][yCount - 1] = WALL;

			if (setting.MODEL == setting.HARD) {// 较难
				if (x < xCount / 3 || x > xCount / 3 * 2) {
					map[x][yCount / 2] = WALL;
				}
			}

		}

		for (int y = 0; y < yCount; y++) {// 生成Y方向的墙
			map[0][y] = WALL;
			map[xCount - 1][y] = WALL;

			if (setting.MODEL == setting.HARD) {// 较难
				if (y < yCount / 3 || y > yCount / 3 * 2) {
					map[xCount / 2][y] = WALL;
				}
			}

		}

	}

	private void createSnake() {// 生成蛇

		snake.add(new Point(2, yCount - 6));
		snake.add(new Point(2, yCount - 5));
		snake.add(new Point(2, yCount - 4));
		snake.add(new Point(2, yCount - 3));

		// 填充表示蛇的图片
		for (Point point : snake) {
			setMap(point, SNAKE_BODY);
		}
		setMap(snake.get(0), SNAKE_HEAD);

	}

	private void createMouse() {// 生成老鼠

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

	private void clearGame() {// 清除当前绘画以便更新绘画界面

		for (int x = 0; x < xCount; x++) {
			for (int y = 0; y < yCount; y++) {
				map[x][y] = 0;
			}
		}
	}

	private void setMap(Point mPoint, int pictureType) {// 根据当前数据更新map数据
		// TODO Auto-generated method stub
		map[mPoint.getX()][mPoint.getY()] = pictureType;
	}

	private void gameRunning() {// 游戏进入运行状态
		clearGame();
		createWall();
		createMouse();
		updateSnake();
		SnakeView.this.invalidate();
	}

	private void updateSnake() {// 刷新蛇

		Point oldSnakeHeaad = snake.get(0);
		Point newSnakeHead = null;

		/* ======更新蛇头====== */
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

		/* ======判断游戏是否需要结束====== */
		// if (newSnakeHead.getX() == 0 || newSnakeHead.getY() == 0
		// || newSnakeHead.getX() == xCount - 1
		// || newSnakeHead.getY() == yCount - 1) {
		if (map[newSnakeHead.getX()][newSnakeHead.getY()] == WALL) {
			if (setting.WALL_YES_NO) {// 穿墙
				if (newSnakeHead.getX() == 0 || newSnakeHead.getY() == 0
						|| newSnakeHead.getX() == xCount - 1
						|| newSnakeHead.getY() == yCount - 1) {// 边墙
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
				} else {// 障碍物
					gameOver(0);
				}

			} else {// 撞墙
				gameOver(0);
			}
		} else if (newSnakeHead.equals(mouse1)) {// 吃到老鼠

			// // 图片闪烁特效
			// clearGame();
			// createWall();
			// // map[mouse.getX() - 1][mouse.getY() - 1] = IMAGE;
			// setMap(mouse, IMAGE);
			// SnakeView.this.invalidate();

			showImage = true;

			mouse1 = null;
			score++;

			if (setting.MODEL == setting.MEDIUM) {// 一般
				count++;
			} else if (setting.MODEL == setting.HARD) {
				count = Math.abs(score);
			}

			Intent intent = new Intent();// 发送广播更新当前得分
			intent.setAction("UpdateScore");
			getContext().sendBroadcast(intent);

			intent = new Intent("com.snake.service.MusicService");// 播放音乐
			intent.addFlags(1);
			getContext().startService(intent);

		} else if ((setting.MODEL != setting.EASE)
				&& newSnakeHead.equals(mouse2)) {// 吃到带毒的老鼠

			// // 图片闪烁特效
			// clearGame();
			// createWall();
			// // map[mouse.getX() - 1][mouse.getY() - 1] = IMAGE;
			// setMap(mouse, IMAGE);
			// SnakeView.this.invalidate();

			showImage = true;
			mouse2 = null;
			score--;
			if (setting.MODEL == setting.MEDIUM) {// 一般
				count--;
			} else if (setting.MODEL == setting.HARD) {
				count++;
			}

			Intent intent = new Intent();// 发送广播更新当前得分
			intent.setAction("UpdateScore");
			getContext().sendBroadcast(intent);

			intent = new Intent("com.snake.service.MusicService");// 播放音乐
			intent.addFlags(1);
			getContext().startService(intent);

		} else {// 咬到自己
			for (Point point : snake) {
				if (newSnakeHead.equals(point)) {
					gameOver(0);
					break;
				}
			}
		}
		/* ======更改蛇的长度====== */
		// if (mouse1 != null) {
		// snake.add(0, newSnakeHead);
		// } else {
		// snake.add(0, newSnakeHead);
		// }

		// if (setting.MODEL == setting.HARD) {// 吃到带毒的老鼠
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
				// Toast.makeText(getContext(), "测试", 0).show();
			}
		} else {
			snake.add(0, newSnakeHead);
			snake.remove(snake.size() - 1);
		}
		/* ======重置蛇，以便刷新====== */
		for (Point point : snake) {
			setMap(point, SNAKE_BODY);
		}
		setMap(snake.get(0), SNAKE_HEAD);

		/* ======更改蛇头图片====== */

		switch (direction) {// 更改蛇头图片
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

	public void gameOver(int flag) {// 游戏结束发送广播
		Intent intent = new Intent();// 发送广播结束游戏
		intent.setAction("SnakeDeath");
		getContext().sendBroadcast(intent);
		isRunning = false;
		setting.SNAKE_IS_LIVING = false;

		intent = new Intent("com.snake.service.MusicService");// 播放音乐
		intent.addFlags(flag);
		getContext().startService(intent);
	}

	/* ===========供外部操作的函数========= */
	public void setSnakeDirection(int direction) {// 改变蛇的移动方向

		if (isRunning && (Math.abs(this.direction - direction) != 2)) {
			this.direction = direction;
		}

		// this.direction = direction;
	}

	public int getSnakeScore() {// 返回当前的分
		return score;
	}

	public void newGame() {// 新游戏
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

		Intent intent = new Intent();// 发送广播更新当前得分
		intent.setAction("UpdateScore");
		getContext().sendBroadcast(intent);
	}

	public void stopGame() {// 暂停游戏
		isRunning = false;

	}

	public void againGame() {// 继续游戏

		if (mSnakeManger.query().size() <= 0) {
			if (setting.SNAKE_IS_LIVING) {
				isRunning = true;
				handler.sleep(200);
			} else {
				newGame();
			}
		} else {// 从数据库中读取数据
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

		Intent intent = new Intent();// 发送广播更新当前得分
		intent.setAction("UpdateScore");
		getContext().sendBroadcast(intent);

	}

	public void saveGame() {// 保存游戏
		mSnakeManger.delete();// 清空数据库
		snake.add(mouse2);// 有毒老鼠位置
		snake.add(mouse1);// 老鼠位置
		snake.add(new Point(direction, score));// 当前得分
		snake.add(new Point(count, mouse2_time));
		mSnakeManger.insert(snake);

	}

	public boolean getSnakeIsRunning() {// 判断蛇的运行状态
		return isRunning;
	}
}
