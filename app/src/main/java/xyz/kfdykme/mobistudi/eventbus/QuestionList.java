package xyz.kfdykme.mobistudi.eventbus;

import java.util.ArrayList;
import java.util.List;

import xyz.kfdykme.mobistudi.bean.Question;

/**
 * @author bbw
 * @date 2017/12/5
 */

public class QuestionList {

    private List<Question> questionList = new ArrayList<>();

    public QuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
