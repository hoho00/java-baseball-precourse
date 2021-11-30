package controller.classes;

import static camp.nextstep.edu.missionutils.Randoms.*;
import static model.constants.ConstantsForGame.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.classes.CountingStatus;
import model.constants.BallStatus;
import model.constants.StrikeStatus;

public class BaseBallAnswer {
	private String answer = "";
	private int[] checkingList = new int[MAX_LENGTH_OF_ARRAY];
	private List<Integer> answerArray = new ArrayList<>(3);

	public BaseBallAnswer() {
		initCheckingList();
		cleanAnswerArray();
	}

	public void cleanAnswerArray() {
		// for (int i = 0; i < answerArray.size(); i++) {
		// 	answerArray.remove(i);
		// }
		answerArray = new ArrayList<>(3);
	}

	public void makeAnswer() {
		int[] checkDuplication = {0,0,0,0,0,0,0,0,0,0};
		int duplicationCount = 0;

		while(duplicationCount < 3) {
			int tmpRandom = pickNumberInRange(1,9);
			if(checkDuplication[tmpRandom] == 0) {
				makeAnswerArray(tmpRandom);
				//answerArray.add(tmpRandom);
				duplicationCount++;
				checkDuplication[tmpRandom] = 1;
			}
		}
		//answerArray = pickUniqueNumbersInRange(1, 9, 3);
		initCheckingList();
		refreshAnswer();
		System.out.println(answerArray);
	}
	private void makeAnswerArray(int randomNumber) {
		answerArray.add(randomNumber);
		if (answerArray.size() > 3) {
			for(int i = 0; i < answerArray.size(); i++) {
				answerArray.remove(i);
			}
			answerArray.add(randomNumber);
		}
	}
	public String getAnswer() {
		return answer;
	}

	public CountingStatus calculateStatus(String userAnswer) {
		int ballCount = 0;
		int strikeCount = 0;
		int[] userCheckingArray = getArrayFromString(userAnswer);
		for (int i = 0; i < userCheckingArray.length; i++) {
			if (checkingList[userCheckingArray[i]] == 0) {
				continue;
			} else if (checkingList[userCheckingArray[i]] == i + 1) {
				strikeCount++;
			} else if (checkingList[userCheckingArray[i]] != i + 1) {
				ballCount++;
			}
		}
		return new CountingStatus(getBallsFromInt(ballCount), getStrikesFromInt(strikeCount));
	}

	private int[] getArrayFromString(String formatString) {
		int[] array = Arrays.stream(formatString.substring(0, formatString.length()).split("")).map(String::trim).mapToInt(Integer::parseInt).toArray();
		return array;
	}

	private void initCheckingList() {
		for (int i = 0; i < checkingList.length; i++) {
			checkingList[i] = 0;
		}
	}

	private void refreshAnswer() {
		for (int i = 0; i < answerArray.size(); i++) {
			answer += String.valueOf(answerArray.get(i));
			checkingList[answerArray.get(i)] = i + 1;
		}
	}

	private StrikeStatus getStrikesFromInt(int StrikeStatusInt) {
		//return
		if(StrikeStatusInt == 0) {
			return StrikeStatus.zero;
		} else if (StrikeStatusInt == 1) {
			return StrikeStatus.one;
		} else if (StrikeStatusInt == 2) {
			return StrikeStatus.two;
		} else if (StrikeStatusInt == 3) {
			return StrikeStatus.three;
		}
		return StrikeStatus.zero;
	}

	private BallStatus getBallsFromInt(int BallStatusInt) {
		if(BallStatusInt == 0) {
			return BallStatus.zero;
		} else if (BallStatusInt == 1) {
			return BallStatus.one;
		} else if (BallStatusInt == 2) {
			return BallStatus.two;
		} else if (BallStatusInt == 3) {
			return BallStatus.three;
		} else if (BallStatusInt == 4) {
			return BallStatus.four;
		}
		return BallStatus.zero;
	}
}
