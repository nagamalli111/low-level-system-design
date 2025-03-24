package com.matty.stackoverflow;

/** REQUIREMENTS
 * 1. User can post questions, answer questions, and comment on question and answers.
 * 2. Users can vote question and answers.
 * 3. Questions should have tag associated with it.
 * 4. User can search questions based on keywords, tags and user profile.
 * 5. System should add reputation score to users based on their activity and quality of contributions.
 * 6. The system should handle concurrent access and ensure data consistency.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/** Classes, Interfaces and Enumerations.
 * 1. User, Question
 *
 */
class IdGenerator {
    private IdGenerator() {
        // Private constructor to prevent instantiation
    }

    public static int generateId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}

@Getter
class Tag {
    int id;
    String name;
    public Tag(String name) {
        this.name = name;
        this.id = generateId();
    }

    private int generateId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}

@Getter
class User {
    private int id;
    private String userName;
    private  String email;
    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;
    @Setter
    private int reputation;

    private static final int QUESTION_REPUTATION = 5;
    private static final int ANSWER_REPUTATION = 10;
    private static final int COMMENT_REPUTATION = 2;

    public User(String userName, String email) {
        this.id = IdGenerator.generateId();
        this.userName = userName;
        this.email = email;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        comments = new ArrayList<>();
        reputation = 0;
    }

    public Question askQuestion(String title, String content, List<String> tags) {
        Question question = new Question(this, title, content, tags);
        this.questions.add(question);
        this.updateReputation(QUESTION_REPUTATION);
        return question;
    }

    public Answer answerQuestion(Question question, String answer) {
        Answer ans = new Answer(this, question, answer);
        this.answers.add(ans);
        this.updateReputation(ANSWER_REPUTATION);
        return ans;
    }

    public Comment addComment(Commentable commentable, String content) {
        Comment comment = new Comment(this, content);
        commentable.addComment(comment);
        this.comments.add(comment);
        this.updateReputation(COMMENT_REPUTATION);
        return comment;
    }

    public void updateReputation(int value) {
        this.setReputation(this.getReputation() + value);
    }

}


@Getter
class Question implements Commentable, Votable {
    private int id;
    private String title;
    private String content;
    private List<Answer> answers;
    private List<Comment> comments;
    private List<Vote> votes;
    private List<Tag> tags;
    private User author;
    Question(User user, String title, String content, List<String> tagsNames) {
        this.author = user;
        this.id = generateId();
        this.title = title;
        this.content = content;
        this.answers = new ArrayList<>();
        this.comments = new ArrayList<>();
        tagsNames.forEach(tagName -> this.tags.add(new Tag(tagName)));
    }

    private int generateId() {
       return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void vote(User user, int value) {
        if (value != 1 && value != -1) {
            throw new IllegalArgumentException("Vote value must be either 1 or -1");
        }
        votes.removeIf(v -> v.getUser().equals(user));
        this.votes.add(new Vote(user, value));
        this.author.updateReputation(5 * value);
    }

    @Override
    public int getVoteCount() {
        return this.votes.size();
    }
}

@AllArgsConstructor
@Getter
class Vote {
    private User user;
    private int value;
}

interface Votable {
    void vote(User user, int value);
    int getVoteCount();
}

interface Commentable {
    void addComment(Comment comment);
    List<Comment> getComments();
}

@Getter
class Answer implements Votable, Commentable {
    private int id;
    private String content;
    private User postedBy;
    private Question question;
    private boolean isAccepted;
    private int votes;

    Answer(User user, Question question, String content) {
        this.postedBy = user;
        this.question = question;
        this.content = content;
    }

    @Override
    public void vote(User user, int value) {

    }

    @Override
    public int getVoteCount() {
        return 0;
    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public List<Comment> getComments() {
        return List.of();
    }

    public void markAsAccepted() {
        if (isAccepted) {
            throw new IllegalStateException("This answer is already accepted");
        }
        isAccepted = true;
        postedBy.updateReputation(15);
    }
}

class Comment {
    private int id;
    private String content;
    private User user;
    private Date createdOn;

    Comment(User user, String comment) {
        this.id = IdGenerator.generateId();
        this.content = comment;
        this.user = user;
        this.createdOn = new Date();
    }

}

class StackOverFlow {
    private final Map<Integer, User> users;
    private final Map<Integer, Question> questions;
    private final Map<Integer, Answer> answers;
    private final Map<String, Tag> tags;

    StackOverFlow() {
        this.users = new ConcurrentHashMap<>();
        this.questions = new ConcurrentHashMap<>();
        this.answers = new ConcurrentHashMap<>();
        this.tags = new ConcurrentHashMap<>();
    }

    public User createUser(String username, String email) {
        int id = users.size() + 1;
        User user = new User(username, email);
        users.put(id, user);
        return user;
    }

    public Question askQuestion(User user, String title, String content, List<String> tags) {
        Question question = user.askQuestion(title, content, tags);
        questions.put(question.getId(), question);
        for (Tag tag : question.getTags()) {
            this.tags.putIfAbsent(tag.getName(), tag);
        }
        return question;
    }

    public Answer answerQuestion(User user, Question question, String content) {
        Answer answer = user.answerQuestion(question, content);
        answers.put(answer.getId(), answer);
        return answer;
    }

    public Comment addComment(User user, Commentable commentable, String content) {
        return user.addComment(commentable, content);
    }

    public void voteQuestion(User user, Question question, int value) {
        question.vote(user, value);
    }

    public void voteAnswer(User user, Answer answer, int value) {
        answer.vote(user, value);
    }

    public void acceptAnswer(Answer answer) {
        answer.markAsAccepted();
    }

    public List<Question> searchQuestions(String query) {
       return questions.values().stream().filter(question -> question.getContent().contains(query))
                .toList();
    }

}
public class StackOverFlowDemo {
    public static void main(String[] args) {
        StackOverFlow system = new StackOverFlow();
        User user = system.createUser("matty", "email");
        Question question = system.askQuestion(user, "What is next js", "Help me with it", List.of("TECH"));
        system.addComment(user, question, "First comment");
    }
}
