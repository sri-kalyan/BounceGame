package com.game.bounce;





import com.game.bounce.objects.Ball;
import com.game.bounce.objects.Paddle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BounceGame extends Application {
	
	private final String TITLE = "BounceGame";
	private int scoreVal =0, highScoreVal = 0;
	private Pane rootPane;
	private Ball b;
	private Slider slider;
	private Timeline game;
	private boolean gameEnded =true,isGameRestarted=false;
	private double changeX = 7, changeY = 5,prevSelVal;
	
	/**
	 * 
	 * Start method for setting the stage with all initialized Nodes and displaying on the application.
	 * 
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Setting Title for the game.
		primaryStage.setTitle(TITLE);
		
		Components components = new Components();
		
		rootPane = new Pane();
	    Scene scene = new Scene( rootPane, 800, 700 );
	    
	    //Creating a paddle Object
	    Paddle paddle = new Paddle(90,12,300,600,Color.CORNFLOWERBLUE);
	    
	    //Loading Image, labels, slider and button Nodes from Components class.
	    ImageView wallView = components.getBrickWallView();
	    
	    
	    Label scoreLabel = components.getLabel("Score:", 520, 50, new Font("Arial", 20));
	    Label selectLabel = components.getLabel("Select Level", 575, 488,new Font("Arial", 20));
	    Label highScoreLabel = components.getLabel("High Score:", 650, 50,new Font("Arial", 20));
	    Label gameOverLabel = components.getLabel("Game Over", 530, 250,new Font("Arial", 45));
	    Label restartLabel = components.getLabel("Restart Game", 580, 340,new Font("Arial", 18));
	    Label score = components.getLabel("", 580, 52,new Font("Arial", 19));
	    Label highScore = components.getLabel("", 756, 52,new Font("Arial", 19));
	   
	    restartLabel.setCursor(Cursor.HAND);
	    gameOverLabel.setTextFill(Color.RED);
	    score.textProperty().bind(new SimpleIntegerProperty(scoreVal).asString());
	    highScore.textProperty().bind(new SimpleIntegerProperty(highScoreVal).asString());

	    slider = components.getSlider(0,2,1,560, 520);
	    addSliderValueEvent(slider);   //Setting slider event which gets triggered when slider value changes.

	   
	    Button start = components.getButton("Start New Game", 570, 600);
	    setStartButtonEvent(start,gameOverLabel,restartLabel,highScore,wallView,paddle,score);//Setting start button event.
	    
	    setPaddleMovement(scene,wallView,paddle);//Setting up key press event for paddle movement.
	    
	    setRestartEvent(restartLabel,gameOverLabel,wallView,paddle,score,highScore);//Setting up restart event.
        
        rootPane.getChildren().addAll(wallView,paddle,scoreLabel,score,start,highScore,highScoreLabel,slider,selectLabel);

        primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	
	/**
	 * 
	 * Method to set event when user clicks on Restart Game label.
	 * 
	 * @param restartLabel
	 * @param gameOverLabel
	 * @param wallView
	 * @param paddle
	 * @param score
	 * @param highScore
	 */
	
	private void setRestartEvent(Label restartLabel, Label gameOverLabel, ImageView wallView, Paddle paddle,
			Label score, Label highScore) {
		restartLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(gameEnded)
				{
					rootPane.getChildren().remove(gameOverLabel);
					rootPane.getChildren().remove(restartLabel);
					
					//Setting random Color for paddle for every new game
					paddle.setColor(Color.color(Math.random(), Math.random(), Math.random()));
					slider.setDisable(true);
					
					b = new Ball(10,wallView);
					isGameRestarted = true;
					
					Timeline game =  getNewTimeLine(b,wallView,paddle,score,highScore,gameOverLabel,restartLabel);
					rootPane.getChildren().add(b);
				
					game.setCycleCount(Timeline.INDEFINITE);
					game.play();
					gameEnded = false;
				}
			}
        });
		
	}


	/**
	 * 
	 * Method to set paddleMovement when LEFT or RIGHT keys are pressed.
	 * 
	 * @param scene
	 * @param wallView
	 * @param paddle
	 */

	private void setPaddleMovement(Scene scene, ImageView wallView, Paddle paddle) {
		
		scene.setOnKeyPressed( new EventHandler<KeyEvent>(){
	    	
	    	final Bounds bounds = wallView.getBoundsInLocal();
	    	
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.LEFT && paddle.getLayoutX()>=bounds.getMinX()+40) {
		    		paddle.setLayoutX(paddle.getLayoutX()-(paddle.getLayoutX()-(bounds.getMinX()+40)>40?40:paddle.getLayoutX()-(bounds.getMinX()+50)));
		    		
		    	}else if(event.getCode()==KeyCode.RIGHT && paddle.getLayoutX()<=bounds.getMaxX()-140) {
		    		paddle.setLayoutX(paddle.getLayoutX()+((bounds.getMaxX()-140)-paddle.getLayoutX()>40?40:(bounds.getMaxX()-140)-paddle.getLayoutX()));	
		    	}
		    	
			}
	    });
		
	}


	/**
	 * 
	 * Method to set event for start game button.
	 * 
	 * @param start
	 * @param gameOverLabel
	 * @param restartLabel
	 * @param highScore
	 * @param wallView
	 * @param paddle
	 * @param score
	 */

	private void setStartButtonEvent(Button start, Label gameOverLabel, Label restartLabel, Label highScore,
			ImageView wallView, Paddle paddle, Label score) {
		
		 start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent event) {
					if(gameEnded)
					{
						if(rootPane.getChildren().contains(gameOverLabel)) {
							rootPane.getChildren().remove(gameOverLabel);
						}
						if(rootPane.getChildren().contains(restartLabel)) {
							rootPane.getChildren().remove(restartLabel);
						}
						
						//Setting random Color for paddle for every new game
						paddle.setColor(Color.color(Math.random(), Math.random(), Math.random()));
						
						b = new Ball(10,wallView);
						isGameRestarted = false;
						
						highScoreVal =0;
						highScore.textProperty().bind(new SimpleIntegerProperty(highScoreVal).asString());
						
						Timeline game =  getNewTimeLine(b,wallView,paddle,score,highScore,gameOverLabel,restartLabel);
						rootPane.getChildren().add(b);
					
						game.setCycleCount(Timeline.INDEFINITE);
						game.play();
						gameEnded = false;
					}
				}
	        });
		
	}


	/**
	 * 
	 * Method to setup Slider to select velocity of the ball.
	 * 
	 * @param slider
	 */

	private void addSliderValueEvent(Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double selectVal = Math.round(newValue.doubleValue());
				prevSelVal = slider.getValue();
				slider.setValue(selectVal);
			}
		});
		
	}

	/**
	 * 
	 * Method to create a new game time line when start or restart events occur.
	 * 
	 * @param ball
	 * @param wallView
	 * @param paddle
	 * @param score
	 * @param highScore
	 * @param gameOverLabel
	 * @param restartLabel
	 * @return
	 */

	private Timeline getNewTimeLine(Ball ball, ImageView wallView, Paddle paddle, Label score, Label highScore,Label gameOverLabel,Label restartLabel) {
		
			if(isGameRestarted)
			{
				slider.setValue(prevSelVal);
			}
			else {
				updateChangedXY(slider.getValue());
			}
			
			scoreVal = 0;
			score.textProperty().bind(new SimpleIntegerProperty(scoreVal).asString());
			
	    	game = new Timeline(new KeyFrame(Duration.millis(15), new EventHandler<ActionEvent>() {

            public void handle(final ActionEvent t) {
            	ball.setLayoutX(ball.getLayoutX() + changeX);
            	ball.setLayoutY(ball.getLayoutY() + changeY);

                final Bounds bounds = wallView.getBoundsInLocal();
                final boolean atRightBorder = ball.getLayoutX() >= (bounds.getMaxX()-40 - ball.getRadius());
                final boolean atLeftBorder = ball.getLayoutX() <= (bounds.getMinX()+40 + ball.getRadius());
                final boolean atTopBorder = ball.getLayoutY() <= (bounds.getMinY()+40 + ball.getRadius());
                final boolean isCollision = ball.getLayoutY() >= paddle.getLayoutY()&&ball.getLayoutY()<=paddle.getLayoutY()+12 && paddle.getLayoutX()<=ball.getLayoutX() && paddle.getLayoutX()+90>=ball.getLayoutX();
                
                if (atRightBorder || atLeftBorder) {
                	changeX *= -1;
                }
                if (atTopBorder) {
                	changeY *= -1;
                }
                if(isCollision) {
                	ball.setLayoutY(ball.getLayoutY()-20);
                	score.textProperty().bind(new SimpleIntegerProperty(++scoreVal).asString());
                	changeY *= -1;
                }
                if(ball.getLayoutY() > paddle.getLayoutY()+13)
                {
                	stopGame(highScore,gameOverLabel,restartLabel);
                }
            }
        }));
	    
	    return game;
	}
	
	
	/**
	 * 
	 * Method to update change values when match is not restarted.
	 * 
	 * @param value
	 */
	private void updateChangedXY(double value) {
		
		switch(Double.toString(value))
		{
			case "0.0" :	changeX = 4;
							changeY = 4;
							break;
							
			case "1.0" :	changeX = 7;
							changeY =5;
							break;
							
			case "2.0" :	changeX = 9;
							changeY =6;
							break;
			
			default    :	changeX = 7;
							changeY =5;
							break;
		}
		
	}


	/**
	 * 
	 * Method to handle GameOver scenario.
	 * 
	 * @param highScore
	 * @param gameOverLabel
	 * @param restartLabel
	 */
	private void stopGame(Label highScore,Label gameOverLabel, Label restartLabel) {
		game.stop();
		rootPane.getChildren().remove(b);
	    rootPane.getChildren().add(gameOverLabel);
	    rootPane.getChildren().add(restartLabel);
		gameEnded = true;
		if(scoreVal>highScoreVal)
		{
			highScoreVal = scoreVal;
			highScore.textProperty().bind(new SimpleIntegerProperty(highScoreVal).asString());
		}
		slider.setDisable(false);
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
