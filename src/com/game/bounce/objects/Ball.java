package com.game.bounce.objects;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Sphere;

public class Ball extends Sphere{
	
	
	public Ball(double r,ImageView wallView) {
		this.setRadius(r);
		double xrandom = (Math.random() * (((wallView.getFitWidth()-70) - 70) + 1)) + 70;
		this.setLayoutX(xrandom);
		this.setLayoutY(50);
	}

}
