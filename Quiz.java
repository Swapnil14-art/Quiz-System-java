import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Quiz {

    private List<Question> questions;

    public Quiz() {
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void saveScoreToFile(String name, int score, int total) {
        try {
            FileWriter writer = new FileWriter("scores.txt", true);
            writer.write("Name: " + name + " | Score: " + score + "/" + total + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving score.");
        }
    }
}