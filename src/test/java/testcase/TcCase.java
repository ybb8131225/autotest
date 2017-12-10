package testcase;

import operate.TrainingMain;
import operate.Train;

public class TcCase {

	TrainingMain trainingMain = new TrainingMain();
	Train train = new Train();

	public void testOutTrain(){
		train.outTrain();
	}

	public void testTrain(){
		train.train();
	}

	public void testPublish(){
		trainingMain.publish();
	}

	public void testHistoryTrain(){
		trainingMain.trainHistory();
	}

	public void testSchedule(){
		trainingMain.scheduledOnlyOneDay();
	}

	public void testNextSchedule(){
		trainingMain.nextSchedule();
	}

	public void testOutSchedule(){
		trainingMain.outSchedule();
	}

	public void testDefinedSchedule(){
		trainingMain.definedSchedule();
	}

	public void testTT(){
//		trainingMain.publish();
//		trainingMain.testPraise();
	}

}
