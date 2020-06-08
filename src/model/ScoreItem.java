package model;

public class ScoreItem implements Comparable<ScoreItem> {

    String username;
    int score;
    int wins;
    int draws;
    int looses;

    public ScoreItem(User user) {
        this.username = user.getUsername();
        this.score = user.getScore();
        this.wins = user.getWins();
        this.draws = user.getDraws();
        this.looses = user.getLooses();
    }

    public String getUsername() {
        return username;
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

    @Override
    public String toString() {
        return username + " " + score + " " + wins + " " + draws + " " + looses;
    }

    @Override
    public int compareTo(ScoreItem o) {
        if (score != o.score) {
            return score - o.score;
        }
        if (wins != o.wins) {
            return wins - o.wins;
        }
        if (draws != o.draws) {
            return draws - o.draws;
        }
        if (looses != o.looses) {
            return -looses + o.looses;
        }
        return -username.compareTo(o.username);
    }
}
