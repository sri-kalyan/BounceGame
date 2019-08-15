package com.game.bounce.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle{
	
	public Paddle(double w,double h,double x,double y,Color c) {
		this.setWidth(w);
		this.setHeight(h);
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setFill(c);
		this.setArcHeight(15);
	    this.setArcWidth(15);
	}
	
	public void setColor(Color c)
	{
		this.setFill(c);
	}

}
