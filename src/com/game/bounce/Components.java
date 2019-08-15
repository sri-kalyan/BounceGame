package com.game.bounce;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class Components {

	protected ImageView getBrickWallView() {
		
		 Image brickwall = new Image("brickwall.jpg");
		 ImageView wallView = new ImageView(brickwall);
		 wallView.setFitHeight(700);
		 wallView.setFitWidth(500);
		 return wallView;
		 
	}
	
	protected Label getLabel(String labelName,double layoutX, double layoutY, Font labelFont) {
		
		Label newLabel = new Label(labelName);
		newLabel.setLayoutX(layoutX);
		newLabel.setLayoutY(layoutY);
		newLabel.setFont(labelFont);
		return newLabel;
		
	}
	
	protected Slider getSlider(double min, double max,double val,double layoutX, double layoutY) {
		Slider slider = new Slider(min,max,val);
	    slider.setLayoutX(layoutX);
	    slider.setLayoutY(layoutY);
	    slider.setShowTickLabels(true);
	    slider.setShowTickMarks(true);
	    slider.setMajorTickUnit(1);
	    slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        
        return slider;
		
	}
	
	protected Button getButton(String buttonName, double layoutX,double layoutY)
	{
		Button button = new Button("Start New Game");
		button.setLayoutX(570);
		button.setLayoutY(600);
		return button;
	}
	
}
