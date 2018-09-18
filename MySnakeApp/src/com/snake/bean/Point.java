package com.snake.bean;

public class Point {// µã

	private int x;

	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	public Point() {
		// TODO Auto-generated constructor stub
	}

	public boolean equals(Point point) {

		if (point == null) {
			return false;
		}
		if (point.x == x && point.y == y) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
