package model;

public class User implements Comparable<User> {
    private String username;
    private String password;
    private int score;
    private int wins;
    private int draws;
    private int looses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLooses() {
        return looses;
    }

    public void addWins() {
        this.wins++;
    }

    public void addDraws() {
        this.draws++;
    }

    public void addLooses() {
        this.looses++;
    }

    public void addScore(int gameScore) {
        this.score += gameScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;
        return username.equals(((User)obj).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public int compareTo(User anotherUser) {
        return username.compareTo(anotherUser.username);
    }
}
