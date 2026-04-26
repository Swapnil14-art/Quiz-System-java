import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizGUI {

    private JFrame frame;

    private Quiz quiz;
    private List<Question> questions;

    private int currentIndex = 0;
    private int score = 0;
    private String playerName = "";

    public QuizGUI() {
        quiz = new Quiz();

        quiz.addQuestion(new Question("Java is?",
                new String[]{"Programming Language", "Car", "OS", "Game"}, 'A'));

        quiz.addQuestion(new Question("OOP stands for?",
                new String[]{"Object Oriented Programming", "Only One Program", "None", "Other"}, 'A'));

        quiz.addQuestion(new Question("Which is loop?",
                new String[]{"for", "loop", "repeat", "iterate"}, 'A'));

        quiz.addQuestion(new Question("JVM stands for?",
                new String[]{"Java Virtual Machine", "None", "Other", "System"}, 'A'));

        quiz.addQuestion(new Question("Which keyword creates object?",
                new String[]{"new", "class", "make", "build"}, 'A'));

        setupFrame();
        showMainMenu();
    }

    private void setupFrame() {
        frame = new JFrame("Online Quiz Platform");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refresh() {
        frame.revalidate();
        frame.repaint();
    }

    // ================= MENU =================
    private void showMainMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Online Quiz Platform", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton adminBtn = new JButton("Admin");
        JButton playerBtn = new JButton("Player");

        adminBtn.addActionListener(e -> showAdminScreen());
        playerBtn.addActionListener(e -> showPlayerScreen());

        panel.add(title);
        panel.add(adminBtn);
        panel.add(playerBtn);

        frame.setContentPane(panel);
        refresh();
    }

    // ================= ADMIN =================
    private void showAdminScreen() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        JTextField questionField = new JTextField();
        JTextField[] options = new JTextField[4];
        for (int i = 0; i < 4; i++) options[i] = new JTextField();

        JTextField answerField = new JTextField();

        panel.add(new JLabel("Question:"));
        panel.add(questionField);

        for (int i = 0; i < 4; i++) {
            panel.add(new JLabel("Option " + (char) ('A' + i)));
            panel.add(options[i]);
        }

        panel.add(new JLabel("Correct Answer (A/B/C/D):"));
        panel.add(answerField);

        JButton addBtn = new JButton("Add Question");
        JButton backBtn = new JButton("Back");

        addBtn.addActionListener(e -> {
            try {
                String q = questionField.getText();

                String[] opts = new String[4];
                for (int i = 0; i < 4; i++) {
                    opts[i] = options[i].getText();
                }

                char ans = answerField.getText().toUpperCase().charAt(0);

                quiz.addQuestion(new Question(q, opts, ans));

                JOptionPane.showMessageDialog(frame, "Question Added!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!");
            }
        });

        backBtn.addActionListener(e -> showMainMenu());

        panel.add(addBtn);
        panel.add(backBtn);

        frame.setContentPane(panel);
        refresh();
    }

    // ================= PLAYER =================
    private void showPlayerScreen() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JTextField nameField = new JTextField();

        JButton startBtn = new JButton("Start Quiz");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Enter Name:"));
        panel.add(nameField);
        panel.add(startBtn);
        panel.add(backBtn);

        startBtn.addActionListener(e -> {
            playerName = nameField.getText();
            startQuiz();
        });

        backBtn.addActionListener(e -> showMainMenu());

        frame.setContentPane(panel);
        refresh();
    }

    // ================= QUIZ =================
    private void startQuiz() {
        questions = new ArrayList<Question>(quiz.getQuestions());
        Collections.shuffle(questions);

        currentIndex = 0;
        score = 0;

        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex >= 5) {
            quiz.saveScoreToFile(playerName, score, 5);
            JOptionPane.showMessageDialog(frame, "Score: " + score + "/5");
            showMainMenu();
            return;
        }

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        Question q = questions.get(currentIndex);

        panel.add(new JLabel(q.getQuestionText()));

        String[] opts = q.getOptions();

        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton(opts[i]);
            int index = i;

            btn.addActionListener(e -> {
                if (index == (q.getCorrectAnswer() - 'A')) {
                    score++;
                }
                currentIndex++;
                showQuestion();
            });

            panel.add(btn);
        }

        frame.setContentPane(panel);
        refresh();
    }

    public static void main(String[] args) {
        new QuizGUI();
    }
}